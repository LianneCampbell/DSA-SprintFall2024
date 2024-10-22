import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskList {
    private Node head;

    private class Node {
        Task task;
        Node next;

        Node(Task task) {
            this.task = task;
            this.next = null;
        }
    }

    public TaskList() {
        this.head = null;
    }

    public void addTask(String description, int priority) {
        Task newTask = new Task(description, priority);
        Node newNode = new Node(newTask);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void markTaskCompleted(String description) {
        Node current = head;
        while (current != null) {
            if (current.task.getDescription().equals(description)) {
                current.task.markCompleted();
                return;
            }
            current = current.next;
        }
        System.out.println("Task not found.");
    }

    public void printTasksByPriority() {
        List<Task> tasks = new ArrayList<>();
        Node current = head;
        while (current != null) {
            tasks.add(current.task);
            current = current.next;
        }

        // Sort by priority (higher priority first)
        tasks.sort(Comparator.comparingInt(Task::getPriority));

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void writeTasks(BufferedWriter writer) throws IOException {
        Node current = head;
        while (current != null) {
            writer.write(current.task.getDescription() + "," + current.task.isCompleted() + ","
                    + current.task.getPriority() + "\n");
            current = current.next;
        }
    }
}