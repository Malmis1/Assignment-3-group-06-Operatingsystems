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
    public List<CPUProcess> getSortedQueue() throws IllegalStateException {
        if (this.processes.isEmpty()) {
            throw new IllegalStateException("there must be at least one process");
        }

        List<CPUProcess> queue = new ArrayList<>();
        List<CPUProcess> remainingProcesses = this.processes;
        CPUProcess earliestArrival = null;
        while (!remainingProcesses.isEmpty()) {
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

    /**
     * Calculates the average waiting time for all processes for the FCFS scheduling algorithm.
     *
     * @return The average waiting time for all processes. Returns 0 if there are no processes.
     */
    public float getAverageWaitingTimeFCFS() {
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
    public double getAverageTurnaroundTimeFCFS() throws IllegalStateException {
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

        return totalTurnaroundTime / n;
    }

    public void calculatePreemptivePriority() {
        // Reset times for all processes
        for (CPUProcess process : this.processes) {
            process.setCompletionTime(0);
            process.resetWaitingAndTurnaroundTimes();
        }

        // Priority queue considering the priority and then the arrival time
        PriorityQueue<CPUProcess> queue = new PriorityQueue<>(
                Comparator.comparingInt(CPUProcess::getPriority)
                        .thenComparingInt(CPUProcess::getArrivalTime));

        int currentTime = 0;
        CPUProcess currentProcess = null;

        // Sort processes by arrival time to manage the arrival of new processes
        List<CPUProcess> sortedProcesses = new ArrayList<>(this.processes);
        sortedProcesses.sort(Comparator.comparingInt(CPUProcess::getArrivalTime));

        while (!sortedProcesses.isEmpty() || !queue.isEmpty() || currentProcess != null) {
            // Add processes that have arrived by currentTime to the queue
            while (!sortedProcesses.isEmpty() && sortedProcesses.get(0).getArrivalTime() <= currentTime) {
                queue.add(sortedProcesses.remove(0));
            }

            // If current process finished or was never assigned, get next from queue
            if (currentProcess == null || currentProcess.getRemainingBurstTime() <= 0) {
                if (!queue.isEmpty()) {
                    currentProcess = queue.poll();
                    currentProcess.setStartTime(currentTime);
                }
            } else {
                // Check for higher priority process
                if (!queue.isEmpty() && queue.peek().getPriority() < currentProcess.getPriority()) {
                    CPUProcess preemptedProcess = currentProcess;
                    currentProcess = queue.poll();
                    currentProcess.setStartTime(currentTime);
                    queue.add(preemptedProcess); // Re-add the preempted process back to the queue
                }
            }

            // Execute current process for a unit time
            if (currentProcess != null) {
                currentProcess.executeForOneUnit();
                currentTime++;

                // If the process is completed, update its completion and turnaround times
                if (currentProcess.getRemainingBurstTime() <= 0) {
                    currentProcess.setCompletionTime(currentTime);
                    currentProcess.calculateTurnaroundTime();
                    currentProcess = null; // Allow picking a new process in the next iteration
                }
            } else {
                // No process is running; increment time
                currentTime++;
            }
        }
    }

    public float getAverageWaitingTimePreemptivePriority() {
        calculatePreemptivePriority();
        // Similar calculation logic to FCFS but after running calculatePreemptivePriority
        return 0;
    }

    public double getAverageTurnaroundTimePreemptivePriority() {
        calculatePreemptivePriority();
        // Similar calculation logic to FCFS but after running calculatePreemptivePriority
        return 0;
    }
}