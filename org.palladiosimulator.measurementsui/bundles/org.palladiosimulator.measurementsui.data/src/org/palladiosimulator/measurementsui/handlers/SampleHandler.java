package org.palladiosimulator.measurementsui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.extensionpoint.evaluation.EvaluateExtensions;
import org.palladiosimulator.measurementsui.fileaccess.DataGathering;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;




public class SampleHandler extends AbstractHandler {
    
    private static final String ID = "org.palladiosimulator.measurementsui."
            + "extensionpoint.definition.PossibleMetricDescription";
             
        



	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	    
        EvaluateExtensions  ex = new EvaluateExtensions ();
        ex.loadExtensions();
        Object o = ex.getMeasuringPointmetricsCombinations().getUsageScenarioMeasuringPointMetrics().keySet();
        System.out.println(o);
        
        

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
    


		DataApplication start = DataApplication.getInstance();
		ResourceEditor editor = new ResourceEditorImpl();
		DataGathering gatherer = new DataGathering();
		
		start.loadData(0);
		
		Monitor aMon = start.getModelAccessor().getMonitorRepository().get(0).getMonitors().get(0); 
		EList<MeasurementSpecification> mSpecs = aMon.getMeasurementSpecifications();

		return null;
	}

	public static int test(String test) {
		if (test.equals("test1")) {
			return 1;
		} 
		return 0;
	}
}
