package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountSuccessPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.RegisterPage;
import com.tutorialsninja.qa.utils.Utilities;

public class RegisterTest extends Base {
	
	RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;
	
	public RegisterTest() {
		super();
	}
	
	public WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		
		HomePage homePage = new HomePage(driver);
		registerPage = homePage.navigateToRegisterPage();
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test(priority=1)
	public void verifyRegisteringAnAccountWithMandatoryFields() {
		accountSuccessPage = registerPage.registerWithMandatoryFields(dataProp.getProperty("firstName"), dataProp.getProperty("lastName"), Utilities.generateEmailWithTimestamp(), dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));
		
		String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
		String expectedSuccessHeading = dataProp.getProperty("accountSuccessfullyCreatedHeading");
		
		Assert.assertEquals(actualSuccessHeading, expectedSuccessHeading, "register is not successfuly done");
	}
	
	@Test(priority=2)
	public void verifyRegisteringAccountByProvidingAllFields() {
		accountSuccessPage = registerPage.registerWithMandatoryFields(dataProp.getProperty("firstName"), dataProp.getProperty("lastName"), Utilities.generateEmailWithTimestamp(), dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));
		
		String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
		String expectedSuccessHeading = dataProp.getProperty("accountSuccessfullyCreatedHeading");
		
		Assert.assertEquals(actualSuccessHeading, expectedSuccessHeading, "register is not successfuly done");

	}
	
	@Test(priority=3)
	public void verifyRegisteringAccountWithExistingEmailAddress() {
		registerPage.registerWithMandatoryFields(dataProp.getProperty("firstName"), dataProp.getProperty("lastName"), prop.getProperty("validEmail"), dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));
		
		String actualWarningText = registerPage.retrieveDuplicateEmailAddressWarning();
		String expectedWarningText = dataProp.getProperty("duplicateEmailWarning");
		
		Assert.assertTrue(actualWarningText.contains(expectedWarningText), "register email is already exists is not displayed");
	}
	
	@Test(priority=4)
	public void verifyRegisteringAccountWithoutFillingAnyDetails() {
		
		registerPage.clickOnContinueButton();
		
		String actualPrivacyPolicyWarningText = registerPage.retrievePrivacyPolicyWarning();
		String expectedPrivacyPolicWarningText = dataProp.getProperty("privacyPolicyWarning");
		Assert.assertTrue(actualPrivacyPolicyWarningText.contains(expectedPrivacyPolicWarningText), "Privacy Policy warning message is not displayed");
		
		String actualFirstNameWarningText = registerPage.retrieveFirstNameWarning();
		Assert.assertEquals(actualFirstNameWarningText, dataProp.getProperty("firstNameWarning"), "First Name warning message is not displayed");
		
		String actualLastNameWarningText = registerPage.retrieveLastNameWarning();
		Assert.assertEquals(actualLastNameWarningText, dataProp.getProperty("lastNameWarning"), "Last Name warning message is not displayed");
		
		String actualEmailWarningText = registerPage.retrieveEmailWarning();
		Assert.assertEquals(actualEmailWarningText, dataProp.getProperty("emailWarning"), "Email warning message is not displayed");
		
		String actualTelephoneWarningText = registerPage.retrieveTelephoneWarning();
		Assert.assertEquals(actualTelephoneWarningText, dataProp.getProperty("telephoneWarning"), "Telephone warning message is not displayed");
		
		String actualPasswordWarningText = registerPage.retrievePasswordWarning();
		Assert.assertEquals(actualPasswordWarningText, dataProp.getProperty("passwordWarning"), "Password warning message is not displayed");
	}
}
