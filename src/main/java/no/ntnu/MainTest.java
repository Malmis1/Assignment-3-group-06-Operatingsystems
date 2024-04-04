package no.ntnu;

public class MainTest {
    public static void main(String[] args) {
        no.ntnu.CPUScheduler cpuScheduler = getCpuScheduler();

        // For FCFS
        double[] fcfsResults = cpuScheduler.calculateAverageTurnaroundAndWaitingTimeFCS();
        double fcfsAverageWaitingTime = fcfsResults[0];
        double fcfsAverageTurnaroundTime = fcfsResults[1];

        // For Preemptive Priority
        cpuScheduler.calculatePreemptivePriority();
        double preemptivePriorityAvgWaitingTime = cpuScheduler.getAverageWaitingTimePreemptivePriority();
        double preemptivePriorityAvgTurnaroundTime = cpuScheduler.getAverageTurnaroundTimePreemptivePriority();

        System.out.println("Results:");
        System.out.println("========");
        System.out.println("FCFS Average Waiting Time: " + fcfsAverageWaitingTime);
        System.out.println("FCFS Average Turnaround Time: " + fcfsAverageTurnaroundTime);
        System.out.println("Preemptive Priority Average Waiting Time: " + preemptivePriorityAvgWaitingTime);
        System.out.println("Preemptive Priority Average Turnaround Time: " + preemptivePriorityAvgTurnaroundTime);
    }

    private static CPUScheduler getCpuScheduler() {
        CPUScheduler cpuScheduler = new CPUScheduler();
        CPUProcess process1 = new CPUProcess(1, 4, 5, 0);
        CPUProcess process2 = new CPUProcess(2, 6, 4, 0);
        CPUProcess process3 = new CPUProcess(3, 0, 3, 0);
        CPUProcess process4 = new CPUProcess(4, 6, 2, 0);
        CPUProcess process5 = new CPUProcess(5, 5, 4, 0);
        cpuScheduler.addProcess(process1);
        cpuScheduler.addProcess(process2);
        cpuScheduler.addProcess(process3);
        cpuScheduler.addProcess(process4);
        cpuScheduler.addProcess(process5);
        return cpuScheduler;
    }
}
