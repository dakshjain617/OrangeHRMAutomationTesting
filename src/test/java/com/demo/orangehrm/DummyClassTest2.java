package com.demo.orangehrm;


import com.demo.orangehrm.base.BaseClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class DummyClassTest2 extends BaseClass {
    private static final Logger logger = LoggerFactory.getLogger(DummyClassTest2.class);

    //First Ever test case
    //@Test
    public void firstTestCase() {
        logger.info("First Demo Test Case");
        //throw new RuntimeException("Runtime Exception");
    }

    //Part 4
    @Test
    public void firstBaseClassTestCase() {
        logger.info("First Base Class Test Case");
        logger.info("Title : " + BaseClass.getDriver().getTitle());
    }
}
