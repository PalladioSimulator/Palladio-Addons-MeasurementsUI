package mpview;

import com.google.inject.Guice;
import com.google.inject.Injector;
import mpview.MpviewEmfParsleyGuiceModule;
import org.eclipse.emf.parsley.runtime.ui.PluginUtil;

@SuppressWarnings("all")
public class MpviewInjectorProvider {
  private static Injector injector;
  
  public static synchronized Injector getInjector() {
    if (injector == null) {
      injector = Guice.createInjector(
        new MpviewEmfParsleyGuiceModule(PluginUtil.getPlugin(
          PluginUtil.getBundle(MpviewInjectorProvider.class))));
    }
    return injector;
  }
}
