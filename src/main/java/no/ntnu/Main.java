package no.ntnu;

public class Main {
    public static void main(String[] args) {
        FCFS fcfs = new FCFS();
        CPUProcess process1 = new CPUProcess(1, 0, 11, 2);
        CPUProcess process2 = new CPUProcess(2, 5, 28, 0);
        CPUProcess process3 = new CPUProcess(3, 12, 2, 3);
        CPUProcess process4 = new CPUProcess(4, 2, 10, 1);
        CPUProcess process5 = new CPUProcess(5, 9, 16, 4);
        fcfs.addProcess(process1);
        fcfs.addProcess(process2);
        fcfs.addProcess(process3);
        fcfs.addProcess(process4);
        fcfs.addProcess(process5);
        double avgTurnaroundTime = fcfs.calculateAverageTurnaroundTime();
        System.out.println(avgTurnaroundTime);

    }
}
