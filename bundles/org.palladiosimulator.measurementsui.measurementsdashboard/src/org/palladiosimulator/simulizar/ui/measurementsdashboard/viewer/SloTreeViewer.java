package org.palladiosimulator.simulizar.ui.measurementsdashboard.viewer;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.abstractviewer.MeasurementsTreeViewer;

import org.palladiosimulator.measurementsui.servicelevelobjectiveview.ServicelevelobjectiveviewInjectorProvider;
import org.palladiosimulator.servicelevelobjective.ServiceLevelObjectiveRepository;




public class SloTreeViewer extends MeasurementsTreeViewer{

    /**
     * 
     * @param parent
     *            the container where the tree viewer is placed in
     * @param dirty
     *            the dirty state which indicates whether there were changes made
     * @param commandService
     *            a service of the eclipse application in order to make the tree view saveable
     * @param repository
     *            a measuring point repository which is displayed with all its measuring points in
     *            the tree view
     */
	public SloTreeViewer(Composite parent, MDirtyable dirty, ECommandService commandService, ServiceLevelObjectiveRepository sloRepository) {
		super(parent, dirty, commandService, sloRepository);
	}

	@Override
	protected void initInjector() {
		this.injector = ServicelevelobjectiveviewInjectorProvider.getInjector();
	}

}
