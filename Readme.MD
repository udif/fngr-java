The Fngr library
================

[![CircleCI](https://circleci.com/gh/udif/fngr-java/tree/master.svg?style=svg&circle-token=61071651b2a87152a1b09a77e823a15951d6960e)](https://circleci.com/gh/udif/fngr-java/tree/master)


This library is intended to help tool writer get a "fingerprint" of their input files. e.g. :

```
$ foo a.in a.out
$ cat a.out
//
// Warning! This is a machine generated file! Do not edit!
// Generated from:
// SVN: /home/foo/a.in@1374 (modified)
//
...
```

Features:
---------
* SVN Support
* Git support (work in progress)
* Plain Files support (but detect if they are within a Version controlled tree)
* Automatically detect what version control a file is under.
* Report back the exact revision of the file
* Detect if the file was modified. If on SVN, also report from what revision it originated, even if it was modified.

TODO
----
* Complete Git support
* Java, C++, Python versions (should will appear on separate repositories?)
* JavaDoc
* Report file fingerprint (md5sum, sha1, etc.)
* Decide on sane behavior in case of nested Version Control systems

Building:
---------
```
$ chmod u+x ./build-test-dir.sh
$ ./build-test-dir.sh
...
$ gradle build

```

`./build_test_dir.sh`  is used create a local SVN repository and a working copy, complete with various combination of local and versioned files and directories, which are used by some of the unit tests.
