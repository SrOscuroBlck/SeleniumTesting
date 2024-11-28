package co.com.camargo.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(linkText = "My Account")
    WebElement myAccount;

    @FindBy(linkText = "Login")
    WebElement loginOption;

    @FindBy(id = "input-email")
    WebElement emailField;

    @FindBy(id = "input-password")
    WebElement passwordField;

    @FindBy(xpath = "//input[@type='submit' and @value='Login']")
    WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Espera explícita de 10 segundos
        PageFactory.initElements(driver, this);
    }

    public void navigateToLogin() {
        // Esperar que 'My Account' sea clickeable antes de hacer click
        wait.until(ExpectedConditions.elementToBeClickable(myAccount)).click();
        // Esperar que 'Login' sea visible antes de hacer click
        wait.until(ExpectedConditions.elementToBeClickable(loginOption)).click();
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public boolean isLoggedIn() {
        try {
            // Esperar hasta que la URL contenga "route=account/account"
            wait.until(ExpectedConditions.urlContains("route=account/account"));
            System.out.println("Login exitoso: redirigido a la página de cuenta.");
            return true;
        } catch (TimeoutException e) {
            System.out.println("Login fallido: no se detectó redirección a la página de cuenta.");
            return false;
        }
    }

}
