package statefull

import hooks.runState
import java.util.*

/**
 * Created by Mark on 16.05.2016.
 * Class represents statefull check machine.
 * S - represents type of initial state
 * T - represents type of tested object
 */
class StateMachine<S, T> {
    //TODO - state holder
    val initState: S?
    val target: T?
    val stateGen: (T)->(S)
    val sequenceLength : Int
    constructor(state : S, target: T, sequenceLength: Int = 100){
        this.initState = state
        this.target = target
        this.sequenceLength = sequenceLength
        this.stateGen = {t: T -> initState}
    }

    constructor(stateGen: (T)->(S), sequenceLength: Int = 100){
        this.initState = null
        this.target = null
        this.sequenceLength = sequenceLength
        this.stateGen = stateGen
    }


    private val commands = ArrayList<Command<S, T, *>>()

    fun isRandomStateMachine(): Boolean{
        return initState == null
    }
    fun getNumberOfCommands(): Int{
        return commands.size
    }

    fun getExecutableCommands(s: S) : List<Command<S, T, *>>{
        return commands.filter({c: Command<S, T, *> -> c.evalPreCondition(s)})
    }

    /**
     * @param command - register command for state machine
     */
    fun <R> addCommand(transformState: (S) -> (S), transformTarget: Function<R>,
                       precondition: (S)->(Boolean) = {s: S -> true},
                       postcondition: (S, T)->(Boolean) = {s: S, t: T -> true},
                       resultCondition: (S, R)->(Boolean) = {s: S, r: R -> true},
                       name : String = "N/A") {
        commands.add(Command<S, T, R>(transformState, transformTarget, precondition, postcondition, resultCondition, name))
    }

    fun execute(trials: Int = 100){
        runState(this, trials = trials)
    }
}
