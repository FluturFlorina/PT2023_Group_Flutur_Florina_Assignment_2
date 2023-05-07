package Model;

public class Task
{
    private int idTask;
    private final int arrivalTime; // timpul necesar pentru client sa ajunga la coada
    private int serviceTime; // timpul cat sta un client in coada la serviciu de casierie
    public Task(int serviceTime,int arrivalTime)
    {
        this.arrivalTime=arrivalTime;
        this.serviceTime=serviceTime;
    }
    public int getIdTask() {
        return idTask;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

}
