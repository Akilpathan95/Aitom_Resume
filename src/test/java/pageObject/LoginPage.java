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

    public void clkLoginButton()
    {
        //String mainWindow = driver.getWindowHandle();

        btnLogin.click();
        System.out.println("Clicked on the Login button");

        List<String> tabs=new ArrayList<>(driver.getWindowHandles());

        driver.switchTo().window(tabs.get(1));
        System.out.printf("Switched to a new tab.");
    }

    public void clkSignUpButton()
    {
        btnSignUpAccount.click();
        System.out.println("Clicked on the Sign Up Create Account button");
    }

}
