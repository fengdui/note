#!/bin/sh

JAVA_OPT="${JAVA_OPT} -jar"
JAVA_OPT="${JAVA_OPT} -Xms3g -Xmx3g"
JAVA_OPT="${JAVA_OPT} -Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=XXX -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

export Java="$JAVA_HOME/bin/java"
$JAVA ${JAVA_OPT} $@