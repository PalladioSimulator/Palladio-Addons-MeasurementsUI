package toolbar.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointPackage;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;

import simulizarmeasuringpoint.SimulizarmeasuringpointPackage;



/** <b>Warning</b> : 
As explained in <a href="http://wiki.eclipse.org/Eclipse4/RCP/FAQ#Why_aren.27t_my_handler_fields_being_re-injected.3F">this wiki page</a>, it is not recommended to define @Inject fields in a handler. <br/><br/>
<b>Inject the values in the @Execute methods</b>
*/
public class HelloWorldHandler {

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell s) {
		
		String filename ="/Users/enpro1/git/Palladio-Analyzer-SimuLizar/ExampleModels/org.palladiosimulator.simulizar.examples.loadbalancer/monitors/sensors.measuringpoint";

//		IFile file= new IFile() {
//		};
		MeasuringpointPackage p = MeasuringpointPackage.eINSTANCE;
		File f = new File(filename); 
		if(f.exists() && !f.isDirectory()) { 
		    System.out.println("Yes");
		}
		ResourceSet rset = new ResourceSetImpl();
		rset.getPackageRegistry().put(SimulizarmeasuringpointPackage.eNS_URI, SimulizarmeasuringpointPackage.eINSTANCE);
		Resource resource = rset.getResource(URI.createFileURI(filename) , true);
		SimulizarmeasuringpointPackage  packageinstance = SimulizarmeasuringpointPackage.eINSTANCE;
		TreeIterator<EObject> it= resource.getAllContents();
		String test="";
		while(it.hasNext()) {
			EObject tada =it.next();
			test+=tada;
			TreeIterator<EObject> it2= tada.eAllContents();
			while(it2.hasNext()) {
				test+=it2.next();
			}
		}
		MessageDialog.openInformation(s, "E4 Information Dialog", test+ " "+resource.getContents().get(0).toString()+ " Hello world from a pure Eclipse 4 plug-in");

		
//		   IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
//		   IProject myWebProject = myWorkspaceRoot.getProject("org.palladiosimulator.simulizar.examples.znncom");
////		    open if necessary
//		   if (myWebProject.exists() && !myWebProject.isOpen()) {
//			   myWebProject.open(null);
//			   System.out.println("Yeah");
//		   }else {
//			   System.out.println("No");
//		   }
//			 Resource[] psResource =  (Resource[]) myWebProject.members();
//
//			 System.out.println(psResource.isLoaded());
//			 System.out.println(psResource.getContents().get(0));
//		      myWebProject.open(null);
//		   MessageDialog.openInformation(s, "E4 Information Dialog", myWebProject.getDescription()+"Hello world from a pure Eclipse 4 plug-in");
//	}
//	public static boolean match(String text, String pattern)
//	{
//	  return text.matches(pattern.replace("?", ".?").replace("*", ".*?"));
		
		
	} 
	

}
