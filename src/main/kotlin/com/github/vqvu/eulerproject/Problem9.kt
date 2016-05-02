package com.github.vqvu.eulerproject

import java.util.ArrayList

/**
 * @author victor
 */
fun main(args: Array<String>) {
    // The following properties from https://en.wikipedia.org/wiki/Pythagorean_triple#Elementary_properties_of_primitive_Pythagorean_triples are used.
    //   - Exactly one of a, b is odd
    //   - c is odd
    //   - Exactly one of a, b is divisible by 3
    //   - Exactly one of a, b is divisible by 4
    val sum: Int = 1000
    var pythaTriples: MutableList<Triple<Int, Int, Int>> = ArrayList()

    // Case: a is divisible by 3.
    // Since b + c > 2 * a => 3 * a < sum
    // Since c > b => b < (sum - a) / 2
    // Since b is divisible by 4 and is thus even =>
    //   a must be odd and not divisible by 4
    for (a in 3..(sum / 3) step 3) {
        if (a % 4 == 0 || a % 2 == 0) {
            continue
        }

        val initialB = MathFn.roundUp(a, 4)
        for (b in initialB..(sum - a) / 2 step 4) {
            val c = 1000 - a - b
            if (c < b) {
                continue
            }

            if (a * a + b * b == c * c) {
                pythaTriples.add(Triple(a, b, c))
            }
        }
    }

    // Case: a is divisible by 4
    // Since b + c > 2 * a => 3 * a < sum
    // Since c > b => b < (sum - a) / 2
    // Since a is divisible by 4 and is thus even =>
    //   b must be odd
    for (a in 4..(sum / 3) step 4) {
        if (a % 3 == 0) {
            continue
        }

        val closestMultiple3 = MathFn.roundUp(a, 3)
        val initialB: Int
        if (closestMultiple3 % 2 == 0) {
            initialB = closestMultiple3 + 3
        } else {
            initialB = closestMultiple3
        }

        for (b in initialB..(sum - a) / 2 step 6) {
            val c = 1000 - a - b
            if (c < b) {
                continue
            }

            if (a * a + b * b == c * c) {
                pythaTriples.add(Triple(a, b, c))
            }
        }
    }

    val result = reduce(Int::times, pythaTriples.first())
    answer(result)
}

fun reduce(f: (Int, Int) -> Int, triple: Triple<Int, Int, Int>): Int {
    return f(f(triple.first, triple.second), triple.third)
}
