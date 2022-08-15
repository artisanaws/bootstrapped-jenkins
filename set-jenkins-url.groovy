#!groovy
//This script should set the default URL for Jenkins. Use the following command to update the URL to match
//the EC2 instance public IP: sed --expression "s@replaceme@$JENKINS_IP@" set-jenkins-url.groovy

// imports
import jenkins.model.Jenkins
import jenkins.model.JenkinsLocationConfiguration

// parameters
def jenkinsParameters = [
  email:  'Jenkins Admin <admin@example.com>',
  url:    'replaceme:8083/'
]

// get Jenkins location configuration
def jenkinsLocationConfiguration = JenkinsLocationConfiguration.get()

// set Jenkins URL
jenkinsLocationConfiguration.setUrl(jenkinsParameters.url)

// set Jenkins admin email address
jenkinsLocationConfiguration.setAdminAddress(jenkinsParameters.email)

// save current Jenkins state to disk
jenkinsLocationConfiguration.save()