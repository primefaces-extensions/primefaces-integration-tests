/*
 * Copyright 2011-2020 PrimeFaces Extensions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.extensions.integrationtests.datatable;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.component.DataTable;
import org.primefaces.extensions.selenium.component.Messages;

public class DataTable009Test extends AbstractDataTableTest {

    private final List<ProgrammingLanguage> langs = new ProgrammingLanguageService().getLangs();

    @Test
    @Order(1)
    @DisplayName("DataTable: filter - issue 1390 - https://github.com/primefaces/primefaces/issues/1390")
    public void testFilterIssue1390(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act - do some filtering
        dataTable.sort("Name");
        dataTable.filter("Name", "Java");

        // Assert
        Assertions.assertEquals("FilterValue for name", page.messages.getMessage(0).getSummary());
        Assertions.assertEquals("Java", page.messages.getMessage(0).getDetail());
        Assertions.assertEquals("FilteredValue(s)", page.messages.getMessage(1).getSummary());
        Assertions.assertEquals("Java,JavaScript", page.messages.getMessage(1).getDetail());

        // Act - do some other filtering
        dataTable.filter("Name", "JavaScript");

        // Assert
        Assertions.assertEquals("FilterValue for name", page.messages.getMessage(0).getSummary());
        Assertions.assertEquals("JavaScript", page.messages.getMessage(0).getDetail());
        Assertions.assertEquals("FilteredValue(s)", page.messages.getMessage(1).getSummary());
        Assertions.assertEquals("JavaScript", page.messages.getMessage(1).getDetail());

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("filter"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @Override
        public String getLocation() {
            return "datatable/dataTable009.xhtml";
        }
    }
}
