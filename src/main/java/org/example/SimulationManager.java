package org.example;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import javax.annotation.processing.Generated;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.example.SelectionPolicy.SHORTEST_QUEUE;
public class SimulationManager implements Runnable
{
    public int timeLimit;// =100;
    public int maxProcessingTime;//=10;
    public int minProcessingTime;//=2;
    public int minArrivalTime;//=2;
    public int maxArrivalTime;//=10;
    public int numberOfServers=3;//=3;
    public int numberOfClients;//=100;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks;
    int currentTime;
    int serviceTime;
    int peakHour;
    int queueLenght;
    public SelectionPolicy selectionPolicy=SelectionPolicy.SHORTEST_TIME;
    public SimulationManager()
    {
        // initialize the scheduler
        this.scheduler=new Scheduler(numberOfServers,10);
        // => initialize selection strategy => createStrategy
        scheduler.changeStrategy(SHORTEST_QUEUE);
        // initialize frame to display simulation
        this.frame=new SimulationFrame();
        initializare(frame.getInitValues());
        generatedTasks= Collections.synchronizedList(new ArrayList<>());
        generateNRadnomTasks();
    }

    private void generateNRadnomTasks()
    {
        for(int i=0;i<numberOfClients;i++)
        {
            int random_service=(int)Math.floor(Math.random() * (maxProcessingTime - minProcessingTime + 1) + minProcessingTime);
            int random_arrival=(int)Math.floor(Math.random() * (maxArrivalTime - minArrivalTime + 1) + minArrivalTime);
            generatedTasks.add(new Task(random_service,random_arrival));
        }
        generatedTasks.sort((Comparator.comparing(Task::getArrivalTime)));
    }

    private String prettyPrint(){
        StringBuilder sb = new StringBuilder("Waiting clients: ");
        for (Task client : generatedTasks) {
            sb.append("(").append(client.getIdTask() + 1).append(",").append(client.getArrivalTime()).append(",").append(client.getServiceTime()).append(")  ");
        }
        int serverNumber = 1;
        for (Server server : scheduler.getServers()) {
            sb.append("\nQueue number ").append(serverNumber).append(": ");
            if (server.getSizeTask() != 0) {
                Task[] clients = server.getTasks();
                for (Task client : clients) {
                    sb.append("(").append(client.getIdTask() + 1).append(",").append(client.getArrivalTime()).append(",").append(client.getServiceTime()).append(")  ");
                }
            } else {
                sb.append("Closed");
            }
            serverNumber++;
        }
        return sb.toString();
    }

    @Override
    public void run() {
        this.currentTime=0;
        this.serviceTime=0;
        this.peakHour=0;
        this.queueLenght=0;
        FileWriter fileWriter=null;
        try { fileWriter = new FileWriter("log.txt");}
        catch (IOException e) {throw new RuntimeException(e);}
        while (currentTime < timeLimit) {
            try {fileWriter.write("\n"+"Time " + currentTime + "\n"+ prettyPrint());}
            catch (IOException e) {throw new RuntimeException(e);}
            List<Task> sters = new ArrayList<>();
            for (Task task : generatedTasks) {
                if(task.getArrivalTime()<=currentTime){
                    scheduler.dispatchTask(task);
                    sters.add(task);
                }
            }
            generatedTasks.removeAll(sters);
            for (int i = 0; i < scheduler.getMaxNoServers(); i++) {
                Server server = scheduler.getServers().get(i);
                int length = server.getSizeTask();
                if (length > queueLenght) {
                    queueLenght = length;
                    peakHour = currentTime;
                }
                Task[] clients = server.getTasks();
                for (int j = 0; j < length; j++) {
                    serviceTime += clients[j].getServiceTime();
                }
            }
            System.out.println("Time " + currentTime + "\n" + prettyPrint());
            currentTime++;
            try{Thread.sleep(1000);}
            catch (InterruptedException e) {e.printStackTrace();}
            double averageWaitingTime = (double) scheduler.averageWaitingTime / numberOfClients;
            double averageServiceTime = (double)  serviceTime / numberOfClients;
            System.out.println("Average waiting time: " + averageWaitingTime + "\n" + "Average service time: " + averageServiceTime + "\n" + "Peak hour: " + peakHour+"\n");
        }
    }

   public void initializare(List<Integer> list){
       this.numberOfClients=list.get(0);
       this.numberOfServers=list.get(1);
       this.timeLimit=list.get(2);
       this.minArrivalTime=list.get(3);
       this.maxArrivalTime=list.get(4);
       this.minProcessingTime=list.get(5);
       this.maxProcessingTime=list.get(6);
   }

}
