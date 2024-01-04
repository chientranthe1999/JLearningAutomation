package melbourne;

import base.utils.BasicTest;
import base.utils.Constant;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class LoginTest extends BasicTest {

    public void openLoginPage() {
        System.out.println("Open login page " + Constant.LOGIN_URL);
        driver.get(Constant.LOGIN_URL);

    }

    public void loginCommon(String email, String password) {
        openLoginPage();
        WebElement loginBtn = driver.findElement(By.cssSelector(".px-9"));

        wait.until(
                ExpectedConditions.elementToBeClickable(loginBtn)
        );
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.clear();
        System.out.println("Enter email: " + email);
        emailField.sendKeys(email);

        System.out.println("Enter password: " + password);
        driver.findElement(By.name("password")).sendKeys(password);

        loginBtn.click();
    }

    @Test
    public void TC01_LoginSuccess() {
        loginCommon("demo01@gmail.com", "12345678");
        Assert.assertEquals(getSuccessMessage(), "Login Successfully");
    }

    @Test
    public void TC02_LoginSuccess() {
        loginCommon("    demo01@gmail.com     ", "12345678");
        Assert.assertEquals(getSuccessMessage(), "Login Successfully");
    }

    @Test
    public void TC03_LoginSuccess() {
        loginCommon("    DEMO01@gmail.com     ", "12345678");
        Assert.assertEquals(getSuccessMessage(), "Login Successfully");
    }

    @Test
    public void TC04() {
        loginCommon("    test@gmail.com     ", "12345678");
        Assert.assertEquals(getErrorMessage(), "Incorrect account or password");
    }

    @Test
    public void TC05() {
        loginCommon("test@gm.co", "123456");
        Assert.assertEquals(getErrorMessage(), "Wrong email format!");
    }

    @Test
    public void TC06() {
        loginCommon(" demo01@gmail.com", "12345678910");
        Assert.assertEquals(getErrorMessage(), "Incorrect account or password");
    }

    @Test
    public void TC07_EmptyEmail() {
        loginCommon("", "12345678910");
        Assert.assertEquals(getErrorMessage(), "Enter your email!");
    }

    @Test
    public void TC08_EmptyPassword() {
        loginCommon("demo01@gmail.com", "");
        Assert.assertEquals(getErrorMessage(), "Enter your password!");
    }

    @Test
    public void TC09_EmptyPasswordAndEmail() {
        loginCommon("", "");
        Assert.assertEquals(getErrorMessage(), "Enter your email or password!");
    }

    @Test
    public void TC10_SpaceEmail() {
        loginCommon("", "1");
        Assert.assertEquals(getErrorMessage(), "Enter your email!");
    }

    @Test
    public void TC11_ForgetPassLink() {
        openLoginPage();
        driver.findElement(By.xpath("//a[@href='/forgot-password']")).click();
        wait.until(ExpectedConditions.urlContains(Constant.FORGET_PASSWORD_URL));
    }
}
