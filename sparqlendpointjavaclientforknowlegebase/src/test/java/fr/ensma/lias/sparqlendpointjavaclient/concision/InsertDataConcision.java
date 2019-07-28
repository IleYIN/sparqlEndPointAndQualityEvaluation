package fr.ensma.lias.sparqlendpointjavaclient.concision;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.ensma.lias.sparqlendpointjavaclient.OutputFormat;
import fr.ensma.lias.sparqlendpointjavaclient.SPARQLEndpointClient;
import fr.ensma.lias.sparqlendpointjavaclient.utils.ReaderRank1;
import fr.ensma.lias.sparqlendpointjavaclient.utils.ReaderRank2;

public class InsertDataConcision {
	
	public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException {
		
		String urladdress = "http://linkedgeodata.org/sparql/";
//		String command = "SELECT distinct ?p WHERE {?s a lgdo:Restaurant. ?s ?p ?o . filter regex(?p, \"http://linkedgeodata.org/ontology/\")}  order by ?p";
		String command = "SELECT DISTINCT ?class WHERE {?s a ?class.  filter regex(?class, \"http://linkedgeodata.org/ontology/\")}  order by ?class";
		String resultfilename = "resultFileConciPred.txt";

		
		requete(urladdress, command, resultfilename);
		
		List<DataConcision> list = new ArrayList<DataConcision>();
		insertDataString("resultFileConciClass.txt", list);	
		
		LinkedList[] simiRow = new LinkedList[30000];
		
		PrintWriter pwSimi = new PrintWriter(new FileWriter("resultFileConSimiClass.txt"));
		
		List<Integer> listIndex = new ArrayList<Integer>();
		List<Integer> listIndexJ = new ArrayList<Integer>();
		
		for (int i=0; i<list.size(); i++){
			for (int j=i+1; j<list.size();j++){
				if ((CompareString.compare(list.get(i).getItem(), list.get(j).getItem(), 0.85)&&!listIndexJ.contains(i))){
					if (simiRow[i]==null){
						LinkedList<String> listline = new LinkedList<String>();
						simiRow[i] = listline;
						listline.add(list.get(i).getItem());
						listline.add(list.get(j).getItem());
						listIndex.add(i);
						listIndexJ.add(j);
//						pwSimi.write(list.get(i).getItem()+"\r\n"+list.get(j).getItem());
//						pwSimi.flush();
					} else{
						LinkedList<String> listline = simiRow[i];
						listline.add(list.get(j).getItem());
						listIndexJ.add(j);
//						pwSimi.write(list.get(j).getItem());
//						pwSimi.flush();
					}
				
//					LinkedList<String> listline = simiRow[i];
//					for (String str:listline)
//					System.out.println(str);
//					System.out.println("-----------");
				}
			}
		}
		
		
		int numbergroup = 0;
		int numbersimitot = 0;
		for(int n : listIndex){
			LinkedList<String> listline = simiRow[n];
			for(String prop : listline){
				System.out.println(prop+"\r\n");
				pwSimi.write(prop+"\r\n");
				pwSimi.flush();
				numbersimitot++;
			}
			numbergroup++;
			System.out.println("-----------");
			pwSimi.write("-----------"+"\r\n");
		}
		double concision = 1.0 - (double)(numbersimitot-numbergroup)/(double)list.size();
		pwSimi.write("-----------"+"\r\n"+"numbergroup: "+numbergroup+"  numbersimitot: "+numbersimitot+" concision: "+concision);
		pwSimi.flush();
		pwSimi.close();
	}
	
	
	
	
	public static void requete(String urladdress, String command, String resultfilename) throws IOException {
		
		SPARQLEndpointClient stagetest = new SPARQLEndpointClient.Builder().url(urladdress)
				.outputFormat(OutputFormat.TAB_SEPARATED).build();

		/*以上不可改*/
	
		String prefix = "Prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> Prefix lgdr:<http://linkedgeodata.org/triplify/> Prefix lgdo:<http://linkedgeodata.org/ontology/> ";	
		
		
		PrintWriter pwC = new PrintWriter(new FileWriter(resultfilename));
		
		
		String sparqlPred = prefix + command;
		String queryPred = stagetest.query(sparqlPred);
	//	System.out.println(sparqlPred);
//		System.out.println(queryPred);
		
		
		pwC.write(queryPred);
      	pwC.flush();
      	pwC.close();
		 
      
		
	}
	private static void insertDataString(String filename, List<DataConcision> list){
		
		ReaderRank2 readData = new ReaderRank2();
		
		for (int i = 0; i<list.size(); i++){
			
			String Data = readData.readLineFile(filename, 1, i+1);
			
			if (null==Data) break;
			else{
				String[] values = Data.split("/");
				if (values.length>=5){
					Data = values[4];
					
					DataConcision prop = new DataConcision(Data);
					list.add(prop);
//					System.out.println(list.get(i).getItem());
				};
			}
		}
	}
}
