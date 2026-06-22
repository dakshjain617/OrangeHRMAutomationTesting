package com.demo.orangehrm;

import com.demo.orangehrm.base.BaseClass;
import com.demo.orangehrm.pages.HomePage;
import com.demo.orangehrm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseClass {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpPages() {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @Test
    public void testHomePageVisibilityAfterLogin() {
        loginPage.login(BaseClass.getProperties().getProperty("local.username"), BaseClass.getProperties().getProperty("local.password"));
        Assert.assertTrue(homePage.verfiyOrnageHrmLogo(), "Landed on Home Page Successfully");
        homePage.logout();
    }
}
