/*
 * XmlReader.java
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
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import dirsync.directory.Directory;


/**
 * Reads a XML config file. The data can be retrieved via the various getter
 * methods.
 * 
 * @author F. Gerbig (fgerbig@users.sourceforge.net)
 */
public class XmlReader extends DefaultHandler {

	private String logFileName = "";

	private boolean skipLinks = false;

	private boolean writeTimestampBack = false;

	private int maxTimestampDiff = 0;

	private Vector dirs = new Vector();

	/**
	 * Reads data from the given XML file. The data can be retreived via the
	 * various getter methods.
	 * 
	 * @param filename
	 *            The filename of the XML fil to read the data from.
	 * @throws Exception
	 */
	public XmlReader(String filename) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();

		factory.setNamespaceAware(false);
		factory.setValidating(true);

		SAXParser parser = factory.newSAXParser();
		parser.parse(new FileInputStream(filename), this);
	}

	/**
	 * SAX method called for every XML element start.
	 * 
	 * @param namespaceURI
	 *            The namespace of this XML element (if the parser is namespace
	 *            aware).
	 * @param localname
	 *            The localname of this XML element (if the parser is namespace
	 *            aware).
	 * @param qName
	 *            The name of this XML element (if the parser is NOT namespace
	 *            aware).
	 * @param atts
	 *            The XML elements attributes.
	 * @throws SAXException
	 */
	public void startElement(String namespaceURI, String localname,
			String qName, Attributes atts) throws SAXException {

		String att;
		Directory dir;

		System.out.println();
		if (qName.equals(Xml.TAG_ROOT)) {
			att = (atts.getValue(Xml.ATTR_LOGFILE) != null) ? atts
					.getValue(Xml.ATTR_LOGFILE) : "";
			logFileName = att;

			att = (atts.getValue(Xml.ATTR_SKIP_LINKS) != null) ? atts
					.getValue(Xml.ATTR_SKIP_LINKS) : "false";
			skipLinks = Boolean.valueOf(att).booleanValue();

			att = (atts.getValue(Xml.ATTR_TIMESTAMPWRITEBACK) != null) ? atts
					.getValue(Xml.ATTR_TIMESTAMPWRITEBACK) : "false";
			writeTimestampBack = Boolean.valueOf(att).booleanValue();

			att = (atts.getValue(Xml.ATTR_TIMESTAMPDIFF) != null) ? atts
					.getValue(Xml.ATTR_TIMESTAMPDIFF) : "0";
			maxTimestampDiff = Integer.decode(att).intValue();

			return;
		}

		if (qName.equals(Xml.TAG_DIR)) {
			try {
				dir = new Directory();
			} catch (Exception e) {
				throw new Error(e);
			}

			att = (atts.getValue(Xml.ATTR_NAME) != null) ? atts
					.getValue(Xml.ATTR_NAME) : "";
			dir.setName(att);

			att = (atts.getValue(Xml.ATTR_SRC) != null) ? atts
					.getValue(Xml.ATTR_SRC) : "";
			dir.setSrc(att);

			att = (atts.getValue(Xml.ATTR_DST) != null) ? atts
					.getValue(Xml.ATTR_DST) : "";
			dir.setDst(att);

			att = (atts.getValue(Xml.ATTR_WITHSUBFOLDERS) != null) ? atts
					.getValue(Xml.ATTR_WITHSUBFOLDERS) : "false";
			dir.setWithSubfolders(Boolean.valueOf(att).booleanValue());

			att = (atts.getValue(Xml.ATTR_VERIFY) != null) ? atts
					.getValue(Xml.ATTR_VERIFY) : "false";
			dir.setVerify(Boolean.valueOf(att).booleanValue());

			att = (atts.getValue(Xml.ATTR_FILE_INCLUDE) != null) ? atts
					.getValue(Xml.ATTR_FILE_INCLUDE) : "";
			dir.setFileInclude(att);

			att = (atts.getValue(Xml.ATTR_FILE_EXCLUDE) != null) ? atts
					.getValue(Xml.ATTR_FILE_EXCLUDE) : "";
			dir.setFileExclude(att);

			att = (atts.getValue(Xml.ATTR_DIR_INCLUDE) != null) ? atts
					.getValue(Xml.ATTR_DIR_INCLUDE) : "";
			dir.setDirInclude(att);

			att = (atts.getValue(Xml.ATTR_DIR_EXCLUDE) != null) ? atts
					.getValue(Xml.ATTR_DIR_EXCLUDE) : "";
			dir.setDirExclude(att);

			att = (atts.getValue(Xml.ATTR_LOGFILE) != null) ? atts
					.getValue(Xml.ATTR_LOGFILE) : "";
			dir.setLogfile(att);

			att = (atts.getValue(Xml.ATTR_SYNC_NEW) != null) ? atts
					.getValue(Xml.ATTR_SYNC_NEW) : "false";
			dir.setCopyNew(Boolean.valueOf(att).booleanValue());

			att = (atts.getValue(Xml.ATTR_SYNC_MODIFIED) != null) ? atts
					.getValue(Xml.ATTR_SYNC_MODIFIED) : "false";
			dir.setCopyModified(Boolean.valueOf(att).booleanValue());

			att = (atts.getValue(Xml.ATTR_SYNC_LARGER) != null) ? atts
					.getValue(Xml.ATTR_SYNC_LARGER) : "false";
			dir.setCopyLarger(Boolean.valueOf(att).booleanValue());

			att = (atts.getValue(Xml.ATTR_SYNC_LARGERMODIFIED) != null) ? atts
					.getValue(Xml.ATTR_SYNC_LARGERMODIFIED) : "false";
			dir.setCopyLargerModified(Boolean.valueOf(att).booleanValue());

			att = (atts.getValue(Xml.ATTR_SYNC_ALL) != null) ? atts
					.getValue(Xml.ATTR_SYNC_ALL) : "false";
			dir.setCopyAll(Boolean.valueOf(att).booleanValue());

			att = (atts.getValue(Xml.ATTR_DEL_FILES) != null) ? atts
					.getValue(Xml.ATTR_DEL_FILES) : "false";
			dir.setDelFiles(Boolean.valueOf(att).booleanValue());

			att = (atts.getValue(Xml.ATTR_DEL_DIRS) != null) ? atts
					.getValue(Xml.ATTR_DEL_DIRS) : "false";
			dir.setDelDirs(Boolean.valueOf(att).booleanValue());

			dirs.add(dir);
		}
	}

	/**
	 * Returns a <code>List</code> of <code>Directory</code>s to dirsync.
	 * 
	 * @return List The <code>Directory</code>s to dirsync.
	 */
	public Vector getDirs() {
		return dirs;
	}

	/**
	 * Returns the file where to log what's being done.
	 * 
	 * @return The logfile.
	 */
	public String getLogFileName() {
		return logFileName;
	}

	/**
	 * Gets wheter symbolic links are skipped.
	 * @return Returns the skipLinks.
	 */
	public boolean isSkipLinks() {
		return skipLinks;
	}

	/**
	 * Gets the maximum difference in seconds between file timestamps.
	 * @return Returns the timestampDiff.
	 */
	public int getMaxTimestampDiff() {
		return maxTimestampDiff;
	}

	/**
	 * Gets whether the destination file timestamp should be written back to the source file.
	 * @return Returns the timestampFix.
	 */
	public boolean isWriteTimestampBack() {
		return writeTimestampBack;
	}

}