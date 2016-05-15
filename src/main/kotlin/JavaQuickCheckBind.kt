import annotations.QGen
import annotations.QTest
import annotations.QtestRunner
import com.pholser.junit.quickcheck.From
import com.pholser.junit.quickcheck.generator.InRange
import generators.QGenerator
import hooks.checkAll

fun isShort(@From(name="generateTwo")x: Int) = x < 1500

@QGen fun generateTwo() : Int{
    return 2;
}

@QTest fun test(){
    val lambda = { x: String -> x.length < 10 }
    checkAll(::isShort)
}

fun main(args: Array<String>) {
    QtestRunner.run()
}