package pageObject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class SignUpAccountPage extends BasePage {

    public SignUpAccountPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath = "//input[@placeholder='Enter your first name']")
    WebElement txtFirstName;

    @FindBy(xpath = "//input[@placeholder=\"Enter your last name\"]")
    WebElement txtLastName;

    @FindBy(xpath = "//input[@placeholder=\"Enter your email\"]")
    WebElement txtEmail;

    @FindBy(xpath = "//select[@name='country']")
    WebElement selectCountry;

    @FindBy(xpath = "//input[@name='mobile']")
    WebElement txtMobileNumber;

    public void enterFirstName(String firstName)
    {
        txtFirstName.sendKeys(Keys.CONTROL + "a");
        txtFirstName.sendKeys(Keys.DELETE);
        txtFirstName.sendKeys(firstName);
    }

    public void enterLastName(String lastName)
    {
        txtLastName.sendKeys(Keys.CONTROL + "a");
        txtLastName.sendKeys((Keys.DELETE));
        txtLastName.sendKeys(lastName);

    }

    public void enterEmail(String email)
    {
        txtEmail.sendKeys(Keys.CONTROL + "a");
        txtEmail.sendKeys(Keys.DELETE);
        txtEmail.sendKeys(email);
    }

    public void clkCountry()
    {
        Select country=new Select(selectCountry);
        country.selectByVisibleText("India");
    }

    public void enterMobileNumber(String mobileNumber)
    {
        txtMobileNumber.sendKeys(Keys.CONTROL + "a");
        txtMobileNumber.sendKeys((Keys.DELETE));
        txtMobileNumber.sendKeys(mobileNumber);
    }
}
