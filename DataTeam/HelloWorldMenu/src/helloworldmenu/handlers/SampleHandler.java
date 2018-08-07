package helloworldmenu.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepository;

import DataCreation.DataMeasuringCreator;
import dataManipulation.ResourceEditor;
import init.StartApplication;



public class SampleHandler extends AbstractHandler {

	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		
		StartApplication start = StartApplication.getInstance();
		ResourceEditor editor = new ResourceEditor();
		DataMeasuringCreator creator = new DataMeasuringCreator();
		
		start.startApplication();
		
		 MonitorRepository monRep = start.getModelAccessor().getMonitorRepository().get(0);
		 MeasuringPointRepository mpRep = start.getModelAccessor().getMeasuringPointRpository().get(0);
		 Monitor mon1 = monRep.getMonitors().get(0);
		 Monitor mon2 = monRep.getMonitors().get(1);
		 
		 MeasuringPoint mp1 = mpRep.getMeasuringPoints().get(0);
		 System.out.println("length: "+ mpRep.getMeasuringPoints().size());
		 editor.addMeasuringPoint(mpRep, creator.createResourceURIMeasuringPoint("123gfsda"));
		 System.out.println("length: "+ mpRep.getMeasuringPoints().size());
		 for(MeasuringPoint mp : mpRep.getMeasuringPoints()) {
			 System.out.println(mp.getResourceURIRepresentation());
		 }
		 
//		 
//		 System.out.println("Monitor: "+ mon1.getEntityName());
//		 String oldName = mon1.getEntityName();
//		 editor.setResourceName(mon1, "dogMonitor");
//		 System.out.println("Monitor: "+ mon1.getEntityName());
//		 editor.setResourceName(mon1, oldName);
//		 
//		 
//		 
//		 System.out.println("Status: "+mon1.isActivated());
//		 editor.setMonitorActive(mon1);
//		 System.out.println("Status: "+mon1.isActivated());
//		 editor.setMonitorUnactive(mon1);
//		 System.out.println("Status: "+mon1.isActivated());
//		 
//		 System.out.println("Measuring Point1: "+ mon1.getMeasuringPoint());
//		 System.out.println("Measuring Point2: "+ mon2.getMeasuringPoint());
//		 MeasuringPoint oldMP = mon1.getMeasuringPoint();
//		 editor.setMeasuringPoint(mon1, mon2.getMeasuringPoint());
//		 System.out.println("Measuring Point1: "+ mon1.getMeasuringPoint());
//		 System.out.println("Measuring Point2: "+ mon2.getMeasuringPoint());
//		 editor.setMeasuringPoint(mon1, oldMP);
//		 System.out.println("Measuring Point1: "+ mon1.getMeasuringPoint());
//		 System.out.println("Measuring Point2: "+ mon2.getMeasuringPoint());	 
			
		return null;
	}
}
