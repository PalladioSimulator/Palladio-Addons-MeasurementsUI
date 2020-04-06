package org.palladiosimulator.measurementsui.abstractviewer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.net4j.util.om.log.Log;
import org.eclipse.swt.widgets.Composite;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;

/**
 * A common saveable view based on a parsley view.
 * 
 * @author David Schuetz
 */
public abstract class SaveableComponentViewer extends ComponentViewer {
    private static final String SAVE_COMMAND = "org.eclipse.ui.file.save";
    private static final String SAVEALL_COMMAND = "org.eclipse.ui.file.saveAll";

    protected MDirtyable dirty;
    protected ECommandService commandService;

    /**
     * 
     * @param parent
     *            the container where the view is embedded
     * @param dirty
     *            the dirty state which indicates whether there were changes made in the viewer
     * @param commandService
     *            a service of the eclipse application in order to make the tree view saveable
     * @param modelRepository
     *            EObject which is shown in the view
     */
    protected SaveableComponentViewer(Composite parent, MDirtyable dirty, ECommandService commandService,
            EObject modelRepository) {
        super(parent, modelRepository);
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
        // context menu won't function anymore if you want to delete a measuring point
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
                commandService.getCommand(SAVEALL_COMMAND).isEnabled();
            }
        });
    }

    /**
     * Undos one command on the command stack
     */
    public void undo() {
        initEditingDomain();
        CommandStack commandStack = editingDomain.getCommandStack();
        if (commandStack.canUndo()) {
            commandStack.undo();
        }
    }

    /**
     * Undos every command on the command stack
     */
    public void undoAll() {
        initEditingDomain();
        CommandStack commandStack = editingDomain.getCommandStack();
        while (commandStack.canUndo()) {
            commandStack.undo();
        }
    }

    /**
     * Redos one command on the command stack
     */
    public void redo() {
        initEditingDomain();
        CommandStack commandStack = editingDomain.getCommandStack();
        if (commandStack.canRedo()) {
            commandStack.redo();
        }
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
        final Map<Object, Object> options = new HashMap<>();
        options.put(XMLResource.OPTION_SAVE_ONLY_IF_CHANGED, XMLResource.OPTION_SAVE_ONLY_IF_CHANGED_FILE_BUFFER);       
        resource.save(options);
        if (dirty != null) {
            dirty.setDirty(false);
            commandService.getCommand(SAVE_COMMAND).isEnabled();
            commandService.getCommand(SAVEALL_COMMAND).isEnabled();
        }
    }

    /**
     * Saves the current state of the view
     * 
     * @throws IOException
     *             if the save operation fails
     */
    public void save() throws IOException {
        save(dirty);
    }
    
    public abstract void update(EObject modelRepository);
}