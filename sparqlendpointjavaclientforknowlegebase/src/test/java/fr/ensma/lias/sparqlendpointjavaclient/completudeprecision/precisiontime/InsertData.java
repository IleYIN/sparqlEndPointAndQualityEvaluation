package fr.ensma.lias.sparqlendpointjavaclient.completudeprecision.precisiontime;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertData {

	public static boolean findRegexFormat(String str, String regEx) {
	    // 要验证的字符串
	    // 验证规则 
//	    String regEx = "([a-zA-Z]{2}(-[a-zA-Z]{2}){0,} [0-9]{2}:[0-9]{2}-[0-9]{2}:[0-9]{2})|24/7";
	    // 编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    // 忽略大小写的写法
	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(str);
//	    // 字符串是否与正则表达式相匹配
//	    boolean rs = matcher.matches();
	    boolean rs = matcher.find();
	    return(rs);
	}
	
	
	public static void main(String[] args) throws IOException {
		ReaderRank1time r1 = new ReaderRank1time();
		ReaderRank2time r2 = new ReaderRank2time();
		
		int num_d0 = 0;
		int num_d1 = 0;
		int num_d2 = 0;
		int num_d3 = 0;
		int num_d4 = 0;
		
		PrintWriter pwTime = new PrintWriter(new FileWriter("resultFileTimeModiCF.txt"));
		pwTime.write("Number	Opening_hours	Distance" + "\r\n" );
		
		for (int i = 0; i<30000; i++){
			
			int number = r1.readLineFile("resultFileObjetTimeCF.txt", 1, i, 0);
			String time = r2.readLineFile("resultFileObjetTimeCF.txt", 1, i, 2);
			
			if (number == 0) break;
			int distance = 4;
			
			if (time != null){
				time = time.replaceAll("noon|Noon", "12:00").replaceAll("midnight|Midnight", "24:00").replaceAll("24/7", "Mo-Su 00:00-24:00").replaceAll("am|pm|AM|PM","").replaceAll("Everyday|EVERYDAY|everyday|(Every Day)|(Every day)|(every day)", "Mo-Su").replaceAll("\\+", "-23:59").replaceAll("\\.|\\s\\.\\s",":").replaceAll("\\s:\\s",":").replaceAll("\\s~\\s|~|\\s-\\s", "-").replaceAll("off|closed|Off|OFF|Closed|CLOSED", "00:00-00:00").replaceAll("WEEKDAYS|weekdays|Weekdays|WEEKDAY|weekday|Weekday", "Mo-Fr").replaceAll("weekends|WEEKENDS|Weekends|WEEKEND|weekend|Weekend", "Sa-Su").replaceAll("MONDAY|Monday|monday|MON|Mon|mon|MO|mo", "Mo").replaceAll("TUESDAY|Tuesday|tuesday|TUES|tues|Tues|TUE|tue|Tue|TU|tu","Tu").replaceAll("Wednesday|WEDNESDAY|wednesday|WED|wed|Wed|WE|we","We").replaceAll("THURSDAY|Thursday|thursday|THUR|Thur|thur|THU|thu|Thu|TU|th","Th").replaceAll("FRIDAY|Friday|friday|FRI|fri|Fri|FR|fr","Fr").replaceAll("SATURDAY|Saturday|saturday|SAT|sat|Sat|SA|sa","Sa").replaceAll("SUNDAY|Sunday|sunday|SUN|sun|Sun|SU|su","Su");
				if (findRegexFormat(time, "(Mo|Tu|We|Th|Fr|Sa|Su|PH|SH)((-|,)(Mo|Tu|We|Th|Fr|Sa|Su|PH|SH)){0,} ([0-1][0-9]|20|21|22|23|24):[0-5][0-9]-([0-1][0-9]|20|21|22|23|24):[0-5][0-9]")){
					distance-=4;
//							System.out.println(distance);
				}
//				else if (findRegexFormat(time, "[0-9]{1,2}:[0-9]{2}-[0-9]{2}:[0-9]{2}")&&findRegexFormat(time, "(Mo|Tu|We|Th|Fr|Sa|Su|PH|SH){1}((-|,)(Mo|Tu|We|Th|Fr|Sa|Su|PH|SH)){0,}")) {
//					distance-=3;
////					System.out.println(distance);
//				}
				else if (findRegexFormat(time, "^([0-1][0-9]|20|21|22|23|24|[0-9]):[0-5][0-9]-([0-1][0-9]|20|21|22|23|24|[0-9]):[0-5][0-9]")) {
					distance-=3;
//					System.out.println(distance);
				}
				else if (findRegexFormat(time, "(Mo|Tu|We|Th|Fr|Sa|Su|PH|SH)(-|,)(Mo|Tu|We|Th|Fr|Sa|Su|PH|SH){1,}")) {
					distance-=2;
//					System.out.println(distance);
				}
				else if (findRegexFormat(time, "^([0-1][0-9]|20|21|22|23|24|[0-9])-([0-1][0-9]|20|21|22|23|24|[0-9])")) {
					distance-=1;
//					System.out.println(distance);
				}
				

//				OpeningHours oh = new OpeningHours(number, time, distance);
//				pwTime.write(oh.getNumber()+ "	" + oh.getTime()+"	"+oh.getDistance()+"\r\n");
				pwTime.write(number+ "	" + time+"	"+distance+"\r\n");
		      	pwTime.flush();
			}
			
			switch (distance) {
			case 0: {
				num_d0 += number;
				break;
			}
			case 1: {
				num_d1 += number;
				break;
			}
			case 2: {
				num_d2 += number;
				break;
			}
			case 3: {
				num_d3 += number;
				break;
			}
			case 4: {
				num_d4 += number;
				break;
			}
			}
			
		
		}
			
		System.out.println(num_d0);
		System.out.println(num_d1);
		System.out.println(num_d2);
		System.out.println(num_d3);
		System.out.println(num_d4);
		
		double ratioPrecisionTime = (double)(num_d0)/(double)((num_d0+num_d1+num_d2+num_d3+num_d4));
		System.out.println(ratioPrecisionTime);
		
		pwTime.write("Distance = 0: "+num_d0 + "\r\n"+"Distance = 1: " +num_d1 + "\r\n" + "Distance = 2: "+ num_d2 + "\r\n"+ "Distance = 3: "+ num_d3 + "\r\n"+ "Precision_Opening_hours: "+ ratioPrecisionTime);
		pwTime.flush();
		pwTime.close();
	}
}

