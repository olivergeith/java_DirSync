/*
 * InclompleteConfigurationException.java
 * 
 * Copyright (C) 2002 E. Gerber  
 * Copyright (C) 2003 F. Gerbig (fgerbig@users.sourceforge.net)
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

package dirsync.exceptions;

/**
 * This exception is thrown if a directory has an incomplete configuration.
 * 
 * @author F. Gerbig (fgerbig@users.sourceforge.net)
 */
public class IncompleteConfigurationException extends Exception {

	/**
	 * Initializes a new <code>InclompleteConfigurationException</code>.
	 */
	public IncompleteConfigurationException() {
		super();
	}

	/**
	 * Initializes a new <code>InclompleteConfigurationException</code>.
	 * @param message
	 */
	public IncompleteConfigurationException(String message) {
		super(message);
	}

	/**
	 * Initializes a new <code>InclompleteConfigurationException</code>.
	 * @param cause
	 */
	public IncompleteConfigurationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Initializes a new <code>InclompleteConfigurationException</code>.
	 * @param message
	 * @param cause
	 */
	public IncompleteConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
}
