package tests;

import co.com.camargo.pages.*;
import co.com.camargo.utils.ExcelUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PurchaseTest {
    WebDriver driver;
    LoginPage loginPage;
    SearchPage searchPage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    ExcelUtils excel;

    @BeforeMethod
    public void setUp() throws IOException {
        // Configurar ChromeDriver automáticamente
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://opencart.abstracta.us/");

        loginPage = new LoginPage(driver);
        searchPage = new SearchPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        excel = new ExcelUtils("src/test/resources/testdata.xlsx");
    }

    @Test
    public void testPurchaseFlow() {
        try {
            // Proceso de login
            loginPage.navigateToLogin();
            String email = excel.getCellData("Credenciales", 2, 0);
            String password = excel.getCellData("Credenciales", 2, 1);
            loginPage.enterEmail(email);
            loginPage.enterPassword(password);
            loginPage.clickLogin();
            Assert.assertTrue(loginPage.isLoggedIn(), "Login fallido!");

            // Variables para resultados
            List<String> successProducts = new ArrayList<>();
            List<String> failedProducts = new ArrayList<>();

            // Buscar y añadir productos
            int rows = excel.getRowCount("Productos");
            for (int i = 1; i < rows; i++) {
                String product = excel.getCellData("Productos", i, 0);
                String quantity = excel.getCellData("Productos", i, 1);

                searchPage.searchProduct(product);
                if (searchPage.isProductPresent(product)) {
                    searchPage.clickOnProduct(product);
                    productPage.setQuantity(quantity);
                    productPage.addToCart();
                    successProducts.add(product + " (Cantidad: " + quantity + ")");
                } else {
                    failedProducts.add(product);
                    System.out.println("Producto no encontrado: " + product);
                }
            }

            // Checkout
            cartPage.proceedToCheckout();
            checkoutPage.billingDetails();
            checkoutPage.shippingDetails();
            checkoutPage.deliveryMethod();
            checkoutPage.acceptTermsAndProceed();
            checkoutPage.confirmOrder();
            Assert.assertTrue(checkoutPage.isOrderSuccessful(), "La orden no fue exitosa!");

            // Generar reporte en consola
            System.out.println("\n=== Reporte de Resultados ===");
            System.out.println("Productos procesados correctamente:");
            successProducts.forEach(System.out::println);

            System.out.println("\nErrores encontrados:");
            failedProducts.forEach(System.out::println);

            // Generar archivo Excel (opcional)
            String excelOutputPath = "src/test/resources/output_report.xlsx";
            ExcelUtils.createReportExcel(successProducts, failedProducts, excelOutputPath);

        } catch (Exception e) {
            System.out.println("Error durante el flujo de compra: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Flujo de compra fallido.");
        }
    }


    @AfterMethod
    public void tearDown() throws IOException {
        excel.closeWorkbook();
        driver.quit();
    }
}
