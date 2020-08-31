package duke.command;

import duke.exception.FileUpdateFailException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * Handles the addition of tasks into the Task List.
 */
public abstract class AddCommand extends Command {

    private final String taskDetails;

    /**
     * Initializes the add command with the task details.
     *
     * @param taskDetails Task details.
     */
    protected AddCommand(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    /**
     * Retrieves the task details of this complex task.
     *
     * @return Task details.
     */
    protected String getTaskDetails() {
        return taskDetails;
    }

    /**
     * Adds the new task into the Task List.
     *
     * @param newTask New task to be added.
     * @param tasks Task List object.
     * @param ui User Interface object.
     * @param storage Storage object.
     */
    protected String addTask(Task newTask, TaskList tasks, Ui ui, Storage storage) throws FileUpdateFailException {
        tasks.add(newTask);
        storage.update(tasks);
        return ui.addTask(newTask, tasks.size());
    }
}
