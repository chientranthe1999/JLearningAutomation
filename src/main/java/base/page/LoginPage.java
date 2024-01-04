package base.page;

import base.utils.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
    @FindBy(name = "email")
    public WebElement emailField;
    @FindBy(name = "password")
    public WebElement passwordField;
    @FindBy(css = ".px-9")
    public WebElement loginBtn;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver);
        this.url = Constant.LOGIN_URL;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public LoginPage loginAdminRole() {
        this.openCurrentPage();

        wait.until(ExpectedConditions.elementToBeClickable(loginBtn));

        System.out.println("Login role admin");
        emailField.sendKeys("admin");
        passwordField.sendKeys("admin");
        loginBtn.click();
        System.out.println("Login success " + getSuccessMessage());
        return this;
    }

    public LoginPage clickCourse() {
        driver.findElement(By.xpath("//nav/ul/li[2]")).click();
        return this;
    }

    public LoginPage clickNews() {
        driver.findElement(By.xpath("//nav/ul/li[5]")).click();
        return this;
    }
}
