package com.stanfield.karma.bdd.pages;

import org.openqa.selenium.WebDriver;

import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

@DefaultUrl("http://localhost:9000/")
public class DashboardPage extends PageObject{
	
	@FindBy(id="search")
    WebElementFacade searchField;
	
	@FindBy(id="searchButton")
	WebElementFacade searchButton;
	
	@FindBy(linkText="All Articles")
	WebElementFacade allArticlesLink;
	
	@FindBy(linkText="All Knowledge Areas")
	WebElementFacade allKnowledgeAreasLink;
	
	@FindBy(linkText="All Tags")
	WebElementFacade allTagLink;
	
	String browserURL = null;
	
	public DashboardPage(WebDriver driver) {
		super(driver);
		browserURL = System.getProperty("browser.url");
		if (browserURL != null) {
			this.getDriver().get(browserURL);
			System.out.println("Retrieved browser url from system properties");
		}
	}
	
	public void searchKeyword(String searchString) {
		searchField.clear();
		searchField.sendKeys(searchString);
		searchButton.click();
	}
	
	public void clickAllArticles() {
		allArticlesLink.click();
	}
	
	public void clickAllKnowledgeArea() {
		allKnowledgeAreasLink.click();
	}
	
	public void clickAllTags() {
		allTagLink.click();
	}
	

  
}
