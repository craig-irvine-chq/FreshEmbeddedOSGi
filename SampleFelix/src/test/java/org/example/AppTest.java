package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.example.interfaces.StringTransformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.*;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Unit test for simple App.
 */
public class AppTest
{
    EmbeddedOSGiContainer embeddedOSGiContainer = null;
    StringTransformerServiceProvider serviceProvider;

    @Before
    public void initialiseFelix() throws BundleException {
        embeddedOSGiContainer = new EmbeddedOSGiContainer();
        embeddedOSGiContainer.setEmbeddedOSGiServiceProviders(initializeProviders());
        embeddedOSGiContainer.addSystemPackage("org.example.interfaces");
        embeddedOSGiContainer.initialise();
    }

    @Test
    public void testBundleClass() {
        MyStringTransformer stringTransformer = new MyStringTransformer();
        assertEquals("Test has been transformed", stringTransformer.transformString("Test"));
    }

    @Test
    public void testBundles() throws InvalidSyntaxException, BundleException, InterruptedException {

        StringTransformer[] stringTransformers;
        Bundle[] bundles = embeddedOSGiContainer.getInstalledBundles();
        BundleContext bundleContext = embeddedOSGiContainer.getBundleContext();
        String implBundlePath = "file:/D:/Documents/tutorials/FreshEmbeddedOSGi/SampleBundle/target/SampleBundle-1.0-SNAPSHOT.jar";

        Bundle implBundleInstalled = bundleContext.installBundle(implBundlePath);

        try {
            implBundleInstalled.start();
        } catch(BundleException e) {
            System.out.println("Missing bundle:" + implBundlePath);

        }

        stringTransformers = this.serviceProvider.getStringTransformerServices();
        assertEquals(stringTransformers[0].transformString("test"),  "test has been transformed");

    }

    @After
    public void destroyFelix() throws BundleException, InterruptedException {
        embeddedOSGiContainer.getFelix().stop();
        embeddedOSGiContainer.getFelix().waitForStop(0);
    }


    private Collection<MyServiceProvider> initializeProviders() {
        Collection<MyServiceProvider> providers = new ArrayList<>();
        this.serviceProvider = new StringTransformerServiceProvider();
        providers.add(this.serviceProvider);
        return providers;
    }
}
