package com.test.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Utility class for formatting contents before writing to output
 * @author Arunkumar.s
 *
 */
public class AppUtils {
	/**variable to hold Month Map **/
	private final static Logger itsLogger = LogManager.getLogger(AppUtils.class);
	/**variable to hold Date Formatter**/
	private final static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			CommonConstants.YYYY_DD_MM);

	/**
	 * Time taken to run the job.
	 * @param inMilliseconds
	 * @return
	 */
	public static String getTimeElapsed( long inMilliseconds )
	{
		int theSeconds = ( int ) ( inMilliseconds / 1000 ) % 60;
		int theMinutes = ( int ) ( ( inMilliseconds / ( 1000 * 60 ) ) % 60 );
		int theHours = ( int ) ( ( inMilliseconds / ( 1000 * 60 * 60 ) ) % 24 );
		return theHours + CommonConstants.HOURS + theMinutes + CommonConstants.MINUTES + theSeconds
		        + CommonConstants.SECONDS;
	}

	/**
	 * To get formatted date.
	 * @param addition
	 * @return
	 */
	public static String getFormattedDate(int addition) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, addition);
		String fmtDate = dateFormatter.format(calendar.getTime());

		return fmtDate;
	}

	/**
	 * To get the DB Date Format.
	 * @param date
	 * @return
	 */
	public static String getDBDateFormat(Date date){
		return dateFormatter.format(date);
	}



	/**
	 * To trim the space.
	 * @param content
	 * @return
	 */
	public static String trimSpace(String content){
		if(content==null){
			return "";
		}
		content = StringUtils.trimToEmpty(content);

		return content;
	}

	/**
	 * check if the date is valid date
	 * @param inDate
	 * @param inFormat
	 * @return
	 */
	public static boolean isValidDate(String inDate, String inFormat) {
		SimpleDateFormat theFormat = new SimpleDateFormat(inFormat);
		try {
			theFormat.parse(inDate);
			return true;
		} catch (ParseException ex) {
			LogManager.logStackTrace(itsLogger, ex.getStackTrace());
			itsLogger.error("AppUtils:isValidDate:" + ex);
		}

		return false;
	}
}
