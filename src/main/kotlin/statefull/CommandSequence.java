package statefull;

import com.pholser.junit.quickcheck.internal.ParameterTypeContext;
import com.pholser.junit.quickcheck.internal.generator.GeneratorRepository;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import java.lang.reflect.Parameter;

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

    public <S, T, R> Object[] getArgumentsForCommand(Command<S, T, R> command, GeneratorRepository repo){
        Parameter[] parameters = command.getParameters();
        Object[] res = new Object[parameters.length];
        for(int i = 0; i < parameters.length; i++){
            Parameter parameter = parameters[i];
            res[i] = repo.generatorFor(new ParameterTypeContext(parameter.getName(),
                    parameter.getAnnotatedType(), "N/A")).generate(random, null);
        }
        return res;
    }
}
