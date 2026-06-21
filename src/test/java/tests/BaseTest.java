package tests;

import assertions.UserAssertions;
import listeners.TestListener;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import utils.LogManagerUtil;

@Listeners(TestListener.class)
public class BaseTest {

    private static final Logger logger = LogManagerUtil.getLogger(BaseTest.class);
    protected UserAssertions userAssertions;

    public BaseTest() {
        userAssertions = new UserAssertions();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod(ITestResult result) {
        logger.info("Test Starts: {}", result.getMethod().getMethodName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterTestMethod(ITestResult result) {
        logger.info("Test Ends: {} | Status: {}",
                result.getMethod().getMethodName(),
                getTestStatus(result));
    }

    private String getTestStatus(ITestResult result) {
        return switch (result.getStatus()) {
            case ITestResult.SUCCESS -> "PASSED";
            case ITestResult.FAILURE -> "FAILED";
            case ITestResult.SKIP -> "SKIPPED";
            default -> "UNKNOWN";
        };
    }
}