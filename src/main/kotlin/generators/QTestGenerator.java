package generators;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import kotlin.reflect.KFunction;

/**
 * Created by Mark on 07.05.2016.
 */
public class QTestGenerator extends Generator{
    private final KFunction<Object> function;
    public QTestGenerator(KFunction<Object> function) {
        super(function.getReturnType().getClass());
        this.function = function;
    }

    @Override
    public Object generate(SourceOfRandomness random, GenerationStatus status) {
        if(function.getParameters().size() == 2){
            return function.call(random, status);
        }
        return function.call();
    }
}
