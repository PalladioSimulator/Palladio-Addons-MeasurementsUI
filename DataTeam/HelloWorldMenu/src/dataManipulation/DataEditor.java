package dataManipulation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

public class DataEditor {

	
	public static void doEditing(EObject element, String attribute, String newName) {
	    // Make sure your element is attached to a resource, otherwise this will return null
	    TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(element);
	    domain.getCommandStack().execute(new RecordingCommand(domain) {
	    
	        @Override
	        protected void doExecute() {
	            // Implement your write operations here,
	            // for example: set a new name
	            element.eSet(element.eClass().getEStructuralFeature(attribute), newName);
	        }
	    });
	}
}
