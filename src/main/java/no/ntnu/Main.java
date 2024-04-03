package no.ntnu;

public class Main {
    public static void main(String[] args) {
        CPUScheduler CPUScheduler = new CPUScheduler();
        CPUProcess process1 = new CPUProcess(1, 4, 5, 0);
        CPUProcess process2 = new CPUProcess(2, 6, 4, 0);
        CPUProcess process3 = new CPUProcess(3, 0, 3, 0);
        CPUProcess process4 = new CPUProcess(4, 6, 2, 0);
        CPUProcess process5 = new CPUProcess(5, 5, 4, 0);
        CPUScheduler.addProcess(process1);
        CPUScheduler.addProcess(process2);
        CPUScheduler.addProcess(process3);
        CPUScheduler.addProcess(process4);
        CPUScheduler.addProcess(process5);

        // For FCFS

        double[] results = CPUScheduler.calculateAverageTurnaroundAndWaitingTime();
        double averageWaitingTime = results[0];
        double averageTurnaroundTime = results[1];
        System.out.println("FCFS Average Waiting Time: " + averageWaitingTime);
        System.out.println("FCFS Average Turnaround Time: " + averageTurnaroundTime);

        // For Preemptive Priority
        double avgWaitingTimePreemptivePriority = CPUScheduler.getAverageWaitingTimePreemptivePriority();
        double avgTurnaroundTimePreemptivePriority = CPUScheduler.getAverageTurnaroundTimePreemptivePriority();
        System.out.println("Preemptive Priority Average Waiting Time: " + avgWaitingTimePreemptivePriority);
        System.out.println("Preemptive Priority Average Turnaround Time: " + avgTurnaroundTimePreemptivePriority);
    }
}
