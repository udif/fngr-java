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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//
// Thin abstract class from which concrete classes for each VCS are derived
//
public abstract class VCS {
    private String filename;
    private String revision;
    private boolean in_local_vcs_dir; // true if file location is within a local repository
    private boolean versioned; // true if file is tracked by VCS (in_local_vcs_dir must be true)
    private boolean modified; // true if local file was modified compared to VCS copy (versioned must be true)

    private Logger logger = LoggerFactory.getLogger(VCS.class);

    // return true if the given filename is really part of this VCS type
    // (to be implemented by specific derived classes for each VCS type)
    // Also, if true, set the private fields above
    public abstract boolean isItMe(File f);

    public boolean getModified() {
        return modified;
    }

    public String getRevision() {
        return revision;
    }

    public String getFilename() {
        return filename;
    }

    public abstract VCSTypes getVCSType();

    public String getFingerPrint() {
        return getVCSType() + ": " + getFilename() + "@" + getRevision() + (getModified() ? " (modified)" : "");
    }
}