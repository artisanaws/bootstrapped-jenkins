## Note that the base image is built using the jenkins/jenkins:lts-jdk11 nightly build. Previously, we built directly off of the latest lts-jdk11 build, but consistently ran into problems with features being deprecated and plugins left unsupported. So we built a static base image 
## Note this Dockerfile uses the install-plugins.sh script in the Docker image to add plugins. Jenkins LTS 2.346.x replaces that script with jenkins-plugin-cli. If you upgrade the base image to 2.346.x, you'll need to figure out how to use the jenkins-plugin-cli to add plugins.

from jenkins:baseimage

USER root

## Docker in Docker stuff
RUN apt-get update -qq && apt-get install -qqy apt-transport-https ca-certificates curl gnupg2 software-properties-common vim

RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -

RUN add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/debian \
   $(lsb_release -cs) \
   stable"

RUN apt-get update -qq && apt-get install -qqy docker-ce docker-ce-cli containerd.io

## Install other dependencies
RUN apt-get install -qqy sudo unzip git apt-utils

## Set user to jenkins and install Jenkins plugins
RUN usermod -aG docker jenkins
USER jenkins

## Some plugins were intermittently failing to download, so we're forcing CURL to use HTTP 1.1
ENV CURL_OPTIONS -sSfL --http1.1
ENV CURL_CONNECTION_TIMEOUT=60

## Install plugins
RUN /usr/local/bin/install-plugins.sh git matrix-auth workflow-aggregator docker-workflow blueocean credentials-binding aws-codecommit-trigger github amazon-ecr docker-build-publish deployit-plugin pipeline-stage-view workflow-api:1162.va_1e49062a_00e workflow-step-api workflow-cps-global-lib pipeline-groovy-lib:593.va_a_fc25d520e9 authorize-project

## Skip initial setup and do some other config stuff
USER root
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
COPY executors.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY default-user.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY disable-warnings.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY set-jenkins-url.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY config /etc/ssh/ssh_config

## Change default user creds -- note: buildtime_variable must be included as an argument in docker build command
ENV JENKINS_USER=admin
ARG buildtime_variable=default
ENV JENKINS_PASS=$buildtime_variable

## Install AWS CLI
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
RUN unzip awscliv2.zip
RUN ./aws/install

## Start Docker and Jenkins
ENTRYPOINT ["/bin/sh", "-c", "service docker start && /sbin/tini -- /usr/local/bin/jenkins.sh"]
