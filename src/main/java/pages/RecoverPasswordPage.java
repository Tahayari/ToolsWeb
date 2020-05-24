package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ExtentReport;
import utils.TestUtil;

import java.util.NoSuchElementException;

import static org.testng.Assert.assertTrue;
import static utils.DriverFactory.getDriver;

public class RecoverPasswordPage {

    //-------PATHS-------

    //---Input fields
    private final String emailAddressField_ID = "email";
    private final String verificationCodeField_ID = "email_ver_input";
    private final String newPassword_ID = "newPassword";
    private final String reenterPassword_ID = "reenterPassword";

    //---Buttons
    private final String sendVerificationCodeBTN_ID = "email_ver_but_send";
    private final String verifyCodeBTN_ID = "email_ver_but_verify";
    private final String sendNewCodeBTN_ID = "email_ver_but_resend";
    private final String changeEmailBTN_ID = "email_ver_but_edit";
    private final String continueBTN_ID = "continue";
    private final String cancelBTN_ID = "cancel";

    //---Errors
    private final String incorrectVerificationCode_ID = "email_fail_retry";
    private final String expiredVerificationCode_ID = "email_fail_code_expired";
    private final String invalidEmailMsg_XPATH = "//p[contains(text(),'Please enter a valid email address.')]";
    private final String tooManyIncorrectAttemptsError_ID = "email_fail_no_retry";
    private final String requiredFieldMissing_ID = "requiredFieldMissing";
    private final String invalidPassError_XPATH = "//input[@id='newPassword']//preceding::p[contains(text(),'8-16 characters')]";
    private final String invalidConfirmPassError_XPATH = "//input[@id='newPassword']//following::p[contains(text(),'8-16 characters')]";
    private final String passwordEntryMismatch_ID = "passwordEntryMismatch";
    //--------------

    //-------Locators-------

    //---Input fields
    @FindBy(id = emailAddressField_ID)
    private WebElement emailAddress_field;
    @FindBy(id = verificationCodeField_ID)
    private WebElement verificationCode_field;
    @FindBy(id = newPassword_ID)
    private WebElement newPassword_field;
    @FindBy(id = reenterPassword_ID)
    private WebElement confNewPassword_field;

    //---Buttons
    @FindBy(id = sendVerificationCodeBTN_ID)
    private WebElement sendVerCode_BTN;
    @FindBy(id = verifyCodeBTN_ID)
    private WebElement verifyCode_BTN;
    @FindBy(id = sendNewCodeBTN_ID)
    private WebElement sendNewCode_BTN;
    @FindBy(id = changeEmailBTN_ID)
    private WebElement changeEmail_BTN;
    @FindBy(id = continueBTN_ID)
    private WebElement continue_BTN;
    @FindBy(id = cancelBTN_ID)
    private WebElement cancel_BTN;

    //---Errors
    @FindBy(id = incorrectVerificationCode_ID)
    private WebElement incorrectVerCode_Msg;
    @FindBy(xpath = invalidEmailMsg_XPATH)
    private WebElement invalidEmail_Msg;
    @FindBy(id = expiredVerificationCode_ID)
    private WebElement expiredVerCode_Msg;
    @FindBy(id = tooManyIncorrectAttemptsError_ID)
    private WebElement tooManyAttempts_Msg;
    @FindBy(id = requiredFieldMissing_ID)
    private WebElement requiredFieldMissing_Msg;
    @FindBy(xpath = invalidPassError_XPATH)
    private WebElement invalidPass_Msg;
    @FindBy(xpath = invalidConfirmPassError_XPATH)
    private WebElement invalidConfirmPass_Msg;
    @FindBy(id = passwordEntryMismatch_ID)
    private WebElement passMismatch_Msg;
    //--------------

    //Constructor
    private RecoverPasswordPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public static RecoverPasswordPage getRecoverPasswordPage() {
        return new RecoverPasswordPage();
    }
    //-----------

    //-----------GETTERS
    public WebElement emailAddress_field() {
        return emailAddress_field;
    }

    public WebElement sendVerCode_BTN() {
        return sendVerCode_BTN;
    }

//    public WebElement verificationCode_field() {
//        return verificationCode_field;
//    }

//    public WebElement verifyCode_BTN() {
//        return verifyCode_BTN;
//    }

//    public WebElement sendNewCode_BTN() {
//        return sendNewCode_BTN;
//    }

    public WebElement changeEmail_BTN() {
        return changeEmail_BTN;
    }

//    public WebElement cancel_BTN() {
//        return cancel_BTN;
//    }

    public WebElement incorrectVerCode_Msg() {
        return incorrectVerCode_Msg;
    }

    public WebElement invalidEmail_Msg() {
        return invalidEmail_Msg;
    }

    public WebElement expiredVerCode_Msg() {
        return expiredVerCode_Msg;
    }

    public WebElement tooManyAttempts_Msg() {
        return tooManyAttempts_Msg;
    }

    public WebElement newPassword_field() {
        return newPassword_field;
    }

    public WebElement confNewPassword_field() {
        return confNewPassword_field;
    }

    public WebElement requiredFieldMissing_Msg() {return requiredFieldMissing_Msg;}

    public WebElement passMismatch_Msg(){return passMismatch_Msg;}

    //--------------

    //-----------SETTERS
    public RecoverPasswordPage setVerificationCode_field(String code) {
        TestUtil.waitForElementToLoad(verificationCode_field);
        verificationCode_field.clear();
        verificationCode_field.sendKeys(code);
        ExtentReport.addTestCaseStep("Entered the following Verification Code: " + code);
        return this;
    }

    public RecoverPasswordPage setEmail(String email) {
        TestUtil.waitForElementToLoad(emailAddress_field);
        emailAddress_field.clear();
        emailAddress_field.sendKeys(email);
        ExtentReport.addTestCaseStep("Entered the following email: " + email);
        return this;
    }

    public RecoverPasswordPage setNewPassword(String password) {
        TestUtil.waitForElementToLoad(newPassword_field);
        newPassword_field.clear();
        newPassword_field.sendKeys(password);
        ExtentReport.addTestCaseStep("Entered the following password: " + password);
        return this;
    }

    public RecoverPasswordPage setConfirmNewPassword(String confirmNewPassword) {
        TestUtil.waitForElementToLoad(confNewPassword_field);
        confNewPassword_field.clear();
        confNewPassword_field.sendKeys(confirmNewPassword);
        ExtentReport.addTestCaseStep("Entered the following password in the Confirm password field: " + confirmNewPassword);
        return this;
    }
    //--------------

    //Actions
    public RecoverPasswordPage clearField(WebElement webElement) {
        webElement.clear();
        return this;
    }

    public RecoverPasswordPage sendTokenToEmail(String email) {
        setEmail(email)
                .clickOn_sendVerificationCode_BTN();
        return this;
    }

    public void clickOn_sendVerificationCode_BTN() {
        TestUtil.waitForElementToLoad(sendVerCode_BTN);
        sendVerCode_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on Send Verification Code button");
    }

    public void clickOn_changeEmail_BTN() {
        changeEmail_BTN().click();
        assertTrue(sendVerCode_BTN().isDisplayed());
        assertTrue(sendVerCode_BTN().getAttribute("value").isEmpty());
        ExtentReport.addTestCaseStep("Clicked on the Change e-mail button. Email field is now empty");
    }

    public void clickOn_cancel_BTN(){
        TestUtil.waitForElementToLoad(cancel_BTN);
        cancel_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on the Cancel button");
    }

    public void clickOn_sendNewCode_BTN(){
        TestUtil.waitForElementToLoad(sendNewCode_BTN);
        sendNewCode_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on the Send new code button");
    }

    public RecoverPasswordPage clickOn_verifyCode_BTN() {
        TestUtil.waitForElementToLoad(verifyCode_BTN);
        verifyCode_BTN.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExtentReport.addTestCaseStep("Clicked on Verify Code button");
        return this;
    }

    public RecoverPasswordPage clickOn_continue_BTN(){
        TestUtil.waitForElementToLoad(continue_BTN);
        continue_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on the Continue button");
        return this;
    }

    public void verifyIfPageLoaded() {
        TestUtil.waitForElementToLoad(emailAddress_field);
        ExtentReport.addTestCaseStep("Navigated to the Recover Password page");
    }

    public void verifyIfChangePassScreenLoaded() {
        TestUtil.waitForElementToLoad(newPassword_field);
        ExtentReport.addTestCaseStep("Navigated to the Set new Password page");
    }

    public void checkErrMsgIsDisplayed(WebElement error_Msg) {
        TestUtil.waitForElementToLoad(error_Msg);
        ExtentReport.addTestCaseStep("Error message is displayed: " + error_Msg.getText());
    }

    public void tryIncorrectPasswords(WebElement passField) {
        String[] invalidPass = {"passwordd", "Passwordd", "passwordd!!", "ThisIsAReallyReallyLongPassword1!"};

        for (String pass : invalidPass) {
            passField.clear();
            passField.sendKeys(pass);
            ExtentReport.addTestCaseStep("Entered the following password: " + pass);

            if (passField == newPassword_field) {
                TestUtil.waitForElementToLoad(invalidPass_Msg);
                ExtentReport.addTestCaseStep("Error message is displayed: " + invalidPass_Msg.getText());
            } else if (passField == confNewPassword_field) {
                TestUtil.waitForElementToLoad(invalidConfirmPass_Msg);
                ExtentReport.addTestCaseStep("Error message is displayed: " + invalidConfirmPass_Msg.getText());
            } else throw new NoSuchElementException();
        }
    }
//--------------
}