import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<User> users = new ArrayList<>();
    private static final String FILENAME = "todolist_data.txt";

    public static void main(String[] args) {
        loadFromFile();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n================ To-Do List Manager ================");
            System.out.println("\n1. Add User");
            System.out.println("2. Add Task");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. View Tasks");
            System.out.println("6. Exit and Save");
            System.out.println("\n====================================================");

            // Adding this line to prompt the user for input
            System.out.print("\nPlease choose your option: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addUser(scanner);
                    break;
                case 2:
                    addTask(scanner);
                    break;
                case 3:
                    markTaskCompleted(scanner);
                    break;
                case 4: // New case to delete a task
                    deleteTask(scanner);
                    break;
                case 5:
                    viewTasks(scanner);
                    break;
                case 6:
                    System.out.println("\n=================== Save and Exit ==================");
                    saveToFile();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("\n----------------------------------------------------");
                    System.out.println("\nInvalid option. Please try again.");
            }

        } while (choice != 6);
        scanner.close();
    }

    private static void addUser(Scanner scanner) {
        System.out.println("\n===================== Add User ====================");
        System.out.print("\nEnter user name: ");
        String name = scanner.nextLine();

        // Check for duplicate user
        for (User user : users) {
            if (user.getName().equals(name)) {
                System.out.println("\n----------------------------------------------------");
                System.out.println("\nUser already exists.");
                return;
            }
        }

        users.add(new User(name));
        System.out.println("\n----------------------------------------------------");
        System.out.println("\nUser added.");
    }

    private static void addTask(Scanner scanner) {
        User user = selectUser(scanner);
        if (user != null) {
            System.out.println("\n===================== Add Task ====================");
            System.out.print("\nEnter task description: ");
            String description = scanner.nextLine();

            System.out.println("\n----------------------------------------------------");
            System.out.print("\nEnter task priority (1: High, 2: Medium, 3: Low): ");
            int priority = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (priority < 1 || priority > 3) {
                System.out.println("\n----------------------------------------------------");
                System.out.println("\nInvalid priority. Please enter 1, 2, or 3.");
                return;
            }

            user.addTask(description, priority);
            System.out.println("\n----------------------------------------------------");
            System.out.println("\nTask added.");
        }
    }

    private static void markTaskCompleted(Scanner scanner) {
        User user = selectUser(scanner);
        if (user != null) {
            System.out.println("\n=================== Complete Task ==================");
            System.out.print("\nEnter task description to mark as completed: ");
            String description = scanner.nextLine();
            if (description.isEmpty()) {
                System.out.println("Task description cannot be empty.");
                return;
            }

            user.markTaskCompleted(description);
        }
    }

    private static void deleteTask(Scanner scanner) { // Method to delete a task
        User user = selectUser(scanner);
        if (user != null) {
            System.out.println("\n====================== Delete ======================");
            System.out.print("\nEnter task description to delete: ");
            String description = scanner.nextLine();
            if (description.isEmpty()) {
                System.out.println("Task description cannot be empty.");
                return;
            }

            user.deleteTask(description);
        }
    }

    private static void viewTasks(Scanner scanner) {
        User user = selectUser(scanner);
        if (user != null) {
            user.printTasks();
        }
    }

    private static User selectUser(Scanner scanner) {
        System.out.println("\n====================== Sign In =====================");
        System.out.print("\nEnter user name: ");
        String name = scanner.nextLine();
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        System.out.println("\n----------------------------------------------------");
        System.out.println("\nUser not found.");
        return null;
    }

    public static void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (User user : users) {
                writer.write(user.getName() + "\n");
                user.getTaskList().writeTasks(writer);
                writer.write("END\n"); // Mark end of user's tasks
            }
            System.out.println("\nData saved successfully.");
        } catch (IOException e) {
            System.out.println("\nAn error occurred while saving data.");
        }
    }

    public static void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            User currentUser = null;

            while ((line = reader.readLine()) != null) {
                if (line.equals("END")) {
                    currentUser = null;
                } else if (currentUser == null) {
                    currentUser = new User(line);
                    users.add(currentUser);
                } else {
                    String[] parts = line.split(",");
                    String description = parts[0];
                    boolean isCompleted = Boolean.parseBoolean(parts[1]);
                    int priority = Integer.parseInt(parts[2]);

                    currentUser.addTask(description, priority);
                    if (isCompleted) {
                        currentUser.markTaskCompleted(description);
                    }
                }
            }
            System.out.println("\nData loaded successfully.");
        } catch (IOException e) {
            System.out.println("\nNo previous data found or an error occurred while loading data.");
        }
    }
}