package generators;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import kotlin.reflect.KFunction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Mark on 07.05.2016.
 */
public class QGenerator extends Generator{
    private final Method method;
    public QGenerator(Method method) {
        super(method.getReturnType());
        this.method = method;
    }

    @Override
    public Object generate(SourceOfRandomness random, GenerationStatus status) {
        try {
            if(method.getParameters().length == 2){
                return method.invoke(null, random, status);
            }
            return method.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Couldn'n invoke generator method");
            e.printStackTrace();
        }
        return null;
    }
}
