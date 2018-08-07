package dataManagement;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class DataGathering {
	
	/**
	 * @author Florian
	 * This Method returns the path to the ".aird" file depeding on the selected project.
	 * @return String path of .aird file
	 */
	public String getAirdPath() {
		return getAirdFile(getCurrentSelectedProject());
	}
	
	/**
	 * @author Florian
	 * This method returns the aird file of the project that is currently selected in project explorer
	 * @param selectedProject a String of the base project that is currently selected in the project explorer
	 */
		private String getAirdFile(String selectedProject) {
			IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
			IProject [] wsProjects = wsRoot.getProjects();
			for (IProject project : wsProjects) {
				if(selectedProject != null && project.getName().equals(selectedProject)) {	
//					System.out.println("Project :"+project.getName());
					try {
						IResource [] allMembers = project.members();
						for( IResource oneMember : allMembers) {
//							System.out.println("Member: "+oneMember.getName());
//							System.out.println("Extension : "+oneMember.getFileExtension());
							try {
								if( oneMember.getFileExtension().equals("aird")) {
//									System.out.println("Path of aird File: "+ oneMember.getFullPath());
//									System.out.println("URI of aird File: "+ oneMember.getLocationURI().toString());
									
									return oneMember.getFullPath().toString();
								}
							} catch (NullPointerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			return null;
		}
		/**
		 * @author Florian
		 * @return the name of the selected base project
		 */
		private String getCurrentSelectedProject() {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			try {
				if(window != null) {
//					System.out.println("Window: "+window.toString());
					IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
//					System.out.println("Selection: "+selection.toString());
					
					Object firstElement = selection.getFirstElement();
//					System.out.println("First Element: "+ firstElement.toString());
					String projectName = firstElement.toString().split("/")[1];
//					System.out.println("Base Project: "+ projectName);
					
//					if(firstElement instanceof IAdaptable) {
//						IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
//						System.out.println("Project: "+ project);
//						
//					}
					return projectName;
				}
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				System.out.println("Make sure a project is selected");
			}
			return null;
		}

}
