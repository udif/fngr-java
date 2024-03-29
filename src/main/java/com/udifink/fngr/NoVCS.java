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

public class NoVCS extends VCS {
    public VCSTypes getVCSType() {
        return VCSTypes.NONE;
    }

    public boolean isItMe(File f) {
        return true;
    }

    final protected void calcVcsFingerPrint() {

    }

    public String getFingerPrint() {
        return getFilename() + " (no version control)";
    }
}