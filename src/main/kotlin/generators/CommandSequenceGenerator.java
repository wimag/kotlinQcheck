package generators;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import statefull.CommandSequence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 17.05.2016.
 * Simplier version of ArrayGenerator from juni-quickcheck
 * But this one is copyable
 */
public class CommandSequenceGenerator extends Generator<CommandSequence> {
    private int sampleSize = 100;
    private int numberOfCommands = 100;

    public CommandSequenceGenerator() {
        this(CommandSequence.class);
    }

    protected CommandSequenceGenerator(Class<CommandSequence> type) {
        super(type);
    }

    protected CommandSequenceGenerator(List<Class<CommandSequence>> types) {
        super(types);
    }

    @Override
    public CommandSequence generate(SourceOfRandomness random, GenerationStatus status) {
        int commands[] = new int[sampleSize];
        for (int i = 0; i < sampleSize; i++) {
            commands[i] = random.nextInt(numberOfCommands);
        }
        return new CommandSequence(commands, random);
    }

    public void configure(int sampleSize, int numberOfCommands) {
        this.sampleSize = sampleSize;
        this.numberOfCommands = numberOfCommands;
    }

    @Override
    public Generator<?> getCopy() {
        CommandSequenceGenerator copy = new CommandSequenceGenerator();
        copy.configure(sampleSize, numberOfCommands);
        return copy;
    }

    @Override
    public String getName() {
        return "CommandSequenceGenerator";
    }

    @Override
    public List<CommandSequence> doShrink(SourceOfRandomness random, CommandSequence larger) {
        int[] oldCommands = larger.getCommands();
        int numberOfShrinks = 5;
        ArrayList<CommandSequence> result = new ArrayList<>();
        for (int i = 0; i < numberOfShrinks; i++) {
            result.add(new CommandSequence(getSubsequence(oldCommands, random), larger.getRandom()));
        }
        return result;
    }

    private int[] getSubsequence(int commands[], SourceOfRandomness random) {
        int size = random.nextInt(commands.length/2, commands.length);
        int res[] = new int[size];
        int pos = size-1;
        for (int i = commands.length - 1; (pos >= 0) && (i > pos); i--) {
            if (random.nextBoolean()) {
                res[pos] = commands[i];
                pos--;
            }
        }
        System.arraycopy(commands, 0, res, 0, pos);
        return res;

    }
}
