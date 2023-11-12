package com.sat.utils;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public abstract class BasicTest {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static Faker faker;
    protected static JavascriptExecutor js;

    @BeforeMethod
    public void preCondition() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        faker = new Faker(new Locale("en"));
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void postCondition(){
        // Quit the Browser
//        driver.quit();
    }

    public String getErrorMessage() {
        List<WebElement> errorMessage = driver.findElements(By.xpath("//div[contains(@class, 'Toastify__toast--warning')]"));

        WebElement lastMessage = errorMessage.get(errorMessage.size() - 1);
        wait.until(ExpectedConditions.visibilityOf(lastMessage));
        return lastMessage.getText();
    }

    public String getSuccessMessage() {
        List<WebElement> successMessages = driver.findElements(By.xpath("//div[contains(@class, 'Toastify__toast--success')]"));

        WebElement lastMessage = successMessages.get(successMessages.size() - 1);
        wait.until(ExpectedConditions.visibilityOf(lastMessage));
        return lastMessage.getText();
    }

    public String fakeParagraphWithLimit(int limit) {
        StringBuilder fakeValue = new StringBuilder(faker.lorem().paragraph());
        while (fakeValue.length() < limit) {
            fakeValue.append(faker.lorem().paragraph());
        }

        fakeValue.setLength(limit);
        return fakeValue.toString();
    }
}