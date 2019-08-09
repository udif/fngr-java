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
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNStatusClient;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.SVNException;

public class SVNVCS extends VCS {

    public VCSTypes getVCSType() {
        return VCSTypes.GIT;
    }

    public boolean isItMe(File f) {
        try {
            return SVNClientManager.newInstance().getStatusClient().doStatus(f, false).isVersioned();
        } catch (SVNException e) {
            return false;
        }
        //public static long getRevisionNumber(String localPath) throws SVNException {
        //    final SVNStatus status = SVNClientManager.newInstance().getStatusClient().doStatus(new File(localPath), false);
        //    return status != null ? status.getRevision().getNumber() : -1;
        //}
        //return false; // TODO fix this when we actually implement SVN
    }
}