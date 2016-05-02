package com.github.vqvu.eulerproject

/**
 * @author victor
 */
fun main(args: Array<String>) {
    val sumSquares = (1..100)
        .map { i -> i * i }
        .reduce(Int::plus)
    val squareSum = pow((1..100).reduce(Int::plus), 2)

    answer(25164150, squareSum - sumSquares)
}