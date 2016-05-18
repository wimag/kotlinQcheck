package statefull;

/**
 * Created by Mark on 17.05.2016.
 */
public class CommandSequence {
    private final int[] commands;

    public CommandSequence(int[] commands) {
        this.commands = commands;
    }

    public int[] getCommands() {
        return commands;
    }
}
