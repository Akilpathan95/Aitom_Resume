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

    Select select;

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

    @FindBy(xpath = "//select[@name='qualification']")
    WebElement selectEducation;

    @FindBy(xpath = "//input[@name='experience']")
    WebElement txtExperience;

    @FindBy(xpath = "//select[@name='industry']")
    WebElement selectIndustry;

    @FindBy(xpath = "//select[@name='role']")
    WebElement selectRole;

    @FindBy(xpath = "//input[@id='termsCheckbox']")
    WebElement selectTermsConditions;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnSubmit;

    @FindBy(xpath = "//*[contains(text(), 'Password Setup')]")
    WebElement textPasswordSetupText;

    public String getPasswordSetupText()
    {
        return textPasswordSetupText.getText().trim();
    }

    @FindBy (xpath = "(//label[normalize-space()='Enter your Password']/following::input)[1]")
    WebElement txtEnterPassword;

    @FindBy(xpath = "//label[normalize-space()='Confirm your Password']/following::input")
    WebElement txtConfirmPassword;

    @FindBy(xpath = "//button[normalize-space()='Set Password']")
    WebElement btnSetPassword;

    public void enterFirstName(String firstName)
    {
        txtFirstName.sendKeys(Keys.CONTROL + "a");
        txtFirstName.sendKeys(Keys.DELETE);
        txtFirstName.sendKeys(firstName);
        System.out.println("First Name entered");
    }

    public void enterLastName(String lastName)
    {
        txtLastName.sendKeys(Keys.CONTROL + "a");
        txtLastName.sendKeys((Keys.DELETE));
        txtLastName.sendKeys(lastName);
        System.out.println("Last Name entered");


    }

    public void enterEmail(String email)
    {
        txtEmail.sendKeys(Keys.CONTROL + "a");
        txtEmail.sendKeys(Keys.DELETE);
        txtEmail.sendKeys(email);
        System.out.println("Email entered");

    }

    public void clkCountry()
    {
        select=new Select(selectCountry);
        select.selectByVisibleText("India");
        System.out.println("Country Selected");

    }

    public void enterMobileNumber(String mobileNumber)
    {
        txtMobileNumber.sendKeys(Keys.CONTROL + "a");
        txtMobileNumber.sendKeys((Keys.DELETE));
        txtMobileNumber.sendKeys(mobileNumber);
        System.out.println("Mobile Number entered");

    }

    public void clkEducation()
    {
        select=new Select(selectEducation);
        select.selectByVisibleText("Bachelor's Degree");
        System.out.println("Education Selected");
    }

    public void enterExperience(String exp)
    {
        txtExperience.sendKeys(exp);
        System.out.println("Experience entered");
    }

    public void clkIndustry()
    {
        select=new Select(selectIndustry);
        select.selectByVisibleText("Technology");
        System.out.println("Technology selected");
    }

    public void clkRole()
    {
        select=new Select(selectRole);
        select.selectByVisibleText("QA Engineer");
        System.out.println("Role selected");
    }

    public void clkTermsConditions()
    {
        selectTermsConditions.click();
        System.out.println("Terms and Conditions selected");
    }

    public void clkSubmit()
    {
        btnSubmit.click();
        System.out.println("Clicked on the submit button");
    }

    public void enterPassword(String pass)
    {
        txtEnterPassword.sendKeys(pass);
        System.out.println("Password is entered");
    }

    public void enterConfirmPassword(String pass)
    {
        txtConfirmPassword.sendKeys(pass);
        System.out.println("Confirm Password entered");
    }

    public void clkSetPassword()
    {
        btnSetPassword.click();
        System.out.println("Clicked on the Set Password button");
    }
}
