import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;



public class HillelTest {
    private WebDriver driver;
    private String name = "Nata.Test";
    @BeforeTest
    public void BeforeTest(){
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.navigate().to("http://soft.it-hillel.com.ua:3000/");

    }

@Test(priority = 1)
private void positiveAddUser()  throws InterruptedException{



       WebElement AddButon=driver.findElement(By.className("add-user"));
       AddButon.click();


        WebElement FillName=driver.findElement(By.id("icon_prefix"));
        FillName.clear();
        FillName.sendKeys(name);
        WebElement FillPhone=driver.findElement(By.id("icon_telephone"));
        FillPhone.clear();
        FillPhone.sendKeys("11112222233333334444555555");
                WebElement Savebtn=driver.findElement(By.className("save-btn"));
        Savebtn.click();
        Thread.sleep(3000);


        WebElement CreatedUserInfo=driver.findElement(By.cssSelector("#user-list li:last-child h4"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].scrollIntoView();", CreatedUserInfo);
        String GetTextFromteList=CreatedUserInfo.getText();
        Assert.assertEquals(name,GetTextFromteList);
    }
@Test(priority = 2)
private void PositiveRemoveUser()throws InterruptedException{
    Thread.sleep(3000);
    WebElement RemoveButon=driver.findElement(By.cssSelector("#user-list li:last-child .remove"));
    RemoveButon.click();
    Thread.sleep(2000);//for test purposes.
    driver.switchTo().alert().accept();

    Thread.sleep(1000);
    WebElement CreatedUserInfo=driver.findElement(By.cssSelector("#user-list li:last-child h4"));
    JavascriptExecutor jse = (JavascriptExecutor)driver;
    jse.executeScript("arguments[0].scrollIntoView();", CreatedUserInfo);
    String GetTextFromteList=CreatedUserInfo.getText();
    Assert.assertNotEquals(name,GetTextFromteList);
}
    @AfterTest
    public void EndTest()throws InterruptedException{
       Thread.sleep(5000);
        driver.quit();
    }
}
