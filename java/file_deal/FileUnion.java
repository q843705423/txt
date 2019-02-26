import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class FileUnion {

	public static void main(String[] args) throws IOException {
		if(args.length!=1) {
			System.out.println("your need a param about fileName");
			return ;
		}
		String fileName = args[0];
		File f = new File(fileName);
		String father = f.getAbsolutePath().substring(0,f.getAbsolutePath().lastIndexOf(File.separator));
		File fatherDir = new File(father);
		String[] list = fatherDir.list(new FilenameFilter() {			
			@Override
			public boolean accept(File dir, String name) {
				return name.contains("tmp")&&name.contains(fileName);
			}
		});
		ArrayList<String>arr = new ArrayList<>();
		for(int i=0;i<list.length;i++) {
			arr.add(list[i]);
		}
		arr.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				String temp1 = o1.replace(fileName+".", "").replace(".tmp", "");
				String temp2 = o2.replace(fileName+".", "").replace(".tmp", "");
				return Integer.valueOf(temp1)-Integer.valueOf(temp2);
			}
		});
		System.out.println(arr);
		FileOutputStream out = new FileOutputStream(new File(fileName));
		for(String son:arr) {
			FileInputStream in = new FileInputStream(new File(son));
			for(int i=in.read();i!=-1;i=in.read()) {
				out.write(i);
			}
			in.close();
		}
		out.close();
	}
	
}
