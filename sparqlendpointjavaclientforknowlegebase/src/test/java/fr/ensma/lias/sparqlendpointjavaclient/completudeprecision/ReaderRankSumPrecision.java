package fr.ensma.lias.sparqlendpointjavaclient.completudeprecision;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReaderRankSumPrecision {
	public double readLineFile(String filename, int init){ 
		int r = 0, r2 = 0 ;
        try {  
            FileInputStream in = new FileInputStream(filename);  
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
            BufferedReader bufReader = new BufferedReader(inReader);  
            String line = null;  
            int i = 0;  
       

            while((line = bufReader.readLine()) != null){  
              
            	
            	String[] values=line.split("	");
            	System.out.println(values[0]);
         
            	if (i > init) {
            	
            		r2 += (Integer.parseInt(values[0]));
            		
            		if(i>init && Integer.parseInt(values[0]) > 1){
            			r += (Integer.parseInt(values[0]));
            			
            		}
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
        double r3 = (double)r/r2;
//		System.out.println(r);
//		System.out.println(r2);
		  
        return r3;
	
    }  
	
	//main函数是用来测试Reader能不能正常运行的
	public static void main(String[] args){
		ReaderRankSumPrecision r = new ReaderRankSumPrecision();
		System.out.println(r.readLineFile("resultFilePredPrecis.txt", 2 ));
		
	}
}
