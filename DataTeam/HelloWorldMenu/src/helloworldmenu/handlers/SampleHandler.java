package helloworldmenu.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepository;


import dataManipulation.DataEditor;
import init.StartApplication;



public class SampleHandler extends AbstractHandler {

	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		
		StartApplication start = StartApplication.getInstance();
		
		start.startApplication();
		MonitorRepository repo = start.getModelAccessor().getMonitorRepository().get(0);
		System.out.println("monitor name:"+ repo.getEntityName());
		System.out.println(repo.getId());
		DataEditor.doEditing(repo, "entityName", "Parent2Monitor");
		System.out.println("monitor name:"+ repo.getEntityName());
		System.out.println(repo.getId());
		
		 Monitor mp1 = start.getModelAccessor().getMonitorRepository().get(0).getMonitors().get(0);
		 System.out.println("monitor name:"+ mp1.getEntityName());
		 DataEditor.doEditing(mp1, "entityName", "childMonitor");
		 System.out.println("monitor name:"+ mp1.getEntityName());

		
		
		
		
		
		// test for any available session
//		for (Session session : SessionManager.INSTANCE.getSessions()) {
//			System.out.println(session.getID());
//			for (Resource resource : session.getSemanticResources()) {
//				System.out.println(resource.getURI());
//				System.out.println(resource.toString());
//			}
//		}


			
		return null;
	}
}
