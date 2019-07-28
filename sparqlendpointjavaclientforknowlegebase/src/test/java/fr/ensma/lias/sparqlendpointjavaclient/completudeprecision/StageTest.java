package fr.ensma.lias.sparqlendpointjavaclient.completudeprecision;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Scanner;

import fr.ensma.lias.sparqlendpointjavaclient.OutputFormat;
import fr.ensma.lias.sparqlendpointjavaclient.SPARQLEndpointClient;
import fr.ensma.lias.sparqlendpointjavaclient.utils.Reader;

public class StageTest {



	/**
	 * @param args
	 * @return 
	 * @return 
	 * @throws IOException
	 * @throws ProtocolException
	 * @throws MalformedURLException
	 */
	
	

	
	
	
	
	
	
	
	public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException {
		
		SPARQLEndpointClient stagetest = new SPARQLEndpointClient.Builder().url("http://linkedgeodata.org/sparql/")
				.outputFormat(OutputFormat.TAB_SEPARATED).build();

		/*以上不可改*/
	
		String prefix = "Prefix lgdr:<http://linkedgeodata.org/triplify/> Prefix lgdo:<http://linkedgeodata.org/ontology/> ";	
		
		
		
		/**
		 * 选择Sujet即Class并提出来Sujet的总数
		 */
		
		
		
		int numSujtot = 0;
		int numSujPred = 0;
		String requeteSuj = null;
		String requetePred;
		String requetePredVoulu = null;
		String requeteObjetVoulu;
		boolean choix = true;
		
		while (choix) {
			
			System.out.println("Sujet? \"0\"; Menu des predicats? \"1\"; Predicat précis et sa precision? \"2\"; Objet voulu? \"3\"; Sortie? \"4\".");
			System.err.println("Attension: Vous ne pouvez pas entrer une valeur plus grande si vous n'avez pas entré les valeurs plus petites. ");
			Scanner entrer = new Scanner(System.in);
			int decisionEntrer = Integer.parseInt(entrer.nextLine());
			
			
			switch(decisionEntrer){
			
			case 0:{
				//donner le sujet	
				Scanner suj = new Scanner(System.in);
				System.out.println("entrer le sujet de la requete : ");
				requeteSuj = suj.nextLine();
				
				//afficher le nombre du sujet et nombre du predicat de ce sujet
				String sparqlSuj = prefix + "Select count(distinct ?s) as ?n_stot  count(distinct ?p) as ?n_ptot { ?s a lgdo:"+ requeteSuj+ ";  ?p ?o. filter regex(?p, lgdo:) }";
				String querySuj = stagetest.query(sparqlSuj);
				//	System.out.println(sparqlNumSuj);
				System.out.println(querySuj);	
				
				
				//ecrire dans un file
				PrintWriter pw1 = new PrintWriter(new FileWriter("resultFileSuj.txt"));
		        //new PrintWriter(new FileWriter("resultFile.txt",true)) 如果删除掉true，就是不保留原来内容，重写;如果保留true就是接着写
				pw1.write(querySuj);
				pw1.flush();
				pw1.close();
				
				
				//lire le file pour trouver nombre total du sujet
				Reader r1 = new Reader();
				numSujtot = r1.readLineFile("resultFileSuj.txt",1);
				
				
				
			}
			
			continue;

			case 1:{
				/**
				 * 选择predicat即property的首几个字母并给出目录和与sujet total占比
				 */
				
				//donner les predicats par les premieres lettres
				Scanner pred = new Scanner(System.in);
				System.out.println("entrer les premieres lettres du predicat : ");
				requetePred = pred.nextLine();
				
			

				//afficher les predicats par les premieres lettres
				String sparqlPred = prefix + "Select count(distinct ?s) as ?n_sp count(distinct ?s)*1.000000/"+ numSujtot + " as ?ratio_n_sp_stot ?p { ?s a lgdo:" + requeteSuj +"; ?p ?o . filter regex(?p, \"http://linkedgeodata.org/ontology/" + requetePred + "\",\"i\") } order by ?p";
				String queryPred = stagetest.query(sparqlPred);
			//	System.out.println(sparqlPred);
				System.out.println(queryPred);
				
				//Predicat的目录，如开头为Open...
				PrintWriter pw2 = new PrintWriter(new FileWriter("resultFilePredMenu.txt"));
		      	pw2.write(queryPred);
				pw2.flush();
				pw2.close();
				
				
			}
			
			continue;
			
			
			case 2:{
				/**
				 * 选择predicat voulu并求和sujet total占比 以及 和predicat voulu grp与predicat的占比		
				 */
				
				
				//donner le predicat voulu
				Scanner predVoulu = new Scanner(System.in);
				System.out.println("entrer les premieres lettres du predicat voulu : ");
				requetePredVoulu = predVoulu.nextLine();
				
				//afficher le nombre du predicat voulu et le nombre du sujet pour ce predicat groupe par l'objet
				String sparqlObjet = prefix + "Select count(distinct ?s) as ?n_sp count(distinct ?s)*1.000000/"+ numSujtot + " as ?ratio_n_sp_stot { ?s a lgdo:"+ requeteSuj +". ?s ?p ?o . filter regex(?p,\"http://linkedgeodata.org/ontology/" + requetePredVoulu + "\",\"i\") }";
				String queryObjet = stagetest.query(sparqlObjet);
				PrintWriter pw3 = new PrintWriter(new FileWriter("resultFilePredPrecis.txt"));
				pw3.write(queryObjet);
				pw3.flush();
				pw3.close();
				System.out.println(queryObjet);
				
				Reader r2 = new Reader();
				numSujPred = r2.readLineFile("resultFilePredPrecis.txt", 1);
				
				String sparqlObjetGrp = prefix + "Select count(distinct ?s) as ?n_spGrp count(distinct ?s)*1.000000/"+ numSujtot + " as ?ratio_n_spGrp_stot count(distinct ?s)*1.000000/"+ numSujPred + " as ?ratio_n_spGrp_sp  ?o { ?s a lgdo:" + requeteSuj +". ?s ?p ?o . filter regex(?p,\"http://linkedgeodata.org/ontology/" + requetePredVoulu + "\",\"i\")} group by ?o order by desc(?n_spGrp)";
				String queryObjetGrp = stagetest.query(sparqlObjetGrp);
				
		    	System.out.println(queryObjetGrp);	
		    	
		
				

			//PredicatVoulu group by differents objets
				PrintWriter pw4 = new PrintWriter(new FileWriter("resultFilePredPrecis.txt",true));
				pw4.write(queryObjetGrp);
				pw4.flush();
				pw4.close();
				
				ReaderRankSumPrecision rpre = new ReaderRankSumPrecision();
		    	double ratioPrecision = rpre.readLineFile("resultFilePredPrecis.txt",2);
				System.out.println(ratioPrecision);
				
				PrintWriter pw402 = new PrintWriter(new FileWriter("resultFilePredPrecis.txt",true));
				pw402.append("ratioPrecision " + ratioPrecision);
				pw402.flush();
				pw402.close();
				
				
			}
			
			continue;
			
			case 3:{
				/**
				 * Objet voulu通过正则表达式选出想要的forme de l'objet
				 */
				

				//donner l'objet voulu
				Scanner objetVoulu = new Scanner(System.in);
				System.out.println("entrer l'objet voulu en utilisant regex: ");
				requeteObjetVoulu = objetVoulu.nextLine();
				
				//afficher le nombre du objet voulu
				String sparqlObjetVoulu = prefix + "Select count(distinct ?s) as ?n_sov count(distinct ?s)*1.000000/"+ numSujtot + " as ?ratio_n_sov_tot count(distinct ?s)*1.000000/"+ numSujPred + " as ?ratio_n_sov_sp { ?s a lgdo:"+ requeteSuj +". ?s ?p ?o . filter regex(?p,\"http://linkedgeodata.org/ontology/" + requetePredVoulu + "\",\"i\"). filter regex(?o, \"" + requeteObjetVoulu + "\", \"i\")} ";
				
				PrintWriter pw5 = new PrintWriter(new FileWriter("resultFileObjetVoulu.txt"));
				String queryObjetVoulu = stagetest.query(sparqlObjetVoulu);
				pw5.write(queryObjetVoulu);
				pw5.flush();
				pw5.close();
				
				System.out.println(queryObjetVoulu);
				
				Reader r3 = new Reader();
				int numSujOV = r3.readLineFile("resultFileObjetVoulu.txt", 1);
				
				String sparqlObjetVouluGrp = prefix + "Select count(distinct ?s) as ?n_sovGrp count(distinct ?s)*1.000000/"+ numSujtot + " as ?ratio_n_sovGrp_stot  count(distinct ?s)*1.000000/"+ numSujPred+ " as ?ratio_n_sovGrp_sp  count(distinct ?s)*1.000000/"+ numSujOV + " as ?ratio_n_sovGrp_sov ?o { ?s a lgdo:"+ requeteSuj +". ?s ?p ?o . filter regex(?p,\"http://linkedgeodata.org/ontology/" + requetePredVoulu + "\",\"i\"). filter regex(?o, \"" + requeteObjetVoulu + "\", \"i\")} order by desc(?n_sovGrp)";
				String queryObjetVouluGrp = stagetest.query(sparqlObjetVouluGrp);
				
				System.out.println(queryObjetVouluGrp);		
				
				//ObjetVoulu group by differents objets
				
				PrintWriter pw6 = new PrintWriter(new FileWriter("resultFileObjetVoulu.txt",true));
		        		
				pw6.write(queryObjetVouluGrp);
				pw6.flush();
				pw6.close();
				

				
				
				
			}
			
			continue;
			
			
			case 4:
				choix = false;
		
			}
				
					   
		}

		
		
		}
		
		
	
	
}
