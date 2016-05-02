package com.github.vqvu.eulerproject

import com.github.vqvu.eulerproject.SlidingWindow.Companion.slidingWindow
import org.funktionale.currying.curried
import java.util.ArrayDeque
import java.util.ArrayList
import java.util.Deque
import java.util.NoSuchElementException

/**
 * @author victor
 */
val INPUT = "73167176531330624919225119674426574742355349194934" +
        "96983520312774506326239578318016984801869478851843" +
        "85861560789112949495459501737958331952853208805511" +
        "12540698747158523863050715693290963295227443043557" +
        "66896648950445244523161731856403098711121722383113" +
        "62229893423380308135336276614282806444486645238749" +
        "30358907296290491560440772390713810515859307960866" +
        "70172427121883998797908792274921901699720888093776" +
        "65727333001053367881220235421809751254540594752243" +
        "52584907711670556013604839586446706324415722155397" +
        "53697817977846174064955149290862569321978468622482" +
        "83972241375657056057490261407972968652414535100474" +
        "82166370484403199890008895243450658541227588666881" +
        "16427171479924442928230863465674813919123162824586" +
        "17866458359124566529476545682848912883142607690042" +
        "24219022671055626321111109370544217506941658960408" +
        "07198403850962455444362981230987879927244284909188" +
        "84580156166097919133875499200524063689912560717606" +
        "05886116467109405077541002256983155200055935729725" +
        "71636269561882670428252483600823257530420752963450"

fun main(args: Array<String>) {
    val windowSize = 13
    val result: Long = INPUT.asSequence()
        .map { (it - '0').toLong() }
        .transform(slidingWindow(windowSize))
        .map(MathFn.reduce<Long>().curried()(1)(Long::times))
        .sortedDescending()
        .first()

    answer(23514624000, result)
}

fun <T, R> Sequence<T>.transform(tformCtor: (Sequence<T>) -> Sequence<R>) : Sequence<R> {
    return tformCtor(this)
}

class SlidingWindow<T>(
        val windowSize: Int,
        val delegate: Sequence<T>) : Sequence<List<T>> {
    val slidingWindowScan: Sequence<List<T>> = SlidingWindowScan<T, List<T>>(
            windowSize,
            { ArrayList<T>(it) },
            { prev, next, memo -> createWindow(prev, next, memo) },
            delegate)

    override fun iterator(): Iterator<List<T>> {
        return slidingWindowScan.iterator()
    }

    companion object {
        private fun <T> createWindow(
                @Suppress("UNUSED_PARAMETER") prev: T,
                next: T, memo: List<T>): List<T> {
            val result: MutableList<T> = ArrayList(memo.subList(1, memo.size - 1))
            result.add(next)
            return result
        }

        fun <T> slidingWindow(windowSize: Int) : (Sequence<T>) -> Sequence<List<T>> {
            return {
                SlidingWindow(windowSize, it)
            }
        }
    }
}

class SlidingWindowScan<T, R>(
        val windowSize: Int,
        val init: (window: Collection<T>) -> R,
        val operator: (prev: T, next: T, memo: R) -> R,
        val delegate: Sequence<T>) : Sequence<R> {
    override fun iterator(): Iterator<R> {
        return It(delegate.iterator())
    }

    companion object {
        fun <T, R> slidingWindowScan(
            windowSize: Int,
            init: (window: Collection<T>) -> R,
            operator: (prev: T, next: T, memo: R) -> R) : (Sequence<T>) -> Sequence<R> {
            return {
                SlidingWindowScan(windowSize, init, operator, it)
            }
        }
    }

    inner class It(val delegate: Iterator<T>) : Iterator<R> {
        val window: Deque<T> = ArrayDeque(windowSize)
        var prev: T? = null
        var memo: R? = null
        var hasMemo: Boolean = false

        override fun hasNext(): Boolean {
            if (window.size == windowSize) {
                return true
            }

            while (window.size < windowSize && delegate.hasNext()) {
                window.offer(delegate.next())
            }

            return window.size == windowSize
        }

        override fun next(): R {
            if (hasNext()) {
                if (hasMemo) {
                    val next = window.last
                    memo = operator(prev!!, next, memo!!)
                } else {
                    memo = init(window)
                }
                prev = window.remove()
                return memo!!
            }
            throw NoSuchElementException()
        }
    }
}