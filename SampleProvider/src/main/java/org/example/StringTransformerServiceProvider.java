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




    @Override
    public void initialize(BundleContext bundleContext) {
        this.stringServiceTracker = new ServiceTracker<StringTransformer, StringTransformer>(bundleContext, StringTransformer.class.getName(), null);
        this.stringServiceTracker.open();
    }

}
