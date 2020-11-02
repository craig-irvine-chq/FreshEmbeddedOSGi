package org.example;


import org.apache.felix.framework.Felix;
import org.osgi.framework.*;

import java.util.*;

import static org.apache.felix.framework.util.FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP;
import static org.osgi.framework.Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA;

public class EmbeddedOSGiContainer
{
    private static final String SYSTEM_PACKAGE_SEPARATOR = ",";

    private HostActivator activator;
    private Felix felix;
    private Collection<MyServiceProvider> embeddedServiceProviders;
    private final StringBuilder systemPackages = new StringBuilder();
    private ContainerConfiguration containerConfiguration = new ContainerConfiguration();

    public EmbeddedOSGiContainer() {

    }

    public void initialise() {

        // Create host activator;
        activator = new HostActivator();

        try
        {
            // Now create an instance of the framework with
            // our configuration properties.
            felix = new Felix(createFelixContainerConfiguration());
            // Now start Felix instance.
            felix.start();
        }
        catch (Exception ex)
        {
            System.err.println("Could not create framework: " + ex);
            ex.printStackTrace();
        }

        initialiseServiceProviders();
    }

    public void addSystemPackage(String additionalPackage) {

        if (this.systemPackages.length() > 0) {
            this.systemPackages.append(SYSTEM_PACKAGE_SEPARATOR);
        }

        this.systemPackages.append(additionalPackage);
    }

    public Bundle[] getInstalledBundles()
    {
        // Use the system bundle activator to gain external
        // access to the set of installed bundles.
        return activator.getBundles();
    }

    public void shutdownApplication() throws BundleException, InterruptedException {
        // Shut down the felix framework when stopping the
        // host application.
        felix.stop();
        felix.waitForStop(0);
    }

    public Felix getFelix () {
        return felix;
    }

    public BundleContext getBundleContext() {
        return activator.getBundleContext();
    }

    public void setEmbeddedOSGiServiceProviders(Collection<MyServiceProvider> providers) {
        this.embeddedServiceProviders = providers;
    }
    private Map<String, Object> createFelixContainerConfiguration() {
        Map<String, Object> config = new HashMap<>();

        this.configHostActivator(config);
        this.configSystemExtraClasspath(config);
        this.configBundles(config);

        return config;
    }

    protected void configHostActivator(Map<String, Object> config) {
        List<BundleActivator> activators = new ArrayList<>();
        activators.add(this.activator);
        config.put(SYSTEMBUNDLE_ACTIVATORS_PROP, activators);
    }

    protected void configSystemExtraClasspath(Map<String, Object> config) {
        config.put(FRAMEWORK_SYSTEMPACKAGES_EXTRA, this.systemPackages.toString());
    }

    private void configBundles(Map<String, Object> config) {
        this.containerConfiguration.apply(config);
    }


    private void initialiseServiceProviders() {
        for (MyServiceProvider serviceProvider : this.embeddedServiceProviders) {
            serviceProvider.initialize(this.activator.getBundleContext());
        }
    }
}