package com.smartbear.soapui.groovy;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.support.ModelSupport;
import groovy.ui.Console;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GroovyConsoleActionHelper<T extends ModelItem> {
    private final String variableName;
    private Map<T, Console> consoleMap = new HashMap<T, Console>();

    public GroovyConsoleActionHelper(String variableName) {
        this.variableName = variableName;
    }

    public void showConsole(T modelItem) {
        Console console = consoleMap.get(modelItem);

        if (console == null || !console.getFrame().getContentPane().isDisplayable()) {
            console = new Console();
            console.setVariable(variableName, modelItem);

            consoleMap.put(modelItem, console);
        }

        if (console.getFrame() != null && console.getFrame().getContentPane().isVisible() && console.getFrame() instanceof Window) {
            ((Window) console.getFrame()).toFront();
        } else {
            console.run();
            if (console.getFrame() instanceof Frame)
                ((Frame) console.getFrame()).setTitle("SoapUI Groovy Console for [" + variableName + "]");
        }
    }

    public void removeConsole(ModelItem modelItem) {
        Console console = consoleMap.get(modelItem);
        if (console != null) {
            console.exit();
            consoleMap.remove(modelItem);
        }
    }

    public void removeConsolesForModelItem(ModelItem modelItem) {
        for (ModelItem item : consoleMap.keySet()) {
            if (ModelSupport.dependsOn(item, modelItem))
                removeConsole(item);
        }
    }

    public Collection<T> getModelItems() {
        return consoleMap.keySet();
    }
}
