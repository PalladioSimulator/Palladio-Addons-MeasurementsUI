package org.palladiosimulator.measurementsui.fileaccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to get valid projects from the workspace. Mainly used for finding the .aird file of projects
 * to determine if wizard work-flow can be applied on the project.
 * 
 * @author Florian Nieuwenhuizen
 *
 */
public class ValidProjectAccessor {

    private final Logger logger = LoggerFactory.getLogger(ValidProjectAccessor.class);

    /**
     * Passes a project instead of the string of the project name.
     * 
     * @return the String Path to .aird File
     * @param project
     *            in which to search for
     * 
     */
    public Optional<String> getAirdFileOfProject(IProject project) {
        try {
            IResource[] allMembers = project.members();
            for (IResource oneMember : allMembers) {
                if (oneMember.getFileExtension() != null && oneMember.getFileExtension().equals("aird")) {
                    return Optional.of(oneMember.getFullPath().toString());
                }
            }
        } catch (CoreException e) {
            logger.warn(e.toString());
        }
        return Optional.empty();
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
        List<IProject> allAirdProjects = new ArrayList<>();
        for (IProject project : wsProjects) {
            try {
                IResource[] allMembers = project.members();
                for (IResource oneMember : allMembers) {
                    if (oneMember.getFileExtension() != null && oneMember.getFileExtension().equals("aird")) {
                        allAirdProjects.add(project);
                    }
                }
            } catch (CoreException e) {
                logger.warn(e.toString());
            }
        }
        return allAirdProjects;
    }
}
