package melbourne.course;

import base.page.LoginPage;
import base.utils.Message;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;

import static java.lang.Thread.sleep;


public class CourseEditTest extends CourseTest {
    LoginPage loginPage;

    public void login() {
        loginPage = new LoginPage(driver, wait);
        loginPage.loginAdminRole().clickCourse();
    }
    public void editFirstResult() {
        login();
        List<WebElement> searchResult = getListCourse();
        Assert.assertFalse(searchResult.isEmpty());
        searchResult.get(0).findElement(By.xpath(".//button")).click();
    }

    public List<WebElement> search(String keyword) {
        return helper.searchAndGetList(keyword);
    }

    public List<WebElement> getListCourse() {
        List<WebElement> searchResult = driver.findElements(By.xpath("//tbody//tr"));
        System.out.println("Table search result length: " + searchResult.size());
        return searchResult;
    }

    public void inputCourseName(String courseName) throws InterruptedException {
        System.out.println("Enter course name :" + courseName);
        WebElement courseField =  driver.findElement(By.xpath("//body/div/div[2]/main/div/div[2]/div[2]/div/div[1]/div/input"));
        clearInput(courseField);
        if(!courseName.isEmpty()) {
            courseField.sendKeys(courseName);
        }
    }

    public void clearInput(WebElement inputField) {
        inputField.click();
        inputField.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
    }

    public void inputStudyTime() {
        System.out.println("Enter study time: ");
        WebElement periodField =  driver.findElement(By.xpath("//body/div/div[2]/main/div/div[2]/div[2]/div/div[2]//input"));
        clearInput(periodField);
    }

    public void clickCheckbox() {
        driver.findElement(By.xpath("//input[@type='checkbox']")).click();
    }

    public void enterChapterName(String name) {
        WebElement chapterNameField = driver.findElement(By.xpath("//body/div[2]/div[3]/div/div/div/div[1]/div[1]/div/input"));
        clearInput(chapterNameField);
        chapterNameField.sendKeys(name);
    }

    public void enterChapterDescription(String description) {
        WebElement descriptionField = driver.findElement(By.xpath("//body/div[2]/div[3]/div/div/div/div[1]/div[2]/div/textarea"));
        clearInput(descriptionField);
        descriptionField.sendKeys(description);
    }

    public void clickSaveChapter() {
        WebElement btnSave = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div/div[2]/div/button[2]"));
        js.executeScript("arguments[0].click();", btnSave);
    }

    public void clickSave() {
        WebElement btnSave = driver.findElement(By.xpath("//body/div/div[2]/main/div/div[2]/div[3]/button[2]"));
        js.executeScript("arguments[0].click();", btnSave);
        System.out.println("Click btn Save");
    }

    public void clickAddChapter() {
        driver.findElement(By.xpath("/html/body/div/div[2]/main/div/div[1]/div/div/div[1]/button")).click();
    }

    public List<WebElement> getChapterList() {
        return driver.findElements(By.xpath("//body/div/div[2]/main/div/div[1]/div/div/div[2]/div"));
    }

    public void deleteChapter(String chapterName) {
        List<WebElement> chapters = getChapterList();

        WebElement finedChapter = null;
        for(WebElement chapter : chapters) {
            if(chapter.findElement(By.xpath(".//p[1]")).getText().trim().equals(chapterName)) {
                finedChapter = chapter;
                break;
            }
        }

        if(Objects.isNull(finedChapter)) {
            Assert.fail("Can't find chapter with name: " + chapterName);
        }

        System.out.println("Founded chapter: " + chapterName);

        js.executeScript("arguments[0].scrollIntoView(true);", finedChapter);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", finedChapter);

        finedChapter.click();

        System.out.println("Click delete chapter");
        finedChapter.findElement(By.xpath(".//button[2]")).click();

        System.out.println("Click Continue Delete chapter");
        driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div/div/button[2]")).click();
    }

    @Test(priority = 1)
    public void TC01_empty_course_name() throws InterruptedException {
        editFirstResult();
        inputCourseName("");
        clickSave();
        Assert.assertEquals(getErrorMessage(), Message.COURSE_NAME_EMPTY);
    }

    @Test(priority = 2)
    public void TC02_empty_period() throws InterruptedException {
        editFirstResult();

        inputStudyTime();
        clickSave();
        Assert.assertEquals(getErrorMessage(), Message.STUDY_TIME_EMPTY);
    }

    @Test(priority = 3)
    public void TC03_empty_description() throws InterruptedException {
        editFirstResult();

        enterDescription("");
        clickSave();
        Assert.assertEquals(getErrorMessage(), Message.COURSE_DESCRIPTION_EMPTY);
    }

    @Test(priority = 4)
    public void TC04_successfully() throws InterruptedException {
        editFirstResult();

        String newCourseName = fakeParagraphWithLimit(10);
        String newDescription = fakeParagraphWithLimit(100);

        inputCourseName(newCourseName);
        enterDescription(newDescription);
        clickSave();
        Thread.sleep(500);
        Assert.assertEquals(getSuccessMessage(), Message.UPDATE_COURSE_SUCCESS);

        loginPage.clickCourse();
        List<WebElement> result = search(newCourseName);

        Assert.assertFalse(result.isEmpty());
    }

    @Test(priority = 5)
    public void TC05_add_chapter() throws InterruptedException {
        editFirstResult();

        String chapterName = fakeParagraphWithLimit(10);
        String description = fakeParagraphWithLimit(100);
        clickAddChapter();
        enterChapterName(chapterName);
        enterChapterDescription(description);
        clickSaveChapter();
        Thread.sleep(500);
        Assert.assertEquals(getSuccessMessage(), Message.ADD_CHAPTER_SUCCESS);

        deleteChapter(chapterName);

        Assert.assertEquals(getSuccessMessage(), Message.DELETE_CHAPTER_SUCCESS);
    }

    @Test(priority = 6)
    public void TC06_empty_chapter_name() throws InterruptedException {
        editFirstResult();

        String chapterName = fakeParagraphWithLimit(10);
        String description = fakeParagraphWithLimit(100);
        clickAddChapter();
        enterChapterDescription(description);
        clickSaveChapter();
        Thread.sleep(500);
        Assert.assertEquals(getErrorMessage(), Message.CHAPTER_REQUIRED);
    }

    @Test(priority = 7)
    public void TC07_empty_chapter_description() throws InterruptedException {
        editFirstResult();

        String chapterName = fakeParagraphWithLimit(10);
        String description = fakeParagraphWithLimit(100);
        clickAddChapter();
        enterChapterDescription(description);
        clickSaveChapter();
        Thread.sleep(500);
        Assert.assertEquals(getErrorMessage(), Message.CHAPTER_REQUIRED);
    }

    @Test(priority = 8)
    public void TC08_empty_chapter_description() throws InterruptedException {
        editFirstResult();

        String chapterName = fakeParagraphWithLimit(10);
        String description = fakeParagraphWithLimit(100);
        clickAddChapter();
        enterChapterDescription(description);
        clickSaveChapter();
        Thread.sleep(500);
        Assert.assertEquals(getErrorMessage(), Message.CHAPTER_REQUIRED);
    }


    @Test(priority = 9)
    public void TC09_empty_search_result() throws InterruptedException {
        login();

        List<WebElement> result = search(faker.name().fullName());

        Assert.assertEquals(result.size(), 0);
    }

    @Test(priority = 9)
    public void TC10_search() throws InterruptedException {
        loginAdminRoleAndOpenCourseAdd();

        enterImage();
        List<WebElement> inputField = driver.findElements(By.xpath("//div[2]/div/div/div/input"));
        String name = faker.name().fullName();
        enterName(inputField.get(0), name);
        enterPeriod(inputField.get(1), 1, 36);
        enterPrice(inputField.get(2), 10000, 100000);
        enterDescription(100);

        driver.findElement(By.xpath("//div[2]/div/button[2]")).click();

        sleep(500);

        Assert.assertEquals(Message.ADD_COURSE_SUCCESS, getSuccessMessage());

        Assert.assertEquals(search(name).size(), 1);
    }
}
