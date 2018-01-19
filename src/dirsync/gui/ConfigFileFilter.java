/*
 * ConfigFileFilter.java
 * 
 * Copyright (C) 2003, 2006 F. Gerbig (fgerbig@users.sourceforge.net)
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

package dirsync.gui;
import java.io.File;
import javax.swing.filechooser.*;

import dirsync.Const;

/**
 * Shows only files with the configuration extension of DirSync.
 *  
 * @author F. Gerbig (fgerbig@users.sourceforge.net)
 */
public class ConfigFileFilter extends FileFilter {
	
	/**
	 * Returns if the given File shall be shown in a file chooser.
	 * 
	 * @param f The File in question.
	 * @return <code>true</code> if the file shall be shown, <code>false</code> else.
	 */
	public boolean accept(File f) {
		// show all directories
		if (f.isDirectory()) {
			return true;
		}

		// show all files with the right extension
		if (f.getAbsolutePath().toLowerCase().endsWith("." + Const.FILE_EXTENSION)) {
			return true;
		}

		// hide everything else (files with a wrong extension)
		return false;
	}

	/**
	 * Returns a description for this file filter.
	 * 
	 * @return The description for this file filter.
	 */
	public String getDescription() {
		return "DirSync config files (*." + Const.FILE_EXTENSION + ")";
	}
}
