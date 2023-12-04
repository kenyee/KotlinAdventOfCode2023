import strikt.api.expectThat
import strikt.assertions.isEqualTo

fun main() {
    data class Cubes(
        val red: Int,
        val green: Int,
        val blue: Int,
    )
    val initialCubes = Cubes(
        red = 12,
        green = 13,
        blue = 14,
    )

    fun part1(input: List<String>): Int {
        val putBackIn = true
        val validGameIDs = mutableListOf<Int>()
        for (line in input) {
            var isGameValid = true
            val (game, history) = line.split(": ")
            val gameID = game.split(" ")[1].toInt()
            var cubes = initialCubes
            val steps = history.split("; ")
            for (step in steps) {
                val cubesShownInStep = step.split(", ")
                for (cubesShown in cubesShownInStep) {
                    val (countString, color) = cubesShown.split(" ")

                    val count = countString.toInt()
                    cubes = when (color) {
                        "red" -> cubes.copy(red = cubes.red - count)
                        "green" -> cubes.copy(green = cubes.green - count)
                        "blue" -> cubes.copy(blue = cubes.blue - count)
                        else -> error("Unknown $color")
                    }
                }
                if (cubes.red < 0 || cubes.green < 0 || cubes.blue < 0) {
                    isGameValid = false
                }
                if (putBackIn) {
                    cubes = initialCubes
                }
            }
            if (isGameValid) {
                validGameIDs.add(gameID)
            }
        }
        println("valid games IDs is $validGameIDs")
        return validGameIDs.sum()
    }

    fun part2(input: List<String>): Int {
        var powerSum = 0
        for (line in input) {
            val (_, history) = line.split(": ")
            var cubes = Cubes(0,0,0)
            val steps = history.split("; ")
            for (step in steps) {
                val cubesShownInStep = step.split(", ")
                for (cubesShown in cubesShownInStep) {
                    val (countString, color) = cubesShown.split(" ")

                    val count = countString.toInt()
                    cubes = when (color) {
                        "red" -> cubes.copy(red = cubes.red.coerceAtLeast(count))
                        "green" -> cubes.copy(green = cubes.green.coerceAtLeast(count))
                        "blue" -> cubes.copy(blue = cubes.blue.coerceAtLeast(count))
                        else -> error("Unknown $color")
                    }
                }
            }
            powerSum += (cubes.red * cubes.green * cubes.blue)
        }
        return powerSum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    expectThat(part1(testInput)).isEqualTo(8)

    val input = readInput("Day02")
    part1(input).println()

    expectThat(part2(testInput)).isEqualTo(2286)

    val input2 = readInput("Day02")
    part2(input2).println()
}
