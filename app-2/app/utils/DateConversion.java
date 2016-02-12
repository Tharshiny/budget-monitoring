package utils;

import org.joda.time.LocalDateTime;

public class DateConversion {

	public static LocalDateTime parse(String stringDate){
		return LocalDateTime.parse(stringDate);
	}
	
	public static String format(LocalDateTime date){
		return date.toString();
	}
}
