package com.hubelias.bigdata.flink

import org.apache.flink.api.common.functions.MapFunction
import java.util.*


object TokenizeComment : MapFunction<Comment, CommentTokens> {
    private val DELIMITERS = listOf(',')

    override fun map(comment: Comment): CommentTokens {
        val tokens = StringTokenizer(comment.message, " \t\n\r,.;!?':\"").toList() as List<String>
        return CommentTokens(
                comment.time,
                comment.firstTeam,
                comment.secondTeam,
                tokens.filter { it.first().isUpperCase() }
        )
    }
}