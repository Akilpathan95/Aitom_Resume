package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath = "//button[normalize-space()='Login']")
    WebElement btnLogin;

    @FindBy(xpath = "//span[normalize-space()='Sign up to create account']")
    WebElement btnSignUpAccount;

    @FindBy(xpath = "//*[contains(text(), 'Sign') and contains(text(), 'Register')]")
    WebElement txtSignUpRegister;

    public String getSignUpRegisterText()
    {
        return txtSignUpRegister.getText().trim();
    }

    public void clkLoginButton()
    {
        //String mainWindow = driver.getWindowHandle();
        driver.getTitle();

        btnLogin.click();
        System.out.println("Clicked on the Login button");

        List<String> tabs=new ArrayList<>(driver.getWindowHandles());

        driver.switchTo().window(tabs.get(1));
        System.out.println("Switched to a new tab.");



    }

    public void clkSignUpButton()
    {
        btnSignUpAccount.click();
        System.out.println("Clicked on the Sign Up Create Account button");
    }

}
