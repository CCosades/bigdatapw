package com.hubelias.bigdata.crawler.squads

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver


class EnglishWikipedia(private val driver: ChromeDriver) : WorldCupSquadsCrawler {
    override fun getSquads(): Sequence<Squad> {
        driver.get("https://en.wikipedia.org/wiki/2018_FIFA_World_Cup_squads")

        getCountriesWithCoach().forEach(::println)

        return driver
                .findElementsByTagName("tbody")
                .take(32)
                .asSequence()
                .map { squadTable ->
                    squadTable.findElements(By.tagName("tr")).map { playerRow ->
                        val cells = playerRow.findElements(By.tagName("td"))
                        Player(
                                number = cells[0].text.trim().toInt(),
                                position = Position.valueOf(cells[1].text.trim()),
                                name = playerRow.findElements(By.tagName("th")).first().text.substringBefore("(captain)").trim(),
                                age = cells[2].text.trim().takeLast(3).take(2).toInt()
                        )
                    }
                }.zip(getCountriesWithCoach().asSequence())
                .map { (players, countryAndCoach) ->
                    val (country, coach) = countryAndCoach
                    Squad(country, coach, players)
                }
    }

    private fun getCountriesWithCoach(): List<Pair<String, String>> {
        val teams = driver
                .findElementsByTagName("h3")
                .take(32)
                .map { it.text.trim() }
        val coaches = driver
                .findElementsByXPath("//*[contains(text(), 'Coach')]")
                .drop(1)
                .take(32)
                .map { it.text.substringAfter("Coach:").trim() }
        return teams.zip(coaches)
    }
}