/*
 * DirSync.java
 *
 * Copyright (C) 2002 E. Gerber
 * Copyright (C) 2005 T. Groetzner 
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

package dirsync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


import dirsync.gui.Gui;
import dirsync.sync.Sync;
import dirsync.tools.Log;

/**
 * The main class of DirSync.
 * 
 * @author E. Gerber , F. Gerbig (fgerbig@users.sourceforge.net)
 */
public class DirSync {

	private static Sync sync = null;

	// The global log-Objekt
	private static Log log;

	private static Gui gui = null;

	private static Properties properties;

	private static boolean option_help;

	private static boolean option_usage;

	private static boolean option_noGui;

	private static boolean option_preview;

	private static boolean option_sync;

	private static boolean option_quit;

	private static String[] parseOptions(String[] args) {

		option_help = false;
		option_usage = false;
		option_noGui = false;
		option_preview = false;
		option_sync = false;
		option_quit = false;

		List options = new ArrayList(Arrays.asList(args));

		for (Iterator iter = options.iterator(); iter.hasNext();) {
			// all arguments
			String option = ((String) iter.next()).toLowerCase();

			if (option.startsWith(DirSync.getOptionsMarker())) {
				// is option ?
				option = option.substring(1);
				iter.remove(); // remove option from arguments

				if (option.equals("?") || option.equalsIgnoreCase("h")
						|| option.equalsIgnoreCase("help")) {
					option_help = true;
				} else if (option.equalsIgnoreCase("usage")) {
					option_usage = true;
				} else if (option.equalsIgnoreCase("nogui")) {
					option_noGui = true;
				} else if (option.equalsIgnoreCase("preview")) {
					option_preview = true;
				} else if (option.equalsIgnoreCase("sync")) {
					option_sync = true;
				} else if (option.equalsIgnoreCase("quit")) {
					option_quit = true;
				} else {
					DirSync.displayError("Unknown option '" + option + "'.");
					option_usage = true;
				}
			}
		}
		return (String[]) options.toArray(new String[options.size()]);
	}

	/**
	 * The main class of DirSync.
	 * 
	 * @param args
	 *            The command line argguments.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(Const.VERSION);
		System.out.println(Const.COPYRIGHT);
		System.out.println();

		DirSync.loadProperties();
		args = parseOptions(args);

		// help or usage?
		if (option_help) {
			DirSync.displayHelp();
		} else if (option_usage) {
			DirSync.displayUsage();
		}

		// both of 'preview' or 'sync' specified
		if (option_preview && option_sync) {
			DirSync
					.displayError("Please specify exactly one of the arguments 'preview' or 'sync'.");
			DirSync.displayUsage();
		}

		log = new Log(""); // log is always needed
		sync = new Sync(); // sync is always needed
		
		switch (args.length) {

		case 0: // no configuration specified => gui
			if (option_noGui) {
				System.out.println("Error: No configuration file specified.");
				DirSync.displayUsage();
			} else {
				gui = new Gui(properties.getProperty(
						"dirsync.gui.systemlookandfeel").equalsIgnoreCase(
						"true"));
			}
			break;

		case 1: // configuration specified
			if (option_noGui) {
				try {
					sync.load(args[0]);

					if (option_preview) {
						getSync().synchronize(Sync.PREVIEW); // start
						// preview
					} else {
						// start synchronization
						getSync().synchronize(Sync.SYNCHRONIZATION); 
					}

				} catch (Exception e) {
					System.err.println("Error while loading configuration '"
							+ args[0] + "'.");
				}

			} else { // gui
				gui = new Gui(getProperties().getProperty(
						"dirsync.gui.systemlookandfeel").equalsIgnoreCase(
						"true"));

				try {
					File file = (new File(args[0])).getCanonicalFile();

					sync.load(args[0]);
					gui.initConfig(); // delete current loaded config

					// remember directory and file
					gui.setCurrentConfig(file);
					gui.updateTitle();

					if (option_preview) {
						gui.previewStart(); // start preview
					}
					if (option_sync) {
						gui.synchronizationStart(); // start synchronization
					}

				} catch (Exception e) {
					gui.setCurrentConfig(null);
					displayError("Error while loading configuration '"
							+ args[0] + "'.");
				}
			}
			break;

		default:
			displayError("Too many arguments.");
			displayUsage();
		}
	}

	/**
	 * Returns whether the progam is in GUI mode.
	 * 
	 * @return boolean <code>true</code> if the program is in GUI mode,
	 *         <code>false</code> else.
	 */
	public static boolean isGuiMode() {
		return !((option_noGui) || (gui == null));
	}

	/** Displays the help message. */
	public static void displayHelp() {
		System.out.println(Const.HELP);
		if (!option_noGui) {
			Gui.displayHelpDialog();
		}
		System.exit(0);
	}

	/** Displays the usage message. */
	public static void displayUsage() {
		System.out.println(Const.USAGE);
		if (!option_noGui) {
			Gui.displayUsageDialog();
		}
		System.exit(1);
	}

	/** 
	 * Displays an error message.
	 * @param s The error message. 
	 */
	public static void displayError(String s) {
		System.out.println("ERROR: " + s + "\n");
		if (!option_noGui) {
			Gui.displayErrorDialog(s);
		}
	}

	/**
	 * Prints an error message to the log.
	 * @param message The error message.
	 */
	public static void printError(String message) {
		log.print(message, Const.STYLE_ERROR);

		if (!isGuiMode()) {
			log.print(message);
		}
	}

	/**
	 * Set the global log.
	 * @param log The global log.
	 */
	public static void setLog(Log log) {
		DirSync.log = log;
	}

	/**
	 * Get the global log.
	 * @return The global log.
	 */
	public static Log getLog() {
		return log;
	}

	/**
	 * Get the GUI.
	 * @return The GUI.
	 */
	public static Gui getGui() {
		return gui;
	}

	/**
	 * Get the properties.
	 * @return The properties.
	 */
	public static Properties getProperties() {
		return properties;
	}

	/**
	 * Set the quit option.
	 * @param option_quit If set to <code>true</code> the program will quit after the current operation.
	 */
	public static void setOption_quit(boolean option_quit) {
		DirSync.option_quit = option_quit;
	}

	/**
	 * Get the setting of the quit option.
	 * @return <code>true</code> if the option is enabled, <code>false</code> otherwise.
	 */
	public static boolean isOption_quit() {
		return option_quit;
	}

	/**
	 * Get the character preceding a command line option on this platform.
	 * @return The character preceding a command line option.
	 */
	public static String getOptionsMarker() {
		return(Const.runningUnderWindows ? "/" : "-");
	}

	/**
	 * Loads the properties.
	 * @throws Exception
	 */
	public static void loadProperties() throws Exception {

		// search properties at specified location or in current directory
		String filename = new File(System.getProperty("dirsync.home"),
				Const.PROPERTIES_FILENAME).getCanonicalPath();

		// init properties
		properties = new Properties();

		// if program properties exist, load them
		if (new File(filename).exists()) {
			properties.load(new FileInputStream(filename));
		}

		// if no default for the look and feel of the gui is given
		// default to java look and feel
		properties.put("dirsync.gui.systemlookandfeel", properties.getProperty(
				"dirsync.gui.systemlookandfeel", "false"));

		// if no default for the usage of NIO is given default to old copy method
		properties.put("dirsync.NIO", properties.getProperty(
				"dirsync.NIO", "false"));

		// make sure that parameter exists
		properties.put("dirsync.config.path", properties.getProperty(
				"dirsync.config.path", ""));
	}

	/**
	 * Saves the properties. 
	 */
	public static void saveProperties() {
		try { // write properties to disk
			String filename = new File(System.getProperty("dirsync.home"),
					Const.PROPERTIES_FILENAME).getCanonicalPath();
			FileOutputStream out = new FileOutputStream(filename);
			properties.store(out, "- Properties of DirSync -");
			out.close();
		} catch (Exception e) {
		}
	}

	/**
	 * Set the synchronization.
	 * @param sync The synchronization.
	 */
	public static void setSync(Sync sync) {
		DirSync.sync = sync;
	}

	/**
	 * Gets the synchronization.
	 * @return The synchronization.
	 */
	public static Sync getSync() {
		return sync;
	}

	/**
	 * Check if the option "no GUI" is enabled.
	 * @return Returns the option_noGui.
	 */
	public static boolean isOption_noGui() {
		return option_noGui;
	}

}
