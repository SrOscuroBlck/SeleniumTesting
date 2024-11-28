package co.com.camargo.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(name = "agree")
    WebElement termsCheckbox;

    @FindBy(id = "button-payment-address")
    WebElement paymentAddressConfirmButton;

    @FindBy(id = "button-shipping-address")
    WebElement shippingAddressConfirmButton;

    @FindBy(id = "button-shipping-method")
    WebElement shippingMethodButton;

    @FindBy(id = "button-payment-method")
    WebElement paymentMethodButton;

    @FindBy(id = "button-confirm")
    WebElement confirmOrderButton;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void billingDetails() {
        wait.until(ExpectedConditions.elementToBeClickable(paymentAddressConfirmButton)).click();
    }

    public void shippingDetails() {
        wait.until(ExpectedConditions.elementToBeClickable(shippingAddressConfirmButton)).click();
    }

    public void deliveryMethod() {
        wait.until(ExpectedConditions.elementToBeClickable(shippingMethodButton)).click();
    }


    public void acceptTermsAndProceed() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(termsCheckbox)).click();
            wait.until(ExpectedConditions.elementToBeClickable(paymentMethodButton)).click();
        } catch (TimeoutException e) {
            System.out.println("Error: No se pudo interactuar con el botón o checkbox. Verifica su disponibilidad.");
            throw e; // Relanza la excepción para debug
        }
    }


    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton)).click();
    }

    public boolean isOrderSuccessful() {
        try {
            // Espera hasta que la URL contenga "route=checkout/success"
            wait.until(ExpectedConditions.urlContains("route=checkout/success"));
            System.out.println("Página de confirmación de orden cargada exitosamente.");
            return true;
        } catch (Exception e) {
            System.out.println("La página de confirmación de orden no se cargó. URL actual: " + driver.getCurrentUrl());
            return false;
        }
    }

}
