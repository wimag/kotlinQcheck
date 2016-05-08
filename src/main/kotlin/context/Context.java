package context;

import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Lambda;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Mark on 06.05.2016.
 */
public abstract class Context<T> {
    private final int trials;
    private final int shrinks;
    private final int maxDepth;
    private final boolean shrink;
    private final int maxShrinkTime;

    public Context(int trials, int shrinks, int maxDepth, boolean shrink, int maxShrinkTime) {
        this.trials = trials;
        this.shrinks = shrinks;
        this.maxDepth = maxDepth;
        this.shrink = shrink;
        this.maxShrinkTime = maxShrinkTime;
    }

    public abstract T getTestFunction();

    public abstract Method getVerifyMethod();

    public abstract Object[] getParams();

    public abstract Method getTestMethod();

    public int trials(){
        return trials;
    }

    public boolean shrink(){
        return shrink;
    }

    public int maxShrinks(){
        return shrinks;
    }

    public int maxShrinkDepth(){
        return maxDepth;
    }

    public int maxShrinkTime(){
        return maxShrinkTime;
    }
}
