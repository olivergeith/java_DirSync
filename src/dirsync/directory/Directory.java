/*
 * Directory.java
 * 
 * Copyright (C) 2002 E. Gerber  
 * Copyright (C) 2003, 2004, 2005, 2006 F. Gerbig (fgerbig@users.sourceforge.net)
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

import java.io.*;

import dirsync.Const;
import dirsync.DirSync;
import dirsync.exceptions.ErrorException;
import dirsync.exceptions.IncompleteConfigurationException;
import dirsync.exceptions.WarningException;
import dirsync.sync.Sync;
import dirsync.tools.FileTools;
import dirsync.tools.Log;

/**
 * Represents a directory (source and destination) to be synchronized. Contains
 * the source and destination path to dirsync, whether the dirsync will be done
 * recursive, which file extensions to fileInclude and fileExclude, if and where
 * to log what's being done and how the synchronization should be done.
 * 
 * @author E. Gerber , F. Gerbig (fgerbig@users.sourceforge.net)
 */
public class Directory extends DirectoryVarGetSet {
	/**
	 * Initializes a new Directory.
	 */
	public Directory() {
		setFileInclude("*");
		setFileExclude("");
		setDirInclude("*");
		setDirExclude("");

		try {
			log = new Log("");
		} catch (Exception e) {
			throw new Error(e);
		}
		enabled = true;
	}

	/**
	 * Initializes a new Directory.
	 * @param enabled The enabled state of the directory.
	 */
	public Directory(boolean enabled) {
		this();
		this.enabled = enabled;
	}

	/**
	 * Changes the path of the given file from the sorce path to the destination
	 * path.
	 * 
	 * @param srcFile
	 *            The file in the source directory.
	 * @param srcPath
	 *            The source path.
	 * @param dstPath
	 *            The destination path.
	 * @return File The file with the destination path.
	 */
	private static File replacePath(File srcFile, File srcPath, File dstPath) {

		// get actual path
		String path = srcFile.getAbsolutePath();
		// delete source path from actual path
		String cut = path.substring(srcPath.getAbsolutePath().length(), path
				.length());

		if (!cut.startsWith(System.getProperty("file.separator"))) {
			cut = System.getProperty("file.separator") + cut;
		}
		// add destination path with remaining actual path
		String newPath = dstPath.getAbsolutePath() + cut;

		return new File(newPath);
	}

	/**
	 * Changes the path of the given files from the sorce path to the
	 * destination path.
	 * 
	 * @param srcFiles
	 *            The files in the source directory.
	 * @param srcPath
	 *            The source path.
	 * @param dstPath
	 *            The destination path.
	 * @return File[] The files with the destination path.
	 */
	private static File[] replacePath(File[] srcFiles, File srcPath,
			File dstPath) {

		File[] dstFiles = new File[srcFiles.length];

		// for all files
		for (int i = 0; i < srcFiles.length; i++) {
			dstFiles[i] = replacePath(srcFiles[i], srcPath, dstPath);
		}

		return dstFiles;
	}

	private void copy(File src, File dst) throws IOException {
		// Copy All
		if (copyAll) {
			countCopyAll = copy(src, dst, countCopyAll, "Copy All");
		} else { // if copymode is not "Copy All" check other copymodes

			// Copy New
			if (copyNew && !dst.exists()) {
				countCopyNew = copy(src, dst, countCopyNew, "Copy New");
			} else {

				// Copy Modified
				if (copyModified && FileTools.cmpFileDates(src, dst)) {
					countCopyModified = copy(src, dst, countCopyModified,
							"Copy Modified");
				} else {

					// Copy Larger
					if (copyLarger && FileTools.cmpFileSizes(src, dst)) {
						countCopyLarger = copy(src, dst, countCopyLarger,
								"Copy Larger");
					} else {

						// Copy LargerModified
						// (only if not copyLarger or copyModified are selected;
						// in this case the files have alread been copied)
						if (copyLargerModified && !(copyLarger || copyModified)
								&& FileTools.cmpFileDates(src, dst)
								&& FileTools.cmpFileSizes(src, dst)) {
							countCopyLargerModified = copy(src, dst,
									countCopyLargerModified,
									"Copy Larger&Modified");
						}
					}
				}
			}
		}
	}

	private int copy(File src, File dst, int countCopied, String copymode) throws IOException {
		boolean verifyOk = true; 

		try {
			if (DirSync.getSync().isPreview()) {
				copymode = "Preview of " + copymode;
				log.print("    (" + copymode + ")", Const.STYLE_ACTION);
				countCopied++;
			} else {
				FileTools.copy(src, dst);
				countCopied++;
				// if file was not copied an exception is thrown by
				// FileTools.copy
				// so that the countCopied++ is not reached.

				if (verify && src.isFile()) { // verify is done by calculating and checking checksums
					
					// set current progress bar to "Verify"
					DirSync.getGui().setCurrentProgressMaximum(0);
					DirSync.getGui().setCurrentProgress(0);
					DirSync.getGui().setCurrentProgressText("Verify " + src.getName());
				
					// calculate and check checksums
					verifyOk = FileTools.checksumIdentical(src, dst); 
				}

				if (!verify || verifyOk) { // if no verify is necessary or
					// verify is ok print only copymode
					log.print("    (" + copymode + ")", Const.STYLE_ACTION);
				} else { // if verify is not ok print copymode followed by
					// error
					DirSync.getSync().setError(Sync.ERROR_THIS_DIRECTORY);
					log.print("    (" + copymode + ") ERROR: Verify error.",
							Const.STYLE_ERROR);
				}

				// copy destination file modification date back to the
				// source file; some platforms round or truncate the file
				// modification date and the important point is, that the
				// timestamps are equal so the file isn't copied again by
				// the "copy modified" option.
				if (DirSync.getSync().isWriteTimestampBack()) {
					src.setLastModified(dst.lastModified());
				}
			}
			// print information about copied file
			log.print("       '" + src.getAbsolutePath() + "'");
			log.print("       '" + dst.getAbsolutePath() + "'");
			log.print("");

		} catch (WarningException we) {
			if (DirSync.getSync().getError() == Sync.NOERROR) {
				DirSync.getSync().setError(Sync.WARNING);
			}
			print("    (" + copymode + ") Warning: "
					+ we.getMessage(), Const.STYLE_WARNING);
			print("       '" + src.getAbsolutePath() + "'");
			print("       '" + dst.getAbsolutePath() + "'");
			print("");
		} catch (ErrorException ee) {
			DirSync.getSync().setError(Sync.ERROR_THIS_DIRECTORY);
			print("    (" + copymode + ") ERROR: "
					+ ee.getMessage(), Const.STYLE_ERROR);
			print("       '" + src.getAbsolutePath() + "'");
			print("       '" + dst.getAbsolutePath() + "'");
			print("");
		}
		return countCopied;

	}

	/**
	 * Synchronises a directory.
	 * <p>
	 * Steps:
	 * <ol>
	 * <li>read all files from source (with / without recursive)</li>
	 * <li>Clone this dirctorystructure to destination</li>
	 * <li>Copy new files (files not existent in destination)</li>
	 * <li>Compare existing files and copy/update them if necessary</li>
	 * <li>Delete files if necessary</li>
	 * <li>Delete directories if necessary</li>
	 * </ol>
	 * 
	 * @throws Exception
	 */
	public void synchronize() throws Exception {

		try {
			log = new Log(logfile);
		} catch (WarningException we) {
			log = new Log(""); // disable logging
			log.print("Warning: " + we.getMessage() + " Logging for directory '" + this.getName() + "' has been disabled!", Const.STYLE_WARNING);
			log.print("");
		}
		
		log.print("* Started "
				+ (DirSync.getSync().isPreview() ? "preview of " : "")
				+ "synchronization of directory '" + name + "' *",
				Const.STYLE_SUBDIR);
		log.print("");
		// check that src directory exists
		if (src.equals("")) {
			print("    no source directory specified!",
					Const.STYLE_ERROR);
			throw new IncompleteConfigurationException(
					"No source directory specified!");
		}

		srcFile = new File(src);
		if (!srcFile.exists() || !srcFile.isDirectory()) {

			print("    source directory '"
							+ srcFile.getAbsolutePath()
							+ "' doesn't exist or isn't a directory!",
							Const.STYLE_ERROR);
			throw new IncompleteConfigurationException();
		}

		// check that dst directory exists
		if (dst.equals("")) {
			print("    no destination directory specified!",
					Const.STYLE_ERROR);
			throw new IncompleteConfigurationException();
		}
		dstFile = new File(dst);
		if (dstFile.exists() && !dstFile.isDirectory()) {
			print("    destination directory '"
					+ dstFile.getAbsolutePath() + "' isn't a directory!",
					Const.STYLE_ERROR);
			throw new IncompleteConfigurationException();
		}

		// initialize progress bar in indeterminate mode
		if (DirSync.isGuiMode()) {
			DirSync.getGui().setDirProgressText("Counting");
			DirSync.getGui().setDirProgressIndeterminate(true);
		}
		
		// initialize directory lister
		DirectoryLister dl = new DirectoryLister(srcFile, log, DirSync.getSync()
				.isSkipLinks());
		// count files
		count = dl.countFiles(withSubfolders);
		print("  Total number of files found in '"
				+ srcFile.getAbsolutePath() + "'"
				+ (withSubfolders ? " and it's subdirectories" : "") + ": "
				+ count, Const.STYLE_INFO);
		print("");

		// set progress bar to determinate mode
		if (DirSync.isGuiMode()) {
			DirSync.getGui().setDirProgressText(null);
			DirSync.getGui().setDirProgressIndeterminate(false);
			DirSync.getGui().setDirProgressMaximum(count);
			DirSync.getGui().setProgress(0, "");
		}
		
		// reset counters
		count = 0;
		countCopyAll = 0;
		countCopyNew = 0;
		countCopyModified = 0;
		countCopyLarger = 0;
		countCopyLargerModified = 0;
		countDeleteFiles = 0;
		countDeleteDirs = 0;

		// RECURSIVE START
		synchronizeCopy(srcFile);
		if (deleteFiles || deleteDirs) {
			if (DirSync.getSync().getError() == Sync.ERROR_THIS_DIRECTORY) {
				print(
								"  Skipping 'Delete Files' and 'Delete Dirs' because of errors while synchronizing this directory.",
								Const.STYLE_WARNING);
			} else {
				if (DirSync.isGuiMode()) {
					// set progress bar to indeterminate mode
					DirSync.getGui()
						.setDirProgressText("Checking for deletion");
					DirSync.getGui().setDirProgressIndeterminate(true);
					DirSync.getGui().setProgress(0, "");
				}
				
				synchronizeDelete(dstFile);
				
				if (DirSync.isGuiMode()) {
					// set progress bar to determinate mode
					DirSync.getGui().setDirProgressText(null);
					DirSync.getGui().setDirProgressIndeterminate(false);
					DirSync.getGui().setDirProgressMaximum(100);
					DirSync.getGui().setProgress(100, "");
				}	
			}
		} // RECURSIVE END

		if (countCopyAll > 0) {
			print(
					"  (" + (DirSync.getSync().isPreview() ? "Preview of " : "")
							+ "Copy All) files copied: "
							+ String.valueOf(countCopyAll), Const.STYLE_INFO);
		}
		if (countCopyNew > 0) {
			print(
					"  (" + (DirSync.getSync().isPreview() ? "Preview of " : "")
							+ "Copy New) files copied: "
							+ String.valueOf(countCopyNew), Const.STYLE_INFO);
		}
		if (countCopyModified > 0) {
			print("  ("
					+ (DirSync.getSync().isPreview() ? "Preview of " : "")
					+ "Copy Modified) files copied: "
					+ String.valueOf(countCopyModified), Const.STYLE_INFO);
		}
		if (countCopyLarger > 0) {
			print("  ("
					+ (DirSync.getSync().isPreview() ? "Preview of " : "")
					+ "Copy Larger) files copied: "
					+ String.valueOf(countCopyLarger), Const.STYLE_INFO);
		}
		if (countCopyLargerModified > 0) {
			print("  ("
					+ (DirSync.getSync().isPreview() ? "Preview of " : "")
					+ "Copy Larger&Modified) files copied: "
					+ String.valueOf(countCopyLargerModified), Const.STYLE_INFO);
		}
		if (countDeleteFiles > 0) {
			print("  ("
					+ (DirSync.getSync().isPreview() ? "Preview of " : "")
					+ "Delete Files) files deleted: "
					+ String.valueOf(countDeleteFiles), Const.STYLE_INFO);
		}
		if (countDeleteDirs > 0) {
			print("  ("
					+ (DirSync.getSync().isPreview() ? "Preview of " : "")
					+ "Delete Dirs) directories deleted: "
					+ String.valueOf(countDeleteDirs), Const.STYLE_INFO);
		}

		if ((countCopyAll == 0) && (countCopyNew == 0)
				&& (countCopyModified == 0) && (countCopyLarger == 0)
				&& (countCopyLargerModified == 0) && (countDeleteFiles == 0)
				&& (countDeleteDirs == 0)) {

			// if there was nothing to do in the preview, disable this dir
			if (DirSync.getSync().isPreview()) {
				print(
								"  Nothing to do in preview: Directory has been disabled.",
								Const.STYLE_INFO);
				enabled = false;

			} else {
				print("  Nothing to do.", Const.STYLE_INFO);
			}
			log.print("");
		}

		if (DirSync.getSync().isStopping()) {
			log.print(
					"* Stopped synchronization of directory '" + name + "' *",
					Const.STYLE_SUBDIR);
		} else {
			log.print("* Finished synchronization of directory '" + name
					+ "' *", Const.STYLE_SUBDIR);
		}
	}

	private void synchronizeCopy(File dir) throws Exception {
		int i;

		DirSync.getSync().sleepOnPause();
		if (DirSync.getSync().isStopping()) {
			return;
		}

		// read files and directories
		DirectoryLister dirLister = new DirectoryLister(dir, log, DirSync.getSync()
				.isSkipLinks());

		File[] dirs = dirLister.listDirectories(dirIncludes, dirExcludes, true);
		// create a new array of destination dirs
		// (path changed from source to destination)
		File[] dstDirs = Directory.replacePath(dirs, srcFile, dstFile);

		File[] files = dirLister.listFiles(fileIncludes, fileExcludes, true);

		// create a new array of destination files
		// (path changed from source to destination)
		File[] dstFiles = Directory.replacePath(files, srcFile, dstFile);

		// files
		for (i = 0; i < files.length; i++) {

			DirSync.getSync().sleepOnPause();
			if (DirSync.getSync().isStopping()) {
				break;
			}

			copy(files[i], dstFiles[i]);
			count++;
			if (DirSync.isGuiMode()) {
				DirSync.getGui().setProgress(count, files[i].getCanonicalPath());
			}	
		} // free memory allocated by files
		files = null;
		dstFiles = null;

		DirSync.getSync().sleepOnPause();
		if (DirSync.getSync().isStopping()) {
			return;
		}

		// directories
		for (i = 0; i < dirs.length; i++) {
			DirSync.getSync().sleepOnPause();
			if (DirSync.getSync().isStopping()) {
				break;
			}

			if (withSubfolders) {
				synchronizeCopy(dirs[i]);
				if (!DirSync.getSync().isPreview()) {
					// Creating all directories solves the problem of copying
					// empty directories.
					dstDirs[i].mkdirs();
				}
			}
		}
	}

	private void synchronizeDelete(File dir) throws Exception {
		int i;
		boolean delete = false;

		DirSync.getSync().sleepOnPause();
		if (DirSync.getSync().isStopping()) {
			return;
		}
		// read files from destination directory, bring them to the
		// source directory, delete all files in the destination directory
		// that don't exist in the source directory

		// read files (don't skip links, so they can be deleted)
		DirectoryLister dirLister = new DirectoryLister(dir, log, false);

		// directories in destination directory
		// (dont care for includes / excludes)
		File[] dirs = dirLister.listDirectories();
		// directories from destination directory with path changed to source
		// directory
		File[] dirsInSrc = Directory.replacePath(dirs, dstFile, srcFile);

		// files in destination directory
		// (dont care for includes / excludes)
		File[] files = dirLister.listFiles();

		// files from destination directory with path changed to source
		// directory
		File[] filesInSrc = Directory.replacePath(files, dstFile, srcFile);

		// files
		for (i = 0; i < files.length; i++) {
			DirSync.getSync().sleepOnPause();
			if (DirSync.getSync().isStopping()) {
				break;
			}

			if (DirSync.isGuiMode()) {
				// set progress
				DirSync.getGui().setProgress(count, files[i].getAbsolutePath());
			}
			
			delete = true;
			// check includes
			if (FileTools.checkFileMatchesPatterns(files[i], fileIncludes)) {
				delete = false;
			}

			// check excludes
			if (FileTools.checkFileMatchesPatterns(files[i], fileExcludes)) {
				delete = true;
			}

			if (deleteFiles) {
				// file not in src, or (file in src but link with
				// skipLinks selected or file is excluded)

				if (!filesInSrc[i].exists()
						|| (DirSync.getSync().isSkipLinks() && FileTools
								.isLink(filesInSrc[i])) || delete) {

					if (DirSync.getSync().isPreview()) {
						log.print("    (Preview of Delete Files)", Const.STYLE_ACTION); 
						log
								.print("       '" + files[i].getAbsolutePath()
										+ "'");
						log.print("");
						countDeleteFiles++;
					} else {
						if (files[i].delete()) {
							log.print("    (Delete Files)", Const.STYLE_ACTION);
							log.print("       '" + files[i].getAbsolutePath()
									+ "'");
							log.print("");
							countDeleteFiles++;
						} else {
							log
									.print("    (Delete Files) ERROR: Can't delete.", Const.STYLE_ERROR);
							log.print("       '" + files[i].getAbsolutePath()
									+ "'");
							log.print("");
						}
					}
				}
			}
		}

		// directories
		for (i = 0; i < dirs.length; i++) {
			DirSync.getSync().sleepOnPause();
			if (DirSync.getSync().isStopping()) {
				break;
			}

			if (withSubfolders) {
				synchronizeDelete(dirs[i]);
			}

			if (DirSync.isGuiMode()) {
				// set progress
				DirSync.getGui().setProgress(count, dirs[i].getAbsolutePath());
			}	

			delete = true;
			// check includes
			if (FileTools.checkFileMatchesPatterns(dirs[i], dirIncludes)) {
				delete = false;
			}

			// check excludes
			if (FileTools.checkFileMatchesPatterns(dirs[i], dirExcludes)) {
				delete = true;
			}

			if (deleteDirs) { // dir not in src, or dir in src but link with
				// skipLinks selected
				if (!dirsInSrc[i].exists()
						|| (DirSync.getSync().isSkipLinks() && FileTools
								.isLink(dirsInSrc[i])) || delete) {

					if (DirSync.getSync().isPreview()) {
						log.print("    (Preview of Delete Dirs)", Const.STYLE_ACTION); 
						log.print("       '" + dirs[i].getAbsolutePath() + "'");
						log.print("");
						countDeleteDirs++;
					} else {
						if (FileTools.deleteDir(dirs[i])) {
							log.print("    (Delete Dirs)", Const.STYLE_ACTION);
							log.print("       '" + dirs[i].getAbsolutePath()
									+ "'");
							log.print("");
							countDeleteDirs++;
						} else {
							log.print("    (Delete Dirs) ERROR: Can't delete.", Const.STYLE_ERROR);
							log.print("       '" + dirs[i].getAbsolutePath()
									+ "'");
							log.print("");
						}
					}
				}
			}
		}
	}

	/**
	 * Writes a message with date and time to the log and the global log.
	 * 
	 * @param message
	 *            The message to print.
	 */
	public void print(String message) {
		print(message, null);
	}

	/**
	 * Writes a message with date and time to the log and the global log.
	 * 
	 * @param message
	 *            The message to print.
	 * @param style 
	 *            The style to use for the message.
	 */
	public void print(String message, String style) {
		DirSync.getLog().print(message, style);
		// swicth echo off so messages to both logs are only echoed once.
		if (!DirSync.isGuiMode()) {
			log.print(message);
		}
	}
	
	/**
	 * Copies the options from the given directory to this directory.
	 * @param dir The directory from which to copy the options.
	 */
	public void copyOptions(Directory dir) {
		setCopyAll(dir.isCopyAll());
		setCopyNew(dir.isCopyNew());
		setCopyLarger(dir.isCopyLarger());
		setCopyModified(dir.isCopyModified());
		setCopyLargerModified(dir.isCopyLargerModified());
		
		setWithSubfolders(dir.isWithSubfolders());
		setVerify(dir.isVerify());
		
		setDelFiles(dir.isDelFiles());
		setDelDirs(dir.isDelDirs());
	}
}