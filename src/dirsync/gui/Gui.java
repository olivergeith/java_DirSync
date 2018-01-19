/*
 * Gui.java
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

package dirsync.gui;

import javax.swing.*;

import dirsync.Const;
import dirsync.DirSync;

/**
 * The graphical user interface (GUI).
 * @author F. Gerbig (fgerbig@users.sourceforge.net) 
 */
public class Gui extends GuiMethods {

	/**
	 * Initializes a new DirSync GUI.
	 * @param useSystemLookAndFeel The look and feel to use.
	 */
	public Gui(boolean useSystemLookAndFeel) {

		try {
			if (useSystemLookAndFeel) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} else {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}
		} catch (Exception e) {
		}

		init();
		
		if (useSystemLookAndFeel) {
			optionsLookandfeelCheckBoxMenuItem.setSelected(false);
		} else {
			optionsLookandfeelCheckBoxMenuItem.setSelected(true);
		}

		if (DirSync.getProperties().getProperty("dirsync.NIO", "false").equals("true")) {
			optionsNIOCheckBoxMenuItem.setSelected(true);
		} else {
			optionsNIOCheckBoxMenuItem.setSelected(false);
		}

		newConfig();
		initOutputAreaStyles();

		setVisible(true);
	}

	/**
	 * Displays the help dialog.
	 */
	public static void displayHelpDialog() {
		JOptionPane.showMessageDialog(null, Const.HELP, "HELP",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Displays the usage dialog.
	 */
	public static void displayUsageDialog() {
		JOptionPane.showMessageDialog(null, Const.USAGE, "USAGE",
				JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Displays an error message.
	 * @param s The error message.
	 */
	public static void displayErrorDialog(String s) {
		JOptionPane.showMessageDialog(null, s, "ERROR",
				JOptionPane.ERROR_MESSAGE);
	}
}
