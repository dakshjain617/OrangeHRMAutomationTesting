package com.demo.orangehrm.pages;

import com.demo.orangehrm.actionDriver.ActionDriver;
import com.demo.orangehrm.base.BaseClass;
import org.openqa.selenium.By;

public class LoginPage {

    By userName = By.xpath("//input[@placeholder='Username']");
    By password = By.xpath("//input[@placeholder='Password']");
    By loginButton = By.xpath("//button[normalize-space()='Login']");
    By errorMessage = By.xpath("//div[@role='alert']");
    private ActionDriver actionDriver;

    public LoginPage() {
        actionDriver = BaseClass.getActionDriver();

    }

    public void login(String username, String pwd) {
        actionDriver.enterText(userName, username);
        actionDriver.enterText(password, pwd);
        actionDriver.clickElement(loginButton);
    }

    public void isErrorMessageDisplayed() {
        actionDriver.isVisible(errorMessage);
    }

    public String getErrorMessageText() {
        return actionDriver.getText(errorMessage);
    }

    public boolean verfiyErrorMessage(String expectedError) {
        return actionDriver.compareText(errorMessage, expectedError);
    }
}
