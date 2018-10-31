package com.scottlogic.librarygradproject.cucumber.steps;


import cucumber.api.java8.En;
import static org.junit.Assert.*;



public class Stepdefs implements En {
    private boolean run = false;
    public Stepdefs() {

        Given("test is run", () -> this.run = true);

        Then("test should succeed", () ->  assertTrue(run));

    }

}