package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.SearchPage;

public class SearchTest extends Base {
	
	SearchPage searchPage;
	HomePage homePage;
	
	public SearchTest() {
		super();
	}
	
	public WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		
		homePage = new HomePage(driver);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test(priority=1)
	public void verifySearchWithValidProduct() {
		searchPage = homePage.searchForAProduct(dataProp.getProperty("validProduct"));
		
		Assert.assertTrue(searchPage.displayStatusOfHPValidProduct(), "Valid search product HP is not displayed");
	}
	
	@Test(priority=2)
	public void verifySearchWithInvalidProduct() {
		searchPage = homePage.searchForAProduct(dataProp.getProperty("invalidProduct"));
		
		String actualSearchMessage = searchPage.retrieveNoProductMessageText();
		
		Assert.assertEquals(actualSearchMessage, "abcd", "No product message in search list is not displayed");
	}
	
	@Test(priority=3, dependsOnMethods={"verifySearchWithValidProduct","verifySearchWithInvalidProduct"})
	public void verifySearchWithoutAnyProduct() {
		searchPage = homePage.clickOnSearchButton();
		
		String actualSearchMessage = searchPage.retrieveNoProductMessageText();
		
		Assert.assertEquals(actualSearchMessage, dataProp.getProperty("NoProductTextInSearchResults"), "No product message in search list is not displayed");
	}
}
