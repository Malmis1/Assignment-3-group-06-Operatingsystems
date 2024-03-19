package no.ntnu;

import java.util.ArrayList;
import java.util.Iterator;
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
     * Gets the queue with priorities considered.
     * 
     * @return the complete queue.
     */
    public List<CPUProcess> getCompleteQueue() {
        // This method is broken
        List<CPUProcess> sortedQueue = this.getSortedQueue();
        Iterator<CPUProcess> it = sortedQueue.iterator();
        if (!it.hasNext()) {
            return sortedQueue;
        }
        CPUProcess currentProcess = it.next();
        if (!it.hasNext()) {
            return sortedQueue;
        }
        CPUProcess nextProcess = it.next();
        List<CPUProcess> queue = new ArrayList<>();
        boolean isComplete = false;
        while (!isComplete) {
            if (currentProcess == null) {
                isComplete = true;
            } else if (nextProcess == null) {
                queue.add(currentProcess);
                currentProcess = null;
            } else {
                if ((nextProcess.getArrivalTime() > (currentProcess.getArrivalTime() + currentProcess.getBurstTime()))
                        || (nextProcess.getPriority() >= currentProcess.getPriority())) {
                    queue.add(currentProcess);
                    currentProcess = nextProcess;
                    nextProcess = it.hasNext() ? it.next() : null;
                } else {
                    CPUProcess fragmentedProcess1 = new CPUProcess(currentProcess.getId(),
                            currentProcess.getArrivalTime(),
                            (nextProcess.getArrivalTime() - currentProcess.getArrivalTime()) < 0
                                    ? currentProcess.getArrivalTime() + currentProcess.getBurstTime()
                                            - nextProcess.getArrivalTime()
                                    : nextProcess.getArrivalTime() - currentProcess.getArrivalTime(),
                            currentProcess.getPriority());
                    CPUProcess fragmentedProcess2 = new CPUProcess(currentProcess.getId(),
                            nextProcess.getArrivalTime() + nextProcess.getBurstTime(),
                            currentProcess.getBurstTime() > fragmentedProcess1.getBurstTime()
                                    ? currentProcess.getBurstTime() - fragmentedProcess1.getBurstTime()
                                    : fragmentedProcess1.getBurstTime() - currentProcess.getBurstTime(),
                            currentProcess.getPriority());
                    if (fragmentedProcess1.getBurstTime() != 0) {
                        queue.add(fragmentedProcess1);
                    }
                    currentProcess = nextProcess;
                    nextProcess = fragmentedProcess2;
                }
            }
        }

        return queue;
    }

    /**
     * Gets a list of all the processes sorted by arrival times in an ascending
     * order.
     * 
     * @return a list of all the processes sorted by arrival times.
     * @throws IllegalStateException if there are no processes added.
     */
    private List<CPUProcess> getSortedQueue() throws IllegalStateException {
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
}