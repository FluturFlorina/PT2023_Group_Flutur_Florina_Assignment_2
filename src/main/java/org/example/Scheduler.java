package org.example;

import Model.*;

import java.util.ArrayList;
import java.util.List;

import static org.example.SelectionPolicy.SHORTEST_QUEUE;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;
    public float averageWaitingTime = 0;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        servers=new ArrayList<>();
        this.maxNoServers=maxNoServers;
        this.maxTasksPerServer=maxTasksPerServer;
        for(int i=0;i<maxNoServers;i++)
        {
            Server ser=new Server(maxTasksPerServer,i);
            servers.add(ser);// - create server object
            Thread thread=new Thread(ser); // -create thread with the object
            thread.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t)
    {
        // call the strategy addTask method
        strategy.addTask(servers,t);
        averageWaitingTime += t.getServiceTime();
    }

    public List<Server> getServers()
    {
        return servers;
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

}
