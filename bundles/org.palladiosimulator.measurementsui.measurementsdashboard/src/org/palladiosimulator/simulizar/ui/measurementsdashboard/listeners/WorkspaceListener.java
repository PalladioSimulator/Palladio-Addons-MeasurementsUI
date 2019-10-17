package org.palladiosimulator.simulizar.ui.measurementsdashboard.listeners;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.swt.widgets.Display;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.simulizar.ui.measurementsdashboard.parts.MeasurementsDashboardView;

/**
 * This class implements the IResourceChangeListener to listen to changes in the
 * workspace and update our GUI accordingly
 * 
 * @author Lasse Merz
 *
 */
public class WorkspaceListener implements IResourceChangeListener {

	private IProject addedProject;
	private IProject deletedProject;
	private IProject changedProject;

	private MeasurementsDashboardView dashboardView;
	private DataApplication dataApplication;

	/**
	 * Constructor initiates dashboardView and dataApplication
	 * 
	 * @param dashboardView to call for changes
	 */
	public WorkspaceListener(MeasurementsDashboardView dashboardView) {
		this.dashboardView = dashboardView;
		this.dataApplication = DataApplication.getInstance();
	}

	/**
	 * Listens to ChangeEvents and sets the changed project, which are then used to
	 * update our Dashboard view
	 */
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		deletedProject = null;
		addedProject = null;
		changedProject = null;

		IResourceDelta delta = event.getDelta();
		for (IResourceDelta deltaElement : delta.getAffectedChildren()) {
			IResource res = deltaElement.getResource();

			switch (deltaElement.getKind()) {
			case IResourceDelta.ADDED:
				if (res instanceof IProject) {
					addedProject = (IProject) res;
				}
				break;
			case IResourceDelta.REMOVED:
				if (res instanceof IProject) {
					deletedProject = (IProject) res;
				}
				break;
			case IResourceDelta.CHANGED:
				if (res instanceof IProject) {
					changedProject = (IProject) res;
				}
				break;
			default:
				break;
			}

		}
		updateDashboardView();
	}

	/**
	 * Updates the parts of our Dashboard view accordinglyt the changes in the
	 * workspace
	 */
	private void updateDashboardView() {
		if (deletedProject != null || addedProject != null) {

			Display.getDefault().asyncExec(() -> {

				if (deletedProject != null && deletedProject.equals(dataApplication.getProject())) {

					// currently selected project was deleted -> load different project
					if (addedProject == null) {
						if (!dataApplication.getValidProjectAccessor().getAllProjectAirdfiles().isEmpty()) {
							dashboardView.updateMeasurementsDashboardView(
									dataApplication.getValidProjectAccessor().getAllProjectAirdfiles().get(0));
						}
						dashboardView.updateProjectComboBox();

					} else {
						// the name of the currently selected project was changed
						dashboardView.updateMeasurementsDashboardView(addedProject);
						dashboardView.updateProjectComboBox();
						dashboardView.updateSLORepositoryComboBox();
					}

					// some project got added/deleted or name changed -> update comboBox with
					// Projects
				} else if (addedProject != null || deletedProject != null) {
					if (!dataApplication.getValidProjectAccessor().getAllProjectAirdfiles().isEmpty()) {
						dashboardView.updateMeasurementsDashboardView(addedProject);
					}
					dashboardView.updateProjectComboBox();
				}
			});
			
		} else if (changedProject != null) {
			// File in the project got created or deleted without the measurement dashboard
			try {
				dashboardView.updateMeasurementsDashboardView();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}