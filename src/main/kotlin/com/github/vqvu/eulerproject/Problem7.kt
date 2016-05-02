package com.github.vqvu.eulerproject

import java.util.ArrayList

/**
 * @author victor
 */
fun main(args: Array<String>) {
    val primes: MutableList<Int> = ArrayList()

    primes.add(2)
    primes.add(3)
    var i: Int = 5
    while (primes.size != 10001) {
        if (i.isPrime(primes)) {
            primes.add(i)
        }
        i++
    }
    println("primes.last() = ${primes.last()}")
}

fun Int.isPrime(prevPrimes: List<Int>): Boolean {
    val root = Math.sqrt(this.toDouble()).toInt()
    for (divisor in prevPrimes) {
        if (divisor > root) {
            break
        } else if (this % divisor == 0) {
            return false
        }
    }
    return true
}

fun Long.isPrime(prevPrimes: List<Long>): Boolean {
    val root = Math.sqrt(this.toDouble()).toLong()
    for (divisor in prevPrimes) {
        if (divisor > root) {
            break
        } else if (this % divisor == 0L) {
            return false
        }
    }
    return true
}