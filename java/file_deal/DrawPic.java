package file_deal;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.omg.CORBA.INTERNAL;
 
public class DrawPic {
	
	
	
	
	/*public static void main(String[] args) {
 
	    BufferedImage bi = new BufferedImage(800, 600, BufferedImage.TYPE_INT_BGR);
 
	    final File file = new File("D:\\a.jpg");
		
	    try {
	    	if(file.exists()) {
		    	file.delete();
		    	file.createNewFile();
	    	}
	    }catch(IOException e) {
	    	e.printStackTrace();
	    }
	    
	    
		writeImage(bi, "jpg", file);
		System.out.println("绘图成功");
		
	}
	
	*//** 通过指定参数写一个图片  *//*
    public static boolean writeImage(BufferedImage bi, String picType, File file) {
        
    	Graphics g = bi.getGraphics();
        g.setColor(new Color(12, 123, 88));
        g.drawLine(0, 100, 100, 100);
        
        
        Graphics w = bi.getGraphics();
        w.setColor(new Color(255,255,255));
        w.fillRect(0, 0, 800, 600);
        
        w.setColor(new Color(255,0,0));
        Font f = new Font("微软雅黑", Font.BOLD, 36);
        g.setFont(f);
        g.setColor(Color.BLACK);
        g.drawString("我为掌控天下代言", 111, 222);
        
        boolean val = false;
        try {
            val = ImageIO.write(bi, picType, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return val;
    }*/
	private static List<String>fontList = new ArrayList<>();
	public static void load(String src)throws Exception{
		File f = new File(src);
		BufferedReader br = new BufferedReader(new FileReader(f));
		for(String s = br.readLine();s!=null;s=br.readLine()) {
			fontList.add(s);
		}
		br.close();
	}
	public static void main(String[] args)throws Exception {
		load("D:\\data\\japaese\\real_fifth_table.txt");
		int fontSize[] = new int[] {
				8,12,16,20,24,28,32
		};
		
		Pictrue p = new Pictrue(800, 600);
		for(int i=0;i<50;i++) {
			int s = (int) (Math.random()*fontList.size());
			int size = (int) (Math.random()*fontSize.length);
			int x = (int) (Math.random()*800);
			int y = (int) (Math.random()*600);
			String backgroundColor = Math.random()>0.7?"#FFFFFF":"#000000";
			String fontColor = backgroundColor.equals("#FFFFFF")?"#000000":"#FFFFFF";
	//		System.out.println("s="+s+",size="+size+",x="+x+",y="+y);
			boolean ok = p.drawStringAndBackground(fontList.get(s), x, y, fontSize[size],fontColor , backgroundColor);
			if(ok==false)i--;
		}
		
	//	p.drawStringAndBackground(fontList.get(s), size, color, startX, startY);
		
	//	p.drawStringAndBackground("先生", 0, 0, 20, "#FFFFFF", "#000000");
		
	//	p.drawStringAndBackground("天空", 100, 200, 16, "#FFFFFF", "#000000");
		
	//	p.drawStringAndBackground("ウ", 322, 400, 12, "#FFFFFF", "#000000");
		p.output("D:\\b.jpg","D:\\b.xml");
		
		
	}
   
}
class Pictrue{
	private BufferedImage bi;
	private Graphics g;
	private List<HashMap<String,String>>maps = new ArrayList<>();
	public Pictrue(int width,int heigth) {
		bi = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_BGR);
		g = bi.getGraphics();
		g.fillRect(0, 0, width, heigth);
	
	}
	
	public boolean drawStringAndBackground(String message,int x,int y,int fontSize,String fontColor,String backgroundColor) {
		
		for(int i=0;i<message.length();i++) {
			boolean ok = drawCharAndBackground(message.charAt(i), x, y, fontSize, fontColor, backgroundColor,i);
			if(ok==false)return ok;
			break;
		}
		return true;
	}
	public boolean drawCharAndBackground(char message,int x,int y,int fontSize,String fontColor,String backgroundColor,int i) {
		HashMap<String,String>map = new HashMap<String,String>();
		int minx = (x+fontSize*i);
		int miny = y;
		int maxx = (  x+fontSize*(i+1));
		int maxy = (int)(y+fontSize*1.2);
		System.out.println("---------------------------");
		/*System.out.println("minx="+minx);
		System.out.println("miny="+miny);
		System.out.println("maxx="+maxx);
		System.out.println("maxy="+ maxy);*/
		map.put("minx", minx+"");
		map.put("miny", miny+"");
		map.put("maxx", maxx+"");
		map.put("maxy", maxy+"");
		map.put("name", message+"");
		if(minx<0||maxx>800||miny<0||maxy>600) {
			return false;
		}
		boolean canPut = judgeCanPut(map);
		
		if(canPut) {
			fillRect(x+fontSize*i, y, fontSize, (int)(fontSize*1.2),backgroundColor);
			drawFont(message+"", fontSize, fontColor, x+fontSize*i, y);
			maps.add(map);
			return true;
		}
		return false;
	
		
	}
	/**
	 * 检查 是否可以放
	 * @param map2
	 * @return
	 */
	private  boolean  judgeCanPut(HashMap<String, String> map2) {
		for(HashMap<String,String>m:maps) {
			if(rectInRect(map2, m)) {
				System.out.println(map2);
				System.out.println(m);
				return false;
			}
		}	
		return true;
	}
	/**
	 * 矩形是否香蕉
	 * @param a
	 * @param b
	 * @return
	 */
	private boolean rectInRect(HashMap<String,String>a,HashMap<String, String>b) {
		if(pointInRect(Integer.valueOf(a.get("minx")),Integer.valueOf(a.get("miny")), b))return true;
		if(pointInRect(Integer.valueOf(a.get("minx")),Integer.valueOf(a.get("maxy")), b))return true;		
		if(pointInRect(Integer.valueOf(a.get("maxx")),Integer.valueOf(a.get("miny")), b))return true;
		if(pointInRect(Integer.valueOf(a.get("maxx")),Integer.valueOf(a.get("maxy")), b))return true;
		
		if(pointInRect(Integer.valueOf(b.get("minx")),Integer.valueOf(b.get("miny")), a))return true;
		if(pointInRect(Integer.valueOf(b.get("minx")),Integer.valueOf(b.get("maxy")), a))return true;
		if(pointInRect(Integer.valueOf(b.get("maxx")),Integer.valueOf(b.get("miny")), a))return true;
		if(pointInRect(Integer.valueOf(b.get("maxx")),Integer.valueOf(b.get("maxy")), a))return true;
		
	
		
		return false;
	}

	/**
	 * 点是否在矩形里
	 * @param x
	 * @param y
	 * @param a
	 * @return
	 */
	private boolean pointInRect(int  x,int y,HashMap<String,String>a){
		int minx = Integer.valueOf(a.get("minx"));
		int miny = Integer.valueOf(a.get("miny"));
		int maxx = Integer.valueOf(a.get("maxx"));
		int maxy = Integer.valueOf(a.get("maxy"));
		if(x>=minx&&x<=maxx&&y>=miny&&y<=maxy) {
		//	System.out.println("true");
			return true;
		}else {
		//	System.out.println("false");
			return false;
		}
		
	}
	public void fillRect(int x,int y,int width,int heigth,String color) {
		
		g.setColor(dealWithColorString(color));
		g.fillRect(x, y, width, heigth);
		
	}
	public void drawRect(int x,int y,int width,int height,String color) {
		g.setColor(dealWithColorString(color));
		g.fillRect(x, y, width, height);
	}
	public void drawFont(String message,int size,String color,int startX,int startY) {
		startY+= size;
		Font f = new Font("微软雅黑", Font.PLAIN, size);
		g.setFont(f);
		g.setColor(dealWithColorString(color));
		g.drawString(message, startX, startY);
	}
	/**
	 * 把颜色的字符串 #FFFFF 转变成 255,255,255
	 * @param color
	 * @return
	 */
	private Color dealWithColorString(String color) {
		color = color.toUpperCase();
		color =color.substring(1);
		String r = color.substring(0, 2);
		String g = color.substring(2, 4);
		String b = color.substring(4, 6);
	//	System.out.println(r+" "+g+" "+b);
		return new Color(dealOX(r),dealOX(g),dealOX(b));		
	}
	private int dealOX(String b) {
		int result = 0;
		for(int i=0;i<b.length();i++) {
			char c = b.charAt(i);
			int value = c<='9'?c-'0':c-'A'+10;
			result = result*16+value;
		}
		//System.out.println(result);
		return result;
	}
	public void output(String imgSrc,String xmlSrc) {
		String type = imgSrc.substring(imgSrc.lastIndexOf(".")+1);
		try {
			ImageIO.write(bi, type, new File(imgSrc));
			generLatePicture(imgSrc, xmlSrc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void generLatePicture(String imgSrc ,String xmlsrc) throws IOException {
		System.out.println(maps);
		String tt = "\t";
		String rn = "\r\n";
		String content = "<annotation>" +rn;
		content += tt+"<folder>v1s1</folder>"+rn;
		content += tt+"<filename>${filename}</filename>"+rn;
		content += tt+"<path>${imgSrc}</path>"+rn;
		content += tt+"<source>"+rn;
		content += tt+tt+"<database>Unknown</database>"+rn;
		content += tt+"</source>"+rn;
		content += tt+"<size>"+rn;
		content += tt+tt+"<width>${width}</width>"+rn;
		content += tt+tt+"<height>${height}</height>"+rn;
		content += tt+tt+"<depth>3</depth>"+rn;
		//content += tt+tt+"<folder>v1s1</folder>" +rn;
		content += tt+"</size>";
		content += tt+"<segmented>0</segmented>" +rn;
		content = content.replace("${imgSrc}", imgSrc).replace("${filename}", imgSrc.substring(imgSrc.lastIndexOf("/")+1));
		for(int i=0;i<maps.size();i++) {
			content += tt+"<object>" +rn;
			content += tt+tt+"<name>${name}</name>" +rn;
			content += tt+tt+"<pose>Unspecified</pose>" +rn;
			content += tt+tt+"<truncated>0</truncated>" +rn;
			content += tt+tt+"<difficult>0</difficult>" +rn;
			content += tt+tt+"<bndbox>" +rn;
			content += tt+tt+tt+"<xmin>${minx}</xmin>" +rn;
			content += tt+tt+tt+"<ymin>${miny}</ymin>" +rn;
			content += tt+tt+tt+"<xmax>${maxx}</xmax>" +rn;
			content += tt+tt+tt+"<ymax>${maxy}</ymax>" +rn;
			content += tt+tt+"</bndbox>" +rn;
			content += tt+"</object>" +rn;
			content = content.replace("${name}", maps.get(i).get("name")+"")
			.replace("${minx}", maps.get(i).get("minx")+"")
			.replace("${miny}", maps.get(i).get("miny")+"")
			.replace("${maxx}", maps.get(i).get("maxx")+"")
			.replace("${maxy}", maps.get(i).get("maxy")+"");
		}
		content += "</annotation>"+rn;
		content = content.replace("${width}", "800").replace("${height}", "600");
	
		FileWriter fw =new FileWriter(new File(xmlsrc));
		fw.write(content);
		fw.close();

		
	}
}
