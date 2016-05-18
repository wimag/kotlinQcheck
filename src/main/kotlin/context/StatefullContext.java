package context;

import Utils.MethodNameHelper;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.internal.generator.GeneratorRepository;
import generators.CommandSequenceGenerator;
import statefull.StateExecutor;
import statefull.StateMachine;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by Mark on 16.05.2016.
 */
public class StatefullContext<S, T> extends Context {
    private final StateExecutor<S, T> executor;
    private final Method verifyMethod;

    public StatefullContext(Method verifyMethod, StateExecutor<S, T> statemachine, int trials,
                            boolean shrink, int shrinks, int maxShrinkDeptth,
                            int maxShrinkTime) {
        super(trials, shrinks, maxShrinkDeptth, shrink, maxShrinkTime);
        this.verifyMethod = verifyMethod;
        this.executor = statemachine;
    }

    @Override
    public Object getTestFunction() {
        throw new UnsupportedOperationException("Cant call getTestFunction on StatefullContext");
    }

    @Override
    public Method getVerifyMethod() {
        return verifyMethod;
    }

    @Override
    public Object[] getArguments() {
        return new Object[]{this};
    }

    @Override
    public Method getTestMethod() {
        return MethodNameHelper.getMethod("eval", StateExecutor.class);
    }

    @Override
    public Parameter[] getParameters() {
        if(executor.isRandomStateMachineExecutor()) {
            Parameter[] baseArgs = super.getParameters();
            Parameter[] args = new Parameter[baseArgs.length + 1];
            System.arraycopy(baseArgs, 0, args, 1, baseArgs.length);
            args[0] = executor.getTargetParameterTypes()[0];
            return args;
        }
        return super.getParameters();

    }

    @Override
    public void initializeRepo(GeneratorRepository repo) {
        Generator gen = repo.generatorForName("CommandSequenceGenerator");
        if(gen instanceof CommandSequenceGenerator){
            ((CommandSequenceGenerator) gen).configure(executor.sampleLength(), executor.getNumberOfCommands());
        }
    }

    public StateExecutor<S, T> getExecutor(){
        return executor;
    }
}
