package statefull;

import com.pholser.junit.quickcheck.random.SourceOfRandomness;

/**
 * Created by Mark on 17.05.2016.
 */
public class CommandSequence {
    private final int[] commands;
    private final SourceOfRandomness random;
    public CommandSequence(int[] commands, SourceOfRandomness random) {
        this.commands = commands;
        this.random = random;
    }

    public int[] getCommands() {
        return commands;
    }

    public SourceOfRandomness getRandom() {
        return random;
    }
}
