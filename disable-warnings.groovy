import jenkins.model.*;
import org.jenkinsci.plugins.envinject.*

EnvInjectPluginConfiguration envInject = GlobalConfiguration.all().get(EnvInjectPluginConfiguration.class)
envInject.setHideInjectedVars(true)

// update warnings
import jenkins.security.*

ExtensionList<UpdateSiteWarningsConfiguration> configurations = ExtensionList.lookup(UpdateSiteWarningsConfiguration.class);
println configurations

UpdateSiteWarningsConfiguration configuration = configurations.get(0);
HashSet<UpdateSite.Warning> activeWarnings = new HashSet<>();

activeWarnings.add('SECURITY-359')
activeWarnings.add('SECURITY-2760')

configuration.ignoredWarnings = activeWarnings

configuration.save()