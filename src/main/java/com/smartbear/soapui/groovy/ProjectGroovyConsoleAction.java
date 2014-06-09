package com.smartbear.soapui.groovy;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.project.Project;
import com.eviware.soapui.model.support.WorkspaceListenerAdapter;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;

@ActionConfiguration(actionGroup = "EnabledWsdlProjectActions")
public class ProjectGroovyConsoleAction extends AbstractSoapUIAction<WsdlProject> {

    GroovyConsoleActionHelper<Project> helper = new GroovyConsoleActionHelper<Project>("project");
    InternalWorkspaceListener workspaceListener;

    public ProjectGroovyConsoleAction() {
        super("Groovy Console", "Opens the Groovy Console for this Project");
    }

    @Override
    public void perform(WsdlProject wsdlProject, Object o) {
        helper.showConsole(wsdlProject);

        if (workspaceListener == null) {
            workspaceListener = new InternalWorkspaceListener();
            wsdlProject.getWorkspace().addWorkspaceListener(workspaceListener);
        }
    }

    private class InternalWorkspaceListener extends WorkspaceListenerAdapter {
        @Override
        public void projectClosed(Project project) {
            helper.removeConsole(project);
        }
    }


}
