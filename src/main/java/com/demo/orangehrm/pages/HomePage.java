package com.demo.orangehrm.pages;

import com.demo.orangehrm.actionDriver.ActionDriver;
import com.demo.orangehrm.base.BaseClass;
import org.openqa.selenium.By;

public class HomePage {

    By adminTab = By.xpath("//li[1]//a[1]//span[1]");
    By userMenu = By.xpath("//p[@class='oxd-userdropdown-name']");
    By logoutOption = By.xpath("//a[normalize-space()='Logout']");
    By orangehrmLogo = By.xpath("//img[@alt='client brand banner']");
    private ActionDriver actionDriver;

    public HomePage() {
        actionDriver = BaseClass.getActionDriver();
    }

    public boolean isAdminTabVisible() {
        return actionDriver.isVisible(adminTab);
    }

    public boolean verfiyOrnageHrmLogo() {
        return actionDriver.isVisible(orangehrmLogo);
    }

    public void logout() {
        actionDriver.clickElement(userMenu);
        actionDriver.clickElement(logoutOption);
    }
}
