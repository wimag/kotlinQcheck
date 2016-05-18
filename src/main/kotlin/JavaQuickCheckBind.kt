import annotations.QGen
import annotations.QTest
import annotations.QtestRunner
import com.pholser.junit.quickcheck.From
import com.pholser.junit.quickcheck.generator.InRange
import hooks.checkAll
import statefull.StateMachine

fun isShort(@From(name="generateTwo")x: Int) = x < 1500


class Counter{
    private var x: Int
    constructor(value: Int){
        x = value
    }

    fun get() : Int {
        return x
    }

    fun inc(@From(name="generateTwo")v: Int) {
        x += v
        if(x > 100){
            x --;
        }
    }

    fun dec() {
        x -= 1
    }
}

@QGen fun generateCounter() : Counter{
    return Counter(1)
}

fun genState(c: Counter) : Int{
    return c.get()
}
@QTest fun test(){
//    val lambda = { x: String -> x.length < 10 }
//    checkAll(lambda)
    val state = StateMachine(::genState)
    state.addCommand({x: Int, y:Int -> x+y}, Counter::inc,postcondition = {x: Int, c: Counter -> x==c.get()},
            name = "Inc")
    state.addCommand({x: Int -> x-1}, Counter::dec, name = "Dec")
    state.execute()
}

fun main(args: Array<String>) {
    QtestRunner.run()
}