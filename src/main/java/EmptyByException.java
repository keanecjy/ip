public class EmptyByException extends DukeException {
    public EmptyByException(TaskType complexTask) {
        super(String.format("Deadline / time of %s is not specified", complexTask));
    }
}
