package mpview.edit.action;

import java.util.Collections;
import java.util.List;
import org.eclipse.emf.parsley.edit.action.EditingMenuBuilder;
import org.eclipse.emf.parsley.edit.action.IMenuContributionSpecification;
import org.eclipse.emf.parsley.runtime.util.IAcceptor;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.palladiosimulator.monitorrepository.MonitorRepository;

@SuppressWarnings("all")
public class MpviewMenuBuilder extends EditingMenuBuilder {
  public List<IMenuContributionSpecification> menuContributions(final MonitorRepository mr) {
    final IAcceptor<MonitorRepository> _function = (MonitorRepository it) -> {
      System.out.println("Hier kommt die Wizard anbindung");
    };
    IMenuContributionSpecification _actionChange = this.<MonitorRepository>actionChange("Add new Measuringpoint", mr, _function);
    return Collections.<IMenuContributionSpecification>unmodifiableList(CollectionLiterals.<IMenuContributionSpecification>newArrayList(_actionChange));
  }
}
