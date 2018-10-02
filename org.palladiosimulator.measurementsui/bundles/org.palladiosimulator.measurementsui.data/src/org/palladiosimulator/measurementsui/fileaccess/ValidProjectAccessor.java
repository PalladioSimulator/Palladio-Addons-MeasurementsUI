package org.palladiosimulator.measurementsui.fileaccess;

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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used to get valid projects from the workspace. Mainly used for finding the .aird file of projects
 * to determine if wizard work-flow can be applied on the project.
 * 
 * @author Florian
 *
 */
public class ValidProjectAccessor {

    private static final Logger LOGGER = Logger.getLogger(ValidProjectAccessor.class.getName());

    /**
     * This Method returns the path to the ".aird" file depeding on the selected project.
     * 
     * @return String path of .aird file
     */
    public String getAirdPath() {
        return getAirdFile(getCurrentSelectedProject());
    }

    /**
     * This method returns the aird file of the project that is currently selected in project
     * explorer
     * 
     * @param selectedProject
     *            a String of the base project that is currently selected in the project explorer
     * @return the String Path to .aird File
     */
    private String getAirdFile(String selectedProject) {
        IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] wsProjects = wsRoot.getProjects();
        for (IProject project : wsProjects) {
            if (selectedProject != null && project.getName().equals(selectedProject)) {
                return getAirdFileOfProject(project);
            }
        }
        return null;
    }

    /**
     * Passes a project instead of the string of the project name.
     * 
     * @return the String Path to .aird File
     * @param project
     *            in which to search for
     * 
     */
    public String getAirdFileOfProject(IProject project) {
        try {
            IResource[] allMembers = project.members();
            for (IResource oneMember : allMembers) {
                if (oneMember.getFileExtension() != null && oneMember.getFileExtension().equals("aird")) {
                    return oneMember.getFullPath().toString();
                }
            }
        } catch (CoreException e) {
            LOGGER.log(Level.FINEST, e.toString(), e);
        }
        return null;
    }

    /**
     * Allows us to search for any file with certain file ending in the given project.
     * 
     * @param project
     *            The project to search for
     * @param fileEnding
     *            The file ending to search for
     * @return we remove the ":file" at the beginning of the URI because we can't convert java URI
     *         to EMF URI, and emf URI doesn't like the ":file" at beginning
     */
    public String getChosenFile(IProject project, String fileEnding) {
        try {
            IResource[] allMembers = project.members();
            for (IResource oneMember : allMembers) {
                if (oneMember.getFileExtension() != null && oneMember.getFileExtension().equals(fileEnding)) {
                    return oneMember.getLocationURI().toString().replace("file:", "");
                }
            }
        } catch (CoreException e) {
            LOGGER.log(Level.FINEST, e.toString(), e);
        }
        return null;
    }

    /**
     * Looks through each project in the workspace. If project contains an .aird file, we add it to
     * List and return
     * 
     * @return List of all projects that contain an .aird file
     */
    public List<IProject> getAllProjectAirdfiles() {
        IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] wsProjects = wsRoot.getProjects();
        List<IProject> allAirdProjects = new ArrayList<IProject>();
        for (IProject project : wsProjects) {
            try {
                IResource[] allMembers = project.members();
                for (IResource oneMember : allMembers) {
                    if (oneMember.getFileExtension() != null && oneMember.getFileExtension().equals("aird")) {
                        allAirdProjects.add(project);
                    }
                }
            } catch (CoreException e) {
                LOGGER.log(Level.FINEST, e.toString(), e);
            }
        }
        return allAirdProjects;
    }

    /**
     * Currently not in use. Will likely be deleted later.
     * 
     * @return the name of the selected base project.
     */
    private String getCurrentSelectedProject() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        try {
            if (window != null) {
                IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
                Object firstElement = selection.getFirstElement();
                return firstElement.toString().split("/")[1];
            }
        } catch (NullPointerException e) {
            LOGGER.log(Level.FINEST, "Make sure a project is selected", e);
        }
        return null;
    }

}
