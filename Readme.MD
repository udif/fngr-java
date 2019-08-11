The Fngr library
================

This library is intended to help tool writer get a "fingerprint" of their input files.

When writing a tool that reads an input file "a" and generates an output file "b" (preferrably text),
it is desirable if the output file "b" can contain the input filename ("a" in this case) that was used to generate
this specific output file.  
Furthermore, if "a" is under version control, it would be even more desirable if we knew the exact revision this file was taken from.
On some revision control systems we have a notion of what revision file "a" was derived from, even if it was modified.

Goals (not all are implemented):
-----------------------------------
* Supports Git, SVN but also detects plain files (pull requests for other VCS are welcomed)
* Automatically detect what version control a file is under.
* Report back the exact revision of the file
* Detect if the file was modified. If on SVNm also report from what revision it originated, even if it was modified.

TODO
----
* Complete Git support
* Java, C++, Python versions (should will appear on separate repositories?)
* JavaDoc
* CI
* Report file fingerprint (md5sum, sha1, etc.)

Setup
-----
Before running the test, please run `./build_test_dir.sh` , which will create a local SVN repository and a working copy, complete with various combination of local and versioned files and directories, which are used by the tests.
