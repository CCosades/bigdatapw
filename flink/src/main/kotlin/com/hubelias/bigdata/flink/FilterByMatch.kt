package com.hubelias.bigdata.flink

import org.apache.flink.api.common.functions.FilterFunction
import org.apache.flink.api.java.tuple.Tuple3


class FilterByMatch(val matchType: PlayerMatcher.MatchType) : FilterFunction<Tuple3<String, PlayerMatcher.MatchType, Int>> {
    override fun filter(value: Tuple3<String, PlayerMatcher.MatchType, Int>): Boolean {
        return value.f1 == matchType
    }
}