package org.example;

public class SimpleSingleTonClass {
    private static SimpleSingleTonClass simpleSingleTonClassInstance =null;

    private SimpleSingleTonClass(){
        System.out.println("Constructor of SingleTon class called");
    }

    public static SimpleSingleTonClass getSingletonInstance() {
        if(simpleSingleTonClassInstance ==null) simpleSingleTonClassInstance =new SimpleSingleTonClass();
        return simpleSingleTonClassInstance;
    }
}
