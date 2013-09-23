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
package gov.redhawk.ide.codegen.jet.cplusplus.pattern;

import gov.redhawk.ide.codegen.IScaComponentCodegenTemplate;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.template.BuildShTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.ConfigureAcTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.MakefileAmTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.ReconfTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.component.pull.PullMainCppTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.component.pull.PullPortImplCppTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.component.pull.PullPortImplHTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.component.pull.PullResourceBaseCppTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.component.pull.PullResourceBaseHTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.component.pull.PullResourceCppTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.component.pull.PullResourceHTemplate;
import gov.redhawk.ide.codegen.jet.cplusplus.template.component.pull.StructPropsHTemplate;
import gov.redhawk.ide.codegen.jet.template.ResourceSpecTemplate;
import gov.redhawk.ide.codegen.util.CodegenFileHelper;
import gov.redhawk.model.sca.util.ModelUtil;

import java.util.ArrayList;
import java.util.List;

import mil.jpeojtrs.sca.prf.Properties;
import mil.jpeojtrs.sca.spd.SoftPkg;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * @since 6.0
 */
public class PullPortDataTemplate implements IScaComponentCodegenTemplate {

	public PullPortDataTemplate() {
	}

	@Override
	public String generateFile(final String fileName, final SoftPkg softPkg, final ImplementationSettings implSettings,
	        final Object helperObject) throws CoreException {
		final TemplateParameter templ = (TemplateParameter) helperObject;
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		String file = "";
		final List<String> sourceFiles = new ArrayList<String>();
		sourceFiles.add(prefix + ".cpp");
		sourceFiles.add(prefix + "_base.cpp");
		sourceFiles.add(prefix + ".h");
		sourceFiles.add(prefix + "_base.h");
		sourceFiles.add("main.cpp");
		sourceFiles.add("port_impl.cpp");
		sourceFiles.add("port_impl.h");
		final Properties properties = softPkg.getPropertyFile().getProperties();
		if ((properties != null) && (properties.getStruct() != null)) {
			sourceFiles.add("struct_props.h");
		}
		templ.setSourceFiles(sourceFiles.toArray(new String[sourceFiles.size()]));

		if (fileName.equals("port_impl.cpp")) {
			file = new PullPortImplCppTemplate().generate(templ);
		} else if (fileName.equals("port_impl.h")) {
			file = new PullPortImplHTemplate().generate(templ);
		} else if (fileName.equals("main.cpp")) {
			file = new PullMainCppTemplate().generate(templ);
		} else if (fileName.equals(prefix + ".cpp")) {
			file = new PullResourceCppTemplate().generate(templ);
		} else if (fileName.equals(prefix + ".h")) {
			file = new PullResourceHTemplate().generate(templ);
		} else if (fileName.equals(prefix + "_base.cpp")) {
			file = new PullResourceBaseCppTemplate().generate(templ);
		} else if (fileName.equals(prefix + "_base.h")) {
			file = new PullResourceBaseHTemplate().generate(templ);
		} else if (fileName.equals("struct_props.h")) {
			file = new StructPropsHTemplate().generate(templ);
		} else if (fileName.equals("build.sh")) {
			file = new BuildShTemplate().generate(templ);
		} else if (fileName.equals("configure.ac")) {
			file = new ConfigureAcTemplate().generate(templ);
		} else if (fileName.equals("Makefile.am")) {
			file = new MakefileAmTemplate().generate(templ);
		} else if (fileName.equals("reconf")) {
			file = new ReconfTemplate().generate(templ);
		} else if (fileName.equals("../" + CodegenFileHelper.getProjectFileName(softPkg) + ".spec")) {
			file = new ResourceSpecTemplate().generate(templ);
		}

		return file;
	}

	@Override
	public List<String> getExecutableFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		final List<String> fileNames = new ArrayList<String>();

		fileNames.add("build.sh");
		fileNames.add("reconf");

		return fileNames;
	}

	@Override
	public List<String> getAllGeneratedFileNames(final ImplementationSettings implSettings, final SoftPkg softPkg) {
		final List<String> fileNames = new ArrayList<String>();
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		final IProject project = ModelUtil.getProject(softPkg);

		fileNames.add("build.sh");
		fileNames.add("configure.ac");
		fileNames.add("Makefile.am");
		fileNames.add("main.cpp");
		fileNames.add("port_impl.cpp");
		fileNames.add("port_impl.h");
		fileNames.add("reconf");
		fileNames.add("../" + CodegenFileHelper.getProjectFileName(softPkg) + ".spec");
		fileNames.add(prefix + "_base.cpp");
		fileNames.add(prefix + "_base.h");

		final Properties properties = softPkg.getPropertyFile().getProperties();
		if ((properties != null) && (properties.getStruct() != null)) {
			fileNames.add("struct_props.h");
		}

		if ((project == null) || !project.getFile(implSettings.getOutputDir() + IPath.SEPARATOR + prefix + ".cpp").exists()) {
			fileNames.add(prefix + ".cpp");
		}

		if ((project == null) || !project.getFile(implSettings.getOutputDir() + IPath.SEPARATOR + prefix + ".h").exists()) {
			fileNames.add(prefix + ".h");
		}

		return fileNames;
	}

	@Override
	public boolean shouldGenerate() {
		return true;
	}

	@Override
	public String getDefaultFilename(final SoftPkg softPkg, final ImplementationSettings implSettings, final String srcDir) {
		final String prefix = CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
		return srcDir + prefix + ".cpp";
	}

}
