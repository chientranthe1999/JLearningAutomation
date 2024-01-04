package base.page;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BasePage {
    WebDriver driver;
    static WebDriverWait wait;
    static Faker faker;
    static JavascriptExecutor js;

    public String url;

    public BasePage(WebDriver driver) {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        wait = new WebDriverWait(driver, 10);
//        faker = new Faker(new Locale("en"));
//        js = (JavascriptExecutor) driver;
//        driver.manage().window().maximize();
        this.driver = driver;
    }



    public String getErrorMessage() {
        List<WebElement> errorMessage = driver.findElements(By.xpath("//div[contains(@class, 'Toastify__toast--warning')]"));
        WebElement lastMessage = errorMessage.get(errorMessage.size() - 1);
        wait.until(ExpectedConditions.visibilityOf(lastMessage));
        System.out.println("Error message: " + lastMessage.getText());
        return lastMessage.getText();
    }

    public String getSuccessMessage() {
        List<WebElement> successMessages = driver.findElements(By.xpath("//div[contains(@class, 'Toastify__toast--success')]"));

        WebElement lastMessage = successMessages.get(successMessages.size() - 1);
        wait.until(ExpectedConditions.visibilityOf(lastMessage));
        System.out.println("Message: " + lastMessage.getText());
        return lastMessage.getText();
    }

    @Step("Open page")
    public void openCurrentPage() {
        System.out.println("Open page " + this.url);
        driver.get(this.url);
    }
}
