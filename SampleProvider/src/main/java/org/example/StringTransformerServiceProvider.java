package org.example;
import org.osgi.framework.BundleContext;
public interface StringTransformerServiceProvider {

    void destroy();

    void initialize(BundleContext bundleContext);
}
