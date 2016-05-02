package com.github.vqvu.eulerproject

/**
 * @author victor
 */
fun main(args: Array<String>) {
    val num = primeFactors(600851475143)
        .keys
        .sorted()
        .last()

    assert(num == 6857L)
    println("num = $num")
}
