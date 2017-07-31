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
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ChromeDrive {
    private ChromeDriver driver;

    private WebElement GetElementBySelector(String selector){
        try{
            return driver.findElement(By.cssSelector(selector));
        }
        catch(NoSuchElementException ex){
            return null;
        }
    }

    private WebElement GetElementById(String id){
        try {
            return driver.findElement(By.id(id));
        }
            catch(NoSuchElementException ex){
            return null;
            }
        }

    private WebElement GetElementByXPATH(String XPATHselector){
        try{
            return driver.findElement(By.xpath(XPATHselector));
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
        driver.get("https://accounts.google.com/ServiceLogin/identifier?flowName=GlifWebSignIn&flowEntry=AddSession");
    }
    @BeforeMethod
    void pause(){
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex){}
    }


    @Test(priority = 3,enabled = false)
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
        WebElement  Login =GetElementById("identifierId");
        WebElement submitBtn = GetElementById("identifierNext");

        if(Login == null)
            return;
        if(submitBtn == null)
            return;


        //obligatory clear the input field beacuse there is a text there
        Login.clear();
        //input.sendKeys("nlevina08@gmail.com");
        Login.sendKeys("testusermail2017@gmail.com");
        submitBtn.click();

        Assert.assertTrue(IfElementExits("#password"));
    }
    @Test(priority = 5,enabled = false)
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
            Assert.fail("Password input not found");
        if(submitBtn == null)
            Assert.fail("'Next' button not found");

        //obligatory clear the input field beacuse there is a text there
        input.clear();

        input.sendKeys("Password11");
        submitBtn.click();
        pause();

        Assert.assertEquals(driver.getTitle(), "My Account");

    }


    @Test(priority = 7)
    void GoogleDriveSwitching(){
        WebElement googleAppsIcon = GetElementBySelector("a[title=\"Google apps\"]");
        WebElement driveIcon = GetElementBySelector("a[href*=\"drive.google.com\"]");

        if(googleAppsIcon == null)
            Assert.fail("'Google apps' icon not found");
        if(driveIcon == null)
            Assert.fail("'Drive' icon not found");

        googleAppsIcon.click();
        Assert.assertTrue(driveIcon.isDisplayed(), "'Drive' icon not visible");

        driveIcon.click();

        Assert.assertTrue(driver.getTitle().indexOf("Google Drive") >= 0, "This is probably not the Google Drive page");
    }

    @Test(priority = 10)
    void FileUploadPositive() throws  AWTException{
        WebElement newButton = GetElementBySelector("button[aria-label='New']");
        //WebElement newButton = GetElementByXPATH("//button[contains(text(),'New')]");

        if(newButton == null)
            Assert.fail("'New' button not found");

        newButton.click();

        pause();
        //WebElement newFileMenuItem = GetElementBySelector("[id=':6a']");
        WebElement newFileMenuItem = GetElementByXPATH("//div[contains(text(),'File upload')]");

        if(newFileMenuItem == null)
            Assert.fail("'Upload files' menu item not found");

        Assert.assertTrue(newFileMenuItem.isDisplayed(), "'Upload files' menu item not visible");

        newFileMenuItem.click();

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
        WebElement pop_up2 = driver.switchTo().activeElement();
        System.out.println(pop_up2.getText());

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label=\"Locate in My Drive\"]")));
    }
    @AfterTest
    void tearDown(){
        driver.quit();
    }
}
