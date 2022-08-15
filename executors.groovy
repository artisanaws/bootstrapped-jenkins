import jenkins.model.*
Jenkins.instance.setNumExecutors(2)
def location_config = JenkinsLocationConfiguration.get()
location_config.setAdminAddress("admin@example.com")