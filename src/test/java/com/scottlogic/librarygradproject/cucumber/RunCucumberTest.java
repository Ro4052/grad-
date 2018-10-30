package com.scottlogic.librarygradproject.cucumber;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        glue = {"com.scottlogic.librarygradproject.cucumber.steps"},
        features = {"src/test/java/com/scottlogic/librarygradproject/cucumber/features"}
)

public class RunCucumberTest {
}