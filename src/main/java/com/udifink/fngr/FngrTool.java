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
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Parameters;

//@Command(name = "fngrtool", mixinStandardHelpOptions = true, version = {FngrTool.VERSION},
@Command(name = "fngrtool", mixinStandardHelpOptions = true, versionProvider = FngrTool.StaticVersionProvider.class,
         description = "Prints a file header fingerprint for the given input file")
class FngrTool implements Callable<Integer> {

    @Parameters(index = "0", description = "The file whose signature we want to calculate.")
    private File file;

    //@Option(names = {"-a", "--algorithm"}, description = "MD5, SHA-1, SHA-256, ...")
    //private String algorithm = "MD5";

    public Integer call() throws Exception { 
        //if (args.length != 1) {
        //    System.out.println("Usage: " + FngrTool.class.getName() + " <file>");
        //    System.exit(1);
        //}

        Fngr fngr = new Fngr();
        VCS vcs = fngr.calcFingerPrint(file.toString());
        System.out.println(vcs.getFingerPrint());

        return 0;
    }

    public static void main(String... args) throws IOException {
        int exitCode = new CommandLine(new FngrTool()).execute(args);
        System.exit(exitCode);
    }
    static class StaticVersionProvider implements IVersionProvider {
        static final String V = FngrTool.class.getPackage().getImplementationVersion();
        static final String VERSION = "FngrTool-" + ((V == null) ? "SNAPSHOT" : V);
    
        public String[] getVersion() {
            return new String[] { VERSION };
        }
    }
}