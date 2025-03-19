package demo;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        @Test
        public void testCase01() {
                //navigate to youtube.com
                Wrappers.navigate(driver, "https://www.youtube.com/");
                SoftAssert softAssert = new SoftAssert();
                String currentURL = driver.getCurrentUrl();
                //assert youtube url
                softAssert.assertTrue(currentURL.contains("youtube"), "URL doesn't contains Youtube");
                
                //clicking on threebars top left
                WebElement hamBurgerSign = driver.findElement(By.id("guide-button"));
                Wrappers.click(hamBurgerSign, driver);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                
                //clicking on aboutLink
                WebElement aboutLink = wait
                                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='About']")));
                Wrappers.click(aboutLink, driver);

                //validating aboutHeading
                WebElement aboutHeading = wait.until(ExpectedConditions
                                .visibilityOfElementLocated(By.xpath("//main[@id='content']/section/h1")));
                String aboutHeadingText = aboutHeading.getText();
                softAssert.assertTrue(aboutHeadingText.contains("About"), "Heading doesn't contains About");
                
                //softAssert decision making
                softAssert.assertAll();
        }

        @Test
        public void testCase02() throws InterruptedException {

                //navigating to Youtube
                Wrappers.navigate(driver, "https://www.youtube.com/");
                SoftAssert softAssert = new SoftAssert();
                String currentURL = driver.getCurrentUrl();
                softAssert.assertTrue(currentURL.contains("youtube"), "URL doesn't contains Youtube");
                
                //clicking on threebars top left
                WebElement hamBurgerSign = driver.findElement(By.id("guide-button"));
                Wrappers.click(hamBurgerSign, driver);

                //moving to desired section
                Wrappers.moveToDesiredSection(driver, "Movies");

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                wait.until(ExpectedConditions.urlContains("feed"));
                Thread.sleep(3000);

               
                WebElement nextButton = driver.findElement(By.xpath(
                                "//span[text()='Top selling']/ancestor::div[@id='dismissible']//button[@aria-label='Next']"));
                
                Wrappers.nextButtonClick(driver, nextButton);
                
                System.out.println("Reached last movie tile");

                //Getting Genre
                WebElement lastMovieGenre = driver.findElement(
                                By.xpath("(//span[contains(@class,'ytd-grid-movie-renderer')])[position()=last()]"));
                String lastMovieGenreText = lastMovieGenre.getText();
                softAssert.assertTrue((lastMovieGenreText.contains("Comedy") || lastMovieGenreText.contains("Animation") || lastMovieGenreText.contains("Drama") ), "Doesn't contains Comedy");
                
                //Getting Rating
                WebElement lastAdultRating = driver.findElement(
                                By.xpath("(//p[contains(@class,'ytd-badge-supported-renderer')])[position()=last()]"));
                String lastAdultRatingText = lastAdultRating.getText();
                softAssert.assertTrue((lastAdultRatingText.contains("U/A") || lastAdultRatingText.equals("A")), "Rating is not A");

                //softAssert decision making
                softAssert.assertAll();

        }

        @Test
        public void testCase03() throws InterruptedException {
                //navigating to Youtube.com
                Wrappers.navigate(driver, "https://www.youtube.com/");
                SoftAssert softAssert = new SoftAssert();
                String currentURL = driver.getCurrentUrl();
                softAssert.assertTrue(currentURL.contains("youtube"), "URL doesn't contains Youtube");
                
                //Clicking on Top Left Menu button
                WebElement hamBurgerSign = driver.findElement(By.id("guide-button"));
                Wrappers.click(hamBurgerSign, driver);

                //Moving to section
                Wrappers.moveToDesiredSection(driver, "Music");
                Thread.sleep(3000);

                //Getting last Song Tile
                WebElement lastMusicTileSongs = driver.findElement(By.xpath(
                                "//span[text()=\"India's Biggest Hits\"]/ancestor::div[@id='dismissible']//div[@id='contents']//ytd-rich-item-renderer[4]//div[@class='badge-shape-wiz__text']"));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("var rect = arguments[0].getBoundingClientRect();" +
                                "window.scrollTo({ top: rect.top + window.pageYOffset - (window.innerHeight / 2), behavior: 'smooth' });",
                                lastMusicTileSongs);
                
                //Getting Number of Songs
                int numberOfSongsInPlaylist = Integer.parseInt(lastMusicTileSongs.getText().split(" ")[0]);
                softAssert.assertTrue(numberOfSongsInPlaylist >= 50, "Number of songs are more than 50");
                
                //softAssert decision making
                softAssert.assertAll();    

        }

        @Test
        public void testCase04() throws InterruptedException {
                //navigating to Youtube.com
                Wrappers.navigate(driver, "https://www.youtube.com/");
                SoftAssert softAssert = new SoftAssert();
                String currentURL = driver.getCurrentUrl();
                softAssert.assertTrue(currentURL.contains("youtube"), "URL doesn't contains Youtube");
                
                //Clicking on Top Left Menu button
                WebElement hamBurgerSign = driver.findElement(By.id("guide-button"));
                Wrappers.click(hamBurgerSign, driver);

                //Moving to section
                Wrappers.moveToDesiredSection(driver, "News");

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement newTiles = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Latest news posts']/ancestor::div[@id='dismissible']//span[@id='vote-count-middle']")));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].style.display='block'", newTiles);

                List<WebElement> newsTilesLikes = driver.findElements(By.xpath("//span[text()='Latest news posts']/ancestor::div[@id='dismissible']//span[@id='vote-count-middle']"));
                
                int sumOfLikes = Wrappers.sumOfLikes(newsTilesLikes);

                System.out.println("Total like of Three tiles: "+ sumOfLikes);

                //softAssert decision making
                softAssert.assertAll();    
        }

        @Test
        public void testCase05(){

        }

        
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}

