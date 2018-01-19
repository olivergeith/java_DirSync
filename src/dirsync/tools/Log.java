/*
 * Log.java
 * 
 * Copyright (C) 2002 E. Gerber  
 * Copyright (C) 2003, 2005, 2006 F. Gerbig (fgerbig@users.sourceforge.net)
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

package dirsync.tools;

import java.io.*;
import java.util.*;
import java.text.*;

import dirsync.DirSync;
import dirsync.exceptions.WarningException;
import dirsync.exceptions.IncompleteConfigurationException;

/**
 * Represents a log file. Contains methods to create or continue a log file.
 * 
 * @author E. Gerber , F. Gerbig (fgerbig@users.sourceforge.net)
 */
public class Log {

	// the date formatter
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"[yyyy.MM.dd HH:mm:ss] ");

	// whether actions are logged.
	private boolean enabled = false;

	// The file where to log what's being done.
	private String filename = "";

	private File file;

	private Writer out;

	/**
	 * Initialize a new Log.
	 * 
	 * @param filename
	 *            The filename of this log.
	 * @throws IncompleteConfigurationException
	 * @throws WarningException
	 */
	public Log(String filename) throws IncompleteConfigurationException, WarningException {
		this.filename = filename;
		// disable log if no filename was specified
		this.enabled = !filename.equals("");

		try {
			file = new File(filename);

			if (enabled) {

				if (!file.exists()) { // logfile doesn't exist

					if (file.getParentFile() != null) {
						// create directories
						file.getParentFile().mkdirs(); 
					}

					if (!file.createNewFile()) { // create logfile
						DirSync.printError("    can't create logfile '"
								+ file.getAbsolutePath() + "'!");
						throw new IncompleteConfigurationException();
					}
				}

				if (!file.canWrite()) { // logfile is read-only
					DirSync.printError("    can't write to logfile '"
							+ file.getAbsolutePath() + "'!");
					throw new IncompleteConfigurationException();
				}

				if (!file.isFile()) { // logfile is a directory
					DirSync.printError("    logfile '" + file.getAbsolutePath()
							+ "' isn't a file!");
					throw new IncompleteConfigurationException();
				}
				
				out = new BufferedWriter(new FileWriter(file));
			}
		} catch (IOException e) {
			throw new WarningException("Log file '" + filename + "' could not be created.");
		}
	}

	/**
	 * Writes a message with date and time to the log. If echo is enabled the
	 * message is echoed to the console, too.
	 * 
	 * @param message
	 *            The message to write.
	 */
	public void print(String message) {
		print(message, null);
	}

	/**
	 * Writes a message with date and time to the log. If echo is enabled the
	 * message is echoed to the console, too.
	 * 
	 * @param message
	 *            The message to write.
	 * @param style 
	 *            The style to use for the message.
	 */

	public void print(String message, String style) {
		// echo to console only if no gui selected and only messages to glogal log
		if (DirSync.isOption_noGui() && (DirSync.getLog() == this)) {
			System.out.println(message);
		}

		if (DirSync.getGui() != null) {
			DirSync.getGui().print(message, style);
		}
		
		try {
			if (enabled) {
				out.write(DATE_FORMAT.format(new Date()) + message + System.getProperty("line.separator"));
				out.flush();
			}
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	/**
	 * Close the log on garbage collection.
	 * 
	 * @throws Throwable
	 */
	protected void finalize() throws Throwable {
		out.close();
		super.finalize();
	}

	/**
	 * Gets the file name of the log.
	 * @return Returns the filename.
	 */
	public String getFilename() {
		return filename;
	}

}