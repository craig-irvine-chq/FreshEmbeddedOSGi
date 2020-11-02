package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.*;

import tutorial.couplerBase.service.CouplerService;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    HostApplication felixManager = null;


    @Before
    public void initialiseFelix() throws BundleException {
        felixManager = new HostApplication();
    }

    @Test
    public void testBundles() throws InvalidSyntaxException, BundleException, InterruptedException {
        //Bundle[] bundles = felixManager.getInstalledBundles();
        BundleContext bundleContext = felixManager.getBundleContext();
        String interfaceBundlePath = "file:/D:/Documents/tutorials/FreshEmbeddedOSGi/SampleInterface/target/SampleInterface-1.0-SNAPSHOT.jar";
        String implBundlePath = "file:/D:/Documents/tutorials/FreshEmbeddedOSGi/SampleBundle/target/SampleBundle-1.0-SNAPSHOT.jar";

        Bundle interfaceBundleInstalled = bundleContext.installBundle(interfaceBundlePath);
        Bundle implBundleInstalled = bundleContext.installBundle(implBundlePath);
        try {
            interfaceBundleInstalled.start();
        } catch ( BundleException e) {
            System.out.println("Missing bundle:" + interfaceBundlePath);
        }

        try {
            implBundleInstalled.start();
        } catch(BundleException e) {
            System.out.println("Missing bundle:" + implBundlePath);

        }
        Bundle[] bundles = felixManager.getInstalledBundles();
        Thread.sleep(1000);
        StringTransformer stringTransformerService =
                (StringTransformer)bundleContext.getServiceReference(StringTransformer.class.getName());

        assertEquals(stringTransformerService.transformString("test"),  "test has been transformed");
        assertTrue(true);
    }

    @Test
    public void testBasicBundles() throws InvalidSyntaxException, BundleException, InterruptedException {
        Bundle[] bundles = felixManager.getInstalledBundles();
        BundleContext bundleContext = felixManager.getBundleContext();
        String interfaceBundlePath = "file:/D:/Documents/tutorials/FreshEmbeddedOSGi/SampleFelix/src/main/java/resources/couplerBase.jar";
        String implBundlePath = "file:/D:/Documents/tutorials/FreshEmbeddedOSGi/SampleFelix/src/main/java/resources/coupler1.jar";

        Bundle interfaceBundleInstalled = bundleContext.installBundle(interfaceBundlePath);
        try {
            interfaceBundleInstalled.start();
        } catch ( BundleException e) {
            System.out.println("Missing bundle:" + interfaceBundlePath);
        }
        Thread.sleep(1000);

        Bundle implBundleInstalled = bundleContext.installBundle(implBundlePath);
        try {
            implBundleInstalled.start();
        } catch(BundleException e) {
            System.out.println("Missing bundle:" + implBundlePath);

        }

        Thread.sleep(1000);
        ServiceReference[] refs = bundleContext.getServiceReferences(CouplerService.class.getName(), "(Language=*)");
        assertTrue(refs!=null);
        if (refs!=null && refs.length!=0) {
            CouplerService couplerService = (CouplerService)refs[0];
            assertEquals(couplerService.translateWord("test"),  "test has been transformed");
        }
    }

    @After
    public void destroyFelix() throws BundleException, InterruptedException {
        felixManager.getFelix().stop();
        felixManager.getFelix().waitForStop(0);
    }
}
