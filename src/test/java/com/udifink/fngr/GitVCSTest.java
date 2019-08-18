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

public class GitVCSTest {
    @Test
    public void testisItMeGitFile() {
        GitVCS vcs = new GitVCS();
        assertTrue("Failed!", vcs.isItMe(new File("build.gradle")));
    }

    @Test
    public void testisItMeGitDir() {
        GitVCS vcs = new GitVCS();
        assertTrue("Failed!", vcs.isItMe(new File("src/main/java")));
    }

    @Test
    public void testisItMeGitFileInGitDir() {
        GitVCS vcs = new GitVCS();
        assertTrue("Failed!", vcs.isItMe(new File("src/main/java/com/udifink/fngr/Fngr.java")));
    }

    @Test
    public void testisItMeNoGitFileInNoGitDir() {
        GitVCS vcs = new GitVCS();
        assertTrue("Failed!", vcs.isItMe(new File("bin/test/com/udifink/fngr/GitVCSTest.class")));
    }

    @Test
    public void testisItMeGitNoDir() {
        GitVCS vcs = new GitVCS();
        assertTrue("Failed!", vcs.isItMe(new File("bin")));
    }

    @Test
    public void testisItMeGitNoFile() {
        GitVCS vcs = new GitVCS();
        assertTrue("Failed!", vcs.isItMe(new File("xxxxxx.xxxxx")));
    }

    @Test
    public void testisItMeNoGitFile() {
        GitVCS vcs = new GitVCS();
        assertFalse("Failed!", vcs.isItMe(new File("/etc/fstab")));
    }

    @Test
    public void testisItMeNoFile() {
        GitVCS vcs = new GitVCS();
        assertFalse("Failed!", vcs.isItMe(new File("/xxxxxx")));
    }
}