package org.palladiosimulator.measurementsui.datamanipulation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;

public class DataEditor {

    /**
     * Changes the EObject element's attribute to the new Value. Implementation is done in ResourceEditor class.
     * @author Florian
     * @param element
     * @param attribute
     * @param newValue
     */
    public void editResource(EObject element, String attribute, Object newValue) {
        // Make sure your element is attached to a resource, otherwise this will return null
        EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
        domain.getCommandStack()
                .execute(new SetCommand(domain, element, element.eClass().getEStructuralFeature(attribute), newValue));
    }

}