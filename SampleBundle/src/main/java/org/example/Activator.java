package org.example;

import org.example.interfaces.StringTransformer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(StringTransformer.class.getName(), new MyStringTransformer(), null);
        System.out.println("--> StringTransformer was registered as OSGi service.");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("--> StringTransformer was unregistered as OSGi service.");
    }
}
