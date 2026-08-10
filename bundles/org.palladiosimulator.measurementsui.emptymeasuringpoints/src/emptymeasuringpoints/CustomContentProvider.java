package emptymeasuringpoints;

import jakarta.inject.Inject;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.parsley.edit.ui.provider.ViewerContentProvider;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.dataprovider.DataApplication;

/**
 * This class is a custom version of the Content provider for a parsley view.
 * In this customization only the measuring Points who are not assigened to a monitor
 * are shown in the view.
 * 
 * @author Lasse Merz
 *
 */
public class CustomContentProvider extends ViewerContentProvider {

    @Inject
    public CustomContentProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);

    }

    /**
     * As children of our MeasuringPointRepository
     * there is a list with unassigned Measuring Points provided
     * as content.
     * 
     * @param measuringPointRepository
     * @return List of unassigned Measuring Points
     */
    public Object children(MeasuringPointRepository measuringPointRepository) {

        return DataApplication.getInstance().getModelAccessor().getUnassignedMeasuringPoints();
    }

}
