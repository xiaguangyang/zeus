#!/bin/sh

BASEDIR=`dirname $0`/..
BASEDIR=`(cd "$BASEDIR"; pwd)`

# If a specific java binary isn't specified search for the standard 'java' binary
if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD=`which java`
  fi
fi

CLASSPATH="$BASEDIR"/conf/:"$BASEDIR"/lib/cap-jdos-${project.version}.jar:"$BASEDIR"/lib/cap-deploy-ssh-${project.version}.jar:"$BASEDIR"/lib/*
LOGDIR="$BASEDIR/log/"

echo "$CLASSPATH"

if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not defined correctly."
  echo "  We cannot execute $JAVACMD"
  exit 1
fi

if [ -z "$OPTS_MEMORY" ] ; then
    OPTS_MEMORY="-Xms2G -Xmx6G -server -XX:MaxPermSize=256M -Xss256K"
fi
DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5066"
nohup "$JAVACMD" $JAVA_OPTS \
  $OPTS_MEMORY \
  $DEBUG_OPTS \
  -classpath "$CLASSPATH" \
  -Dbasedir="$BASEDIR" \
  -Dfile.encoding="UTF-8" \
  com.jd.cap.service.Launcher
 "$@" >/dev/null 2>/dev/null &
