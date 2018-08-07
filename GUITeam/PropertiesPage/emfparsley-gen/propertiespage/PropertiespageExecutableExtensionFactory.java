package propertiespage;

import com.google.inject.Injector;
import org.eclipse.emf.parsley.runtime.ui.AbstractGuiceAwareExecutableExtensionFactory;
import propertiespage.PropertiespageInjectorProvider;

@SuppressWarnings("all")
public class PropertiespageExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
  @Override
  public Injector getInjector() throws Exception {
    return PropertiespageInjectorProvider.getInjector();
  }
}
