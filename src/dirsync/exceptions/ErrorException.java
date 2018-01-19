/*
 * ErrorException.java
 * 
 * Copyright (C) 2004 F. Gerbig (fgerbig@users.sourceforge.net)
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
 * This exception is thrown if a synchronization generates an error.
 * 
 * @author F. Gerbig (fgerbig@users.sourceforge.net)
 */
public class ErrorException extends Exception {

	/**
	 * Initializes a new <code>ErrorException</code>.
	 */
	public ErrorException() {
		super();
	}

	/**
	 * Initializes a new <code>ErrorException</code>.
	 * @param message
	 */
	public ErrorException(String message) {
		super(message);
	}

	/**
	 * Initializes a new <code>ErrorException</code>.
	 * @param cause
	 */
	public ErrorException(Throwable cause) {
		super(cause);
	}

	/**
	 * Initializes a new <code>ErrorException</code>.
	 * @param message
	 * @param cause
	 */
	public ErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
