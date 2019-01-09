package com.hubelias.bigdata.flink

import org.apache.flink.api.common.functions.MapFunction
import org.apache.flink.api.java.tuple.Tuple2
import org.apache.flink.api.java.tuple.Tuple3


object PlayerMatcher : MapFunction<Tuple2<String, Int>, Tuple3<String, PlayerMatcher.MatchType, Int>> {
    val allPlayers = SquadsParser.getSquads().flatMap { it.players }

    init {
        allPlayers.map { it.name }.forEach(::println)
    }

    override fun map(value: Tuple2<String, Int>): Tuple3<String, MatchType, Int> {
        val matchingPlayers = allPlayers.filter { it.name.endsWith(value.f0) }

        val match = when (matchingPlayers.size) {
            0 -> MatchType.NONE
            1 -> MatchType.SINGLE
            else -> MatchType.MULTI
        }

        return Tuple3.of(value.f0, match, value.f1)
    }

    enum class MatchType {
        NONE, SINGLE, MULTI
    }
}