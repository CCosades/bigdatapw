package com.hubelias.bigdata.flink

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.util.Collector


object ExtractComments : FlatMapFunction<String, Comment> {
    override fun flatMap(line: String, out: Collector<Comment>) {
        val objectMapper = ObjectMapper()
        val relationNode = objectMapper.readValue(line, JsonNode::class.java)
        val firstTeam = relationNode["firstTeam"].asText()
        val secondTeam = relationNode["secondTeam"].asText()
        val elements = relationNode["elements"]

        elements.forEach { elementNode ->

            val comment = Comment(
                    elementNode["time"]?.asText() ?: "",
                    firstTeam,
                    secondTeam,
                    elementNode["message"].asText())
            out.collect(comment)
        }
    }
}