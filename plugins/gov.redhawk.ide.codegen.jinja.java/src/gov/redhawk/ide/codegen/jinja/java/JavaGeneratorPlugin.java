package gov.redhawk.ide.codegen.jinja.java;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class JavaGeneratorPlugin extends Plugin {

	public static final String PLUGIN_ID = "gov.redhawk.ide.codegen.jinja.java";

	private static BundleContext context;

	static BundleContext getContext() {
		return JavaGeneratorPlugin.context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		JavaGeneratorPlugin.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		JavaGeneratorPlugin.context = null;
	}

}
