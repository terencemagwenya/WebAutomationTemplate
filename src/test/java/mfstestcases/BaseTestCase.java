package mfstestcases;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
public class BaseTestCase {
    public WebDriver driver;
    public static WebDriverWait wait;
    String baseurl;
    public static boolean loggedIn = false;
    protected static Properties properties;
    private static FileInputStream fileInputStream = null;

    @Parameters("BrowserName")
    @BeforeClass
    public void setupBrowser(String BrowserName) {
        if (BrowserName.equalsIgnoreCase("Chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (BrowserName.equalsIgnoreCase("Firefox")) {

            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (BrowserName.equalsIgnoreCase("Edge")) {

            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }


        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 30);
        properties = new Properties();

        try {
            fileInputStream = new FileInputStream("src/test/java/utils/config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        baseurl = properties.getProperty("baseUrl");
    }

    @AfterClass
    public void quitDriver() {

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       // driver.close();
        //driver.quit();
    }
}
