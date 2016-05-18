package generators;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Mark on 07.05.2016.
 */
public class QGenerator extends Generator{
    //TODO - pass generator through lambdas
    private final Method method;
    private final String name;
    public QGenerator(Method method, String name) {
        super(method.getReturnType());
        this.method = method;
        this.name = name;
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

    @Override
    public Generator<?> getCopy() {
        return new QGenerator(method, name);
    }

    @Override
    public String getName() {
        return name;
    }
}
