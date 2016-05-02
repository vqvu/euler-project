package com.github.vqvu.eulerproject

import java.util.NoSuchElementException

/**
 * @author victor
 */
fun main(args: Array<String>) {
    var sum = 0
    for (i in Fib(4e6.toInt())) {
        if (i % 2 == 0) {
            sum += i
        }
    }
    assert(sum == 4613732)
    println("sum = $sum")
}

class Fib(val max: Int) : Sequence<Int> {
    override fun iterator(): Iterator<Int> {
        return It()
    }

    inner class It : Iterator<Int> {
        var prev = 0
        var next = 1

        override fun hasNext(): Boolean {
            return next > 0 && next < max
        }

        override fun next(): Int {
            if (hasNext()) {
                val res = next
                val prev2 = prev
                prev = next
                next = prev2 + prev
                return res
            }
            throw NoSuchElementException()
        }
    }
}