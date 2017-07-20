/**
 * Created by natali on 7/18/2017.
 */
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.TimeUnit;



public class FileUploading {
    private WebDriver driver;

    private WebElement GetElementBySelector(String selector) {
        try {
            return driver.findElement(By.cssSelector(selector));
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    private boolean IfElementExits(String selector) {
        try {
            driver.findElement(By.cssSelector(selector));
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    @BeforeTest
    void Setup() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("https://account.box.com/login");
    }

    @BeforeMethod
    void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }


    @Test(priority = 1)
    void LoginCheckPositive() {
        WebElement inputLogin = GetElementBySelector("input[name='login']");
        WebElement inputPassword=GetElementBySelector("input[name='password']");
        WebElement submitBtn = GetElementBySelector("[type='submit']");

        if (inputLogin == null)
            return;
        if (inputPassword == null)
            return;
        if (submitBtn == null)
            return;


        //obligatory clear the input field beacuse there is a text there
        inputLogin.clear();
        inputLogin.sendKeys("rvalek@intersog.de");
        inputPassword.clear();
        inputPassword.sendKeys("welcome2hillel");
        submitBtn.click();

        Assert.assertTrue(IfElementExits(".upload-menu-btn"));
    }

    @Test(priority = 2)
    void FileUploadingPositive() throws AWTException{
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement button= driver.findElement(By.className("upload-menu-btn"));
        button.click();

        WebElement UploadFileButton=driver.findElement(By.className("upload-file-handler-target"));
        UploadFileButton.click();
        File UploadingFile=new File("resources\\Natasha.jpg");
        /*// switch to the file upload window
        Alert alert = driver.switchTo().alert();

        // enter the filename
        alert.sendKeys(UploadingFile.getAbsolutePath());*/

        StringSelection ss = new StringSelection(UploadingFile.getAbsolutePath());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

        // hit enter
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

        // switch back
        driver.switchTo().activeElement();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#mod-uploads-manager-1.is-completed")));


        String UploadedFileName;
        WebElement UploadedFile=driver.findElement(By.cssSelector(".file-list-body li:first-child .file-list-name .item-name-link"));
        UploadedFileName=UploadedFile.getText();

        Assert.assertEquals("Natasha.jpg",UploadedFileName);

    }

    @AfterTest
    void Finally(){
        pause();
        driver.quit();
    }
}


