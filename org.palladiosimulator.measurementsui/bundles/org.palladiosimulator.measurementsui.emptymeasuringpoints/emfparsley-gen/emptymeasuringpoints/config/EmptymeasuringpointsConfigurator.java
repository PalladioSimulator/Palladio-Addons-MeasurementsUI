package emptymeasuringpoints.config;

import emptymeasuringpoints.EmptymeasuringpointsSaveableTreeView;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.parsley.config.Configurator;

@SuppressWarnings("all")
public class EmptymeasuringpointsConfigurator extends Configurator {
  public URI resourceURI(final EmptymeasuringpointsSaveableTreeView it) {
    return URI.createFileURI("/Users/zss2/runtime-EclipseApplication/Pets.com/PetsMeasuringPoint.measuringpoint");
  }
}