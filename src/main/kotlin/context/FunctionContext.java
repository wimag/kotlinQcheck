package context;

import java.lang.reflect.Method;

/**
 * Created by Mark on 20.04.2016.
 */
public class FunctionContext<T> extends Context {
    //TODO specify T constraints
    private final T testFunction;
    private final Method verifyMethod;

    public FunctionContext(Method verifyMethod, T testMethod, int trials,
                           boolean shrink, int shrinks, int maxShrinkDeptth,
                           int maxShrinkTime) {
        super(trials, shrinks, maxShrinkDeptth, shrink, maxShrinkTime);
        this.verifyMethod = verifyMethod;
        this.testFunction = testMethod;
    }

    @Override
    public T getTestFunction() {
        return testFunction;
    }

    @Override
    public Method getVerifyMethod() {
        return verifyMethod;
    }

    @Override
    public Method getTestMethod() {
        return (Method)testFunction;
    }

}
