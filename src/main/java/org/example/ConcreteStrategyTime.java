package org.example;


import Model.Server;
import Model.Task;

import java.util.*;

public class ConcreteStrategyTime implements Strategy
{
    @Override
    public void addTask(List<Server> servers, Task t)
    {
        if(servers.size()==0)
            return;
        else
        {
            int indexServer=0;
            int minim=servers.get(0).getWaitingPeriod();
            for(int i=0;i<servers.size();i++)
            {
                if(servers.get(i).getSizeTask()<servers.get(i).maxTasksPerQueue)
                {
                    if(minim>servers.get(i).getWaitingPeriod())
                    {
                        minim=servers.get(i).getWaitingPeriod();
                        indexServer=i;
                    }
                }
            }
            servers.get(indexServer).addTask(t);
        }
    }
}
