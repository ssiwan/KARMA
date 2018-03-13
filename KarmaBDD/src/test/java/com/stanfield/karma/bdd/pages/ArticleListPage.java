package com.stanfield.karma.bdd.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;

import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

@DefaultUrl("http://localhost:9000/")
public class ArticleListPage extends PageObject{
	
	@FindBy(tagName="tr")
	List<WebElementFacade> articles;
	
	String browserURL = null;
	
	public ArticleListPage(WebDriver driver) {
		super(driver);
		browserURL = System.getProperty("browser.url");
		if (browserURL != null) {
			this.getDriver().get(browserURL);
			System.out.println("Retrieved browser url from system properties");
		}
	}
	
	public boolean hasArticles() {
		return articles.size() > 0;
	}
	
  
}
