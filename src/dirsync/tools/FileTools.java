/*
 * FileTools.java
 * 
 * Copyright (C) 2002 E. Gerber  
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

package dirsync.tools;

import java.io.*;
import java.nio.channels.FileChannel;
// import java.nio.channels.FileChannel;
import java.util.*;
import java.util.zip.*;

import dirsync.DirSync;
import dirsync.directory.DirectoryLister;
import dirsync.exceptions.ErrorException;
import dirsync.exceptions.WarningException;

/**
 * Contains methods to compare file date and size and to copy a file.
 * 
 * @author E. Gerber , F. Gerbig (fgerbig@users.sourceforge.net)
 */
public abstract class FileTools {

	private static final int BUFFER_SIZE = 1 * 1024 * 1024;

	// a buffer for the copy method
	private static byte[] buffers = new byte[BUFFER_SIZE];

	// Don't let anyone instantiate this class.
	private FileTools() {
	}

	/**
	 * Compares the dates of the given files. File dates are only accurate to
	 * the second; therefore file dates are divided by 1000 and truncated
	 * (converting milliseconds to seconds).
	 * 
	 * @param src
	 *            The source file.
	 * @param dst
	 *            The destination file.
	 * @return boolean <code>true</code> if the first file is newer than the
	 *         second one, <code>false</code> else. If the second file doesn't
	 *         exist <code>false</code> is returned.
	 */
	public static boolean cmpFileDates(File src, File dst) {
		long srcLastModified, dstLastModified;

		if (!dst.exists()) {
			return false;
		}

		srcLastModified = src.lastModified() / 1000;
		// convert to seconds
		dstLastModified = dst.lastModified() / 1000;
		// convert to seconds
		return srcLastModified > dstLastModified + DirSync.getSync().getMaxTimestampDiff();
	}

	/**
	 * Compares the sizes of the given files.
	 * 
	 * @param src
	 *            The source file.
	 * @param dst
	 *            The destination file.
	 * @return boolean <code>true</code> if the first file is larger than the
	 *         second one, <code>false</code> else. If the second file doesn't
	 *         exist <code>false</code> is returned.
	 */
	public static boolean cmpFileSizes(File src, File dst) {
		if (!dst.exists()) {
			return false;
		}
		return src.length() > dst.length();
	}

	/**
	 * Copies the source file to the given destination with the same filename.
	 * 
	 * @param srcFile
	 *            The file to copy.
	 * @param dstFile
	 *            The destination (where to copy the source file).
	 * @throws IOException
	 * @throws WarningException
	 * @throws ErrorException
	 */
	public static void copy(File srcFile, File dstFile) throws IOException, WarningException, ErrorException {

		long lastModified;

		// first test for common errors:

		// does source file exist ?
		if (!srcFile.exists()) {
			throw new ErrorException("Source file not found.");
		}

		// is source file really a file ?
		if (!srcFile.isFile()) {
			throw new ErrorException("Source isn't a file.");
		}

		// can the source file be read ?
		if (!srcFile.canRead()) {
			throw new ErrorException("Source file can't be read.");
		}

		// create the destination directory if necessary
		if ((dstFile.getParentFile() != null) && (!dstFile.getParentFile().isDirectory())) {
			dstFile.getParentFile().mkdirs();
		}

		if (dstFile.exists()) { // does the destination file already exist?

			// can the existing destination file be overwritten ?
			if (!dstFile.canWrite()) {
				throw new ErrorException("Destination file can't be overwritten.");
			}
		} else {

			// can the not existing destination file be created?
			try {
				dstFile.createNewFile();
			} catch (Exception e) {
				throw new ErrorException("Destination file can't be created.");
			}
		}

		// everything ok: copy

		// The input stream
		FileInputStream in = null;
		// The output stream
		FileOutputStream out = null;

		// The input channel
		FileChannel inChannel = null;
		// The output channel
		FileChannel outChannel = null;

		try {
			try {
				in = new FileInputStream(srcFile);
				if (in == null) {
					throw new WarningException("Could not open input stream for file '" + srcFile + "'");
				}
			} catch (FileNotFoundException fnfe) {
				throw new WarningException("Could not open input stream for file '" + srcFile + "' (probably because it is being used by another process)");
			}

			try {
				out = new FileOutputStream(dstFile);
				if (out == null) {
					throw new WarningException("Could not open output stream for file '" + dstFile + "'");
				}
			} catch (FileNotFoundException fnfe) {
				throw new WarningException("Could not open output stream for file '" + dstFile + "' (probably because access to the source file '" + srcFile
						+ "' is denied)");
			}

			inChannel = in.getChannel(); // get in channel
			outChannel = out.getChannel(); // get out channel

			// init progress bar
			if (DirSync.isGuiMode()) {
				DirSync.getGui().setCurrentProgressText(srcFile.getName());
				DirSync.getGui().setCurrentProgressMaximum((int) inChannel.size());
			}

			if (DirSync.getProperties().getProperty("dirsync.NIO", "false").equals("true")) {
				// NIO: copy file in portions of x MB otherwise
				// system resources (buffers, RAM) can run out
				long copied = -1;
				long position = 0;
				while (copied != 0) {
					copied = inChannel.transferTo(position, Math.min(inChannel.size() - position, BUFFER_SIZE), outChannel);
					position += copied;
					if (DirSync.isGuiMode()) {
						DirSync.getGui().setCurrentProgress((int) position);
					}
				}
			} else {
				// einlesen der daten in den buffer und dann schreiben, so lange
				// bis wir am ende des files sind (wenn read() '-1'
				// zurueckgibt):
				int position = 0;
				int copied = 0;
				while ((copied = in.read(buffers)) != -1) {
					out.write(buffers, 0, copied); // Die daten schreiben
					position += copied;
					if (DirSync.isGuiMode()) {
						DirSync.getGui().setCurrentProgress(position);
					}
				}
			}

			if (DirSync.isGuiMode()) {
				DirSync.getGui().setCurrentProgress((int) inChannel.size());
			}

			inChannel.close(); // close in channel
			outChannel.close(); // close out channel

			// copy the file modification date
			lastModified = srcFile.lastModified();
			// solves the problem of files without modification time on NTFS
			if (lastModified < 0) {
				lastModified = 0;
			}
			dstFile.setLastModified(lastModified);

		} finally {
			try {
				// always close the channels
				if (inChannel != null) {
					inChannel.close();
				}
				if (outChannel != null) {
					outChannel.close();
				}

				// always close the streams
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Changes a give file pattern to a regular expression. The regular
	 * expression meta characters are escaped by backslash, '?' becomes '.?' and
	 * '*' becomes '.*'. E.g. the file pattern '*.doc' is changed to the regular
	 * expression '.*\.doc'.
	 * 
	 * @param filepattern
	 *            The file pattern to change.
	 * @return The regular expression derived from the file pattern.
	 */
	private static String getRegEx(String filepattern) {
		String metachar;

		// all regular expression meta chars
		StringTokenizer st = new StringTokenizer("( [ { \\ ^ $ | ) + .");

		try {
			// escape meta chars
			while (st.hasMoreTokens()) {
				metachar = st.nextToken();
				filepattern = filepattern.replaceAll("\\" + metachar, "\\\\" + metachar);
			}

			// change '?' to '.?'
			// file pattern '?' means none, or one character
			// none, or one char as regular expression is '.?'
			filepattern = filepattern.replaceAll("\\?", ".?");

			// change '*' to '.*'
			// file pattern '*' means none, one, or more chars
			// none, one, or more chars as regular expression is '.*'
			filepattern = filepattern.replaceAll("\\*", ".*");

		} catch (StringIndexOutOfBoundsException e) {
		}

		return filepattern;
	}

	/**
	 * Tokenizes a given pattern string in an <code>String</code> array and
	 * converts the patterns from DOS-Style in regular expressions.
	 * 
	 * @param pattern
	 *            The pattern <code>String</code>.
	 * @return The converted patterns.
	 */
	public static String[] tokenizePattern(String pattern) {
		StringTokenizer st = new StringTokenizer(pattern, ",;");
		String[] patterns = new String[st.countTokens()];
		for (int i = 0; st.hasMoreTokens(); i++) {
			patterns[i] = st.nextToken();
			// change patterns to regular expressions:
			patterns[i] = getRegEx(patterns[i]);
		}
		return patterns;
	}

	/**
	 * Checks if the given <code>File</code> matches the given patterns
	 * (regular expressions).
	 * 
	 * @param file
	 *            The file to check against the patterns.
	 * @param patterns
	 *            The regular expressions to match the file aigainst.
	 * @return <code>true</code> if the file matches the regular expressions
	 *         and <code>false</code> otherwise.
	 */
	public static boolean checkFileMatchesPatterns(File file, String[] patterns) {
		boolean match = false;

		for (int i = 0; i < patterns.length; i++) {
			if (DirectoryLister.matchDependingOnOS(file.getName(), patterns[i])) {
				match = true; // file matches file include pattern
			}
		}

		return match;
	}

	/**
	 * Compares two files.
	 * 
	 * @param srcFile
	 *            The source file to compare.
	 * @param dstFile
	 *            The destination file to compare.
	 * @return <code>true</code> if the checksums match.
	 */
	public static boolean checksumIdentical(File srcFile, File dstFile) {
		return checksum(srcFile) == checksum(dstFile);
	}

	/**
	 * Calculates the checksum of a file.
	 * 
	 * @param file
	 *            The file which checksum shall be calculated.
	 * @return The checksum.
	 */
	public static long checksum(File file) {
		// The input stream
		FileInputStream in = null;
		// The checksum
		Checksum checksum = null;

		try {

			in = new FileInputStream(file);
			checksum = new CRC32();

			int bytes_read; // wie viele bytes wurden schon eingelesen?

			// einlesen der daten in den buffer und dann schreiben, so lange
			// bis wir am ende des files sind
			// (wenn read() '-1' zurueckgibt):
			while ((bytes_read = in.read(buffers)) != -1) {
				checksum.update(buffers, 0, bytes_read);
			}

		} catch (IOException e) {
			throw new Error(e.getMessage());

		} finally {
			if (in != null) {
				try {
					in.close(); // always close streams.
				} catch (IOException e) {
				}
			}
		}

		return checksum.getValue();
	}

	/**
	 * Removes all symbolic links from the given array.
	 * 
	 * @param filesAndLinks
	 *            A list of files and links.
	 * @return File[] A list of files only (links have been removed).
	 */
	public static File[] removeLinks(File[] filesAndLinks) {
		List files = new ArrayList(Arrays.asList(filesAndLinks));

		for (Iterator iter = files.iterator(); iter.hasNext();) {
			File file = (File) iter.next();
			if (isLink(file)) {
				iter.remove();
			}
		}
		return (File[]) files.toArray(new File[files.size()]);
	}

	/**
	 * Returns whether the file is a symbolic link.
	 * 
	 * @param file
	 *            The file to check.
	 * @return boolean <code>true</code> if the file is a symbolic link,
	 *         <code>false</code> otherwise.
	 */
	public static boolean isLink(File file) {
		try {
			return !(file.getAbsolutePath()).equals(file.getCanonicalPath());
		} catch (IOException ioe) {
			throw new Error(ioe);
		}
	}

	/**
	 * Deletes a directory with contained files and subdirectories.
	 * 
	 * @param dir
	 *            The directory to delete.
	 * @return <code>true</code> if the deletion could be completed,
	 *         <code>false</code> if an error accoured.
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] filesAndDirs = dir.list();
			for (int i = 0; i < filesAndDirs.length; i++) {
				if (!deleteDir(new File(dir, filesAndDirs[i]))) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}
