package melbourne;

import base.utils.BasicTest;
import base.utils.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class PaymentTest extends BasicTest {
    public void loginUserRoleAndGoToCourseList() {
        System.out.println("Open login page " + Constant.LOGIN_URL);
        driver.get(Constant.LOGIN_URL);
        WebElement loginBtn = driver.findElement(By.cssSelector(".px-9"));

        wait.until(
                ExpectedConditions.elementToBeClickable(loginBtn)
        );

        System.out.println("Login role admin");
        driver.findElement(By.name("email")).sendKeys("demo01@gmail.com");
        driver.findElement(By.name("password")).sendKeys("12345678");
        loginBtn.click();
        System.out.println("Login success " + getSuccessMessage());

        System.out.println("Click course list");
        driver.findElement(By.xpath("//nav[@id='navbarCollapse']/ul/li[2]/a")).click();

        List<WebElement> btnList = driver.findElements(By.xpath("//button[contains(.,'Buy')]"));

        js.executeScript("arguments[0].scrollIntoView(true);", btnList.get(0));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnList.get(0));
        btnList.get(0).click();

        wait.until(ExpectedConditions.urlContains(Constant.PAYMENT_URL));
    }

    public String fakePhone() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            stringBuilder.append(random.nextInt(10)); // Append random digits (0-9)
        }

        return stringBuilder.toString();
    }

    @Test
    public void TC01_buy_course_successfully() {
        loginUserRoleAndGoToCourseList();

        System.out.println("Enter phone number");
        driver.findElement(By.xpath("//div[2]/div/input")).sendKeys(fakePhone());

        System.out.println("Enter address");
        driver.findElement(By.xpath("//textarea")).sendKeys(faker.address().fullAddress());

        js.executeScript("window.scrollTo(0,303.4693298339844)");

        System.out.println("Click buy");
        driver.findElement(By.xpath("//button[2]")).click();

        wait.until(ExpectedConditions.urlContains("sandbox"));
        driver.findElement(By.xpath("//div[@id='accordionList']/div[2]")).click();
        System.out.println("Redirect to VNPay sandbox: " + driver.getCurrentUrl());

        driver.findElement(By.xpath("//button[@value='NCB']")).click();

        WebElement continueBtn = driver.findElement(By.xpath("//button[@id='btnContinue']"));
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn));

        driver.findElement(By.xpath("//input[@id='card_number_mask']")).sendKeys("9704198526191432198");
        driver.findElement(By.xpath("//input[@id='cardHolder']")).sendKeys("NGUYEN VAN A");
        driver.findElement(By.xpath("//input[@id='cardDate']")).sendKeys("07/15");
        continueBtn.click();

        System.out.println("Redirect to OTP input: " + driver.getCurrentUrl());
        WebElement confirmBtn = driver.findElement(By.xpath("//button[@id='btnConfirm']"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmBtn));

        driver.findElement(By.xpath("//input[@id='otpvalue']")).sendKeys("123456");
        confirmBtn.click();

        wait.until(ExpectedConditions.urlContains(Constant.PAYMENT_RESULT_URL));
        System.out.println("After payment success -> redirect url: " + driver.getCurrentUrl());

        Assert.assertEquals("Successfully registered for the course", getSuccessMessage());
    }

    @Test
    public void TC02_empty_phone() {
        loginUserRoleAndGoToCourseList();

        System.out.println("Enter address");
        driver.findElement(By.xpath("//textarea")).sendKeys(faker.address().fullAddress());

        System.out.println("Click buy");
        driver.findElement(By.xpath("//button[2]")).click();

        Assert.assertEquals("Please enter your phone number", getErrorMessage());
    }

    @Test
    public void TC03_wrong_phone_format() {
        loginUserRoleAndGoToCourseList();

        System.out.println("Enter address");
        driver.findElement(By.xpath("//textarea")).sendKeys(faker.address().fullAddress());

        System.out.println("Enter phone number");
        driver.findElement(By.xpath("//div[2]/div/input")).sendKeys("123456789");

        System.out.println("Click buy");
        driver.findElement(By.xpath("//button[2]")).click();

        Assert.assertEquals("Invalid phone number", getErrorMessage());
    }

    @Test
    public void TC04_wrong_phone_format() {
        loginUserRoleAndGoToCourseList();

        System.out.println("Enter address");
        driver.findElement(By.xpath("//textarea")).sendKeys(faker.address().fullAddress());

        System.out.println("Enter phone number");
        driver.findElement(By.xpath("//div[2]/div/input")).sendKeys("12345678910");

        System.out.println("Click buy");
        driver.findElement(By.xpath("//button[2]")).click();

        Assert.assertEquals("Invalid phone number", getErrorMessage());
    }

    @Test
    public void TC5_empty_address() {
        loginUserRoleAndGoToCourseList();

        System.out.println("Enter phone number");
        driver.findElement(By.xpath("//div[2]/div/input")).sendKeys("1234567891");

        System.out.println("Click buy");
        driver.findElement(By.xpath("//button[2]")).click();

        Assert.assertEquals("Please enter your address", getErrorMessage());
    }
}
