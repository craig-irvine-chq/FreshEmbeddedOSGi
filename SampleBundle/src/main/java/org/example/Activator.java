package org.example;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(StringTransformer.class.getName(), new MyStringTransformer(), null);
        System.out.println("--> DateService was registered as OSGi service.");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("--> DateService was unregistered as OSGi service.");
    }
}
