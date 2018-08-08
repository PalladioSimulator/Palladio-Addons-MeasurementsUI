package emptymeasuringpoints;

import emptymeasuringpoints.config.EmptymeasuringpointsConfigurator;
import emptymeasuringpoints.edit.ui.provider.EmptymeasuringpointsViewerContentProvider;
import emptymeasuringpoints.resource.EmptymeasuringpointsResourceManager;
import org.eclipse.emf.parsley.EmfParsleyGuiceModule;
import org.eclipse.emf.parsley.config.Configurator;
import org.eclipse.emf.parsley.resource.ResourceManager;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * emptymeasuringpoints EMF Parsley Dsl Module file
 */
@SuppressWarnings("all")
public class EmptymeasuringpointsEmfParsleyGuiceModule extends EmfParsleyGuiceModule {
  public EmptymeasuringpointsEmfParsleyGuiceModule(final AbstractUIPlugin plugin) {
    super(plugin);
  }
  
  @Override
  public Class<? extends IContentProvider> bindIContentProvider() {
    return EmptymeasuringpointsViewerContentProvider.class;
  }
  
  @Override
  public Class<? extends Configurator> bindConfigurator() {
    return EmptymeasuringpointsConfigurator.class;
  }
  
  @Override
  public Class<? extends ResourceManager> bindResourceManager() {
    return EmptymeasuringpointsResourceManager.class;
  }
}
