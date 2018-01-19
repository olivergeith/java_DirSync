/*
 * Sync.java
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

package dirsync.sync;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import dirsync.Const;
import dirsync.DirSync;
import dirsync.directory.Directory;
import dirsync.exceptions.IncompleteConfigurationException;
import dirsync.tools.DateTools;
import dirsync.tools.Log;
import dirsync.xml.XmlReader;
import dirsync.xml.XmlWriter;

/**
 * Represents the synchronization.
 * 
 * @author F. Gerbig (fgerbig@users.sourceforge.net)
 */
public class Sync {

	/** The mode of the synchronization: synchronization, or preview */
	public static final int SYNCHRONIZATION = 1;

	/** The mode of the synchronization: synchronization, or preview */
	public static final int PREVIEW = 2;

	/** The state the synchronization is in: start, pause, stop, or stopping */
	public static final int STOP = 0;

	/** The state the synchronization is in: start, pause, stop, or stopping */
	public static final int STOPPING = -1;

	/** The state the synchronization is in: start, pause, stop, or stopping */
	public static final int START = 1;

	/** The state the synchronization is in: start, pause, stop, or stopping */
	public static final int PAUSE = 2;

	/** The errorstate of the synchronization: no error, warning, or error */
	public static final int ERROR_OTHER_DIRECTORY = 3;

	/** The errorstate of the synchronization: no error, warning, or error */
	public static final int ERROR_THIS_DIRECTORY = 2;

	/** The errorstate of the synchronization: no error, warning, or error */
	public static final int WARNING = 1;

	/** The errorstate of the synchronization: no error, warning, or error */
	public static final int NOERROR = 0;

	private static final String WILDCARD_GLOBALLOG = "<global>";

	private static final String WILDCARD_NAME = "<name>";

	/** The directory definitions of the synchronization */
	private Vector dirs = new Vector();

	private int dirCounter;

	private int mode = SYNCHRONIZATION;

	private int state = STOP;

	private int error = NOERROR;

	private Date syncDate;

	private boolean skipLinks = false;

	private boolean writeTimestampBack = false;

	private int maxTimestampDiff = 0;

	/**
	 * Initializes a new synchronization.
	 */
	public Sync() {
		dirs = new Vector();

		Directory dir = new Directory();
		dir.setName("Directory");
		dirs.add(dir);

		mode = SYNCHRONIZATION;
		state = STOP;

		skipLinks = false;
		writeTimestampBack = false;
		maxTimestampDiff = 0;
	}

	/**
	 * Loads a configuration.
	 * 
	 * @param filename
	 *            The name of the file.
	 * @throws Exception
	 */
	public void load(String filename) throws Exception {
		XmlReader xmlReader = new XmlReader(filename);

		dirs = xmlReader.getDirs();

		skipLinks = xmlReader.isSkipLinks();
		writeTimestampBack = xmlReader.isWriteTimestampBack();
		maxTimestampDiff = xmlReader.getMaxTimestampDiff();

		DirSync.setLog(new Log(xmlReader.getLogFileName()));
	}

	/**
	 * Saves a configuration.
	 * 
	 * @param filename
	 *            The name of the file.
	 * @throws Exception
	 */
	public void save(String filename) throws Exception {
		new XmlWriter(filename, DirSync.getLog().getFilename(), dirs);
	}

	/**
	 * Swaps the source and destination directories in all directory
	 * definitions.
	 */
	public void swapSrcDst() {
		String temp;

		for (Iterator iter = dirs.iterator(); iter.hasNext();) {
			// on all dirs
			Directory dir = (Directory) iter.next();

			temp = dir.getSrc(); // swap src and dst
			dir.setSrc(dir.getDst());
			dir.setDst(temp);
		}
	}

	/**
	 * Get the directory counter.
	 * 
	 * @return The directory counter.
	 */
	public int getDirCounter() {
		return dirCounter;
	}

	/**
	 * Starts a preview or a synchronization (depending on mode).
	 * 
	 * @param syncMode
	 *            The mode.
	 */
	public void synchronize(int syncMode) {
		this.mode = syncMode;
		synchronize();
	}

	/**
	 * Synchronizes the given directories one by one.
	 */
	public void synchronize() {
		String globalLogFilename = "";
		try {
			if (DirSync.isGuiMode()) {
				// set cursor to wait cursor
				DirSync.getGui().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			}

			setError(Sync.NOERROR);
			syncDate = new Date();

			DirSync.getLog().print("* Started " + (isPreview() ? "preview of " : "") + "synchronization *", Const.STYLE_SYNC);
			DirSync.getLog().print("");
			DirSync.getLog().print("Global configuration:", Const.STYLE_INFO);
			globalLogFilename = DateTools.replaceWildcards(DirSync.getLog().getFilename(), syncDate);
			DirSync.getLog().print("  Logfile              = \"" + globalLogFilename + "\"", Const.STYLE_CONFIG);
			DirSync.getLog().print("  Write TimeStamp back = " + isWriteTimestampBack(), Const.STYLE_CONFIG);
			DirSync.getLog().print("  Max TimeStamp Diff   = " + getMaxTimestampDiff() + " s", Const.STYLE_CONFIG);
			DirSync.getLog().print("  Skip Symbolic Links  = " + isSkipLinks(), Const.STYLE_CONFIG);
			DirSync.getLog().print("");
			DirSync.getLog().print("Number of directories to synchronize = " + getNumberOfEnabledDirs(), Const.STYLE_INFO);

			// init dirsync progressbar and title
			if (DirSync.isGuiMode()) {
				DirSync.getGui().initSyncProgress(getNumberOfEnabledDirs());
				DirSync.getGui().initDirProgress();
				DirSync.getGui().initCurrentProgress();
				DirSync.getGui().updateTitle();
			}

			dirCounter = 0;
			for (Iterator iter = dirs.iterator(); iter.hasNext();) {

				// if an error has happened it has happened in an other
				// directory
				if (getError() == Sync.ERROR_THIS_DIRECTORY) {
					setError(Sync.ERROR_OTHER_DIRECTORY);
				}

				try {
					Directory dir = (Directory) iter.next();

					if (!dir.isEnabled()) {
						continue;
					}

					dirCounter++;

					if (DirSync.isGuiMode()) {
						// for displaying in the title bar
						DirSync.getGui().setCurrentDirName(dir.getName());
						// set dirsync progressbar
						DirSync.getGui().setSyncProgress((dirCounter - 1) * 100);
						DirSync.getGui().setCurrentProgressText(dir.getName());
					}

					// replace date and time wildcards in destination directory
					dir.setDst(DateTools.replaceWildcards(dir.getDst(), syncDate));

					DirSync.getLog().print("");

					DirSync.getLog().print(
							(DirSync.getSync().isPreview() ? "Preview of d" : "D") + "irectory #" + dirCounter + " '" + dir.getName() + "' started:",
							Const.STYLE_DIR);
					DirSync.getLog().print("  Source Path      = \"" + dir.getSrc() + "\"", Const.STYLE_CONFIG);
					DirSync.getLog().print("  Destination Path = \"" + dir.getDst() + "\"", Const.STYLE_CONFIG);
					DirSync.getLog().print("  With Subfolders  = " + dir.isWithSubfolders(), Const.STYLE_CONFIG);
					DirSync.getLog().print("  Verify           = " + dir.isVerify(), Const.STYLE_CONFIG);

					// replace date and time wildcards in log file
					String log = DateTools.replaceWildcards(dir.getLogfile(), syncDate);
					// replace global log path wildcard in log file
					if (log.indexOf(WILDCARD_GLOBALLOG) != -1) {
						if (globalLogFilename != null 
								&& !globalLogFilename.equals("") 
								&& new File(globalLogFilename) != null 
								&& new File(globalLogFilename).getParent() != null) {
							log = log.replaceAll(WILDCARD_GLOBALLOG, new File(globalLogFilename).getParent());
						} else {
							log = log.replaceAll(WILDCARD_GLOBALLOG, "");
							setError(Sync.WARNING);
							DirSync.getLog().print("Wildcard '" + WILDCARD_GLOBALLOG + "' specified in Logfile but global log not set correctly.", Const.STYLE_WARNING);
						}
					}
					// replace directory definition name wildcard in log file
					log = log.replaceAll(WILDCARD_NAME, dir.getName());
					dir.setLogfile(log); // TODO replace!

					DirSync.getLog().print("  Logfile          = \"" + dir.getLogfile() + "\"", Const.STYLE_CONFIG);

					// empty include means all
					if (dir.getFileInclude().equals("")) {
						dir.setFileInclude("*");
					}
					if (dir.getDirInclude().equals("")) {
						dir.setDirInclude("*");
					}
					// print include mask (if any)
					if (dir.hasFileIncludes()) {
						DirSync.getLog().print("  Include files    = " + dir.getFileInclude(), Const.STYLE_CONFIG);
					}
					// print exclude mask (if any)
					if (dir.hasFileExcludes()) {
						DirSync.getLog().print("  Exclude files    = " + dir.getFileExclude(), Const.STYLE_CONFIG);
					}
					if (dir.hasDirIncludes()) {
						DirSync.getLog().print("  Include dirs     = " + dir.getDirInclude(), Const.STYLE_CONFIG);
					}
					// print exclude mask (if any)
					if (dir.hasDirExcludes()) {
						DirSync.getLog().print("  Exclude dirs     = " + dir.getDirExclude(), Const.STYLE_CONFIG);
					}

					DirSync.getLog().print("");
					DirSync.getLog().print("  Sync mode:", Const.STYLE_INFO);
					DirSync.getLog().print("    All            = " + dir.isCopyAll(), Const.STYLE_CONFIG);
					DirSync.getLog().print("    New            = " + dir.isCopyNew(), Const.STYLE_CONFIG);
					DirSync.getLog().print("    Modified       = " + dir.isCopyModified(), Const.STYLE_CONFIG);
					DirSync.getLog().print("    Larger         = " + dir.isCopyLarger(), Const.STYLE_CONFIG);
					DirSync.getLog().print("    LargerModified = " + dir.isCopyLargerModified(), Const.STYLE_CONFIG);
					DirSync.getLog().print("    Delete Files   = " + dir.isDelFiles(), Const.STYLE_CONFIG);
					DirSync.getLog().print("    Delete Dirs    = " + dir.isDelDirs(), Const.STYLE_CONFIG);

					DirSync.getLog().print("");

					dir.synchronize();

					DirSync.getLog().print("");

					if (isStopping()) {
						DirSync.getLog().print((isPreview() ? "Preview of d" : "D") + "irectory #" + dirCounter + " '" + dir.getName() + "' stopped.",
								Const.STYLE_DIR);
						break;
					}
					DirSync.getLog().print((isPreview() ? "Preview of d" : "D") + "irectory #" + dirCounter + " '" + dir.getName() + "' finished.",
							Const.STYLE_DIR);
					DirSync.getLog().print("");

					// set dirsync progressbar
					if (DirSync.isGuiMode()) {
						DirSync.getGui().setSyncProgress(dirCounter * 100);
					}

				} catch (IncompleteConfigurationException ice) {
					setError(Sync.ERROR_THIS_DIRECTORY);
					DirSync.getLog().print("Skipping " + (isPreview() ? "preview of " : "") + "directory because of incomplete configuration.",
							Const.STYLE_ERROR);
				}
			}

			switch (getError()) {
				case Sync.WARNING: // finished with warnings
					DirSync.getLog().print("* Finished " + (isPreview() ? "preview of " : "") + "synchronization with warnings! *", Const.STYLE_INFO);
					break;

				case Sync.ERROR_THIS_DIRECTORY: // finished with errors
				case Sync.ERROR_OTHER_DIRECTORY:
					DirSync.getLog().print("* Finished " + (isPreview() ? "preview of " : "") + "synchronization with errors! *", Const.STYLE_ERROR);

					// beep
					Toolkit.getDefaultToolkit().beep();

					// show error message
					DirSync.displayError("Finished " + (isPreview() ? "preview of " : "") + "synchronization with errors!");
					break;

				default: // finished without warnings or errors
					DirSync.getLog().print("* Finished " + (isPreview() ? "preview of " : "") + "synchronization *", Const.STYLE_SYNC);
			}

			// quit if "/quit" specified as command line option
			if (DirSync.isOption_quit()) {
				System.exit(0);
			}

		} catch (Throwable e) {
			if (DirSync.getLog() != null) {
				DirSync.getLog().print("A fatal error occured; the program will print debug information and exit.", Const.STYLE_ERROR);
				DirSync.getLog().print("");
				DirSync.getLog().print("*** DEBUG INFORMATION START ***", Const.STYLE_SYNC);
				DirSync.getLog().print("OS:      " + System.getProperty("os.name") + " on " + System.getProperty("os.arch"));
				DirSync.getLog().print(
						"JAVA:    " + System.getProperty("java.vendor") + " Version " + System.getProperty("java.version") + " Class-Version "
								+ System.getProperty("java.class.version"));
				DirSync.getLog().print("PROGRAM: " + Const.VERSION);
				DirSync.getLog().print("ERROR: " + e); // print exception
				StackTraceElement[] trace = e.getStackTrace();
				for (int i = 0; i < trace.length; i++) {
					DirSync.getLog().print("\tat " + trace[i]); // print
					// stacktrace
				}
				DirSync.getLog().print("*** DEBUG INFORMATION END ***", Const.STYLE_SYNC);
				DirSync.getLog().print("");
				DirSync.getLog().print("Please try to reproduce the error and to find out which specific action triggers it;", Const.STYLE_INFO);
				DirSync.getLog().print("then send a description of this error and the debug information to: " + Const.EMAIL, Const.STYLE_INFO);
				DirSync.getLog().print("Thanks in advance.", Const.STYLE_INFO);
				DirSync.getLog().print("");
				DirSync.getLog().print("Exiting...");

				DirSync.displayError("A fatal error occured:\n" + e + ".\n\n" + "The program will write debug information to the global log file and exit.\n\n"
						+ "Please try to reproduce the error and to find out which specific action triggers it;\n"
						+ "then send a description of this error and the debug information to:\n" + Const.EMAIL + "\n\nThanks in advance.");
			}
			e.printStackTrace(System.err);
			System.exit(0);

		} finally {
			if (DirSync.isGuiMode()) {
				// change cursor back to default cursor
				DirSync.getGui().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				DirSync.getGui().setCurrentProgressText("");
				DirSync.getGui().updateTitle();
			}
			setState(Sync.STOP);
		}
	}

	/**
	 * Checks if the mode if the synchronization is preview.
	 * 
	 * @return <code>true</code> if the synchronization is in preview mode,
	 *         <code>false</code> otherwise.
	 */
	public boolean isPreview() {
		return (mode == PREVIEW);
	}

	/**
	 * @return Returns the syncMode.
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * @param syncMode
	 *            The syncMode to set.
	 */
	public void setMode(int syncMode) {
		this.mode = syncMode;
	}

	/**
	 * @return Returns the syncState.
	 */
	public int getState() {
		return state;
	}

	/**
	 * Checks if DirSync is stopping, i.e. if <code>syncState</code> is set to
	 * <code>STOPPING</code>.
	 * 
	 * @return <code>true</code> if DirSync is stopping.
	 */
	public boolean isStopping() {
		return state == STOPPING;
	}

	/**
	 * @param syncState
	 *            The syncState to set.
	 */
	public void setState(int syncState) {
		this.state = syncState;
	}

	/**
	 * @return <code>true</code> if link skipping is enabled.
	 */
	public boolean isSkipLinks() {
		return skipLinks;
	}

	/**
	 * @param skipLinks
	 *            Set to <code>true</code> to enable link skipping.
	 */
	public void setSkipLinks(boolean skipLinks) {
		this.skipLinks = skipLinks;
	}

	/**
	 * @return <code>true</code> if timestampFix is enabled.
	 */
	public boolean isWriteTimestampBack() {
		return writeTimestampBack;
	}

	/**
	 * @param b
	 *            Set to <code>true</code> to enable timestampFix.
	 */
	public void setWriteTimestampBack(boolean b) {
		writeTimestampBack = b;
	}

	/**
	 * @return Returns the timestampDiff.
	 */
	public int getMaxTimestampDiff() {
		return maxTimestampDiff;
	}

	/**
	 * @param maxTimestampDiff
	 *            The timestampDiff to set.
	 */
	public void setMaxTimestampDiff(int maxTimestampDiff) {
		this.maxTimestampDiff = maxTimestampDiff;
	}

	/**
	 * @param maxTimestampDiff
	 *            The timestampDiff to set.
	 */

	public void setMaxTimestampDiff(String maxTimestampDiff) {
		try {
			this.maxTimestampDiff = Integer.parseInt(maxTimestampDiff);
		} catch (NumberFormatException nfe) {
			this.maxTimestampDiff = 0;
		}
	}

	/**
	 * Sleeps if <code>syncState</code> is pause.
	 */
	public void sleepOnPause() {
		while (getState() == PAUSE) {
			try {
				Thread.sleep(Const.PAUSE);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * @param error
	 *            The error to set.
	 */
	public void setError(int error) {
		this.error = error;
	}

	/**
	 * @return Returns the error.
	 */
	public int getError() {
		return error;
	}

	/**
	 * Counts the number of enables directory definitions.
	 * 
	 * @return The number of enables directory definitions.
	 */
	public int getNumberOfEnabledDirs() {
		int count = 0;
		for (Iterator iter = dirs.iterator(); iter.hasNext();) {
			Directory dir = (Directory) iter.next();

			if (dir.isEnabled()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Set the enables status of all directory definitions to the given value.
	 * 
	 * @param enabled
	 *            The value to set in all directory definitions.
	 */
	public void enableAllDirs(boolean enabled) {
		for (Iterator iter = dirs.iterator(); iter.hasNext();) {
			Directory dir = (Directory) iter.next();
			dir.setEnabled(enabled);
		}
	}

	/**
	 * Copy the options of the given directory definitions to ALL directory
	 * definitions (including itself).
	 * 
	 * @param dirWithOptions
	 *            The directory definition with the options to copy.
	 */
	public void copyOptionsToAllDirs(Directory dirWithOptions) {
		for (Iterator iter = dirs.iterator(); iter.hasNext();) {
			Directory dir = (Directory) iter.next();
			dir.copyOptions(dirWithOptions);
		}
	}

	/**
	 * Copy the options of the given directory definitions to all ENABLED
	 * directory definitions (including itself).
	 * 
	 * @param dirWithOptions
	 *            The directory definition with the options to copy.
	 */
	public void copyOptionsToEnabledDirs(Directory dirWithOptions) {
		for (Iterator iter = dirs.iterator(); iter.hasNext();) {
			Directory dir = (Directory) iter.next();
			if (dir.isEnabled()) {
				dir.copyOptions(dirWithOptions);
			}
		}
	}

	/**
	 * @return Returns the dirs.
	 */
	public Vector getDirs() {
		return dirs;
	}

}
