package duke.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import duke.exception.FileUpdateFailException;
import duke.task.Task;
import duke.task.ToDo;
import duke.tasklist.TaskList;

public class StorageTest {

    private final Storage storage = new Storage();

    @Test
    public void testTaskUpdate() {
        try {
            TaskList taskList = new TaskList(new ArrayList<>());
            Task task1 = new ToDo("cs2103t");
            Task task2 = new ToDo("tutorial");
            Task task3 = new ToDo("test");
            taskList.add(task1);
            taskList.add(task2);
            taskList.add(task3);
            storage.update(taskList);
            ArrayList<Task> tasks = storage.getTasks();
            assertEquals(3, tasks.size());
            assertEquals(task1.getDescription(), tasks.get(0).getDescription());
            assertEquals(task2.getDescription(), tasks.get(1).getDescription());
            assertEquals(task3.getDescription(), tasks.get(2).getDescription());
            for (Task task : tasks) {
                assertEquals("TODO", task.getTaskName());
                assertEquals("-", task.getTime());
            }
        } catch (FileUpdateFailException e) {
            fail();
        }
    }
    @Test
    public void testEmptyTasks() {
        TaskList emptyTaskList = new TaskList(new ArrayList<>());
        try {
            storage.update(emptyTaskList);
            ArrayList<Task> emptyTasks = storage.getTasks();
            assertTrue(emptyTasks.isEmpty());
        } catch (FileUpdateFailException e) {
            fail();
        }
    }
}