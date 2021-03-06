package testCases;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ImageDetailsPage;
import pages.LibraryPage;
import pages.LoginPage;
import pages.SettingsPage;
import reports.ExtentReport;
import utils.TestUtil;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseDesc;

import java.util.List;

import static pages.ImageDetailsPage.getImageDetailsPage;
import static pages.LoginPage.getLoginPage;

public class SmokeTest extends TestBase {
    LoginPage loginPage;
    LibraryPage libraryPage;
    SettingsPage settingsPage;
    ImageDetailsPage imageDetailsPage;

    enum Temperature {DEFAULT, CELSIUS, FAHRENHEIT, KELVIN}

    enum Distance {DEFAULT, METERS, FEET}

    enum Language {DEFAULT, CZECH, DANISH, GERMAN, ENGLISH}

    enum DateFormat {LANGUAGE_DEFAULT_LONG, LANGUAGE_DEFAULT_SHORT, ISO8601_LONG, ISO8601_SHORT}

    @Test
    public void smokeTest1() {

        executeSetup(TestCaseDesc.SMOKETEST_TEST1);

        String temp = String.valueOf(Temperature.FAHRENHEIT);
        String dist = String.valueOf(Distance.FEET);
        String lang = String.valueOf(Language.ENGLISH);
        String date = String.valueOf(DateFormat.ISO8601_LONG);

        libraryPage = loginPage.login(testData.getSmokeTestAccount(), testData.getPassOfExistingAcc());

        settingsPage = libraryPage.goToSettingsPage();
        settingsPage.setTemperature(temp)
                .setDistance(dist)
                .setLanguage(lang)
                .setDate(date);

        TestUtil.refreshPage();

        checkIfValuesAreSaved(temp, dist, lang, date);

        libraryPage.logout();

        goToLoginPage();
        libraryPage = loginPage.login(testData.getSmokeTestAccount(), testData.getPassOfExistingAcc());
        settingsPage = libraryPage.goToSettingsPage();
        checkIfValuesAreSaved(temp, dist, lang, date);

        settingsPage.goToLibraryPage();

        String newFolderName = TestUtil.getRandomString(7);
        libraryPage.createNewFolder(newFolderName)
                .openFolder(newFolderName)
                .uploadFile(System.getProperty("user.dir") + "\\src\\main\\java\\testData\\Images\\measurements.jpg")
                .closeToastMessage()
                .uploadFile(System.getProperty("user.dir") + "\\src\\main\\java\\testData\\Images\\GPS.jpg")
                .closeToastMessage();

        imageDetailsPage = libraryPage.openFile("GPS.jpg");
        imageDetailsPage.clickOn_showAll_BTN();

        checkSettingsInImageDetails(temp, dist, date);

        imageDetailsPage.clickOn_comments_TAB()
                .checkForExistingNotes();

        imageDetailsPage.clickOn_location_TAB()
                .checkForExistingLocation();

        imageDetailsPage.clickOn_zoomIn_BTN()
                .clickOn_zoomIn_BTN()
                .clickOn_zoomOut_BTN()
                .clickOn_zoomToFit_BTN()
                .clickOn_toggleDC_BTN()
                .clickOn_toggleIR_BTN()
                .clickOn_enterFullscreen_BTN()
                .clickOn_exitFullscreen_BTN();

        imageDetailsPage.clickOn_nextImage_BTN()
                .clickOn_prevImage_BTN();

        imageDetailsPage.clickOn_backToLibrary_BTN();

        libraryPage.goToMainFolder()
                .clickOn_gridView_BTN()
                .clickOn_listView_BTN()
                .deleteFolder(newFolderName)
                .logout();
    }

    //-----------
    private void goToLoginPage() {
        landingPage.verifyIfPageLoaded()
                .clickOn_loginBTN();
        loginPage.verifyIfPageLoaded();
    }

    private void executeSetup(TestCaseDesc testCaseDesc) {
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("************************Begin test " + parentMethodName + "*********************************");
        String testCaseDescription = String.valueOf(testCaseDesc.description);
        String testCaseCategory = String.valueOf(TestCaseCategory.SMOKETEST);
        createTestCase(parentMethodName, testCaseDescription, testCaseCategory);

        loginPage = getLoginPage();
        goToLoginPage();
        log.info("************************End test " + parentMethodName + "*********************************");
    }

    private String getSelectedDate() {
        String selectedDate;
        Select select = new Select(settingsPage.dateFormat_dropdown());
        WebElement currElement = select.getFirstSelectedOption();

        if (currElement.getText().contains("Language Default"))
            selectedDate = DateFormat.LANGUAGE_DEFAULT_LONG.toString();
        else if (currElement.getText().contains("1970")) {
            if (currElement.getText().contains("13"))
                selectedDate = DateFormat.ISO8601_LONG.toString();
            else selectedDate = DateFormat.ISO8601_SHORT.toString();
        } else selectedDate = DateFormat.LANGUAGE_DEFAULT_SHORT.toString();
        return selectedDate;
    }

    private void checkIfValuesAreSaved(String temp, String dist, String lang, String date) {
        String selectedTemp = settingsPage.getSelectedElementFromDropdown(settingsPage.temperatureUnits_dropdown()).toUpperCase();
        String selectedDist = settingsPage.getSelectedElementFromDropdown(settingsPage.distanceUnits_dropdown()).toUpperCase();
        String selectedLang = settingsPage.getSelectedElementFromDropdown(settingsPage.language_dropdown()).toUpperCase();
        String selectedDate = getSelectedDate().toUpperCase();

        Assert.assertTrue(selectedTemp.contains(temp));
        ExtentReport.addTestCaseStep("Temperature settings remained the same after page was refreshed");

        Assert.assertTrue(selectedDist.contains(dist));
        ExtentReport.addTestCaseStep("Distance settings remained the same after page was refreshed");

        Assert.assertTrue(selectedLang.contains(lang));
        ExtentReport.addTestCaseStep("Language settings remained the same after page was refreshed");

        Assert.assertTrue(selectedDate.contains(date));
        ExtentReport.addTestCaseStep("Date settings remained the same after page was refreshed");
    }

    private void checkSettingsInImageDetails(String temp, String dist, String date) {
        ImageDetailsPage imageDetailsPage = getImageDetailsPage();
        if (imageDetailsPage.temperatures().isDisplayed()) {
            List<WebElement> temperatures = driver.findElements(By.xpath("//span[@class='flir-unit' and contains(.,'°')]"));
            for (int i = 0, temperaturesSize = temperatures.size(); i < temperaturesSize; i++) {
                WebElement temperature = temperatures.get(i);
                System.out.println(temperature.getText());
                String tempUnit = temperature.getText();
                tempUnit = tempUnit.substring(tempUnit.lastIndexOf("°") + 1);
                Assert.assertTrue(temp.contains(tempUnit));
            }
            ExtentReport.addTestCaseStep("All of the temperature units are the ones set in Settings: " + temp);
        }

        TestUtil.waitForElementToLoad(imageDetailsPage.parameters_distance());
        String distanceValue = imageDetailsPage.parameters_distance().getText();

        String distUnit = distanceValue.substring(distanceValue.length() - 2, distanceValue.length() - 1).toUpperCase();
        Assert.assertTrue(dist.contains(distUnit));
        ExtentReport.addTestCaseStep("All of the distance units are the ones set in Settings: " + dist);

        imageDetailsPage.clickOn_details_TAB();
        checkDateSettings(imageDetailsPage.created(), date);
        checkDateSettings(imageDetailsPage.uploaded(), date);
    }

    private void checkDateSettings(WebElement webElement, String savedDateFromSettings) {
        //this will work only for English(US) !!!
        String format;
        String date = webElement.getText();
        long count = date.chars().filter(ch -> ch == ',').count();

        if (count == 2)
            format = String.valueOf(DateFormat.LANGUAGE_DEFAULT_LONG);
        else if (date.contains("AM") || date.contains("PM"))
            format = String.valueOf(DateFormat.LANGUAGE_DEFAULT_SHORT);
        else if (date.contains(" "))
            format = String.valueOf(DateFormat.ISO8601_LONG);
        else format = String.valueOf(DateFormat.ISO8601_SHORT);

        Assert.assertEquals(format, savedDateFromSettings);
        ExtentReport.addTestCaseStep("Date displayed in has the same format as the one saved in Settings");
    }
}
