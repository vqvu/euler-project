package com.github.vqvu.eulerproject

/**
 * @author victor
 */
fun pow(num: Long, power: Int): Long {
    check(power >= 0) {
        "The power must be positive."
    }
    if (power == 0) {
        return 1
    } else if (power == 1) {
        return num
    }

    var result = num
    for (i in 2..power) {
        result *= num
    }
    return result
}

fun pow(num: Int, power: Int): Int {
    check(power >= 0) {
        "The power must be positive."
    }
    if (power == 0) {
        return 1
    } else if (power == 1) {
        return num
    }

    var result = num
    for (i in 2..power) {
        result *= num
    }
    return result
}

class MathFn {
    companion object {
        fun roundUp(value: Int, mod: Int): Int {
            check(mod > 0, { "mod must be positive." })
            return ((value + mod - 1) / mod) * mod
        }

        fun <T> reduce(): (initial: T, operator: (T, T) -> T, collection: Collection<T>) -> T {
            return { initial, operator, collection ->
                collection.fold(initial, operator)
            }
        }
    }
}
