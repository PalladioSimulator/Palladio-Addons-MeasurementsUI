package emptymeasuringpoints.edit.ui.provider;

import com.google.inject.Inject;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.parsley.edit.ui.provider.ViewerContentProvider;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;

@SuppressWarnings("all")
public class EmptymeasuringpointsViewerContentProvider extends ViewerContentProvider {
  @Inject
  public EmptymeasuringpointsViewerContentProvider(final AdapterFactory adapterFactory) {
    super(adapterFactory);
  }
  
  public Object elements(final MeasuringPointRepository mpf) {
    EList<MeasuringPoint> _measuringPoints = mpf.getMeasuringPoints();
    return _measuringPoints;
  }
}
