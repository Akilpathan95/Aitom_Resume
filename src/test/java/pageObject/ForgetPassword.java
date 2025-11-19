package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgetPassword extends BasePage{

    public ForgetPassword(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath = "//input[@name=\"resetEmail\"]")
    WebElement txtResetEmail;

    @FindBy(xpath = "//button[normalize-space()='Send Reset Link']")
    WebElement btnSendResetLink;

    @FindBy(xpath = "//input[@name='newPassword']")
    WebElement txtNewPassword;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnSubmit;

    public void enterResetEmail(String email)
    {
        txtResetEmail.sendKeys(email);
        System.out.println("Reset Email sent to the user");
    }

    public void clkSendResetEmail()
    {
        btnSendResetLink.click();
        System.out.println("Clicked on the Send Reset Email Button");
    }

    public void enterNewPassword(String password)
    {
        txtNewPassword.sendKeys(password);
        System.out.println("Enter the new password");
    }

    public void clkSubmit()
    {
        btnSubmit.click();
        System.out.println("Clicked on the Submit button");
    }
}
