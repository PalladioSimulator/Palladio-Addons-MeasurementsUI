package org.palladiosimulator.measurementsui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditor;
import org.palladiosimulator.measurementsui.datamanipulation.ResourceEditorImpl;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;
import org.palladiosimulator.measurementsui.fileaccess.DataGathering;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;




public class SampleHandler extends AbstractHandler {


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {


		DataApplication start = DataApplication.getInstance();
		ResourceEditor editor = ResourceEditorImpl.getInstance();
		DataGathering gatherer = new DataGathering();
		
		//start.loadData(0);
		
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
