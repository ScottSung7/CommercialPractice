#!/bin/bash

JAR_NAME="invite.jar"
echo "> build 파일명: $JAR_NAME" >> /home/ec2-user/deploy.log

echo "> build 파일 복사" >> /home/ec2-user/deploy.log
DEPLOY_PATH=/home/ec2-user/
cp /home/ec2-user/build/invite.jar $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ec2-user/deploy.log
CURRENT_PID=${pgrep -f $JAR_NAME}

JAVA_OPTS=""

if[ -z $CURRENT_PID]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/deploy.log
else
  echo "> kill -9 $CURRENT_PID"
  kill -9 $CURRENT_PID
  JAVA_OPTS="-Dspring.jpa.hibernate.ddl-auto=none"
  sleep 5
fi

BUILD_JAR=$DEPLOY_PATH/$JAR_NAME
echo "> BUILD_JAR 배포" >> /home/ec2-user/deploy.log
nohup java $JAVA_OPTS -jar $BUILD_JAR >> /home/ec2-user/deploy.log 2> /home/ec2-user/deploy_err.log &

