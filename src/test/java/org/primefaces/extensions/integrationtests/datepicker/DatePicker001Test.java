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
package org.primefaces.extensions.integrationtests.datepicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.integrationtests.general.utilities.TestUtils;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DatePicker;

public class DatePicker001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("DatePicker: set date and basic panel validation")
    public void testBasic(Page page) {
        // Arrange
        DatePicker datePicker = page.datePicker;
        Assertions.assertEquals(LocalDate.now(), datePicker.getValue().toLocalDate());
        LocalDate value = LocalDate.of(1978, 2, 19);

        // Act
        datePicker.setValue(value);
        datePicker.showPanel(); // focus to bring up panel

        // Assert Panel
        WebElement panel = datePicker.getPanel();
        Assertions.assertNotNull(panel);
        String text = panel.getText();
        System.out.println(text);
        Assertions.assertTrue(text.contains("1978"));
        Assertions.assertTrue(text.contains("February"));

        // Assert Submit Value
        page.button.click();
        LocalDate newValue = datePicker.getValueAsLocalDate();
        Assertions.assertEquals(value, newValue);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        assertConfiguration(datePicker.getWidgetConfiguration(), newValue.format(dateTimeFormatter));
    }

    @Test
    @Order(2)
    @DisplayName("DatePicker: select date via click on day")
    public void testSelectDate(Page page) {
        // Arrange
        TestUtils.pause(1000);
        DatePicker datePicker = page.datePicker;
        LocalDate value = LocalDate.of(1978, 2, 19);

        // Act
        datePicker.setValue(value);
        datePicker.showPanel(); // focus to bring up panel
        WebElement panel = datePicker.getPanel();
        panel.findElement(By.className("ui-datepicker-next")).click();
        panel.findElement(By.linkText("25")).click();

        // Assert selected value
        LocalDate expectedDate = LocalDate.of(1978, 3, 25);
        Assertions.assertEquals(expectedDate, datePicker.getValueAsLocalDate());

        // Assert Submit Value
        page.button.click();
        LocalDate newValue = datePicker.getValueAsLocalDate();
        Assertions.assertEquals(expectedDate, newValue);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        assertConfiguration(datePicker.getWidgetConfiguration(), newValue.format(dateTimeFormatter));
    }

    @Test
    @Order(3)
    @DisplayName("DatePicker: highlight today and selected")
    public void testHighlight(Page page) {
        // Arrange
        TestUtils.pause(1000);
        DatePicker datePicker = page.datePicker;

        // Act
        LocalDate selectedDate = LocalDate.now();
        if (selectedDate.getDayOfMonth() == 1) {
            selectedDate = selectedDate.plusMonths(1).minusDays(1);
        }
        else {
            selectedDate = selectedDate.minusDays(1);
        }
        datePicker.setValue(selectedDate);
        datePicker.click(); // focus to bring up panel

        //Assert panel
        String currentDayOfMonth = ((Integer) LocalDate.now().getDayOfMonth()).toString();
        String selectedDayOfMonth = ((Integer) selectedDate.getDayOfMonth()).toString();
        Assertions.assertTrue(PrimeSelenium.hasCssClass(datePicker.getPanel().findElement(By.linkText(selectedDayOfMonth)), "ui-state-active"));
        Assertions.assertTrue(PrimeSelenium.hasCssClass(datePicker.getPanel().findElement(By.linkText(currentDayOfMonth)), "ui-state-highlight"));
    }

    private void assertConfiguration(JSONObject cfg, String defaultDate) {
        assertNoJavascriptErrors();
        System.out.println("DatePicker Config = " + cfg);
        Assertions.assertEquals("mm/dd/yy", cfg.getString("dateFormat"));
        Assertions.assertEquals(defaultDate, cfg.getString("defaultDate"));
        Assertions.assertEquals("single", cfg.getString("selectionMode"));
        Assertions.assertFalse(cfg.getBoolean("inline"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:datepicker")
        DatePicker datePicker;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "datepicker/datePicker001.xhtml";
        }
    }
}
