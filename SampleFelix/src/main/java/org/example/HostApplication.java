package org.example;


import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HostApplication
{
    private HostActivator activator = null;
    private Felix felix = null;

    public HostApplication()
    {
        // Create a configuration property map.
        Map config = new HashMap();
        // Create host activator;
        activator = new HostActivator();
        List list = new ArrayList();
        list.add(activator);
        config.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, list);

        try
        {
            // Now create an instance of the framework with
            // our configuration properties.
            felix = new Felix(config);
            // Now start Felix instance.
            felix.start();
        }
        catch (Exception ex)
        {
            System.err.println("Could not create framework: " + ex);
            ex.printStackTrace();
        }
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
        return activator.getContext();
    }
}