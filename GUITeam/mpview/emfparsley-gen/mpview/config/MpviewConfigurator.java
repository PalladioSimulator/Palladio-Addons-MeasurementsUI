package mpview.config;

import mpview.MpviewSaveableTreeFormView;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.parsley.config.Configurator;

@SuppressWarnings("all")
public class MpviewConfigurator extends Configurator {
  public URI resourceURI(final MpviewSaveableTreeFormView it) {
    return URI.createFileURI("D:/Benutzer/David/Dokumente/Uni/EnPro/runtime-EclipseApplication/Pets.com/PetsMonitor.monitorrepository");
  }
}
