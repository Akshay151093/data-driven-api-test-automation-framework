package listeners;

import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TestListener implements ITestListener{

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("STARTED : " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("PASSED : " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        System.out.println("FAILED : " + result.getMethod().getMethodName());

        Throwable throwable = result.getThrowable();

        if (throwable != null) {

            // Failure message
            Allure.addAttachment(
                    "Failure Message",
                    throwable.getMessage());

            // Complete stack trace
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));

            Allure.addAttachment(
                    "Stack Trace",
                    "text/plain",
                    sw.toString(),
                    ".txt");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("SKIPPED : " + result.getMethod().getMethodName());
    }
}
