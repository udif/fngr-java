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

public enum VCSTypes {
    GIT, SVN, NONE;

    public static VCSTypes getEnum(String s) {
        for (VCSTypes v : values()) {
            if (v.name().equalsIgnoreCase(s)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Illegal VCS name");
    }

    public static VCS getVcs(VCSTypes vcstype) {
        switch (vcstype) {
            case GIT:
                return new Git();
        
            case SVN:
                return new SVN();

            case NONE:
                return new NoVCS();
        }
    }
}