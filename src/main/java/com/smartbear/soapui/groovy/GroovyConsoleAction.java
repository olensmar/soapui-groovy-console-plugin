package com.smartbear.soapui.groovy;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;
import groovy.ui.Console;

public class GroovyConsoleAction extends AbstractSoapUIAction<WsdlProject> {
    Console console;

    public GroovyConsoleAction()

    {
        super("Groovy Console", "Opens the Groovy Console for this project");
    }

    @Override
    public void perform(WsdlProject wsdlProject, Object o) {
        if (console == null)
            console = new Console();

        console.setVariable("project", wsdlProject);
        console.run();
    }
}
