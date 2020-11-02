package org.example;

import org.example.interfaces.StringTransformer;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;


public class StringTransformerServiceProvider implements MyServiceProvider {

    private ServiceTracker<StringTransformer, StringTransformer> stringServiceTracker;


    @Override
    public void destroy() {
        this.stringServiceTracker.close();
    }

    public StringTransformer[] getStringTransformerServices() {
        return this.stringServiceTracker.getServices(new StringTransformer[0]);
    }


    @Override
    public void initialize(BundleContext bundleContext) {
        this.stringServiceTracker = new ServiceTracker<StringTransformer, StringTransformer>(bundleContext, StringTransformer.class.getName(), null);
        this.stringServiceTracker.open();
    }

}
