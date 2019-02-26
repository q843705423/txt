import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileDeal {
	
	public static void main(String[] args) throws Exception {
		if(args.length!=2) {
			System.out.println("your need two param,fileName and cutNumber");
			return;
			
		}
		String fileName = args[0];
		Integer cutNumber;
		try {
			cutNumber = Integer.valueOf(args[1]);
		}catch (Exception e) {
			System.out.println("your cutNumber is not a integer");
			return;
		}
		div(fileName,cutNumber);
	}
	public static void div(String fileName,Integer cutNumber) throws Exception{

		File f = new File(fileName);
	
		String father=null;
		String name = null;
		try {
			 father = f.getAbsolutePath().substring(0,f.getAbsolutePath().lastIndexOf(File.separator));
			 name = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(File.separator)+1);
		}catch (Exception e) {
			System.out.println("error");
		}
	
		System.out.println(father+" "+name);
		InputStream in = new FileInputStream(f);
		int cnt = 0;
		for(int i=in.read();i!=-1;i=in.read()) {
			cnt++;
		}
		in.close();
		InputStream in2 = new FileInputStream(f);
		if(cnt%cutNumber==0) {
			int each = cnt/cutNumber;
			System.out.println("if each:"+each);
			for(int i=0;i<cutNumber;i++) {
				OutputStream out = new FileOutputStream(new File(father+File.separator+name+"."+i+".tmp"));
				for(int j=0;j<each;j++) {
					out.write(in2.read());
				}
				out.close();
			}
		
		}else {
			int each = cnt/cutNumber;
			System.out.println("else each:"+each);
			for(int i=0;i<cutNumber+1;i++) {
				OutputStream out = new FileOutputStream(new File(father+File.separator+name+"."+i+".tmp"));
				if(i!=cutNumber) {
					for(int j=0;j<each;j++) {
						out.write(in2.read());
					}
				}else {
					for(int j=0;j<cnt%cutNumber;j++) {
						out.write(in2.read());
					}
				}			
				out.close();
			}
		}
		in2.close();
	}
	
	public static void union(String fileName) {
		File f = new File(fileName);
	}
	
	
}
