package testCases;

import org.testng.annotations.Test;
import pageObject.LoginPage;
import pageObject.SignUpAccountPage;
import testBase.BaseClass;

public class TC001_SignUpAccount extends BaseClass {

    @Test
    public void verify_SignUpAccount()
    {
        LoginPage lp=new LoginPage(driver);
        lp.clkLoginButton();
        lp.clkSignUpButton();

        SignUpAccountPage sp=new SignUpAccountPage(driver);
        sp.enterFirstName("Akil");
        sp.enterLastName("Pathan");
        sp.enterEmail("akil.pathan@orgzstack.com");
        sp.clkCountry();
        sp.enterMobileNumber("9812345678");
    }
}
