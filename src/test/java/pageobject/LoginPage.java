package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver = null;

    public LoginPage (WebDriver driver) {
        this.driver = driver;
    }

    By sign_in_link = By.className("login");
    By username_textbox = By.id("email");
    By password_textbox = By.id("passwd");
    By submit_button = By.id("SubmitLogin");

    public void clickSignInLink () {
        driver.findElement(sign_in_link).click(); }

    public void setUsername(String username) {
        driver.findElement(username_textbox).sendKeys(username);
    }

    public void setPassword(String password) {
        driver.findElement(password_textbox).sendKeys(password);
    }

    public void clickSubmitButton () {
        driver.findElement(submit_button).click();
    }


}
