import strikt.api.expectThat
import strikt.assertions.isEqualTo

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            10 * line.first { it.isDigit() }.digitToInt() +
                line.last { it.isDigit() }.digitToInt()
        }
    }

    val numberNames = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->

            var firstDigit = 0
            for (index in line.indices) {
                var found = false
                if (line[index].isDigit()) {
                    firstDigit = line[index].digitToInt()
                    found = true
                } else {
                    for (textNumber in numberNames.keys) {
                        if (line.indexOf(textNumber, startIndex = index) == index) {
                            firstDigit = numberNames[textNumber]!!.toInt()
                            found = true
                            break
                        }
                    }
                }
                if (found) break
            }

            var lastDigit = 0
            for (index in line.length - 1 downTo 0) {
                var found = false
                if (line[index].isDigit()) {
                    lastDigit = line[index].digitToInt()
                    found = true
                } else {
                    for (textNumber in numberNames.keys) {
                        if (line.indexOf(textNumber, startIndex = index) == index) {
                            lastDigit = numberNames[textNumber]!!.toInt()
                            found = true
                            break
                        }
                    }
                }
                if (found) break
            }
            10 * firstDigit + lastDigit
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    expectThat(part1(testInput)).isEqualTo(142)

    val input = readInput("Day01")
    part1(input).println()

    val testInput2 = readInput("Day01_test_2")
    expectThat(part2(testInput2)).isEqualTo(281)

    val input2 = readInput("Day01_2")
    part2(input2).println()
}
