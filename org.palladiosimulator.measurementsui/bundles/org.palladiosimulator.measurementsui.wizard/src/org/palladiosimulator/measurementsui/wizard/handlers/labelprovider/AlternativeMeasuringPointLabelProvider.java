package org.palladiosimulator.measurementsui.wizard.handlers.labelprovider;

import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.palladiosimulator.measurementsui.wizard.util.ImageProviderSwitch;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.subsystem.SubSystem;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

/**
 * An alternative label provider for the first step of the measuringpoint creation wizard pages
 * 
 * @author Domas Mikalkinas
 *
 */
public class AlternativeMeasuringPointLabelProvider implements ILabelProvider, IFontProvider {

    private static final String TRANSIENT = "[TRANSIENT]";

    @Override
    public void addListener(ILabelProviderListener listener) {
        // not needed
    }

    @Override
    public void dispose() {
        // not needed
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        // not needed
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
        // not needed
    }

    @Override
    public Image getImage(Object element) {
        ImageProviderSwitch imageSwitch = new ImageProviderSwitch();
        if (element instanceof LinkedList) {
            return null;
        } else {

            return imageSwitch.doSwitch((EObject) element);

        }

    }

    @Override
    public String getText(Object element) {
        if (element instanceof LinkedList) {
            if (!((LinkedList<?>) element).isEmpty()) {
                return (((LinkedList<?>) element).get(0).getClass().getSimpleName().replaceAll("Impl", "")
                        .replaceAll("([A-Z])", " $1"));
            } else {
                return null;
            }

        } else {
            if (element instanceof ProcessingResourceSpecification) {
                return ((ProcessingResourceSpecification) element).getActiveResourceType_ActiveResourceSpecification()
                        .getEntityName();

            } else if (element instanceof ResourceDemandingSEFF) {
                return ((ResourceDemandingSEFF) element).toString().replace(TRANSIENT, "");
            } else if (element instanceof UsageModel) {
                return ((UsageModel) element).toString().replace(TRANSIENT, "");

            } else if (element instanceof BranchTransition) {
                return ((BranchTransition) element).toString().replace(TRANSIENT, "");
            } else {
                return ((NamedElement) element).getEntityName();
            }

        }

    }

    @Override
    public Font getFont(Object element) {
        if (element instanceof ResourceEnvironment || element instanceof ResourceContainer
                || element instanceof ProcessingResourceSpecification || element instanceof AssemblyContext
                || element instanceof EntryLevelSystemCall || element instanceof ExternalCallAction
                || element instanceof LinkingResource || element instanceof SubSystem || element instanceof System
                || element instanceof UsageScenario) {

            return JFaceResources.getBannerFont();
        }
        return null;
    }

}
