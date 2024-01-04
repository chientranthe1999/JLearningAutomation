package base.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static java.lang.Thread.sleep;

public class Helper {
    public WebDriver driver;
    public WebDriverWait wait;


    public Helper(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
    public void search(String keyword) {
        WebElement searchInput = driver.findElement(By.xpath("//input[contains(@class, 'h-full')]"));
        searchInput.sendKeys("");
        searchInput.sendKeys(keyword);
    }

    public List<WebElement> getListResult() {
        List<WebElement> searchResult = driver.findElements(By.xpath("//tbody//tr"));
        System.out.println("Table search result length: " + searchResult.size());
        return searchResult;
    }

    public List<WebElement> searchAndGetList(String keyword) {
        search(keyword);
        return getListResult();
    }

    public void goToAddPage() {
        driver.findElement(By.xpath("//div[@id='root']/div[2]/main/div/div/div/div/button")).click();
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
}
