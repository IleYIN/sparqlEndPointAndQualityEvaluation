package fr.ensma.lias.sparqlendpointjavaclient.completudeprecision.precisiontime;

import java.util.regex.*;


public class test {
	public boolean findRegexFormat(String str, String regEx) {
	    // 要验证的字符串
	    // 验证规则 
//	    String regEx = "([a-zA-Z]{2}(-[a-zA-Z]{2}){0,} [0-9]{2}:[0-9]{2}-[0-9]{2}:[0-9]{2})|24/7";
//	    String regEx = "([a-zA-Z]{2}(-[a-zA-Z]{2}){0,} [0-9]{2}:[0-9]{2}-[0-9]{2}:[0-9]{2})|24/7|[0-9]{2}:[0-9]{2}";
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
	public static void main(String[] args) {
		
		String time = "10.00 - 20 . 15";
		time = time.replaceAll("\\.|\\s\\.\\s",":");
				System.out.println(time);
	}
}


