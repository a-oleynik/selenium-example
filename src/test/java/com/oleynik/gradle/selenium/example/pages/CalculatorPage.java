package com.oleynik.gradle.selenium.example.pages;

import com.oleynik.gradle.selenium.example.framework.manager.WebdriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.oleynik.gradle.selenium.example.framework.utils.WebdriverUtils.clickIfElementShown;

public class CalculatorPage {

    public CalculatorPage(String url) {
        super();
        WebDriver driver = WebdriverManager.getDriver();
        driver.get(url);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@onclick='javascript:equals()']")
    private WebElement equalButton;

    @FindBy(xpath = "//input[@onclick='javascript:operator(\"+\")']")
    private WebElement plusButton;

    @FindBy(xpath = "//input[@onclick='javascript:operator(\"-\")']")
    private WebElement minusButton;

    @FindBy(xpath = "//input[@onclick='javascript:operator(\"*\")']")
    private WebElement multiplyButton;

    @FindBy(xpath = "//input[@onclick='javascript:operator(\"/\")']")
    private WebElement divideButton;

    @FindBy(xpath = "(//input[@name=\"tapefld[]\"])[last()]")
    private WebElement resultInput;

    @FindBy(tagName = "textarea")
    private WebElement taperoll;

    private static final By GOT_IT_BUTTON_BY = By.xpath("//*[@class='tibrr-cookie-consent-button']/button");

    private static final By CONSENT_BUTTON_BY = By.xpath("//*[@class='fc-button-label' and contains(text(), 'Consent')]");

    public String getTitle() {
        WebDriver driver = WebdriverManager.getDriver();
        return driver.getTitle();
    }

    public void pressNumber(char number) {
        WebDriver driver = WebdriverManager.getDriver();
        driver.findElement(By.cssSelector("[type=\"button\"][value = \"" + number + "\"]")).click();
    }

    public void pressPlus() {
        plusButton.click();
    }

    public void pressMinus() {
        minusButton.click();
    }

    public void pressMultiply() {
        multiplyButton.click();
    }

    public void pressDivide() {
        divideButton.click();
    }

    public void pressEqual() {
        equalButton.click();
    }

    public String getResultValue() {
        return resultInput.getDomProperty("value");
    }

    public boolean divideButtonShown() {
        return divideButton.isDisplayed();
    }

    public boolean multiplyButtonShown() {
        return multiplyButton.isDisplayed();
    }

    public boolean minusButtonShown() {
        return minusButton.isDisplayed();
    }

    public boolean plusButtonShown() {
        return plusButton.isDisplayed();
    }

    public boolean equalButtonShown() {
        return equalButton.isDisplayed();
    }

    public boolean resultInputShown() {
        return resultInput.isDisplayed();
    }

    public void acceptCookie() {
        clickIfElementShown(GOT_IT_BUTTON_BY);
    }

    public void pressConsent() {
        clickIfElementShown(CONSENT_BUTTON_BY);
    }

}
