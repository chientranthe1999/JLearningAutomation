package melbourne.news;

import base.page.LoginPage;
import base.utils.BasicTest;
import base.utils.Message;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class NewsTest extends BasicTest {
    LoginPage loginPage;
    String selectedCategory = "Tin tức sự kiện";

    public void login() {
        loginPage = new LoginPage(driver, wait);
        loginPage.loginAdminRole().clickNews();
    }


    public void enterDescription(int limit) {
        StringBuilder fakeValue = new StringBuilder(faker.lorem().paragraph());
        while (fakeValue.length() < limit) {
            fakeValue.append(faker.lorem().paragraph());
        }

        fakeValue.setLength(limit);
        System.out.println("Enter course description, value: " + fakeValue);

        driver.findElement(By.xpath("//textarea")).sendKeys(
                fakeValue
        );
    }

    public void enterDescription(String text) {
        System.out.println("Enter course description, value: " + text);
        WebElement descriptionField = driver.findElement(By.xpath("//textarea"));
        descriptionField.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        descriptionField.sendKeys(text);
    }

    public void enterImage() {
        System.out.println("Enter image of course");
        String productDetailsPath = System.getProperty("user.dir")+ File.separator + "src/test/resources/testdata/product/productDetails.jpg";
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(productDetailsPath);
    }

    public void enterImage(String fileName) {
        System.out.println("Enter image of course");
        String productDetailsPath = System.getProperty("user.dir")+ File.separator + "src/test/resources/testdata/product/" + fileName;
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(productDetailsPath);
    }

    public void enterName(int limit) {
        String fakeValue = fakeParagraphWithLimit(limit);
        System.out.println("Enter name of course, value: " + fakeValue);
        enterName(fakeValue);
    }

    public void enterName(String text) {
        System.out.println("Enter name of course, value: " + text);
        driver.findElement(By.xpath("//body/div[2]/div[3]/div/div/div/div/div[1]/div/div[2]/div/div[2]/div/div[1]/div[1]/div/input")).sendKeys(text);
    }

    public void selectCategory(String text) throws InterruptedException {
        WebElement category = driver.findElement(By.xpath("//div[@id='demo-simple-select']"));
        helper.selectDropdownByText(category, text);
    }

    public void clickSave() {
        WebElement saveBtn = driver.findElement(By.xpath("//div[2]/div/button[2]"));
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
        js.executeScript("arguments[0].click();", saveBtn);
        System.out.println("Click btn Save");
    }

    @Test
    public void TC01_add_success() throws InterruptedException {
        login();
        helper.goToAddPage();

        enterImage();
        enterDescription(100);
        enterName(10);
        selectCategory("Tin tức sự kiện");
        clickSave();

        Assert.assertEquals(getSuccessMessage(), Message.NEWS_ADD_SUCCESS);
    }

    @Test
    public void TC02_empty_title() throws InterruptedException {
        login();
        helper.goToAddPage();

        selectCategory(selectedCategory);
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getErrorMessage(), Message.NEWS_EMPTY_TITLE);
    }

    @Test
    public void TC03_space_title() throws InterruptedException {
        login();
        helper.goToAddPage();

        enterName("                         ");
        selectCategory("");
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getErrorMessage(), Message.NEWS_EMPTY_TITLE);
    }

    @Test
    public void TC04_empty_category() throws InterruptedException {
        login();
        helper.goToAddPage();

        enterName(10);
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getErrorMessage(), Message.NEWS_EMPTY_CATEGORY);
    }

    @Test
    public void TC05_empty_description() throws InterruptedException {
        login();
        helper.goToAddPage();

        enterName(10);
        selectCategory(selectedCategory);
        clickSave();

        Assert.assertEquals(getErrorMessage(), Message.NEWS_EMPTY_DESCRIPTION);
    }

    @Test
    public void TC06_empty_cover() throws InterruptedException {
        login();
        helper.goToAddPage();

        enterName(10);
        selectCategory(selectedCategory);
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getErrorMessage(), Message.NEWS_EMPTY_PHOTO_COVER);
    }
}
