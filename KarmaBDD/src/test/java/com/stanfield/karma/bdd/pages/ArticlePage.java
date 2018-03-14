package com.stanfield.karma.bdd.pages;

import org.openqa.selenium.WebDriver;

import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

@DefaultUrl("http://localhost:9000/")
public class ArticlePage extends PageObject{
	
	@FindBy(className="jh-entity-details")
	WebElementFacade articleContent;
	
	String browserURL = null;
	
	public ArticlePage(WebDriver driver) {
		super(driver);
		browserURL = System.getProperty("browser.url");
		if (browserURL != null) {
			this.getDriver().get(browserURL);
			System.out.println("Retrieved browser url from system properties");
		}
	}
	
	public boolean containsContent(String contentText) {
		return articleContent.containsText(contentText);
	}
	
  
}
