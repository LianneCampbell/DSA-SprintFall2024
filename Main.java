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
            System.out.println("1. Add User\n2. Add Task\n3. Mark Task as Completed\n4. View Tasks\n5. Exit");
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
                case 4:
                    viewTasks(scanner);
                    break;
                case 5:
                    saveToFile();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (choice != 5);
        scanner.close();
    }

    private static void addUser(Scanner scanner) {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();

        // Check for duplicate user
        for (User user : users) {
            if (user.getName().equals(name)) {
                System.out.println("User already exists.");
                return;
            }
        }

        users.add(new User(name));
        System.out.println("User added.");
    }

    private static void addTask(Scanner scanner) {
        User user = selectUser(scanner);
        if (user != null) {
            System.out.print("Enter task description: ");
            String description = scanner.nextLine();

            System.out.print("Enter task priority (1: High, 2: Medium, 3: Low): ");
            int priority = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (priority < 1 || priority > 3) {
                System.out.println("Invalid priority. Please enter 1, 2, or 3.");
                return;
            }

            user.addTask(description, priority);
            System.out.println("Task added.");
        }
    }

    private static void markTaskCompleted(Scanner scanner) {
        User user = selectUser(scanner);
        if (user != null) {
            System.out.print("Enter task description to mark as completed: ");
            String description = scanner.nextLine();
            if (description.isEmpty()) {
                System.out.println("Task description cannot be empty.");
                return;
            }

            user.markTaskCompleted(description);
        }
    }

    private static void viewTasks(Scanner scanner) {
        User user = selectUser(scanner);
        if (user != null) {
            user.printTasks();
        }
    }

    private static User selectUser(Scanner scanner) {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        System.out.println("User not found.");
        return null;
    }

    public static void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (User user : users) {
                writer.write(user.getName() + "\n");
                user.getTaskList().writeTasks(writer);
                writer.write("END\n"); // Mark end of user's tasks
            }
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving data.");
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
            System.out.println("Data loaded successfully.");
        } catch (IOException e) {
            System.out.println("No previous data found or an error occurred while loading data.");
        }
    }
}