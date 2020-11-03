package org.example;


import org.apache.felix.framework.Felix;
import org.example.interfaces.StringTransformer;
import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;

import java.util.*;

import static org.apache.felix.framework.util.FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP;
import static org.osgi.framework.Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA;

public class EmbeddedOSGiContainer
{
    private HostActivator activator;
    private Felix felix;
    private ServiceTracker<StringTransformer, StringTransformer> stringServiceTracker;

    public EmbeddedOSGiContainer() {

    }

    public void initialise() {
        activator = new HostActivator();
        Map<String, Object> config = new HashMap<>();
        config.put(FRAMEWORK_SYSTEMPACKAGES_EXTRA, "org.example.interfaces");
        this.configHostActivator(config);

        try
        {
            felix = new Felix(config);
            felix.start();
        }
        catch (Exception ex)
        {
            System.err.println("Could not create framework: " + ex);
            ex.printStackTrace();
        }

        stringServiceTracker = new ServiceTracker<>(this.getBundleContext(), StringTransformer.class.getName(), null);
        stringServiceTracker.open();
    }

    public StringTransformer[] getStringTransformerServices() {
        return this.stringServiceTracker.getServices(new StringTransformer[0]);
    }

    public void shutdownApplication() throws BundleException, InterruptedException {
        stringServiceTracker.close();
        felix.stop();
        felix.waitForStop(0);
    }

    public BundleContext getBundleContext() {
        return activator.getBundleContext();
    }

    protected void configHostActivator(Map<String, Object> config) {
        List<BundleActivator> activators = new ArrayList<>();
        activators.add(this.activator);
        config.put(SYSTEMBUNDLE_ACTIVATORS_PROP, activators);
    }

    class HostActivator implements BundleActivator
    {
        private BundleContext m_context = null;

        public void start(BundleContext context)
        {
            m_context = context;
        }

        public void stop(BundleContext context)
        {
            m_context = null;
        }

        public BundleContext getBundleContext()
        {
            return m_context;
        }
    }
}