package no.ntnu;

public class Main {
    public static void main(String[] args) {
        FCFS fcfs = new FCFS();
        CPUProcess process1 = new CPUProcess(1, 5, 6);
        CPUProcess process2 = new CPUProcess(2, 0, 2);
        CPUProcess process3 = new CPUProcess(3, 2, 3);
        fcfs.addProcess(process1);
        fcfs.addProcess(process2);
        fcfs.addProcess(process3);
    }
}