package zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.*;
public class UnionFile {
	
	public static void main(String[] args)throws Exception {
		File ans = new File("/home/chenqiqi/iso/w7.iso");
		FileOutputStream outans = new FileOutputStream(ans);
		List<File>files = Arrays.asList(
			new File("/home/chenqiqi/iso/iso1.tmp"),
			new File("/home/chenqiqi/iso/iso2.tmp")
		);
	
		files.stream().forEach((f)->{
			try {
				byte[]b = new byte[1024];
				long num = 0;
				FileInputStream in = new FileInputStream(f);
				for(int i=in.read(b);i!=-1;i=in.read(b)) {
					num+=i;
					outans.write(b);
					if(num%(1024*1024)==0) {
						System.out.println(num>>20);
					}
					
				}
				in.close();
				
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			
		});
		outans.close();
		
	}
}

