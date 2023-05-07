package org.example;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    public void addTask(List<Server> servers, Task t)
    {
        if(servers.size()==0)
            return;
        else
        {
            int indexServer=0;
            int minim=servers.get(0).getSizeTask();
            for(int i=0;i<servers.size();i++)
            {
                if(servers.get(i).getSizeTask()<servers.get(i).maxTasksPerQueue)
                {
                    if(servers.get(i).getSizeTask()<minim)
                    {
                        minim=servers.get(i).getSizeTask();
                        indexServer=i;
                    }
                }
            }
            servers.get(indexServer).addTask(t);
        }
    }
}
