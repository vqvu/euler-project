package com.github.vqvu.eulerproject

import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap
import java.util.SortedSet
import java.util.TreeSet

/**
 * @author victor
 */
fun main(args: Array<String>) {
    val primePermutations: MutableMap<String, SortedSet<Int>> = HashMap()
    for (prime in computePrimes(9999).map(Long::toInt)) {
        if (prime > 1000) {
            val key = sortChars(prime.toString())
            var permutations = primePermutations[key]
            if (permutations == null) {
                permutations = TreeSet()
                primePermutations[key] = permutations
            }
            permutations.add(prime)
        }
    }

    for (permutations in primePermutations.values) {
        if (permutations.size < 3) {
            continue
        }

        val permList = ArrayList(permutations)
        for (i in 0..permList.size - 3) {
            for (j in i + 1..permList.size - 2) {
                for (k in j + 1..permList.size - 1) {
                    val first = permList[i]
                    val second = permList[j]
                    val third = permList[k]
                    if (second - first == third - second) {
                        println(listOf(first, second, third))
                    }
                }
            }
        }
    }
}

fun sortChars(str: String): String {
    val chars = str.toCharArray()
    Arrays.sort(chars)
    return String(chars)
}
