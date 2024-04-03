package no.ntnu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // CPUProcess process1 = new CPUProcess(1, 4, 5, 0);
        // CPUProcess process2 = new CPUProcess(2, 6, 4, 0);
        // CPUProcess process3 = new CPUProcess(3, 0, 3, 0);
        // CPUProcess process4 = new CPUProcess(4, 6, 2, 0);
        // CPUProcess process5 = new CPUProcess(5, 5, 4, 0);
        // CPUScheduler.addProcess(process1);
        // CPUScheduler.addProcess(process2);
        // CPUScheduler.addProcess(process3);
        // CPUScheduler.addProcess(process4);
        // CPUScheduler.addProcess(process5);

        Scanner scanner = new Scanner(System.in);
        CPUScheduler CPUScheduler = new CPUScheduler();

        boolean validResponse = false;
        int algorithm = 1;
        while (!validResponse) {
            System.out.println("Please select one of the following CPU scheduling algorithm:");
            System.out.println("1) First Come First Serve (FCFS)");
            System.out.println("2) Preemptive priority");

            try {
                algorithm = scanner.nextInt();
                if (1 <= algorithm && algorithm <= 2) {
                    validResponse = true;
                } else {
                    System.out.println(algorithm + " is not a valid response.");
                }
            } catch (Exception e) {
                System.out.println("Please input a valid number.");
                scanner.next(); // avoids looping bug
            }
        }

        System.out.println("Please enter details for each process:");

        boolean continueAddingProcesses = true;
        while (continueAddingProcesses) {
            System.out.println();

            int id = 0;
            boolean validID = false;
            while (!validID) {
                System.out.println("\nProcess id: ");
                try {
                    id = scanner.nextInt();
                    if (!CPUScheduler.hasID(id)) {
                        validID = true;
                    } else {
                        System.out.println("Process with id '" + id + "' already exists.");
                    }
                } catch (Exception e) {
                    System.out.println("Please type a valid id number.");
                    scanner.next(); // avoids looping bug
                }
            }

            int arrivalTime = 0;
            arrivalTime = requestInt(scanner, arrivalTime, "Arrival Time: ");

            int burstTime = 0;
            burstTime = requestInt(scanner, arrivalTime, "Burst Time: ");

            int priority = 0;
            if (algorithm == 2) {
                priority = requestInt(scanner, arrivalTime, "Priority: ");
            }

            CPUProcess process = new CPUProcess(id, arrivalTime, burstTime, priority);
            CPUScheduler.addProcess(process);

            validResponse = false;
            while (!validResponse) {
                System.out.print("\nDo you want to add another process? (yes/no): ");
                String response = scanner.next();
                if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")) {
                    validResponse = true;
                    continueAddingProcesses = true;
                } else if (response.equalsIgnoreCase("no") || response.equalsIgnoreCase("n")) {
                    validResponse = true;
                    continueAddingProcesses = false;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }
            id++;
        }

        System.out.println("Results:");
        System.out.println("========");
        if (algorithm == 1) {
            // For FCFS
            double[] fcfsResults = CPUScheduler.calculateAverageTurnaroundAndWaitingTime();
            double fcfsAverageWaitingTime = fcfsResults[0];
            double fcfsAverageTurnaroundTime = fcfsResults[1];
            System.out.println("FCFS Average Waiting Time: " + fcfsAverageWaitingTime);
            System.out.println("FCFS Average Turnaround Time: " + fcfsAverageTurnaroundTime);
        } else {
            // For Preemptive Priority
            CPUScheduler.calculatePreemptivePriority();
            double preemptivePriorityAvgWaitingTime = CPUScheduler.getAverageWaitingTimePreemptivePriority();
            double preemptivePriorityAvgTurnaroundTime = CPUScheduler.getAverageTurnaroundTimePreemptivePriority();
            System.out.println("Preemptive Priority Average Waiting Time: " + preemptivePriorityAvgWaitingTime);
            System.out.println("Preemptive Priority Average Turnaround Time: " + preemptivePriorityAvgTurnaroundTime);
        }

        scanner.close();
    }

    private static int requestInt(Scanner scanner, int value, String prompt) {
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            try {
                value = scanner.nextInt();
                validInput = true;
            } catch (Exception e) {
                System.out.println("Please type a valid number.");
                scanner.next(); // avoids looping bug
            }
        }
        return value;
    }
}
