package org.example;

public class Main {
    public static void main(String[] args)
    {
        SimulationManager gen=new SimulationManager();
        Thread t=new Thread(gen);
        t.start();
    }
}