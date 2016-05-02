package com.github.vqvu.eulerproject

/**
 * @author victor
 */
fun answer(answer: Any) {
    println("answer = $answer")
}

fun <T: Any> answer(expected: T, answer: T) {
    assert(expected == answer)
    answer(answer)
}