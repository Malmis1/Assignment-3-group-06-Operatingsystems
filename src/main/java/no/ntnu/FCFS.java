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
        CPUProcess firstArrival = null;
        while (remainingProcesses.size() != 0) {
            for (CPUProcess process : this.processes) {
                if (remainingProcesses.contains(process)) {
                    if (firstArrival == null || process.getArrivalTime() < firstArrival.getArrivalTime()) {
                        firstArrival = process;
                    }
                }
            }
            queue.add(firstArrival);
            remainingProcesses.remove(firstArrival);
            firstArrival = null;
        }

        return queue;
    }
}