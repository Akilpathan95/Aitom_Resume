package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener
{

    public ExtentSparkReporter sparkReporter; //UI of the report
    public ExtentReports extent; //populate common info on the report
    public ExtentTest test; //Creating test case entriesin the report and update status of the test methods

    String repName;

    public void onStart(ITestContext testContext) {

        /*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date dt=new Date();
        String currentdatetimestamp=df.format(dt);*/

        String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName= "Test-Report-" + timeStamp + ".html";
        sparkReporter=new ExtentSparkReporter(".\\reports\\"+ repName); //specify location of the report

        sparkReporter.config().setDocumentTitle("AitomOrgzstack Automation Report"); //Title of the Report
        sparkReporter.config().setReportName("AitomOrgzstack Functional Testing"); //name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent=new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "AitomOrgzstack");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name",System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os=testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser=testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includedGroups.isEmpty())
        {
            extent.setSystemInfo("Groups",includedGroups.toString());
        }
    }


    public void onTestSuccess(ITestResult result) {

        test=extent.createTest(result.getTestClass().getName()); //Create new entry in report
        test.assignCategory(result.getMethod().getGroups()); //to display groups in report
        test.log(Status.PASS, result.getName() + "got successfully executed");  //Update status pass/fail/skipped

    }

    public void onTestFailure(ITestResult result) {

        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups()); //to display groups in report
        test.log(Status.FAIL, result.getName() + "Test case FAILED ");
        test.log(Status.INFO,  result.getThrowable().getMessage());

        try
        {
            String imgpath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgpath);
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }

    }

    public void onTestSkipped(ITestResult result) {

        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + "Test case SKIPPED is:" );
        test.log(Status.INFO, result.getThrowable().getMessage());

    }

    public void onFinish(ITestContext context) {

        extent.flush();

        String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
        File extentReport=new File(pathOfExtentReport);

        try
        {
            Desktop.getDesktop().browse(extentReport.toURI());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


       /* try
        {
            URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
            //Create the email msg
            ImageHtmlEmail email=new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);

        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }*/


    }


}
