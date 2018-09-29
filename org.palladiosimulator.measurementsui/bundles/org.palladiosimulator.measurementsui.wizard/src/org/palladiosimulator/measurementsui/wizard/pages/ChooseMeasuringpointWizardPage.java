package org.palladiosimulator.measurementsui.wizard.pages;

import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPoint;
import org.palladiosimulator.edp2.models.measuringpoint.MeasuringPointRepository;
import org.palladiosimulator.measurementsui.wizard.handlers.contentprovider.AlternativeMeasuringPointContentProvider;
import org.palladiosimulator.measurementsui.wizard.handlers.contentprovider.ExistingMeasuringpointContentProvider;
import org.palladiosimulator.measurementsui.wizard.handlers.contentprovider.MeasuringPointsContentProvider;
import org.palladiosimulator.measurementsui.wizard.handlers.labelprovider.AlternativeMeasuringPointLabelProvider;
import org.palladiosimulator.measurementsui.wizard.handlers.labelprovider.ExistingMeasuringpointLabelProvider;
import org.palladiosimulator.measurementsui.wizard.handlers.labelprovider.MeasuringPointsLabelProvider;
import org.palladiosimulator.measurementsui.wizardmodel.pages.MeasuringPointSelectionWizardModel;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

/**
 * This is the wizard page for the first step of the creation of a measuring
 * point. It creates all necessary ui elements like the page itself, tabs and
 * tree viewers. It provides function to dynamically show info texts,
 * dynamically choose the next wizard page
 * 
 * @author Domas Mikalkinas
 *
 */

public class ChooseMeasuringpointWizardPage extends WizardPage {

	private TreeViewer emptyMeasuringpointViewer;
	private TreeViewer createTreeViewer;

	private MeasuringPointSelectionWizardModel selectionWizardModel;
	private boolean selected = false;
	private boolean validatedNextPressed;

	private TabFolder tabFolder;
	Button[] radios = new Button[2];

	String filterString;

	/**
	 * Constructor for the second wizard page, sets structural features like the
	 * title, the name of the page and the description
	 * 
	 * @param selectionWizardModel
	 *            the needed wizard model
	 */
	public ChooseMeasuringpointWizardPage(MeasuringPointSelectionWizardModel selectionWizardModel) {
		super("Second Page");
		this.selectionWizardModel = selectionWizardModel;
		setTitle(selectionWizardModel.getTitleText());
		setDescription(selectionWizardModel.getInfoText());
	}

	/**
	 * 
	 * Creates the second page of the wizard, which shows a tabbed table of
	 * measuringpoints, either existing or those which can be created
	 * 
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		container.setLayout(layout);
		setControl(container);

		tabFolder = new TabFolder(container, SWT.SINGLE);

		createNewMeasuringPointTab();

		createExistingMeasuringPointTab(layout);

		setSelectedItem(selectionWizardModel.isEditing());
		this.setPageComplete(true);
	}

	/**
	 * Creates the tab for showing all models from which you can derive and create a
	 * new measuring point
	 * 
	 * @param layout
	 *            the layout for the tab
	 * 
	 */
	private void createNewMeasuringPointTab() {
		Composite all = new Composite(tabFolder, SWT.NONE);
		Composite buttonComposite = new Composite(all, SWT.NONE);
		buttonComposite.setLayout(new GridLayout(3, false));
		TabItem createMeasuringTabbedItem = new TabItem(tabFolder, SWT.NONE);
		createMeasuringTabbedItem.setText("Create new measuring point");
		GridData parentData = new GridData(SWT.FILL, SWT.FILL, true, true);
		all.setLayout(new GridLayout(1, false));
		all.setLayoutData(parentData);

		Composite createMPcomposite = new Composite(all, SWT.SINGLE);
		createMPcomposite.setLayout(new GridLayout(1, true));
		createMPcomposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		radios[0] = new Button(buttonComposite, SWT.RADIO);
		radios[0].setSelection(true);
		radios[0].setText("Hierarchical");
		radios[0].setBounds(10, 5, 75, 30);

		radios[1] = new Button(buttonComposite, SWT.RADIO);
		radios[1].setText("Flat");
		radios[1].setBounds(10, 30, 75, 30);
		radios[0].addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				changeViewToHierarchical();
			}

		});
		radios[1].addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				changeViewToFlat();
			}

		});
		AlternativeMeasuringPointContentProvider createContentProvider = new AlternativeMeasuringPointContentProvider();
		PatternFilter filter = new PatternFilter();
		FilteredTree tree = new FilteredTree(createMPcomposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL, filter, true);
		createTreeViewer = tree.getViewer();
		createTreeViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createTreeViewer.setContentProvider(createContentProvider);
		createTreeViewer.setInput(selectionWizardModel.getAllSecondPageObjects());

		createTreeViewer.setLabelProvider(new AlternativeMeasuringPointLabelProvider());

		createTreeViewer.getTree().getItem(0).setExpanded(true);
		createTreeViewer.refresh();
		createTreeViewer.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (filterString != null && !filterString.isEmpty()) {
					if (element instanceof NamedElement) {
						NamedElement d = (NamedElement) element;
						return d.getEntityName().toLowerCase().startsWith(filterString.toLowerCase());
					} else if (element instanceof UsageModel) {
						UsageModel d = (UsageModel) element;
						return d.toString().toLowerCase().startsWith(filterString.toLowerCase());
					}
				}
				return true;
			}
		});
		createTreeViewer.getTree().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				TreeItem item = (TreeItem) e.item;

				if (isMeasuringPointCreatable(item.getData())) {
					selectionWizardModel.setFinishable(true);
					validatedNextPressed = true;
					getContainer().updateButtons();
					setPageComplete(true);
					showMessage(item);

				}

				else {
					selectionWizardModel.setFinishable(false);
					validatedNextPressed = false;
					setPageComplete(false);
					getContainer().updateButtons();
					setErrorMessage("Choose a model for which a measuring point will be created.");
				}

			}

		});
		
		createTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				nextPressed();
				getContainer().showPage( getNextPage());
				 
				
			}
		});
		createMeasuringTabbedItem.setControl(all);

	}

	/**
	 * Creates the tab which shows all existing measuring points.
	 * 
	 * @param layout
	 *            the layout for the tab
	 */
	private void createExistingMeasuringPointTab(FillLayout layout) {
		TabItem existingMeasuringTabbedItem = new TabItem(tabFolder, SWT.SINGLE);
		existingMeasuringTabbedItem.setText("Select existing measuring point");
		Composite all = new Composite(tabFolder, SWT.NONE);
		GridData parentData = new GridData(SWT.FILL, SWT.FILL, true, true);
		all.setLayout(new GridLayout(1, false));
		all.setLayoutData(parentData);
		Composite buttonComposite = new Composite(all, SWT.NONE);
		Label label = new Label(buttonComposite, SWT.CENTER);
		label.setText("             ");
		label.setBounds(10, 5, 75, 30);
		buttonComposite.setLayout(new GridLayout(1, false));
		Composite existingMPcomposite = new Composite(all, SWT.SINGLE);
		existingMPcomposite.setLayout(new GridLayout(1, true));
		existingMPcomposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		PatternFilter filter = new PatternFilter();
		FilteredTree tree = new FilteredTree(existingMPcomposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL, filter,
				true);
		emptyMeasuringpointViewer = tree.getViewer();
		emptyMeasuringpointViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		emptyMeasuringpointViewer.setContentProvider(new ExistingMeasuringpointContentProvider(selectionWizardModel));
		emptyMeasuringpointViewer.setInput(selectionWizardModel.getExistingMeasuringPoints());
		emptyMeasuringpointViewer.setLabelProvider(new ExistingMeasuringpointLabelProvider());
		emptyMeasuringpointViewer.refresh();

		emptyMeasuringpointViewer.getTree().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				TreeItem item = (TreeItem) e.item;

				if (item.getData() instanceof MeasuringPoint) {

					selectionWizardModel.setFinishable(true);
					validatedNextPressed = true;
					getContainer().updateButtons();
					setPageComplete(true);
					showMessage(item);
				}

			}
		});
		emptyMeasuringpointViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				nextPressed();
				getContainer().showPage( getNextPage());
				 
				
			}
		});
		existingMeasuringTabbedItem.setControl(all);
	}

	/**
	 * overrides the getNextPage() method of the wizard page to allow a dynamic flow
	 * of wizard pages, depending on the selected item
	 */
	@Override
	public org.eclipse.jface.wizard.IWizardPage getNextPage() {
		boolean isNextPressed = "nextPressed"
				.equalsIgnoreCase(Thread.currentThread().getStackTrace()[2].getMethodName());
		if (isNextPressed) {
			boolean validatedNextPress = this.nextPressed();
			if (!validatedNextPress) {
				return this;
			}
		}
		if (selected) {

			AdditionalModelsToMeasuringpointWizardPage page = (AdditionalModelsToMeasuringpointWizardPage) super.getWizard()
					.getPage("page2extra");
			page.loadData();
			return page;

		} else {

			return super.getNextPage();
		}

	}

	/**
	 * shows an informative message on the top, depending on the chosen element.
	 * 
	 * @param item
	 *            the selected item
	 */
	public void showMessage(TreeItem item) {
		this.setErrorMessage(null);
		if (item.getData() instanceof MeasuringPoint) {
			this.setMessage("This is an existing measuring point.");
		} else if (item.getData() instanceof ResourceEnvironment) {
			this.setMessage("This will create a resource environment measuring point.");
		} else if (item.getData() instanceof ResourceContainer) {
			this.setMessage("This will create a resource container measuring point.");
		} else if (item.getData() instanceof ProcessingResourceSpecification) {
			this.setMessage("This will create an active resource measuring point.");
		} else if (item.getData() instanceof AssemblyContext) {
			this.setMessage(
					"This will create either an assembly operation or an assembly passive resource measuring point. In the next step you will need to specify further components.");
		} else if (item.getData() instanceof EntryLevelSystemCall) {
			this.setMessage("This will create an entry level system call measuring point.");
		} else if (item.getData() instanceof ExternalCallAction) {
			this.setMessage("This will create an external call action measuring point.");
		} else if (item.getData() instanceof LinkingResource) {
			this.setMessage("This will create a linking resource measuring point.");
		} else if (item.getData() instanceof SubSystem) {
			this.setMessage(
					"This will create a subsystem measuring point. In the next step you will need to specify further components.");
		} else if (item.getData() instanceof org.palladiosimulator.pcm.system.System) {
			this.setMessage(
					"This will create a system measuring point. In the next step you will need to specify further components.");
		} else if (item.getData() instanceof UsageScenario) {
			this.setMessage("This will create a usage scenario measuring point.");
		} else {
			this.setMessage(selectionWizardModel.getInfoText());
		}

	}

	/**
	 * @see WizardDialog#nextPressed()
	 * @see WizardPage#getNextPage()
	 * @return boolean validates whether the next button is pressed or not
	 */
	protected boolean nextPressed() {
		validatedNextPressed = true;
		validatedNextPressed = performAddingOperations(validatedNextPressed);
		return validatedNextPressed;
	}

	public boolean performAddingOperations(boolean validatedNextPressed) {
		getContainer().updateButtons();
		try {

			if (tabFolder.getSelectionIndex() == 1) {
				if (emptyMeasuringpointViewer.getStructuredSelection().getFirstElement() instanceof MeasuringPoint) {
					setErrorMessage(null);
					setPageComplete(true);
					selectionWizardModel.setMeasuringPointDependingOnEditMode(
							(MeasuringPoint) emptyMeasuringpointViewer.getStructuredSelection().getFirstElement());
				}
			} else {
				if (createTreeViewer.getStructuredSelection().getFirstElement() instanceof AssemblyContext
						|| createTreeViewer.getStructuredSelection().getFirstElement() instanceof SubSystem
						|| createTreeViewer.getStructuredSelection()
								.getFirstElement() instanceof org.palladiosimulator.pcm.system.System) {
					selectionWizardModel
							.setCurrentSelection(createTreeViewer.getStructuredSelection().getFirstElement());
					selected = true;
				} else {
					selectionWizardModel
							.setCurrentSelection(createTreeViewer.getStructuredSelection().getFirstElement());
					selectionWizardModel
							.createMeasuringPoint(createTreeViewer.getStructuredSelection().getFirstElement());
					selected = false;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return validatedNextPressed;
	}

	private void changeViewToHierarchical() {
		AlternativeMeasuringPointLabelProvider createLabelProvider = new AlternativeMeasuringPointLabelProvider();
		createTreeViewer.setLabelProvider(createLabelProvider);
		AlternativeMeasuringPointContentProvider createContentProvider = new AlternativeMeasuringPointContentProvider();
		createTreeViewer.setContentProvider(createContentProvider);

		createTreeViewer.getTree().update();

	}

	private void changeViewToFlat() {
		MeasuringPointsContentProvider createContentProvider = new MeasuringPointsContentProvider(selectionWizardModel);
		createTreeViewer.setContentProvider(createContentProvider);
		MeasuringPointsLabelProvider createLabelProvider = new MeasuringPointsLabelProvider();
		createTreeViewer.setLabelProvider(createLabelProvider);
		createTreeViewer.getTree().update();
	}

	private void setSelectedItem(boolean isEditing) {
		if (isEditing) {
			tabFolder.setSelection(1);

			ISelection selection = new StructuredSelection(selectionWizardModel.getMonitor().getMeasuringPoint());

			emptyMeasuringpointViewer.setSelection(selection);
			emptyMeasuringpointViewer.refresh();

		} else if (selectionWizardModel.getAllSecondPageObjects().length != 0) {

			LinkedList<?> temp = (LinkedList<?>) selectionWizardModel.getAllSecondPageObjects()[0];
			Iterator iter = temp.iterator();
			while (iter.hasNext()) {
				if (isMeasuringPointCreatable(iter.next())) {
					ISelection selection = new StructuredSelection(temp.get(0));
					createTreeViewer.setSelection(selection);
					break;
				}
			}

		}
	}

	private boolean isMeasuringPointCreatable(Object item) {
		if (item instanceof MeasuringPoint || item instanceof ResourceEnvironment || item instanceof ResourceContainer
				|| item instanceof ProcessingResourceSpecification || item instanceof AssemblyContext
				|| item instanceof AssemblyContext || item instanceof EntryLevelSystemCall
				|| item instanceof ExternalCallAction || item instanceof LinkingResource || item instanceof SubSystem
				|| item instanceof org.palladiosimulator.pcm.system.System || item instanceof UsageScenario) {
			return true;

		} else {
			return false;
		}
	}

}
