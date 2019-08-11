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

    private void testCalcFingerSVN(String path, boolean exists, boolean is_versioned) {
        try {
            Fngr fngr = new Fngr();
            VCS vcs = fngr.calcFingerPrint(path);
            assertTrue(path + ": VCS object type failure", vcs instanceof SVNVCS);
            assertTrue(path + ": 'exists' flag", exists == vcs.exists);
        } catch (IOException e) {
            assertTrue("Caught IOException!", false);
        }
    }

    @Test
    public void testCalcFingerSVN1() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir",
            true /* exists */,
            false /* is_versioned */);
    }

    @Test
    public void testCalcFingerSVN2() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/versioned-file",
            true /* exists */,
            true /* is_versioned */);
    }

    @Test
    public void testCalcFingerSVN3() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/unversioned-file",
            true /* exists */,
            false /* is_versioned */);
    }

    @Test
    public void testCalcFingerSVN4() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/versioned-dir",
            true /* exists */,
            true /* is_versioned */);
    }

    @Test
    public void testCalcFingerSVN5() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/versioned-dir/versioned-file-in-a-versioned-dir",
            true /* exists */,
            true /* is_versioned */);
    }

    @Test
    public void testCalcFingerSVN6() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/versioned-dir/unversioned-file-in-a-versioned-dir",
            true /* exists */,
            false /* is_versioned */);
    }

    @Test
    public void testCalcFingerSVN7() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/unversioned-dir",
            true /* exists */,
            false /* is_versioned */);
    }

    @Test
    public void testCalcFingerSVN8() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/nonexistent",
            false /* exists */,
            false /* is_versioned */);
    }

    @Test
    public void testCalcFingerSVN9() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/unversioned-dir/unversioned-file-in-an-unversioned-dir",
            true /* exists */,
            false /* is_versioned */);
    }
}