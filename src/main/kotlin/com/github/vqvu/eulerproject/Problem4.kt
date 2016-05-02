package com.github.vqvu.eulerproject

import java.util.NoSuchElementException

/**
 * @author victor
 */
fun main(args: Array<String>) {
    var max: Result? = null
    for (left in 999 downTo 100) {
        for (right in 999 downTo left) {
            val product = left * right
            if ((max == null || product > max.product) && product.isPalindrome()) {
                max = Result(left, right)
            }
        }
    }
    println("max = $max")
}

fun Int.isPalindrome(): Boolean {
    if (this < 0) {
        return false
    }

    return this.toString().isPalindrome()
}

fun String.isPalindrome(): Boolean {
    if (isEmpty()) {
        return true
    }

    for (i in 0..this.length / 2) {
        if (this[i] != this[length - 1 - i]) {
            return false
        }
    }
    return true
}

data class Result(val left: Int, val right: Int) {
    val product: Int = left * right

    override fun toString(): String{
        return "Result(left=$left, right=$right, product=$product)"
    }
}

class PairsIterable(val range: ClosedRange<Int>, val incrementing: Boolean) : Sequence<Pair<Int, Int>> {
    init {
        check(range.start <= range.endInclusive) {
            "The minimum should be less than or equal to the maximum. Actual: $range"
        }
    }

    override fun iterator(): Iterator<Pair<Int, Int>> {
        return It()
    }

    private inner class It() : Iterator<Pair<Int, Int>> {
        private val END_SENTINEL: Long = Long.MIN_VALUE

        var left = range.endInclusive
        var right = range.endInclusive
        var sum: Long = left.toLong() + right.toLong()

        override fun hasNext(): Boolean {
            return sum != END_SENTINEL
        }

        private fun toResultValue(value: Int): Int {
            if (!incrementing) {
                return value
            }

            return (range.endInclusive.toLong() - value + range.start).toInt()
        }

        override fun next(): Pair<Int, Int> {
            if (hasNext()) {
                assert(left in range)
                assert(right in range)

                val res = Pair(toResultValue(left), toResultValue(right))
                if (left == range.endInclusive || right == range.start) {
                    sum--
                    if (sum >= range.endInclusive + range.start) {
                        right = range.endInclusive
                        left = (sum - right).toInt()
                    } else if (sum >= 2 * range.start) {
                        left = range.start
                        right = (sum - left).toInt()
                    } else {
                        // This is the end.
                        sum = END_SENTINEL
                    }
                } else {
                    left++
                    right--
                }

                assert(left.toLong() + right == sum)
                assert(left in range)
                assert(right in range)

                assert(res.first in range)
                assert(res.second in range)
                return res
            }
            throw NoSuchElementException()
        }
    }
}