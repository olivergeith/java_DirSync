/*
 * DirectoryVarGetSet.java
 *
 * Copyright (C) 2006 F. Gerbig (fgerbig@users.sourceforge.net)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package dirsync.directory;

import java.io.File;

import dirsync.tools.FileTools;
import dirsync.tools.Log;

/**
 * Contains variables and getter and setter methods for the Directory class.
 * @author F. Gerbig (fgerbig@users.sourceforge.net) 
 */
public abstract class DirectoryVarGetSet {
	
	protected static String test = ""; // TEST

	String name = "";

	protected boolean enabled;

	// log object for this directory
	protected Log log;

	protected String logfile = "";

	// source and destination directories
	protected String src = "";

	protected File srcFile;

	protected String dst = "";

	protected File dstFile;

	// In- an fileExclude pattern for files
	protected String fileInclude = "*";

	protected String[] fileIncludes;

	protected String fileExclude = "";

	protected String[] fileExcludes;

	// In- an fileExclude pattern for directories
	protected String dirInclude = "*";

	protected String[] dirIncludes;

	protected String dirExclude = "";

	protected String[] dirExcludes;

	// copy options
	protected boolean withSubfolders = false;

	protected boolean verify = false;

	protected boolean copyAll = false;

	protected boolean copyNew = false;

	protected boolean copyLarger = false;

	protected boolean copyModified = false;

	protected boolean copyLargerModified = false;

	protected boolean deleteDirs = false;

	protected boolean deleteFiles = false;

	// counters
	protected int count = 0;

	protected int countCopyAll = 0;

	protected int countCopyNew = 0;

	protected int countCopyLarger = 0;

	protected int countCopyModified = 0;

	protected int countCopyLargerModified = 0;

	protected int countDeleteDirs = 0;

	protected int countDeleteFiles = 0;

	/**
	 * Returns which directory patterns are excluded in the dirsync.
	 * 
	 * @return The excluded file patterns.
	 */
	public String getDirExclude() {
		return dirExclude;
	}

	/**
	 * Returns which directory patterns are included in the dirsync.
	 * 
	 * @return The included file patterns.
	 */
	public String getDirInclude() {
		return dirInclude;
	}

	/**
	 * Returns the destination path of the directory to dirsync.
	 * 
	 * @return The destination path.
	 */
	public String getDst() {
		return dst;
	}

	/**
	 * Returns which file patterns are excluded in the dirsync.
	 * 
	 * @return The excluded file patterns.
	 */
	public String getFileExclude() {
		return fileExclude;
	}

	/**
	 * Returns which file patterns are included in the dirsync.
	 * 
	 * @return The included file patterns.
	 */
	public String getFileInclude() {
		return fileInclude;
	}

	/**
	 * Returns the file where to log what's being done.
	 * 
	 * @return The logfile.
	 */
	public String getLogfile() {
		return logfile;
	}

	/**
	 * Returns the name of the dirsync action.
	 * 
	 * @return String The name of the dirsync action.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the source path of the directory to dirsync.
	 * 
	 * @return The source path.
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * Returns if the directory has directory excludes.
	 * 
	 * @return <code>true</code> if the directory has directory excludes, else
	 *         <code>false</code>.
	 */
	public boolean hasDirExcludes() {
		return !dirExclude.equals("");
	}

	/**
	 * Returns if the directory has directory includes.
	 * 
	 * @return <code>true</code> if the directory has directory includes, else
	 *         <code>false</code>.
	 */
	public boolean hasDirIncludes() {
		return !dirInclude.equals("*");
	}

	/**
	 * Returns if the directory has file excludes.
	 * 
	 * @return <code>true</code> if the directory has file excludes, else
	 *         <code>false</code>.
	 */
	public boolean hasFileExcludes() {
		return !fileExclude.equals("");
	}

	/**
	 * Returns if the directory has file includes.
	 * 
	 * @return <code>true</code> if the directory has file includes, else
	 *         <code>false</code>.
	 */
	public boolean hasFileIncludes() {
		return !fileInclude.equals("*");
	}

	/**
	 * Returns if all files are copied.
	 * 
	 * @return <code>true</code> if all files are copied, else
	 *         <code>false</code>.
	 */
	public boolean isCopyAll() {
		return copyAll;
	}

	/**
	 * Returns if only larger files are copied.
	 * 
	 * @return <code>true</code> if only larger files are copied, else
	 *         <code>false</code>.
	 */
	public boolean isCopyLarger() {
		return copyLarger;
	}

	/**
	 * Returns if only files are copied that are larger <b>and </b> modified.
	 * 
	 * @return <code>true</code> if only are copied that are larger <b>and
	 *         </b> modified, else <code>false</code>.
	 */
	public boolean isCopyLargerModified() {
		return copyLargerModified;
	}

	/**
	 * Returns if only modified files are copied.
	 * 
	 * @return <code>true</code> if only modified files are copied, else
	 *         <code>false</code>.
	 */
	public boolean isCopyModified() {
		return copyModified;
	}

	/**
	 * Returns if only new files (files not existing in the destination
	 * directory) are copied.
	 * 
	 * @return <code>true</code> if only new files are copied, else
	 *         <code>false</code>.
	 */
	public boolean isCopyNew() {
		return copyNew;
	}

	/**
	 * Returns if directories deleted in the source directory are deleted in the
	 * destination directory.
	 * 
	 * @return <code>true</code> if directories deleted in the source
	 *         directory are deleted in the destination directory, else
	 *         <code>false</code>.
	 */
	public boolean isDelDirs() {
		return deleteDirs;
	}

	/**
	 * Returns if files deleted in the source directory are deleted in the
	 * destination directory.
	 * 
	 * @return <code>true</code> if files deleted in the source directory are
	 *         deleted in the destination directory, else <code>false</code>.
	 */
	public boolean isDelFiles() {
		return deleteFiles;
	}

	/**
	 * Determines whether this directory is enabled.
	 * 
	 * @return <code>true</code> if the component is enabled,
	 *         <code>false</code> otherwise.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Returns if the dirsync is done with verify.
	 * 
	 * @return <code>true</code> if verify is enabled.
	 */
	public boolean isVerify() {
		return verify;
	}

	/**
	 * Returns if the dirsync is done with subfolders.
	 * 
	 * @return <code>true</code> if the dirsync is done with subfolders, else
	 *         <code>false</code>.
	 */
	public boolean isWithSubfolders() {
		return withSubfolders;
	}

	/**
	 * Sets whether all files are copied.
	 * 
	 * @param copyAll
	 *            <code>true</code> if all files are copied.
	 */
	public void setCopyAll(boolean copyAll) {
		this.copyAll = copyAll;
	}

	/**
	 * Sets whether only larger files are copied.
	 * 
	 * @param copyLarger
	 *            <code>true</code> if only larger files are copied.
	 */
	public void setCopyLarger(boolean copyLarger) {
		this.copyLarger = copyLarger;
	}

	/**
	 * Sets whether only files are copied that are larger <b>and </b> modified.
	 * 
	 * @param copyLargerModified
	 *            <code>true</code> if only files are copied that are larger
	 *            <b>and </b> modified.
	 */
	public void setCopyLargerModified(boolean copyLargerModified) {
		this.copyLargerModified = copyLargerModified;
	}

	/**
	 * Sets whether only modified files are copied.
	 * 
	 * @param copyModified
	 *            <code>true</code> if only modified files are copied.
	 */
	public void setCopyModified(boolean copyModified) {
		this.copyModified = copyModified;
	}

	/**
	 * Sets whether only new files (files not existing in the destination
	 * directory) are copied.
	 * 
	 * @param copyNew
	 *            <code>true</code> if only new files are copied.
	 */
	public void setCopyNew(boolean copyNew) {
		this.copyNew = copyNew;
	}

	/**
	 * Sets whether directories deleted in the source directory are deleted in
	 * the destination directory.
	 * 
	 * @param delDirs
	 *            <code>true</code> if directories deleted in the source
	 *            directory are deleted in the destination directory.
	 */
	public void setDelDirs(boolean delDirs) {
		this.deleteDirs = delDirs;
	}

	/**
	 * Sets whether files deleted in the source directory are deleted in the
	 * destination directory.
	 * 
	 * @param delFiles
	 *            <code>true</code> if files deleted in the source directory
	 *            are deleted in the destination directory.
	 */
	public void setDelFiles(boolean delFiles) {
		this.deleteFiles = delFiles;
	}

	/**
	 * Sets which directory patterns are excluded in the dirsync.
	 * 
	 * @param pattern
	 *            The excluded directory patterns.
	 */
	public void setDirExclude(String pattern) {
		this.dirExclude = pattern;
		this.dirExcludes = FileTools.tokenizePattern(pattern);
	}

	/**
	 * Sets which directory patterns are included in the dirsync.
	 * 
	 * @param pattern
	 *            The included directory patterns.
	 */
	public void setDirInclude(String pattern) {
		this.dirInclude = pattern;
		this.dirIncludes = FileTools.tokenizePattern(pattern);
	}

	/**
	 * Sets the destination path of the directory to dirsync.
	 * 
	 * @param dst
	 *            The destination path.
	 */
	public void setDst(String dst) {
		this.dst = dst;
	}

	/**
	 * Enables or disables this directory, depending on the value of the
	 * parameter enabled.
	 * 
	 * @param enabled
	 *            If <code>true</code>, this component is enabled; otherwise
	 *            this component is disabled.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Sets which file patterns are excluded in the dirsync.
	 * 
	 * @param pattern
	 *            The excluded file patterns.
	 */
	public void setFileExclude(String pattern) {
		this.fileExclude = pattern;
		this.fileExcludes = FileTools.tokenizePattern(pattern);
	}

	/**
	 * Sets which file patterns are included in the dirsync.
	 * 
	 * @param pattern
	 *            The included file patterns.
	 */
	public void setFileInclude(String pattern) {
		this.fileInclude = pattern;
		this.fileIncludes = FileTools.tokenizePattern(pattern);
	}

	/**
	 * Sets the file where to log what's being done. If an empty
	 * <code>String</code>"" is specified, logging is disabled.
	 * 
	 * @param logfile
	 *            The logfile.
	 */
	public void setLogfile(String logfile) {
		this.logfile = logfile;
	}

	/**
	 * Sets the name of the dirsync action.
	 * 
	 * @param name
	 *            The name of this dirsync action.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the source path of the directory to dirsync.
	 * 
	 * @param src
	 *            The source path.
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * Sets whether copied files should be verified.
	 * @param b Whether copied files should be verified.
	 *            Set to <code>true</code> to enable verify.
	 */
	public void setVerify(boolean b) {
		verify = b;
	}

	/**
	 * Sets whether the dirsync is done with subfolders.
	 * @param withSubfolders Whether the dirsync is done with subfolders.
	 *            <code>true</code> if the synchroize is done with subfolders.
	 */
	public void setWithSubfolders(boolean withSubfolders) {
		this.withSubfolders = withSubfolders;
	}
}
