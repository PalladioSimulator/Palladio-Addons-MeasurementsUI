package emptymeasuringpoints;

import com.google.inject.Injector;
import emptymeasuringpoints.EmptymeasuringpointsInjectorProvider;
import org.eclipse.emf.parsley.runtime.ui.AbstractGuiceAwareExecutableExtensionFactory;

@SuppressWarnings("all")
public class EmptymeasuringpointsExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
  @Override
  public Injector getInjector() throws Exception {
    return EmptymeasuringpointsInjectorProvider.getInjector();
  }
}
