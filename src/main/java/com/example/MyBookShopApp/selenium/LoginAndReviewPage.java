package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.example.MyBookShopApp.selenium.MainPage.URL_MAIN_PAGE;

public class LoginAndReviewPage {

    private final ChromeDriver driver;

    public LoginAndReviewPage(ChromeDriver driver) {
        this.driver = driver;
    }

    public LoginAndReviewPage callMainPage() {
        driver.get(URL_MAIN_PAGE);
        return this;
    }

    public LoginAndReviewPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

    public LoginAndReviewPage loginPage() {
        WebElement element = driver.findElement(By.id("login"));
        element.click();
        return this;
    }

    public LoginAndReviewPage submitEmail() {
        WebElement element = driver.findElement(By.id("typeauth"));
        element.click();
        return this;
    }

    public LoginAndReviewPage setUpEmail() {
        WebElement element = driver.findElement(By.id("mail"));
        String email = "skillbox@inbox.ru";
        element.sendKeys(email);
        return this;
    }

    public LoginAndReviewPage clickSendAuth() {
        WebElement element = driver.findElement(By.id("sendauth"));
        element.click();
        return this;
    }

    public LoginAndReviewPage setUpCode() {
        WebElement element = driver.findElement(By.id("mailcode"));
        String password = "123123";
        element.sendKeys(password);
        return this;
    }

    public LoginAndReviewPage clickLogin() {
        WebElement element = driver.findElement(By.id("toComeInMail"));
        element.click();
        return this;
    }

    public LoginAndReviewPage setUpSearchToken(String token) {
        WebElement element = driver.findElement(By.id("query"));
        element.sendKeys(token);
        return this;
    }

    public LoginAndReviewPage submit() {
        WebElement element = driver.findElement(By.id("search"));
        element.submit();
        return this;
    }

    public LoginAndReviewPage clickBookBySlugId(){
        WebElement element = driver.findElement(By.id("book-qtp-317"));
        element.click();
        return this;
    }

    public LoginAndReviewPage addTextReview(){
        WebElement element = driver.findElement(By.id("reviewText"));
        element.sendKeys("Прекрасное чтиво, всем советую");
        return this;
    }

    public LoginAndReviewPage addStarReview(){
        WebElement element = driver.findElement(By.id("ratingReview5"));
        element.click();
        return this;
    }

    public LoginAndReviewPage addReview(){
        WebElement element = driver.findElement(By.id("addReview"));
        element.click();
        return this;
    }
}
