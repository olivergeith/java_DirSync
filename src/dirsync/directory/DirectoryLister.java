/*
 * DirectoryLister.java
 * 
 * Copyright (C) 2002 E. Gerber
 * Copyright (C) 2003, 2004, 2005, 2006 F. Gerbig  
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

import dirsync.Const;
import dirsync.DirSync;
import dirsync.tools.FileTools;
import dirsync.tools.Log;

/**
 * Lists the contents of a directory. Has methods to count how many files and
 * directories are in a given directory and to list these files and dirctories
 * (recursive, non recursive).
 * 
 * @author E. Gerber , F. Gerbig (fgerbig@users.sourceforge.net)
 */
public class DirectoryLister {

	//	The directory to list
	private final File dir;

	// counter for recursive file counting
	private int count;

	private final Log log;
	
	boolean skipLinks;

	/**
	 * Initializes a new DirectoryLister
	 * 
	 * @param dir
	 *            The root directory to list.
	 * @param log
	 *            The log to write messages.
	 * @param skipLinks
	 *            <code>true</code> if links shall be skipped
	 */
	public DirectoryLister(File dir, Log log, boolean skipLinks) {
		this.dir = dir;
		this.log = log;
		this.skipLinks = skipLinks;
	}

	/**
	 * Lists only files (no directories).
	 * 
	 * @return File[] The files.
	 */
	public File[] listFiles() {
		// get all files and dirs in this directory
		File[] filesAndDirs = listFiles(dir);

		// count number of files
		int count = 0;
		for (int i = 0; i < filesAndDirs.length; i++) {
			if (filesAndDirs[i].isFile()) {
				count++;
			}
		}

		// copy all files to a new array
		File[] files = new File[count];
		count = 0;
		for (int i = 0; i < filesAndDirs.length; i++) {
			if (filesAndDirs[i].isFile()) {
				files[count] = filesAndDirs[i];
				count++;
			}
		}
		return files;
	}

	/**
	 * Lists only directories (no files).
	 * 
	 * @return File[] The directories.
	 */
	public File[] listDirectories() {
		// get all files and dirs in this directory
		File[] filesAndDirs = listFiles(dir);

		// count number of directories
		int count = 0;
		for (int i = 0; i < filesAndDirs.length; i++) {
			if (filesAndDirs[i].isDirectory()) {
				count++;
			}
		}

		// copy all dirs to a new array
		File[] dirs = new File[count];
		count = 0;
		for (int i = 0; i < filesAndDirs.length; i++) {
			if (filesAndDirs[i].isDirectory()) {
				dirs[count] = filesAndDirs[i];
				count++;
			}
		}

		return dirs;
	}

	/**
	 * Lists all files (no directories) that match the include and don't match
	 * the exclude patterns.
	 * 
	 * @param includes
	 *            The patterns to include.
	 * @param excludes
	 *            The patterns to exclude.
	 * @param printExcluded
	 *            If set to <code>true</code> excluded files are printed.
	 * 
	 * @return File[] The files.
	 */
	public File[] listFiles(String[] includes, String[] excludes,
			boolean printExcluded) {

		boolean includeFile = false;
		File[] filesAndDirs = listFiles(dir);

		int count = 0;

		// count files
		for (int i = 0; i < filesAndDirs.length; i++) {
			includeFile = false;

			// check if file should be included
			if (filesAndDirs[i].isFile()) {

				// check includes
				if (FileTools.checkFileMatchesPatterns(filesAndDirs[i], includes)) {
					includeFile = true;
				}

				// check excludes
				if (FileTools.checkFileMatchesPatterns(filesAndDirs[i], excludes)) {
					includeFile = false;
				}
			}

			// count files to include
			if (includeFile) {
				count++;
			}
		}

		// get files
		File[] files = new File[count];
		count = 0;
		for (int i = 0; i < filesAndDirs.length; i++) {
			includeFile = false;

			// check if file should be included
			if (filesAndDirs[i].isFile()) {

				// check includes
				if (FileTools.checkFileMatchesPatterns(filesAndDirs[i], includes)) {
					includeFile = true;
				}
				
				// check excludes
				if (FileTools.checkFileMatchesPatterns(filesAndDirs[i], excludes)) {
					includeFile = false;

					if (printExcluded) {
						log.print("    (Exclude File)", Const.STYLE_INFO);
						log.print("       '"
								+ filesAndDirs[i].getAbsolutePath() + "'");
						log.print("");
					}
				}
			}

			// get files to include
			if (includeFile) {
				files[count] = filesAndDirs[i];
				count++;
			}
		}

		return files;
	}
	
	/**
	 * Lists all directories (no files) that match the include and don't match
	 * the exclude patterns.
	 * 
	 * @param includes
	 *            The patterns to include.
	 * @param excludes
	 *            The patterns to exclude.
	 * @param printExcluded
	 *            If set to <code>true</code> excluded directories are printed.
	 * 
	 * @return File[] The files.
	 */
	public File[] listDirectories(String[] includes, String[] excludes,
			boolean printExcluded) {

		boolean includeDirectory = false;
		File[] filesAndDirs = listFiles(dir);

		int count = 0;

		// count files
		for (int i = 0; i < filesAndDirs.length; i++) {
			includeDirectory = false;

			// check if file should be included
			if (filesAndDirs[i].isDirectory()) {

				// check includes
				if (FileTools.checkFileMatchesPatterns(filesAndDirs[i], includes)) {
					includeDirectory = true; 
				}

				// check excludes
				if (FileTools.checkFileMatchesPatterns(filesAndDirs[i], excludes)) {
					includeDirectory = false; 
				}
			}

			// count files to include
			if (includeDirectory) {
				count++;
			}
		}

		// get files
		File[] directories = new File[count];
		count = 0;
		for (int i = 0; i < filesAndDirs.length; i++) {
			includeDirectory = false;

			// check if file should be included
			if (filesAndDirs[i].isDirectory()) {

				// check includes
				if (FileTools.checkFileMatchesPatterns(filesAndDirs[i], includes)) {
					includeDirectory = true; 
				}

				// check excludes
				if (FileTools.checkFileMatchesPatterns(filesAndDirs[i], excludes)) {
					includeDirectory = false; 

					if (printExcluded) {
						log.print("    (Exclude Directory)", Const.STYLE_INFO);
						log.print("       '"
								+ filesAndDirs[i].getAbsolutePath() + "'");
						log.print("");
					}
				}
			}

			// get directories to include
			if (includeDirectory) {
				directories[count] = filesAndDirs[i];
				count++;
			}
		}

		return directories;
	}
	
	/**
	 * Compares two Strings depending on the operating system
	 * (If the OS is MS Windows the comparison i done case insensitive).
	 * This comparison is used to match file and directory patterns to files and directories.
	 * 
	 * @param a The first string.
	 * @param b The second string.
	 * 
	 * @return <code>true</code> if the strings match, <code>false</code> otherwise.
	 */
	public static boolean matchDependingOnOS(String a, String b) { 
		if (Const.runningUnderWindows) { 
			return a.toLowerCase().matches(b.toLowerCase());
		} else {
			return a.matches(b);
		}
	}

	/**
	 * Counts the number of files (not directories) in the directory and it's
	 * subdirectories.
	 * 
	 * @param recursive
	 *            Whether the counting shall be done recursively.
	 * @return Number of files in the directory and it's subdirectories.
	 */
	public int countFiles(boolean recursive) {
		count = 0;

		if (recursive) {
			countFilesRecursive(dir);
		} else {
			File[] filesAndDirs = listFiles(dir);

			// count number of files (not directories)
			for (int i = 0; i < filesAndDirs.length; i++) {
				DirSync.getSync().sleepOnPause();
				if (DirSync.getSync().isStopping()) {
					break;
				}

				if (filesAndDirs[i].isFile()) {
					count++;
				}
			}
		}
		return count;
	}

	private void countFilesRecursive(File dir) {
		DirSync.getSync().sleepOnPause();
		if (DirSync.getSync().isStopping()) {
			return;
		}

		// Die eigentliche Methode zum Rekursiv zaehlen: Fuehrt sich selber wieder
		// aus, falls ein Element eine dir ist.
		try {
			File[] filesAndDirs = listFiles(dir);

			for (int i = 0; i < filesAndDirs.length; i++) {
				if (filesAndDirs[i].isDirectory()) {
					countFilesRecursive(filesAndDirs[i]);
					// check recursively for files
				} else {
					count++; // count as file
				}
			}
		} catch (Exception e) {
			log
					.print("Error in DirectoryLister.countRecursive() while listing '"
							+ dir.getAbsolutePath() + "'");
		}
	}

	private File[] listFiles(File dir) {
		File[] filesAndDirs = dir.listFiles();

		if (filesAndDirs == null) {
			filesAndDirs = new File[0];
		}

		if (skipLinks) { // remove links if required
			filesAndDirs = FileTools.removeLinks(filesAndDirs);
		}

		return filesAndDirs;
	}
}