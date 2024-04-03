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
    private float completionTime;
    private float startTime;
    private float waitingTime;
    private float turnaroundTime;
    private boolean started = false;
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
        this.remainingBurstTime = burstTime; // Make sure to initialize this correctly.
        this.completed = false;
    }

    /**
     * Creates a new instance of {@code CPUProcess}.
     * 
     * @param id          the id of the process.
     * @param arrivalTime the arrival time of the process.
     * @param burstTime   the burst time of the process.
     * @throws IllegalArgumentException if any of the provided parameters are
     *                                  invalid.
     */
    public CPUProcess(int id, int arrivalTime, int burstTime) throws IllegalArgumentException {
        this.setId(id);
        this.setArrivalTime(arrivalTime);
        this.setBurstTime(burstTime);
        this.remainingBurstTime = burstTime;
        this.priority = 0;
    }

    /**
     * Gets the id of the process.
     * 
     * @return the id of the process.
     */
    public int getId() {
        return this.id;
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
     * Gets the arrival time of the process.
     * 
     * @return the arrival time of the process.
     */
    public int getArrivalTime() {
        return this.arrivalTime;
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
    public float getWaitingTime() {
        return this.getTurnaroundTime() - this.burstTime;
    }

    /**
     * Sets the completion time of the process.
     *
     * @param completionTime the completion time to set.
     */
    public void setCompletionTime(float completionTime) {
        this.completionTime = completionTime;
        this.calculateTurnaroundTime();
    }


    /**
     * Calculates and updates the turnaround time for the process.
     */
    public void calculateTurnaroundTime() {
        this.turnaroundTime = this.completionTime - this.arrivalTime;
    }

    /**
     * Gets the turnaround time of the process.
     *
     * @return the turnaround time of the process.
     */
    public float getTurnaroundTime() {
        return this.turnaroundTime;
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
     * Gets the priority of the process.
     * 
     * @return the priority of the process.
     */
    public int getPriority() {
        return this.priority;
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
     * Resets the waiting and turnaround times for this process.
     */
    public void resetWaitingAndTurnaroundTimes() {
        // Reset only if the process has started, otherwise keep initial values
        if (this.started) {
            this.waitingTime = 0;
        }
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
     * Sets the start time of the process. This method also marks the process as started.
     *
     * @param startTime The start time to set.
     */
    public void setStartTime(float startTime) {
        // Only set the start time if the process hasn't started before
        if (!this.started) {
            this.startTime = startTime;
            this.started = true;
        }
    }

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
}
