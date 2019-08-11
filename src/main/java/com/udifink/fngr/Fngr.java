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
import java.io.IOException;

class Fngr {
    //private VCSTypes vcstype;
    private VCS vcs;

    public VCS calcFingerPrint(String filename) throws IOException {
        File f = new File(filename).getCanonicalFile();
        String canonical_filename = f.getAbsolutePath();
        for (VCSTypes v : VCSTypes.values()) {
            vcs = VCSTypes.newVcs(v);
            vcs.filename = canonical_filename;
            vcs.in_local_vcs_dir = vcs.isItMe(f);           
            if (vcs.in_local_vcs_dir) {
                vcs.exists = f.isDirectory() || f.isFile();
                vcs.is_file = f.isFile();
                vcs.calcVcsFingerPrint();
                return vcs;
            }
        }
        assert false : "Should never happen";
        throw new IOException();
    }
}