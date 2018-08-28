package org.palladiosimulator.measurementsui.datamanipulation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;

public class DataEditor {

    /**
     * https://stackoverflow.com/questions/38114267/emf-write-transaction
     * 
     * @author Florian
     * @param element
     * @param attribute
     * @param newName
     */
    public void editResourceName(EObject element, String attribute, String newName) {
        // Make sure your element is attached to a resource, otherwise this will return null
        EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
        domain.getCommandStack()
                .execute(new SetCommand(domain, element, element.eClass().getEStructuralFeature(attribute), newName));
    }

    /**
     * Gets the editing domain of the element, changes the boolean attribute of the element
     * 
     * @param element
     * @param attribute
     * @param status
     */
    public void editResourceActivated(EObject element, String attribute, boolean status) {
        // Make sure your element is attached to a resource, otherwise this will return null
        EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
        domain.getCommandStack()
                .execute(new SetCommand(domain, element, element.eClass().getEStructuralFeature(attribute), status));
    }

    /**
     * alternative method to edit measuring points, if it is not possible to do it with parsley
     * 
     * @param element
     * @param attribute
     * @param mp
     */
    public void editMeasuringPoint(EObject element, String attribute, MeasuringPoint mp) {
        // Make sure your element is attached to a resource, otherwise this will return null
        EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
        domain.getCommandStack()
                .execute(new SetCommand(domain, element, element.eClass().getEStructuralFeature(attribute), mp));
    }

    /**
     * alternative method to add resources, if it is not possible to do it with parsley
     * 
     * @param element
     * @param attribute
     * @param newObject
     */
    public void addResource(EObject element, String attribute, EObject newObject) {
        EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
        domain.getCommandStack()
                .execute(new AddCommand(domain, element, element.eClass().getEStructuralFeature(attribute), newObject));
    }

}
