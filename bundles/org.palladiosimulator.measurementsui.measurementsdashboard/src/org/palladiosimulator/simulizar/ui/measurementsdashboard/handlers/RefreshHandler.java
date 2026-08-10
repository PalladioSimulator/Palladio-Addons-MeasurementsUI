package org.palladiosimulator.simulizar.ui.measurementsdashboard.handlers;

import jakarta.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.palladiosimulator.simulizar.ui.measurementsdashboard.parts.MeasurementsDashboardView;

/**
 * Handler to support refresh functionality
 * @author Lasse Merz
 *
 */

public class RefreshHandler {
   
    @Execute
    void execute(EPartService partService, @Named(IServiceConstants.ACTIVE_PART) MPart part) {
        MeasurementsDashboardView view = (MeasurementsDashboardView) part.getObject();
        view.updateMeasurementsDashboardView();
    }

}
