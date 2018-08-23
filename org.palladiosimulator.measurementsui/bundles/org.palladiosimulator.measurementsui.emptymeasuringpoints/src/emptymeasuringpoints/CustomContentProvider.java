package emptymeasuringpoints;


import javax.inject.Inject;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.parsley.edit.ui.provider.ViewerContentProvider;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;

import init.DataApplication;

public class CustomContentProvider extends ViewerContentProvider {
	
	
	@Inject
	public CustomContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
		
	}

	public Object elements(MeasuringPointRepository measuringPointRepository) {
		
		return measuringPointRepository;
	}

	public Object children(MeasuringPointRepository measuringPointRepository) {

		EList<MeasuringPoint> measuringPoints = DataApplication.getInstance().getModelAccessor().getUnassignedMeasuringPoints();
		System.out.println(measuringPoints);
			
		
		return measuringPoints;
	}

	public Object children(MeasuringPoint measuringPoint) {
		return null;
	}
}
