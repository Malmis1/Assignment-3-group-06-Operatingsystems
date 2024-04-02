package no.ntnu;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a First-Come-First-Serve CPU scheduling algorithm.
 */
public class FCFS {
    private List<CPUProcess> processes;

    /**
     * Creates a new instance of {@code FCFS}.
     */
    public FCFS() {
        this.processes = new ArrayList<>();
    }

    /**
     * Adds a process to be handled by the scheduling algorithm.
     * 
     * @param process the process to be added.
     */
    public void addProcess(CPUProcess process) {
        this.processes.add(process);
    }

    /**
     * Gets a list of all the processes sorted by arrival times in an ascending
     * order.
     * 
     * @return a list of all the processes sorted by arrival times.
     * @throws IllegalStateException if there are no processes added.
     */
    public List<CPUProcess> getSortedQueue() throws IllegalStateException {
        if (this.processes.size() == 0) {
            throw new IllegalStateException("there must be at least one process");
        }

        List<CPUProcess> queue = new ArrayList<>();
        List<CPUProcess> remainingProcesses = this.processes;
        CPUProcess earliestArrival = null;
        while (remainingProcesses.size() != 0) {
            for (CPUProcess process : this.processes) {
                if (remainingProcesses.contains(process)) {
                    if (earliestArrival == null || process.getArrivalTime() < earliestArrival.getArrivalTime()) {
                        earliestArrival = process;
                    }
                }
            }
            queue.add(earliestArrival);
            remainingProcesses.remove(earliestArrival);
            earliestArrival = null;
        }

        return queue;
    }


    public float getAverageWaitingTime() {
        float combinedWaitingTime = 0;

        for (CPUProcess process : this.processes) {
            combinedWaitingTime += process.getWaitingTime();
        }

        return combinedWaitingTime / this.processes.size();
    }
    /**
     * Calculates the average turnaround time for the processes scheduled using FCFS algorithm.
     *
     * @return the average turnaround time.
     * @throws IllegalStateException if there are no processes added or if the queue is empty.
     */
    public double calculateAverageTurnaroundTime() throws IllegalStateException {
        List<CPUProcess> sortedQueue = getSortedQueue();
        if (sortedQueue.isEmpty()) {
            throw new IllegalStateException("No processes added.");
        }

        int n = sortedQueue.size();
        int[] completionTime = new int[n];
        double totalTurnaroundTime = 0;

        completionTime[0] = sortedQueue.get(0).getBurstTime() + sortedQueue.get(0).getArrivalTime();
        sortedQueue.get(0).setCompletionTime(completionTime[0]);
        totalTurnaroundTime = completionTime[0] - sortedQueue.get(0).getArrivalTime();

        for (int i = 1; i < n; i++) {
            CPUProcess currentProcess = sortedQueue.get(i);
            int startAfterPreviousCompletion = Math.max(completionTime[i - 1], currentProcess.getArrivalTime());
            completionTime[i] = startAfterPreviousCompletion + currentProcess.getBurstTime();

            currentProcess.setCompletionTime(completionTime[i]);
            totalTurnaroundTime += completionTime[i] - currentProcess.getArrivalTime();
        }

        return totalTurnaroundTime/n;
    }
}