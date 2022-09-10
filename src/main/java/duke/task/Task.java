package duke.task;

public abstract class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        isDone = false;
    }

    public Task(boolean isDone, String description) {
        this.description = description;
        this.isDone = isDone;
    }

    public boolean descriptionContains(String keyword) {
        return description.contains(keyword);
    }

    public void markAsDone() {
        isDone = true;
        assert isDone == true : "Task should be marked as done.";
    }

    public void markAsUndone() {
        isDone = false;
    }

    @Override
    public String toString() {
        String checkBox = isDone ? "X" : " ";
        return "[" + checkBox + "] " + description;
    }

    public String toDbString() {
        String binaryIsDone = isDone ? "1" : "0";
        return binaryIsDone + " | " + description;
    }
}
