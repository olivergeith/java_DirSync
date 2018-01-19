/*
 * XmlWriter.java
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

import java.io.*;
import java.util.*;

import dirsync.DirSync;
import dirsync.directory.Directory;


/**
 * Writes the given data to a XML config file.
 * 
 * @author F. Gerbig (fgerbig@users.sourceforge.net)
 */
public class XmlWriter {

	private final int INDENTATION = 2;

	private OutputStream out;

	private Stack stack;

	private int indent;

	private String tag;

	/**
	 * Instantiates and initializes a new XMLWriter.
	 * 
	 * @param filename
	 * @param logFile
	 * @param dirs
	 * 
	 * @throws IOException
	 */
	public XmlWriter(String filename, String logFile, Vector dirs) throws IOException {
		out = new FileOutputStream(filename);
		stack = new Stack();
		indent = 0;

		writeProlog();

		tag = Xml.TAG_ROOT;
		writeDTD(tag);
		tag += addAttr(Xml.ATTR_LOGFILE, logFile);
		tag += addAttr(Xml.ATTR_TIMESTAMPWRITEBACK, DirSync.getSync().isWriteTimestampBack());
		tag += addAttr(Xml.ATTR_TIMESTAMPDIFF, DirSync.getSync().getMaxTimestampDiff());
		tag += addAttr(Xml.ATTR_SKIP_LINKS, DirSync.getSync().isSkipLinks());
		writeStartTag(tag);

		for (Iterator iter = dirs.iterator(); iter.hasNext();) {
			Directory dir = (Directory) iter.next();

			tag = Xml.TAG_DIR;
			tag += addAttr(Xml.ATTR_NAME, dir.getName());
			tag += addAttr(Xml.ATTR_SRC, dir.getSrc());
			tag += addAttr(Xml.ATTR_DST, dir.getDst());
			tag += addAttr(Xml.ATTR_WITHSUBFOLDERS, dir.isWithSubfolders());
			tag += addAttr(Xml.ATTR_VERIFY, dir.isVerify());
			tag += addAttr(Xml.ATTR_FILE_INCLUDE, dir.getFileInclude());
			tag += addAttr(Xml.ATTR_FILE_EXCLUDE, dir.getFileExclude());
			tag += addAttr(Xml.ATTR_DIR_INCLUDE, dir.getDirInclude());
			tag += addAttr(Xml.ATTR_DIR_EXCLUDE, dir.getDirExclude());
			tag += addAttr(Xml.ATTR_LOGFILE, dir.getLogfile());
			tag += addAttr(Xml.ATTR_SYNC_ALL, dir.isCopyAll());
			tag += addAttr(Xml.ATTR_SYNC_LARGER, dir.isCopyLarger());
			tag += addAttr(Xml.ATTR_SYNC_LARGERMODIFIED, dir
					.isCopyLargerModified());
			tag += addAttr(Xml.ATTR_SYNC_MODIFIED, dir.isCopyModified());
			tag += addAttr(Xml.ATTR_SYNC_NEW, dir.isCopyNew());
			tag += addAttr(Xml.ATTR_DEL_FILES, dir.isDelFiles());
			tag += addAttr(Xml.ATTR_DEL_DIRS, dir.isDelDirs());
			writeEmptyTag(tag);
		}

		writeEndTag();
	}

	/**
	 * Encodes the string using XML entities. The five standard XML entities are
	 * replaced and additionally backslashes and linefeeds are encoded.
	 * 
	 * @param s
	 *            The string to encode.
	 * 
	 * @return The encoded string.
	 */
	private String encode(String s) {
		String t = "";

		if (s == null) {
			return t;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == '<') {
				t += "&lt;";
			} else if (c == '>') {
				t += "&gt;";
			} else if (c == '&') {
				t += "&amp;";
			} else if (c == '"') {
				t += "&quot;";
			} else if (c == '\'') {
				t += "&apos;";
			} else {
				t += c;
			}
		}

		return t;
	}

	private void writeProlog() throws IOException {
		writeln("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	}

	private void writeDTD(String tag) throws IOException {
		writeln("<!DOCTYPE " + tag + " [");
		writeln("<!ELEMENT " + Xml.TAG_ROOT + " (directory*)>");
		writeln("<!ATTLIST " + Xml.TAG_ROOT + " " + Xml.ATTR_LOGFILE
				+ " CDATA ''>");
		writeln("<!ATTLIST " + Xml.TAG_ROOT + " " + Xml.ATTR_TIMESTAMPWRITEBACK
				+ " (true|false) 'false'>");
		writeln("<!ATTLIST " + Xml.TAG_ROOT + " " + Xml.ATTR_TIMESTAMPDIFF
				+ " CDATA '0'>");
		writeln("<!ATTLIST " + Xml.TAG_ROOT + " " + Xml.ATTR_SKIP_LINKS
				+ " (true|false) 'false'>");
		writeln("");
		writeln("<!ELEMENT " + Xml.TAG_DIR + " EMPTY>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_NAME
				+ " CDATA #REQUIRED>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_SRC
				+ " CDATA #REQUIRED>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_DST
				+ " CDATA #REQUIRED>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_WITHSUBFOLDERS
				+ " (true|false) 'false'>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_VERIFY
				+ " (true|false) 'false'>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_FILE_INCLUDE
				+ " CDATA ''>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_FILE_EXCLUDE
				+ " CDATA ''>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_DIR_INCLUDE
				+ " CDATA ''>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_DIR_EXCLUDE
				+ " CDATA ''>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_LOGFILE
				+ " CDATA ''>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_SYNC_ALL
				+ " (true|false) 'false'>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_SYNC_LARGER
				+ " (true|false) 'false'>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_SYNC_MODIFIED
				+ " (true|false) 'false'>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_SYNC_LARGERMODIFIED
				+ " (true|false) 'false'>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_SYNC_NEW
				+ " (true|false) 'false'>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_DEL_FILES
				+ " (true|false) 'false'>");
		writeln("<!ATTLIST " + Xml.TAG_DIR + " " + Xml.ATTR_DEL_DIRS
				+ " (true|false) 'false'>");
		writeln("]>");
	}

	private void write(String s) throws IOException {
		out.write(s.toString().getBytes("UTF-8"));
	}

	private void writeln() throws IOException {
		write(System.getProperty("line.separator"));
	}

	private void writeln(String s) throws IOException {
		write(s);
		writeln();
	}

	private void writelnText(String s) throws IOException {
		indent();
		writeln(s);
	}

	private void writeStartTag(String s) throws IOException {
		writelnText("<" + s + ">");
		indent += INDENTATION;
		stack.push(firstWord(s)); // Attribute weglassen
	}

	private String firstWord(String s) {
		String[] ss = s.split(" ");
		return ss[0];
	}

	private void writeEmptyTag(String s) throws IOException {
		writelnText("<" + s + "/>");
	}

	private final void writeEndTag() throws IOException {
		indent -= INDENTATION;
		writelnText("</" + stack.pop() + ">");
	}

	private void indent() throws IOException {
		for (int i = 0; i < indent; i++) {
			write(" ");
		}
	}

	private String addAttr(String attr, String value) {
		return " " + attr + "=\"" + encode(value) + "\"";
	}

	private String addAttr(String attr, int value) {
		return " " + attr + "='" + encode(value + "") + "'";
	}

	private String addAttr(String attr, boolean value) {
		return " " + attr + "=\"" + encode(value + "") + "\"";
	}
}