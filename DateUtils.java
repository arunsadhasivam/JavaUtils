import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadableInstant;

public class DateUtils {

	DateUtils(){
		
	}
	
	public int compareDate(String strDate1,String strDate2){
		
		SimpleDateFormat df = new SimpleDateFormat("mmddYYYY");
		int maxyears = 0;
		try {
			Date date1 =   df.parse(strDate1);
			Date date2 =   df.parse(strDate2);
			
			
			GregorianCalendar cal1 = new GregorianCalendar();
			cal1.setTime(date1);
			
			GregorianCalendar cal2 = new GregorianCalendar();
			cal2.setTime(date2);
			ReadableInstant inst1 = new DateTime(cal1.getTimeInMillis());
			ReadableInstant inst2= new DateTime(cal2.getTimeInMillis());
			Days days = Days.daysBetween(inst1, inst2);
 			//leapyear - 366
			
			
			
 			if(cal1.isLeapYear(date1.getYear())){
 				maxyears  = days.getDays()/366; 
 			}else{
 				maxyears  = days.getDays()/365; 
 			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return maxyears;
	}
	
	
public int compareDate1(String strDate1,String strDate2){
		
		SimpleDateFormat df = new SimpleDateFormat("MMddyyyy");
		int maxyears = 0;
		try {
			Date date1 =   df.parse(strDate1);
			Date date2 =   df.parse(strDate2);
			
			GregorianCalendar calFromDate = new GregorianCalendar();
			calFromDate.setTime(date1);
			
			GregorianCalendar calToDate = new GregorianCalendar();
			calToDate.setTime(date2);
			
			
			
			GregorianCalendar calImputedDate = new GregorianCalendar();
			calImputedDate.setTime(date1);
			calImputedDate.add(Calendar.YEAR, 1);
			
			System.out.println("DateUtils.compareDate1() : BEFORE ::::"+print(calFromDate) +" -" +print(calToDate) + " -" + print(calImputedDate));
			int totalDaysbtwYear=0;
			int leapcount =0;
			int totalDays = 0;
			int yearCount =0;
			while(calFromDate.compareTo(calImputedDate)<=0  && 
					calImputedDate.compareTo(calToDate)<=0  ){
				yearCount++;
				
				long diff = daysBetween(calFromDate, calImputedDate);
				int year = calFromDate.get(Calendar.YEAR);
				//leap -366
				if(calFromDate.isLeapYear(year)){
					leapcount ++;
					totalDaysbtwYear+= diff;
					totalDays += 366;
					//System.out.println("DateUtils.compareDate1():year"+year +" is leap year " + print(calFromDate) +" -" +print(calImputedDate) + " diff:"+ diff);
				}else{
					totalDaysbtwYear+= diff;
					totalDays += 365;
					//System.out.println("DateUtils.compareDate1():year"+year +" is not leap year " + print(calFromDate) +" -" +print(calImputedDate) + " diff:"+ diff);
				}
				
				System.out.println("DateUtils.compareDate1():"+ print(calFromDate) +" -" +print(calImputedDate) + " diff:"+ diff);
			   calFromDate.add(Calendar.YEAR, 1);
			   calImputedDate.add(Calendar.YEAR, 1);
			}
			 calImputedDate.add(Calendar.YEAR, -1);
			 int year = calImputedDate.get(Calendar.YEAR);
			 if(calImputedDate.isLeapYear(year)){
				 leapcount++;
			 }
			 
			 
			 System.out.println("DateUtils.compareDate1():remaining:"+print(calImputedDate) +" -" +print(calToDate) + "diff:"+daysBetween(calImputedDate,calToDate));
			 long  remDays = daysBetween(calImputedDate,calToDate);
			 totalDaysbtwYear+=remDays;
			 totalDays += remDays;
			 
			 
			 System.out.println("DateUtils.compareDate1():totalDaysbtwYear:::"+totalDaysbtwYear + " totalDays" +totalDays);
			int avg = (totalDays/yearCount);
			int years = totalDaysbtwYear/avg;
			
			System.out.println("DateUtils.compareDate1()"+years);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return maxyears;
	}

 

	private long daysBetween(GregorianCalendar  cal1, GregorianCalendar cal2){
		
		ReadableInstant inst1 = new DateTime(cal1.getTimeInMillis());
		ReadableInstant inst2= new DateTime(cal2.getTimeInMillis());
		long diff =  Days.daysBetween(inst1, inst2).getDays();
		
		return diff;
		
	}
	
    private int daysBetween1(GregorianCalendar  cal1, GregorianCalendar cal2){
    	int year1 = cal1.get(Calendar.DAY_OF_MONTH);
    	int year2= cal2.get(Calendar.YEAR);
    	
    	
    	return year2- year1;
    }

    public String print(Calendar calFromDate){
    	
    	int day = calFromDate.get(Calendar.DAY_OF_MONTH);
		int month = calFromDate.get(Calendar.MONTH)+1;
		int year = calFromDate.get(Calendar.YEAR);
    	
		String result= day + "/" +month +"/" + year;
		
		
		return result;
    }
	
	public static String getTimeElapsed( long inMilliseconds )
	{
		long theSeconds = ( int ) ( inMilliseconds / 1000 ) % 60;
		long theMinutes = ( int ) ( ( inMilliseconds / ( 1000 * 60 ) ) % 60 );
		long theHours = ( int ) ( ( inMilliseconds / ( 1000 * 60 * 60 ) ) % 24 );
		return theHours + ":" + theMinutes + ":"  + theSeconds
		        + ":" ;
	}
	
	public static void main(String args[]){
		int result = new DateUtils().compareDate1("12312015", "12302018");
		System.out.println("DateUtils.main()"+result);
	}
}
