package fr.ensma.lias.sparqlendpointjavaclient.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import fr.ensma.lias.sparqlendpointjavaclient.completudeprecision.CompletudeAndPrecision;

//用来按周期读一列的整数

public class ReaderRank1m {
	public int readLineFile(String filename, int init, int nend,  int nline, int nrank){ 
		int r[] = new int[200] ;
        try {  
            FileInputStream in = new FileInputStream(filename);  
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");  
            BufferedReader bufReader = new BufferedReader(inReader);  
            String line = null;  
            int i = 0;  
       

            while((line = bufReader.readLine()) != null && i < nend){  
              
            	
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
		ReaderRank1m r = new ReaderRank1m();
		
		System.out.println(r.readLineFile("resultFilePredCompletude.txt", 1, 22, 0, 0));
		
	}
}
