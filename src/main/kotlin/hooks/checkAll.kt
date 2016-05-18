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

@Property fun <T : Function<*>?> checkAllProxy(context: Context<T>, vararg params: Any) {
    if(!(context.testMethod(context.testFunction, *params) as Boolean)){
        System.err.println("Test %s failed with params %s".format(context.name, params.joinToString(",")));
        org.junit.Assert.fail();
    }
}

fun checkAll(func: KFunction<*>?, trials: Int = 100, shrink: Boolean = true, shrinks: Int = 100, maxShrinkDepth: Int = 20,
             maxShrinkTime: Int = 60000, name: String = "Unknown") {
    val checker = MethodNameHelper.getMethod("checkAllProxy")
    TestStorage.getInstance().addTest(FunctionContext(checker, func!!.javaMethod, trials, shrink, shrinks, maxShrinkDepth, maxShrinkTime, name))
}


fun checkAll(func: Function<*>?, trials: Int = 100, shrink: Boolean = true, shrinks: Int = 100, maxShrinkDepth: Int = 20,
             maxShrinkTime: Int = 60000, name: String = "Unknown") {
    val checker = MethodNameHelper.getMethod("checkAllProxy")
    TestStorage.getInstance().addTest(LambdaContext(checker, func, trials, shrink, shrinks, maxShrinkDepth, maxShrinkTime, name))
}


