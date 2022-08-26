import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Application {
    private TaskList taskList = new TaskList();
    boolean active = true;

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (active) {
            printResponse("please choose from these commands: add | delete | mark | unmark | list | bye");
            String input = sc.nextLine().strip().toUpperCase();
            switch (input) {
                case "LIST":
                    list();
                    break;
                case "MARK":
                    mark(sc);
                    break;
                case "UNMARK":
                    unmark(sc);
                    break;
                case "ADD":
                    add(sc);
                    break;
                case "DELETE":
                    delete(sc);
                    break;
                case "BYE":
                    bye();
                    active = false;
                    sc.close();
                    break;
                default:
                    handleInvalidCommand();
            }
        }
    }

    private void list() {
        printResponse(taskList.toString());
    }

    private void mark(Scanner sc) {
        printResponse("which task would you like to mark as done? (enter its number in the list!)");
        int index = Integer.valueOf(sc.nextLine()) - 1;
        String doneTask = taskList.markAsDone(index);
        String response = "Task marked as done:\n" + doneTask;
        printResponse(response);
        // todo: handle missing/invalid index
    }

    private void unmark(Scanner sc) {
        printResponse("which task would you like to mark as undone? (enter its number in the list!)");
        int index = Integer.valueOf(sc.nextLine()) - 1;
        String doneTask = taskList.markAsUndone(index);
        String response = "Task marked as undone:\n" + doneTask;
        printResponse(response);
        // todo: handle missing/invalid index
    }

    private void delete(Scanner sc) {
        printResponse("which task would you like to delete? (enter its number in the list!)");
        int index = Integer.valueOf(sc.nextLine()) - 1;
        String taskToDelete = taskList.delete(index);
        String response = "I've removed this task:\n" + taskToDelete;
        printResponse(response);
        // todo: handle missing/invalid index
    }

    private void add(Scanner sc) {
        // add task to TaskList
        Task newTask = null;
        TaskType taskType = getTaskType(sc);
        String description = getTaskDescription(sc);
        switch (taskType) {
            case TODO:
                newTask = new Todo(description);
                break;
            case DEADLINE:
                printResponse("deadline (dd/mm/yy):");
                String deadlineString = sc.nextLine();
                LocalDate deadline = LocalDate.parse(deadlineString, DateTimeFormatter.ofPattern("dd/MM/yy"));
                newTask = new Deadline(description, deadline);
                break;
            case EVENT:
                printResponse("event date (dd/mm/yy):");
                String eventTimeString = sc.nextLine();
                LocalDate eventTime = LocalDate.parse(eventTimeString, DateTimeFormatter.ofPattern("dd/MM/yy"));
                newTask = new Event(description, eventTime);
                break;
        }
        taskList.add(newTask);
        printResponse("added: " + newTask);
    }

    private void bye() {
        printResponse("bye...");
    }

    private void handleInvalidCommand() {
        printResponse("I'm sorry, but I don't know what that means :)\n"
                + "Please choose from: add | mark | unmark | list | bye");
    }

    private TaskType getTaskType(Scanner sc) {
        printResponse("Please choose one: todo | deadline | event");
        String taskType = sc.nextLine().strip().toUpperCase();
        try {
            return TaskType.valueOf(taskType);
        } catch (IllegalArgumentException e) {
            // keep trying until valid TaskType provided by user
            return getTaskType(sc);
        }
    }

    private String getTaskDescription(Scanner sc) {
        printResponse("description:");
        String description = "";
        while (description.isEmpty()) {
            description = sc.nextLine().strip();
            if (description.isEmpty()) {
                printResponse("Please enter a non-blank description:");
            }
        }
        return description;
    }

    private void printResponse(String input) {
        // String indentedInput = input.replaceAll("(?m)^", "\t");
        // System.out.println("\t----------------------------------");
        System.out.println("----------------------------------");
        // System.out.println(indentedInput);
        System.out.println(input);
        System.out.println("----------------------------------");
        // System.out.println("\t----------------------------------");
    }
}
