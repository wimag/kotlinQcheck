package hooks

import Runners.QuickCheckBuilder
import context.Context
import context.FunctionContext
import Utils.MethodNameHelper
import context.LambdaContext
import com.pholser.junit.quickcheck.Property
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.reflect


/**
 * Created by Mark on 04.04.2016.
 */

@Property fun <T : Function<*>?> checkAllProxy(context: Context<T>, params : Any) {
    org.junit.Assert.assertTrue(context.testMethod(context.testFunction, params) as Boolean)
}


//TODO - recieve func here

fun checkAll(func: KFunction<*>?, trials: Int = 100, shrink: Boolean = true, shrinks : Int = 100, maxShrinkDepth : Int = 20,
             maxShrinkTime : Int = 60000) {
    //TODO SAM construction
    val lambda = { x: String -> x.length < 10 }
    lambda.reflect()!!.parameters
    var checker = MethodNameHelper.getMethod("checkAllProxy", Context::class.java)
    QuickCheckBuilder.addTest(FunctionContext(checker, func!!.javaMethod, trials, shrink, shrinks, maxShrinkDepth, maxShrinkTime))
}


fun checkAll(func: Function<*>?, trials: Int = 100, shrink: Boolean = true, shrinks : Int = 100, maxShrinkDepth : Int = 20,
                    maxShrinkTime : Int = 60000) {
    //TODO SAM construction
    val lambda = { x: String -> x.length < 10 }
    var checker = MethodNameHelper.getMethod("checkAllProxy", Context::class.java)
    QuickCheckBuilder.addTest(LambdaContext(checker, func, trials, shrink, shrinks, maxShrinkDepth, maxShrinkTime))
}



