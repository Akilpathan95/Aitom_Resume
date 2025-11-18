package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.DashboardPage;
import pageObject.LoginPage;
import testBase.BaseClass;

public class TC002_VerifyLogin extends BaseClass {

    @Test
    public void verifyLogin()
    {
        LoginPage lp=new LoginPage(driver);
        lp.clkLoginButton();
        lp.enterEmail("akil.pathan@orgzstack.com");
        lp.enterPassword("Akil@7086");
        lp.clkLoginButton();

        DashboardPage dp=new DashboardPage(driver);
        String actualResult=dp.verifyWelcometxt();
        System.out.println(actualResult);
        Assert.assertTrue(actualResult.contains("Welcome"), "Expected Welcome in the text but we found: " + actualResult);

    }
}
