package hooks

import Runners.TestStorage
import Utils.MethodNameHelper
import com.pholser.junit.quickcheck.Property
import context.StatefullContext
import statefull.StateExecutor
import statefull.StateMachine

/**
 * Created by Mark on 17.05.2016.
 */

@Property fun checkState(context: StatefullContext<*, *>, vararg params: Any) {
    context.executor.verify(params)
}

fun <S, T> runState(sm: StateMachine<S, T>, trials: Int = 100, shrink: Boolean = true, shrinks: Int = 100, maxShrinkDepth: Int = 20,
                    maxShrinkTime: Int = 60000){
    val checker = MethodNameHelper.getMethod("checkState")
    TestStorage.getInstance().addTest(StatefullContext(checker, StateExecutor(sm), trials, shrink, shrinks, maxShrinkDepth, maxShrinkTime))
}
