package com.demo.orangehrm.actionDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ActionDriver {
    private static final Logger logger = LoggerFactory.getLogger(ActionDriver.class);

    public WebDriver driver;
    public WebDriverWait wait;

    public ActionDriver(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    //Wait for element to be clickable
    public void isClickable(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            logger.error("Is Clickable Failed : ", e);
        }
    }

    //Wait for element to be displayed
    public boolean isVisible(By by) {
        boolean isVisible = false;
        try {
            isVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed();
        } catch (Exception e) {
            logger.error("Is Visible Failed : ", e);
        }
        return isVisible;
    }

    //Method to click an element
    public void clickElement(By by) {
        try {
            isClickable(by);
            driver.findElement(by).click();
        } catch (Exception e) {
            logger.error("Click Element Failed : ", e);
        }
    }

    //Method to enter text into an input field
    public void enterText(By by, String inputText) {
        try {
            isVisible(by);
            driver.findElement(by).sendKeys(inputText);
        } catch (Exception e) {
            logger.error("Enter Text Failed : ", e);
        }
    }

    //Method to get text from an element
    public String getText(By by) {
        String text = null;
        try {
            isVisible(by);
            text = driver.findElement(by).getText();
        } catch (Exception e) {
            logger.error("Get Text Failed : ", e);
        }
        return text;
    }

    //Method to compare two text
    public boolean compareText(By by, String expectedText) {
        String text = null;
        try {
            isVisible(by);
            text = driver.findElement(by).getText();
        } catch (Exception e) {
            logger.error("Text Comparison Failed : ", e);
        }
        return text.equalsIgnoreCase(expectedText);
    }

    //Method to scroll to an element (Using JavaScript)
    public void scrollToAnElement(By by) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(by));
        } catch (Exception e) {
            logger.error("Scroll to an element Failed : ", e);
        }
    }

    //Method to wait for page load (Using JavaScript)
    public void waitForPageLoad(int durationInSeconds) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            wait.withTimeout(Duration.ofSeconds(durationInSeconds))
                    .until(WebDriver -> (JavascriptExecutor) WebDriver)
                    .executeScript("document.readyState").equals("complete");
        } catch (Exception e) {
            logger.error("Page Load Failed : ", e);
        }
    }

}
