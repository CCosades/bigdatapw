package com.hubelias.bigdata.crawler.squads2

import java.io.File

object Logs {
    private val logs = mutableListOf<String>()

    fun add(log: String) {
        println(log)
        logs.add(log)
    }
}

private var currentTeam = "UNKNOWN"
    set(value) {
        Logs.add("Processing team: $value")
        field = value
    }
private var currentPosition = Position.GK
    set(value) {
        Logs.add("Processing position: $value")
        field = value
    }


fun main(args: Array<String>) {
    File("data/squads.txt").useLines { lines ->
        lines.forEachIndexed { index, line ->
            try {
                when {
                    line.isBlank() -> { /*ignore*/
                    }
                    line.startsWith("REPREZENTACJA") -> processHeader(line)
                    else -> processPlayer(line)
                }
            } catch (e: IllegalArgumentException) {
                throw RuntimeException("Error in line ${index + 1}", e)
            }
        }
    }
}

private fun processHeader(text: String) {
    val tokens = text.split(' ')

    val teamToken = tokens[1]
    val parsedTeam = when (teamToken.last()) {
        'I', 'Y' -> teamToken.dropLast(1).plus('A')
        'U' -> teamToken.dropLast(1)
        'A' -> teamToken.dropLast(1).plus('O')
        'C' -> "NIEMCY"
        else -> throw IllegalArgumentException("Failed extracting team name: $teamToken")
    }
    currentTeam = when (parsedTeam) {
        "ARABIA" -> "ARABIA SAUDYJSKA"
        "KOREA" -> "KOREA POŁUDNIOWA"
        "PER" -> "PERU"
        else -> parsedTeam
    }

    val positionToken = tokens.last()
    currentPosition = when (positionToken) {
        "BRAMKARZE:" -> Position.GK
        "OBROŃCY:" -> Position.DF
        "POMOCNICY:" -> Position.MF
        "NAPASTNICY:" -> Position.FW
        else -> throw IllegalArgumentException("Failed extracting position: $positionToken")
    }
}

private fun processPlayer(text: String) {
    val number = text.takeWhile { it.isDigit() }.toInt()
    val name = text.substringAfter('.').substringBefore('(').trim()
    val club = text.substringAfter('(').substringBefore(')').trim()
    val yearOfBirth = text.substringAfter("ur.").substringBefore("r.").trim().toInt()
    println(Player2(number, name, currentPosition, club, yearOfBirth))
}
