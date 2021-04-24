package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.example.MyBookShopApp.selenium.MainPage.URL_MAIN_PAGE;

public class DocumentsPage {

    private final ChromeDriver driver;

    public DocumentsPage(ChromeDriver driver) {
        this.driver = driver;
    }

    public DocumentsPage callPage() {
        driver.get(URL_MAIN_PAGE+"documents");
        return this;
    }

    public DocumentsPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

    public DocumentsPage click() {
        WebElement element = driver.findElement(By.id("offerta"));
        element.click();
        return this;
    }

    public DocumentsPage returnMain() {
        WebElement element = driver.findElement(By.id("mainFooter"));
        element.click();
        return this;
    }
}
