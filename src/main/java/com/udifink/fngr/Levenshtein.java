package com.udifink.fngr;

import java.util.Arrays;

/**
 * The Levenshtein distance between two words is the minimum number of
 * single-character edits (insertions, deletions or substitutions) required to
 * change one string into the other.
 *
 * Originally taken from:
 * https://github.com/tdebatty/java-string-similarity/blob/624fe288f34eb14ac7f9db1a7695ad74525fb94b/src/main/java/info/debatty/java/stringsimilarity/Levenshtein.java
 * 
 * Modifications (by Udi Finkelstein):
 * 1. Change arguments from Strings to byte[] to prevent needless copying
 * 2. Added length + offset to support future heuristics of running a batch of smaller levenshtein runs on very large files
 * 3. Turned methods into static so we don't need to create a useless class object that holds no state.
 * 
 * @author Thibault Debatty, modified by Udi Finkelstein
 */
public class Levenshtein {

    /**
     * Equivalent to distance(byte[] b1, byte[] b2, Integer.MAX_VALUE).
     */
    public static final int distance(byte[] b1, byte[] b2) {
        return distance(b1, b2, 0, 0, b1.length, b2.length, Integer.MAX_VALUE);
    }

    public static final int distance(byte[] b1, byte[] b2,
                                 final int offset1, final int offset2,
                                 final int length1, final int length2) {
        return distance(b1, b2, offset1, offset2, length1, length2, Integer.MAX_VALUE);
    }
    /**
     * The Levenshtein distance, or edit distance, between two words is the
     * minimum number of single-character edits (insertions, deletions or
     * substitutions) required to change one word into the other.
     *
     * http://en.wikipedia.org/wiki/Levenshtein_distance
     *
     * It is always at least the difference of the sizes of the two strings.
     * It is at most the length of the longer string.
     * It is zero if and only if the strings are equal.
     * If the strings are the same size, the Hamming distance is an upper bound
     * on the Levenshtein distance.
     * The Levenshtein distance verifies the triangle inequality (the distance
     * between two strings is no greater than the sum Levenshtein distances from
     * a third string).
     *
     * Implementation uses dynamic programming (Wagner–Fischer algorithm), with
     * only 2 rows of data. The space requirement is thus O(m) and the algorithm
     * runs in O(mn).
     *
     * This implementation works on a sub-range of byte arrays (length+offset)
     * but these are allowed to wrap around!
     * The intended usage is to work on large files - instead of having an O(N*M) time
     * we split them into smaller K bites, having those bites overlap
     * 
     * @param b1 The first byte array to compare.
     * @param b2 The second byte array to compare.
     * @param offset1 Where to start checking the 1st byte array
     * @param offset1 Where to start checking the 2nd byte array
     * @param length1 How many bytes to check on the 1st byte array
     * @param length2 How many bytes to check on the 2nd byte array
     * @param limit The maximum result to compute before stopping. This
     *              means that the calculation can terminate early if you
     *              only care about strings with a certain similarity.
     *              Set this to Integer.MAX_VALUE if you want to run the
     *              calculation to completion in every case.
     * @return The computed Levenshtein distance.
     * @throws NullPointerException if b1 or b2 is null.
     */
    public static final int distance(byte[] b1, byte[] b2,
                                 final int offset1, final int offset2,
                                 final int length1, final int length2,
                                 final int limit) {
        if (b1 == null) {
            throw new NullPointerException("b1 must not be null");
        }

        if (b2 == null) {
            throw new NullPointerException("b2 must not be null");
        }

        final int l1 = Math.min(b1.length, length1);
        final int l2 = Math.min(b2.length, length2);

        if (Arrays.equals(b1, b2)) {
            return 0;
        }

        if (l1 == 0) {
            return l2;
        }

        if (l2 == 0) {
            return l1;
        }

        // create two work vectors of integer distances
        int[] v0 = new int[l2 + 1];
        int[] v1 = new int[l2 + 1];
        int[] vtemp;

        // initialize v0 (the previous row of distances)
        // this row is A[0][i]: edit distance for an empty s
        // the distance is just the number of characters to delete from t
        for (int i = 0; i < v0.length; i++) {
            v0[i] = i;
        }

        for (int i = 0; i < l1; i++) {
            // calculate v1 (current row distances) from the previous row v0
            // first element of v1 is A[i+1][0]
            //   edit distance is delete (i+1) chars from s to match empty t
            v1[0] = i + 1;

            int minv1 = v1[0];
            final int index1 = (i + offset1) % b1.length;
            // use formula to fill in the rest of the row
            for (int j = 0; j < l2; j++) {
                int cost = 1;
                final int index2 = (i + offset2) % b2.length;
                if (b1[index1] == b2[index2]) {
                    cost = 0;
                }
                v1[j + 1] = Math.min(
                        v1[j] + 1,              // Cost of insertion
                        Math.min(
                                v0[j + 1] + 1,  // Cost of remove
                                v0[j] + cost)); // Cost of substitution

                minv1 = Math.min(minv1, v1[j + 1]);
            }

            if (minv1 >= limit) {
                return limit;
            }

            // copy v1 (current row) to v0 (previous row) for next iteration
            //System.arraycopy(v1, 0, v0, 0, v0.length);

            // Flip references to current and previous row
            vtemp = v0;
            v0 = v1;
            v1 = vtemp;

        }

        return v0[b2.length];
    }
}