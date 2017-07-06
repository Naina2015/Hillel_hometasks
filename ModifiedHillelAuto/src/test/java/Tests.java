/**
 * Created by hillel on 30.06.17.
 */

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.regex.Pattern;

import MyCode.Validator;

public class Tests {

    Validator myValidator;

    @DataProvider(name = "TestData")
    public Object[][] createData(){
        return new Object[][] {
                {"6000", Boolean.TRUE},
                {"6000,1", Boolean.FALSE},
                {"6000,10000", Boolean.TRUE},
                {"6000.10", Boolean.TRUE}
        };
    }
    @DataProvider(name = "TestEmailData")
    public Object[][] createEmailData(){
        return new Object[][] {
                {"Test@gmail.com", Boolean.TRUE},
                {"TEST@gmail.com", Boolean.TRUE},
                {"123@rambler.ru", Boolean.FALSE},
                {"T E st@gmail.com", Boolean.TRUE},
                {"123@gmail.com", Boolean.TRUE},
                {" mailbox+tag@gmail.com", Boolean.TRUE},
                {" @gmail.com", Boolean.TRUE},
                {"!#$%&'*+-/=?^_`{}|~@gmail.com", Boolean.TRUE},
                {"Miles.O'Brian@gmail.com", Boolean.TRUE}

        };
    }
    @DataProvider(name = "TestReplaceData")
    public Object[][] createReplaceData(){
        return new Object[][] {
                {"test@gmail.com", "@rambler.ru", Boolean.TRUE},
                {"test@mail.ru", "@rambler.ru", Boolean.FALSE}
        };
    }
    @BeforeTest
    void setUp(){
        System.out.println("Created Object");
         myValidator = new Validator();
    }

    @Test(dataProvider = "TestData", description = "Test for Numbers Validation")
    void Positive(String testString, Boolean expectedResult){
        System.out.println(testString);
        Assert.assertEquals( (Boolean) myValidator.numbers(testString), expectedResult,"Bad input " + testString);
    }

    @Test(dataProvider = "TestEmailData", description = "Test for Email Validation")
    void PositiveEmail(String testString, Boolean expectedResult){
        System.out.println(testString);
        Assert.assertEquals( (Boolean) myValidator.emails(testString), expectedResult,"Bad input " + testString);
    }

    @Test(dataProvider = "TestReplaceData", description = "Test for Email Validation")
    void PositiveReplacement(String testString, String repl, Boolean expectedResult){
        System.out.println(testString);
        String result = myValidator.Replacing(testString,repl);
        Assert.assertEquals((Boolean)Pattern.matches("^.+"+repl+"$", result), expectedResult,"Bad input " + testString);
    }

    @AfterMethod
    void afterM(ITestResult testResult){
        System.out.println(testResult.isSuccess());
        System.out.println(testResult.getMethod().getDescription());
    }

//    @Parameters({"input"})
//    @Test
//    public static void Negative(String testString){
//        System.out.println(testString);
//        Assert.assertFalse(Validator.isNumberBetween(testString), "Bad input " + testString);    }
}
