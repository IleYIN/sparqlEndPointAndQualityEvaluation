package fr.ensma.lias.sparqlendpointjavaclient.completudeprecision.precisiontime;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
//å?–æŸ?ä¸€åˆ—çš„å­—ç¬¦ä¸²
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReaderRank2time {

	public static boolean findRegex(String str) {
	    // 要验证的字符串
	    // 验证规则 
	    String regEx = "[0-9]{1,}";
//	    String regEx = "([a-zA-Z]{2}(-[a-zA-Z]{2}){0,} [0-9]{2}:[0-9]{2}-[0-9]{2}:[0-9]{2})|24/7|[0-9]{2}:[0-9]{2}";
	    // 编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    // 忽略大小写的写法
	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(str);
//	    // 字符串是否与正则表达式相匹配
	    boolean rs = matcher.matches();
//	    boolean rs = matcher.find();
	    return(rs);
	}
	
	
	public String readLineFile(String filename, int init, int nline, int nrank){ 
		String[] r = new String[200000] ;
        try {  
            FileInputStream in = new FileInputStream(filename);  
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
            BufferedReader bufReader = new BufferedReader(inReader);  
            String line = null;  
            int i = 0;  
       

            while((line = bufReader.readLine()) != null){  
              
            	String[] values=line.split("	|\"");
            	

             	if(i > init && findRegex(values[0]) && nrank < values.length) {
                    
             	   r[i-1-init] = values[nrank];
//             	   System.out.println(r[i-1-init]);
             	}
                 i++;
            	                
            }  
            bufReader.close();  
            inReader.close();  
            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.out.println("è¯»å?–" + filename + "å‡ºé”™ï¼?");  
        }
	
		return r[nline];
    }  
	
	//mainå‡½æ•°æ˜¯ç”¨æ?¥æµ‹è¯•Readerèƒ½ä¸?èƒ½æ­£å¸¸è¿?è¡Œçš„
	public static void main(String[] args){
		ReaderRank2time r = new ReaderRank2time();
		
		System.out.println(r.readLineFile("resultFileObjetTime0.txt", 1, 29862, 2));
		
	}
}
