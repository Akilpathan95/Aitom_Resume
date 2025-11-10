package pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    WebDriver driver;
    WebDriverWait wait;
    Alert alert;
    JavascriptExecutor js;

    public BasePage(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void handleAlert() {
        try
        {
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert Text: " + alert.getText());
            alert.accept(); // Click OK or accept the alert
            System.out.println("Alert accepted.");
        } catch (TimeoutException | NoAlertPresentException e)
        {
            System.out.println("Alert did not appear. Continuing test... " + e.getMessage());
        } catch (UnhandledAlertException e)
        {
            System.out.println("Unhandled alert exception: " + e.getMessage());

            try
            {
                Alert alert = driver.switchTo().alert();
                alert.accept(); // Attempt to accept it
            }
            catch (Exception innerEx)
            {
                System.out.println("Failed to handle unexpected alert.");
            }

        }
    }

    //Method to get POP-UP text
    public String getAlertText()
    {
        try
        {
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            alert = driver.switchTo().alert();
            return alert.getText(); //return the POP-UP text
        }
        catch (NoAlertPresentException e)
        {
            System.out.println("No Validation Pop-Up is displayed");
            return null;
        }

    }

    public void javascript(WebElement element)
    {
        js=(JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public boolean isAlertPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.alertIsPresent());
            return true; // Alert is present
        } catch (Exception e) {
            return false; // No alert
        }
    }

    public void toastMessage()
    {
        try {
            wait=new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement text=driver.findElement(By.xpath("//div[@role=\"alert\"]"));
            wait.until(ExpectedConditions.visibilityOf(text));
            String getText=text.getText();
            System.out.println("Text on the POP-UP is : " + getText);

            WebElement cancelButton=driver.findElement(By.xpath("//*[name()=\"path\" and contains(@fill-rule,\"evenodd\")]"));

            wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
            cancelButton.click();
            System.out.println("Clicked on the Cancel button");
        }
        catch (Exception e)
        {
            System.out.println("Alert is not present : " + e.getMessage());
        }
    }
}
