package file_deal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class RemoveTable {

	public static void main(String[] args) throws Exception {
		String src ="D:\\data\\japaese\\fifth_table.txt";
		File f = new File(src);
		ArrayList<String>ls = new ArrayList<>();
		BufferedReader br = new BufferedReader( new FileReader(f));
		String str = br.readLine();
		while(str!=null) {
			ls.add(str);
			str = br.readLine();
		}
		List<String >real = new ArrayList<String>();
		for(int i=0;i<ls.size();i++) {
			String s = ls.get(i);
			if(s.contains("ÐÐ")) {
				continue;
			}
			if(containCharA2Z(s)) {
				continue;
			}
			
			real.add(s);
			
		}
		
		System.out.println(real.size());
		File ff = new File("D:\\data\\japaese\\real_fifth_table.txt");
		FileWriter fw = new FileWriter(ff);
		for(String s:real) {
			fw.write(s+"\r\n");
		}
		fw.close();
		
		
		System.out.println(real);
		
	
	}

	private static boolean containCharA2Z(String s) {
		for(char i='a';i<='z';i++) {
			if(s.contains(i+"")) {
				return true;
			}
		}
		return false;
	}
}
