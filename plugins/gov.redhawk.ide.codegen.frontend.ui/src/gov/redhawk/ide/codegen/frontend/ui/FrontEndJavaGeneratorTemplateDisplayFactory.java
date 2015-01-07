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
package gov.redhawk.ide.codegen.frontend.ui;

import gov.redhawk.ide.codegen.frontend.FrontendFactory;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndJavaGeneratorPropertiesWizardPage;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerOptionsWizardPage;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerPropsPage;
import gov.redhawk.ide.codegen.frontend.ui.wizard.FrontEndTunerTypeSelectionWizardPage;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;

import java.util.ArrayList;
import java.util.List;


public class FrontEndJavaGeneratorTemplateDisplayFactory extends FrontEndGeneratorTemplateDisplayFactory {
	private FrontEndTunerPropsPage frontEndTunerPropsWizardPage;
	private FrontEndTunerTypeSelectionWizardPage frontEndTunerTypeSelectionPage;
	private FrontEndTunerOptionsWizardPage frontEndTunerOptionsWizardPage;
	private FrontEndJavaGeneratorPropertiesWizardPage frontEndJavaGeneratorPropertiesWizardPage;
	
	@Override
	public ICodegenWizardPage[] createPages() {
		List<ICodegenWizardPage> pages = new ArrayList<ICodegenWizardPage>();

		setFeiDevice(FrontendFactory.eINSTANCE.createFeiDevice());

		this.frontEndJavaGeneratorPropertiesWizardPage = new FrontEndJavaGeneratorPropertiesWizardPage();
		this.frontEndJavaGeneratorPropertiesWizardPage.setCanFlipToNextPage(true);
		this.frontEndTunerTypeSelectionPage = new FrontEndTunerTypeSelectionWizardPage(getFeiDevice());
		this.frontEndTunerOptionsWizardPage = new FrontEndTunerOptionsWizardPage(getFeiDevice());
		this.frontEndTunerPropsWizardPage = new FrontEndTunerPropsPage(getFeiDevice());
		
		pages.add(this.frontEndJavaGeneratorPropertiesWizardPage);
		pages.add(this.frontEndTunerTypeSelectionPage);
		pages.add(this.frontEndTunerOptionsWizardPage);
		pages.add(this.frontEndTunerPropsWizardPage);

		return pages.toArray(new ICodegenWizardPage[pages.size()]);
	}
}
