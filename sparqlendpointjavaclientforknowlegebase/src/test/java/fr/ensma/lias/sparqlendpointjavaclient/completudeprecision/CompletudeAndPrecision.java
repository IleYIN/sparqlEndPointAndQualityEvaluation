package fr.ensma.lias.sparqlendpointjavaclient.completudeprecision;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import fr.ensma.lias.sparqlendpointjavaclient.OutputFormat;
import fr.ensma.lias.sparqlendpointjavaclient.SPARQLEndpointClient;
import fr.ensma.lias.sparqlendpointjavaclient.utils.ReaderRank1m;
import fr.ensma.lias.sparqlendpointjavaclient.utils.ReaderRank2;

public class CompletudeAndPrecision {



	/**
	 * @param args
	 * @return 
	 * @return 
	 * @throws IOException
	 * @throws ProtocolException
	 * @throws MalformedURLException
	 */
	
	public static boolean findRegex(String str) {
	    // 要验证的字符串
	    // 验证规则 
	    String regEx = "[0-9]{1,}";
	    // 编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    // 忽略大小写的写法
	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(str);
//	    // 字符串是否与正则表达式相匹配
	    boolean rs = matcher.matches();
//	    boolean rs = matcher.find();
	    return(rs);
	}
	
	
	public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException {
		
		SPARQLEndpointClient stagetest = new SPARQLEndpointClient.Builder().url("http://linkedgeodata.org/sparql/")
				.outputFormat(OutputFormat.TAB_SEPARATED).build();

		/*以上不可改*/
	
		String prefix = "Prefix lgdr:<http://linkedgeodata.org/triplify/> Prefix lgdo:<http://linkedgeodata.org/ontology/> ";	
		
		
		
		/**
		 * 选择Sujet即Class并提出来Sujet的总数
		 */
		
		
		final int NUMBER = 30;
//		final int NUMBER = 80;
		int[] numSujtot = new int[NUMBER];
		String[] nameSuj = new String[NUMBER];
		
		
		boolean choix = true;
		
		while (choix) {
			
			System.out.println("chercher les subclass qui ont le plus d’instances \"0\"; chercher les propriétés de ces classes qui ont le plus de complétude de propriétés\"1\";"
					+ "faire le dessin pour les complétude \"2\""+ " chercher le nombre d'instances pour ces propriétés groupés par les objets donner la précision\"3\"" +"faire le dessin pour la précision \"4\""+ " Sortie? \"5\".");
			System.err.println("Attension: Vous ne pouvez pas entrer une valeur plus grande si vous n'avez pas entré les valeurs plus petites. ");
			Scanner entrer = new Scanner(System.in);
			int decisionEntrer = Integer.parseInt(entrer.nextLine());
			
			
			switch(decisionEntrer){
			
			case 0:{
				/**
				 * chercher les 5 subclass qui ont le plus d’instances
				 */
				String sparqlSuj = prefix + " Select (count(distinct?s) as ?n_stot) ?subClass where { ?subClass rdfs:subClassOf ?class. ?s a ?subClass} order by desc(?n_stot) offset 100 limit " + NUMBER;
				String querySuj = stagetest.query(sparqlSuj);
				//	System.out.println(sparqlNumSuj);
				System.out.println(querySuj);	
				
				
				//ecrire dans un file
				PrintWriter pw1 = new PrintWriter(new FileWriter("resultFileSubclass04.txt"));
		        //new PrintWriter(new FileWriter("resultFile.txt",true)) 如果删除掉true，就是不保留原来内容，重写;如果保留true就是接着写
				pw1.write(querySuj);
				pw1.flush();
				pw1.close();
//				
//				
//				//lire le file pour trouver nombre total du sujet et le nom du sujet
			
//				for (int i = 0;i < NUMBER;i++) {
//				for (int i = 0;i <3 ;i++){
//					ReaderRank1 r1 = new ReaderRank1();
//					numSujtot[i] = r1.readLineFile("resultFileSubclass2.txt", 0, i, 0);
//					ReaderRank2 r2 = new ReaderRank2();
//					nameSuj[i] = r2.readLineFile("resultFileSubclass2.txt", 2, i+1);
//					System.out.println(numSujtot[i]);
//					System.out.println(nameSuj[i]);
//				}
					
				
			}
			continue;

			case 1:{
				/**
				 * chercher les 5 propriétés de ces classes qui ont le plus de complétude
				 */
			
				int j = 0;
				
				PrintWriter pw2 = new PrintWriter(new FileWriter("resultFilePredCompletude80.txt"));
				
//				for (j = 0; j < NUMBER; j++) {
				for (j = 0;j<3;j++) {
				
					String sparqlPred = prefix + "Select (count(distinct ?s) as ?n_sp) (count(distinct ?s)*1.0/" + numSujtot[j] + " as ?ratio_n_sp_stot) ?p { ?s a <" + nameSuj[j]   + ">; ?p ?o . filter regex(?p, lgdo:) } order by desc(?n_sp) limit " + NUMBER;
					String queryPred = stagetest.query(sparqlPred);
				//	System.out.println(sparqlPred);
					System.out.println(queryPred);
					

			      	pw2.write(nameSuj[j] + "\r\n" + queryPred);
			      	pw2.flush();
			      	
				}
//如果前几个不是想要的可以用某一个命令从第几个开始
				pw2.close();	
				
			}
			continue;
			
			case 2:{
				/**
				 * faire le dessin pour la complétude
				 */
				JFrame frame=new JFrame("JavaBarChart");
				frame.setLayout(new GridLayout(2,2,10,10));
				String[] str = {"opening_hours","cuisine","operator","internet_access","takeaway"};
				frame.add(new BarChartCompletude(NUMBER, str).getChartPanel());           //添加柱形图
				frame.setBounds(50, 50, 800, 600);
				frame.setVisible(true);
			}
			continue;
			
			case 3:{
				/**
				 * chercher la précision de ces propriétés de ces classes 
				 */
				PrintWriter pwp = new PrintWriter(new FileWriter("resultFilePrecisiontest80.txt"));
				
				for (int m = 0; m<3; m++){
					int n = m*(2+NUMBER);
					ReaderRank1m rPred1 = new ReaderRank1m();
					ReaderRank2 rPred2 = new ReaderRank2();
					String[] namePred = new String[NUMBER];
					int[] numSujPred = new int[NUMBER];
					
				
					pwp.write(nameSuj[m]+ "\r\n");
					
					
					for (int k = 0; k<NUMBER; k++ ){
					
						
						numSujPred[k] = rPred1.readLineFile("resultFilePredCompletude80.txt", 1 + n, 2 + NUMBER + n, k, 0);
						//首位,末位，行数，列数 	
						namePred[k] = rPred2.readLineFile("resultFilePredCompletude80.txt", 3, k + 2 + n);
						

						/**
						 * requete predicat
						 */
						
											
						String sparqlObjetGrp = prefix + "Select (count(distinct ?s) as ?n_spGrp) (count(distinct ?s)*1.0/"+ numSujtot[m] + " as ?ratio_n_spGrp_stot) (count(distinct ?s)*1.000000/"+ numSujPred[k] + " as ?ratio_n_spGrp_sp)  ?o { ?s a <" + nameSuj[m] +"> . ?s ?p ?o . filter regex(?p,\"" + namePred[k] + "\",\"i\")} group by ?o order by desc(?n_spGrp)";
						String queryObjetGrp = stagetest.query(sparqlObjetGrp);
						
				    	System.out.println(queryObjetGrp);	
				    	

					//PredicatVoulu group by differents objets
						PrintWriter pw4 = new PrintWriter(new FileWriter("resultFileBufferComPrecis.txt"));
						pw4.write(namePred[k]+"\r\n"+queryObjetGrp);
						pw4.flush();
						pw4.close();
						
						ReaderRankSumPrecision rPredPrecis = new ReaderRankSumPrecision();
				    	double ratioPrecision = rPredPrecis.readLineFile("resultFileBufferComPrecis.txt", 1);
						System.out.println(ratioPrecision);
						pwp.write(namePred[k]+ "\t" +"ratioPrecision:" + "\t" + ratioPrecision+"\r\n");
						pwp.flush();
						
					}
				}
				
				
				pwp.close();
				
			}
			continue;	 
			
			case 4:{
				JFrame frame=new JFrame("JavaBarChart");
				frame.setLayout(new GridLayout(2,2,10,10));
				String[] str = {"cuisine", "takeaway","internet_access","smoking","outdoor_seating"};
				frame.add(new BarChartPrecision(NUMBER, str).getChartPanel());           //添加柱形图
				frame.setBounds(50, 50, 800, 600);
				frame.setVisible(true);
			}
			
			continue;
			case 5:
				choix = false;
				
			}
			
		}
		
	}
	
}
			
			
		
	
		
		
		
	
	

