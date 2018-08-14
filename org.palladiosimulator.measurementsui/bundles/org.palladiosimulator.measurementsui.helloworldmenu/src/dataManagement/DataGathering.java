package dataManagement;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
					try {
						IResource [] allMembers = project.members();
						for( IResource oneMember : allMembers) {
							try {
								if( oneMember.getFileExtension().equals("aird")) {
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
		
		public String getAirdFile(IProject project) {
			try {
				IResource [] allMembers = project.members();
				for( IResource oneMember : allMembers) {
					try {
						if( oneMember.getFileExtension().equals("aird")) {
							return oneMember.getFullPath().toString();
						}
					} catch (NullPointerException e) {
						//NullPointer occurs when files don't have a file ending. We can ignore it
					}
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 
		 * @param project
		 * @param fileEnding
		 * @return we remove the ":file" at the beginning of the URI because we cant convert java URI to EMF URI, and emf URI doesn't like the ":file" at beginning
		 */
		public String getChosenFile(IProject project, String fileEnding) {
			try {
				IResource [] allMembers = project.members();
				for( IResource oneMember : allMembers) {
					try {
						if( oneMember.getFileExtension().equals(fileEnding)) {
							return oneMember.getLocationURI().toString().replace("file:", "");
							
						}
					} catch (NullPointerException e) {
						//NullPointer occurs when files don't have a file ending. We can ignore it
					}
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * Looks through each project in the workspace. If project contains an .aird file, we add it to List and return
		 * @return List of all projects that contain an .aird file
		 */
		public List<IProject> getAllProjectAirdfiles() {
			IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
			IProject [] wsProjects = wsRoot.getProjects();
			List<IProject> allAirdProjects = new ArrayList<IProject>();
			for (IProject project : wsProjects) {
				try {
					IResource [] allMembers = project.members();
					for(IResource oneMember : allMembers) {
						try {
							if(oneMember.getFileExtension().equals("aird")) {
								allAirdProjects.add(project);
							}
						} catch (NullPointerException e) {
							//NullPointer occurs when files don't have a file ending. We can ignore it
						}
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return allAirdProjects;
		}
		
		/**
		 * @author Florian
		 * @return the name of the selected base project
		 */
		private String getCurrentSelectedProject() {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			try {
				if(window != null) {
					IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
					Object firstElement = selection.getFirstElement();
					String projectName = firstElement.toString().split("/")[1];
					return projectName;
				}
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				System.out.println("Make sure a project is selected");
			}
			return null;
		}

}
