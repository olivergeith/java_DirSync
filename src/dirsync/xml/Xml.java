/*
 * Xml.java
 * 
 * Copyright (C) 2002, 2003, 2005, 2006 F. Gerbig (fgerbig@users.sourceforge.net)
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

package dirsync.xml;

/**
 * Constants for XML tags and attributes.
 * 
 * @author F. Gerbig (fgerbig@users.sourceforge.net)
 */
abstract class Xml {

	//Don't let anyone instantiate this class.
	private Xml() {
	}

	static final String TAG_ROOT = "dirsync";

	static final String TAG_DIR = "directory";

	static final String ATTR_LOGFILE = "logfile";

	static final String ATTR_NAME = "name";

	static final String ATTR_SRC = "src";

	static final String ATTR_DST = "dst";

	static final String ATTR_WITHSUBFOLDERS = "withsubfolders";

	static final String ATTR_VERIFY = "verify";

	static final String ATTR_TIMESTAMPWRITEBACK = "timestampwriteback";

	static final String ATTR_TIMESTAMPDIFF = "timestampdiff";

	static final String ATTR_SKIP_LINKS = "skiplinks";

	static final String ATTR_FILE_INCLUDE = "include";

	static final String ATTR_FILE_EXCLUDE = "exclude";

	static final String ATTR_DIR_INCLUDE = "dirinclude";

	static final String ATTR_DIR_EXCLUDE = "direxclude";

	static final String ATTR_SYNC_NEW = "copynew";

	static final String ATTR_SYNC_MODIFIED = "copymodified";

	static final String ATTR_SYNC_LARGER = "copylarger";

	static final String ATTR_SYNC_LARGERMODIFIED = "copylargermodified";

	static final String ATTR_SYNC_ALL = "copyall";

	static final String ATTR_DEL_FILES = "delfiles";

	static final String ATTR_DEL_DIRS = "deldirs";
}