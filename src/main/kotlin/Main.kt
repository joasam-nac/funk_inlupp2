package inlamningsuppgift2.joakim

import java.io.File

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val rotations = File("2025day1_input.txt").readLines()
    val passwords = File("2020day2_input.txt").readLines()
    val ingredients = File("2025day5_input.txt").readLines()

    println("Dag 1 2025")
    println("del 1: ${day1PartOne2025(rotations)}")
    println("del 2: ${day1PartTwo2025(rotations)}")
    println("Dag 2 2020")
    println("del 1: ${day2PartOne2020(passwords)}")
    println("del 2: ${day2PartTwo2020(passwords)}")
    println("Dag 5 2025")
    println("del 1: ${day5PartOne2025(ingredients)}")
    println("del 2: ${day5PartTwo2025(ingredients)}")

    println("Dag 8 2025")
    // har inte gjort dag 8 själv!!
    val day8 = Day82025("2025day8_input.txt")
    //day8.solve()
    day8.exportVisualisationData(".")

    /*
    val l1 = mutableListOf<String>()
    val t1 = Tomteland().getUnderlings("Räven", l1)
    println("Rävens underordnade: $t1 $l1")*/

}

//räknar antal gånger pekaren visar 0
fun day1PartOne2025(rotations: List<String>): Int {
    var position = 50

    return rotations.sumOf { r ->
        val direction = r[0]
        val amount = r.drop(1).toInt()

        position = when (direction) {
            'R' -> position + amount
            'L' -> position - amount
            else -> error("Invalid direction $direction")
        }.mod(100)

        if (position == 0) 1 else 0
    }
}

//räknar antal gånger pekaren rör/går under eller över 0
fun day1PartTwo2025(rotations: List<String>): Int {
    var position = 50
    var amountsZero = 0

    rotations.forEach { r ->
        val direction = r[0]
        val amount = r.drop(1).toInt()

        repeat(amount) {
            position += when (direction) {
                'R' -> 1
                'L' -> -1
                else -> error("Invalid direction $direction")
            }

            position = position.mod(100)
            if (position == 0) amountsZero++
        }
    }
    return amountsZero
}

//räknar antal lösenord som följer policy att ett specifikt tecken ska finnas ett antal gånger inom ett intervall
fun day2PartOne2020(passwords: List<String>): Int {
    return passwords.count {password ->
        val splitTokens = password
            .split(" ")
        val (min: Int, max: Int) = splitTokens[0]
            .split("-")
            .map { it.toInt() }
        val character = splitTokens[1][0]    //a:
        val actualPassword = splitTokens[2] // ^

        val amount = actualPassword.count { it == character}
        amount in min..max
    }
}

//räknar antal lösenord som följer policy att ett specifikt tecken ska finnas på ett av förutbestämda platser
fun day2PartTwo2020(passwords: List<String>): Int {
    return passwords.count {password ->
        val splitTokens = password
            .split(" ")
        val (min: Int, max: Int) = splitTokens[0]
            .split("-")
            .map { it.toInt() -1 }
        val character = splitTokens[1][0]    //a:
        val actualPassword = splitTokens[2] // ^

        val checkFirst = actualPassword.getOrNull(min) == character
        val checkSecond = actualPassword.getOrNull(max) == character

        checkFirst != checkSecond
    }
}

//räknar antal ingredienser som finns i godtycklig intervall
fun day5PartOne2025(ingredients: List<String>): Int {
    val split = ingredients.indexOfFirst { it.isBlank() }

    val interval = ingredients.take(split)
        .map {
            val (a, b) = it.split("-")
            a.toLong() to b.toLong()
        }
        .sortedBy { it.first }

    val ingredientIds = ingredients.drop(split + 1)
        .filter { it.isNotBlank() }
        .map { it.toLong() }

    if (interval.isEmpty()) return 0

    var total = 0
    var currentStart = interval[0].first
    var currentEnd = interval[0].second

    //går igenom varje intervall
    for (i in 1 until interval.size) {
        val (start, end) = interval[i]

        if (start <= currentEnd) { //sammanfoga intervaller
            currentEnd = maxOf(currentEnd, end)
        } else {
            //räknar alla ingredienser som är fräscha enligt aktuell intervall
            total += ingredientIds.count { it in currentStart..currentEnd }
            currentStart = start
            currentEnd = end
        }
    }

    total += ingredientIds.count { it in currentStart..currentEnd }
    return total
}

//räknar max antal fräscha ingredienser givet intervallerna
fun day5PartTwo2025(ingredients: List<String>): Long {
    val interval = ingredients.takeWhile { it.isNotBlank() }
        .map {
            val (a, b) = it.split("-")
            a.toLong() to b.toLong()
        }
        .sortedBy { it.first }

    if (interval.isEmpty()) return 0L

    var total = 0L
    var currentStart = interval[0].first
    var currentEnd = interval[0].second

    //går igenom varje intervall
    for (i in 1 until interval.size) {
        val (start, end) = interval[i]

        if (start <= currentEnd) { //sammanfoga intervaller
            currentEnd = maxOf(currentEnd, end)
        } else {
            // enligt [min, max] inte [min, max)
            total += currentEnd - currentStart + 1
            currentStart = start
            currentEnd = end
        }
    }

    total += currentEnd - currentStart + 1
    return total
}
