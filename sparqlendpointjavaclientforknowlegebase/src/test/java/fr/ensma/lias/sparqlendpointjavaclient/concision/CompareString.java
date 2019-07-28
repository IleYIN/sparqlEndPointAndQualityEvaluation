package fr.ensma.lias.sparqlendpointjavaclient.concision;

public class CompareString {
	
	//Méthode pour juger si la similitude des deux termes est supérieur à la limite donnée
	
	public static boolean compare (String strA, String strB, double limiteSimi){
		
		int maxLength = Math.max(strA.length(), strB.length());
		double ratio = (double)longestCommonSubstring(strA,strB).length()/(double)maxLength;
		if (ratio > limiteSimi){
			return true;
		}
		return false;
	}
	
	// Méthode pour tirer la chaine de caractères commune des deux termes « strA » et « strB »  
	
	 private static String longestCommonSubstring(String strA, String strB) {  
		 
	       if (strA.length()<strB.length()){
	    	   String temp = strA;
	    	   strA = strB;
	    	   strB = temp;
	       }
		 
		   char[] chars_strA = strA.toCharArray();  
	       char[] chars_strB = strB.toCharArray();  
	       int m = chars_strA.length;  
	       int n = chars_strB.length;  
	       int[][] matrix = new int[m + 1][n + 1];  
	       
	       for (int i = 1; i <= m; i++) {  
	           for (int j = 1; j <= n; j++) {  
	               if (chars_strA[i - 1] == chars_strB[j - 1])  
	                   matrix[i][j] = matrix[i - 1][j - 1] + 1;  
	               else  
	                   matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);  
	           }  
	       }  
	 
	       char[] result = new char[matrix[m][n]];  
	       int currentIndex = result.length - 1;  
	       
	       while (matrix[m][n] != 0) {  
	           if (matrix[n] == matrix[n - 1])  
	               n--;  
	           else if (matrix[m][n] == matrix[m - 1][n])   
	               m--;  
	           else {  
	               result[currentIndex] = chars_strA[m - 1];  
	               currentIndex--;  
	               n--;  
	               m--;  
	           }  
	       }  
	       return new String(result);  
	 }  
	 
	
//	public static void main(String[] args) {
//		String a = "bbbbdddddddd";
//		String b = "ddddbbbbb";
//		System.out.println(longestCommonSubstring(a,b));
//	}
}
