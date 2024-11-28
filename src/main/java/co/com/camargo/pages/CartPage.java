package co.com.camargo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//button[contains(@class, 'dropdown-toggle') and contains(@class, 'btn-inverse')]")
    WebElement cartButton;

    @FindBy(xpath = "//a[contains(@href, 'checkout/checkout')]")
    WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }
}
