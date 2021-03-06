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
package gov.redhawk.ide.codegen.jinja.preferences;

import gov.redhawk.ide.codegen.jinja.JinjaGeneratorPlugin;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		JinjaGeneratorPlugin.getDefault().getPreferenceAccessor().setDefault(JinjaPreferenceConstants.CODEGEN_PATH_PREFERENCE, "${OssieHome}/bin/redhawk-codegen");
	}

}
