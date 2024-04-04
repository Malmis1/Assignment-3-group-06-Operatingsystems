package no.ntnu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CPUScheduler cpuScheduler = new CPUScheduler();

        boolean validResponse = false;
        int algorithm = 1;
        while (!validResponse) {
            System.out.println("Please select one of the following CPU scheduling algorithm:");
            System.out.println("1) First Come First Serve (FCFS)");
            System.out.println("2) Preemptive priority");
            System.out.println("Please choose an option:");

            try {
                algorithm = scanner.nextInt();
                if (1 <= algorithm && algorithm <= 2) {
                    validResponse = true;
                } else {
                    System.out.println(algorithm + " is not a valid response.");
                }
            } catch (Exception e) {
                System.out.println("Please input a valid number.");
                scanner.next(); // Avoids looping bug
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
                    if (id < 0) {
                        System.out.println("Please enter a non-negative ID.");
                    } else if (cpuScheduler.hasID(id)) {
                        System.out.println("Process with id '" + id + "' already exists.");
                    } else {
                        validID = true;
                    }
                } catch (Exception e) {
                    System.out.println("Please type a valid id number.");
                    scanner.next(); // Avoids looping bug
                }
            }

            int arrivalTime = requestInt(scanner, "Arrival Time: ");
            int burstTime = requestInt(scanner, "Burst Time: ");

            int priority = 0;
            if (algorithm == 2) {
                priority = requestInt(scanner, "Priority: ");
            }


            CPUProcess process = new CPUProcess(id, arrivalTime, burstTime, priority);
            cpuScheduler.addProcess(process);

            validResponse = false;
            while (!validResponse) {
                System.out.print("\nDo you want to add another process? (yes/no): ");
                String response = scanner.next();
                if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")) {
                    validResponse = true;
                } else if (response.equalsIgnoreCase("no") || response.equalsIgnoreCase("n")) {
                    validResponse = true;
                    continueAddingProcesses = false;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }
        }

        System.out.println("\nResults:");
        System.out.println("========");
        if (algorithm == 1) {
            // For FCFS
            double[] fcfsResults = cpuScheduler.calculateAverageTurnaroundAndWaitingTimeFCFS();
            double fcfsAverageWaitingTime = fcfsResults[0];
            double fcfsAverageTurnaroundTime = fcfsResults[1];
            System.out.println("FCFS Average Waiting Time: " + fcfsAverageWaitingTime);
            System.out.println("FCFS Average Turnaround Time: " + fcfsAverageTurnaroundTime);
        } else {
            // For Preemptive Priority
            cpuScheduler.calculatePreemptivePriority();
            double preemptivePriorityAvgWaitingTime = cpuScheduler.getAverageWaitingTimePreemptivePriority();
            double preemptivePriorityAvgTurnaroundTime = cpuScheduler.getAverageTurnaroundTimePreemptivePriority();
            System.out.println("Preemptive Priority Average Waiting Time: " + preemptivePriorityAvgWaitingTime);
            System.out.println("Preemptive Priority Average Turnaround Time: " + preemptivePriorityAvgTurnaroundTime);
        }

        scanner.close();
    }

    private static int requestInt(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = scanner.nextInt();
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Please enter a non-negative value.");
                }
            } catch (Exception e) {
                System.out.println("Please type a valid number.");
                scanner.next(); // Avoids looping bug
            }
        }
    }


}
