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
import java.nio.file.Path;

//import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
//import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevWalk;

public class GitVCS extends VCS {
    private Repository repository;
    File workTree;
    Path filterPath;

    public VCSTypes getVCSType() {
        return VCSTypes.GIT;
    }

    public boolean isItMe(File f) {
        try {
            FileRepositoryBuilder builder = new FileRepositoryBuilder().findGitDir(f.getCanonicalFile().getParentFile());
            if (builder.getGitDir() == null)
                return false;
            repository = builder.setMustExist(true).build();
            workTree = builder.getWorkTree();
            filterPath = workTree.toPath().relativize(f.getCanonicalFile().toPath());
        } catch (IOException e) {

        }
        return true;
    }

    //
    // The following code was highly inspired by:
    // https://github.com/centic9/jgit-cookbook/blob/276ad0fecb4f1c616ef459ed8b7feb6d503724eb/src/main/java/org/dstadler/jgit/api/ReadFileFromCommit.java
    //
    final protected void calcVcsFingerPrint() throws IOException {
        ObjectId headCommit = repository.resolve(Constants.HEAD);
        try (RevWalk revWalk = new RevWalk(repository)) {
            RevCommit commit = revWalk.parseCommit(headCommit);
            RevTree tree = commit.getTree();
            try (TreeWalk treeWalk = new TreeWalk(repository)) {
                treeWalk.addTree(tree);
                treeWalk.setRecursive(true);
                treeWalk.setFilter(PathFilter.create(filterPath.toString()));
                if (!treeWalk.next()) {
                    throw new IllegalStateException("Did not find expected file '" + filename + "'");
                }
                ObjectId objectId = treeWalk.getObjectId(0);
                ObjectLoader loader = repository.open(objectId);

                // and then one can the loader to read the file
                loader.copyTo(System.out);

            }
        }
    }

    public String getFingerPrint() {
        return getVCSType() + ": " + getFilename() + "(commit: " + getRevision() + ")" + (getModified() ? " (modified)" : "");
    }
}