package com.example.MyBookShopApp.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class GenresPageSeleniumTests {

    private static ChromeDriver driver;

    @BeforeAll
    static void setup(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\strel\\Downloads\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @AfterAll
    static void tearDown(){
        driver.quit();
    }

    @Test
    public void testGenresPageAccess() throws InterruptedException {
        GenresPage genresPage = new GenresPage(driver);
        genresPage
                .callPage()
                .pause();

        assertTrue(driver.getPageSource().contains("Document"));
    }

    @Test
    public void testGenresPageOfferta() throws InterruptedException {
        GenresPage genresPage = new GenresPage(driver);
        genresPage
                .callPage()
                .pause()
                .clickDrama()
                .pause();

        assertTrue(driver.getPageSource().contains("Feast of July"));
    }

    @Test
    public void testGenresPageRedirectMain() throws InterruptedException {
        GenresPage genresPage = new GenresPage(driver);
        genresPage
                .callPage()
                .pause()
                .clickDrama()
                .pause()
                .returnMain();

        assertTrue(driver.getPageSource().contains("магия"));
    }
}