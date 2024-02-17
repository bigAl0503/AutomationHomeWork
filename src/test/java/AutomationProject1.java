import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.security.Key;

public class AutomationProject1 {

    @Test

    public void testCase1() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://duotify.us-east-2.elasticbeanstalk.com/register.php");

        String expectedTitle = "Welcome to Duotify!";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle,expectedTitle);

        driver.findElement(By.id("hideLogin")).click();

        Faker faker = new Faker();
        String userName = faker.name().username();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        driver.findElement(By.id("username")).sendKeys(userName, Keys.ENTER);
        Thread.sleep(500);
        driver.findElement(By.id("firstName")).sendKeys(firstName, Keys.ENTER);
        Thread.sleep(500);
        driver.findElement(By.id("lastName")).sendKeys(lastName, Keys.ENTER);
        Thread.sleep(500);
        driver.findElement(By.id("email")).sendKeys(email, Keys.ENTER);
        Thread.sleep(500);
        driver.findElement(By.id("email2")).sendKeys(email, Keys.ENTER);
        Thread.sleep(500);
        driver.findElement(By.id("password")).sendKeys(password, Keys.ENTER);
        Thread.sleep(500);
        driver.findElement(By.id("password2")).sendKeys(password, Keys.ENTER);

        String expectedURL = "http://duotify.us-east-2.elasticbeanstalk.com/browse.php?";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(expectedURL,actualURL);

        String firstAndLast = driver.findElement(By.id("nameFirstAndLast")).getText();
        Assert.assertEquals(firstName+" " +lastName,firstAndLast);

        driver.findElement(By.id("nameFirstAndLast")).click();
        Thread.sleep(1000);
        String username = driver.findElement(By.xpath("//div[@class='userInfo']")).getText();
        Thread.sleep(1000);

        Assert.assertEquals(firstAndLast,username);
        driver.findElement(By.id("rafael")).click();

        driver.navigate().refresh();
        String expectedLogOutUrl =  "http://duotify.us-east-2.elasticbeanstalk.com/register.php";
        Assert.assertEquals(driver.getCurrentUrl(),expectedLogOutUrl);

        driver.findElement(By.id("loginUsername")).sendKeys(userName,Keys.ENTER);
        driver.findElement(By.id("loginPassword")).sendKeys(password,Keys.ENTER);
        Thread.sleep(1000);

        Assert.assertTrue(driver.findElement(By.cssSelector("h1[class='pageHeadingBig']")).getText().contains("You Might Also Like"));

        driver.findElement(By.id("nameFirstAndLast")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("rafael")).click();

        driver.navigate().refresh();
        Assert.assertEquals(driver.getCurrentUrl(),"http://duotify.us-east-2.elasticbeanstalk.com/register.php");




    }

}
