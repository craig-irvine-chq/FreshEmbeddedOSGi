package org.example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    @Test
    public void testBundleClass() {
        MyStringTransformer stringTransformer = new MyStringTransformer();
        assertEquals(stringTransformer.transformString("Test"), "Test has been transformed");
    }
}
