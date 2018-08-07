package emptymeasuringpoints.resource;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.parsley.resource.ResourceManager;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringpointFactory;

@SuppressWarnings("all")
public class EmptymeasuringpointsResourceManager extends ResourceManager {
  @Override
  public void initialize(final Resource it) {
    EList<EObject> _contents = it.getContents();
    _contents.add(MeasuringpointFactory.eINSTANCE);
  }
}
