package testCases.recoverPassPage;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LibraryPage;
import pages.LoginPage;
import pages.RecoverPasswordPage;
import utils.TestUtil;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseDesc;

import static pages.LibraryPage.getLibraryPage;
import static pages.LoginPage.getLoginPage;
import static pages.RecoverPasswordPage.getRecoverPasswordPage;
import static utils.TestUtil.getDataFromExcel;

public class RecoverPassPageNegativeTests extends TestBase {
    LoginPage loginPage;
    RecoverPasswordPage recoverPasswordPage;

    @Test
    public void invalidEmail_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_INVALIDEMAIL);

        verifyListOfInvalidEmails();
    }


    @Test
    public void invalidToken_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_INVALIDTOKEN);

        recoverPasswordPage.sendTokenToEmail(testData.getRandomEmail())
                .setVerificationCode(testData.getInvalidToken())
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.incorrectVerCode_Msg());
    }

    @Test()
    public void expiredToken_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_EXPIREDTOKEN);

        recoverPasswordPage.sendTokenToEmail(testData.getGmailEmail());
        String token = TestUtil.getTokenFromGmail();
        TestUtil.waitForSomeMinutes(testData.getMinForTokenToExpire());
        recoverPasswordPage.setVerificationCode(token)
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.expiredVerCode_Msg());
    }

    @Test
    public void tooManyIncorrectAttemptsToken_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_TOOMANYINCORRECTTOKENATTEMPTS);
        verifyInvalidTokenTooManyTimes();
    }

    @Test
    public void sendNewCode_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_SENDNEWCODE);

        recoverPasswordPage.sendTokenToEmail(testData.getGmailEmail());
        String firstToken = TestUtil.getTokenFromGmail();
        recoverPasswordPage.sendTokenToEmail(testData.getGmailEmail());
        String secondToken = TestUtil.getTokenFromGmail();

        recoverPasswordPage.setVerificationCode(firstToken)
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.incorrectVerCode_Msg());

        recoverPasswordPage.setVerificationCode(secondToken)
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
    }

    @Test
    public void resendToken_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_RESENDTOKEN);

        recoverPasswordPage.sendTokenToEmail(testData.getGmailEmail());
        String firstToken = TestUtil.getTokenFromGmail();
        recoverPasswordPage.clickOn_sendNewCode_BTN();
        String secondToken = TestUtil.getTokenFromGmail();
        recoverPasswordPage.setVerificationCode(firstToken)
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.incorrectVerCode_Msg());

        recoverPasswordPage.setVerificationCode(secondToken)
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
    }

    @Test
    public void changeEmail_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_CHANGEEMAIL);

        verifyEmail();
        recoverPasswordPage.clickOn_changeEmail_BTN();
    }

    @Test
    public void cancelRecoverPass_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_CANCELRECOVERPASS);

        verifyEmail();
        recoverPasswordPage.clickOn_cancel_BTN();
        landingPage.verifyIfPageLoaded();
    }


    @Test
    public void incorrectPasswordFormat_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_INCORECTPASSFORMAT);

        goToChangePassScreen();

        recoverPasswordPage.tryIncorrectPasswords(recoverPasswordPage.newPassword_field());
    }

    @Test
    public void incorrectPasswordFormatConfirmPassField_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_INCORECTCONFIRMPASSFORMAT);

        goToChangePassScreen();

        recoverPasswordPage.tryIncorrectPasswords(recoverPasswordPage.confNewPassword_field());
    }

    @Test
    public void blankPass_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_BLANKPASS);

        goToChangePassScreen();

        recoverPasswordPage.setNewPassword("")
                .clickOn_continue_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.requiredFieldMissing_Msg());
    }

    @Test
    public void mismatchingPasswords_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_MISSMATCHINGPASS);

        goToChangePassScreen();

        recoverPasswordPage.setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword("")
                .clickOn_continue_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.passMismatch_Msg());
    }

    @Test
    public void cancelUpdatingPassword_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_CANCELUPDATINGPASSWORD);

        goToChangePassScreen();
        String newPass = testData.getPassOfExistingAcc() + "123";
        recoverPasswordPage.setNewPassword(newPass)
                .setConfirmNewPassword(newPass)
                .clickOn_cancel_BTN();
        landingPage.verifyIfPageLoaded();
        landingPage.clickOn_loginBTN();
        loginPage.setEmail(testData.getGmailEmail())
                .setPass(testData.getPassOfExistingAcc())
                .clickOn_signInBTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.verifyIfPageLoaded();
        libraryPage.logout();
        landingPage.verifyIfPageLoaded();
    }


    //---
    private void goToRecoverPassPage() {
        landingPage.verifyIfPageLoaded();
        landingPage.clickOn_loginBTN();
        loginPage.clickOn_forgotPasswordLink();
        recoverPasswordPage.verifyIfPageLoaded();
    }

    private void executeSetup(TestCaseDesc testCaseDesc) {
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("************************Begin test " + parentMethodName +"*********************************");
        String testCaseDescription = String.valueOf(testCaseDesc.description);
        String testCaseCategory = String.valueOf(TestCaseCategory.RECOVERPASSWORD_PAGE);
        createTestCase(parentMethodName,testCaseDescription,testCaseCategory);

        loginPage = getLoginPage();
        recoverPasswordPage = getRecoverPasswordPage();
        goToRecoverPassPage();
    }

    private void goToChangePassScreen() {
        verifyEmail();
        recoverPasswordPage.clickOn_continue_BTN()
                .verifyIfChangePassScreenLoaded();
    }

    private void verifyListOfInvalidEmails() {
        Object[][] invalidEmailsList = getDataFromExcel(testData.getNameOfInvalidEmailsFile(), testData.getNameOfFirstSheet());
        for (int i = 1; i < invalidEmailsList.length; i++) {
            recoverPasswordPage.clearField(recoverPasswordPage.emailAddress_field())
                    .setEmail(invalidEmailsList[i][0].toString())
                    .checkErrMsgIsDisplayed(recoverPasswordPage.invalidEmail_Msg());
        }
    }

    private void verifyInvalidTokenTooManyTimes() {
        int timesToRetry = 2;
        recoverPasswordPage.sendTokenToEmail(testData.getRandomEmail());
        for (int i = 0; i < timesToRetry; i++) {
            recoverPasswordPage.setVerificationCode(String.valueOf(i + 10000))
                    .clickOn_verifyCode_BTN()
                    .checkErrMsgIsDisplayed(recoverPasswordPage.incorrectVerCode_Msg());
        }
        recoverPasswordPage.setVerificationCode("10002")
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.tooManyAttempts_Msg());
    }

    private void verifyEmail() {
        recoverPasswordPage.sendTokenToEmail(testData.getGmailEmail())
                .setVerificationCode(TestUtil.getTokenFromGmail())
                .clickOn_verifyCode_BTN();
    }

}
