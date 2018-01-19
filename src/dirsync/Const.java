/*
 * Const.java
 * 
 * Copyright (C) 2006 F. Gerbig (fgerbig@users.sourceforge.net)
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General public License as 
 * published by the Free Software Foundation; either version 2 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General public License for more details.
 * 
 * You should have received a copy of the GNU General public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package dirsync;

import javax.swing.ImageIcon;

/**
 * Contains the constants used in DirSync. 
 * @author F. Gerbig (fgerbig@users.sourceforge.net)
 */
public abstract class Const {
	
	/** The program version. */ // TODO program version 
	public static final String VERSION = "DirectorySynchronize 0.91";

	//Don't let anyone instantiate this class.
	private Const(){
	}

    /** The file extension for config files. */
	public static final String FILE_EXTENSION = "dsc";

	/** The name of the properties file. */
	public static final String PROPERTIES_FILENAME = "dirsync.properties";
	
    /** The number of milliseconds to sleep while pause. */
    public static final long PAUSE = 500;

    /** true if the operating system is MS Windows, false otherwise. */
	public static final boolean runningUnderWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

	/** The copyright message. */
	public static final String COPYRIGHT = "(c) 2002-2007 by E. Gerber, F. Gerbig, and T. Groetzner.\nThanks to T. Brixel, D. Caravana, and R. Williams.";

	/** The support email address. */
	public static final String EMAIL = "fgerbig@users.sourceforge.net";

	/** The homepage location (URL). */
	public static final String HOMEPAGE = "http://directorysync.sourceforge.net";

	/** The "about" message. */
	public static final String MESSAGE = "Homepage: "
	+ HOMEPAGE
	+ "\n"
	+ "Contact:  "
	+ EMAIL
	+ "\n\n"
	+ "This program is governed by the GNU License (GPL) either version 2 of the License, or any later version.\n\n"
	+ "- 'Help.java' from J2SE example 'MetalWorks.java' (c) 2002 Sun Microsystems.\n"
	+ "- 'SwingWorker.java' from 'The Java Tutorial' (c) 2003 Sun Microsystems.\n"
	+ "- Icons from the Kommon Desktop Environment (KDE) released under the GPL.\n\n"
	+ "For more help on DirectorySynchronize please consult the online help or take a look at the homepage.";

	/** The help message. */
	public static String HELP = MESSAGE + "\n\n"
	+ "For help on using DirectorySynchronize try '"
	+ DirSync.getOptionsMarker() + "usage'.\n";

	/** The usage message. */
	public static String USAGE = "Usage:\n'dirsync ["
	+ DirSync.getOptionsMarker()
	+ "help] ["
	+ DirSync.getOptionsMarker()
	+ "usage] ["
	+ DirSync.getOptionsMarker()
	+ "sync] ["
	+ DirSync.getOptionsMarker()
	+ "preview] ["
	+ DirSync.getOptionsMarker()
	+ "quit] ["
	+ DirSync.getOptionsMarker()
	+ "nogui] <configfile>'\n"
	+ "  "
	+ DirSync.getOptionsMarker()
	+ "help    Display help on homepage, contact, and license.\n"
	+ "  "
	+ DirSync.getOptionsMarker()
	+ "usage   Displays this screen.\n"
	+ "  "
	+ DirSync.getOptionsMarker()
	+ "sync    Starts a synchronization if a configuration file is specified.\n"
	+ "  "
	+ DirSync.getOptionsMarker()
	+ "preview Starts a preview rather than a synchronization if a configuration\n           file is specified.\n"
	+ "  "
	+ DirSync.getOptionsMarker()
	+ "quit    Quits the program after the preview or the synchronization.\n"
	+ "  "
	+ DirSync.getOptionsMarker()
	+ "nogui   Start DirectorySynchronize in console mode and start synchronization.\n\n"
	+ "Usage examples:\n" + "  * Start in GUI mode:\n"
	+ "      dirsync\n" + "  * Start synchronization in GUI mode and quit afterwards:\n"
	+ "      dirsync " + DirSync.getOptionsMarker() + "sync " + DirSync.getOptionsMarker() + "quit <configfile>\n"
	+ "  * Start synchronization in command line mode:\n"
	+ "      dirsync " + DirSync.getOptionsMarker() + "nogui <configfile>\n";

	/** The style token for the sync style. */
	public static final String STYLE_SYNC = "sync";
	/** The style token for the directory style. */
	public static final String STYLE_DIR = "dir";
	/** The style token for the subdirectory style. */
	public static final String STYLE_SUBDIR = "subdir";
	/** The style token for the info style. */
	public static final String STYLE_INFO = "info";
	/** The style token for the list style. */
	public static final String STYLE_CONFIG = "list";
	/** The style token for the action style. */
	public static final String STYLE_ACTION = "action";
	/** The style token for the warning style. */
	public static final String STYLE_WARNING = "warning";
	/** The style token for the error style. */
	public static final String STYLE_ERROR = "error";

	
	/** The icon for the sync style. */
	public static final ImageIcon ICON_SYNC = new ImageIcon(Const.class.getResource("/icons/icon_sync.png"));
	/** The icon for the directory style. */
	public static final ImageIcon ICON_DIR = new ImageIcon(Const.class.getResource("/icons/icon_dir.png"));
	/** The icon for the subdirectory style. */
	public static final ImageIcon ICON_SUBDIR = new ImageIcon(Const.class.getResource("/icons/icon_subdir.png"));
	/** The icon for the info style. */
	public static final ImageIcon ICON_INFO = new ImageIcon(Const.class.getResource("/icons/icon_info.png"));
	/** The icon for the config style. */
	public static final ImageIcon ICON_CONFIG = new ImageIcon(Const.class.getResource("/icons/icon_config.png"));
	/** The icon for the action style. */
	public static final ImageIcon ICON_ACTION = new ImageIcon(Const.class.getResource("/icons/icon_action.png"));
	/** The icon for the warning style. */
	public static final ImageIcon ICON_WARNING = new ImageIcon(Const.class.getResource("/icons/icon_warning.png"));
	/** The icon for the error style. */
	public static final ImageIcon ICON_ERROR = new ImageIcon(Const.class.getResource("/icons/icon_error.png"));
	/** The file icon. */
	public static final ImageIcon ICON_FILE = new ImageIcon(Const.class.getResource("/icons/icon_file.png"));
}
