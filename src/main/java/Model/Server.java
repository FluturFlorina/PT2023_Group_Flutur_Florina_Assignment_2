package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable
{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private Integer idServer;
    public Integer maxTasksPerQueue;

    public Server(Integer maxTasksPerQueue,Integer serverId)
    {
        tasks=new ArrayBlockingQueue<>(maxTasksPerQueue); // se initializeaza coada
        waitingPeriod=new AtomicInteger(0);
        this.maxTasksPerQueue=maxTasksPerQueue;
        this.idServer=serverId;
    }

    public void addTask(Task taskNou)
    {
        tasks.add(taskNou);
        waitingPeriod.set(taskNou.getServiceTime());
    }

    @Override
    public void run()
    {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tasks.size() > 0) {

                tasks.element().setServiceTime(tasks.element().getServiceTime() - 1);
                waitingPeriod.addAndGet(-1);
                if (tasks.element().getServiceTime() == 0) {
                    try {
                        tasks.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
    public Task[] getTasks()
    {
        BlockingQueue<Task> coada=new LinkedBlockingQueue<>(tasks);
        Task[] t =new Task[coada.size()];
        int k=0;
        for (Task i:coada) {
            t[k]=i;
            k++;
        }
        return t;
    }
    public int getSizeTask(){return tasks.size();}

    public int getWaitingPeriod(){return waitingPeriod.get();}
}

