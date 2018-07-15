package com.hubelias.bigdata.crawler.relations

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver

class Onet(driver: ChromeDriver) : AbstractWorldCupRelationsCrawler(driver) {
    override fun acceptCookies() {
        driver.findElementsByClassName("cmp-intro_acceptAll").filter { it.isDisplayed }.singleOrNull()?.click()
    }

    override fun getLinksToAllRelations(): List<String> {
//        return listOf("https://sport.onet.pl/mundial-2018/reprezentacja-rosji/rosja-arabia-saudyjska-relacja-na-zywo/5g2xqnv")
//        return listOf("https://sport.onet.pl/mundial-2018/reprezentacja-danii/mundial-2018-dania-australia-relacja-live-i-wynik-na-zywo/g3f84jb")

        val groupPhase = getLinksToRelationsFrom("https://sport.onet.pl/mundial-2018/faza-grupowa")
        val cupPhase = getLinksToRelationsFrom("https://sport.onet.pl/mundial-2018/drabinka-ms-w-pilce-noznej")

        return groupPhase.plus(cupPhase)
    }

    private fun getLinksToRelationsFrom(url: String): List<String> {
        getAndAcceptCookies(url)
        return driver.findElementsByXPath("//*[contains(text(), 'Relacja Live')]").map {
            it.getAttribute("href")
        }
    }

    override fun loadWholeRelation() {
        driver.findElementsByXPath("//*[contains(text(), 'CZYTAJ DALEJ')]").singleOrNull()?.click()
        sleep(200)
        driver.findElementsByClassName("commentatorLiveMoreBtn").singleOrNull()?.let { evenMore ->
            while (evenMore.isDisplayed) {
                evenMore.click()
                sleep(200)
            }
        }
        sleep(200)
    }

    override fun getTeams(): Pair<String, String> {
        val first = driver.findElementById("firstClub").findElement(By.tagName("h3")).text
        val second = driver.findElementById("secondClub").findElement(By.tagName("h3")).text
        return first to second
    }

    override fun getElements(): List<RelationElement> {
        return driver.findElementsByClassName("messageItem").mapNotNull(this::createRelationElement)
    }

    private fun createRelationElement(element: WebElement): RelationElement? {
        val clazz = element.getAttribute("class")

        if (clazz.contains("textItem").not()) {
            return null
        }

        return RelationElement.get(
                element.findElements(By.className("text")).singleOrNull()?.text,
                element.findElements(By.className("message")).singleOrNull()?.text
        )
    }
}


