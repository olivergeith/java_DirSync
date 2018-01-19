/*
 * DateTools.java
 *
 * Copyright (C) 2005 F. Gerbig (fgerbig@users.sourceforge.net)
 * Copyright (C) 2005 T. Groetzner 
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tools for handling the time and date wildcards. 
 * @author T. Groetzner, F. Gerbig (fgerbig@users.sourceforge.net) 
 */
public abstract class DateTools {
	// the wildcards to be replaced by the current date and time
	private static final String WILDCARD_DATE = "<date>";
	private static final String WILDCARD_TIME = "<time>";
	private static final String WILDCARD_DATE_DAY = "<DD>";
	private static final String WILDCARD_DATE_MONTH = "<MM>";
	private static final String WILDCARD_DATE_YEAR = "<YYYY>";
	private static final String WILDCARD_TIME_HOUR = "<hh>";
	private static final String WILDCARD_TIME_MINUTE = "<mm>";
	private static final String WILDCARD_TIME_SECOND = "<ss>";
	
	// Don't let anyone instantiate this class.
	private DateTools() {
	}

	/**
	 * Replaces the date and time wildcards in the given String with the given Date.
	 * @param s The String containing wildcards.
	 * @param d The date to replace the wildcards with.
	 * @return The string with the wildcards replaced.
	 */
	public static String replaceWildcards(String s, Date d) {
		String date;
		String time;
		String year;
		String month;
		String day;
		String hour;
		String minute;
		String second;

		// format date
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
		df.applyPattern("yyyy-MM-dd");
		date = df.format(d);

		// format time
		df.applyPattern("HH-mm-ss");
		time = df.format(d);
		
		// format single date parts
		df.applyPattern("yyyy"); // year
		year = df.format(d);
		df.applyPattern("MM"); // month
		month = df.format(d);
		df.applyPattern("dd"); // day
		day = df.format(d);

		// format single time parts
		df.applyPattern("HH"); // hour
		hour = df.format(d);
		df.applyPattern("mm"); // minute
		minute = df.format(d);
		df.applyPattern("ss"); // second
		second = df.format(d);

		s = s.replaceAll(WILDCARD_DATE, date);
		s = s.replaceAll(WILDCARD_TIME, time);

		s = s.replaceAll(WILDCARD_DATE_YEAR, year);
		s = s.replaceAll(WILDCARD_DATE_MONTH, month);
		s = s.replaceAll(WILDCARD_DATE_DAY, day);

		s = s.replaceAll(WILDCARD_TIME_HOUR, hour);
		s = s.replaceAll(WILDCARD_TIME_MINUTE, minute);
		s = s.replaceAll(WILDCARD_TIME_SECOND, second);

		return s;
	}
}
