import annotations.QTest
import annotations.QtestRunner
import hooks.checkAll

@QTest fun test(){
    checkAll()
}

fun main(args: Array<String>) {
    QtestRunner.run()
}