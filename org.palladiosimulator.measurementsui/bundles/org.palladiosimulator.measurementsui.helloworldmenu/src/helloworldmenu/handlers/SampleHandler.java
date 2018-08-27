package helloworldmenu.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.datacreation.DataMeasuringCreator;
import org.palladiosimulator.measurementsui.datamanagement.DataGathering;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.measurementsui.init.DataApplication;
import org.palladiosimulator.monitorrepository.Monitor;
import org.palladiosimulator.monitorrepository.MonitorRepository;



public class SampleHandler extends AbstractHandler {


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {


		DataApplication start = DataApplication.getInstance();
		ResourceEditor editor = new ResourceEditor();
		DataMeasuringCreator creator = new DataMeasuringCreator();
		DataGathering gatherer = new DataGathering();

		List<IProject> projects = gatherer.getAllProjectAirdfiles();
		for(IProject eachProject : projects) {
			System.out.println("Project Name: "+eachProject.getName());
			System.out.println("Project Aird Path: "+gatherer.getAirdFile(eachProject));
		}
		
		System.out.println("Testing .monitorrepository return: "+ gatherer.getChosenFile(gatherer.getAllProjectAirdfiles().get(0), "monitorrepository"));
		

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
		return null;
	}

	public static int test(String test) {
		if (test.equals("test1")) {
			return 1;
		} 
		return 0;
	}
}
