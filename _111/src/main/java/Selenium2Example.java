//package org.openqa.selenium.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class Selenium2Example {
    public static void main(String[] args) throws InterruptedException{

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.navigate().to("http://www.random.org/integers");

        Thread.sleep(2000);

        WebElement minThreshold=driver.findElement(By.name("min"));
        WebElement maxThreshold=driver.findElement(By.name("max"));
        WebElement NumberQuantity=driver.findElement(By.name("num"));
        WebElement Submit=driver.findElement(By.cssSelector("input[type='submit']"));


        minThreshold.clear();
        minThreshold.sendKeys("0");
        maxThreshold.clear();
        maxThreshold.sendKeys("150");
        NumberQuantity.clear();
        NumberQuantity.sendKeys("1");

        Submit.click();
        Thread.sleep(2000);
        WebElement Result=driver.findElement(By.className("data"));
        System.out.println(Result.getText());

        Thread.sleep(2000);
        driver.quit();


    }
    }
