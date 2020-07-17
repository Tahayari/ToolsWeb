package testCases;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.LoginPage;
import utils.ExtentReport;
import utils.TestUtil;

import java.io.File;

import static pages.LandingPage.getLandingPage;
import static pages.LoginPage.getLoginPage;

public class LibraryPageTest extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;
    LibraryPage libraryPage;

    // Test cases begin here------------------------------------------------------------
    @Test
    public void testing() {
        executeSetup("title", "description");
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getValidAccountPasswd());

        File folder = new File(System.getProperty("user.dir") + "\\src\\main\\java\\testData\\Images\\");
        File[] listOfFiles = folder.listFiles();
//        libraryPage.createNewFolder("All file types");
//        create new folder THEN move to new folder
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                log.info("Uploading file: " + listOfFiles[i].getName());
                System.out.println(listOfFiles[i].getPath());
                libraryPage.uploadFile(listOfFiles[i].getPath());
            }
        }

        libraryPage.logout();
    }


    @Test
    public void createNewFolderWithBlankName_Test() {
        executeSetup(testCasesInfo.libraryPageInfo().getCreateNewFolderWithBlankName_Test_title(),
                testCasesInfo.libraryPageInfo().getCreateNewFolderWithBlankName_Test_desc());

        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getValidAccountPasswd());

        libraryPage.clickOn_createNewFolder_BTN()
                .setFolderName("test")
                .clearField(libraryPage.folderName_field())
                .checkErrMsgIsDisplayed(libraryPage.folderNameError_Msg());

        libraryPage.clickOn_newFolderCancel_BTN()
                .logout();
    }

    @Test
    public void createNewFolderWithReallyLongName_Test() {
        executeSetup(testCasesInfo.libraryPageInfo().getCreateNewFolderWithReallyLongName_Test_title(),
                testCasesInfo.libraryPageInfo().getCreateNewFolderWithReallyLongName_Test_desc());
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        libraryPage.clickOn_createNewFolder_BTN()
                .setFolderName(testData.getLongName())
                .checkErrMsgIsDisplayed(libraryPage.folderNameError_Msg());

        libraryPage.clickOn_newFolderCancel_BTN()
                .logout();
    }

    @Test
    public void createFolderWithIllegalCharacters_Test() {
        executeSetup(testCasesInfo.libraryPageInfo().getCreateFolderWithIllegalCharacters_Test_title(),
                testCasesInfo.libraryPageInfo().getCreateFolderWithIllegalCharacters_Test_desc());
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        libraryPage.clickOn_createNewFolder_BTN();
        String[] invalidCharacters = {":", "/", "\\", "\"", "?", "<", ">", "|", "*"};

        for (String invalidCharacter : invalidCharacters) {
            libraryPage.setFolderName(TestUtil.getRandomString(5) + invalidCharacter)
                    .checkErrMsgIsDisplayed(libraryPage.folderNameError_Msg());
            libraryPage.clearField(libraryPage.folderName_field());
        }
        libraryPage.clickOn_newFolderCancel_BTN()
                .logout();
    }

    @Test
    public void createNewFolder_Test() {
        executeSetup(testCasesInfo.libraryPageInfo().getCreateNewFolder_Test_title(),
                testCasesInfo.libraryPageInfo().getCreateNewFolder_Test_desc());
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        libraryPage.createNewFolder(TestUtil.getRandomString(7));
    }
//
//    @Test
//    public void uploadValidFile_Test() {
//        String testCaseTitle = "LIBRARY PAGE - uploadValidFile_Test";
//        String testCaseDescription = "Successfully upload a valid file";
//        String fileLocation = System.getProperty("user.dir") + "\\src\\main\\java\\testData\\Images\\validImage.jpg";
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToLibraryPage();
//
//        libraryPage.uploadFile(fileLocation);
//    }

    //----

    private void executeSetup(String testCaseTitle, String testCaseDescription) {
        log.info("----Begin to test " + testCaseTitle + "----");
        landingPage = getLandingPage();
        loginPage = getLoginPage();
        ExtentReport.createTestCase(testCaseTitle, testCaseDescription);
        ExtentReport.assignCategory(testCasesInfo.libraryPageInfo().getCategory());
        goToLoginPage();
    }

    private void goToLoginPage() {
        landingPage.verifyIfPageLoaded()
                .clickOn_loginBTN();
        loginPage.verifyIfPageLoaded();
    }

}
