package com.oleynik.gradle.selenium.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.oleynik.gradle.selenium.example.framework.manager.WebdriverManager.getDriver;
import static com.oleynik.gradle.selenium.example.framework.utils.WebdriverUtils.*;

public class CalculatorPage {

    public CalculatorPage(String url) {
        super();
        WebDriver driver = getDriver();
        driver.get(url);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@aria-label='=']")
    private WebElement equalButton;

    @FindBy(xpath = "//button[@aria-label='+']")
    private WebElement plusButton;

    @FindBy(xpath = "//button[@aria-label='-']")
    private WebElement minusButton;

    @FindBy(xpath = "//button[@aria-label='×']")
    private WebElement multiplyButton;

    @FindBy(xpath = "//button[@aria-label='÷']")
    private WebElement divideButton;

    @FindBy(xpath = "(//input[@name=\"tapefld[]\"])[last()]")
    private WebElement resultInput;

    @FindBy(tagName = "textarea")
    private WebElement taperoll;

    private static final By GOT_IT_BUTTON_BY = By.xpath("//*[@class='tibrr-cookie-consent-button']/button");

    private static final By CONSENT_BUTTON_BY = By.xpath("//*[@class='fc-button-label' and contains(text(), 'Consent')]");

    public String getTitle() {
        WebDriver driver = getDriver();
        return driver.getTitle();
    }

    public void pressNumber(char number) {
        WebDriver driver = getDriver();
        //WebElement button = driver.findElement(By.xpath("//button[@aria-label='" + number + "']"));
        elementExistsScrollAndVisible(By.xpath("//button[@aria-label='" + number + "']"))
                .click();
    }

    public void pressPlus() {
        scrollToElementCenter(plusButton);
        plusButton.click();
    }

    public void pressMinus() {
        scrollToElementCenter(minusButton);
        minusButton.click();
    }

    public void pressMultiply() {
        scrollToElementCenter(multiplyButton);
        multiplyButton.click();
    }

    public void pressDivide() {
        scrollToElementCenter(divideButton);
        divideButton.click();
    }

    public void pressEqual() {
        scrollToElementCenter(equalButton);
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

    public void removeAds() {
        ((JavascriptExecutor) getDriver()).executeScript("document.querySelectorAll(\"ins.adsbygoogle\").forEach(el => el.remove());");
    }

    public void pressConsent() {
        clickIfElementShown(CONSENT_BUTTON_BY);
    }

}
