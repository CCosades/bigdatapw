package com.hubelias.bigdata.crawler.squads


interface WorldCupSquadsCrawler {
    fun getSquads() : Sequence<Squad>
}