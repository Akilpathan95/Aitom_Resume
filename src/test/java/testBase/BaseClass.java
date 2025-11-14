package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger; //Log4j
    public Properties p;

    @Parameters({"os","browser"})
    @BeforeSuite
    public void setup(@Optional("Windows")String os, @Optional("chrome")String br) throws IOException{

        //Loading config.properties file
        FileReader file=new FileReader(".//src/test/resources//config.properties");
        p=new Properties();
        p.load(file);

        logger= LogManager.getLogger(this.getClass());

        ChromeOptions options = new ChromeOptions();

        // Disable browser notifications
        options.addArguments("--disable-notifications");

        if (p.getProperty("execution_env").equalsIgnoreCase("remote"))
        {
            DesiredCapabilities capabilities=new DesiredCapabilities();
            //os
            if (os.equalsIgnoreCase("windows"))
            {
                //options.setBrowserVersion("latest");
                capabilities.setPlatform(Platform.WIN11);
            }
            if (os.equalsIgnoreCase("linux"))
            {
                capabilities.setPlatform(Platform.LINUX);
            }
            else if (os.equalsIgnoreCase("mac"))
            {
                capabilities.setPlatform(Platform.MAC);
            }
            else
            {
                System.out.println("No matching os");
                return;
            }

            //browser
            switch (br.toLowerCase())
            {
                case  "chrome": capabilities.setBrowserName("chrome"); break;
                case  "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
                case  "firefox": capabilities.setBrowserName("firefox"); break;
                default: System.out.println("No matching browser");
                return;

            }
            driver=new RemoteWebDriver(new URL("http://192.168.0.101:4444/wd/hub"), capabilities);
        }
        if (p.getProperty("execution_env").equalsIgnoreCase("local"))
        {
            switch (br.toLowerCase())
            {
                case "chrome" : driver=new ChromeDriver(options); break;
                case "edge" : driver=new EdgeDriver(); break;
                case "firefox" : driver=new FirefoxDriver(); break;
                default: System.out.println("Invalid browser name....");
                return;
            }
        }

        driver.manage().deleteAllCookies();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(p.getProperty("url")); //Reading url from Properties file.
        driver.manage().window().maximize();

    }

    @AfterSuite(groups = "Master")
    public void tearDown()
    {
        driver.quit();
    }

    public String randomString()
    {
        String generatedString= RandomStringUtils.randomAlphabetic(5);
        return generatedString;
    }

    public String randomNumber()
    {
        String generatedNumber=RandomStringUtils.randomNumeric(10);
        return generatedNumber;
    }

    public String randomAlphaNumeric()
    {
        String generatedString=RandomStringUtils.randomAlphabetic(3);
        String generatedNumber=RandomStringUtils.randomNumeric(3);
        return (generatedString+"@"+generatedNumber);
    }

    public String randomStringNumeric()
    {
        String generatedString=RandomStringUtils.randomAlphabetic(3);
        String generatedNumber=RandomStringUtils.randomNumeric(3);
        return (generatedString + generatedNumber);
    }
//https://github.com/Akilpathan95/AitomOrgzStack.git
    public String captureScreen(String tname) throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile =takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile=new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;
    }

}
