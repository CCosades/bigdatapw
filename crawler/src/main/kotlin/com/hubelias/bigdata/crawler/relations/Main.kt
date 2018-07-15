package com.hubelias.bigdata.crawler.relations

import com.google.gson.Gson
import org.openqa.selenium.chrome.ChromeDriver
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {
    val driverPath = Paths.get(System.getProperty("user.home")).resolve("Downloads").resolve("chromedriver.exe")
    System.setProperty("webdriver.chrome.driver", driverPath.toString())

    val driver = ChromeDriver()
    val crawler : AbstractWorldCupRelationsCrawler = Onet(driver)

    val gson = Gson()
    val outputFile = File(crawler.javaClass.simpleName.toLowerCase() + ".txt")
    outputFile.takeIf(File::exists)?.delete()

    outputFile.printWriter().use { output ->
        crawler.getRelations().forEach { relation ->
            val relationJson = gson.toJson(relation)

            output.println(relationJson)
            println(relationJson)
        }
    }

    driver.close()
}
