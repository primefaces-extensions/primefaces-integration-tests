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
package org.primefaces.extensions.integrationtests.selectonemenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.util.LangUtils;

import lombok.Data;

@Named
@ViewScoped
@Data
public class SelectOneMenu006 implements Serializable {

    private static final long serialVersionUID = -7798312444085660208L;

    private String console;
    private List<SelectItem> availableConsoles;

    @PostConstruct
    public void init() {
        availableConsoles = new ArrayList<>();
        SelectItemGroup group1 = new SelectItemGroup("Older");
        group1.setSelectItems(new SelectItem[] {new SelectItem("XBOXONE", "Xbox One"), new SelectItem("PS3", "PlayStation 3"),
                    new SelectItem("WII", "Wii U")});
        availableConsoles.add(group1);

        SelectItemGroup group2 = new SelectItemGroup("Newer");
        group2.setSelectItems(new SelectItem[] {new SelectItem("XBOXX", "Xbox X"), new SelectItem("PS5", "PlayStation 5"),
                    new SelectItem("SWITCH", "Nintendo Switch")});
        availableConsoles.add(group2);
    }

    public void submit() {
        if (!LangUtils.isValueBlank(console)) {
            FacesMessage msg = new FacesMessage("Console", console);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
