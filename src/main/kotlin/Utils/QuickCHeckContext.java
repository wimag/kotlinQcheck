package Utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import kotlin.Function;
import kotlin.jvm.internal.Lambda;

/**
 * Created by Mark on 20.04.2016.
 */
public class QuickCheckContext<T extends  Function> {
    private final T testFunction;
    private final Method verifyMethod;
    private final Object[] params;

    public QuickCheckContext(Method verifyMethod, T testMethod, Object... params) {
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
            for(Method method: ((Lambda) testFunction).getClass().getMethods()){
                Object t = new Object();
                //TODO - do something better
                if(method.getName().equals("invoke") && Modifier.isFinal(method.getModifiers())){
                    return method;
                }
            }
        }else{
            //TODO -  throw exception
            System.out.println("Please pass lambda");
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
