package statefull;


import Utils.FunctionHelper;
import kotlin.Function;

/**
 * Created by Mark on 17.05.2016.
 * this class represents single command of
 */
public class Command<S, T, R> {
    private final Function<S> transformState;
    private final Function<R> transformTarget;
    private final Function<Boolean> preCondition;
    private final Function<Boolean> postCondition;
    private final Function<Boolean> resultCondition;
    private final String name;

    public Command(Function<S> transformState, Function<R> transformTarget, Function<Boolean> preCondition,
                   Function<Boolean> postCondition, Function<Boolean> resultCondition, String name) {
        this.transformState = transformState;
        this.transformTarget = transformTarget;
        this.preCondition = preCondition;
        this.postCondition = postCondition;
        this.resultCondition = resultCondition;
        this.name = name;
    }


    /**
     * Apply command to test target
     *
     * @param testObject - test target
     * @param args       - optional params
     */
    public R applyTarget(T testObject, Object... args){
        Object[] temp = new Object[args.length + 1];
        System.arraycopy(args, 0, temp, 1, args.length);
        temp[0] = testObject;
        return FunctionHelper.invoke(transformTarget,temp);
    }

    /**
     * Compute state condition. if  <b>transformState</b>
     * takes one argument - state is passed
     *
     * @param state      -  state before applying command
     * @param testObject - object <b>before</b> applying
     *                   command
     * @return new value of state
     */
    public S applyState(S state, T testObject){
        if(FunctionHelper.getArity(transformState) == 1){
            return FunctionHelper.invoke(transformState, state);
        }
        return FunctionHelper.invoke(transformState, state, testObject);
    }

    /**
     * Check that postcondition is satisfied.
     * condition that should be satisfied before command application
     *
     * @param state      - state <b>after</b> applying command
     * @return - weather the condition was satisfied
     */
    public boolean evalPreCondition(S state){
        return FunctionHelper.invoke(preCondition, state);
    }

    /**
     * Check that postcondition is satisfied.
     * condition that should be satisfied after every command application
     *
     * @param state      - state <b>after</b> applying command
     * @param testObject - object <b>after</b> applying command
     * @return - weather the condition was satisfied
     */
    public boolean evalPostCondition(S state, T testObject){
        return FunctionHelper.invoke(postCondition, state, testObject);
    }

    /**
     * Check, that the value returned by command evaluation
     * satisfies state <b>adter</b> transition
     *
     * @param state      - state <b>after</b> applying command
     * @param result - test subject
     * @return - weather the condition was satisfied
     */
    public boolean evalResults(S state, R result){
        return FunctionHelper.invoke(resultCondition, state, result);
    }
}
