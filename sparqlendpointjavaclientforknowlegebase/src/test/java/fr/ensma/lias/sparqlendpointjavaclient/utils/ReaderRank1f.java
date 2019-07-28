package fr.ensma.lias.sparqlendpointjavaclient.utils;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.InputStreamReader;
//按周期取一列的小数
public class ReaderRank1f {
	public double readLineFile(String filename, int init, int nend,  int nline, int nrank){ 
		double r[] = new double[200] ;
        try {  
            FileInputStream in = new FileInputStream(filename);  
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
            BufferedReader bufReader = new BufferedReader(inReader);  
            String line = null;  
            int i = 0;  
       

            while((line = bufReader.readLine()) != null && i < nend){  
              
            	
            	String[] values=line.split("	");
            	//System.out.println(values);
         
            	if(i > init) {
            		while ((i-1-init) == r.length){
            			int newCapacity = r.length<<1;
            			double[] newlist = new double[newCapacity];
            			System.arraycopy(r, 0, newlist, 0, r.length);
            			r=newlist;
            		}
            	   r[i-1-init] = (Double.parseDouble(values[nrank]));
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
		ReaderRank1f r = new ReaderRank1f();
		
		System.out.println(r.readLineFile("resultFilePrecisiontest01.txt",0, 21, 0, 2));
		
	}
}
