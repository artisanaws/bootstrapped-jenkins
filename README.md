# bootstrapped-jenkins

The files in this repository build a Jenkins server and disable the standard Startup Wizard. The server is also bootstrap with a variety of common plugins. Note: this dockerfile builds off of a base image that includes the /usr/local/bin/install-plugins.sh. This script has since been replaced with the jenkins-cli.

To build and run this dockerfile, use the following commands:

#start by obtaining a password for the jenkins user from AWS Secrets Manager
export JENKINS_PASS=$(aws secretsmanager get-secret-value --secret-id JenkinsPassword --query SecretString --output text | jq --raw-output .password) 
# build the containere and pass the jenkins password as an argument
docker build . --tag jenkins-docker --build-arg buildtime_variable=$JENKINS_PASS
save the image ID to a shell variable
export IMAGEID=$(docker images -q jenkins-docker)
# run the container and map the default Jenkins port (8080) to 80
docker run --rm -d -v /var/jenkins_home:/var/jenkins_home -p 80:8080 -p 50000:50000 --privileged $IMAGEID
