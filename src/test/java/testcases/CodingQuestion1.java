package testcases;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestNGListeners.class)

public class CodingQuestion1 extends BaseTestCase {

//    1 .	Go to "http://automationpractice.com/index.php" and click on Sign-ln.

    @Test(priority = 1)
    public void launchStore() {
        driver.get(baseurl);
        Assert.assertEquals(driver.getTitle(), "My Store");
    }
}





