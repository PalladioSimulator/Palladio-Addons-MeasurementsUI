package dataManipulation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;

public class DataEditor {

	/**
	 * https://stackoverflow.com/questions/38114267/emf-write-transaction
	 * @author Florian
	 * @param element
	 * @param attribute
	 * @param newName
	 */	
	public void editResourceName(EObject element, String attribute, String newName) {
	    // Make sure your element is attached to a resource, otherwise this will return null
	    EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
	    domain.getCommandStack().execute(new SetCommand(domain, element, element.eClass().getEStructuralFeature(attribute), newName));
	}
	
//	public static void editResourceName(EObject element, String attribute, String newName) {
//    // Make sure your element is attached to a resource, otherwise this will return null
//    TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(element);
//    domain.getCommandStack().execute(new RecordingCommand(domain) {
//    
//        @Override
//        protected void doExecute() {
//            // Implement your write operations here,
//            // for example: set a new name
//            element.eSet(element.eClass().getEStructuralFeature(attribute), newName);
//        }
//    });
//}
	
	public void editResourceActivated(EObject element, String attribute, boolean status) {
	    // Make sure your element is attached to a resource, otherwise this will return null
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
	    domain.getCommandStack().execute(new SetCommand(domain, element, element.eClass().getEStructuralFeature(attribute), status));
	}
	
	public void editMeasuringPoint(EObject element, String attribute, MeasuringPoint mp) {
	    // Make sure your element is attached to a resource, otherwise this will return null
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
	    domain.getCommandStack().execute(new SetCommand(domain, element, element.eClass().getEStructuralFeature(attribute), mp));
	}
	
	public void addResource(EObject element, String attribute, EObject newObject) {
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
		domain.getCommandStack().execute(new AddCommand(domain, element, element.eClass().getEStructuralFeature(attribute), newObject));
	}
	
//	public static void editMeasurementSpecification(EObject element, String attribute, MeasurementSpecification specToAdd) {
//	    // Make sure your element is attached to a resource, otherwise this will return null
//	    TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(element);
//	    domain.getCommandStack().execute(new RecordingCommand(domain) {
//	    
//	        @Override
//	        protected void doExecute() {
//
//	        	Monitor monitor = (Monitor) element;
//	        	EList<MeasurementSpecification> repo = monitor.getMeasurementSpecifications();
//	        	repo.add(specToAdd);
//	            element.eSet(element.eClass().getEStructuralFeature(attribute), repo);
//	            
//	        }
//	    });
//	}
	
	
}
