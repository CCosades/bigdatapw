package com.hubelias.bigdata.crawler.squads

import java.time.LocalDate


data class Squad(
        val team: String,
        val coach: String,
        val players: List<Player>
)

data class Player(
        val number: Int,
        val name: String,
        val position: Position,
        val age: Int
)

enum class Position {
    GK, DF, MF, FW
}
