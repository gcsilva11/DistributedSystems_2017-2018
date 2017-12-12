#!/bin/sh
# - - - - - - - -
export TOMCAT_HOME=/usr/local/Cellar/tomcat/8.5.23/libexec
export WEBAPP_NAME=actions
# - - - - - - - -
mkdir -p target/WEB-INF/classes
cd src
javac -cp .:$TOMCAT_HOME/lib/*:../WebContent/WEB-INF/lib/* -d ../target/WEB-INF/classes primes/action/*.java
javac -cp .:$TOMCAT_HOME/lib/*:../WebContent/WEB-INF/lib/* -d ../target/WEB-INF/classes primes/model/*.java
cd ..
cp -r WebContent/* target
cp src/*.* target/WEB-INF/classes
cp -r src/primes target/WEB-INF/classes
cd target
jar cf ../$WEBAPP_NAME.war *
cd ..
# deploy the 'primes.war' archive into Tomcat's webapps:
# cp $WEBAPP_NAME.war $TOMCAT_HOME/webapps