package duke.storage;

import duke.task.Task;

import duke.tasklist.TaskList;

import duke.ui.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;

/**
 * Handles the interactions with the user's CSV file.
 * Includes creating, updating file and retrieving list of tasks from CSV file.
 */
public class Storage {

    private final String dataDir;
    private final String filePath;
    private final Ui ui;

    /**
     * Initializes the storage object and create a new file.
     */
    public Storage() {
        ui = new Ui();
        dataDir = System.getProperty("user.dir") + "/data";
        filePath = dataDir + "/tasklist.csv";
        createFile();
    }

    private void createFile() {
        try {
            File newDirectory = new File(dataDir);
            newDirectory.mkdir();
            File newFile = new File(filePath);
            newFile.createNewFile();
        } catch (IOException e) {
            ui.fileCreationError();
        }
    }

    /**
     * Gets the list of tasks from the tasklist CSV file, if any.
     *
     * @return List of tasks.
     */
    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String header = br.readLine();
            if (header != null) {
                String line = br.readLine();
                while (line != null) {
                    Task newTask = CSVConverter.parseToTask(line, ui);
                    if (newTask != null) {
                        taskList.add(newTask);
                    }
                    line = br.readLine();
                }
            }
            return taskList;
        } catch (IOException e) {
            ui.fileReadingError();
            return taskList;
        }
    }

    /**
     * Updates the task list.
     *
     * @param tasks Task list.
     */
    public void update(TaskList tasks) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            String header = "Task type  ,Description  ,Time  ,Status\n";
            StringBuilder stringBuilder = new StringBuilder(header);
            for (Task task : tasks.getTasks()) {
                stringBuilder.append(convertToCSVFormat(task));
            }
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();
        } catch (IOException e) {
            ui.fileUpdateError();
        }
    }

    private String convertToCSVFormat(Task task) {
        return String.format("%s  ,%s  ,%s  ,%s\n",
                task.getTaskName(), task.getDescription(), task.getTime(), task.getStatus());
    }
}
