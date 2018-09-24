package org.palladiosimulator.measurementsui.datamanipulation;

import java.util.Collections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * The DataEditor class provides the basic functions for working with Resources that already have an
 * editing domain, meaning they can only be modified through EMF Commands. These methods are further
 * implemented in {@link ResourceEditor}
 * 
 * @author Florian Nieuwenhuizen
 *
 */
public class DataEditor {

    /**
     * Changes the EObject element's attribute to the new value using a SetCommand. Old value is
     * overwritten. Make sure your element is attached to a resource, otherwise this will return
     * null
     * 
     * @author Florian
     * @param element
     * @param attribute
     * @param newValue
     */
    public void editResource(EObject element, String attribute, Object newValue) {

        EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
        domain.getCommandStack()
                .execute(new SetCommand(domain, element, element.eClass().getEStructuralFeature(attribute), newValue));
    }

    /**
     * Command for appending a resource to an element, like a list(measurementspecifications) using
     * the AddCommand
     * 
     * @param element
     * @param attribute
     * @param newValue
     */
    public void addResource(EObject element, String attribute, Object newValue) {
        EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
        domain.getCommandStack().execute(
                AddCommand.create(domain, element, element.eClass().getEStructuralFeature(attribute), newValue));
    }

    /**
     * Delete with DeleteCommand. Removes an element out of its domain.
     * 
     * @param element
     *            The Element to delete
     */
    public void deleteResource(EObject element) {
        EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
        domain.getCommandStack().execute(new DeleteCommand(domain, Collections.singleton(element)));

    }

}
