package melbourne;

import base.utils.BasicTest;
import base.utils.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTest extends BasicTest {
    public void registerCommon(String name, String email, String password, String rePassword, Boolean isAgree) {
        System.out.println("Open register page: " + Constant.REGISTER_URL);
        driver.get(Constant.REGISTER_URL);

        System.out.println("Enter name: " + name);
        WebElement nameField = driver.findElement(By.name("name"));
        nameField.clear();
        nameField.sendKeys(name);

        System.out.println("Enter email: " + email);
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.clear();
        emailField.sendKeys(email);

        System.out.println("Enter password: " + password);
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.clear();
        passwordField.sendKeys(password);

        System.out.println("Enter re-password: " + rePassword);
        WebElement confirmPasswordField = driver.findElement(By.name("confirm-password"));
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(rePassword);

        if(isAgree) {
            driver.findElement(By.cssSelector(".box")).click();
        }


        WebElement btnRegister = driver.findElement(By.xpath("/html/body/div/div[2]/section/div[1]/div/div/div/div[2]/div[6]/button"));
        new Actions(driver).moveToElement(btnRegister).perform();
        btnRegister.click();
    }

    @Test
    public void TC01() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getSuccessMessage(), "Create account successfully");
    }

    @Test
    public void TC02() {
        String name = fakeParagraphWithLimit(101);
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Fullname must be from 6 to 100 characters");
    }

    @Test
    public void TC03() {
        String name = fakeParagraphWithLimit(5);
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Fullname must be from 6 to 100 characters");
    }

    @Test
    public void TC4() {
        String name = fakeParagraphWithLimit(5);
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        registerCommon("", email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Enter your fullname");
    }

    @Test
    public void TC5() {
        String name = fakeParagraphWithLimit(5);
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        registerCommon("       ", email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Enter your fullname");
    }

    @Test
    public void TC06() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getSuccessMessage(), "Create account successfully");
    }

    @Test
    public void TC07() {
        String name = faker.name().fullName();
        String email = "demo01@gmail.com";
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Email already exists");
    }

    @Test
    public void TC08() {
        String name = faker.name().fullName();
        String email = faker.name().username();
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Wrong email format");
    }

    @Test
    public void TC09() {
        String name = faker.name().fullName();
        String email = faker.name().username() + "@gm";
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Wrong email format");
    }

    @Test
    public void TC10() {
        String name = faker.name().fullName();
        String email = faker.name().username() + "@gmail.";
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Wrong email format");
    }

    @Test
    public void TC11() {
        String name = faker.name().fullName();
        String email = "";
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Enter your email");
    }

    @Test
    public void TC12() {
        String name = faker.name().fullName();
        String email = "             ";
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Enter your email");
    }

    @Test
    public void TC13() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getSuccessMessage(), "Create account successfully");
    }

    @Test
    public void TC14() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(1, 7);
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Password must be from 8 to 100 characters");
    }

    @Test
    public void TC15() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 100);
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Password must be from 8 to 100 characters");
    }

    @Test
    public void TC16() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = "";
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Enter your password");
    }

    @Test
    public void TC17() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 100);
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getErrorMessage(), "Enter your password");
    }

    @Test
    public void TC19() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 100);
        String rePassword = faker.internet().password(8, 100);
        registerCommon(name, email, password, rePassword, true);

        Assert.assertEquals(getErrorMessage(), "Passwords do not match");
    }

    @Test
    public void TC20() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 100);
        String rePassword = "";
        registerCommon(name, email, password, rePassword, true);

        Assert.assertEquals(getErrorMessage(), "Enter your re-password");
    }

    @Test
    public void TC21() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 100);
        String rePassword = "            ";
        registerCommon(name, email, password, rePassword, true);

        Assert.assertEquals(getErrorMessage(), "Enter your re-password");
    }

    @Test
    public void TC23() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        registerCommon(name, email, password, password, true);

        Assert.assertEquals(getSuccessMessage(), "Create account successfully");
    }

    @Test
    public void TC24() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        registerCommon(name, email, password, password, false);

        Assert.assertEquals(getErrorMessage(), "Please agree to the policy and terms");
    }
}
