import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class UserSignupAndDeletionTest {

    private static final String BASE_URL = "https://automationexercise.com/";
    private static final String TEST_NAME = "Dariya";
    private static final String TEST_EMAIL = "testdariya@gmail.com";
    private static final String PASSWORD = "123Password";

    @Test
    public void testRegisterUser() {

        WebDriver driver = new ChromeDriver();
        driver.get(BASE_URL);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.xpath("//a[@href='/login']")).click();

        driver.findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys(TEST_NAME);
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(TEST_EMAIL);
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        if (!driver.findElement(By.xpath("//input[@id='id_gender2']")).isSelected()) {
            driver.findElement(By.xpath("//input[@id='id_gender2']")).click();
        }

        driver.findElement(By.xpath("//input[@data-qa='password']")).sendKeys(PASSWORD);

        Select daySelect = new Select(driver.findElement(By.xpath("//select[@data-qa='days']")));
        daySelect.selectByValue("15");

        Select monthSelector = new Select(driver.findElement(By.xpath("//select[@data-qa='months']")));
        monthSelector.selectByValue("9");

        Select yearSelect = new Select(driver.findElement(By.xpath("//select[@data-qa='years']")));
        yearSelect.selectByValue("2000");

        Actions actions = new Actions(driver);
        actions.scrollByAmount(0, 300).perform();

        driver.findElement(By.xpath("//input[@name='newsletter']")).click();
        driver.findElement(By.xpath("//input[@name='optin']")).click();

        actions.scrollByAmount(0, 500).perform();

        driver.findElement(By.xpath("//input[@data-qa='first_name']")).sendKeys(TEST_NAME);
        driver.findElement(By.xpath("//input[@data-qa='last_name']")).sendKeys("Zubovich");
        driver.findElement(By.xpath("//input[@data-qa='company']")).sendKeys("Company X");
        driver.findElement(By.xpath("//input[@data-qa='address']")).sendKeys("123 Maple Street, P.O. Box 789, Company X");

        actions.scrollByAmount(0, 400).perform();
        new Select(driver.findElement(By.xpath("//select[@data-qa='country']"))).selectByValue("Canada");

        driver.findElement(By.xpath("//input[@data-qa='state']")).sendKeys("Alberta");
        driver.findElement(By.xpath("//input[@data-qa='city']")).sendKeys("Brooks");
        driver.findElement(By.xpath("//input[@data-qa='zipcode']")).sendKeys("12345-6789");
        driver.findElement(By.xpath("//input[@data-qa='mobile_number']")).sendKeys("+1 (437) 555-0001");

        actions.scrollByAmount(0, 200).perform();

        driver.findElement(By.xpath("//button[@data-qa='create-account']")).click();

        String actualText = driver.findElement(By.xpath("//h2[@data-qa='account-created']/b")).getText();
        Assert.assertEquals(actualText,"ACCOUNT CREATED!","Unexpected message text after account creation");

        driver.quit();
    }

    @Test(dependsOnMethods = "testRegisterUser")
    public void testDeleteUser(){

        WebDriver driver = new ChromeDriver();
        driver.get(BASE_URL);

        driver.findElement(By.xpath("//a[@href='/login']")).click();

        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(TEST_EMAIL);
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

        driver.findElement(By.xpath("//a[@href='/delete_account']")).click();

        String actualText = driver.findElement(By.xpath("//h2[@data-qa='account-deleted']/b")).getText();
        Assert.assertEquals(actualText, "ACCOUNT DELETED!","Unexpected message text after account deletion");

        driver.quit();
    }
}
