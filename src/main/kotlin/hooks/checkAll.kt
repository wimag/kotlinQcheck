package hooks

import Runners.QuickCheckBuilder
import Runners.QuickCheckRunner
import Utils.MethodNameHelper
import Utils.QuickCheckContext
import com.oracle.webservices.internal.api.message.PropertySet
import com.pholser.junit.quickcheck.Property
import org.junit.Assert
import org.junit.runner.RunWith
import org.junit.runner.JUnitCore
import java.lang.reflect.Method
import kotlin.reflect.jvm.kotlinFunction

/**
 * Created by Mark on 04.04.2016.
 */

@Property fun <T : Function<*>?> t2(context: QuickCheckContext<T>, params: String) {
    org.junit.Assert.assertTrue(context.testMethod(context.testFunction, params) as Boolean)
}


//TODO pass function type
fun t4(s: String) : String {
    return s
}

fun checkAll() {
    //TODO SAM construction
    QuickCheckBuilder.addTest(QuickCheckContext((MethodNameHelper.getMethod("t2", QuickCheckContext::class.java, String::class.java)), { x: String -> x.length < 10 }))

}



