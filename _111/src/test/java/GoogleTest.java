import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by natali on 7/14/2017.
 */
public class GoogleTest {
    private WebDriver driver;

    private WebElement GetElementBySelector(String selector){
        try{
            return driver.findElement(By.cssSelector(selector));
        }
        catch(NoSuchElementException ex){
            return null;
        }
    }
    private boolean IfElementExits(String selector){
        try{
            driver.findElement(By.cssSelector(selector));
            return true;
        }
        catch(NoSuchElementException ex){
            return false;
        }
    }

    @BeforeTest
    void Setup(){
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("https://accounts.google.com/ServiceLogin/identifier");
    }
    @BeforeMethod
    void pause(){
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex){}
    }


    @Test(priority = 3)
    void LoginCheckNegative(){
        WebElement input = GetElementBySelector("#identifierId");
        WebElement submitBtn = GetElementBySelector("#identifierNext");

        if(input == null)
            return;
        if(submitBtn == null)
            return;

        input.sendKeys("nlevina08_tryHerBestToWriteThisTest@gmail.com");
        submitBtn.click();

        Assert.assertFalse(IfElementExits("#password"));
    }
    @Test(priority = 4)
    void LoginCheckPositive(){
        WebElement input = GetElementBySelector("#identifierId");
        WebElement submitBtn = GetElementBySelector("#identifierNext");

        if(input == null)
            return;
        if(submitBtn == null)
            return;


        //obligatory clear the input field beacuse there is a text there
        input.clear();
        //input.sendKeys("nlevina08@gmail.com");
        input.sendKeys("testauto49@gmail.com");
        submitBtn.click();

        Assert.assertTrue(IfElementExits("#password"));
    }
   @Test(priority = 5)
   void PasswordCheckNegative(){
       WebElement input = GetElementBySelector("#password input[type=password]");
      WebElement submitBtn = GetElementBySelector("#passwordNext");

        if(input == null)
           return;
      if(submitBtn == null)
           return;

       input.sendKeys("hggjdjgdj");
       submitBtn.click();

        //if button 'Next'/'Далее' is still visible
      Assert.assertTrue(IfElementExits("#passwordNext"));
        //if title is not changed to Google
       Assert.assertNotEquals(driver.getTitle(), "Google");
    }
    @Test(priority = 6)
    void PasswordCheckPositive(){
        WebElement input = GetElementBySelector("#password input[type=password]");
        WebElement submitBtn = GetElementBySelector("#passwordNext");

        if(input == null)
            return;
        if(submitBtn == null)
            return;

        //obligatory clear the input field beacuse there is a text there
        input.clear();

        input.sendKeys("Test12345");
        submitBtn.click();
        pause();

        Assert.assertEquals(driver.getTitle(), "My Account");

    }

    @AfterTest
    void tearDown(){
        driver.quit();
    }
}
