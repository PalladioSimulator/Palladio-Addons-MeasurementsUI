package emptymeasuringpointswizard;

import org.osgi.framework.BundleContext;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class EmptymeasuringpointsActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "emptymeasuringpoints"; //$NON-NLS-1$

    // The shared instance
    private static EmptymeasuringpointsActivator plugin;

    public EmptymeasuringpointsActivator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static EmptymeasuringpointsActivator getDefault() {
        return plugin;
    }

}
