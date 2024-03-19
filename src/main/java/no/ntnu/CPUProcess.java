package no.ntnu;

/**
 * Represents a process that will be processed by the CPU.
 */
public class CPUProcess {
    private int id;
    private int arrivalTime;
    private int burstTime;
    private int priority;

    /**
     * Creates a new instance of {@code CPUProcess}.
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
    public int getTurnaroundTime(){
        return 1;
    }
    public int getWaitingTime() {
        return this.getTurnaroundTime() - this.burstTime;
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
}
