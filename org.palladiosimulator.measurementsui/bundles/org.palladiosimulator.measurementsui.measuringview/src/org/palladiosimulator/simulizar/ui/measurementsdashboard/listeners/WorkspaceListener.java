package org.palladiosimulator.simulizar.ui.measurementsdashboard.listeners;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.simulizar.ui.measurementsdashboard.parts.MeasurementsDashboardView;

/**
 * This class implements the IResourceChangeListener to listen to changes
 * in the workspace and update our GUI accordingly
 * 
 * @author Lasse
 *
 */
public class WorkspaceListener implements IResourceChangeListener {
    
   private IProject addedProject;
   private IProject changedProject;
   private IProject deletedProject;
   
   private MeasurementsDashboardView dashboardView;
   private DataApplication dataApplication;
    
    
    /**
     * Constructor 
     * initiates dashboardView and dataApplication
     * @param dashboardView to call for changes
     */
    public WorkspaceListener(MeasurementsDashboardView dashboardView) {
        this.dashboardView = dashboardView;
        this.dataApplication = this.dashboardView.getDataApplication();
    }

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
                if(res instanceof IProject) {
                    addedProject = (IProject) res;
                }
                break;
            case IResourceDelta.REMOVED:
                if(res instanceof IProject) {
                    deletedProject = (IProject) res;
                }
                break;
            case IResourceDelta.CHANGED:
                //only listen to changes in currently viewed project of the dashboard view
                if(res instanceof IProject && res.equals(dataApplication.getProject())) {  
                    checkChangedEvent(deltaElement);
                }
                break;
            default:
                break;
            }

        }  
        updateDashboardView();

    }
    /**
     * Checks the ChangeEvent specially
     * we only need to update our view if content was changed
     * in the "monitorrepository" or "measuringpoint" file
     * @param delta
     */
    private void checkChangedEvent(IResourceDelta delta) {
        try {
            delta.accept(new IResourceDeltaVisitor() {                                   
                @Override
                public boolean visit(IResourceDelta delta) throws CoreException {
                    IResource res = delta.getResource();
                    int flags = delta.getFlags();
                    if(delta.getKind() == IResourceDelta.CHANGED) {
                        if (res instanceof IFile && (res.getFileExtension().equals("monitorrepository")||
                                (res.getFileExtension().equals("measuringpoint")))) {
                            if ((flags & IResourceDelta.CONTENT) != 0) {
                                changedProject = res.getProject();
                            }

                        }
                    }

                    return true;
                }
            });
        } catch (CoreException e) {
            e.printStackTrace();
        }                       

    } 
    
    /**
     * Updates the parts of our Dashboard view accordinglyt the changes
     * in the workspace
     */
    private void updateDashboardView() {
        if (deletedProject != null || addedProject != null || changedProject != null) {

            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {

                    if (deletedProject != null && deletedProject.equals(dataApplication.getProject())) {
                        
                        // currently selected project was deleted -> load different project
                        if (addedProject == null) {
                            dataApplication.loadData(
                                    dataApplication.getDataGathering().getAllProjectAirdfiles().get(0));
                            dashboardView.updateProjectComboBox();
                            dashboardView.updateTreeViewer();
                        } else {
                            // the name of the currently selected project was changed                    
                            dataApplication.loadData(addedProject);
                            dashboardView.updateProjectComboBox();
                            dashboardView.updateTreeViewer();
                        }
                        
                        // some project got added/deleted or name changed -> update comboBox with Projects
                    } else if (addedProject != null || deletedProject != null) {
                        dashboardView.updateProjectComboBox();
                        
                        //a monitor or measuringPoint was added/deleted in the selected project -> update data and view
                    } else if (changedProject != null) {
                        dataApplication.loadData(changedProject);
                        dashboardView.updateTreeViewer();
                    }

                }
            });

        }

    }


}
