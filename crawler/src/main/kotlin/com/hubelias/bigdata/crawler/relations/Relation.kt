package com.hubelias.bigdata.crawler.relations

data class Relation(
        val firstTeam: String,
        val secondTeam: String,
        val elements: List<RelationElement>)
