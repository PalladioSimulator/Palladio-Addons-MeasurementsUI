package org.palladiosimulator.measurementsui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.fileaccess.DataGathering;
import org.palladiosimulator.metricspec.MetricDescription;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepositoryPackage;




public class SampleHandler extends AbstractHandler {


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {


		DataApplication start = DataApplication.getInstance();
		ResourceEditor editor = new ResourceEditorImpl();
		DataGathering gatherer = new DataGathering();
		
		start.loadData(0);
		
		Monitor aMon = start.getModelAccessor().getMonitorRepository().get(0).getMonitors().get(0); 
		EList<MeasurementSpecification> mSpecs = aMon.getMeasurementSpecifications();
		
		System.out.println("Size: "+mSpecs.size());
		if(!mSpecs.isEmpty()) {
		    EList<MetricDescription> metricDescInMonitor = new BasicEList<MetricDescription>();
		    EList<MetricDescription> allMetricDescriptions = mSpecs.get(0).getMetricDescription().getRepository().getMetricDescriptions();
		    for (MeasurementSpecification aMSpec: mSpecs) {
		        metricDescInMonitor.add(aMSpec.getMetricDescription());
		    }
		    
		    System.out.println(allMetricDescriptions.size());
		    
		    EList<MetricDescription> nonMatchingMetricDesciptions = new BasicEList<MetricDescription>();
		    
		    for (MetricDescription allMetricDesc : allMetricDescriptions) {
		        for (MetricDescription metricDescs : metricDescInMonitor) {
	                if(!allMetricDesc.getName().equals(metricDescs.getName())) {
	                    nonMatchingMetricDesciptions.add(allMetricDesc);
	                }
	            }
            }
		    
		    System.out.println(nonMatchingMetricDesciptions.size());
		    
		    Monitor tempMon = MonitorRepositoryPackage.eINSTANCE.getMonitorRepositoryFactory().createMonitor();
		    for (MetricDescription desc : nonMatchingMetricDesciptions) {
                editor.addMeasurementSpecification(tempMon);
                editor.setMetricDescription(tempMon.getMeasurementSpecifications().get(0), desc);
                
            }
		}
		
		    
		
		    


		
		System.out.println("Test");
		
		
		
		
		
		
		
		
		
		
		
//		
//		/**
//		 * Test of Deleting an Object
//		 */
//		start.loadData(0);
//		Monitor aMon = start.getModelAccessor().getMonitorRepository().get(0).getMonitors().get(0);	
//		
//		
//		
//        EList<EStructuralFeature> test = aMon.eClass().getEAllStructuralFeatures();
//		EList<MeasurementSpecification> mSpecs = aMon.getMeasurementSpecifications();
//        
//		System.out.println("Size: "+mSpecs.size());
////		mSpecs.add(MonitorRepositoryPackage.eINSTANCE.getMonitorRepositoryFactory().createMeasurementSpecification());
//		editor.addMeasurementSpecification(aMon);
//		System.out.println("Size: "+mSpecs.size());
//		
//		for (MeasurementSpecification aMSpec: mSpecs) {
////		    System.out.println(aMSpec.getName());
//		}
//		
////		EList<MetricDescription> test = aMSpec.getMetricDescription().getRepository().getMetricDescriptions();
//////		EList<EStructuralFeature> allFeatures = aMSpec.eClass().getEAllStructuralFeatures();
////		System.out.println(aMSpec.getMetricDescription().getName());
////		editor.setMetricDescription(aMSpec, test.get(0));
////		System.out.println(aMSpec.getMetricDescription().getName());
////		for (MetricDescription aTest: test) {
////			System.out.println("Name: "+aTest.getName());
////			System.out.println("Tex Desc: "+aTest.getTextualDescription());
////		}
//			
		
//		System.out.println("Test");
//		System.out.println("Length: "+ start.getModelAccessor().getMonitorRepository().get(0).getMonitors().size());
//		//EcoreUtil.delete(aMon); This leads to java.lang.IllegalStateException: Cannot modify resource set without a write transaction
//		editor.deleteResource(aMon);
//		System.out.println("Length: "+ start.getModelAccessor().getMonitorRepository().get(0).getMonitors().size());
//
		
//		List<IProject> projects = gatherer.getAllProjectAirdfiles();
//		for(IProject eachProject : projects) {
//			System.out.println("Project Name: "+eachProject.getName());
//			System.out.println("Project Aird Path: "+gatherer.getAirdFile(eachProject));
//		}
//		
//		System.out.println("Testing .monitorrepository return: "+ gatherer.getChosenFile(gatherer.getAllProjectAirdfiles().get(0), "monitorrepository"));
//		
//
//		MonitorRepository monRep = start.getModelAccessor().getMonitorRepository().get(0);
//		MeasuringPointRepository mpRep = start.getModelAccessor().getMeasuringPointRpository().get(0);
//		Monitor mon1 = monRep.getMonitors().get(0);
//		Monitor mon2 = monRep.getMonitors().get(1);
//
//		MeasuringPoint mp1 = mpRep.getMeasuringPoints().get(0);
//		System.out.println("length: "+ mpRep.getMeasuringPoints().size());
////		editor.addMeasuringPoint(mpRep, creator.createResourceURIMeasuringPoint("123gfsda"));
//		System.out.println("length: "+ mpRep.getMeasuringPoints().size());
//		for(MeasuringPoint mp : mpRep.getMeasuringPoints()) {
//			System.out.println(mp.getResourceURIRepresentation());
//		}
		return null;
	}

	public static int test(String test) {
		if (test.equals("test1")) {
			return 1;
		} 
		return 0;
	}
}
