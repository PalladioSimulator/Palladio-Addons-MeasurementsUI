package emptymeasuringpoints;

import com.google.inject.Guice;
import com.google.inject.Injector;
import emptymeasuringpoints.EmptymeasuringpointsEmfParsleyGuiceModule;
import org.eclipse.emf.parsley.runtime.ui.PluginUtil;

@SuppressWarnings("all")
public class EmptymeasuringpointsInjectorProvider {
  private static Injector injector;
  
  public static synchronized Injector getInjector() {
    if (injector == null) {
      injector = Guice.createInjector(
        new EmptymeasuringpointsEmfParsleyGuiceModule(PluginUtil.getPlugin(
          PluginUtil.getBundle(EmptymeasuringpointsInjectorProvider.class))));
    }
    return injector;
  }
}
