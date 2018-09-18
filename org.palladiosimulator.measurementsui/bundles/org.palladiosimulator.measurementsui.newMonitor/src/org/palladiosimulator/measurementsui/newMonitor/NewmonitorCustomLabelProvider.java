package org.palladiosimulator.measurementsui.newMonitor;

import javax.inject.Inject;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.parsley.ui.provider.ViewerLabelProvider;
import org.palladiosimulator.monitorrepository.Monitor;

/**
 * This class is the custom label provider used in the form view of the first wizard page.
 * @author Ba
 *
 */
public class NewmonitorCustomLabelProvider extends ViewerLabelProvider {
    
    @Inject
    public NewmonitorCustomLabelProvider(AdapterFactoryLabelProvider delegate) {
        super(delegate);
    }

    /**
     * Returns the name for the given monitor
     * @param monitor the given monitor
     * @return the name for the given monitor
     */
    public String text(Monitor monitor) {
        String name;
        if (monitor.getEntityName() != null) {
            name = monitor.getEntityName();
        } else {
            name = "";
        }
        return "Monitor: " + name;
    }

    /**
     * Returns the image icon that is displayed next to the name of the given monitor.
     * @param monitor the given monitor
     * @return the icon that is displayed next to the name of the given monitor
     */
    public String image(Monitor monitor) {
        if (monitor.isActivated()) {
            return "ActiveIcon.png";
        } else {
            return "InactiveIcon.png";
        }
    }
    
}
