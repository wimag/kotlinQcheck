package generators;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import statefull.CommandSequence;

import java.lang.reflect.AnnotatedType;
import java.util.List;

/**
 * Created by Mark on 17.05.2016.
 * Simplier version of ArrayGenerator from juni-quickcheck
 * But this one is copyable
 */
public class CommandSequenceGenerator extends Generator<CommandSequence> {
    private int sampleSize = 100;
    private int numberOfCommands = 100;

    public CommandSequenceGenerator(){
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
        for(int i = 0; i < sampleSize; i++){
            commands[i] = random.nextInt(numberOfCommands);
        }
        return new CommandSequence(commands);
    }

    public void configure(int sampleSize, int numberOfCommands){
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
}
