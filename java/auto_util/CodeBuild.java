package cn.edu.hziee.util;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import cn.edu.hziee.DB.*;

public class CodeBuild {

	public static void main(String[] args) throws Exception {
	//	CodeBuild cb = new CodeBuild("cn.edu.hziee.model", "cn.edu.hziee.service", "cn.edu.hziee.mapper", "cn.edu.hziee.mapper","C:\\Users\\lenovo\\Desktop\\code");
		String model = "cn.edu.hziee.model";
		String service = "cn.edu.hziee.service";
		String mapper4j =  "cn.edu.hziee.mapper";
		String mapper4x =  "cn.edu.hziee.mapper";
		String contoller = "cn.edu.hziee.controller";
		String absoluteModelUrl = "F:\\MyEclipse2016\\localSnack\\src\\main\\java\\cn\\edu\\hziee\\model\\";
		String absoluteServiceUrl ="F:\\MyEclipse2016\\localSnack\\src\\main\\java\\cn\\edu\\hziee\\service\\";
		String absoluteMapper4jUrl = "F:\\MyEclipse2016\\localSnack\\src\\main\\java\\cn\\edu\\hziee\\mapper\\";
		String absoluteMapper4xUrl = "F:\\MyEclipse2016\\localSnack\\src\\main\\java\\cn\\edu\\hziee\\mapper\\";
		String absoluteControllerUrl = "F:\\MyEclipse2016\\localSnack\\src\\main\\java\\cn\\edu\\hziee\\controller\\";
		CodeBuild cb = new CodeBuild(model, service, mapper4j, mapper4x,contoller ,absoluteModelUrl, absoluteServiceUrl, absoluteControllerUrl, absoluteMapper4jUrl, absoluteMapper4xUrl);
		
		/*String[] tableNames = new String[] {
			
				"test_question"
		};
		for(String tableName:tableNames) {
			cb.xmlTemplate(tableName);
			cb.MapTemplate(tableName);
			cb.modelTemplate(tableName);
		}*/
		String tableName = "review_info";
		cb.build(tableName);
		
		
	}
	private String model;
	private String service;
	private String mapper4j;
	private String mapper4x;
	private String controller;
	private String absoluteModelUrl;
	private String absoluteServiceUrl;
	private String absoluteControllerUrl;
	private String absoluteMapper4jUrl;
	private String absoluteMapper4xUrl;
	private DBUtil db;
	private String father;
	public CodeBuild(String model, String service, String mapper4j, String mapper4x,String position) {
		super();
		this.model = model;
		this.service = service;
		this.mapper4j = mapper4j;
		this.mapper4x = mapper4x;
		father = position;
		db = new DBUtil();
	}
	
	

	public CodeBuild(String model, String service, String mapper4j, String mapper4x, String controller,
			String absoluteModelUrl, String absoluteServiceUrl, String absoluteControllerUrl,
			String absoluteMapper4jUrl, String absoluteMapper4xUrl) {
		super();
		this.model = model;
		this.service = service;
		this.mapper4j = mapper4j;
		this.mapper4x = mapper4x;
		this.controller = controller;
		this.absoluteModelUrl = absoluteModelUrl;
		this.absoluteServiceUrl = absoluteServiceUrl;
		this.absoluteControllerUrl = absoluteControllerUrl;
		this.absoluteMapper4jUrl = absoluteMapper4jUrl;
		this.absoluteMapper4xUrl = absoluteMapper4xUrl;
		db = new DBUtil();

	}



	public void build(String tableName) throws Exception {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		xmlTemplate(tableName);
		MapTemplate(tableName);
		modelTemplate(tableName);
		controllerTemplate(tableName);
		serviceTemplate(tableName);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	//Service 层模板
	public void serviceTemplate(String tableName)throws Exception {
		String className = db.tableField2ObjProperty(tableName);
		className = (className.charAt(0)+"").toUpperCase()+className.substring(1);
		String content = "package "+service+";\r\n" + 
				"import java.util.*;\r\n" + 
				"\r\n" + 
				"import org.springframework.beans.factory.annotation.Autowired;\r\n" + 
				"import org.springframework.stereotype.Service;\r\n" + 
				"import org.springframework.transaction.annotation.Transactional;\r\n" + 
				"import cn.edu.hziee.mapper."+className+"Mapper;\r\n" + 
				"import cn.edu.hziee.model."+className+";\r\n" + 
				"import cn.edu.hziee.model.UserInfo;\r\n" + 
				"@Service\r\n" + 
				"@Transactional\r\n" + 
				"public class "+className+"Service{\r\n" + 
				"\r\n" + 
				"	@Autowired\r\n" + 
				"	private "+className+"Mapper mapper;\r\n" + 
				"\r\n" + 
			
				"	public "+className+" findById(Integer id) {\r\n" + 
				"\r\n" + 
				"		return mapper.selectByPrimaryKey(id);\r\n" + 
				"\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	public Integer deleteById(Integer id) {\r\n" + 
				"\r\n" + 
				"		return mapper.deleteByPrimaryKey(id);\r\n" + 
				"\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	public Integer update("+className+" entity) {\r\n" + 
				"\r\n" + 
				"		return mapper.updateByPrimaryKey(entity);\r\n" + 
				"\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	public int insert("+className+" entity) {\r\n" + 
				"\r\n" + 
				"		return mapper.insert(entity);\r\n" + 
				"\r\n" + 
				"	}\r\n" + 
				"}";
	//	System.out.println(content);
		writeContentToFile(absoluteServiceUrl+className+"Service.java", content);
	}
	
	
	//Contoller 层模板
	public void controllerTemplate(String tableName) throws Exception {
		

		String className = db.tableField2ObjProperty(tableName);
		className = (className.charAt(0)+"").toUpperCase()+className.substring(1);
		String content = "package cn.edu.hziee.controller;\r\n" + 
				"\r\n" + 
				"import java.util.List;"+
				"import javax.servlet.http.HttpServletRequest;\r\n" + 
				"import javax.servlet.http.HttpServletResponse;\r\n" + 
				"\r\n" + 
				"import org.springframework.beans.factory.annotation.Autowired;\r\n" + 
				"import org.springframework.stereotype.Controller;\r\n" + 
				"import org.springframework.web.bind.annotation.RequestMapping;\r\n" + 
				"\r\n" + 
				"import cn.edu.hziee.service."+className+"Service;\r\n" + 
				"import cn.edu.hziee.service.UserInfoService;\r\n" + 
				"\r\n" + 
				"@Controller\r\n" + 
				"@RequestMapping(\""+tableName+"\")\r\n" + 
				"public class "+className+"Controller extends BaseController {\r\n" + 
				"\r\n" + 
				"	@Autowired\r\n" + 
				"	"+className+"Service "+className+"Service;\r\n" + 
				"	@Autowired\r\n" + 
				"	UserInfoService UserInfoService;\r\n" + 
				"	\r\n" + 
				"	\r\n" + 
				
				"}";
	//	System.out.println(content);
		writeContentToFile(absoluteControllerUrl+className+"Controller.java", content);
	}
	
	//实体类模板
	public void modelTemplate(String tableName) throws Exception {
	
		List<FieldInfo> ls = db.getTableInfo(tableName);
		String content = "";
		content += "package "+model+";\r\n\r\n";
		
		String className = db.tableField2ObjProperty(tableName);
		
		className = (className.charAt(0)+"").toUpperCase()+className.substring(1);
		
		content += "public class "+className+"{\r\n";
		//属性
		for(FieldInfo f :ls) {
			
		
			
			if(!"".equals(f.getCommit())){
				content += "\r\n\t//"+f.getCommit();
			}
		
			content += "\r\n\tprivate "+db.tableType2objType(f.getType())+" "+db.tableField2ObjProperty(f.getFieldName())+" ;\r\n";

		}
		//set
		for(FieldInfo f:ls) {
			
			String name = db.tableField2ObjProperty(f.getFieldName());
			String Name = (name.charAt(0)+"").toUpperCase()+name.substring(1);
			String type = db.tableType2objType(f.getType());
			
			content += "\tpublic void set"+Name+"("+type+" "+name+"){"+"\r\n\r\n";
			content += "\t\tthis."+name+" = "+name+";\r\n\r\n";
			content += "\t}\r\n";
		}
		
	
		
		
		//get
		for(FieldInfo f:ls) {
			
			String name = db.tableField2ObjProperty(f.getFieldName());
			String Name = (name.charAt(0)+"").toUpperCase()+name.substring(1);
			String type = db.tableType2objType(f.getType());
			
			content += "\tpublic "+type+" get"+Name+"(){"+"\r\n\r\n";
			content += "\t\treturn "+name+";\r\n\r\n";
			content += "\t}\r\n";
		}
		
		content += "\r\n}\r\n";
		
		writeContentToFile(absoluteModelUrl+className+".java", content);
	}
	//XML模板
	public void xmlTemplate(String tableName) throws Exception {
		List<FieldInfo> ls = db.getTableInfo(tableName);
		String className = db.tableField2ObjProperty(tableName);
		FieldInfo key = getKey(tableName);
		
		className = (className.charAt(0)+"").toUpperCase()+className.substring(1);
		
	//	System.out.println(className+"!!!!!!!!!!!!!!!!!!");
		String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n" + 
				"<mapper namespace=\""+mapper4x+"."+className+"Mapper\">\r\n";
		
		//resultMap
		content += "<resultMap id=\"BaseResultMap\" type=\""+model+"."+className+"\">\r\n";
		for(int i=0;i<ls.size();i++) {
			
			FieldInfo f = ls.get(i);
			
			
			String property  =db.tableField2ObjProperty(f.getFieldName());
			
			String type = db.tableType2objType(f.getType());
			
			String realType = type2jdbcType(type);
			
			content += "\t<result column=\""+f.getFieldName()+"\" property=\""+property+"\" jdbcType=\""+realType+"\" />\r\n\r\n";
			
		}
		content += "</resultMap>\r\n\r\n";
		
		//insert
		content += "<insert id=\"insert\" parameterType=\""+model+"."+className+"\">\r\n\r\n";		
		content += "\t<selectKey keyProperty=\"id\" order=\"AFTER\" resultType=\"java.lang.Integer\">\r\n";
		content +=  
				"\t\tSELECT LAST_INSERT_ID()\r\n" + 
				"\t</selectKey>\r\n";
	
		content += "\tinsert into "+tableName+"";
		content += "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\r\n";
		for(FieldInfo f:ls) {
			String property  =db.tableField2ObjProperty(f.getFieldName());
			content += "\t<if test=\""+property+" != null\" >\r\n" + 
					"\t\t"+f.getFieldName()+",\r\n" + 
					"\t</if>\r\n";
			
		}
		content += "\t</trim>\r\n\r\n";
		content += "\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\r\n";
		for(FieldInfo f:ls) {
			String property  =db.tableField2ObjProperty(f.getFieldName());
			content += "			<if test=\""+property+" != null\" >\r\n" + 
					"				#{"+property+"},\r\n" + 
					"			</if>\r\n";
		}
		
		content += "\t</trim>\r\n";
		content += "</insert>\r\n";
		
		
		//select
		content += "<select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.Integer\" >\r\n\t";
		int cnt = 0;
		for(FieldInfo f:ls) {
			String property  =db.tableField2ObjProperty(f.getFieldName());
			if(cnt!=0)content+=",";
			content += f.getFieldName();
			
			cnt++;
			if(cnt%4==0)content+="\r\n";
		}
		content += "</select>\r\n";
		//delete
		content += "<delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang.Integer\" >\r\n" + 
				"\r\n" + 
				"		delete from "+tableName+" where id = #{id}\r\n" + 
				"\r\n" + 
				"</delete>\r\n";
		
		content += "<update id=\"updateByPrimaryKey\" parameterType=\""+model+"."+className+"\" >\r\n";
		content += "\tupdate "+tableName+" set\r\n";
		content += "\t<trim  suffixOverrides=\",\" >\r\n";
		for(FieldInfo f:ls) {
			String property  =db.tableField2ObjProperty(f.getFieldName());
			content += "		<if test=\""+property+" != null\" >\r\n" + 
					"			"+f.getFieldName()+" = #{"+db.tableField2ObjProperty(f.getFieldName())+",jdbcType="+type2jdbcType2(f.getType())+"},\r\n" + 
					"		</if>\r\n";
		}
		
		
		content += "\t</trim>\r\n";
		content += " where "+key.getFieldName()+"=#{"+db.tableField2ObjProperty(key.getFieldName())+",jdbcType="+type2jdbcType2(key.getType())+"}" ;
		content += "</update>\r\n\r";
		content += "</mapper>";
		writeContentToFile(absoluteMapper4xUrl+className+"Mapper.xml", content);
	}
	//Map.java模板
	public void MapTemplate(String tableName) throws Exception {
		String className = db.tableField2ObjProperty(tableName);
		className = (className.charAt(0)+"").toUpperCase()+className.substring(1);
		String content = "package cn.edu.hziee.mapper;\r\n" + 
				"import java.util.*;\r\n" + 
				"\r\n" + 
				"import org.apache.ibatis.annotations.Insert;\r\n" + 
				"import org.apache.ibatis.annotations.Mapper;\r\n" + 
				"import org.apache.ibatis.annotations.Param;\r\n" + 
				"import org.apache.ibatis.annotations.Select;\r\n";
		content += "import cn.edu.hziee.model."+className+";\r\n";
		content += "@Mapper\r\n" + 
				"public interface "+className+"Mapper{\r\n" + 
				"\r\n" + 
				"	Integer insert("+className+" model);\r\n" + 
				"\r\n" + 
				"	Integer deleteByPrimaryKey(Integer id);\r\n" + 
				"\r\n" + 
				"	Integer updateByPrimaryKey("+className+" model);\r\n" + 
				"\r\n" + 
				"	"+className+" selectByPrimaryKey(Integer id);\r\n" + 
				"}";
		writeContentToFile(absoluteMapper4jUrl+className+"Mapper.java", content);
		
		
	}
	private FieldInfo getKey(String tableName) throws Exception {
		DBUtil db = new DBUtil();
		List<FieldInfo> ls = db.getTableInfo(tableName);
		for(FieldInfo f:ls) {
			if("yes".equalsIgnoreCase(f.getIsKey())) {
				return f;
			}
			
		}
		return null;
	}
	
	
	public static  String  type2jdbcType(String a) {
		if(a.equals("Integer")) {
			return "INTEGER";
		}else if(a.equalsIgnoreCase("String")) {
			return "VARCHAR";
		}else if(a.equalsIgnoreCase("Double")) {
			return "DOUBLE";
		}
		System.out.println(a);
		return "error";
	}
	public static String type2jdbcType2(String a) {
		if("int".equals(a)) {
			return "INTEGER";
		}else if("varchar".equals(a)) {
			return "VARCHAR";
		}else if("double".equals(a)) {
			return "DOUBLE";
		}
		return "error";
	}
	public void writeContentToFile(String fileName,String content) throws Exception {
		
		File f = new File(fileName);
		if(f.exists()) {
			System.out.println(f.getName()+"已存在，不进行覆盖,位置在"+fileName);
			return;
		}
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		fw.append(content);
		fw.close();
		System.out.println(f.getName()+"创建成功!!!位置在"+fileName);
		
		
	}
	
	
	
}
