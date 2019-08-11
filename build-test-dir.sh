#/bin/bash
command -v svn >/dev/null 2>&1 || { echo >&2 "SVN required, but not installed."; exit 1; }

SVN_ROOT=../fngr-testdir
SVN_ROOT=`readlink -f $SVN_ROOT`
[ ! -d $SVN_ROOT ] && mkdir -p $SVN_ROOT
rm -rf $SVN_ROOT/test-svn
rm -rf $SVN_ROOT/test-svn-workdir
svnadmin create $SVN_ROOT/test-svn/
svn checkout file://$SVN_ROOT/test-svn/ $SVN_ROOT/test-svn-workdir/
pushd $SVN_ROOT/test-svn-workdir

echo "versioned-file, 1st revision" >versioned-file
svn add versioned-file 
svn commit -m "1st commit of versioned-file" versioned-file 

echo "another versioned file" >versioned-file-as-well
svn add versioned-file-as-well 
svn commit -m "committed another file" versioned-file-as-well 

echo "versioned-file, 2nd revision" >versioned-file
svn commit -m "another commit of versioned-file" versioned-file 
svn update -r 1 versioned-file

echo "unversioned-file" >unversioned-file 

svn mkdir versioned-dir
svn commit -m "Added a versioned dir" versioned-dir

echo "A versioned file in a versioned dir" >versioned-dir/versioned-file-in-a-versioned-dir
svn add versioned-dir/versioned-file-in-a-versioned-dir
svn commit -m "Added a versioned file in a versioned dir" versioned-dir/versioned-file-in-a-versioned-dir
echo "An unversioned file in a versioned dir" >versioned-dir/unversioned-file-in-a-versioned-dir

mkdir unversioned-dir
echo "An unversioned file in an unversioned dir" >unversioned-dir/unversioned-file-in-an-unversioned-dir
popd
