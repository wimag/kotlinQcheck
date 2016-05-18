package statefull;

import Utils.FunctionHelper;
import com.pholser.junit.quickcheck.internal.generator.GeneratorRepository;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mark on 17.05.2016.
 */
public class StateExecutor<S, T> {
    private final StateMachine<S, T> stateMachine;
    private S state;
    private T target;
    private GeneratorRepository repo = null;
    private List<CommandContext<S,T,?>> executedCommands = new ArrayList<>();

    public StateExecutor(StateMachine<S, T> stateMachine) {
        this.stateMachine = stateMachine;
    }

    public void initialize(T target){
        executedCommands = new ArrayList<>();
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

    public void setRepo(GeneratorRepository repo) {
        this.repo = repo;
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
        for(int i = 0;i < sequence.getCommands().length; i++){
            int commandPos = sequence.getCommands()[i];
            List<Command<S,T,?>> executableCommands = stateMachine.getExecutableCommands(state);
            if(executableCommands.isEmpty()){
                throw new AssertionError(String.format("Trap state reached after sequence [%s]",
                        formatCommandSequence(sequence)));
            }
            Command<S, T, ?> command = executableCommands.get(commandPos % executableCommands.size());
            Object[] args = sequence.getArgumentsForCommand(command, repo);
            executedCommands.add(new CommandContext(command, args));
            if(!applyResults(command, args)){
                throw new AssertionError(String.format("Value returned by function doesn't satisfy" +
                        " resultCondition for sequence[%s]",
                        formatCommandSequence(sequence)));
            }
            if(!command.evalPostCondition(state, target)){
                throw new AssertionError(String.format("Postcondition not satisfied for sequence [%s]",
                        formatCommandSequence(sequence)));
            }

        }
    }

    public void eval(CommandSequence sequence){}

    private <R> boolean applyResults(Command<S, T, R> command, Object... args){
        state = command.applyState(state, target, args);
        R res = command.applyTarget(target, args);
        return command.evalResults(state, res);
    }

    private String  formatCommandSequence(CommandSequence sequence){
        List<String> entries = new ArrayList<>();
        for(CommandContext command: executedCommands){
            entries.add(command.toString());
        }
        return String.join(",", entries);
    }

    private static class CommandContext<S, T, R>{
        private final Command<S, T, R> command;
        private final Object[] argumetns;

        public CommandContext(Command<S, T, R> command, Object[] argumetns) {
            this.command = command;
            this.argumetns = argumetns;
        }

        @Override
        public String toString() {
            String params = String.join(",", Arrays.stream(argumetns).
                    map(Object::toString).collect(Collectors.toList()));
            return command.getName() + "(" + params + ")";
        }
    }
}
