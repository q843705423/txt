﻿import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class MoveFile {

	public static void hint() {
		System.out.println("your need put like:");
		System.out.println("java MoveFile in=D:\\machine_learn\\manhua out=D:\\code\\python\\models\\models-master\\research\\object_detection\\legacy\\images");
		System.out.println("java MoveFile in=/root/machine_learning/manhua out=/root/models/research/object_detection/legacy/images/");
	}
	public static void main(String[] args) throws Exception {
		
		HashMap<String,String>argMap = new HashMap<String,String>();
		for(String arg:args) {
			String key_value[] = arg.split("=");
			
			if(key_value.length!=2) {
				System.out.println("you have a error input param");
				hint();
				return;
			}
			argMap.put(key_value[0], key_value[1]);
		}
		
		if(!argMap.keySet().contains("in")) {
			System.out.println("your loss param in.");
			hint();
			return;
		}
		if(!argMap.keySet().contains("in")) {
			System.out.println("your loss param out.");
			hint();
			return;
		}
		
		
		boolean ok = true;
		
		
		String s = File.separator;
		
		String src = argMap.get("in");
		String target =	argMap.get("out");
		File targetDir = new File(target);
		if(!targetDir.exists())targetDir.mkdirs();
		
		File testDir = new File(targetDir.getPath()+s+"test");
		if(!testDir.exists())testDir.mkdirs();
		File trainDir = new File(targetDir.getPath()+s+"train");
		if(!trainDir.exists())trainDir.mkdirs();
		int cnt = 0;
		File root = new File(src);
		String[] mans = root.list();
		for(String man:mans) {
			
			System.out.println("man:"+man);
			File f = new File(root.getPath()+s+man);
			
			File bFile = new File(f.getPath()+s+"bb");
			
			String []vs = bFile.list();
			for(String v:vs) {
				File vFile = new File(bFile.getPath()+s+v);
				String[]pages = vFile.list();
				for(String page:pages) {
					if(page.endsWith("jpg")) {
						File pFile = new File(vFile.getPath()+s+page);
						System.out.println(pFile.getPath());
						String t = pFile.getPath();
						String pageNum = t.substring(t.lastIndexOf(s)+1,t.lastIndexOf("."));
						File xmlFile = new File(vFile.getPath()+s+"outputs"+s+pageNum+".xml");
						String rand = randString(32);
						if(ok==true) {
							String path = Math.random()>0.1?"train"+s:"test"+s;
							String xmlTargetPath = target+path+rand+".xml";
							String imgTargetPath = target+path+rand+".jpg";
							System.out.println(xmlTargetPath);
							copyXmlAndDeal(xmlFile, xmlTargetPath,imgTargetPath,path+rand+".jpg");
							copyImg(pFile,imgTargetPath );
						}
						
						
					
					}
					
					cnt++;
				}
			}
			
		}
		System.out.println(cnt);
		
		
	}
	public static String  randString(int num) {
		String result = "";
		
		
		for(int i=0;i<num;i++) {
			int a = (int) (Math.random()*52);
			result+= (char)(a<26?a+'a':a-26+'A');
		}
		return result;
	}
	
	public static void copy(File source,String target) throws Exception{
		FileInputStream in = new FileInputStream(source);
		FileOutputStream out = new FileOutputStream(new File(target));
	
		for(int i=in.read();i!=-1;i=in.read()) {
			out.write(i);
		}
		in.close();
		out.close();
		
	}
	public static void copyImg(File source,String target)throws Exception {

        File fileOut = new File(target);
        FileInputStream fileInputStream = new FileInputStream(source);
        FileOutputStream fileOutputStream = new FileOutputStream(fileOut,true);
   
        byte[] buf = new byte[1024*3];
        while((fileInputStream.read(buf))!=-1)
        {
            fileOutputStream.write(buf);
        }
        fileOutputStream.close();
        fileInputStream.close();

	}
	public static void copyXmlAndDeal(File source,String target,String path,String filename)throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(source));
		FileWriter fw = new FileWriter(new File(target));
		String content = "";
		for(String s=br.readLine();s!=null;s=br.readLine()) {
			content+=s;
		}
		content = content.replace("字", "font");
		System.out.println("m="+path);
		String s = path.replace("\\", "/");
		content = content.replaceAll("\\<path\\>.*?\\</path\\>", "<path>"+s+"</path>");
		content = content.replaceAll("\\<filename\\>.*?\\</filename\\>", "<filename>"+filename+"</filename>");
		fw.write(content);
		br.close();
		fw.close();
	}
}
