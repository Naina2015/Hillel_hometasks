import com.sun.xml.internal.ws.policy.AssertionValidationProcessor;
import javafx.scene.layout.Priority;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestRail {
    WebDriver driver;
    APIClient TestRailCon;
    String runId;

    WebDriverWait wait;

   @BeforeTest
    public void initialiser () throws APIException, IOException{
       TestRailCon=new APIClient("https://naina2015.testrail.net");
       TestRailCon.setUser("nlevina08@gmail.com");
       TestRailCon.setPassword("Test12345");

       JSONObject c = (JSONObject) TestRailCon.sendPost("add_run/"+1,new HashMap());
       runId = c.get("id").toString();

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
        driver.navigate().to("https://app.box.com/login");

       wait = new WebDriverWait(driver, 10);
    }

    @AfterTest
    public void finalize()throws APIException, IOException{
       driver.quit();
        JSONObject c = (JSONObject) TestRailCon.sendPost("close_run/"+runId,new HashMap());
    }

    @Test(priority = 2, description="1")
    public void LoginPositive(){
        WebElement natatest1=driver.findElement(By.cssSelector("input[type='text'][name='login']"));
        WebElement natatest2=driver.findElement((By.cssSelector("input[type='password'][name='password']")));
        natatest1.clear();
        natatest2.clear();
        natatest1.sendKeys(" rvalek@intersog.de");
        natatest2.sendKeys("welcome2hillel");
        WebElement natatest3=driver.findElement(By.cssSelector("button[type=submit]"));
        natatest3.click();
        Assert.assertTrue(driver.getTitle().indexOf("All Files") >= 0);
    }
    @Test(priority = 1,description="2")
    public void LoginNegative(){
        String initTitle = driver.getTitle();

        WebElement natatest1=driver.findElement(By.cssSelector("input[type='text'][name='login']"));
        WebElement natatest2=driver.findElement((By.cssSelector("input[type='password'][name='password']")));
        natatest1.clear();
        natatest2.clear();
        natatest1.sendKeys(" 111@intersog.de");
        natatest2.sendKeys("welcome2hillel");
        WebElement natatest3=driver.findElement(By.cssSelector("button[type=submit]"));
        natatest3.click();
        Assert.assertEquals(initTitle, driver.getTitle());

    }
    @AfterMethod
    public void RunResult(ITestResult result)throws APIException,IOException{
        ITestNGMethod m = result.getMethod();
        int testNGStatus=result.getStatus();
        int testRailStatus = 0;
        switch (testNGStatus){
            case ITestResult.FAILURE:
                testRailStatus = 5;
                break;
            case ITestResult.SUCCESS:
                testRailStatus = 1;
                break;
            case ITestResult.SKIP:
                testRailStatus = 2;
                break;
            default:
                testRailStatus = 0;
                break;
        }

        /*JSONObject c = (JSONObject) TestRailCon.sendGet("get_case/"+Integer.parseInt(m.getDescription()));
        System.out.println(c.get("title"));*/

        HashMap map=new HashMap();
        map.put("status_id",testRailStatus);
        JSONObject c1 = (JSONObject) TestRailCon.sendPost("add_result_for_case/"+runId+"/"+Integer.parseInt(m.getDescription()),map);

    }

    @Test (priority =3, description="3")
    public void FileUploading()throws AWTException{

        WebElement btn =driver.findElement(By.cssSelector("a[class*=upload-menu-btn]"));
      btn.click();
      WebElement MenuItem=driver.findElement(By.cssSelector("li[class*=upload-file-handler-target"));
        MenuItem.click();
        File Nata=new File("src/main/resources/Nata.jpg");
        StringSelection ss = new StringSelection(Nata.getAbsolutePath());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

        Robot robot = new Robot();
        robot.delay(250);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_ENTER);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#mod-uploads-manager-1.is-completed")));

        driver.navigate().refresh();
        String UploadedFileName;
        WebElement UploadedFile=driver.findElement(By.cssSelector(".file-list-body li:first-child .file-list-name .item-name-link"));
        UploadedFileName=UploadedFile.getText();

        Assert.assertEquals("Nata.jpg",UploadedFileName);


    }
    @Test (priority=4,description="4")
    public void FileRemoving() {
        driver.navigate().refresh();

        WebElement FileRow = driver.findElement(By.cssSelector(".file-list-body li.has-representation:first-child"));
        FileRow.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class*=sidebar-file]")));

        WebElement DeleteBtn=driver.findElement(By.cssSelector("button[class*=delete-btn]"));
        DeleteBtn.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[class*=popup-confirm-btn]")));

        driver.switchTo().activeElement();
        WebElement Confirmation=driver.findElement(By.cssSelector("button[class*=popup-confirm-btn]"));
        Confirmation.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class*=sidebar-file]")));

        driver.navigate().refresh();

        String UploadedFileName;
        WebElement UploadedFile=driver.findElement(By.cssSelector(".file-list-body li:first-child .file-list-name .item-name-link"));
        UploadedFileName=UploadedFile.getText();
        Assert.assertNotEquals("Nata.jpg",UploadedFileName);


    }
}
