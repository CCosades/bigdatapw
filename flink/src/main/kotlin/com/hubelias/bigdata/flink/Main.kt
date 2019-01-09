package com.hubelias.bigdata.flink

import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.java.ExecutionEnvironment


fun main(args: Array<String>) {
    val env = ExecutionEnvironment.getExecutionEnvironment()

    env
            .readTextFile("data_full/sportpl.txt")
            .flatMap(ExtractComments)
            .map(TokenizeComment)
            .flatMap(WordCountMap)
            .map(PlayerMatcher)
            .filter(FilterByMatch(PlayerMatcher.MatchType.NONE))
            .groupBy(0)
            .sum(2)
            .setParallelism(1)
            .sortPartition(2, Order.DESCENDING)
            .print()
}