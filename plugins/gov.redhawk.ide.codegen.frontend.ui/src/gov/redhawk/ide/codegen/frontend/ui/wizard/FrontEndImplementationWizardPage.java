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

import gov.redhawk.ide.codegen.internal.CodeGeneratorDescriptor;
import gov.redhawk.ide.codegen.jinja.cplusplus.CplusplusGenerator;
import gov.redhawk.ide.codegen.jinja.java.JavaGenerator;
import gov.redhawk.ide.codegen.jinja.python.PythonGenerator;
import gov.redhawk.ide.spd.ui.wizard.ImplementationWizardPage;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

/**
 * @since 1.1
 */
public class FrontEndImplementationWizardPage extends ImplementationWizardPage {

	private ViewerFilter[] viewerFilters = new ViewerFilter[1];
	private boolean pageViewed = false;

	public FrontEndImplementationWizardPage(String name, String componentTypeDevice) {
		super(name, componentTypeDevice);
		ViewerFilter viewerFilter = new ViewerFilter() {

			@SuppressWarnings("restriction")
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (CplusplusGenerator.ID.equalsIgnoreCase(((CodeGeneratorDescriptor) element).getId())
											|| PythonGenerator.ID.equalsIgnoreCase(((CodeGeneratorDescriptor) element).getId()) 
											||	JavaGenerator.ID.equalsIgnoreCase(((CodeGeneratorDescriptor) element).getId())) {
					return true;
				}
				return false;
			}
		};
		this.viewerFilters[0] = viewerFilter;

	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		this.getCodeGeneratorEntryViewer().setFilters(this.viewerFilters);
	}

	// Complete if the page has been drawn.
	@Override
	public boolean isPageComplete() {
		return pageViewed;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		pageViewed = true;
	}

}
