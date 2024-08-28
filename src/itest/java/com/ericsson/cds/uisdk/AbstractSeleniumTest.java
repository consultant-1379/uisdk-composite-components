package com.ericsson.cds.uisdk;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractSeleniumTest {

    private static final String TAF_UI_DEFAULT_OS = "taf_ui.default_OS";

    @BeforeClass
    public static void setUpGridConfiguration() throws Exception {
        System.setProperty(TAF_UI_DEFAULT_OS, "LINUX");
    }

    @AfterClass
    public static void tearDownGridConfiguration() throws Exception {
        System.getProperties().remove("taf_ui.default_OS");
    }
}
