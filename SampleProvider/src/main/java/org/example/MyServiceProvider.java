package org.example;
import org.osgi.framework.BundleContext;
public interface MyServiceProvider {

    void destroy();

    void initialize(BundleContext bundleContext);
}
