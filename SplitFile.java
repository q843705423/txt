package zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SplitFile{
	
	public static void main(String[]args) throws Exception {
		File f = new File("/home/chenqiqi/iso/764_xb_18_7_2.iso");
		InputStream in = new FileInputStream(f);
		long num = 0;
		byte[]b = new byte[1024];
		
		File f1 = new File("/home/chenqiqi/iso/iso1.tmp");
		File f2 = new File("/home/chenqiqi/iso/iso2.tmp");
		FileOutputStream out1 = new FileOutputStream(f1);
		FileOutputStream out2 = new FileOutputStream(f2);
		if(f1.exists()==false) {
			f1.createNewFile();
		}
		if(f2.exists()==false) {
			f2.createNewFile();
		}
		for (int i = in.read(b); i != -1; i = in.read(b)) {
			num += i;
			if (num % (1024 * 1024) == 0) {
				System.out.println(num / 1024 / 1024 );
			}
			if((num>>20)<2900) {
			//	System.out.println(i+"-----------------------------");
				out1.write(b);
				for(int j=0;j<i;j++) {
			//		System.out.println(b[j]);
				}
			//	break;
			}else {
				out2.write(b);
				//System.out.println("now is"+i);
			}
			
			
			
		}
		out1.close();
		out2.close();
	}
	
}