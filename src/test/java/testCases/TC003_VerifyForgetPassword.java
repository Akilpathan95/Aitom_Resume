package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.ForgetPassword;
import pageObject.LoginPage;
import testBase.BaseClass;
import utilities.EmailReader;

import java.time.Duration;

public class TC003_VerifyForgetPassword extends BaseClass {

    @Test
    public void verifyForgetPassword() throws Exception {
        LoginPage lp=new LoginPage(driver);
        lp.clkLoginButton();
        lp.clkForgetPassword();

        ForgetPassword fp=new ForgetPassword(driver);
        fp.enterResetEmail("akil.pathan@orgzstack.com");
        fp.clkSendResetEmail();

        // 2. Wait 5–10 sec for email to arrive
        Thread.sleep(8000);

        // 3. Read email + get verification link
        String link = EmailReader.getVerificationLink(
                "akil.pathan@orgzstack.com",
                "znjs xsco zzzl qekm"
        );

        System.out.println("Verification Link: " + link);

        Assert.assertNotNull(link, "Verification link not found in email!");

        // 4. Open the link in Selenium OR hit via API
        driver.get(link);

        // 5. Add verification assert
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[contains(text(),'Reset your password')]")
        ));

        Assert.assertTrue(heading.isDisplayed(), "Reset Password Page Not Opened!");

        fp.enterNewPassword("Akil@2222");
        fp.clkSubmit();

        WebElement successHeading  = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[contains(text(),'Password changed')]")
        ));

        Assert.assertTrue(successHeading.isDisplayed(), "Password is not changed!");
    }
}
