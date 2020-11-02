package org.example;


public class MyStringTransformer implements StringTransformer {

    @Override
    public String transformString(String candidate) {
        return candidate + " has been transformed";
    }
}
