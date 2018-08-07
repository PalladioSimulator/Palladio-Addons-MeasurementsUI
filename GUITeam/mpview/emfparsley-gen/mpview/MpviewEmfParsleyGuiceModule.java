package mpview;

import mpview.config.MpviewConfigurator;
import mpview.edit.action.MpviewMenuBuilder;
import mpview.edit.ui.provider.MpviewViewerContentProvider;
import mpview.resource.MpviewResourceManager;
import mpview.ui.provider.MpviewLabelProvider;
import org.eclipse.emf.parsley.EmfParsleyGuiceModule;
import org.eclipse.emf.parsley.config.Configurator;
import org.eclipse.emf.parsley.edit.action.EditingMenuBuilder;
import org.eclipse.emf.parsley.resource.ResourceManager;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * mpview EMF Parsley Dsl Module file
 */
@SuppressWarnings("all")
public class MpviewEmfParsleyGuiceModule extends EmfParsleyGuiceModule {
  public MpviewEmfParsleyGuiceModule(final AbstractUIPlugin plugin) {
    super(plugin);
  }
  
  @Override
  public Class<? extends ILabelProvider> bindILabelProvider() {
    return MpviewLabelProvider.class;
  }
  
  @Override
  public Class<? extends IContentProvider> bindIContentProvider() {
    return MpviewViewerContentProvider.class;
  }
  
  @Override
  public Class<? extends EditingMenuBuilder> bindEditingMenuBuilder() {
    return MpviewMenuBuilder.class;
  }
  
  @Override
  public Class<? extends Configurator> bindConfigurator() {
    return MpviewConfigurator.class;
  }
  
  @Override
  public Class<? extends ResourceManager> bindResourceManager() {
    return MpviewResourceManager.class;
  }
}
