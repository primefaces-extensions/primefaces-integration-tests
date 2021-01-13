/*
 * Copyright (c) 2011-2021 PrimeFaces Extensions
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.primefaces.extensions.integrationtests.password;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.Password;

public class Password003Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Password: Localization of prompt")
    public void testLocalizationPrompt(Page page) {
        // Arrange
        Password password = page.password;

        // Act
        password.showFeedback();

        // Assert
        WebElement feedback = password.getFeedbackPanel();
        Assertions.assertEquals("block", feedback.getCssValue("display"));
        assertText(feedback, "Lütfen şifre giriniz");
        assertConfiguration(password.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("Password: Localization of Weak password")
    public void testFeedbackWeak(Page page) {
        // Arrange
        Password password = page.password;

        // Act
        password.setValue("SOweak");
        password.showFeedback();

        // Assert
        WebElement feedback = password.getFeedbackPanel();
        assertText(feedback, "Zayıf");
        assertConfiguration(password.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("Password: Localization of Good password")
    public void testFeedbackGood(Page page) {
        // Arrange
        Password password = page.password;

        // Act
        password.setValue("PrettyGood");
        password.showFeedback();

        // Assert
        WebElement feedback = password.getFeedbackPanel();
        assertText(feedback, "Orta seviye");
        assertConfiguration(password.getWidgetConfiguration());
    }

    @Test
    @Order(4)
    @DisplayName("Password: Localization of Strong password")
    public void testFeedbackStrong(Page page) {
        // Arrange
        Password password = page.password;

        // Act
        password.setValue("Very#Strong12345");
        password.showFeedback();

        // Assert
        WebElement feedback = password.getFeedbackPanel();
        assertText(feedback, "Güçlü");
        assertConfiguration(password.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Password Config = " + cfg);
        Assertions.assertTrue(cfg.getBoolean("feedback"));
        Assertions.assertFalse(cfg.getBoolean("inline"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:password")
        Password password;

        @Override
        public String getLocation() {
            return "password/password003.xhtml";
        }
    }
}
