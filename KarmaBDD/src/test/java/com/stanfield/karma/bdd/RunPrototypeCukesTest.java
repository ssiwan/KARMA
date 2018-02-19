package com.stanfield.karma.bdd;
import org.junit.runner.RunWith;


import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = {"src/test/resources/com/stanfield/karma/bdd/features"},
		         monochrome = true,
		         plugin = {"pretty",
		                   "html:target/report/cucumber",
		                   "json:target/report/cucumber.json",
		                   "junit:target/report/cucumber.xml"
		                   }
                
                )

public class RunPrototypeCukesTest {
}