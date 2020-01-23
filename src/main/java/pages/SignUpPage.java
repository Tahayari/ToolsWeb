package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage extends TestBase {
    //-------PATHS-------
    private final String email_ID = "email";
    private final String verificationCodeField_ID = "email_ver_input";
    private final String sendVerificationCodeBTN_ID = "email_ver_but_send";
    private final String verifyCodeBTN_ID = "email_ver_but_verify";
    private final String changeEmailBTN_ID = "email_ver_but_edit";
    private final String sendNewCodeBTN_ID = "email_ver_but_resend";
    private final String newPasswordField_ID = "newPassword";
    private final String confirmNewPasswordField_ID = "reenterPassword";
    private final String firstNameField_ID = "givenName";
    private final String lastNameField_ID = "surname";
    private final String countrySelector_ID = "country";
    private final String consentYes_ID = "extension_Consent_1";
    private final String consentNo_ID = "extension_Consent_2";
    private final String createBTN_ID = "continue";
    private final String cancelBTN_ID = "cancel";
    private final String incorrectVerificationCode_ID = "email_fail_retry";
    private final String expiredVerificationCode_ID = "email_fail_code_expired";
    private final String invalidEmail_XPATH = "//div[@id='attributeList']//*[contains(text(),'Please enter a valid email address.')]";
    //--------------


    //-------Locators-------
    @FindBy(id = email_ID)
    private WebElement email_field;
    @FindBy(id = verificationCodeField_ID)
    private WebElement verificationCode_field;
    @FindBy(id = sendVerificationCodeBTN_ID)
    private WebElement sendVerCode_BTN;
    @FindBy(id = verifyCodeBTN_ID)
    private WebElement verifyCode_BTN;
    @FindBy(id = changeEmailBTN_ID)
    private WebElement changeEmail_BTN;
    @FindBy(id = sendNewCodeBTN_ID)
    private WebElement sendNewCode_BTN;
    @FindBy(id = newPasswordField_ID)
    private WebElement newPassword_field;
    @FindBy(id = confirmNewPasswordField_ID)
    private WebElement confNewPassword_field;
    @FindBy(id = firstNameField_ID)
    private WebElement firstName_field;
    @FindBy(id = lastNameField_ID)
    private WebElement lastName_field;
    @FindBy(id = countrySelector_ID)
    private WebElement country_dropdown;
    @FindBy(id = consentYes_ID)
    private WebElement consentYes;
    @FindBy(id = consentNo_ID)
    private WebElement consentNo;
    @FindBy(id = createBTN_ID)
    @CacheLookup
    private WebElement create_BTN;
    @FindBy(id = cancelBTN_ID)
    private WebElement cancel_BTN;
    @FindBy(id = incorrectVerificationCode_ID)
    private WebElement incorrectVerCode;
    @FindBy(id = expiredVerificationCode_ID)
    private WebElement expiredVerCode;
    @FindBy(xpath = invalidEmail_XPATH)
    private WebElement invalidEmailMsg;
    //--------------

    //Constructor
    SignUpPage() {
        PageFactory.initElements(driver, this);
    }
    //-----------


    //-----------GETTERS
    public WebElement getEmail_field() {
        return email_field;
    }

    public WebElement getVerificationCode_field() {
        return verificationCode_field;
    }

    public WebElement getSendVerCode_BTN() {
        return sendVerCode_BTN;
    }

    public WebElement getVerifyCode_BTN() {
        return verifyCode_BTN;
    }

    public WebElement getChangeEmail_BTN() {
        return changeEmail_BTN;
    }

    public WebElement getSendNewCode_BTN() {
        return sendNewCode_BTN;
    }

    public WebElement getCreate_BTN() {
        return create_BTN;
    }

    public WebElement getCountry_dropdown() {
        return country_dropdown;
    }

    public WebElement getConsentNo() {
        return consentNo;
    }

    public WebElement getConsentYes() {
        return consentYes;
    }

    public WebElement getCancel_BTN() {
        return cancel_BTN;
    }

    public String getIncorrectVerCode() { return incorrectVerCode.getText(); }

    public WebElement getIncorrectVerCodeWebElement() { return incorrectVerCode; }

    public String getExpiredVerCode() {
        return expiredVerCode.getText();
    }

    public WebElement getExpiredVerCodeWebelement() { return expiredVerCode;    }

    public String getInvalidEmailMsg() {
        String text = invalidEmailMsg.getText();
        return text;
    }

    public WebElement getInvalidEmailWebelement() {
        return invalidEmailMsg;
    }

    public WebElement getNewPassword_field(){ return newPassword_field;}

    public WebElement getConfNewPassword_field() {return newPassword_field;}
    //-----------

    //-----------SETTERS
    public void setEmailAddress(String text) {
        email_field.sendKeys(text);
    }

    public void setVerificationCode_field(String text) {
        verificationCode_field.sendKeys(text);
    }

    public void setNewPassword(String text) {
        newPassword_field.sendKeys(text);
    }

    public void setConfirmNewPassword(String text) {
        confNewPassword_field.sendKeys(text);
    }

    public void setFirstName(String text) {
        firstName_field.sendKeys(text);
    }

    public void setLastName(String text) {
        lastName_field.sendKeys(text);
    }
    //-----------

    //Actions

    public String getPageTitle() {
        return driver.getTitle();
    }

    public LibraryPage createButton_click() {
        create_BTN.click();
        return new LibraryPage();
    }

}