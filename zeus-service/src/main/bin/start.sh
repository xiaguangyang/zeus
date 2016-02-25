#!/bin/sh
BASEDIR=`dirname $0`/..
BASEDIR=`(cd "$BASEDIR"; pwd)`
ROOTDIR=`basename $BASEDIR`

PIDPROC=`ps -ef | grep "$ROOTDIR/conf" | grep -v 'grep'| awk '{print $2}'`
if [ -z "$PIDPROC" ];then
 cd $BASEDIR/bin
 sh launch.sh &
 PIDPROC=`ps -ef | grep "$ROOTDIR/conf" | grep -v 'grep'| awk '{print $2}'`
 echo $PIDPROC
else
 echo "$ROOTDIR(pid:$PIDPROC) is running"
fi