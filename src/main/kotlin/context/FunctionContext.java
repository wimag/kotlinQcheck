package context;

import java.lang.reflect.Method;

/**
 * Created by Mark on 20.04.2016.
 */
public class FunctionContext<T> extends Context {
    //TODO specify T constraints
    private final T testFunction;
    private final Method verifyMethod;
    private final Object[] params;

    public FunctionContext(Method verifyMethod, T testMethod, int trials,
                           boolean shrink, int shrinks, int maxShrinkDeptth,
                           int maxShrinkTime, Object... params) {
        super(trials, shrinks, maxShrinkDeptth, shrink, maxShrinkTime);
        this.verifyMethod = verifyMethod;
        this.testFunction = testMethod;
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
        return (Method)testFunction;
    }

}
