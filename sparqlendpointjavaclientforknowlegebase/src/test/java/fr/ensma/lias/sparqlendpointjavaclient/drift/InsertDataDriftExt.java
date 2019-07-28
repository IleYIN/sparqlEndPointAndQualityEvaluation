package fr.ensma.lias.sparqlendpointjavaclient.drift;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import fr.ensma.lias.sparqlendpointjavaclient.OutputFormat;
import fr.ensma.lias.sparqlendpointjavaclient.SPARQLEndpointClient;
import fr.ensma.lias.sparqlendpointjavaclient.utils.ReaderRank1;
import fr.ensma.lias.sparqlendpointjavaclient.utils.ReaderRank2;

public class InsertDataDriftExt {
	
	public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException {
		
		
		
		ReaderRank2 readData = new ReaderRank2();
		
		for (int i = 1; i<3; i++){
			
			String Data = readData.readLineFile("resultFileSubclass2.txt", 2, i+1);
			
			if (Data == null) break;
			else{
				String[] values = Data.split("/");
				if (values.length>=5){
					Data = values[4];
					System.out.println(Data);
					
					String urladdress1 = "http://193.55.163.212:8214/lgeodata2014/sparql";
					String command1 = "SELECT (count(distinct ?subject) as ?n_s) ?subject WHERE {?subject a lgdo:"+Data+".   } group by ?subject order by ?subject";
					String resultfilename1 = new String("resultFileDriftExtBuffer0001"+i+".txt");
					
					String urladdress2 = "http://193.55.163.212:8215/lgeodata2015/sparql";
					String command2 = "SELECT (count(distinct ?subject) as ?n_s) ?subject WHERE {?subject a lgdo:"+Data+".    } group by ?subject order by ?subject";
					String resultfilename2 = new String("resultFileDriftExtBuffer0002"+i+".txt");
					
					requete(urladdress1, command1, resultfilename1);
					requete(urladdress2, command2, resultfilename2);
					
					List<DataDrift> list1 = new ArrayList<DataDrift>();
					insertData("resultFileDriftExtBuffer0001"+i+".txt", list1);	
					
					List<DataDrift> list2 = new ArrayList<DataDrift>();
					insertData("resultFileDriftExtBuffer0002"+i+".txt", list2);	
					
					
					
					PrintWriter pwDiff = new PrintWriter(new FileWriter("resultFileDiffDriftExt3.txt",true));
					List<String> diff2_1 = compare(list1, list2);
					List<String> diff1_2 = compare(list2, list1);
					
					System.out.println(diff2_1.size());
					System.out.println(diff1_2.size());
					double disjac = (double)(diff2_1.size()+diff1_2.size())/(double)(diff2_1.size()+diff1_2.size()+list2.size()-diff2_1.size());
					
					System.out.println(Data+" DriftExt: "+disjac);
					pwDiff.write(Data+"\r\n");
					pwDiff.write("version2015-version2014 --> nombre des instances nouvelles: "+ diff2_1.size()+"\r\n");
					pwDiff.write("version2014-version2015 --> nombre des instances supprimées: "+ diff1_2.size()+"\r\n");
					pwDiff.write("Jaccard distance (Extensional drift) : "+ disjac+"\r\n");
					pwDiff.flush();
					
//					pwDiff.write("-------------des instances nouvelles----------------"+"\r\n");
//					for (String str: diff2_1){
//						pwDiff.write(str+"\r\n");
//						pwDiff.flush();
//					}
//					pwDiff.write("-------------des instances supprimées---------------"+"\r\n");
//					
//					
//					for (String str: diff1_2){
//						pwDiff.write(str+"\r\n");
//						pwDiff.flush();
//					}
					
					pwDiff.close();
				}
			}
		}
	}
	
		
		
		
	
	
	public static List<String> compare(List<DataDrift> list1, List<DataDrift> list2){
		
		List<String> diff = new ArrayList<String>();
		
		for (int i=0; i<list2.size();i++){
			boolean temp = false;
			String tempData = list2.get(i).getData();
//			System.out.println(tempData);
			for (int j=0; j<list1.size();j++){
				System.out.println(list1.get(j).getData());
				if (list1.get(j).getData().contains(tempData)){
					temp = true;
					System.out.println(tempData+"contain");
					break;
				}
			}
			if (false==temp){
				diff.add(tempData);
				System.out.println(tempData+"not contain");
			}
		}
		
		return diff;
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
	private static void insertData(String filename, List<DataDrift> list){
		ReaderRank2 readData = new ReaderRank2();
		ReaderRank1 readNum = new ReaderRank1();
		
		for (int i = 0; i<1000000; i++){
			
			int number = readNum.readLineFile(filename, 0, i,0);
//				System.out.println(number);
			String Data = readData.readLineFile(filename, 1, i+1);
			
			if (number==0) break;
			
			if (Data != null){
				DataDrift prop = new DataDrift(i, number,Data);
				list.add(prop);
				System.out.println(list.get(i).getData());
			}
		}
	}
}
