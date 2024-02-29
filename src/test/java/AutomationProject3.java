import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutomationProject3 {

    @Test
    public void testAdmuns() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.get("https://www.edmunds.com/");

        driver.findElement(By.xpath("//a[.='Shop Used']")).click();

        driver.findElement(By.xpath("//input[@name='zip']")).sendKeys(Keys.COMMAND,"a");
        driver.findElement(By.xpath("//input[@name='zip']")).sendKeys("22031", Keys.ENTER);

        Thread.sleep(1000);
        driver.findElement(By.xpath("(//label[@class='checkbox-facet d-flex align-items-center mb-0 justify-content-between'])[2]")).click();

        Select dropDownBoxMake = new Select(driver.findElement(By.xpath("//select[@id='usurp-make-select']")));
        dropDownBoxMake.selectByVisibleText("Tesla");

        Select dropDownBoxModel = new Select(driver.findElement(By.xpath("//select[@id='usurp-model-select']")));
        WebElement firstSelectedElement = dropDownBoxModel.getFirstSelectedOption();

        Assert.assertEquals(firstSelectedElement.getText(),"Add Model");

        Assert.assertTrue(driver.findElement(By.xpath("//input[@value='2012']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//input[@value='2023'] ")).isDisplayed());


        List<WebElement> dropBoxModelContent = dropDownBoxModel.getOptions();
        List<String> actualModels = new ArrayList<>();

        for(WebElement model : dropBoxModelContent){
            actualModels.add(model.getText());
        }

        List<String> expectedModels = List.of("Add Model", "Model 3", "Model S", "Model X", "Model Y", "Cybertruck", "Roadster");
        Assert.assertEquals(actualModels,expectedModels);

        dropDownBoxModel.selectByVisibleText("Model 3");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//input[@id='min-value-input-Year']")).click();
        driver.findElement(By.xpath("//input[@id='min-value-input-Year']")).sendKeys("2020", Keys.ENTER);

        driver.findElement(By.xpath("(//label[@class='checkbox-facet d-flex align-items-center mb-0 justify-content-between'])[2]")).click();

        List<WebElement> webElements = driver.findElements(By.xpath("//li[@class='d-flex mb-0_75 mb-md-1_5 col-12 col-md-6']"));

        Assert.assertEquals(webElements.size(),21);

        List<WebElement> searchResults = driver.findElements(By.xpath("//div[contains(text(),'Tesla Model 3')]"));

        List<String> searchResultList = new ArrayList<>();

        for (WebElement searchResult : searchResults) {
            searchResultList.add(searchResult.getText());
        }

        searchResultList.forEach(s-> Assert.assertTrue(s.contains("Tesla Model 3")));

        List<Integer> yearsList = new ArrayList<>();

        for(String numList : searchResultList){
            yearsList.add(Integer.valueOf(numList.substring(0,4)));
        }

        for(Integer i : yearsList){
            Assert.assertTrue(i>=2020 && i<=2023);
        }

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//select[@id='sort-by']")));
        driver.findElement(By.xpath("//option[text()='Price: Low to High']")).click();

        driver.navigate().refresh();
        List<WebElement> price = driver.findElements(By.xpath("//span[@class='heading-3'][text()]"));
        List<String> priceList = new ArrayList<>();

        for(WebElement i : price){
            priceList.add(i.getText());
        }

        List<Integer> cleanPrice = new ArrayList<>();
        for(String i : priceList){
            cleanPrice.add(Integer.valueOf(i.substring(1).replace(",","")));
        }

        List<Integer> newPriceList = new ArrayList<>(cleanPrice);
        Collections.sort(newPriceList);


        Assert.assertEquals(cleanPrice, newPriceList);

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//select[@id='sort-by']")));
        driver.findElement(By.xpath("//option[text()='Price: High to Low']")).click();

        driver.navigate().refresh();
        List<WebElement> highToLowPrice = driver.findElements(By.xpath("//span[@class='heading-3']"));
        List<String> highToLowList = new ArrayList<>();


        for(WebElement i : highToLowPrice){
            highToLowList.add(i.getText());
        }

        List<Integer> cleanPriceHighToLow = new ArrayList<>();
        for(String i : highToLowList){
            cleanPriceHighToLow.add(Integer.valueOf(i.substring(1).replace(",","")));
        }

        List<Integer> sortedHighToLowPrice = new ArrayList<>(cleanPriceHighToLow);
        Collections.sort(sortedHighToLowPrice, Collections.reverseOrder());

        Assert.assertEquals(sortedHighToLowPrice,cleanPriceHighToLow);

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//select[@id='sort-by']")));
        driver.findElement(By.xpath("//option[text()='Mileage: Low to High']")).click();

        driver.navigate().refresh();

        List<WebElement> milesLowToHigh = driver.findElements(By.xpath("//div[@class='size-14 d-flex align-items-baseline mt-0_5 col-12']//span[contains(text(),'miles')]"));
        List<String> milesLowToHighList = new ArrayList<>();

        for(WebElement i : milesLowToHigh){
            milesLowToHighList.add(i.getText());
        }

        List<Integer> cleanMilesLowToHigh = new ArrayList<>();
        for(String i : milesLowToHighList){
            cleanMilesLowToHigh.add(Integer.valueOf(i.replace(" miles","").replace(",","")));
        }

        List<Integer> sortedMilesLowToHigh = new ArrayList<>(cleanMilesLowToHigh);
        Collections.sort(sortedMilesLowToHigh);

        Assert.assertEquals(sortedMilesLowToHigh,cleanMilesLowToHigh);

        List<WebElement> lastVehicleInSearch = driver.findElements(By.xpath("//div[@class='visible-vehicle-info d-flex flex-column']"));
        WebElement lastOne = lastVehicleInSearch.get(lastVehicleInSearch.size() - 1);

        String lastVehicleTitle = lastOne.findElement(By.xpath("(//div[contains(text(),'Tesla Model 3')])[last()]")).getText();
        String lastVehiclePrice = lastOne.findElement(By.xpath("(//span[@class='heading-3'])[last()]")).getText();
        String lastVehicleMiles = lastOne.findElement(By.xpath("(//div[@class='size-14 d-flex align-items-baseline mt-0_5 col-12']//span[contains(text(),'miles')])[last()]")).getText();

        lastOne.click();

        String lastVehiclePrice1 = driver.findElement(By.xpath("//div[@class='heading-2 mb-0']")).getText();
        String lastVehicleTitle1 = driver.findElement(By.xpath("//h1[@class='d-inline-block mb-0 heading-2 mt-0_25']")).getText();
        String lastVehicleMiles3 = driver.findElement(By.xpath("//div[@class='pr-0 font-weight-bold text-right ml-1 col'][1]")).getText();

        Assert.assertEquals(lastVehicleTitle,lastVehicleTitle1);
        Assert.assertEquals(lastVehicleMiles.replace(" miles",""),lastVehicleMiles3);
        Assert.assertEquals(lastVehiclePrice,lastVehiclePrice1);

        driver.navigate().back();

        WebElement viewed = driver.findElement(By.xpath("//div[@class='bg-white text-gray-darker']"));
        Assert.assertTrue(viewed.isDisplayed());

        driver.quit();
    }
}
