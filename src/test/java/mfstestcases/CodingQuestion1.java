package mfstestcases;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobject.AddToCartPage;
import pageobject.LoginPage;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Listeners(listeners.TestNGListeners.class)

public class CodingQuestion1 extends BaseTestCase {

//    1 .	Go to "http://automationpractice.com/index.php" and click on Sign-ln.

    @Test(priority = 1)
    public void launchStore() {
        driver.get(baseurl);
        Assert.assertEquals(driver.getTitle(), "My Store");
    }

    @Test(priority = 2)
    public void login() {

        LoginPage loginPage = new LoginPage(driver);
        driver.get(baseurl);
        loginPage.clickSignInLink();
        loginPage.setUsername(properties.getProperty("username"));
        loginPage.setPassword(properties.getProperty("password"));
        loginPage.clickSubmitButton();
        loggedIn = true;
        // assertion of the login
        Assert.assertEquals(driver.getTitle(), "My account - My Store", "Login Failed ");
    }

//3.	On landing page, under POPULAR category we see a list of apparels,
//      Get the Label and associated Price of those item.
//      Fetch them and  sort it as per their price [Low to High] and print it on Console

    @Test(priority = 3)
    public void getTextLableAndPriceForPopularCategory() {

        // making sure we are on the home page
        driver.get(baseurl);

        // Sorting
        Map<String, String> output = new TreeMap<>();

        // listing all elements with class name product-container
        List<WebElement> productContainers = driver.findElements(By.className("product-container"));

        for (WebElement currentProductContainer : productContainers) {

            // getting product name text for each product container
            String name = currentProductContainer.findElement(By.className("product-name")).getText();

            // getting products price
            List<WebElement> productPrices = currentProductContainer.findElements(By.cssSelector(".price.product-price"));
            String price = "";
            for (WebElement currentProductPrice : productPrices) {
                price = currentProductPrice.getText();
            }
            if (!name.isEmpty()) {
                output.put(price, name);
            }
        }
        System.out.println("Popular category Prices sorted from Low to High");
        output.forEach((Price, Name) -> System.out.println(Price + " - " + Name));
    }

//    Scroll Up - Navigate to Women >> Dresses >> Evening Dresses
//    Go to Catalog and filter out a dress: Size (M) 	Color (Pink)
//    Set Range: $50.00 - $52.28 Once entry is found, click on More.
//    Set Quantity= 3 >> Size= M >> Colour=Pink
//    Click on Add to Cart

    @Test(priority = 4)
    public void Add_to_cart() {
        driver.get(baseurl);
        if (!loggedIn) {
            login();
        }
        Actions action = new Actions(driver);
        AddToCartPage addToCartPage = new AddToCartPage(driver);
        addToCartPage.clickWomenLink();
        addToCartPage.clickDressesLink();
        addToCartPage.clickEveningDressesLink();
        addToCartPage.clickCheckboxM(); //Selecting size M
        addToCartPage.clickColor(); //Selecting color
        addToCartPage.slideToPriceRange("$50.00-$52.28");
        // Hover over product container
        addToCartPage.hoverOnProductContainer();
        addToCartPage.clickMoreButton();
        addToCartPage.quantityTextBox(); //clear text box ans send k3ys
        addToCartPage.selectPinkColor();
        addToCartPage.selectSize("M"); // select dress size
        addToCartPage.clickSubmitButton();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("layer_cart")));
        String cartMessage = driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2")).getText().trim();
        String expectedCartMessage = "Product successfully added to your shopping cart";
        Assert.assertEquals(cartMessage, expectedCartMessage, "Product not successfully added to shopping cart");
        String quantity = driver.findElement(By.id("layer_cart_product_quantity")).getText();
        String cartAttributes = driver.findElement(By.id("layer_cart_product_attributes")).getText();
        String[] cartAttribute = cartAttributes.split(",");
        String colour = "";
        String size = "";
        if (cartAttribute.length == 2) {
            colour = cartAttribute[0].trim();
            size = cartAttribute[1].trim();
        }
        String totalProductCost = driver.findElement(By.id("layer_cart_product_price")).getText();
        String shippingCost = driver.findElement(By.cssSelector("#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div:nth-child(3) > span")).getText();
        String totalCost = driver.findElement(By.cssSelector("#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div:nth-child(4) > span")).getText();
        String totalProdCost = totalProductCost.replace("$", "");
        String shipCost = shippingCost.replace("$", "");
        double expTotalCost = Double.parseDouble(totalProdCost) + Double.parseDouble(shipCost);
        String expectedTotalCost = "$" + expTotalCost;
        // Assertion of the quantity and price
        Assert.assertEquals(quantity, "3", "Order Quantity not correct");
        Assert.assertEquals(colour, "Pink", "Colour not correct");
        Assert.assertEquals(size, "M", "The size is not correct");
        Assert.assertEquals(totalProductCost, "$152.97", "The Total product cost is not correct");
        Assert.assertEquals(totalCost, expectedTotalCost, "The Total product cost is not correct");
        // Printing on the console
        System.out.printf("Quantity is : %14s \n", quantity);
        System.out.printf("Size is : %18s \n", size);
        System.out.printf("color is : %20s \n", colour);
        System.out.printf("Total Product Cost is : %9s \n", totalProdCost);
        System.out.printf("Shipping Cost is : %13s \n", shippingCost);
        System.out.printf("Total Cost is : %18s \n", totalCost);
    }
}






