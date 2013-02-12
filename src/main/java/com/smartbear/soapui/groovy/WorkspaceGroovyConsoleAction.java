package com.smartbear.soapui.groovy;

import com.eviware.soapui.model.workspace.Workspace;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;

public class WorkspaceGroovyConsoleAction extends AbstractSoapUIAction<Workspace> {

    GroovyConsoleActionHelper<Workspace> helper = new GroovyConsoleActionHelper<Workspace>("workspace");

    public WorkspaceGroovyConsoleAction() {
        super("Groovy Console", "Opens the Groovy Console for this Project");
    }

    @Override
    public void perform(Workspace workspace, Object o) {
        helper.showConsole(workspace);
    }
}
