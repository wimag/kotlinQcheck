package context;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Lambda;

/**
 * Created by Mark on 20.04.2016.
 */
public class LambdaContext<T> extends Context {
    //TODO specify T constraints
    private final T testFunction;
    private final Method verifyMethod;
    private final Object[] params;

    public LambdaContext(Method verifyMethod, T testMethod, int trials,
                           boolean shrink, int shrinks, int maxShrinkDeptth,
                           int maxShrinkTime, Object... params) {
        super(trials, shrinks, maxShrinkDeptth, shrink, maxShrinkTime);
        this.testFunction = testMethod;
        this.verifyMethod = verifyMethod;
        this.params = params;
    }

    public T getTestFunction() {
        return testFunction;
    }

    public Method getVerifyMethod() {
        return verifyMethod;
    }

    public Object[] getParams() {
        return params;
    }

    public Method getTestMethod() {
        if(testFunction instanceof Lambda){
            for(Method method: testFunction.getClass().getMethods()){
                Object t = new Object();
                //TODO - do something better
                if(method.getName().equals("invoke") && Modifier.isFinal(method.getModifiers())){
                    method.setAccessible(true);
                    return method;
                }
            }
        }else if(testFunction instanceof FunctionReference) {

        }else{
            //TODO -  throw exception
            System.out.println("Please pass lambda or function");
        }
        return null;
    }

    public boolean isLambda() {
        return testFunction instanceof Lambda;
    }

    public Lambda getLambda() {
        if(isLambda()){
            return (Lambda)testFunction;
        }
        // Mb throw exception?
        return null;
    }
}
