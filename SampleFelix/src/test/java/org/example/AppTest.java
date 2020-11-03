package org.example;

import static org.junit.Assert.assertEquals;

import org.example.interfaces.StringTransformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.*;


/**
 * Unit test for simple App.
 */
public class AppTest
{
    EmbeddedOSGiContainer embeddedOSGiContainer = null;

    @Before
    public void initialiseFelix() {
        embeddedOSGiContainer = new EmbeddedOSGiContainer();
        embeddedOSGiContainer.initialise();
    }


    @Test
    public void testBundles() throws BundleException {
        String implBundlePath = "file:/D:/Documents/tutorials/FreshEmbeddedOSGi/SampleBundle/target/SampleBundle-1.0-SNAPSHOT.jar";
        Bundle implBundleInstalled = embeddedOSGiContainer.getBundleContext().installBundle(implBundlePath);
        try {
            implBundleInstalled.start();
        } catch(BundleException e) {
            System.out.println("Missing bundle:" + implBundlePath);

        }
        StringTransformer[] stringTransformers = this.embeddedOSGiContainer.getStringTransformerServices();
        assertEquals(stringTransformers[0].transformString("test"),  "test has been transformed");
    }

    @After
    public void destroyFelix() throws BundleException, InterruptedException {
        embeddedOSGiContainer.shutdownApplication();
    }

}
