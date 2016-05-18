package hooks

import Runners.TestStorage
import Utils.MethodNameHelper
import com.pholser.junit.quickcheck.Property
import context.Context
import context.FunctionContext
import context.LambdaContext
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod


/**
 * Created by Mark on 04.04.2016.
 */

@Property fun <T : Function<*>?> verboseAllProxy(context: Context<T>,vararg params : Any) {
    println(params)
    org.junit.Assert.assertTrue(context.testMethod(context.testFunction, *params) as Boolean)
}


fun verboseAll(func: KFunction<*>?, trials: Int = 100, shrink: Boolean = true, shrinks : Int = 100, maxShrinkDepth : Int = 20,
             maxShrinkTime : Int = 60000) {
    val checker = MethodNameHelper.getMethod("verboseAllProxy")
    TestStorage.getInstance().addTest(FunctionContext(checker, func!!.javaMethod, trials, shrink, shrinks, maxShrinkDepth, maxShrinkTime))
}


fun verboseAll(func: Function<*>?, trials: Int = 100, shrink: Boolean = true, shrinks : Int = 100, maxShrinkDepth : Int = 20,
                    maxShrinkTime : Int = 60000) {
    val checker = MethodNameHelper.getMethod("verboseAllProxy")
    TestStorage.getInstance().addTest(LambdaContext(checker, func, trials, shrink, shrinks, maxShrinkDepth, maxShrinkTime))
}



