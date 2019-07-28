package fr.ensma.lias.sparqlendpointjavaclient.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
//取某一列的字符串

public class ReaderRank2 {

	public String readLineFile(String filename, int nrank, int nline){ 
		String[] r = new String[200] ;
        try {  
            FileInputStream in = new FileInputStream(filename);  
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
            BufferedReader bufReader = new BufferedReader(inReader);  
            String line = null;  
            int i = 0;  
       

            while((line = bufReader.readLine()) != null){  
              
            	
            	String[] values=line.split("	|\"");
            	//System.out.println(values);
            	if (nrank < values.length){
            		while (i == r.length){
            			int newCapacity = r.length<<1;
            			String[] newlist = new String[newCapacity];
            			System.arraycopy(r, 0, newlist, 0, r.length);
            			r=newlist;
            		}
            		r[i] = values[nrank];
//            	    System.out.println(r[i]);
            	}
            		
                i++;

                
            }  
            bufReader.close();  
            inReader.close();  
            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.out.println("读取" + filename + "出错！");  
        }
	
		return r[nline];
    }  
	
	//main函数是用来测试Reader能不能正常运行的
	public static void main(String[] args){
		ReaderRank2 r = new ReaderRank2();
		
		System.out.println(r.readLineFile("resultFileDriftExtBuffer1.txt", 1,3000));
		
	}
}
