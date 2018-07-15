package com.hubelias.bigdata.crawler.squads

import com.google.gson.Gson
import org.openqa.selenium.chrome.ChromeDriver
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {
    val driverPath = Paths.get(System.getProperty("user.home")).resolve("Downloads").resolve("chromedriver.exe")
    System.setProperty("webdriver.chrome.driver", driverPath.toString())

    val driver = ChromeDriver()
    val crawler = EnglishWikipedia(driver)

    val gson = Gson()
    val outputFile = File(crawler.javaClass.simpleName.toLowerCase() + "_squads.txt")
    outputFile.takeIf(File::exists)?.delete()

    outputFile.printWriter().use { output ->
        crawler.getSquads().forEach { squad ->
            val squadJson = gson.toJson(squad)

            output.println(squadJson)
            println(squadJson)
        }
    }

    driver.close()
}