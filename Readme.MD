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
* report back the exact revision of the file
* can detect if the file was modified. On SVN can report from what revision it originated, even if it was modified.
* report file fingerprint (md5su, sha1, etc.)
* Java, C++, Python versions

