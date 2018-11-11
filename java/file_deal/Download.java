
import java.io.File;
import java.util.Scanner;

public class Download {

	public static void main(String[] args) {
		//路径
		//文件操作的句柄
			
		Scanner sc = new Scanner(System.in);
		String str = null;
		String ans = "";

		String num = input(sc,"please input thread number: ");
		String realUrl = input(sc, "please input url");
		System.out.println("please input the http header");
		while(true) {
			str = sc.nextLine();
			if(str.equals("#") )break;
			ans += " "+str;
		}
		System.out.println("axel -o /home/chenqiqi/download -H \""+ans+"\" \""+realUrl+"\" -n "+num);
	
		
	}
	public static String input(Scanner sc,String str) {
		System.out.println(str);
		return sc.nextLine();
	}
}
