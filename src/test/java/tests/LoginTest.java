package tests;

import co.com.camargo.pages.LoginPage;
import co.com.camargo.utils.ExcelUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;
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
        excel = new ExcelUtils("src/test/resources/testdata.xlsx");
    }

    @Test
    public void testBadLogin() throws IOException {
        String resultMessage;
        try {
            // Navegar a la página de login
            loginPage.navigateToLogin();

            // Leer credenciales inválidas desde Excel
            String email = excel.getCellData("Credenciales", 1, 0);
            String password = excel.getCellData("Credenciales", 1, 1);

            // Ingresar credenciales y realizar login
            loginPage.enterEmail(email);
            loginPage.enterPassword(password);
            loginPage.clickLogin();

            // Validar login fallido esperado
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            boolean loginSuccess = loginPage.isLoggedIn();
            Assert.assertFalse(loginSuccess, "El login fallido es el comportamiento esperado.");

            resultMessage = "Login con credenciales inválidas falló correctamente.";
        } catch (Exception e) {
            resultMessage = "Error en el testBadLogin: " + e.getMessage();
            e.printStackTrace();
        }

        // Generar reporte en consola
        System.out.println("Resultado de testBadLogin: " + resultMessage);

        // Generar reporte en Excel
        ExcelUtils.addTestResult("LoginTest", "testBadLogin", resultMessage, "src/test/resources/output_report.xlsx");
    }

    @Test
    public void testGoodLogin() {
        String resultMessage;
        try {
            loginPage.navigateToLogin();

            String email = excel.getCellData("Credenciales", 2, 0);
            String password = excel.getCellData("Credenciales", 2, 1);

            loginPage.enterEmail(email);
            loginPage.enterPassword(password);
            loginPage.clickLogin();

            boolean loginSuccess = loginPage.isLoggedIn();
            Assert.assertTrue(loginSuccess, "El login exitoso es el comportamiento esperado.");
            resultMessage = "Login con credenciales válidas fue exitoso.";
        } catch (Exception e) {
            resultMessage = "Error en el testGoodLogin: " + e.getMessage();
            e.printStackTrace();
        }

        // Generar reporte en consola
        System.out.println("Resultado de testGoodLogin: " + resultMessage);

        // Generar reporte en Excel
        try {
            ExcelUtils.addTestResult("LoginTest", "testGoodLogin", resultMessage, "src/test/resources/login_report.xlsx");
        } catch (IOException ioException) {
            System.err.println("Error al generar el reporte Excel: " + ioException.getMessage());
        }
    }




    @AfterMethod
    public void tearDown() throws IOException {
        excel.closeWorkbook();
        driver.quit();
    }
}
