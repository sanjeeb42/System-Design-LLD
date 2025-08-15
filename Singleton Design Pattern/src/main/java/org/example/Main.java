package org.example;

public class Main {
    public static void main(String[] args){
        SimpleSingleTonClass simpleSingleTonClass = SimpleSingleTonClass.getSingletonInstance();
        SimpleSingleTonClass simpleSingleTonClass1 = SimpleSingleTonClass.getSingletonInstance();
    }
}