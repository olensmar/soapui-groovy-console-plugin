package com.smartbear.soapui.groovy;

import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.model.project.Project;
import com.eviware.soapui.model.support.ProjectListenerAdapter;
import com.eviware.soapui.model.support.WorkspaceListenerAdapter;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;

public class TestSuiteGroovyConsoleAction extends AbstractSoapUIAction<WsdlTestSuite> {
    GroovyConsoleActionHelper<TestSuite> helper = new GroovyConsoleActionHelper<TestSuite>("testSuite");
    InternalProjectListener projectListener;

    public TestSuiteGroovyConsoleAction() {
        super("Groovy Console", "Opens the Groovy Console for this TestSuite");
    }

    @Override
    public void perform(WsdlTestSuite testSuite, Object o) {
        helper.showConsole(testSuite);

        if (projectListener == null) {
            projectListener = new InternalProjectListener();
            testSuite.getProject().addProjectListener(projectListener);
            testSuite.getProject().getWorkspace().addWorkspaceListener(new InternalWorkspaceListener());
        }
    }

    private class InternalProjectListener extends ProjectListenerAdapter {
        @Override
        public void testSuiteRemoved(TestSuite testSuite) {
            helper.removeConsole(testSuite);
        }
    }

    private class InternalWorkspaceListener extends WorkspaceListenerAdapter {
        @Override
        public void projectClosed(Project project) {
            project.removeProjectListener(projectListener);

            for (TestSuite testSuite : helper.getModelItems()) {
                testSuite.getProject().removeProjectListener(projectListener);
            }

            helper.removeConsolesForModelItem(project);
        }
    }
}
