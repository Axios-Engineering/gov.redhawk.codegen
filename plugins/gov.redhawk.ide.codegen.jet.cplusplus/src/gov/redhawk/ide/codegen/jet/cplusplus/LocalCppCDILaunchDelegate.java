/*******************************************************************************
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 *
 * This file is part of REDHAWK IDE.
 *
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package gov.redhawk.ide.codegen.jet.cplusplus;

import gov.redhawk.ide.debug.ScaLauncherUtil;
import gov.redhawk.sca.launch.ScaLaunchConfigurationConstants;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.debug.core.CDebugCorePlugin;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.ICDebugConfiguration;
import org.eclipse.cdt.launch.internal.LocalCDILaunchDelegate;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.emf.common.util.URI;

/**
 * @since 9.0
 * 
 */
public class LocalCppCDILaunchDelegate extends LocalCDILaunchDelegate implements ILaunchConfigurationDelegate {

	public static final String ID = "gov.redhawk.ide.codegen.jet.cplusplus.launchComponentApplication";

	/**
	 * 
	 */
	public LocalCppCDILaunchDelegate() {
	}

	@Override
	public void launch(final ILaunchConfiguration config, final String mode, final ILaunch launch, final IProgressMonitor monitor) throws CoreException {
		final ILaunchConfigurationWorkingCopy copy = config.getWorkingCopy();
		if (copy.getAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ID, (String) null) == null) {
			copy.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ID, getDefaultDebugger(config));
		}
		insertProgramArguments(launch, copy);
		super.launch(copy, mode, launch, monitor);
		ScaLauncherUtil.postLaunch(launch);
	}

	protected void insertProgramArguments(final ILaunch launch, final ILaunchConfigurationWorkingCopy configuration) throws CoreException {
		final String args = configuration.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, "");
		final URI spdURI = URI.createPlatformResourceURI(configuration.getAttribute(ScaLaunchConfigurationConstants.ATT_PROFILE, ""), true);
		final String newArgs = ScaLauncherUtil.getSpdProgramArguments(spdURI, launch, configuration);
		configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, args + " " + newArgs);
	}

	public String getDefaultDebugger(final ILaunchConfiguration config) throws CoreException {
		// Mostly from CDebbuggerTab
		String defaultDebugger = null;

		String projectName = config.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
		if (projectName.length() > 0) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			ICProjectDescription projDesc = CoreModel.getDefault().getProjectDescription(project);
			ICConfigurationDescription configDesc = projDesc.getActiveConfiguration();
			String configId = configDesc.getId();
			ICDebugConfiguration[] debugConfigs = CDebugCorePlugin.getDefault().getActiveDebugConfigurations();
			outer: for (int i = 0; i < debugConfigs.length; ++i) {
				ICDebugConfiguration debugConfig = debugConfigs[i];
				String[] patterns = debugConfig.getSupportedBuildConfigPatterns();
				if (patterns != null) {
					for (int j = 0; j < patterns.length; ++j) {
						if (configId.matches(patterns[j])) {
							defaultDebugger = debugConfig.getID();
							break outer;
						}
					}
				}
			}
		}


		if (defaultDebugger == null) {
			ICDebugConfiguration dc = CDebugCorePlugin.getDefault().getDefaultDebugConfiguration();
			if (dc != null) {
				defaultDebugger = dc.getID();
			}
		}

		return defaultDebugger;
	}
}
