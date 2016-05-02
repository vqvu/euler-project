package com.github.vqvu.eulerproject

import java.util.ArrayList
import java.util.HashMap
import java.util.NoSuchElementException

/**
 * @author victor
 */
class Primes() : Sequence<Long> {
    override fun iterator(): Iterator<Long> {
        return It()
    }

    class It : Iterator<Long> {
        val primes: MutableList<Long> = ArrayList()
        var next = 2L

        override fun hasNext(): Boolean {
            return true
        }

        override fun next(): Long {
            if (hasNext()) {
                val res = next
                primes.add(next)

                if (next == 2L) {
                    next = 3
                } else {
                    next += 2
                }

                while (!next.isPrime(primes)) {
                    next += 2
                }
                return res
            }
            throw NoSuchElementException()
        }

    }
}

fun computePrimes(max: Long): List<Long> {
    return Primes().takeWhile { p -> p < max }
        .toList()
}

fun primeFactors(num: Long): Map<Long, Int> {
    val primesIterator = Primes().iterator()
    var p = primesIterator.next()
    val result: MutableMap<Long, Int> = HashMap()

    var curNum = num
    var count = 0
    while (curNum != 1L) {
        if (curNum % p == 0L) {
            count++
            curNum /= p
        } else {
            if (count > 0) {
                result[p] = count
                count = 0
            }
            p = primesIterator.next()
        }
    }

    if (count > 0) {
        result[p] = count
    }
    return result
}