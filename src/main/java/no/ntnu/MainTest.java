package no.ntnu;

import java.util.Scanner;

public class MainTest {
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
        double[] fcfsResults = CPUScheduler.calculateAverageTurnaroundAndWaitingTime();
        double fcfsAverageWaitingTime = fcfsResults[0];
        double fcfsAverageTurnaroundTime = fcfsResults[1];
        
        // For Preemptive Priority
        CPUScheduler.calculatePreemptivePriority();
        double preemptivePriorityAvgWaitingTime = CPUScheduler.getAverageWaitingTimePreemptivePriority();
        double preemptivePriorityAvgTurnaroundTime = CPUScheduler.getAverageTurnaroundTimePreemptivePriority();

        System.out.println("Results:");
        System.out.println("========");
        System.out.println("FCFS Average Waiting Time: " + fcfsAverageWaitingTime);
        System.out.println("FCFS Average Turnaround Time: " + fcfsAverageTurnaroundTime);
        System.out.println("Preemptive Priority Average Waiting Time: " + preemptivePriorityAvgWaitingTime);
        System.out.println("Preemptive Priority Average Turnaround Time: " + preemptivePriorityAvgTurnaroundTime);
    }
}
