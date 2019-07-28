package fr.ensma.lias.sparqlendpointjavaclient.completudeprecision;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import fr.ensma.lias.sparqlendpointjavaclient.OutputFormat;
import fr.ensma.lias.sparqlendpointjavaclient.SPARQLEndpointClient;
import fr.ensma.lias.sparqlendpointjavaclient.utils.ReaderRank2;

public class TraitPredTemp {
	public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException {
		
		SPARQLEndpointClient stagetest = new SPARQLEndpointClient.Builder().url("http://linkedgeodata.org/sparql/")
				.outputFormat(OutputFormat.TAB_SEPARATED).build();

		/*以上不可改*/
	
		String prefix = "Prefix lgdr:<http://linkedgeodata.org/triplify/> Prefix lgdo:<http://linkedgeodata.org/ontology/> ";	
	
		ReaderRank2 r = new ReaderRank2();
		PrintWriter pw1 = new PrintWriter(new FileWriter("resultFileObjetTimeCF.txt"));
		for (int i = 1; r.readLineFile("resultFilePredTimeCF.txt", 2, i)!= null; i++) {
//			System.out.println(r.readLineFile("resultFilePredTime.txt", 2, i));
			String namePredTime  = (r.readLineFile("resultFilePredTimeCF.txt", 2, i));
			
			String sparqlSuj = prefix + "Select count(distinct ?s) as ?n_so ?o { ?s a lgdo:Cafe. ?s ?p ?o . filter regex(?p,\"" + namePredTime +"\", \"i\") } order by desc(?n_so)";
			String querySuj = stagetest.query(sparqlSuj);
			//	System.out.println(sparqlNumSuj);
			System.out.println(querySuj);	
			
			
			//ecrire dans un file
	        //new PrintWriter(new FileWriter("resultFile.txt",true)) 如果删除掉true，就是不保留原来内容，重写;如果保留true就是接着写
			pw1.write(namePredTime  + "\r\n" + querySuj);
			pw1.flush();
		}
		pw1.close();
		
	}
}
