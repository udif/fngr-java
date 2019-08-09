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
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SVNVCSTest {
    @Test
    public void testisItMeSVNVersionedFile() {
        SVNVCS vcs = new SVNVCS();
        assertTrue("Failed to recognized versioned SVN file as under SVN",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/versioned-file")));
    }

    @Test
    public void testisItMeSVNLocalFile() {
        SVNVCS vcs = new SVNVCS();
        assertTrue("Failed to recognized versioned SVN file as under SVN",
            vcs.isItMe(new File("../fngr-testdir/test-svn-workdir/unversioned-file")));
    }

    @Test
    public void testisItMeNoSVNFile() {
        SVNVCS vcs = new SVNVCS();
        assertFalse("Failed!", vcs.isItMe(new File("/etc/fstab")));
    }
}