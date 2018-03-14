package com.stanfield.karma.bdd.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;

import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

@DefaultUrl("http://localhost:9000/")
public class KnowledgeAreaListPage extends PageObject{
	
	@FindBy(tagName="tr")
	List<WebElementFacade> knowledgeArea;
	
	String browserURL = null;
	
	public KnowledgeAreaListPage(WebDriver driver) {
		super(driver);
		browserURL = System.getProperty("browser.url");
		if (browserURL != null) {
			this.getDriver().get(browserURL);
			System.out.println("Retrieved browser url from system properties");
		}
	}
	
	public boolean hasKnowledgeAreas() {
		return knowledgeArea.size() > 0;
	}
	
  
}
