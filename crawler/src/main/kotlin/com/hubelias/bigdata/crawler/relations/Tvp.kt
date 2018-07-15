package com.hubelias.bigdata.crawler.relations

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver

class Tvp(driver: ChromeDriver) : AbstractWorldCupRelationsCrawler(driver) {
    override fun acceptCookies() {
        sleep(1000)
        driver.findElementsByClassName("tvp-covl__ab").singleOrNull()?.click()
        sleep(500)
    }

    override fun getElements(): List<RelationElement> = driver
            .findElementsByClassName("article__minute")
            ?.singleOrNull()
            ?.findElements(By.className("article__minute-wrapper"))
            ?.map {
                val textElement = it.findElement(By.className("article__minute-text"))
                val tweets = textElement.findElements(By.tagName("twitterwidget"))
                val childrenTextPrefix = tweets.firstOrNull()?.text?.take(25)?.takeIf(String::isNotBlank)

                val message = if (childrenTextPrefix != null) {
                    textElement.text.split(childrenTextPrefix).first()
                } else {
                    textElement.text
                }

                RelationElement.get(
                        it.findElement(By.className("article__minute-date")).text.trim(),
                        message.trim()

                )
            }?.reversed() ?: emptyList()

    override fun getTeams(): Pair<String, String> {
        val header = driver.findElementByClassName("sheader__container")
        val first = header
                .findElement(By.className("pull-left"))
                .findElement(By.className("sheader__country-name"))
                .text

        val second = header
                .findElement(By.className("pull-right"))
                .findElement(By.className("sheader__country-name"))
                .text

        return first to second
    }

    override fun loadWholeRelation() {
        sleep(500)
    }

    override fun getLinksToAllRelations(): List<String> {
        //        return listOf("http://sport.tvp.pl/mundial2018/37292908/mundial-2018-rosja-chorwacja-22-chorwaci-lepsi-w-karnych-gospodarze-poza-ms")

        getAndAcceptCookies("http://sport.tvp.pl/mundial2018/mecze")

        return driver.findElementsByClassName("matches__item-country-link").map {
            it.getAttribute("href")
        }
    }
}
