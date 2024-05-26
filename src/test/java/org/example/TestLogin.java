package org.example;

import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class TestLogin {


    WebDriver driver;
    ChromeOptions options;

    @BeforeSuite
        @Description("Verify the driver set all the setting before running the automation")
        public void setUp() {
            options = new ChromeOptions();
            options.addArguments("start-maximized");

            driver = new ChromeDriver(options);

        }

        @Test
        @Description("Verify that user should not be able to login into VWO with invalid credentials")
        public void testInvalidLogin () {

            driver.navigate().to("https://app.vwo.com");
            WebElement email = driver.findElement(By.id("login-username"));
            email.sendKeys("anurag@bluelupin.com");
            WebElement password = driver.findElement(By.name("password"));
            password.sendKeys("");

            WebElement submitbutton = driver.findElement(By.id("js-login-btn"));
            submitbutton.click();


            WebElement errormessage = driver.findElement(By.className("notification-box-description"));
            new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(errormessage));


            String errorString = driver.findElement(By.className("notification-box-description")).getText();
            Assert.assertEquals(errormessage.getText(),"Your email, password, IP address or location did not match");

        }

        @Test
        @Description("Verify that user is able to login into the VWO with valid credentials")
        public void testValidLogin () throws InterruptedException {

            driver.navigate().to("https://app.vwo.com");
            WebElement email = driver.findElement(By.id("login-username"));
            email.sendKeys("anurag@bluelupin.com");
            WebElement password = driver.findElement(By.name("password"));
            password.sendKeys("Anurag@12345");

            WebElement submitbutton = driver.findElement(By.id("js-login-btn"));
            submitbutton.click();

            new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.urlMatches("https://app.vwo.com/#/dashboard"));

            Assert.assertEquals(driver.getTitle(),"Dashboard");
            Assert.assertEquals(driver.getCurrentUrl(),"https://app.vwo.com/#/dashboard");
            Thread.sleep(5000);
        }
        @AfterSuite
                public void tearDown(){
            driver.quit();
        }
}

