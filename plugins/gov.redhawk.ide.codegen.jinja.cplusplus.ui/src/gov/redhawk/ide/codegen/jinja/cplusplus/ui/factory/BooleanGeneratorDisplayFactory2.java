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

package gov.redhawk.ide.codegen.jinja.cplusplus.ui.factory;

import gov.redhawk.ide.codegen.jinja.cplusplus.ui.wizard.BooleanGeneratorPropertiesWizardPage2;
import gov.redhawk.ide.codegen.ui.BooleanGeneratorPropertiesComposite;
import gov.redhawk.ide.codegen.ui.ICodegenComposite;
import gov.redhawk.ide.codegen.ui.ICodegenDisplayFactory;
import gov.redhawk.ide.codegen.ui.ICodegenWizardPage;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @since 1.0
 */
public class BooleanGeneratorDisplayFactory2 implements ICodegenDisplayFactory {

	@Override
	public ICodegenComposite createComposite(final Composite parent, final int style, final FormToolkit toolkit) {
		return new BooleanGeneratorPropertiesComposite(parent, style, toolkit);
	}

	@Override
	public ICodegenWizardPage createPage() {
		return new BooleanGeneratorPropertiesWizardPage2();
	}

}