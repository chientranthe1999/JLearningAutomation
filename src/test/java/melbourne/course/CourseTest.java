package melbourne.course;

import base.utils.BasicTest;
import base.utils.Constant;
import base.utils.Message;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static java.lang.Thread.sleep;

public class CourseTest extends BasicTest {

    public void loginAdminRoleAndOpenCourseAdd() {
        System.out.println("Open login page " + Constant.LOGIN_URL);
        driver.get(Constant.LOGIN_URL);
        WebElement loginBtn = driver.findElement(By.cssSelector(".px-9"));

        wait.until(
                ExpectedConditions.elementToBeClickable(loginBtn)
        );

        System.out.println("Login role admin");
        driver.findElement(By.name("email")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        loginBtn.click();
        System.out.println("Login success " + getSuccessMessage());

        driver.findElement(By.xpath("//span[contains(.,'Course')]")).click();

        System.out.println("Click button add new course");
        driver.findElement(By.xpath("//div[@id='root']/div[2]/main/div/div/div/div/button")).click();
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

    public void enterPrice(WebElement inputField, int from, int to) {
        String fakeValue = String.valueOf(faker.number().numberBetween(from, to));
        System.out.println("Enter price, value: " + fakeValue);
        inputField.sendKeys(fakeValue);
    }

    public void enterPeriod(WebElement inputField, int from, int to) {
        String fakeValue = String.valueOf(faker.number().numberBetween(from, to));
        System.out.println("Enter period, value: " + fakeValue);
        inputField.sendKeys(fakeValue);
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

    public void enterName(WebElement inputField, int limit) {
        String fakeValue = fakeParagraphWithLimit(limit);
        System.out.println("Enter name of course, value: " + fakeValue);
        inputField.sendKeys(fakeValue);
    }

    public void enterName(WebElement inputField, String text) {
        System.out.println("Enter name of course, value: " + text);
        inputField.sendKeys(text);
    }

    @Test
    public void TC01_add_successfully() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), 11);
        enterPeriod(inputField.get(1), 1, 36);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(100);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();

        sleep(500);

        Assert.assertEquals(Message.ADD_COURSE_SUCCESS, getSuccessMessage());
    }

    @Test
    public void TC02_limit_course_name() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), 101);
        enterPeriod(inputField.get(1), 1, 36);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(100);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
        sleep(500);
        Assert.assertEquals(Message.COURSE_NAME_LIMIT, getErrorMessage());
    }

    @Test
    public void TC03_limit_course_name() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), 9);
        enterPeriod(inputField.get(1), 1, 36);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(100);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();

        sleep(500);

        Assert.assertEquals(Message.COURSE_NAME_LIMIT, getErrorMessage());
    }

    @Test
    public void TC04_empty_course_name() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterPeriod(inputField.get(1), 1, 36);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(100);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
        sleep(500);

        Assert.assertEquals(Message.COURSE_NAME_EMPTY, getErrorMessage());
    }

    @Test
    public void TC05_space_course_name() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), "        ");
        enterPeriod(inputField.get(1), 1, 36);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(100);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
        sleep(500);

        Assert.assertEquals(Message.COURSE_NAME_EMPTY, getErrorMessage());
    }

    @Test
    public void TC07_empty_period() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), 80);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(100);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
        sleep(500);
        Assert.assertEquals(Message.STUDY_TIME_EMPTY, getErrorMessage());
    }

    @Test
    public void TC08_limit_period() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), 80);
        enterPeriod(inputField.get(1), 37,37);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(100);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
        sleep(500);
        Assert.assertEquals(Message.STUDY_TIME_LIMIT, getErrorMessage());
    }

    @Test
    public void TC10_limit_description() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), 80);
        enterPeriod(inputField.get(1), 1,36);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(202);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
        sleep(500);
        Assert.assertEquals(Message.COURSE_DESCRIPTION_LIMIT, getErrorMessage());
    }

    @Test
    public void TC11_limit_description() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), 80);
        enterPeriod(inputField.get(1), 1,36);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(9);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
        sleep(500);
        Assert.assertEquals(Message.COURSE_DESCRIPTION_LIMIT, getErrorMessage());
    }

    @Test
    public void TC12_empty_description() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), 80);
        enterPeriod(inputField.get(1), 1,36);
        enterPrice(inputField.get(2), 10000, 100000);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
        sleep(500);
        Assert.assertEquals(Message.COURSE_DESCRIPTION_EMPTY, getErrorMessage());
    }

    @Test
    public void TC13_space_description() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), 80);
        enterPeriod(inputField.get(1), 1,36);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription("        ");
        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
        sleep(500);
        Assert.assertEquals(Message.COURSE_DESCRIPTION_EMPTY, getErrorMessage());
    }

//    @Test
//    public void TC14_empty_description() throws InterruptedException {
//        loginAdminRoleAndOpenCourseAdd();
//
//        enterImage();
//        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
//        enterName(inputField.get(0), 80);
//        enterPeriod(inputField.get(1), 1,36);
//        enterPrice(inputField.get(2), 10000, 100000);
//
//        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
//        sleep(500);
//        Assert.assertEquals("Chưa nhập mô tả", getErrorMessage());
//    }

    @Test
    public void TC15_empty_image() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), 80);
        enterPeriod(inputField.get(1), 1,36);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(100);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
        sleep(500);
        Assert.assertEquals(Message.PHOTO_EMPTY, getErrorMessage());
    }

    @Test
    public void TC16_wrong_image_format() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();
        enterImage("test.txt");

        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        enterName(inputField.get(0), 80);
        enterPeriod(inputField.get(1), 1,36);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(100);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();
        sleep(500);
        Assert.assertEquals(Message.PHOTO_WRONG_FORMAT, getErrorMessage());
    }
}
