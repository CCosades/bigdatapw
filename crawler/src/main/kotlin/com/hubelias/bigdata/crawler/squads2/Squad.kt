package com.hubelias.bigdata.crawler.squads2


data class Squad(
        val team: String,
        val coach: String,
        val players: List<Player>
)

data class Player(
        val name: String,
        val position: Position,
        val club: String
)

data class Player2(
        val number: Int,
        val name: String,
        val position: Position,
        val club: String,
        val yearOfBirth: Int
)

enum class Position {
    GK, DF, MF, FW
}
