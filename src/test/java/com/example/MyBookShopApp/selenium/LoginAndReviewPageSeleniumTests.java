package com.example.MyBookShopApp.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class LoginAndReviewPageSeleniumTests {

    private static ChromeDriver driver;

    @BeforeAll
    static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\strel\\Downloads\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    public void testMainPageAccess() throws InterruptedException {
        LoginAndReviewPage loginAndReviewPage = new LoginAndReviewPage(driver);
        loginAndReviewPage
                .callMainPage()
                .pause();

        assertTrue(driver.getPageSource().contains("BOOKSHOP"));
    }


    @Test
    public void testLoginAndReview() throws InterruptedException {
        LoginAndReviewPage loginAndReviewPage = new LoginAndReviewPage(driver);
        loginAndReviewPage
                .callMainPage()
                .pause()
                .setUpSearchToken("If These Walls Could Talk 2")
                .pause()
                .submit()
                .clickBookBySlugId()
                .pause();
        assertTrue(driver.getPageSource().contains("Чтобы оставить отзыв авторизуйтесь"));

        loginAndReviewPage
                .callMainPage()
                .pause()
                .loginPage()
                .pause()
                .submitEmail()
                .pause()
                .setUpEmail()
                .pause()
                .clickSendAuth()
                .pause()
                .setUpCode()
                .pause()
                .clickLogin()
                .pause()
                .setUpSearchToken("If These Walls Could Talk 2")
                .pause()
                .submit()
                .pause()
                .clickBookBySlugId()
                .pause();
        assertFalse(driver.getPageSource().contains("Чтобы оставить отзыв авторизуйтесь"));

        loginAndReviewPage.
                addTextReview()
                .pause()
                .addStarReview()
                .pause()
                .addStarReview()
                .pause()
                .addReview()
                .pause();

        assertTrue(driver.getPageSource().contains("Прекрасное чтиво, всем советую"));
    }
}