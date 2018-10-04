package org.palladiosimulator.measurementsui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;



public class SampleHandler extends AbstractHandler {


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

//          IExtensionRegistry registry = Platform.getExtensionRegistry();
//
//          IConfigurationElement[] config =
//          registry.getConfigurationElementsFor(ID);
//          try {
//              for (IConfigurationElement e : config) {
//                  System.out.println("Evaluating extension");
//                  final Object o =
//                          e.createExecutableExtension("class");
//                  
//                  System.out.println(o);
//                  
//                  if(o instanceof ITest) {
//                      ((ITest) o).greet();
//                  }
//                  
//                  if(o instanceof IMetrics) {
//                      System.out.println(((IMetrics) o).getmetrics().getName());
//                      System.out.println(((IMetrics) o).getmetrics().getTextualDescription());
//                  }
//
//              }
//
//          } catch (CoreException ex) {
//              System.out.println(ex.getMessage());
//          }
    

		return null;
	}

	public static int test(String test) {
		if (test.equals("test1")) {
			return 1;
		} 
		return 0;
	}
}
