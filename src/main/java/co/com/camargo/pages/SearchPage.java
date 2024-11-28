package co.com.camargo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
import java.util.NoSuchElementException;

public class SearchPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(name = "search")
    WebElement searchBar;

    @FindBy(xpath = "//button[@type='button' and contains(@class, 'btn-default')]")
    WebElement searchButton;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOf(searchBar)).clear();
        searchBar.sendKeys(productName);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }


    public WebElement waitForElement(WebDriver driver, By locator) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }




    public boolean isProductPresent(String productName) {
        try {
            WebElement product = waitForElement(driver, By.xpath("//div[contains(@class, 'product-thumb')]//h4/a[contains(text(), '" + productName + "')]"));
            return product != null;
        } catch (Exception e) {
            System.out.println("No se encontró el producto: " + productName);
            return false;
        }
    }

    public void clickOnProduct(String productName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'product-thumb')]//h4/a[contains(text(), '" + productName + "')]")));
            productLink.click();
        } catch (Exception e) {
            System.out.println("Error al intentar hacer clic en el producto: " + productName);
        }
    }

}
