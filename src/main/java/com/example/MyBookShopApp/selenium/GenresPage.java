package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.example.MyBookShopApp.selenium.MainPage.URL_MAIN_PAGE;

public class GenresPage {

    private final ChromeDriver driver;

    public GenresPage(ChromeDriver driver) {
        this.driver = driver;
    }

    public GenresPage callPage() {
        driver.get(URL_MAIN_PAGE+"genres");
        return this;
    }

    public GenresPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

    public GenresPage clickDrama() {
        WebElement element = driver.findElement(By.id("Драматургия"));
        element.click();
        return this;
    }

    public GenresPage returnMain() {
        WebElement element = driver.findElement(By.id("mainFooter"));
        element.click();
        return this;
    }
}
