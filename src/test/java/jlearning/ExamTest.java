package jlearning;

import com.sat.utils.BasicTest;
import com.sat.utils.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static java.lang.Thread.sleep;

public class ExamTest extends BasicTest {
    public void loginAdminRoleAndOpenExamAdd() {
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

        driver.findElement(By.xpath("//span[contains(.,'Bài kiểm tra')]")).click();

        System.out.println("Click button add new exam");
        driver.findElement(By.xpath("//div[@id='root']/div[2]/main/div/div/div/div/button")).click();
    }

    public void enterName(int limit) {
        StringBuilder fakeValue = new StringBuilder(faker.lorem().paragraph());
        while (fakeValue.length() < limit) {
            fakeValue.append(faker.lorem().paragraph());
        }

        fakeValue.setLength(limit);
        System.out.println("Enter name of exam, value: " + fakeValue);
        driver.findElement(By.xpath("//body/div[2]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]"))
                .sendKeys(fakeValue.toString());
    }

    public void enterName(String text) {
        System.out.println("Enter name of exam, value: " + text);
        driver.findElement(By.xpath("//body/div[2]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]"))
                .sendKeys(text);
    }

    public void enterTime(int from, int to) {
        String fakeValue = String.valueOf(faker.number().numberBetween(from, to));
        System.out.println("Enter time of exam test, value: " + fakeValue);
        driver.findElement(By.xpath("//body/div[2]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/input[1]"))
                .sendKeys(fakeValue);
    }

    public void enterDescription(int limit) {
        StringBuilder fakeValue = new StringBuilder(faker.lorem().paragraph());
        while (fakeValue.length() < limit) {
            fakeValue.append(faker.lorem().paragraph());
        }

        fakeValue.setLength(limit);
        System.out.println("Enter test description, value: " + fakeValue);

        driver.findElement(By.xpath("//textarea")).sendKeys(
                fakeValue
        );
    }

    public void enterDescription(String text) {
        System.out.println("Enter test description, value: " + text);
        driver.findElement(By.xpath("//textarea")).sendKeys(text);
    }

    public void selectDropdownByText(WebElement selectElement, String text) throws InterruptedException {
        selectElement.click();
        sleep(500);
        WebElement selectContainer = driver.findElement(By.xpath("//body/div[@id='menu-']/div[3]/ul"));
        wait.until(ExpectedConditions.visibilityOf(selectContainer));

        List<WebElement> childElements = selectContainer.findElements(By.xpath("./li"));

        for (WebElement element: childElements) {
            if(element.getText().equals(text)) {
                System.out.println("Option selected: " + text);
                element.click();
            }
        }
    }

    public void clickSave() {
        System.out.println("Click save");
        WebElement btnSave = driver.findElement(By.xpath("//body/div[2]/div[3]/div[1]/div[1]/div[2]/button[2]"));
        js.executeScript("arguments[0].click();", btnSave);
    }

    @Test
    public void TC01() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterName(11);
        enterTime(10, 60);
        List<WebElement> selectFields = driver.findElements(By.xpath("//div[@id='demo-simple-select']"));
        System.out.println("Select course: " + courseVal);
        selectDropdownByText(selectFields.get(0), courseVal);
        System.out.println("Select chapter: " + chapter);
        selectDropdownByText(selectFields.get(1), chapter);
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getSuccessMessage(), "Thêm bài kiểm tra thành công");
    }

    @Test
    public void TC02_limit_exam_name() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterName(102);
        enterTime(10, 60);
        List<WebElement> selectFields = driver.findElements(By.xpath("//div[@id='demo-simple-select']"));
        System.out.println("Select course: " + courseVal);
        selectDropdownByText(selectFields.get(0), courseVal);
        System.out.println("Select chapter: " + chapter);
        selectDropdownByText(selectFields.get(1), chapter);
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getErrorMessage(), "Tên bài kiểm tra chỉ được phép từ 10 - 100 ký tự");
    }

    @Test
    public void TC03_limit_exam_name() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterName(102);
        enterTime(10, 60);
        List<WebElement> selectFields = driver.findElements(By.xpath("//div[@id='demo-simple-select']"));
        System.out.println("Select course: " + courseVal);
        selectDropdownByText(selectFields.get(0), courseVal);
        System.out.println("Select chapter: " + chapter);
        selectDropdownByText(selectFields.get(1), chapter);
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getErrorMessage(), "Tên bài kiểm tra chỉ được phép từ 10 - 100 ký tự");
    }

    @Test
    public void TC04_empty_exam_name() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterTime(10, 60);
        List<WebElement> selectFields = driver.findElements(By.xpath("//div[@id='demo-simple-select']"));
        System.out.println("Select course: " + courseVal);
        selectDropdownByText(selectFields.get(0), courseVal);
        System.out.println("Select chapter: " + chapter);
        selectDropdownByText(selectFields.get(1), chapter);
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getErrorMessage(), "Chưa nhập tên bài kiểm tra");
    }

    @Test
    public void TC05_empty_exam_name() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterName("            ");
        enterTime(10, 60);
        List<WebElement> selectFields = driver.findElements(By.xpath("//div[@id='demo-simple-select']"));
        System.out.println("Select course: " + courseVal);
        selectDropdownByText(selectFields.get(0), courseVal);
        System.out.println("Select chapter: " + chapter);
        selectDropdownByText(selectFields.get(1), chapter);
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getErrorMessage(), "Chưa nhập tên bài kiểm tra");
    }

    @Test
    public void TC07_empty_time() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterName(30);
        List<WebElement> selectFields = driver.findElements(By.xpath("//div[@id='demo-simple-select']"));
        System.out.println("Select course: " + courseVal);
        selectDropdownByText(selectFields.get(0), courseVal);
        System.out.println("Select chapter: " + chapter);
        selectDropdownByText(selectFields.get(1), chapter);
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getErrorMessage(), "Chưa nhập thời gian");
    }

    @Test
    public void TC09_empty_course() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterName(30);
        enterTime(10, 60);
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getErrorMessage(), "Chưa chọn khóa học");
    }

    @Test
    public void TC11_empty_chapter() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterName(30);
        enterTime(10, 60);
        List<WebElement> selectFields = driver.findElements(By.xpath("//div[@id='demo-simple-select']"));
        System.out.println("Select course: " + courseVal);
        selectDropdownByText(selectFields.get(0), courseVal);
        enterDescription(100);
        clickSave();

        Assert.assertEquals(getErrorMessage(), "Chưa chọn chương");
    }

    @Test
    public void TC14_limit_description() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterName(30);
        enterTime(10, 60);
        List<WebElement> selectFields = driver.findElements(By.xpath("//div[@id='demo-simple-select']"));
        System.out.println("Select course: " + courseVal);
        selectDropdownByText(selectFields.get(0), courseVal);
        System.out.println("Select chapter: " + chapter);
        selectDropdownByText(selectFields.get(1), chapter);
        enterDescription(201);
        clickSave();

        Assert.assertEquals(getErrorMessage(), "Mô tả bài kiểm tra chỉ được phép từ 10 - 200 ký tự");
    }

    @Test
    public void TC15_limit_description() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterName(30);
        enterTime(10, 60);
        List<WebElement> selectFields = driver.findElements(By.xpath("//div[@id='demo-simple-select']"));
        System.out.println("Select course: " + courseVal);
        selectDropdownByText(selectFields.get(0), courseVal);
        System.out.println("Select chapter: " + chapter);
        selectDropdownByText(selectFields.get(1), chapter);
        enterDescription(9);
        clickSave();

        Assert.assertEquals(getErrorMessage(), "Mô tả bài kiểm tra chỉ được phép từ 10 - 200 ký tự");
    }

    @Test
    public void TC16_empty_description() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterName(30);
        enterTime(10, 60);
        List<WebElement> selectFields = driver.findElements(By.xpath("//div[@id='demo-simple-select']"));
        System.out.println("Select course: " + courseVal);
        selectDropdownByText(selectFields.get(0), courseVal);
        System.out.println("Select chapter: " + chapter);
        selectDropdownByText(selectFields.get(1), chapter);
        clickSave();

        Assert.assertEquals(getErrorMessage(), "Chưa nhập mô tả");
    }

    @Test
    public void TC17_empty_description() throws InterruptedException {
        String courseVal = "Khóa học N4";
        String chapter = "Chương 1";

        loginAdminRoleAndOpenExamAdd();

        enterName(30);
        enterTime(10, 60);
        List<WebElement> selectFields = driver.findElements(By.xpath("//div[@id='demo-simple-select']"));
        System.out.println("Select course: " + courseVal);
        selectDropdownByText(selectFields.get(0), courseVal);
        System.out.println("Select chapter: " + chapter);
        selectDropdownByText(selectFields.get(1), chapter);
        enterDescription("        ");
        clickSave();

        Assert.assertEquals(getErrorMessage(), "Chưa nhập mô tả");
    }
}
