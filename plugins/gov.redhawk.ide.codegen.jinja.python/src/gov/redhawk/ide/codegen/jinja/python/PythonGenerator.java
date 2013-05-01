package gov.redhawk.ide.codegen.jinja.python;

import gov.redhawk.ide.codegen.FileToCRCMap;
import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jinja.JinjaGenerator;
import gov.redhawk.ide.codegen.python.AbstractPythonGenerator;
import gov.redhawk.ide.codegen.python.PythonGeneratorPlugin;
import gov.redhawk.model.sca.util.ModelUtil;

import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;

import mil.jpeojtrs.sca.spd.Code;
import mil.jpeojtrs.sca.spd.CodeFileType;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.LocalFile;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.SpdFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;

public class PythonGenerator extends AbstractPythonGenerator {

	private final JinjaGenerator generator = new JinjaGenerator();

	@Override
	public Code getInitialCodeSettings(final SoftPkg softPkg, final ImplementationSettings settings, final Implementation impl) {
		String outputDir = settings.getOutputDir();
		if (outputDir != null && !"".equals(outputDir) && outputDir.charAt(0) == '/') {
			outputDir = outputDir.substring(1);
		}
		if (outputDir != null && "".equals(outputDir)) {
			outputDir = ".";
		}

		final Code retVal = SpdFactory.eINSTANCE.createCode();
		final String prefix = softPkg.getName();
		retVal.setEntryPoint(outputDir + "/" + prefix + ".py");

		final LocalFile file = SpdFactory.eINSTANCE.createLocalFile();
		file.setName(outputDir);
		retVal.setLocalFile(file);
		retVal.setType(CodeFileType.EXECUTABLE);

		return retVal;
	}

	@Override
	public boolean shouldGenerate() {
		return true;
	}

	@Override
	public IFile getDefaultFile(final Implementation impl, final ImplementationSettings implSettings) {
		final IResource resource = ModelUtil.getResource(implSettings);
		final IProject project = resource.getProject();

		final SoftPkg softpkg = impl.getSoftPkg();
		final String prefix = softpkg.getName();
		final String defaultFilename = implSettings.getOutputDir() + File.separator + prefix + ".py";
		return project.getFile(new Path(defaultFilename));
	}

	@Override
	public IStatus validate() {
		final MultiStatus status = new MultiStatus(PythonGeneratorPlugin.PLUGIN_ID, IStatus.OK, "Validation failed", null);
		status.add(super.validate());
		status.add(this.generator.validate());
		return status;
	}

	@Override
	protected void generateCode(final Implementation impl, final ImplementationSettings implSettings, final IProject project, final String componentName,
	        final PrintStream out, final PrintStream err, final IProgressMonitor monitor, final String[] generateFiles, final List<FileToCRCMap> crcMap)
	        throws CoreException {
		this.generator.generate(implSettings, impl, out, err, monitor, generateFiles);
		// Refresh the project so that generated files are picked up.
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

	@Override
	public HashMap<String, Boolean> getGeneratedFiles(final ImplementationSettings implSettings, final SoftPkg softpkg) throws CoreException {
		return this.generator.list(implSettings, softpkg);
	}

}
