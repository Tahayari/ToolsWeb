package pages;

import base.WebPageBase;
import org.openqa.selenium.WebElement;
import pages.elements.ElementManager;
import utils.TestUtil;

public class LandingPage extends WebPageBase {
//    private WebDriver driver;

    //Constructor
    private LandingPage() {
//        driver = factory.getDriver();
    }

    public static LandingPage getLandingPage() {
        return new LandingPage();
    }
    //-----------

    //-----------GETTERS
    public WebElement signIn_BTN() {
        return getWebElement(ElementManager.LANDINGPAGE_SIGNIN_BTN);
    }

    public WebElement signUp_BTN() {
        return getWebElement(ElementManager.LANDINGPAGE_LOGIN_BTN);
    }
    //-----------

    //-----------Actions
    public void clickOn_loginBTN() {
        TestUtil.waitForElementToBeClickable(signIn_BTN());
        clickAction(signIn_BTN(), "Clicked on Login Button");
    }

    public void clickOn_signUpBTN() {
        TestUtil.waitForElementToBeClickable(signUp_BTN());
        clickAction(signUp_BTN(), "Clicked on SignUp Button");
    }

    public LandingPage verifyIfPageLoaded() {
        checkIfElementHasLoaded(signIn_BTN(), "Navigated to the Landing page");
        return this;
    }
    //-----------
}
