import annotations.QGen
import annotations.QTest
import annotations.QtestRunner
import com.pholser.junit.quickcheck.generator.InRange
import generators.QGenerator
import hooks.checkAll

fun isShort(@InRange(minInt=1000, maxInt=2000) x: Int) = x < 1500

@QGen fun genI() : Int{
    return 2;
}

@QTest fun test(){
    val lambda = { x: String -> x.length < 10 }
    checkAll(::isShort)
}

fun main(args: Array<String>) {
    QtestRunner.run()
}