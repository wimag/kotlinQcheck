package context;

import Utils.FunctionHelper;
import kotlin.Function;
import kotlin.jvm.internal.Lambda;

import java.lang.reflect.Method;

/**
 * Created by Mark on 20.04.2016.
 */
public class LambdaContext<T extends Function> extends Context {
    //TODO specify T constraints
    private final T testFunction;
    private final Method verifyMethod;

    public LambdaContext(Method verifyMethod, T testMethod, int trials,
                           boolean shrink, int shrinks, int maxShrinkDeptth,
                           int maxShrinkTime) {
        super(trials, shrinks, maxShrinkDeptth, shrink, maxShrinkTime);
        this.testFunction = testMethod;
        this.verifyMethod = verifyMethod;
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
        return FunctionHelper.getMethod(testFunction);
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
