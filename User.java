public class User {
    private String name;
    private TaskList taskList;

    public User(String name) {
        this.name = name;
        this.taskList = new TaskList();
    }

    public String getName() {
        return name;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void addTask(String description, int priority) {
        taskList.addTask(description, priority);
    }

    public void markTaskCompleted(String description) {
        taskList.markTaskCompleted(description);
    }

    public void deleteTask(String description) {
        taskList.deleteTask(description);
    }

    public void printTasks() {
        System.out.println("\n==================== To-Do List ====================");
        System.out.println("");
        System.out.println(name + "'s To-Do List (sorted by priority):");
        System.out.println("");
        taskList.printTasksByPriority();
    }
}