// Copyright 2019 Udi Finkelstein
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.udifink.fngr;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class FngrTest {
    @Test
    public void testCalcFingerPrintUntrackedFile() {
        try {
            ArrayList<String> tests = new ArrayList<String>();
            tests.add("/etc/passwd:Failed to identify untracked file as such");
            tests.add("/etc:Failed to identify untracked directory as such");
            Fngr fngr = new Fngr();
            VCS vcs;
            String[] s2;
            for (String s: tests) {
                s2 = s.split(":");
                vcs = fngr.calcFingerPrint(s2[0]);
                assertTrue(s2[1], vcs instanceof NoVCS);
            }
        } catch (IOException e) {
            assertTrue("Caught IOException!", false);
        }
    }

    private void testCalcVcsFingerPrint(
            String path, Class <?> cls, boolean exists, boolean is_versioned, boolean is_modified,
            boolean is_file, String revision) {
        try {
            Fngr fngr = new Fngr();
            VCS vcs = fngr.calcFingerPrint(path);
            assertTrue(path + ": VCS object type failure", vcs.getClass() == cls);
            assertTrue(path + ": 'exists' flag", exists == vcs.exists);
            assertTrue(path + ": 'is_file' flag", is_file == vcs.is_file);
            if (!exists)
                return;
            assertTrue(path + ": 'is_versioned' flag", is_versioned == vcs.is_versioned);
            if (!is_versioned)
                return;
                    assertTrue(path + ": 'Revision'", revision.equals(vcs.revision));
                    assertTrue(path + ": 'is_modified' flag", is_modified == vcs.is_modified);
        } catch (IOException e) {
            assertTrue("Caught IOException!", false);
        }
    }

    @Test
    public void testCalcFingerSVN1() {
        testCalcVcsFingerPrint(
            "../fngr-testdir/test-svn-workdir",
            SVNVCS.class,
            true /* exists */,
            true /* is_versioned */,
            false /* is modified */,
            false /* is file */,
            "0" /* revision */);
    }

    @Test
    public void testCalcFingerSVN2() {
        testCalcVcsFingerPrint(
            "../fngr-testdir/test-svn-workdir/non-head-versioned-file",
            SVNVCS.class,
            true /* exists */,
            true /* is_versioned */,
            false /* is modified */,
            true /* is file */,
            "1" /* revision */);
    }

    @Test
    public void testCalcFingerSVN3() {
        testCalcVcsFingerPrint(
            "../fngr-testdir/test-svn-workdir/unversioned-file",
            SVNVCS.class,
            true /* exists */,
            false /* is_versioned */,
            false /* is modified */,
            true /* is file */,
            "" /* revision */);
    }

    @Test
    public void testCalcFingerSVN4() {
        testCalcVcsFingerPrint(
            "../fngr-testdir/test-svn-workdir/versioned-dir",
            SVNVCS.class,
            true /* exists */,
            true /* is_versioned */,
            false /* is modified */,
            false /* is file */,
            "7" /* revision */);
    }

    @Test
    public void testCalcFingerSVN5() {
        testCalcVcsFingerPrint(
            "../fngr-testdir/test-svn-workdir/versioned-dir/versioned-file-in-a-versioned-dir",
            SVNVCS.class,
            true /* exists */,
            true /* is_versioned */,
            false /* is modified */,
            true /* is file */,
            "8" /* revision */);
    }

    @Test
    public void testCalcFingerSVN6() {
        testCalcVcsFingerPrint(
            "../fngr-testdir/test-svn-workdir/versioned-dir/unversioned-file-in-a-versioned-dir",
            SVNVCS.class,
            true /* exists */,
            false /* is_versioned */,
            false /* is modified */,
            true /* is file */,
            "" /* revision */);
    }

    @Test
    public void testCalcFingerSVN7() {
        testCalcVcsFingerPrint(
            "../fngr-testdir/test-svn-workdir/unversioned-dir",
            SVNVCS.class,
            true /* exists */,
            false /* is_versioned */,
            false /* is modified */,
            false /* is file */,
            "" /* revision */);
    }

    @Test
    public void testCalcFingerSVN8() {
        testCalcVcsFingerPrint(
            "../fngr-testdir/test-svn-workdir/nonexistent",
            SVNVCS.class,
            false /* exists */,
            false /* is_versioned */,
            false /* is modified */,
            false /* is file */,
            "" /* revision */);
    }

    @Test
    public void testCalcFingerSVN9() {
        testCalcVcsFingerPrint(
            "../fngr-testdir/test-svn-workdir/unversioned-dir/unversioned-file-in-an-unversioned-dir",
            SVNVCS.class,
            true /* exists */,
            false /* is_versioned */,
            false /* is modified */,
            true /* is file */,
            "" /* revision */);
    }

    @Test
    public void testCalcFingerSVN10() {
        testCalcVcsFingerPrint(
            "../fngr-testdir/test-svn-workdir/modified-non-head-versioned-file",
            SVNVCS.class,
            true /* exists */,
            true /* is_versioned */,
            true /* is modified */,
            true /* is file */,
            "3" /* revision */);
    }

    @Test
    public void testCalcFingerGit1() {
        testCalcVcsFingerPrint(
            "../fngr-testdir/test-git/non-head-versioned-file",
            GitVCS.class,
            true /* exists */,
            true /* is_versioned */,
            false /* is modified */,
            true /* is file */,
            "7945dc7ccb5deb4a14a9e58592e04052edafbb3e" /* revision */);
    }

}
