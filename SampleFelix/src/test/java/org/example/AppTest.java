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
        embeddedOSGiContainer.initialise();
    }

    @Test
    public void testBundleClass() {
        MyStringTransformer stringTransformer = new MyStringTransformer();
        assertEquals(stringTransformer.transformString("Test"), "Test has been transformed");
    }

    @Test
    public void testBundles() throws InvalidSyntaxException, BundleException, InterruptedException {
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
