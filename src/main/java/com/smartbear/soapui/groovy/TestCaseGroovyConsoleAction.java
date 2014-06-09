package com.smartbear.soapui.groovy;

import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.model.project.Project;
import com.eviware.soapui.model.support.ProjectListenerAdapter;
import com.eviware.soapui.model.support.TestSuiteListenerAdapter;
import com.eviware.soapui.model.support.WorkspaceListenerAdapter;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;

@ActionConfiguration(actionGroup = "WsdlTestCaseActions")
public class TestCaseGroovyConsoleAction extends AbstractSoapUIAction<WsdlTestCase> {
    private GroovyConsoleActionHelper<TestCase> helper = new GroovyConsoleActionHelper<TestCase>("testCase");
    private InternalProjectListener projectListener;
    private InternalTestSuiteListener testSuiteListener;

    public TestCaseGroovyConsoleAction() {
        super("Groovy Console", "Opens the Groovy Console for this TestCase");
    }

    @Override
    public void perform(WsdlTestCase testCase, Object o) {
        helper.showConsole(testCase);

        if (projectListener == null) {
            projectListener = new InternalProjectListener();
            testSuiteListener = new InternalTestSuiteListener();

            testCase.getTestSuite().addTestSuiteListener(testSuiteListener);
            testCase.getTestSuite().getProject().addProjectListener(projectListener);
            testCase.getTestSuite().getProject().getWorkspace().addWorkspaceListener(new InternalWorkspaceListener());
        }
    }

    private class InternalTestSuiteListener extends TestSuiteListenerAdapter {
        @Override
        public void testCaseRemoved(TestCase testCase) {
            helper.removeConsole(testCase);
        }
    }

    private class InternalProjectListener extends ProjectListenerAdapter {
        @Override
        public void testSuiteRemoved(TestSuite testSuite) {
            helper.removeConsolesForModelItem(testSuite);
            testSuite.removeTestSuiteListener(testSuiteListener);
        }
    }

    private class InternalWorkspaceListener extends WorkspaceListenerAdapter {
        @Override
        public void projectClosed(Project project) {
            project.removeProjectListener(projectListener);

            for (TestCase testCase : helper.getModelItems()) {
                testCase.getTestSuite().removeTestSuiteListener(testSuiteListener);
                testCase.getTestSuite().getProject().removeProjectListener(projectListener);
            }

            helper.removeConsolesForModelItem(project);
        }
    }
}
