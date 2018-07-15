package com.hubelias.bigdata.crawler.relations

import org.openqa.selenium.chrome.ChromeDriver


abstract class AbstractWorldCupRelationsCrawler(
        protected val driver: ChromeDriver
) {
    fun getRelations(): Sequence<Relation> {
        val relationUrls = getLinksToAllRelations()

        println("Found ${relationUrls.size} relations")
        relationUrls.forEach(::println)

        return relationUrls.asSequence().map(this::getRelation)
    }

    abstract protected fun getLinksToAllRelations(): List<String>

    private fun getRelation(relationUrl: String): Relation {
        getAndAcceptCookies(relationUrl)
        loadWholeRelation()

        val teams = getTeams()
        val elements = getElements()

        return Relation(teams.first, teams.second, elements)
    }

    protected fun getAndAcceptCookies(url: String) {
        driver.get(url)
        acceptCookies()
        sleep(200)
    }

    abstract protected fun loadWholeRelation()
    abstract protected fun getTeams(): Pair<String, String>
    abstract protected fun getElements(): List<RelationElement>

    abstract protected fun acceptCookies()
}