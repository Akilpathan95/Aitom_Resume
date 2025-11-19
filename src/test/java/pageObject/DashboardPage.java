package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath = "//p[contains(., 'Welcome')]")
    WebElement getWelcometxt;

    public String verifyWelcometxt()
    {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(getWelcometxt));
        return getWelcometxt.getText().trim();
    }
}
