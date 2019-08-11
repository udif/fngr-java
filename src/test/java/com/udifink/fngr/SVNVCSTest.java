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

import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;

public class SVNVCSTest {
    @Test
    public void testisItMeSVNWorkDirRoot() {
        SVNVCS vcs = new SVNVCS();
        assertTrue("Failed to recognize workdir root as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir")));
    }

    @Test
    public void testisItMeSVNVersionedFile() {
        SVNVCS vcs = new SVNVCS();
        assertTrue("Failed to recognize versioned SVN file as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/versioned-file")));
    }

    @Test
    public void testisItMeSVNUnversionedFile() {
        SVNVCS vcs = new SVNVCS();
        assertTrue("Failed to recognize unversioned SVN file as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/unversioned-file")));
    }

    @Test
    public void testisItMeSVNVersionedDir() {
        SVNVCS vcs = new SVNVCS();
        assertTrue("Failed to recognize a versioned directory in SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/versioned-dir")));
    }

    @Test
    public void testisItMeSVNVersionedFileInsideVersionedDir() {
        SVNVCS vcs = new SVNVCS();
        assertTrue("Failed to recognize a versioned file inside a versioned directory in SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/versioned-dir/versioned-file-in-a-versioned-dir")));
    }

    @Test
    public void testisItMeSVNUnersionedFileInsideVersionedDir() {
        SVNVCS vcs = new SVNVCS();
        assertTrue("Failed to recognize an unversioned file inside a versioned directory in SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/versioned-dir/unversioned-file-in-a-versioned-dir")));
    }

    @Test
    public void testisItMeSVNUnversionedDir() {
        SVNVCS vcs = new SVNVCS();
        assertTrue("Failed to recognize an unversioned directory in SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/unversioned-dir")));
    }

    @Test
    public void testisItMeSVNNonExistentFileOrDir() {
        SVNVCS vcs = new SVNVCS();
        assertTrue("Failed to recognize a non existing file/directoryin SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/nonexistent")));
    }

    @Test
    public void testisItMeSVNUnversionedFileInsideUnversionedDir() {
        SVNVCS vcs = new SVNVCS();
        assertTrue("Failed to recognize an unversioned file inside an unversioned directory in SVN workdir as inside SVN repository",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/unversioned-dir/unversioned-file-in-unversioned-dir")));
    }

    @Test
    public void testisItMeNoSVNFile() {
        SVNVCS vcs = new SVNVCS();
        assertFalse("Failed to recognize an arbitrary file as not inside an SVN repository", vcs.isItMe(new File("/etc/fstab")));
    }

    @Test
    public void testisItMeNoSVNDir() {
        SVNVCS vcs = new SVNVCS();
        assertFalse("Failed to recognize an arbitrary directory as not inside an SVN repository", vcs.isItMe(new File("/etc")));
    }
}