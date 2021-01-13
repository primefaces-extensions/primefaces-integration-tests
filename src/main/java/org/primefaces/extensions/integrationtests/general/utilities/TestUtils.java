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
package org.primefaces.extensions.integrationtests.general.utilities;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.extensions.selenium.PrimeSelenium;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {

    /**
     * Hack for Safari that for some reason we need to wait between tests for the driver to reset?
     *
     * @param milliseconds number of millis to pause
     */
    public static void pause(int milliseconds) {
        if (PrimeSelenium.isSafari()) {
            wait(milliseconds);
        }
    }

    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException ex) {
            System.err.println("Wait was interrupted!");
        }
    }

    public static String join(Iterable<? extends CharSequence> values) {
        String result = "";
        if (values != null) {
            result = String.join(", ", values);
        }
        return result;
    }

    public static FacesMessage addMessage(String detail) {
        FacesMessage msg = new FacesMessage(detail);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return msg;
    }

    public static FacesMessage addMessage(String summary, String detail) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return msg;
    }
}
