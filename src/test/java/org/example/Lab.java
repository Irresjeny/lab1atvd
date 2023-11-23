package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Lab {
    private WebDriver chromeDriver;
    private WebDriverWait wait;
    private static final String baseUrl = "https://gsocks.net/";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(15));
        this.chromeDriver = new ChromeDriver(chromeOptions);
    }

    @BeforeMethod
    public void preconditions() {
        chromeDriver.get(baseUrl);
    }

    @AfterClass(alwaysRun=true)
    public void tearDown(){chromeDriver.quit();}

    @Test
    public void testHeaderExists() {
        WebElement header = chromeDriver.findElement(By.className("header_header__P90l2"));
        Assert.assertNotNull(header);
    }

    @Test
    public void testClickOnLogin() {
        WebElement loginButton = chromeDriver.findElement(By.xpath("/html/body/div[1]/header/div/div/button[1]"));
        Assert.assertNotNull(loginButton);
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.invisibilityOf(loginButton));
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(), baseUrl);
    }

    @Test
    public void testEnterEmail() throws InterruptedException {
        chromeDriver.get(baseUrl + "/user/login");
        WebElement emailField = chromeDriver.findElement(By.xpath("//*[@id=\"email\"]"));
        String email = "test@test.test";
        emailField.sendKeys(email);
        Assert.assertEquals(emailField.getAttribute("value"), email);
    }
}
