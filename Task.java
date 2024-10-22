public class Task {
    private String description;
    private boolean isCompleted;
    private int priority; // 1 (High), 2 (Medium), 3 (Low)

    public Task(String description, int priority) {
        this.description = description;
        this.isCompleted = false;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public int getPriority() {
        return priority;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return description + " (Priority: " + getPriorityLabel() + ", Completed: " + (isCompleted ? "Yes" : "No") + ")";
    }

    private String getPriorityLabel() {
        switch (priority) {
            case 1:
                return "High";
            case 2:
                return "Medium";
            case 3:
                return "Low";
            default:
                return "Unknown";
        }
    }
}