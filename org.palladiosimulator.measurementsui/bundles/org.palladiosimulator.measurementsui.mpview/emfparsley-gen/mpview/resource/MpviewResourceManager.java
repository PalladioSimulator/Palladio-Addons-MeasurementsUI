package mpview.resource;

import java.io.IOException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.parsley.resource.ResourceManager;
import org.palladiosimulator.monitorrepository.MonitorRepositoryFactory;

@SuppressWarnings("all")
public class MpviewResourceManager extends ResourceManager {
  @Override
  public void initialize(final Resource it) {
    EList<EObject> _contents = it.getContents();
    _contents.add(MonitorRepositoryFactory.eINSTANCE);
  }
  
  @Override
  public boolean save(final Resource it) throws IOException {
    it.save(null);
    return true;
  }
}
