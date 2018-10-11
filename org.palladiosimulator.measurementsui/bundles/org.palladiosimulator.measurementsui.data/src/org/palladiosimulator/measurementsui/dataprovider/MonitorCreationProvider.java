package org.palladiosimulator.measurementsui.dataprovider;

import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * Provides methods for the MonitorCreationWizardPage, the first page of the wizard.
 * 
 * 
 * @author Lasse
 *
 */
public class MonitorCreationProvider {
    
    private ResourceEditorImpl editor = ResourceEditorImpl.getInstance();
    
    /**
     * Sets the value of the activated attribute of the monitor
     * @param monitor to change the attribute
     * @param activatedValue new value
     */
    public void setMonitorActivatedValue(Monitor monitor, boolean activatedValue) {
        editor.changeMonitorActive(monitor, activatedValue);
    }
    
    /**
     * 
     * Sets the name of the Monitor to the new name
     * @param monitor to change the name
     * @param name new monitor name
     */
    public void setMonitorName(Monitor monitor, String name) {
        editor.setResourceName(monitor, name);
    }

}
