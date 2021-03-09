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
package org.primefaces.extensions.integrationtests.cascadeselect;

import lombok.Data;
import org.primefaces.event.SelectEvent;
import org.primefaces.extensions.integrationtests.general.model.Driver;
import org.primefaces.extensions.integrationtests.general.service.RealDriverService;
import org.primefaces.extensions.integrationtests.general.utilities.TestUtils;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Data
public class CascadeSelect003 implements Serializable {

    private static final long serialVersionUID = -5339070563799128801L;

    @Inject
    private RealDriverService driverService;

    private List<Driver> drivers;

    private Driver selectedDriver;

    @PostConstruct
    public void init() {
        drivers = driverService.getDrivers();
    }

    public void onItemSelect(SelectEvent event) {
        // pause for 1 second to verify AJAX guards are working properly
        TestUtils.wait(1000);

        TestUtils.addMessage("Selected driver", ((Driver)event.getObject()).getName());
    }

    public void submit() {
        TestUtils.addMessage("Selected driver", selectedDriver.getName());
    }

}
