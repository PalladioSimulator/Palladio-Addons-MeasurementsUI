package emptymeasuringpoints;

import javax.inject.Inject;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.parsley.edit.ui.provider.ViewerContentProvider;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;

/**
 * 
 * @author Lasse Merz
 *
 */
public class CustomContentProvider extends ViewerContentProvider {

    @Inject
    public CustomContentProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);

    }

    public Object elements(MeasuringPointRepository measuringPointRepository) {

        return measuringPointRepository;
    }

    public Object children(MeasuringPointRepository measuringPointRepository) {

        return DataApplication.getInstance().getModelAccessor().getUnassignedMeasuringPoints();
    }

    public Object children(MeasuringPoint measuringPoint) {
        return null;
    }
}
