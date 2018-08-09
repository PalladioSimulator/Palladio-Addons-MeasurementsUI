package propertiespage;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.emf.parsley.runtime.ui.PluginUtil;
import propertiespage.PropertiespageEmfParsleyGuiceModule;

@SuppressWarnings("all")
public class PropertiespageInjectorProvider {
  private static Injector injector;
  
  public static synchronized Injector getInjector() {
    if (injector == null) {
      injector = Guice.createInjector(
        new PropertiespageEmfParsleyGuiceModule(PluginUtil.getPlugin(
          PluginUtil.getBundle(PropertiespageInjectorProvider.class))));
    }
    return injector;
  }
}
