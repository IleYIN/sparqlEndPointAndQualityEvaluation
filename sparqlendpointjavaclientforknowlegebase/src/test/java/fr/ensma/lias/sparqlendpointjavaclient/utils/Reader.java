package fr.ensma.lias.sparqlendpointjavaclient.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

//取第一列的数
public class Reader {

	public int readLineFile(String filename, int nwant){ 
		int r = 0;
        try {  
            FileInputStream in = new FileInputStream(filename);  
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
            BufferedReader bufReader = new BufferedReader(inReader);  
            String line = null;  
            int i = 0;  
       

            while((line = bufReader.readLine()) != null){  
              
            	
            	String[] values=line.split("	");
            	if(i == nwant)
               
            	r = (Integer.parseInt(values[0]));
            	//System.out.println(r);
                i++;

        		
            
                
            }  
            bufReader.close();  
            inReader.close();  
            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.out.println("读取" + filename + "出错！");  
        }
		return r;  
    }  
	
	//main函数是用来测试Reader能不能正常运行的
	public static void main(String[] args){
		Reader r = new Reader();
		
		System.err.println(r.readLineFile("resultFilePredPrecis.txt", 1));
		
	}

}
