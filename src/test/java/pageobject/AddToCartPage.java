package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import static mfstestcases.BaseTestCase.wait;

public class AddToCartPage {
    WebDriver driver = null;
    Actions action = null;

    public AddToCartPage (WebDriver driver) {
        this.driver = driver;}


        By women_link = By.linkText("Women");
        By dresses_link =  By.linkText("Dresses");
        By eveningDresses_link = By.linkText("Evening Dresses");
        By selectSize = By.id("layered_id_attribute_group_2");
        By selectColor = By.id("layered_id_attribute_group_24");
        By clickMore = By.cssSelector("a.button.lnk_view.btn.btn-default");
        By quantity_TextBox = By.name("qty");
        By selectPink = By.name("Pink");
        By submitButton = By.name("Submit");

        By slider_element = By.xpath("//*[@id=\"layered_price_slider\"]/a[2]");
        By price_range_value = By.id("layered_price_range");
        By product_container = By.className("product-container");

        By select_size_dropdown = By.id("group_1");

        public void selectSize(String size){
            Select select = new Select(driver.findElement(select_size_dropdown));
            select.selectByVisibleText(size);
        }

        public void hoverOnProductContainer(){
            action.moveToElement(driver.findElement(product_container)).perform();
        }

public void slideToPriceRange(String desiredPriceRange) {
    action = new Actions(driver);
    WebElement Slider = driver.findElement(slider_element);
    int xcord = 0;
    String priceRange = "";
    // Selecting the desired price
    action.click(Slider).build().perform();
    while (!priceRange.equals(desiredPriceRange)) {
        action.sendKeys(Keys.ARROW_LEFT).build().perform();
        xcord--;
        if (xcord < -100) {
            break;
        }
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(price_range_value));
        priceRange = driver.findElement(price_range_value).getText().replaceAll("\\s+", "");
    }
}


    public void clickWomenLink() { driver.findElement(women_link).click(); }

    public void clickDressesLink() { driver.findElement(dresses_link).click(); }

    public void clickEveningDressesLink() {driver.findElement(eveningDresses_link).click();}

    public void clickCheckboxM(){driver.findElement(selectSize).click();}

    public void clickColor(){driver.findElement(selectColor).click();}

    public void  clickMoreButton(){driver.findElement(clickMore).click();}

    public void quantityTextBox(){
   driver.findElement(quantity_TextBox).clear();
    driver.findElement(quantity_TextBox).sendKeys("3");
    }

    public void selectPinkColor(){driver.findElement(selectPink).click();}

    public void clickSubmitButton(){driver.findElement(submitButton).click();
    }
}
