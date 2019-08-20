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
import org.apache.commons.io.FileUtils;
import java.nio.file.Path;
//import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import info.debatty.java.stringsimilarity.Levenshtein;

public class GitVCS extends VCS {
    private Repository repository;
    File workTree;
    Path filterPath;
    Levenshtein levenshtein = new Levenshtein();

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

        byte[] data = FileUtils.readFileToByteArray(f);
        String str = data.toString();
        ObjectInserter.Formatter f = new ObjectInserter.Formatter();
        ObjectId id = f.idFor(Constants.OBJ_BLOB, data);
        String hash = id.getName(); // same as 'git hash-object <file>'

        ObjectId headCommit = repository.resolve(Constants.HEAD);
        try (RevWalk revWalk = new RevWalk(repository)) {
            revWalk.markStart(revWalk.parseCommit(headCommit));
            is_modified = true;
            for (RevCommit commit : revWalk) {
                RevTree tree = commit.getTree();
                try (TreeWalk treeWalk = new TreeWalk(repository)) {
                    treeWalk.addTree(tree);
                    treeWalk.setRecursive(true);
                    treeWalk.setFilter(PathFilter.create(filterPath.toString()));
                    // Since we have an exact filter, a single treeWalk.next()
                    // *MUST* bring us to the file.
                    treeWalk.next();
                    is_versioned = true;
                    ObjectId objectId = treeWalk.getObjectId(0);
                    double d = levenshtein.distance(str, "My $tring");
                    String s = objectId.name();
                    if (s.equalsIgnoreCase(hash)) {
                        is_modified = false;
                        // save last (earliest) commit ID that still matches the file
                        revision = commit.name();
                    }
                }
            }
            revWalk.dispose();
            if (is_modified) {
                // if not modified, return hash-object result instead of commit ID
                revision = hash;
            }
        }
    }

    public String getFingerPrint() {
        String result = getVCSType() + ": " +  filename;
        if (!exists) {
            return result + " (no object, just a path)";
        }
        if (!is_versioned) {
            result += " (not versioned, git-hash: ";
        } else if (is_modified) {
            result += " (modified, git-hash: ";
        } else if (is_file) {
            result += " (commit: ";
        }
        return result + revision + " )";
    }
}