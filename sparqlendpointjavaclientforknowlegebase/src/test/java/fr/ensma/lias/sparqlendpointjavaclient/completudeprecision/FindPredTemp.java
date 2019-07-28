package fr.ensma.lias.sparqlendpointjavaclient.completudeprecision;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import fr.ensma.lias.sparqlendpointjavaclient.OutputFormat;
import fr.ensma.lias.sparqlendpointjavaclient.SPARQLEndpointClient;

public class FindPredTemp {
	
	
	public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException {
		
		SPARQLEndpointClient stagetest = new SPARQLEndpointClient.Builder().url("http://linkedgeodata.org/sparql/")
				.outputFormat(OutputFormat.TAB_SEPARATED).build();

		/*以上不可改*/
	
		String prefix = "Prefix lgdr:<http://linkedgeodata.org/triplify/> Prefix lgdo:<http://linkedgeodata.org/ontology/> ";	
	
		String sparqlSuj = prefix + "Select count(distinct ?s) as ?n_sp ?p { ?s a lgdo:Cafe. ?s ?p ?o . filter regex(?p,\"http://linkedgeodata.org/ontology/[a-zA-Z]{0,}_hour|time\", \"i\") } order by desc(?n_sp)";
		String querySuj = stagetest.query(sparqlSuj);
		//	System.out.println(sparqlNumSuj);
		System.out.println(querySuj);	
		
		
		//ecrire dans un file
		PrintWriter pw1 = new PrintWriter(new FileWriter("resultFilePredTimeCF.txt"));
        //new PrintWriter(new FileWriter("resultFile.txt",true)) 如果删除掉true，就是不保留原来内容，重写;如果保留true就是接着写
		pw1.write(querySuj);
		pw1.flush();
		pw1.close();
		
	}
}
