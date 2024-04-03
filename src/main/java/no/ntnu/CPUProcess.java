package no.ntnu;

/**
 * Represents a process that will be processed by the CPU.
 */
public class CPUProcess {
    private int id;
    private int arrivalTime;
    private int burstTime;
    private int remainingBurstTime;
    private int priority;
    private double completionTime;
    private double turnaroundTime;
    private boolean completed = false;

    /**
     * Creates a new instance of {@code CPUProcess} with a specified priority.
     * 
     * @param id          the id of the process.
     * @param arrivalTime the arrival time of the process.
     * @param burstTime   the burst time of the process.
     * @param priority    the priority of the process, where smaller numbers have
     *                    higher priority.
     * @throws IllegalArgumentException if any of the provided parameters are
     *                                  invalid.
     */
    public CPUProcess(int id, int arrivalTime, int burstTime, int priority) throws IllegalArgumentException {
        this.setId(id);
        this.setArrivalTime(arrivalTime);
        this.setBurstTime(burstTime);
        this.setPriority(priority);
        this.remainingBurstTime = this.burstTime;
        this.completed = false;
    }

    /**
     * Gets the arrival time of the process.
     *
     * @return the arrival time of the process.
     */
    public int getArrivalTime() {
        return this.arrivalTime;
    }

    /**
     * Gets the burst time of the process.
     *
     * @return the burst time of the process.
     */
    public int getBurstTime() {
        return this.burstTime;
    }

    /**
     * Gets the waiting time of the process.
     *
     * @return the waiting time of the process.
     */
    public double getWaitingTime() {
        return this.turnaroundTime - this.burstTime;
    }

    /**
     * Gets the turnaround time of the process.
     *
     * @return the turnaround time of the process.
     */
    public double getTurnaroundTime() {
        return this.turnaroundTime;
    }

    /**
     * Gets the priority of the process.
     *
     * @return the priority of the process.
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * Gets the remaining burst time of the process.
     *
     * @return The remaining burst time.
     */
    public int getRemainingBurstTime() {
        return this.remainingBurstTime;
    }

    /**
     * Sets the id of the process.
     * 
     * @param id the id to set.
     */
    private void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the arrival time of the process.
     * 
     * @param arrivalTime the arrival time to set.
     * @throws IllegalArgumentException if the arrival time is less than 0.
     */
    private void setArrivalTime(int arrivalTime) throws IllegalArgumentException {
        if (arrivalTime < 0) {
            throw new IllegalArgumentException("arrival time cannot be less than 0");
        }
        this.arrivalTime = arrivalTime;
        this.calculateTurnaroundTime();
    }

    /**
     * Sets the burst time of the process.
     *
     * @param burstTime the burst time to set.
     * @throws IllegalArgumentException if the burst time is less than 0.
     */
    private void setBurstTime(int burstTime) throws IllegalArgumentException {
        if (burstTime < 0) {
            throw new IllegalArgumentException("burst time cannot be less than 0");
        }
        this.burstTime = burstTime;
    }

    /**
     * Sets the priority of the process. The smaller the number is, the higher the
     * priority becomes.
     *
     * @param priority the priority to set.
     */
    private void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Sets the completion time of the process.
     *
     * @param completionTime the completion time to set.
     */
    public void setCompletionTime(double completionTime) {
        this.completionTime = completionTime;
        this.calculateTurnaroundTime();
    }

    /**
     * Execute the process for one unit of time.
     */
    public void executeForOneUnit() {
        // Check if the process has remaining time and is not already completed
        if (this.remainingBurstTime > 0 && !this.completed) {
            this.remainingBurstTime--;
            // Check if the process completes after this execution unit
            if (this.remainingBurstTime == 0) {
                this.completed = true;
                // Completion time and other calculations need to be done outside as they depend on global simulation time
            }
        }
    }

    /**
     * Calculates and updates the turnaround time for the process.
     */
    public void calculateTurnaroundTime() {
        this.turnaroundTime = this.completionTime - this.arrivalTime;
    }
}
