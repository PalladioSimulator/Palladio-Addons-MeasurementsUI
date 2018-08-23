package wizard;

import de.unistuttgart.enpro.wizard.handlers.ChooseMeasuringpointWizardPage;

public class Wizard extends org.eclipse.jface.wizard.Wizard {

    private ChooseMeasuringpointWizardPage page1 = new ChooseMeasuringpointWizardPage();
    

    public Wizard() {
        setWindowTitle("Enpro Wizard");

    }

    @Override
    public void addPages() {
        addPage(page1);
      
    }

    @Override
    public boolean performFinish() {
        return false;
    }
}