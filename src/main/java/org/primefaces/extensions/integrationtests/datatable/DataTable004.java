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
package org.primefaces.extensions.integrationtests.datatable;

import lombok.Data;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Data
public class DataTable004 implements Serializable {

    private static final long serialVersionUID = -7518459955779385834L;

    private List<ProgrammingLanguage> progLanguages;
    private ProgrammingLanguage selectedProgLanguage;

    @Inject
    private ProgrammingLanguageService service;

    @PostConstruct
    public void init() {
        progLanguages = service.getLangs();
    }

    public void onRowSelect(SelectEvent<ProgrammingLanguage> event) {
        FacesMessage msg = new FacesMessage("ProgrammingLanguage Selected", event.getObject().getId() + " - " + event.getObject().getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent<ProgrammingLanguage> event) {
        FacesMessage msg = new FacesMessage("ProgrammingLanguage Unselected", event.getObject().getId() + " - " + event.getObject().getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void submit() {
        if (selectedProgLanguage != null) {
            FacesMessage msg = new FacesMessage("Selected ProgrammingLanguage", selectedProgLanguage.getId() + " - " + selectedProgLanguage.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
