package fr.ensma.lias.sparqlendpointjavaclient.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import fr.ensma.lias.sparqlendpointjavaclient.completudeprecision.CompletudeAndPrecision;

//取一列的整数
public class ReaderRank1 {
//	public void readLineFile(String filename){  
	public int readLineFile(String filename, int init, int nline, int nrank){ 
		int r[] = new int[200] ;
        try {  
            FileInputStream in = new FileInputStream(filename);  
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
            BufferedReader bufReader = new BufferedReader(inReader);  
            String line = null;  
            int i = 0;  
       

            while((line = bufReader.readLine()) != null){  
              
            	
            	String[] values=line.split("	");
            	//System.out.println(values);
         
            	if(i > init && CompletudeAndPrecision.findRegex(values[nrank])) {
               
            		while ((i-1-init) == r.length){
            			int newCapacity = r.length<<1;
            			int[] newlist = new int[newCapacity];
            			System.arraycopy(r, 0, newlist, 0, r.length);
            			r=newlist;
            		}
            		r[i-1-init] = (Integer.parseInt(values[nrank]));
//            	      System.out.println(r[i-1-init]);
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
		ReaderRank1 r = new ReaderRank1();
		
		System.out.println(r.readLineFile("resultFileDriftExtBuffer1.txt", 0, 1,0));
		
	}

}
