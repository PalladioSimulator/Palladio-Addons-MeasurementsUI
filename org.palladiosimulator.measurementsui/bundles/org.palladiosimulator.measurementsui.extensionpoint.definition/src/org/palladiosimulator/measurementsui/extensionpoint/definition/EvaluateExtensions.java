package org.palladiosimulator.measurementsui.extensionpoint.definition;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class EvaluateExtensions {

    private static final String ID = "org.palladiosimulator.measurementsui.extensionpoint.definition.PossibleMetricDescription";


    public Object getElement(){


        IExtensionRegistry registry = Platform.getExtensionRegistry();

        IConfigurationElement[] config =
        registry.getConfigurationElementsFor(ID);
        try {
            for (IConfigurationElement e : config) {
                System.out.println("Evaluating extension");
                final Object o =
                        e.createExecutableExtension("class");
                
                System.out.println(o);
                return o;
            }

        } catch (CoreException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
       
    }
}