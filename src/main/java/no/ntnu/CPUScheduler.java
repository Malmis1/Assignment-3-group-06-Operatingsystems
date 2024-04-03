package no.ntnu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Represents Preemptive Priority scheduling alongside FCFS scheduling algorithm.
 */
public class CPUScheduler {
    private final List<CPUProcess> processes;

    /**
     * Creates a new instance of {@code CPUScheduler}.
     */
    public CPUScheduler() {
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
     * Gets a list of all the processes sorted by arrival times in ascending
     * order.
     * 
     * @return a list of all the processes sorted by arrival times.
     * @throws IllegalStateException if there are no processes added.
     */
    public List<CPUProcess> getSortedQueue() {
        if (this.processes.isEmpty()) {
            throw new IllegalStateException("there must be at least one process");
        }

        // Create a copy of the processes list to sort, ensuring the original list is not modified
        List<CPUProcess> sortedProcesses = new ArrayList<>(this.processes);
        sortedProcesses.sort(Comparator.comparingInt(CPUProcess::getArrivalTime));

        return sortedProcesses;
    }

    /**
     * Calculates the average turnaround time for the processes scheduled using FCFS algorithm.
     *
     * @return the average turnaround time.
     * @throws IllegalStateException if there are no processes added or if the queue is empty.
     */
    public double[] calculateAverageTurnaroundAndWaitingTime() throws IllegalStateException {
        List<CPUProcess> sortedQueue = getSortedQueue();
        if (sortedQueue.isEmpty()) {
            throw new IllegalStateException("No processes added.");
        }

        int n = sortedQueue.size();
        int[] completionTime = new int[n];
        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;

        completionTime[0] = sortedQueue.get(0).getBurstTime() + sortedQueue.get(0).getArrivalTime();
        sortedQueue.get(0).setCompletionTime(completionTime[0]);
        totalTurnaroundTime = completionTime[0] - sortedQueue.get(0).getArrivalTime();

        for (int i = 1; i < n; i++) {
            CPUProcess currentProcess = sortedQueue.get(i);
            int startAfterPreviousCompletion = Math.max(completionTime[i - 1], currentProcess.getArrivalTime());
            completionTime[i] = startAfterPreviousCompletion + currentProcess.getBurstTime();

            currentProcess.setCompletionTime(completionTime[i]);
            totalTurnaroundTime += completionTime[i] - currentProcess.getArrivalTime();

            int waitingTime = startAfterPreviousCompletion - currentProcess.getArrivalTime();
            totalWaitingTime += waitingTime;
        }

        double averageWaitingTime = totalWaitingTime / n;

        double averageTurnaroundTime = totalTurnaroundTime / n;

        return new double[]{averageWaitingTime, averageTurnaroundTime};
    }

    /**
     * Executes preemptive priority scheduling on the process.
     */
    public void calculatePreemptivePriority() {
        // Priority queue considering the priority and then the arrival time
        PriorityQueue<CPUProcess> queue = new PriorityQueue<>(
                Comparator.comparingInt(CPUProcess::getPriority).thenComparingInt(CPUProcess::getArrivalTime));

        // Sort processes by arrival time to manage the arrival of new processes
        List<CPUProcess> sortedProcesses = new ArrayList<>(this.processes);
        sortedProcesses.sort(Comparator.comparingInt(CPUProcess::getArrivalTime));

        int currentTime = 0;
        CPUProcess currentProcess = null;
        boolean completed = false;

        while (!completed) {
            // Add processes that have arrived by currentTime to the queue
            while (!sortedProcesses.isEmpty() && sortedProcesses.get(0).getArrivalTime() <= currentTime) {
                queue.add(sortedProcesses.remove(0));
            }
            // If current process finished or was never assigned, get next from queue
            if (currentProcess == null || currentProcess.getRemainingBurstTime() <= 0) {
                if (!queue.isEmpty()) {
                    currentProcess = queue.poll();
                }
            } else if (!queue.isEmpty() && queue.peek().getPriority() < currentProcess.getPriority()) { // Check for higher priority process
                CPUProcess preemptedProcess = currentProcess;
                currentProcess = queue.poll();
                queue.add(preemptedProcess);
            }
            // Execute current process for a unit time
            if (currentProcess != null) {
                currentProcess.executeForOneUnit();
                
                // If the process is completed, update its completion and turnaround times
                if (currentProcess.getRemainingBurstTime() <= 0) {
                    currentProcess.setCompletionTime(currentTime);
                    currentProcess.calculateTurnaroundTime();
                    currentProcess = null; // Allow picking a new process in the next iteration
                }
            }
            if (!sortedProcesses.isEmpty() || !queue.isEmpty() || currentProcess != null) {
                completed = true;
            } else {
                currentTime++;
            }
        }
    }

    /**
     * Calculates the average waiting time for all processes for the Preemptive Priority scheduling algorithm.
     *
     * @return The average waiting time for all processes. Returns 0 if there are no processes.
     */
    public double getAverageWaitingTimePreemptivePriority() {
        calculatePreemptivePriority();

        double totalWaitingTime = 0;
        for (CPUProcess process : this.processes) {
            totalWaitingTime += process.getWaitingTime();
        }

        return processes.isEmpty() ? 0 : totalWaitingTime / this.processes.size();
    }

    /**
     * Calculates the average turnaround time for all processes for the Preemptive Priority scheduling algorithm.
     *
     * @return The average turnaround time for all processes. Returns 0 if there are no processes.
     */
    public double getAverageTurnaroundTimePreemptivePriority() {
        calculatePreemptivePriority();

        double totalTurnaroundTime = 0;
        for (CPUProcess process : this.processes) {
            totalTurnaroundTime += process.getTurnaroundTime();
        }

        return processes.isEmpty() ? 0 : totalTurnaroundTime / this.processes.size();
    }
}
