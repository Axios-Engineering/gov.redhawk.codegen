/**
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 * 
 * This file is part of REDHAWK IDE.
 * 
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 */
package gov.redhawk.ide.softpackage.codegen;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.util.ProjectCreator;
import gov.redhawk.ide.softpackage.ui.SoftPackageUi;
import gov.redhawk.ide.spd.IdeSpdPlugin;
import gov.redhawk.ide.spd.generator.newcomponent.ComponentProjectCreator;
import gov.redhawk.sca.util.SubMonitor;

import java.io.IOException;

import mil.jpeojtrs.sca.spd.Author;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;
import mil.jpeojtrs.sca.spd.SpdPackage;
import mil.jpeojtrs.sca.util.ScaResourceFactoryUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;

@SuppressWarnings("restriction")
public class SoftPackageProjectCreator extends ComponentProjectCreator {

	public static IFile createComponentFiles(final IProject project, final String spdName, final String spdId, final String authorName,
		final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, "Creating SCA softpackage files", 2);

		// Make sure spdFile does not already exist
		final IFile spdFile = project.getFile(spdName + SpdPackage.FILE_EXTENSION);
		if (spdFile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, IdeSpdPlugin.PLUGIN_ID, "File " + spdFile.getName() + " already exists.", null));
		}

		// Create the model object for the .spd.xml file
		SpdPackage.eINSTANCE.eClass();
		final ResourceSet resourceSet = ScaResourceFactoryUtil.createResourceSet();
		final URI spdUri = URI.createPlatformResourceURI(spdFile.getFullPath().toString(), true);
		final XMLResource resource = (XMLResource) resourceSet.createResource(spdUri, SpdPackage.eCONTENT_TYPE);
		final SoftPkg spd = SpdFactory.eINSTANCE.createSoftPkg();
		resource.getContents().add(spd);
		spd.setId(spdId);
		spd.setName(spdName);
		Author author = SpdFactory.eINSTANCE.createAuthor();
		spd.getAuthor().add(author);
		if (authorName != null) {
			author.getName().add(authorName);
		}

		// Write the .spd.xml file to disk
		try {
			resource.save(null);
			progress.worked(1);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, SoftPackageUi.PLUGIN_ID, "Unable to write SPD file", e));
		}

		// Refresh
		spdFile.refreshLocal(IResource.DEPTH_ZERO, progress.newChild(1));

		return spdFile;
	}

	public static void addImplementation(final IProject project, final String spdName, final Implementation impl, final ImplementationSettings settings,
		final IProgressMonitor monitor) throws CoreException {
		ProjectCreator.addImplementation(project, spdName, impl, settings, monitor);
	}
}
