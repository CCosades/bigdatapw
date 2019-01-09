package com.hubelias.bigdata.flink

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.api.java.tuple.Tuple2
import org.apache.flink.util.Collector


object WordCountMap : FlatMapFunction<CommentTokens, Tuple2<String, Int>> {
    override fun flatMap(value: CommentTokens, out: Collector<Tuple2<String, Int>>) {
        value.tokens.forEach {
            out.collect(Tuple2.of(it, 1))
        }
    }
}