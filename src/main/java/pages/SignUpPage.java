package pages;

import base.WebPageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.elements.ElementManager;
import reports.ExtentReport;
import utils.TestUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.testng.Assert.assertTrue;

public class SignUpPage extends WebPageBase {
//    private WebDriver driver;

    //Constructor
    private SignUpPage() {
//        driver = factory.getDriver();
    }

    public static SignUpPage getSignUpPage() {
        return new SignUpPage();
    }
    //-----------


    //-----------GETTERS
    public WebElement email_field() {
        return getWebElement(ElementManager.SIGNUPPAGE_EMAIL_FIELD);
    }

    public WebElement verificationCode_field() {
        return getWebElement(ElementManager.SIGNUPPAGE_VERIFICATIONCODE_FIELD);
    }

    public WebElement sendVerCode_BTN() {
        return getWebElement(ElementManager.SIGNUPPAGE_SENDVERIFICATIONCODE_BTN);
    }

    public WebElement verifyCode_BTN() {
        return getWebElement(ElementManager.SIGNUPPAGE_VERIFYCODE_BTN);
    }

    public WebElement changeEmail_BTN() {
        checkIfElementHasLoaded(getWebElement(ElementManager.SIGNUPPAGE_CHANGEEMAIL_BTN),
                "Token from email is validated; change E-mail button is displayed");
        return getWebElement(ElementManager.SIGNUPPAGE_CHANGEEMAIL_BTN);
    }

    public WebElement sendNewCode_BTN() {
        return getWebElement(ElementManager.SIGNUPPAGE_SENDNEWCODE_BTN);
    }

    public WebElement create_BTN() {
        return getWebElement(ElementManager.SIGNUPPAGE_CREATE_BTN);
    }

    public WebElement cancel_BTN() {
        return getWebElement(ElementManager.SIGNUPPAGE_CANCEL_BTN);
    }

    public WebElement incorrectVerCode_Msg() {
        return getWebElement(ElementManager.SIGNUPPAGE_INCORRECTVERCODE_ERR);
    }

    public WebElement expiredVerCode_Msg() {
        return getWebElement(ElementManager.SIGNUPPAGE_EXPIREDVERCODE_ERR);
    }

    public WebElement invalidEmail_Msg() {
        return getWebElement(ElementManager.SIGNUPPAGE_INVALIDEMAIL_ERR);
    }

    public WebElement invalidPass_Msg() {
        return getWebElement(ElementManager.SIGNUPPAGE_INVALIDPASSWORD_ERR);
    }

    public WebElement invalidConfirmPass_Msg() {
        return getWebElement(ElementManager.SIGNUPPAGE_INVALIDCONFIRMATIONPASSWORD_ERR);
    }

    public WebElement existingUserErr_Msg() {
        return getWebElement(ElementManager.SIGNUPPAGE_EXISTINGUSER_ERR);
    }

    public WebElement newPassword_field() {
        return getWebElement(ElementManager.SIGNUPPAGE_NEWPASSWORD_FIELD);
    }

    public WebElement confNewPassword_field() {
        return getWebElement(ElementManager.SIGNUPPAGE_CONFIRMPASSWORD_FIELD);
    }

    public WebElement firstName_field() {
        return getWebElement(ElementManager.SIGNUPPAGE_FIRSTNAME_FIELD);
    }

    public WebElement lastName_field() {
        return getWebElement(ElementManager.SIGNUPPAGE_LASTNAME_FIELD);
    }

    public WebElement country_dropdown() {
        return getWebElement(ElementManager.SIGNUPPAGE_COUNTRY_DROPDOWN);
    }

    public WebElement consentYes_BTN() {
        return getWebElement(ElementManager.SIGNUPPAGE_CONSENTYES_BTN);
    }

    public WebElement consentNo_BTN() {
        return getWebElement(ElementManager.SIGNUPPAGE_CONSENTNO_BTN);
    }

    public WebElement requiredFieldMissing_Msg() {
        return getWebElement(ElementManager.SIGNUPPAGE_REQUIREDFIELD_ERR);
    }

    public WebElement tooManyAttempts_Msg() {
        return getWebElement(ElementManager.SIGNUPPAGE_TOOMANYATTEMPTS_ERR);
    }

    public WebElement mismatchingPass_Msg() {
        return getWebElement(ElementManager.SIGNUPPAGE_PASSWORDMISSMATCH_ERR);
    }

    public WebElement blankCountry_Msg() {
        return getWebElement(ElementManager.SIGNUPPAGE_BLANKCOUNTRY_ERR);
    }

    public WebElement emailThrottled_Msg(){return getWebElement(ElementManager.SIGNUPPAGE_EMAILTHROTTLED_ERR);}

    //-----------

    //-----------SETTERS

    public SignUpPage setEmail(String email) {
        setField(email_field(), email, "Entered the following email: " + email);
        return this;
    }

    public SignUpPage setVerificationCode_field(String code) {
        setField(verificationCode_field(), code, "Entered the following Verification Code: " + code);
        return this;
    }

    public SignUpPage setNewPassword(String password) {
        setField(newPassword_field(), password, "Entered the following password: " + password);
        return this;
    }

    public SignUpPage setConfirmNewPassword(String confirmNewPassword) {
        setField(confNewPassword_field(), confirmNewPassword,
                "Entered the following password in the Confirm password field: " + confirmNewPassword);
        return this;
    }

    public SignUpPage setFirstName(String firstName) {
        setField(firstName_field(), firstName, "Entered a first name: " + firstName);
        return this;
    }

    public SignUpPage setLastName(String lastName) {
        setField(lastName_field(), lastName, "Entered a last name: " + lastName);
        return this;
    }

    //-----------

    //Actions

    public SignUpPage clearField(WebElement webElement) {
        clearWebElement(webElement);
        return this;
    }


    public SignUpPage sendTokenToEmail(String email) {
        setEmail(email)
                .clickOn_sendVerificationCode_BTN();
        try {
            verificationCode_field().click();
        } catch (Exception e) {
            if (emailThrottled_Msg().isDisplayed()) {
                double minutesToWait = 5;
                ExtentReport.addTestCaseStep("Email throttled error message is displayed. Waiting" + minutesToWait + " and " +
                        "will retry again.");
                TestUtil.waitForSomeMinutes(5);
                ExtentReport.addTestCaseStep("Retrying after " + minutesToWait + " minutes");
                sendTokenToEmail(email);
            }
        }

        return this;
    }

    public SignUpPage clickOn_sendVerificationCode_BTN() {
        clickAction(sendVerCode_BTN(), "Clicked on Send Verification Code button");
        return this;
    }

    public SignUpPage selectRandomCountry() {
        Select country_select = new Select(country_dropdown());
        Random random = new Random();
        int index = random.nextInt(country_select.getOptions().size());
        country_select.selectByIndex(index);
        List<WebElement> countries = country_select.getOptions();
        ExtentReport.addTestCaseStep("Selected a random country from the dropdown list: " + countries.get(index).getText());
        return this;
    }

    public SignUpPage selectRandomConsent() {
        Random random = new Random();
        if (random.nextInt(2) % 2 == 0) {
            consentNo_BTN().click();
            ExtentReport.addTestCaseStep("Selected randomly if I consented or not. Selected NO");
        } else {
            consentYes_BTN().click();
            ExtentReport.addTestCaseStep("Selected randomly if I consented or not. Selected YES");
        }
        return this;
    }

    public void clickOn_changeEmail_BTN() {
        clickAction(changeEmail_BTN(), "Clicked on the Change e-mail button.");
        assertTrue(sendVerCode_BTN().isDisplayed());
        assertTrue(sendVerCode_BTN().getAttribute("value").isEmpty());
        ExtentReport.addTestCaseStep("Email field is now empty");

    }

    public void tryIncorrectPasswords(WebElement passField) {
        String[] invalidPass = {"passwordd", "Passwordd", "passwordd!!", "ThisIsAReallyReallyLongPassword"};

        for (String pass : invalidPass) {
            clearWebElement(passField);
            setField(passField, pass, "Entered the following password: " + pass);

            if (passField.hashCode() == newPassword_field().hashCode()) {
                checkIfElementHasLoaded(invalidPass_Msg(), "Error message is displayed: " + invalidPass_Msg().getText());
            } else if (passField.hashCode() == confNewPassword_field().hashCode()) {
                checkIfElementHasLoaded(invalidConfirmPass_Msg(), "Error message is displayed: " + invalidPass_Msg().getText());
            } else throw new NoSuchElementException();
        }
    }

    public SignUpPage clickOn_verifyCode_BTN() {
        clickAction(verifyCode_BTN(), "Clicked on Verify Code button");
        return this;
    }

    public void verifyIfPageLoaded() {
        checkIfElementHasLoaded(email_field(), "Navigated to the Login page");
    }

    public SignUpPage clickOn_create_BTN() {
        clickAction(create_BTN(), "Clicked on the Create button");
        return this;
    }

    public void clickOn_cancel_BTN() {
        clickAction(cancel_BTN(), "Clicked on the Cancel button");
    }

    public SignUpPage clickOn_sendNewCode_BTN() {
        clickAction(sendNewCode_BTN(), "Clicked on the Send new code button");
        return this;
    }
}