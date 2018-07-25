package com.hubelias.bigdata.crawler.relations

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver


class SportPl(driver: ChromeDriver) : AbstractWorldCupRelationsCrawler(driver) {
    override fun getLinksToAllRelations(): List<String> {
//        return listOf("http://www.sport.pl/pilka/2,158869,,Rosja_Arabia_Saudyjska,,178601530,6735.html")

        var allRelations = emptyList<String>()

        driver.get("http://www.sport.pl/pilka/2,128823,,,,178601517,P_SPORT_SLOWNIK.html")

        do {
            allRelations += relationsOnOnePage()
            val nextPageLink = driver
                    .findElementsByClassName("prevnext")
                    ?.singleOrNull()
                    ?.findElements(By.className("next"))
                    ?.singleOrNull()
                    ?.getAttribute("href")
                    ?.also { driver.get(it) }
            sleep(1500)
        } while (nextPageLink != null)

        return allRelations
    }

    private fun relationsOnOnePage() = driver
            .findElementsByClassName("mod_group_scores")
            .singleOrNull()
            ?.findElements(By.tagName("ul"))
            ?.singleOrNull()
            ?.findElements(By.tagName("a"))
            ?.map { it.getAttribute("href") }
            ?: emptyList()

    override fun loadWholeRelation() {
        sleep(500)
    }

    override fun getTeams(): Pair<String, String> {
        val home = driver
                .findElementById("gr_l")
                .findElement(By.className("game_team"))
                .text
        val away = driver
                .findElementById("gr_r")
                .findElement(By.className("game_team"))
                .text

        return home to away
    }

    override fun getElements(): List<RelationElement> {
        var allElements = emptyList<RelationElement>()

        do {
            allElements += elementsFromOnePage()
            val nextPageLink = driver
                    .findElementsByClassName("pages")
                    ?.singleOrNull()
                    ?.findElements(By.className("next"))
                    ?.singleOrNull()
                    ?.getAttribute("href")
                    ?.also { driver.get(it) }
            sleep(500)
        } while (nextPageLink != null)

        return allElements.reversed()
    }

    private fun elementsFromOnePage() = driver
            .findElementsByClassName("gazeta_rtc2012_body")
            .singleOrNull()
            ?.findElements(By.tagName("li"))
            ?.map { entry ->
                val time = entry.findElements(By.className("time")).singleOrNull()?.text
                val content = entry.findElements(By.className("content")).singleOrNull()?.text
                RelationElement.get(time, content)
            } ?: emptyList()

    override fun acceptCookies() {
        sleep(500)
    }
}