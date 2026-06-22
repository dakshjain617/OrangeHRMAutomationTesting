package com.demo.orangehrm;

import com.demo.orangehrm.base.BaseClass;
import com.demo.orangehrm.pages.HomePage;
import com.demo.orangehrm.pages.LoginPage;
import com.demo.orangehrm.utils.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseClass {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpPages() {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @Test
    public void testValidCredentials() {
        ExtentManager.startTest("Validate Login Credentials");
        ExtentManager.logStep("Navigating to the Login page and entering username & password");
        loginPage.login(BaseClass.getProperties().getProperty("local.username"), BaseClass.getProperties().getProperty("local.password"));
        ExtentManager.logStep("Entered Username and Password");
        Assert.assertTrue(homePage.isAdminTabVisible(), "Login Working Successfully");
        ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Entered Username and Password are correct and we successfully laned on the Home screen", "HomePage");
        ExtentManager.endTest();
        homePage.logout();
    }

    @Test
    void testInvalidCredentials() {
        loginPage.login(BaseClass.getProperties().getProperty("local.username"), "jahgdjuahgv");
        Assert.assertTrue(loginPage.verfiyErrorMessage("Invalid credentials"), "Invalid Login Credentials");

    }
}
