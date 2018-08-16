package emptymeasuringpoints;

import init.StartApplication;
import javax.inject.Inject;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.parsley.edit.ui.provider.ViewerContentProvider;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;

public class CustomContentProvider extends ViewerContentProvider {
	
	StartApplication app;
	
	@Inject
	public CustomContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
		this.app = StartApplication.getInstance();
		
	}

	public Object elements(MeasuringPointRepository measuringPointRepository) {
		
		return measuringPointRepository;
	}

	public Object children(MeasuringPointRepository measuringPointRepository) {

		app.startApplication();
		EList<MeasuringPoint> measuringPoints = app.getModelAccessor().getUnassignedMeasuringPoints();
		System.out.println(measuringPoints);
			
		
		return measuringPoints;
	}

	public Object children(MeasuringPoint measuringPoint) {
		return null;
	}
}
