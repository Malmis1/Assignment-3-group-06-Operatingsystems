package no.ntnu;

public class Main {
    public static void main(String[] args) {
        CPUScheduler CPUScheduler = new CPUScheduler();
        CPUProcess process1 = new CPUProcess(1, 0, 11, 2);
        CPUProcess process2 = new CPUProcess(2, 5, 28, 0);
        CPUProcess process3 = new CPUProcess(3, 12, 2, 3);
        CPUProcess process4 = new CPUProcess(4, 2, 10, 1);
        CPUProcess process5 = new CPUProcess(5, 9, 16, 4);
        CPUScheduler.addProcess(process1);
        CPUScheduler.addProcess(process2);
        CPUScheduler.addProcess(process3);
        CPUScheduler.addProcess(process4);
        CPUScheduler.addProcess(process5);
        double avgWaitingTime = CPUScheduler.getAverageWaitingTimeFCFS();
        double avgTurnaroundTime = CPUScheduler.getAverageTurnaroundTimeFCFS();
        System.out.println("avgWaitingTime: " + avgWaitingTime);
        System.out.println("avgTurnaroundTime: " + avgTurnaroundTime);
    }
}
