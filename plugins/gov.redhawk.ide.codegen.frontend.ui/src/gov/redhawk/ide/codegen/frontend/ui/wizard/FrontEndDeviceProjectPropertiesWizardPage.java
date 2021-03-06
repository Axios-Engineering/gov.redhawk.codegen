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
package gov.redhawk.ide.codegen.frontend.ui.wizard;

import gov.redhawk.ide.dcd.ui.wizard.ScaDeviceProjectPropertiesWizardPage;


public class FrontEndDeviceProjectPropertiesWizardPage extends ScaDeviceProjectPropertiesWizardPage {

	protected FrontEndDeviceProjectPropertiesWizardPage(String pageName, String type) {
		super(pageName, type);
		setShowContentsGroup(false);
		setShowDeviceGroup(false);
	}
	
}
