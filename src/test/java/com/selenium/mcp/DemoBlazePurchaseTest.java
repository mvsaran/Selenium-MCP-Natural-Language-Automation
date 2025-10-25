package com.selenium.mcp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DemoBlazePurchaseTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testPurchaseFlow() throws InterruptedException {
        // Navigate to the website
        driver.get("https://www.demoblaze.com/");

        // Click on Samsung galaxy s6
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Samsung galaxy s6')]")))
                .click();

        // Add to cart
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Add to cart')]"))).click();

        // Handle alert
        Thread.sleep(1000); // Wait for alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        // Go to cart
        driver.findElement(By.id("cartur")).click();

        // Place order
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Place Order')]")))
                .click();

        // Fill out purchase form
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Test User");
        driver.findElement(By.id("country")).sendKeys("United States");
        driver.findElement(By.id("city")).sendKeys("New York");
        driver.findElement(By.id("card")).sendKeys("4111111111111111");
        driver.findElement(By.id("month")).sendKeys("12");
        driver.findElement(By.id("year")).sendKeys("2025");

        // Complete purchase
        driver.findElement(By.xpath("//button[contains(text(), 'Purchase')]")).click();

        // Wait for success message (optional verification step)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Thank you')]")));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}