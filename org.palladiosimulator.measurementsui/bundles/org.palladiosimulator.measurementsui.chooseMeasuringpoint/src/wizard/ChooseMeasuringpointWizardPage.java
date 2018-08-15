package wizard;

import javax.inject.Inject;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.EmptyMpTreeViewer;
import org.palladiosimulator.simulizar.ui.measuringview.parts.controls.MpTreeViewer;

public class ChooseMeasuringpointWizardPage extends WizardPage {
	private Text text1;
	private Composite container;
	private Button btnCheckButton;
	MpTreeViewer emptyMpTreeViewer;

	@Inject
	MDirtyable dirty;

	@Inject
	ECommandService commandService;

	public ChooseMeasuringpointWizardPage() {
		super("Second Page");
		setTitle("Select Measuring Point");
		setDescription("description");
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();

		layout.marginHeight = 0;
		layout.marginWidth = 0;
		container.setLayout(layout);
		setControl(container);
		
		
		
		TabFolder tabFolder = new TabFolder(container, SWT.SINGLE);
		

		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.SINGLE);
		tbtmNewItem.setText("Select existing measuring point");

		Composite existingMPcomposite = new Composite(tabFolder, SWT.SINGLE);
		existingMPcomposite.setLayout(layout);

		emptyMpTreeViewer = createEmptyMpTreeViewer(existingMPcomposite);

		tbtmNewItem.setControl(existingMPcomposite);

		
		
		TabItem tbtmNewItem_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem_1.setText("Create new measuring point");

		Composite existingMPcomposite_1 = new Composite(tabFolder, SWT.SINGLE);
		existingMPcomposite_1.setLayout(layout);
		
		
		
		tbtmNewItem_1.setControl(existingMPcomposite_1);

		setPageComplete(true);
	}

	private MpTreeViewer createEmptyMpTreeViewer(Composite parent) {
		MpTreeViewer mpTreeViewer = new EmptyMpTreeViewer(parent, dirty, commandService);
		return mpTreeViewer;
	}

}
