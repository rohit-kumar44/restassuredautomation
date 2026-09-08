package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repname;
	
	public void onStart(ITestContext testContext)
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
		repname="Test-Report-"+timeStamp+".html";
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+repname);//specify location of the report
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application","Users API");
		extent.setSystemInfo("Operating System",System.getProperty("os.name"));
		extent.setSystemInfo("User Name",System.getProperty("user.name"));
		extent.setSystemInfo("Environemnt","QA");
		extent.setSystemInfo("user","pavan");
		sparkReporter.config().setDocumentTitle("API Test Project"); // Tile of report
		sparkReporter.config().setReportName("User API Test Automation Report"); // name of the report //location of the chart
		sparkReporter.config().setTheme(Theme.DARK);
	}
	public void onTestSuccess(ITestResult tr)
	{
		test=extent.createTest(tr.getName()); // create new entry in th report
		test.assignCategory(tr.getMethod().getGroups());
		test.createNode(tr.getName());
		test.log(Status.PASS,MarkupHelper.createLabel(tr.getName(),ExtentColor.GREEN)); // send the passed information to the report with GREEN color highlighted
	}
	public void onTestFaliure(ITestResult tr)
	{
		test=extent.createTest(tr.getName()); // create new entry in th report
		test.assignCategory(tr.getMethod().getGroups());
		test.createNode(tr.getName());
		test.log(Status.FAIL, "Test Fail");
		test.log(Status.FAIL, tr.getThrowable().getMessage());
		test.log(Status.FAIL,MarkupHelper.createLabel(tr.getName(),ExtentColor.RED)); // send the passed information to the report with GREEN color highlighted
	}
	public void onTestSkipped(ITestResult tr)
	{
		test=extent.createTest(tr.getName()); // create new entry in th report
		test.assignCategory(tr.getMethod().getGroups());
		test.createNode(tr.getName());
		test.log(Status.SKIP, "Test Skipped");
		test.log(Status.SKIP, tr.getThrowable().getMessage());
		test.log(Status.SKIP,MarkupHelper.createLabel(tr.getName(),ExtentColor.AMBER)); // send the passed information to the report with GREEN color highlighted
	}
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
	}
}
