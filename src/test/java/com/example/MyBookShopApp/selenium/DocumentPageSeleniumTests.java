package com.example.MyBookShopApp.selenium;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;



@SpringBootTest
class DocumentPageSeleniumTests {

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
    public void testDocumentPageAccess() throws InterruptedException {
        DocumentsPage documentsPage = new DocumentsPage(driver);
        documentsPage
                .callPage()
                .pause();

        assertTrue(driver.getPageSource().contains("Document"));
    }

    @Test
    public void testDocumentPageOfferta() throws InterruptedException {
        DocumentsPage documentsPage = new DocumentsPage(driver);
        documentsPage
                .callPage()
                .pause()
                .click()
                .pause();

        assertTrue(driver.getPageSource().contains("Политика обработки персональных данных"));
    }

    @Test
    public void testDocumentPageRedirectMain() throws InterruptedException {
        DocumentsPage documentsPage = new DocumentsPage(driver);
        documentsPage
                .callPage()
                .pause()
                .click()
                .pause()
                .returnMain();

        assertTrue(driver.getPageSource().contains("Книги по тегам"));
    }
}