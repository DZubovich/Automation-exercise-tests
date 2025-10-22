import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AddToCartTest {

    @Test
    public void testAddProductToCart() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.xpath("//a[@href='/products']/i[@class='material-icons card_travel']")).click();

        Actions action = new Actions(driver);
        action.scrollByAmount(0, 500).perform();

        String expectedName = driver.findElement(By.xpath("//a[@data-product-id='1']/ancestor::div[@class='productinfo text-center']/p"))
                .getText();
        String expectedPrice = driver.findElement(By.xpath("//a[@data-product-id='1']/ancestor::div[@class='productinfo text-center']/h2"))
                .getText();

        action
                .moveToElement(driver.findElement(By.xpath("//div[@class='productinfo text-center' and .//a[@data-product-id='1']]")))
                .click(driver.findElement(By.xpath("//div[@class='productinfo text-center']//a[text()='Add to cart']")))
                .perform();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='modal-content']//u[text()='View Cart']")))
                .click();

        String actualName = driver.findElement(By.xpath("//tr[@id='product-1']//td[@class='cart_description']//h4/a"))
                .getText();
        String actualPrice = driver.findElement(By.xpath("//tr[@id='product-1']//td[@class='cart_price']//p"))
                .getText();

        Assert.assertEquals(actualName, expectedName);
        Assert.assertEquals(actualPrice, expectedPrice);

        driver.quit();
    }

}
