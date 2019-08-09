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

//
// This is a generic interface for any version control system (VCS) we might support
//
package com.udifink.fngr;

import java.io.File;
import java.io.IOException;
//import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.lib.Repository;
//import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitVCS extends VCS {

    public VCSTypes getVCSType() {
        return VCSTypes.GIT;
    }

    public boolean isItMe(File f) {
        FileRepositoryBuilder builder = new FileRepositoryBuilder().findGitDir(f.getAbsoluteFile().getParentFile());
        if (builder.getGitDir() == null)
            return false;
        try {
            Repository repository = builder.setMustExist(true).build();
        } catch (IOException e) {

        }
        return true;
    }

    final protected void calcVcsFingerPrint() {

    }

}