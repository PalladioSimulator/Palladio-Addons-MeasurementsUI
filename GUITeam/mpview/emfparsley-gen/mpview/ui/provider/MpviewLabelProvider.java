package mpview.ui.provider;

import com.google.inject.Inject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.parsley.ui.provider.ViewerLabelProvider;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.monitorrepository.MeasurementSpecification;
import org.palladiosimulator.monitorrepository.Monitor;

@SuppressWarnings("all")
public class MpviewLabelProvider extends ViewerLabelProvider {
  @Inject
  public MpviewLabelProvider(final AdapterFactoryLabelProvider delegate) {
    super(delegate);
  }
  
  public String text(final Monitor mon) {
    return mon.getEntityName();
  }
  
  public String text(final MeasuringPoint mp) {
    return mp.getStringRepresentation();
  }
  
  public String text(final MeasurementSpecification ms) {
    return ms.getName();
  }
  
  public Object image(final Monitor mon) {
    String _xifexpression = null;
    boolean _isActivated = mon.isActivated();
    if (_isActivated) {
      _xifexpression = "ActiveIcon.png";
    } else {
      _xifexpression = "InactiveIcon.png";
    }
    return _xifexpression;
  }
  
  public Object image(final MeasurementSpecification ms) {
    String _xifexpression = null;
    boolean _isTriggersSelfAdaptations = ms.isTriggersSelfAdaptations();
    if (_isTriggersSelfAdaptations) {
      _xifexpression = "self_adaption.png";
    } else {
      _xifexpression = "nonself_adaption.png";
    }
    return _xifexpression;
  }
}
