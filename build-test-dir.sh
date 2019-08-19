#/bin/bash
command -v svn >/dev/null 2>&1 || { echo >&2 "SVN required, but not installed."; exit 1; }
command -v git >/dev/null 2>&1 || { echo >&2 "Git required, but not installed."; exit 1; }

FNGR_TEST_ROOT=../fngr-testdir
FNGR_TEST_ROOT=`readlink -f $FNGR_TEST_ROOT`
[ ! -d $FNGR_TEST_ROOT ] && mkdir -p $FNGR_TEST_ROOT

#
# SVN
#
rm -rf $FNGR_TEST_ROOT/test-svn
rm -rf $FNGR_TEST_ROOT/test-svn-workdir
svnadmin create $FNGR_TEST_ROOT/test-svn/
svn checkout file://$FNGR_TEST_ROOT/test-svn/ $FNGR_TEST_ROOT/test-svn-workdir/
pushd $FNGR_TEST_ROOT/test-svn-workdir

echo "non-head-versioned-file, 1st revision" >non-head-versioned-file
svn add non-head-versioned-file
svn commit -m "1st commit of versioned-file" non-head-versioned-file

echo "another versioned file" >head-versioned-file
svn add head-versioned-file
svn commit -m "committed another file" head-versioned-file

echo "modified-non-head-versioned-file, 1st revision" >modified-non-head-versioned-file
svn add modified-non-head-versioned-file
svn commit -m "1st commit of modified-non-head-versioned-file" modified-non-head-versioned-file

echo "another versioned file" >modified-head-versioned-file
svn add modified-head-versioned-file
svn commit -m "committed another file" modified-head-versioned-file

echo "non-head-versioned-file, 2nd revision" >non-head-versioned-file
svn commit -m "another commit of non-head-versioned-file" non-head-versioned-file
svn update -r 1 non-head-versioned-file

echo "modified-non-head-versioned-file, 2nd revision" >modified-non-head-versioned-file
svn commit -m "another commit of modified-non-head-versioned-file" modified-non-head-versioned-file
svn update -r 3 modified-non-head-versioned-file

echo "Now lets modify it" >>modified-non-head-versioned-file

echo "Now lets modify it" >>modified-head-versioned-file

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

#
# Git
#

rm -rf $FNGR_TEST_ROOT/test-git $FNGR_TEST_ROOT/test-git-data
mkdir  $FNGR_TEST_ROOT/test-git $FNGR_TEST_ROOT/test-git-data $FNGR_TEST_ROOT/test-git-data/versioned-dir
pushd $FNGR_TEST_ROOT/test-git

git config --get user.name
if [ $? -eq 1 ]
then
  git config user.email testuser@testuser.command
  git config user.name  "Test username"
fi


git init

echo "another versioned file" >head-versioned-file
git add -A
git commit -m "1st commit of 'head-versioned-file'"
git rev-parse HEAD >../test-git-data/head-versioned-file

echo "another versioned file" >modified-head-versioned-file
git add -A
git commit -m "1st commit of 'modified-head-versioned-file'"

echo "non-head-versioned-file, 1st revision" >non-head-versioned-file
git add -A
git commit -m "1st commit of 'non-head-versioned-file'"
git rev-parse HEAD >../test-git-data/non-head-versioned-file

echo "non-head-versioned-file, 2nd revision" >non-head-versioned-file
git add -u
git commit -m "2nd commit of 'non-head-versioned-file'"

mkdir versioned-dir
echo "A versioned file in a directory" >versioned-dir/versioned-file-in-a-versioned-dir
git add versioned-dir/versioned-file-in-a-versioned-dir
git commit -m "Added a versioned file in a versioned dir"
git rev-parse HEAD >../test-git-data/versioned-dir/versioned-file-in-a-versioned-dir

mkdir unversioned-dir
echo "An unversioned file in an unversioned dir" >unversioned-dir/unversioned-file-in-an-unversioned-dir

echo "modified-non-head-versioned-file, 1st revision" >modified-non-head-versioned-file
git add modified-non-head-versioned-file
git commit -m "1st commit of 'modified-non-head-versioned-file'"

echo "modified-non-head-versioned-file, 2nd revision" >modified-non-head-versioned-file
git add modified-non-head-versioned-file
git commit -m "2nd commit of 'modified-non-head-versioned-file'"
git checkout HEAD^ modified-non-head-versioned-file

echo "Now lets modify it" >>modified-non-head-versioned-file
git hash-object modified-non-head-versioned-file >../test-git-data/modified-non-head-versioned-file

echo "Now lets modify it" >>modified-head-versioned-file
git hash-object modified-head-versioned-file >../test-git-data/modified-head-versioned-file

echo "unversioned-file" >unversioned-file
git hash-object unversioned-file >../test-git-data/unversioned-file

echo "An unversioned file in a versioned dir" >versioned-dir/unversioned-file-in-a-versioned-dir
git hash-object unversioned-file >../test-git-data/versioned-dir/unversioned-file-in-a-versioned-dir

git checkout `cat ../test-git-data/non-head-versioned-file` non-head-versioned-file

popd
