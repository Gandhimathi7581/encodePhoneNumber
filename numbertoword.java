import java.util.*;
import java.io.*;

public class numbertoword {

private static Map<String, List<String>> dictionary = new HashMap<>();

private static List<List<String>> findWords(String number, int startAt) {
  //System.out.println("number : " + number);
  ArrayList<List<String>> result = new ArrayList<>();
  if(startAt == number.length()) {
    result.add(new ArrayList<String>());
    return result;
  }
  for(int endAt = startAt + 1; endAt <= number.length(); endAt++) {
    List<String> words = dictionary.get(number.substring(startAt, endAt));
    if(words != null) {
      List<List<String>> encodings = findWords(number, endAt);
      for(String word: words) {
        for(List<String> encoding: encodings) {
          List<String> enc = new ArrayList<>(encoding);
          enc.add(0, word);
          result.add(enc);
        }
      }
    }
  }
  
  return result;
}

private static List<List<String>> findAllCombinations(String number) {
  return findWords(number, 0);
}

public static String getNumberEncode(char[] chrarr) {
	String numberEncode = "";
	for(int i =0 ; i < chrarr.length;i++) {
		if(chrarr[i] == 'a' || chrarr[i] == 'b' || chrarr[i] == 'c') {
		    numberEncode = numberEncode + "2";
		} 
		else if(chrarr[i] == 'd' || chrarr[i] == 'e' || chrarr[i] == 'f') {
		    numberEncode = numberEncode + "3";
		}
		else if(chrarr[i] == 'g' || chrarr[i] == 'h' || chrarr[i] == 'i') {
		    numberEncode = numberEncode + "4";
		}
		else if(chrarr[i] == 'j' || chrarr[i] == 'k' || chrarr[i] == 'l') {
		    numberEncode = numberEncode + "5";
		}
		else if(chrarr[i] == 'm' || chrarr[i] == 'n' || chrarr[i] == 'o') {
		    numberEncode = numberEncode + "6";
		}
		else if(chrarr[i] == 'p' || chrarr[i] == 'q' || chrarr[i] == 'r' || chrarr[i] == 's') {
		    numberEncode = numberEncode + "7";
		}
		else if(chrarr[i] == 't' || chrarr[i] == 'u' || chrarr[i] == 'v') {
		    numberEncode = numberEncode + "8";
		}
		else if(chrarr[i] == 'w' || chrarr[i] == 'x' || chrarr[i] == 'y' || chrarr[i] == 'z') {
		    numberEncode = numberEncode + "9";
		}
	}
	return numberEncode;
}

public static void loadDictionaryData(String dictionaryFile) {

	try {
 	Scanner filescan = new Scanner(new File(dictionaryFile));
        
	
        while (filescan.hasNext()) {
	    String strword = filescan.nextLine();
	    String numberEncode = "";
            char[] charArr = strword.toCharArray();  
            
	    numberEncode = getNumberEncode(charArr);
            
		
        
	    List<String> list = dictionary.get(numberEncode);
	    if(list != null && list.size() > 0) {	
		
		list.add(strword);
            	dictionary.put(numberEncode, list);
	    }
	    else {
		List<String> newlist = new ArrayList<String>();
                newlist.add(strword);
		dictionary.put(numberEncode, newlist);
	    }
        }
	}
	catch(FileNotFoundException e) {
		System.out.println("File not found in the specified location");
	}
}

public static void main(String[] args) {

  Scanner filescan = null;
 
       if(args.length > 0 && args[0] != null) {
	   try {
           	filescan = new Scanner(new File(args[0]));
	   	
	   }
	   catch(FileNotFoundException e){
		   System.out.println("Input file not found");
		   return;
           }
       }
       else {
              System.out.println("Insufficient arguments, require atleast one argument ");
		return;
       }
      
       // System.out.println("Argument " + i + ": " + args[i]);
	if(args.length > 1 && args[1].contains("-d")) {
	    //System.out.println("inside if 1");
	    if(args[2] != null) {
            	loadDictionaryData(args[2]);
	    }
	    
        }
	else {
		//System.out.println("inside default dict");
		loadDictionaryData("dictionary.txt");
	    }
  
  //System.out.println("dict size : " + dictionary.size());
  
  //Scanner sc = new Scanner(System.in);
  String getPhoneNo="";
  while(filescan.hasNext()) {
  
  getPhoneNo = filescan.nextLine();
  System.out.println("Phone No :" +getPhoneNo);
  List<List<String>> result = findAllCombinations(getPhoneNo);
  // If no mapping is found go for second level of iteration, where exactly one digit is not replaced and kept as such.
  if(result.size() == 0) {
	List<List<String>> result1 = new ArrayList<List<String>>();
  	List<List<String>> result2 = new ArrayList<List<String>>();
        
	for(int i = 0; i<getPhoneNo.length();i++) {
		char[] strChrArr = getPhoneNo.toCharArray();
		// leave first digit and see if all other digits could be encoded with words
		if(i==0) {
			String substr = getPhoneNo.substring(i+1,getPhoneNo.length());
			//System.out.println(getPhoneNo.substring(i+1,getPhoneNo.length()));
			result1 = findAllCombinations(substr);
			
						
		}else if(i == getPhoneNo.length()-1) {    
			// leave last digit and see if all other digits could be encoded with words
			String substr = getPhoneNo.substring(0,i).concat(getPhoneNo.substring(i+1,getPhoneNo.length()));
			result1 = findAllCombinations(substr);
			
	    	}
		else {
		     // leave a digit in between and see if all other digits could be encoded with words
		     result1 = findAllCombinations(getPhoneNo.substring(0,i));
		     result2 = findAllCombinations(getPhoneNo.substring(i+1,getPhoneNo.length()));
		
		}
		if(result1.size() > 0) {
			     
			    for(List<String> strList : result1){
				List<String> updatedList = strList;
				
				String getOutput = "";
				if(i==0)
				{
				    getOutput = getOutput + Character.toString(strChrArr[i]) + "-";
				    for(int j = 0; j < updatedList.size(); j++) {
				    
				        if(j == 0) 
				    	    getOutput = getOutput + updatedList.get(j);
				        else
					    getOutput = getOutput + "-" +updatedList.get(j);
				    }
				    System.out.println(getOutput.toUpperCase());	
				    	
				}
				else if(i == getPhoneNo.length()-1) {
				    updatedList.add(Character.toString(strChrArr[i]));
				    for(int j = 0; j < updatedList.size(); j++) {
				    
				        if(j == 0) 
				    	    getOutput = getOutput + updatedList.get(j);
				        else
					    getOutput = getOutput + "-" +updatedList.get(j);
				    }
				    System.out.println(getOutput.toUpperCase());	
				}
				else {
				    if(result2.size() > 0) {
					updatedList.add(Character.toString(strChrArr[i]));
					for(List<String> strList2 : result2) {
					    updatedList = new ArrayList<>(strList);
					    Collections.copy(updatedList, strList);
					    
					    getOutput = "";
					    for(int k = 0; k < strList2.size();k++) {
						
						updatedList.add(strList2.get(k));
					    }
					    
					for(int j = 0; j < updatedList.size(); j++) {
				    
				            if(j == 0) 
				    	        getOutput = getOutput + updatedList.get(j);
				    	    else
						getOutput = getOutput + "-" +updatedList.get(j);
					}
					System.out.println(getOutput.toUpperCase());
 				
					}
			            }
				}
					
			    }
			
			}
	}
    }
    else {
        for(List<String> strList : result) {
	    String getOutput = "";
            List<String> updatedList = strList;
	    for(int j = 0; j < updatedList.size(); j++) {
				    
		if(j == 0) 
		    getOutput = getOutput + updatedList.get(j);
		else
		    getOutput = getOutput + "-" +updatedList.get(j);
	    }
	    System.out.println(getOutput.toUpperCase());
	}
    }
    
  } //while(filescan.hasNext());

 // System.out.println(findAllCombinations("8327238837"));
  
}
}
