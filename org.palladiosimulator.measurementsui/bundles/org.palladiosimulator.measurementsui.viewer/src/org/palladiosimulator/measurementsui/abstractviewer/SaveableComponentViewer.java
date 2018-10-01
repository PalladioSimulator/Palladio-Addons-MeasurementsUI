package org.palladiosimulator.measurementsui.abstractviewer;

import java.io.IOException;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;

/**
 * A common saveable view based on a parsley view.
 * 
 * @author David Schuetz
 */
public abstract class SaveableComponentViewer extends ComponentViewer {
    private static final String SAVE_COMMAND = "org.eclipse.ui.file.save";
    protected MDirtyable dirty;
    protected ECommandService commandService;
    protected DataApplication dataApplication;

    /**
     * 
     * @param parent
     *            the container where the view is embedded
     * @param dirty
     *            the dirty state which indicates whether there were changes made in the viewer
     * @param commandService
     *            a service of the eclipse application in order to make the tree view saveable
     * @param dataApplication
     *            the connection to the data binding. This is needed in order to get the repository
     *            of the current project.
     */
    protected SaveableComponentViewer(Composite parent, MDirtyable dirty, ECommandService commandService,
            DataApplication dataApplication) {
        super(parent);
        this.dataApplication = dataApplication;
        initEditingDomain();
        initParsley(parent);
        initContextMenu();
        initDragAndDrop();
        this.dirty = dirty;
        this.commandService = commandService;
    }

    /**
     * Connects the current selected item in the view with the eclipse selectionservice
     * 
     * @param selectionService
     *            of the eclipse project
     */
    public abstract void addSelectionListener(ESelectionService selectionService);

    @Override
    protected Resource updateResource(EObject model) {
        resource = super.updateResource(model);
        initResourceChangedListener(editingDomain);
        // For some mysterious reason the editing domain has to be set null here else parsley's
        // context menu won't function anymore
        editingDomain = null;
        return resource;
    }

    /**
     * Initializes a Listener to the editing domain, which activates the dirty state if something is
     * changed.
     * 
     * @param editingDomain
     *            where the listener is added to
     */
    private void initResourceChangedListener(EditingDomain editingDomain) {
        editingDomain.getCommandStack().addCommandStackListener(e -> {
            if (dirty != null) {
                dirty.setDirty(true);
                commandService.getCommand(SAVE_COMMAND).isEnabled();
            }
        });
    }

    /**
     * Saves the current state of the view
     * 
     * @param dirty
     *            describes whether the view was edited
     * @throws IOException
     *             if the save operation fails
     */
    public void save(MDirtyable dirty) throws IOException {
        resource.save(null);
        if (dirty != null) {
            dirty.setDirty(false);
            commandService.getCommand(SAVE_COMMAND).isEnabled();
        }
    }

    /**
     * Saves the current state of the view
     * 
     * @throws IOException
     *             if the save operation fails
     */
    public void save() throws IOException {
        resource.save(null);
        if (dirty != null) {
            dirty.setDirty(false);
            commandService.getCommand(SAVE_COMMAND).isEnabled();
        }
    }
}