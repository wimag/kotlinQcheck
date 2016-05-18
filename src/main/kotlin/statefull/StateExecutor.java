package statefull;

import Utils.FunctionHelper;

import java.lang.reflect.Parameter;
import java.util.List;

/**
 * Created by Mark on 17.05.2016.
 */
public class StateExecutor<S, T> {
    private final StateMachine<S, T> stateMachine;
    private S state;
    private T target;

    public StateExecutor(StateMachine<S, T> stateMachine) {
        this.stateMachine = stateMachine;
    }

    public void eval(CommandSequence sequence){
    }

    public void initialize(T target){
        if(stateMachine.isRandomStateMachine()){
            this.target = target;
            state = FunctionHelper.invoke(stateMachine.getStateGen(), target);
        }else{
            state = stateMachine.getInitState();
            this.target = stateMachine.getTarget();
        }
    }
    public int sampleLength(){
        return stateMachine.getSequenceLength();
    }

    public int getNumberOfCommands(){
        return stateMachine.getNumberOfCommands();
    }

    public boolean isRandomStateMachineExecutor(){
        return stateMachine.isRandomStateMachine();
    }

    public Parameter[] getTargetParameterTypes() throws NullPointerException{
        if(!isRandomStateMachineExecutor()){
            return null;
        }
        return FunctionHelper.getMethod(stateMachine.getStateGen()).getParameters();
    }

    /**
     * Verify parametrs given by context
     * @param params - array of object. Contains either
     *               one element of type CommandSequence
     *               or two elements - initial Target and
     *               CommandSequence
     */
    @SuppressWarnings("unchecked")
    public void verify(Object[] params){
        if(params.length == 2){
            initialize((T)params[0]);
        }else{
            initialize(null);
        }
        CommandSequence sequence = (CommandSequence)params[params.length-1];
        for(int commandPos: sequence.getCommands()){
            List<Command<S,T,?>> executableCommands = stateMachine.getExecutableCommands(state);
            if(executableCommands.isEmpty()){
                //TODO- exception class
                throw new AssertionError("WOLOLOLOLO");
            }
            Command<S, T, ?> command = executableCommands.get(commandPos % executableCommands.size());
            applyCommand(command);
        }
    }

    private <R> void applyCommand(Command<S, T, R> command, Object... args){
        state = command.applyState(state, target);
        //TODO - get random arguments in function
        R res = command.applyTarget(target);
        org.junit.Assert.assertTrue(command.evalResults(state, res));
        org.junit.Assert.assertTrue(command.evalPostCondition(state, target));

    }
}
