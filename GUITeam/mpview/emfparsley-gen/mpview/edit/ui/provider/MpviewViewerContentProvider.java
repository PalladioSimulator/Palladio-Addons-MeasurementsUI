package mpview.edit.ui.provider;

import com.google.inject.Inject;
import java.util.ArrayList;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.parsley.edit.ui.provider.ViewerContentProvider;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.palladiosimulator.monitorrepository.Monitor;

@SuppressWarnings("all")
public class MpviewViewerContentProvider extends ViewerContentProvider {
  @Inject
  public MpviewViewerContentProvider(final AdapterFactory adapterFactory) {
    super(adapterFactory);
  }
  
  public Object children(final Monitor mon) {
    ArrayList<CDOObject> _arrayList = new ArrayList<CDOObject>();
    final Procedure1<ArrayList<CDOObject>> _function = (ArrayList<CDOObject> it) -> {
      it.add(mon.getMeasuringPoint());
      it.addAll(mon.getMeasurementSpecifications());
    };
    return ObjectExtensions.<ArrayList<CDOObject>>operator_doubleArrow(_arrayList, _function);
  }
}
