package com.example.anketify.ui

import org.openqa.selenium.WebDriver
import org.openqa.selenium.safari.SafariDriver

import java.util.concurrent.TimeUnit

class SeleniumConfig {
    val driver: WebDriver

    companion object {
        init {
            System.setProperty("webdriver.safari.driver", "/Users/apple/Downloads/geckodriver")
        }
    }

    init {
        driver = SafariDriver()
        driver.manage().window().fullscreen()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    }
}