package it.googlielmo93.apsw.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtility {
	
	private DateUtility() {
	}

	public static Timestamp getTimestampByString(String date) throws ExceptionManager {
		try {
			String dateTime = date.concat(" 00:00:00");
			return Timestamp.valueOf(dateTime);
		}catch(java.lang.IllegalArgumentException exc) {
			throw new ExceptionManager("DateUtility", "Errore IllegalArgumentException Servlet DateUtility -> getTimestampByString(String date). Eccezione lanciata: {0}", exc);
		}
	}
	
	public static Timestamp getTimestampDefault() throws ExceptionManager {
		try {
			return Timestamp.valueOf("2000-01-01 00:00:00");
		}catch(java.lang.IllegalArgumentException exc) {
			throw new ExceptionManager("DateUtility", "Errore IllegalArgumentException Servlet DateUtility -> getTimestampDefault(). Eccezione lanciata: {0}", exc);
		}
	}
	 
	public static String getFormattedStringByTimestamp(Timestamp tsDate, boolean shortDate) throws ExceptionManager {
		try {
			String formatString = shortDate == false ? "dd/MM/yyyy HH:mm:ss" : "dd/MM/yyyy";
			return new SimpleDateFormat(formatString).format(new Date(tsDate.getTime()));
		}catch(java.lang.IllegalArgumentException exc) {
			throw new ExceptionManager("DateUtility", "Errore IllegalArgumentException Servlet DateUtility -> getFormattedStringByTimestamp(Timestamp tsDate, boolean shortDate). Eccezione lanciata: {0}", exc);
		}
	}
	
	public static String getFormattedDataENByTimestamp(Timestamp tsDate) throws ExceptionManager {
		try {
			String formatString = "yyyy-MM-dd";
			return new SimpleDateFormat(formatString).format(new Date(tsDate.getTime()));
		}catch(java.lang.IllegalArgumentException exc) {
			throw new ExceptionManager("DateUtility", "Errore IllegalArgumentException Servlet DateUtility -> getFormattedStringByTimestamp(Timestamp tsDate, boolean shortDate). Eccezione lanciata: {0}", exc);
		}
	}
	
	public static String getFormattedStringCurrentTimestamp() throws ExceptionManager {
		try {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date( new Timestamp( System.currentTimeMillis() ).getTime()));
		}catch(java.lang.IllegalArgumentException exc) {
			throw new ExceptionManager("DateUtility", "Errore IllegalArgumentException Servlet DateUtility -> getFormattedStringCurrentTimestamp(). Eccezione lanciata: {0}", exc);
		}
	}
	
	
	public static String getFormattedStringCurrentDay() throws ExceptionManager {
		try {
			return DateUtility.getFormattedStringByTimestamp(DateUtility.getCurrentTimestamp(), true);
		}catch(java.lang.IllegalArgumentException exc) {
			throw new ExceptionManager("DateUtility", "Errore IllegalArgumentException Servlet DateUtility -> getFormattedStringCurrentTimestamp(). Eccezione lanciata: {0}", exc);
		}
	}
	
	
	public static Timestamp getCurrentTimestamp() throws ExceptionManager {
		try {
			return new Timestamp(System.currentTimeMillis());
		}catch(java.lang.IllegalArgumentException exc) {
			throw new ExceptionManager("DateUtility", "Errore IllegalArgumentException Servlet DateUtility -> getCurrentTimestamp(). Eccezione lanciata: {0}", exc);
		}
	}
	
	
	public static Timestamp getCurrentTimestampTimeZeros() throws ExceptionManager {
		try {
			LocalDate currentdate = LocalDate.now();
			
		    int currentDay = currentdate.getDayOfMonth();
		    
		    int currentMonth = currentdate.getMonthValue();
		    
		    int currentYear = currentdate.getYear();

		    
		    LocalDate localDateMod = LocalDate.of(currentYear, currentMonth, currentDay);
		    LocalDateTime dateTimeMod = localDateMod.atTime(0, 0, 0);
		    
		    return Timestamp.valueOf(dateTimeMod);
			
		}catch(java.lang.IllegalArgumentException exc) {
			throw new ExceptionManager("DateUtility", "Errore IllegalArgumentException Servlet DateUtility -> getCurrentTimestampTimeZeros(). Eccezione lanciata: {0}", exc);
		}
	}
	
	
	public static boolean isAnnoBisestile(int year) {
		if (year % 4 != 0) {
		    return false;
		  } else if (year % 400 == 0) {
		    return true;
		  } else if (year % 100 == 0) {
		    return false;
		  } else {
		    return true;
		  }
	}


	public static Timestamp getNextDayTS(int nextDay) throws ExceptionManager {
		try {
			LocalDate currentdate = LocalDate.now();
			
		    int currentDay = currentdate.getDayOfMonth();
		    
		    int currentMonth = currentdate.getMonthValue();
		    
		    int currentYear = currentdate.getYear();
		    
		    
		    switch(currentMonth) {
		    	case 1 : case 3 : case 5 : case 7 : case 8 : case 10 : case 12 :
		    		if(currentDay == 31) {
		    			if(currentMonth == 12) {
		    				currentYear++;
		    				currentMonth = 1;
		    			}else
		    				currentMonth++;
		    			
				    	currentDay = 1 + nextDay;
				    	
		    		}else if ((currentDay + nextDay) == 31){
		    			if(currentMonth == 12) {
		    				currentYear++;
		    				currentMonth = 1;
		    			}else
		    				currentMonth++;
		    			
				    	currentDay = 1;
		    		}
		    		else
		    			currentDay += nextDay;
		    	break;
		    	
		    	case 4 : case 6 : case 9 : case 11 :
		    		if(currentDay == 30) {
		    			currentMonth++;
				    	currentDay = 1 + nextDay;
				    	
		    		}else if ((currentDay + nextDay) == 30){
		    			currentMonth++;
				    	currentDay = 1;
		    		}
		    		else
		    			currentDay += nextDay;
		    	break;
		    	
		    	case 2 :
		    		int limitMonth = 28;
		    		if(isAnnoBisestile(currentYear)) limitMonth = 29; else limitMonth = 28;
		    		
		    		if(currentDay == limitMonth) {
		    			currentMonth++;
		    			currentDay = 1 + nextDay;
		    		}
		    		else if(currentDay + nextDay == limitMonth) {
		    			currentMonth++;
		    			currentDay = 1;
		    		}
		    		else
		    			currentDay++;
		    	break;
		    
		    }
		    
		    LocalDate localDateMod = LocalDate.of(currentYear, currentMonth, currentDay);
		    LocalDateTime dateTimeMod = localDateMod.atTime(0, 0, 0);
		    
		    return Timestamp.valueOf(dateTimeMod);
			
		}catch(java.lang.IllegalArgumentException exc) {
			throw new ExceptionManager("DateUtility", "Errore IllegalArgumentException Servlet DateUtility -> getCurrentTimestamp(). Eccezione lanciata: {0}", exc);
		}
	}
	
	
	public static int compareWithCurrentTimeTS(Timestamp ts, Timestamp ts2) throws ExceptionManager {
		return ts.compareTo(ts2); // 0 uguali, >0 data corrente maggiore di quella passata, <0 data corrente minore di quella passata
	}
	

}
