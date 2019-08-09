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

import org.junit.Test;

public class FngrTest {
    @Test
    public void testCalcFingerPrintUntrackedFile() {
        try {
            Fngr fngr = new Fngr();
            VCS vcs = fngr.calcFingerPrint("/etc/passwd");
            assertTrue("Failed to identify untracked file as such", vcs instanceof NoVCS);
        } catch (IOException e) {
            assertTrue("Caught IOException!", false);
        }
    }

    @Test
    public void testCalcFingerPrintUntrackedDir() {
        try {
            Fngr fngr = new Fngr();
            VCS vcs = fngr.calcFingerPrint("/etc");
            assertTrue("Failed to identify untracked file as such", vcs instanceof NoVCS);
        } catch (IOException e) {
            assertTrue("Caught IOException!", false);
        }
    }
    /*
    @Test
    public void testisItMeSVNWorkDirRoot() {
        Fngr fngr = new Fngr();
        assertTrue("Failed to recognize workdir root as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir")));
    }

    @Test
    public void testisItMeSVNVersionedFile() {
        Fngr fngr = new Fngr();
        assertTrue("Failed to recognize versioned SVN file as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/versioned-file")));
    }

    @Test
    public void testisItMeSVNUnversionedFile() {
        Fngr fngr = new Fngr();
        assertTrue("Failed to recognize unversioned SVN file as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/unversioned-file")));
    }

    @Test
    public void testisItMeSVNVersionedDir() {
        Fngr fngr = new Fngr();
        assertTrue("Failed to recognize a versioned directory in SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/versioned-dir")));
    }

    @Test
    public void testisItMeSVNVersionedFileInsideVersionedDir() {
        Fngr fngr = new Fngr();
        assertTrue("Failed to recognize a versioned file inside a versioned directory in SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/versioned-dir/versioned-file-in-a-versioned-dir")));
    }

    @Test
    public void testisItMeSVNUnersionedFileInsideVersionedDir() {
        Fngr fngr = new Fngr();
        assertTrue("Failed to recognize an unversioned file inside a versioned directory in SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/unversioned-dir/versioned-file-in-a-versioned-dir")));
    }

    @Test
    public void testisItMeSVNUnversionedDir() {
        Fngr fngr = new Fngr();
        assertTrue("Failed to recognize an unversioned directory in SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/unversioned-dir")));
    }

    @Test
    public void testisItMeSVNNonExistentFileOrDir() {
        Fngr fngr = new Fngr();
        assertTrue("Failed to recognize a non existing file/directoryin SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/nonexistent")));
    }

    @Test
    public void testisItMeSVNUnversionedFileInsideUnversionedDir() {
        Fngr fngr = new Fngr();
        assertTrue("Failed to recognize an unversioned file inside an unversioned directory in SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/unversioned-dir/unversioned-file-in-unversioned-dir")));
    }

    @Test
    public void testisItMeNoSVNFile() {
        Fngr fngr = new Fngr();
        assertFalse("Failed to recognize an arbitrary file as not inside an SVN repository", vcs.isItMe(new File("/etc/fstab")));
    }

    @Test
    public void testisItMeNoSVNDir() {
        Fngr fngr = new Fngr();
        assertFalse("Failed to recognize an arbitrary directory as not inside an SVN repository", vcs.isItMe(new File("/etc")));
    }
    */
}