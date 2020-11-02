/*package org.example;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.FrameworkFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class NewHostApplication {



    public NewHostApplication(){
    }

    public Framework getFramework () throws BundleException, URISyntaxException {
        return initialize();
    }

    private Framework initialize() throws BundleException, URISyntaxException {
        Map<String, String> map = new HashMap<String, String>();

        // make sure the cache is cleaned
        map.put(Constants.FRAMEWORK_STORAGE_CLEAN, Constants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT);

        // more properties available at: http://felix.apache.org/documentation/subprojects/apache-felix-service-component-runtime.html
        map.put("ds.showtrace", "true");
        map.put("ds.showerrors", "true");

        System.out.println("Building OSGi Framework");
        FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
        Framework framework = frameworkFactory.newFramework(map);

        System.out.println("Starting OSGi Framework");
        framework.start();

        // declarative services dependency is necessary, otherwise they won't be picked up!
        loadScrBundle(framework);

        framework.getBundleContext().installBundle("file:/path/to/myBundle.jar").start();

        ServiceReference reference = framework.getBundleContext().getServiceReference("my.Interface");
        System.out.println(framework.getBundleContext().getService(reference));

        for (Bundle bundle : framework.getBundleContext().getBundles()) {
            System.out.println("Bundle: " + bundle.getSymbolicName());
            if (bundle.getRegisteredServices() != null) {
                for (ServiceReference serviceReference : bundle.getRegisteredServices())
                    System.out.println("\tRegistered service: " + serviceReference);
            }
        }

        return framework;
    }

    private void loadScrBundle(Framework framework) throws URISyntaxException, BundleException {
        URL url = getClass().getClassLoader().getResource("org/apache/felix/scr/ScrService.class");
        if (url == null)
            throw new RuntimeException("Could not find the class org.apache.felix.scr.ScrService");
        String jarPath = url.toURI().getSchemeSpecificPart().replaceAll("!.*", "");
        System.out.println("Found declarative services implementation: " + jarPath);
        framework.getBundleContext().installBundle(jarPath).start();
    }
}
*/