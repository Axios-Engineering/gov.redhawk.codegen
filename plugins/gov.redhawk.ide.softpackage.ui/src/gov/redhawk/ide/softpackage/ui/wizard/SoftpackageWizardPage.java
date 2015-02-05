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
package gov.redhawk.ide.softpackage.ui.wizard;

import gov.redhawk.ide.codegen.ICodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.RedhawkCodegenActivator;
import gov.redhawk.ide.softpackage.ui.wizard.models.SoftpackageModel;
import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;
import mil.jpeojtrs.sca.spd.Implementation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class SoftpackageWizardPage extends ImplementationWizardPage {

	private final SoftpackageModel model;

	public SoftpackageWizardPage(String pagename, SoftpackageModel model, String componentType) {
		super(pagename, componentType);
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite client = new Composite(parent, SWT.NULL);
		client.setLayout(new GridLayout(1, false));
		setControl(client);
	}

	// Update Implementation and Implementation Settings values to match selected type
	public void handleTypeSelectionChanged() {

		// Currently only supporting cpp & octave, which use the same generator -
		// if we add new type, use model.getTypeName() to determine the code generator ID
		String codeGenId = "gov.redhawk.ide.codegen.jinja.cplusplus.CplusplusSoftpkgGenerator";

		ICodeGeneratorDescriptor tempCodeGen = RedhawkCodegenActivator.getCodeGeneratorsRegistry().findCodegen(codeGenId);
		Implementation implementation = getImplementation();
		ImplementationSettings settings = getImplSettings();
		settings.setGeneratorId(tempCodeGen.getId());

		if ("C++".equals(model.getTypeName())) {
			implementation.setId("cpp");
			settings.setOutputDir("cpp");
			settings.setTemplate("redhawk.codegen.jinja.project.softPackageDependency.cpp");
		} else if ("Octave".equals(model.getTypeName())) {
			implementation.setId("octave");
			settings.setOutputDir("cpp");
			// TODO: need an octave template to set here
			settings.setTemplate(null);
		}

		implementation.getCompiler().setName(tempCodeGen.getCompiler());
		implementation.getCompiler().setVersion(tempCodeGen.getCompilerVersion());
		this.getProgLang().setName(tempCodeGen.getLanguage());
		if (tempCodeGen.getRuntime() != null) {
			implementation.getRuntime().setName(tempCodeGen.getRuntime());
			implementation.getRuntime().setVersion(tempCodeGen.getRuntimeVersion());
		} else {
			implementation.setRuntime(null);
		}
	}

	/* (non-Javadoc)
	 * @see gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		return getImplementation() != null;
	};
}
