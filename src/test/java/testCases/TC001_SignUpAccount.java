package testCases;

import org.testng.Assert;
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

        String actualText=lp.getSignUpRegisterText();
        String expectedText="Sign up / Register Here";
        Assert.assertEquals(actualText, expectedText, "Sign up/Register text mismatch!");

        SignUpAccountPage sp=new SignUpAccountPage(driver);
        sp.enterFirstName("Akil");
        sp.enterLastName("Pathan");
        sp.enterEmail("akil.pathan@orgzstack.com");
        sp.clkCountry();
        sp.enterMobileNumber("9812345678");
        sp.clkEducation();
        sp.enterExperience("2");
        sp.clkIndustry();
        sp.clkRole();
        sp.clkTermsConditions();
        sp.clkSubmit();

        String actualResult = sp.getPasswordSetupText();
        String expectedResult="Password Setup";
        Assert.assertEquals(actualResult, expectedResult, "We are not on Password Setup Page!");

        sp.enterPassword("Akil@1234");
        sp.enterConfirmPassword("Akil@1234");
        sp.clkSetPassword();
    }
}
