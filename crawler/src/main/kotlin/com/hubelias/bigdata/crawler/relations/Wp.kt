package com.hubelias.bigdata.crawler.relations

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.chrome.ChromeDriver


class Wp(driver: ChromeDriver) : AbstractWorldCupRelationsCrawler(driver) {
    override fun acceptCookies() {
        driver.findElementsByTagName("button").singleOrNull {
            it.text == "PRZECHODZĘ DO SERWISU"
        }?.click()
    }

    override fun getLinksToAllRelations(): List<String> {
//        return listOf("https://sportowefakty.wp.pl/ms-2018/relacja/83950/rosja-arabia-saudyjska")
        getAndAcceptCookies("https://sportowefakty.wp.pl/ms-2018/kalendarz")
        scrollDownSlowly()

        return driver.findElementsByClassName("cmatch__link").map { link ->
            link.getAttribute("href")
        }
    }

    private fun scrollDownSlowly() {
        (driver as JavascriptExecutor).apply {
            repeat(100) {
                sleep(100)
                executeScript("window.scrollBy(0,100)", "")
            }
        }
    }

    override fun loadWholeRelation() {
        sleep(1000)
    }

    override fun getTeams(): Pair<String, String> {
        val first = driver
                .findElementByClassName("matchcoverage__competitor--left")
                .findElement(By.className("matchcoverage__name")).text
        val second = driver
                .findElementByClassName("matchcoverage__competitor--right")
                .findElement(By.className("matchcoverage__name")).text
        return first to second
    }

    override fun getElements(): List<RelationElement> {
       return  driver.findElementsByClassName("coventry").map {
            val time = it.findElements(By.tagName("time")).singleOrNull()?.text
            val body = it.findElements(By.className("coventry__body")).flatMap {
                it.findElements(By.tagName("p")).map {
                    it.text
                }
            }.filterNot { it.isBlank() || it == "Trwa ładowanie..." }.joinToString(separator = " ")

            RelationElement.get(time, body)
        }.reversed()
    }
}