package com.github.vqvu.eulerproject

import java.util.HashMap

/**
 * @author victor
 */
fun main(args: Array<String>) {
    val maxPrimeFactors: MutableMap<Long, Int> = HashMap()
    for (i in 2..20) {
        val primeFactors = primeFactors(i.toLong())
        for ((prime, count) in primeFactors) {
            var curCount = maxPrimeFactors[prime]
            if (curCount == null) {
                curCount = 0
            }
            if (curCount < count) {
                maxPrimeFactors[prime] = count
            }
        }
    }

    val answer = maxPrimeFactors
        .map { entry -> pow(entry.key, entry.value) }
        .fold(1L, Long::times)

    assert(answer == 232792560L)
    println("answer = $answer")
}