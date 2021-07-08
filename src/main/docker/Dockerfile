FROM jenkins/jenkins:lts
LABEL maintainer="wtedongmo@yahoo.fr"
USER root
RUN mkdir /var/log/jenkins
RUN chown -R  jenkins:jenkins /var/log/jenkins
USER jenkins
ENV JAVA_OPTS="-Xmx2048m"
ENV JENKINS_OPTS="--handlerCountMax=300 --logfile=/var/log/jenkins/jenkins.log"
