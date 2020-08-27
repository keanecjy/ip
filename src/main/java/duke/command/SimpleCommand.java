package duke.command;

import duke.exception.DukeException;
import duke.exception.TaskAlreadyDoneException;
import duke.exception.InvalidTaskNumberException;
import duke.exception.InvalidDoneException;
import duke.exception.InvalidDeleteException;

import duke.storage.Storage;

import duke.task.Task;

import duke.tasklist.TaskList;

import duke.ui.Ui;

/**
 * Abstracts the logic of deleting and completing a task.
 */
public class SimpleCommand extends Command {

    private final String input;
    private final SimpleCommandType type;

    public SimpleCommand(String input, SimpleCommandType type) {
        this.input = input;
        this.type = type;
    }

    /**
     * Deletes or complete a task, depending on the task type.
     *
     * @param tasks Task List object.
     * @param ui User Interface object.
     * @param storage Storage object.
     * @throws DukeException If an error is found in the user input.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (checkIfNumber(input)) {
            int digit = Integer.parseInt(input);
            if (tasks.checkIfValid(digit)) {
                Task current = tasks.get(digit - 1);
                if (type == SimpleCommandType.DONE) {
                    if (current.isDone()) {
                        throw new TaskAlreadyDoneException();
                    } else {
                        current.markAsDone();
                        ui.markTaskAsDone(current);
                    }
                } else {
                    tasks.delete(digit - 1);
                    ui.deleteTask(current, tasks.size());
                }
            } else {
                throw new InvalidTaskNumberException(tasks.size());
            }
        } else {
            if (type == SimpleCommandType.DONE) {
                throw new InvalidDoneException();
            } else {
                throw new InvalidDeleteException();
            }
        }
        storage.update(tasks);
    }

    private static boolean checkIfNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
