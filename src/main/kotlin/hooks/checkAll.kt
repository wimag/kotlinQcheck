package hooks

import Runners.QuickCheckBuilder
import Runners.QuickCheckRunner
import Utils.MethodNameHelper
import com.oracle.webservices.internal.api.message.PropertySet
import com.pholser.junit.quickcheck.Property
import org.junit.Assert
import org.junit.runner.RunWith
import org.junit.runner.JUnitCore
/**
 * Created by Mark on 04.04.2016.
 */

@Property fun t2(x : String) {
    println("I am here: " + x)
}

fun checkAll() {
    QuickCheckBuilder.tests.add(MethodNameHelper.getMethod("t2"))
    JUnitCore.runClasses(QuickCheckBuilder::class.java)
}



