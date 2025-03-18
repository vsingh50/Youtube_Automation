package demo;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        @Test
        public void testCase01() {
                Wrappers.navigate(driver, "https://www.youtube.com/");
                SoftAssert softAssert = new SoftAssert();
                String currentURL = driver.getCurrentUrl();
                softAssert.assertTrue(currentURL.contains("youtube"), "URL doesn't contains Youtube");
                WebElement hamBurgerSign = driver.findElement(By.id("guide-button"));
                Wrappers.click(hamBurgerSign, driver);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement aboutLink = wait
                                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='About']")));
                Wrappers.click(aboutLink, driver);
                WebElement aboutHeading = wait.until(ExpectedConditions
                                .visibilityOfElementLocated(By.xpath("//main[@id='content']/section/h1")));
                String aboutHeadingText = aboutHeading.getText();
                softAssert.assertTrue(aboutHeadingText.contains("About"), "Heading doesn't contains About");
                softAssert.assertAll();
        }

        @Test
        public void testCase02() throws InterruptedException {

                Wrappers.navigate(driver, "https://www.youtube.com/");
                SoftAssert softAssert = new SoftAssert();
                String currentURL = driver.getCurrentUrl();
                softAssert.assertTrue(currentURL.contains("youtube"), "URL doesn't contains Youtube");
                WebElement hamBurgerSign = driver.findElement(By.id("guide-button"));
                Wrappers.click(hamBurgerSign, driver);

                String movies = "Movies";
                WebElement moviesButton = driver
                                .findElement(By.xpath("//yt-formatted-string[text()='" + movies + "']"));
                Wrappers.click(moviesButton, driver);

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                wait.until(ExpectedConditions.urlContains("feed"));
                Thread.sleep(3000);
                WebElement nextButton = driver.findElement(By.xpath(
                                "//span[text()='Top selling']/ancestor::div[@id='dismissible']//button[@aria-label='Next']"));
                Boolean flag = true;
                while (flag) {
                        
                        if (!nextButton.isDisplayed()) {
                                flag = false; // Exit the loop when no more "next" button is visible
                        }
                        else{
                              Wrappers.click(nextButton, driver);
                        }   
                }
                System.out.println("Reached last movie tile");

                WebElement lastMovieGenre = driver.findElement(
                                By.xpath("(//span[contains(@class,'ytd-grid-movie-renderer')])[position()=last()]"));
                String lastMovieGenreText = lastMovieGenre.getText();
                softAssert.assertTrue(lastMovieGenreText.contains("Comedy"), "Doesn't contains Comedy");
                softAssert.assertTrue(lastMovieGenreText.contains("Animation"), "Doesn't contains Animation");
                softAssert.assertTrue(lastMovieGenreText.contains("Drama"), "Doesn't contains Drama");
                WebElement lastAdultRating = driver.findElement(
                                By.xpath("(//p[contains(@class,'ytd-badge-supported-renderer')])[position()=last()]"));
                String lastAdultRatingText = lastAdultRating.getText();
                softAssert.assertTrue(lastAdultRatingText.equals("A"), "Rating is not A");

                softAssert.assertAll();

        }

        @Test
        public void testCase03() throws InterruptedException {

                Wrappers.navigate(driver, "https://www.youtube.com/");
                SoftAssert softAssert = new SoftAssert();
                String currentURL = driver.getCurrentUrl();
                softAssert.assertTrue(currentURL.contains("youtube"), "URL doesn't contains Youtube");
                WebElement hamBurgerSign = driver.findElement(By.id("guide-button"));
                Wrappers.click(hamBurgerSign, driver);

                String music = "Music";
                WebElement musicButton = driver.findElement(By.xpath("//yt-formatted-string[text()='" + music + "']"));
                Wrappers.click(musicButton, driver);

                
                Thread.sleep(3000);
                WebElement lastMusicTileSongs = driver.findElement(By.xpath(
                                "//span[text()=\"India's Biggest Hits\"]/ancestor::div[@id='dismissible']//div[@id='contents']//ytd-rich-item-renderer[4]//div[@class='badge-shape-wiz__text']"));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("var rect = arguments[0].getBoundingClientRect();" +
                                "window.scrollTo({ top: rect.top + window.pageYOffset - (window.innerHeight / 2), behavior: 'smooth' });",
                                lastMusicTileSongs);
                int numberOfSongsInPlaylist = Integer.parseInt(lastMusicTileSongs.getText().split(" ")[0]);
                softAssert.assertTrue(numberOfSongsInPlaylist >= 50, "Number of songs are more than 50");
                softAssert.assertAll();
                Thread.sleep(3000);

        }

        @Test
        public void testCase04() throws InterruptedException {

                
                Wrappers.navigate(driver, "https://www.youtube.com/");
                SoftAssert softAssert = new SoftAssert();
                String currentURL = driver.getCurrentUrl();
                softAssert.assertTrue(currentURL.contains("youtube"), "URL doesn't contains Youtube");
                WebElement hamBurgerSign = driver.findElement(By.id("guide-button"));
                Wrappers.click(hamBurgerSign, driver);

                String news = "News";
                WebElement newsButton = driver.findElement(By.xpath("//yt-formatted-string[text()='" + news + "']"));
                Wrappers.click(newsButton, driver);

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement newTiles = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Latest news posts']/ancestor::div[@id='dismissible']//span[@id='vote-count-middle']")));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].style.display='block'", newTiles);

                List<WebElement> newsTilesLikes = driver.findElements(By.xpath("//span[text()='Latest news posts']/ancestor::div[@id='dismissible']//span[@id='vote-count-middle']"));
                int limit = 3;
                int sumOfLikes = 0;
                for(WebElement element : newsTilesLikes){
                        if(limit>0){
                               
                                String likesCount = element.getText().replaceAll("[^\\d]", "");
                                try{
                                        sumOfLikes += Integer.parseInt(likesCount);
                                        System.out.println(likesCount);
                                }
                                catch(Exception e){
                                        sumOfLikes += 0;
                                        System.out.println(0);
                                }
                                limit--;
                        }
                }

                System.out.println("Total like of Three tiles: "+ sumOfLikes);
                Thread.sleep(3000);

        }

        @Test
        public void testCase05() {

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