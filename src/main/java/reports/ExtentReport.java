package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;
import utils.DriverFactory;
import utils.TestUtil;

import java.io.IOException;
import java.util.Properties;

import static setup.ReadProperties.loadProperties;

public class ExtentReport {
    public static ExtentReports extent;
    public static ExtentTest extentTest;
    public static ExtentTest extentTestChild;
    public Properties prop;

    public ExtentReport(String browserName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
        htmlReporter.config().setDocumentTitle("Automation Report"); // Title of the report
        htmlReporter.config().setReportName("Automated Tests Report"); // Name of the report
        htmlReporter.config().setTheme(Theme.DARK);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        try {
            prop = loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        extent.setSystemInfo("Operating system name", System.getProperty("os.name"));
        extent.setSystemInfo("OS architecture", System.getProperty("os.arch").toUpperCase());
//        extent.setSystemInfo("Java version", System.getProperty("java.version"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Browser",browserName.toUpperCase());
        extent.setSystemInfo("URL", prop.getProperty("url"));
    }

    public static void addTestCaseStep(String testCaseStep) {
        extentTestChild.log(Status.PASS, testCaseStep);
    }

    public static void createTestCaseDescription(String testCaseDescription) {
        extentTestChild = extentTest.createNode(testCaseDescription);
    }

    public static void createTestCaseTitle(String testCaseTitle) {
        extentTest = extent.createTest(testCaseTitle);
    }

    public static void assignCategory(String category){
        extentTest.assignCategory(category);
    }

    public static void createTestCase(String testCaseTitle, String testCaseDescription) {
        createTestCaseTitle(testCaseTitle);
        createTestCaseDescription(testCaseDescription);
    }

    public void logFailure(ITestResult result) throws IOException {
        DriverFactory factory = DriverFactory.getInstance();
        extentTestChild.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
        extentTestChild.log(Status.FAIL, result.getThrowable());

        String screenshotPath = TestUtil.getScreenshot(factory.getDriver(), result.getName());
        System.out.println(screenshotPath);
        extentTestChild.fail("Snapshot below : ").addScreenCaptureFromPath(screenshotPath);
    }

    public void logSuccess(ITestResult result) {
        extentTestChild.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case PASSED", ExtentColor.GREEN));
    }

    public void logSkip(ITestResult result) {
        extentTestChild.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
    }

    public void endReport(){
        extent.flush();
    }

    public void setBackend(String webrellaAPI, String ssoAPI){
        extent.setSystemInfo("---Backend API---","------");
        extent.setSystemInfo("Webrella API",webrellaAPI);
        extent.setSystemInfo("SSO API",ssoAPI);
    }
}
