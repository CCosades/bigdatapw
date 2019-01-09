package com.hubelias.bigdata.flink


data class CommentTokens(
        val time: String,
        val firstTeam: String,
        val secondTeam: String,
        val tokens: List<String>)