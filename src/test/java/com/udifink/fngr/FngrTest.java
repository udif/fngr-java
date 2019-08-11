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

    private void testCalcFingerSVN(String path, String errmsg) {
        try {
            Fngr fngr = new Fngr();
            VCS vcs = fngr.calcFingerPrint(path);
            assertTrue(errmsg, vcs instanceof SVNVCS);
        } catch (IOException e) {
            assertTrue("Caught IOException!", false);
        }
    }

    @Test
    public void testCalcFIngerSVN1() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir",
            "Failed to recognize workdir root as inside SVN repository");
    }

    @Test
    public void testCalcFIngerSVN2() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/versioned-file",
            "Failed to recognize versioned SVN file as inside SVN repository");
    }

    @Test
    public void testCalcFIngerSVN3() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/unversioned-file",
            "Failed to recognize unversioned SVN file as inside SVN repository");
    }

    @Test
    public void testCalcFIngerSVN4() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/versioned-dir",
            "Failed to recognize a versioned directory in SVN workdir as inside SVN repository");
    }

    @Test
    public void testCalcFIngerSVN5() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/versioned-dir/versioned-file-in-a-versioned-dir",
            "Failed to recognize a versioned file inside a versioned directory in SVN workdir as inside SVN repository");
    }

    @Test
    public void testCalcFIngerSVN6() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/versioned-dir/unversioned-file-in-a-versioned-dir",
            "Failed to recognize an unversioned file inside a versioned directory in SVN workdir as inside SVN repository");
    }

    @Test
    public void testCalcFIngerSVN7() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/unversioned-dir",
            "Failed to recognize an unversioned directory in SVN workdir as inside SVN repository");
    }

    @Test
    public void testCalcFIngerSVN8() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/nonexistent",
            "Failed to recognize a non existing file/directoryin SVN workdir as inside SVN repository");
    }

    @Test
    public void testCalcFIngerSVN9() {
        testCalcFingerSVN(
            "../fngr-testdir/test-svn-workdir/unversioned-dir/unversioned-file-in-unversioned-dir",
            "Failed to recognize an unversioned file inside an unversioned directory in SVN workdir as inside SVN repository");
    }
}