  
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.math.BigDecimal;


import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mysql.cj.xdevapi.JsonArray;

import java.util.StringTokenizer;

public class JavaProject {
	BigDecimal formatchanger(String par) {
		BigDecimal value=new BigDecimal(par);
		value=value.setScale(2,BigDecimal.ROUND_HALF_UP);
		return value;
	}
	public static void main(String[] args) throws IOException, SQLException {
		DecimalFormat df = new DecimalFormat("#.##");
		JavaProject parse = new JavaProject();
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaProject","root","Kalidass@0803");
		System.out.println("Database connected\n");
		Statement stm=conn.createStatement();
		stm.executeUpdate("create table data(transaction varchar(64),time DECIMAL(10,2));");
		File file = new File("input.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String e;
		int t=0;
		int add=0;
		int max=0;
		int count=0;
		int avg;
		JSONArray array=new JSONArray();
		JSONObject main=new JSONObject();
		JSONObject json = new JSONObject();
		JSONObject obj=new JSONObject();
		while((e=br.readLine())!=null)
		{	
			t++;
			StringTokenizer st=new StringTokenizer(e," ");
			int i=1;
			while(i<9)
			{
				i++;
				st.nextToken();
			}
			stm.executeUpdate("insert into data(transaction,time)values('"+t+"s',"+st.nextToken()+");");	
			System.out.println("Inserted");
		}
		ResultSet rs=stm.executeQuery("select * from data");
		while(rs.next())
		{
			String k=rs.getString("transaction");
			float v=Float.parseFloat(df.format(rs.getFloat("time")));
			obj.put(k,v);
		}
		json.put("value",obj);
		main.put("Total Transaction",json);
		rs = stm.executeQuery("select max(time) from data");
		if(rs.next())
		{
			json.put("MaxValue",parse.formatchanger(rs.getString(1)));
		}
		rs = stm.executeQuery("select avg(time) from data"); 
		if(rs.next())
		{
			json.put("AvgValue",parse.formatchanger(rs.getString(1)));
		}
		
		array.add(0,main);
		FileWriter file1=new FileWriter("op.json");
		file1.write(main.toString());
		file1.close();
		System.out.println("Data Added");
	}
}
