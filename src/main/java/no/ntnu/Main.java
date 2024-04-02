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
