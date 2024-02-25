import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AutomationProject2 {

    @Test
    public void testSpotify() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        String artistName = "David Guetta";
        String songName = "Titanium";

        driver.get("https://open.spotify.com/");

        driver.findElement(By.xpath("//span[@class='ButtonInner-sc-14ud5tc-0 bzuYkS encore-inverted-light-set']")).click();
        driver.findElement(By.xpath("//input[@id='login-username']")).sendKeys("oleksii0503@aol.com", Keys.TAB);
        driver.findElement(By.xpath("//input[@id='login-password']")).sendKeys("5005ilikeCrypto$&@", Keys.ENTER);

        WebElement elementProfile = driver.findElement(By.xpath("//span[@class='Text__TextElement-sc-if376j-0 gYdBJW encore-text-body-small-bold NqzueDshzvgXEygqOGPG']"));

        Assert.assertTrue(elementProfile.isDisplayed());

        driver.findElement(By.xpath("//span[.='Search']")).click();
        driver.findElement(By.xpath("//input[@class='Text__TextElement-sc-if376j-0 gYdBJW encore-text-body-small QO9loc33XC50mMRUCIvf']")).sendKeys(artistName +" "+ songName, Keys.ENTER);

        Thread.sleep(3000);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[@data-testid='play-button']")));

        Thread.sleep(10000);

        String actualArtistName = driver.findElement(By.xpath("//div[@class='Text__TextElement-sc-if376j-0 duYgEj encore-text-marginal gpNta6i8q3KYJC6WBZQC']")).getText();
        String actualSongName = driver.findElement(By.xpath("//div[@class='Text__TextElement-sc-if376j-0 gYdBJW encore-text-body-small Q_174taY6n64ZGC3GsKj']")).getText();

        Assert.assertTrue(actualSongName.contains(songName));
        Assert.assertTrue(actualArtistName.contains(artistName));


        driver.findElement(By.xpath("//span[@data-testid='username-first-letter']")).click();
        driver.findElement(By.xpath("//span[.='Log out']")).click();

        WebElement logInButton = driver.findElement(By.xpath("//span[.='Log in']"));

        Assert.assertTrue(logInButton.isDisplayed());

    }
}
