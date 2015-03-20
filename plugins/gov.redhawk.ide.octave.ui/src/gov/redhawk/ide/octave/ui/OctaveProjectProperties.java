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

package gov.redhawk.ide.octave.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @since 8.1
 */
public class OctaveProjectProperties extends BeanModelObject {
	private String functionName = null;
	private File primaryMFile = null;
	private File currentDepFile = null;
	private List<OctaveFunctionVariables> functionInputs = new ArrayList<OctaveFunctionVariables>();
	private List<OctaveFunctionVariables> functionOutputs = new ArrayList<OctaveFunctionVariables>();
	
	private List<File> mFileDepsList = new ArrayList<File>();
	private boolean hasDeps;
	
	/**
	 * Get the primary m-file for this project.
	 * @return The java.io File object for the m-file.
	 */
	public File getPrimaryMFile() {
		return primaryMFile;
	}
	
	/**
	 * Set the primary m-file for this project.
	 * @param primaryMFile The m-file for this project.
	 */
	public void setPrimaryMFile(File primaryMFile) {
		final File oldValue = this.primaryMFile;
		this.primaryMFile = primaryMFile;
		firePropertyChange("primaryMFile", oldValue, this.primaryMFile);
	}

	/**
	 * Get a List of the secondary m-files.  List is empty if no dependencies
	 * are specified.
	 * @return List of full paths to the m-files.    
	 */
	public List<File> getmFileDepsList() {
		return mFileDepsList;
	}

	public boolean getHasDeps() {
		return hasDeps;
	}

	public void setHasDeps(boolean hasDeps) {
		final boolean oldValue = this.hasDeps;
		this.hasDeps = hasDeps;
		firePropertyChange("hasDeps", oldValue, this.hasDeps);
		
		if (!this.hasDeps) {
			this.mFileDepsList.clear();
		}
	}

	public File getCurrentDepFile() {
		return currentDepFile;
	}

	public void setCurrentDepFile(File currentDepFile) {
		final File oldValue = this.currentDepFile;
		this.currentDepFile = currentDepFile;
		firePropertyChange("currentDepFile", oldValue, this.currentDepFile);
	}
	
	public void setFunctionInputs(List<OctaveFunctionVariables> functionInputs) {
		final List<OctaveFunctionVariables> oldValue = this.functionInputs;
		this.functionInputs = functionInputs;
		firePropertyChange("functionInputs", oldValue, this.functionInputs);
	}

	public List<OctaveFunctionVariables> getFunctionInputs() {
		return Collections.unmodifiableList(functionInputs);
	}
	
	public void setFunctionOutputs(List<OctaveFunctionVariables> functionOutputs) {
		final List<OctaveFunctionVariables> oldValue = this.functionOutputs;
		this.functionOutputs = functionOutputs;
		firePropertyChange("functionOutputs", oldValue, this.functionOutputs);
	}

	public List<OctaveFunctionVariables> getFunctionOutputs() {
		return Collections.unmodifiableList(functionOutputs);
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		final String oldValue = this.functionName;
		this.functionName = functionName;
		firePropertyChange("functionName", oldValue, this.functionName);
	}

}
