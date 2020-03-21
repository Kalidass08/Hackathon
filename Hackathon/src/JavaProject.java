  
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mysql.cj.xdevapi.JsonArray;

import java.util.StringTokenizer;

public class JavaProject {
	public static void main(String[] args) throws IOException, SQLException {
		DecimalFormat df = new DecimalFormat("#.##");
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
			count++;
			add=add+(int)v;
			if(max<(int)v);
			{
				max=(int)v;
			}
		}
		avg=add/count;
		json.put("Avg:",avg);
		json.put("Max",max);
		main.put(json,obj);
		FileWriter file1=new FileWriter("op.json");
		file1.write(main.toString());
		file1.close();
		System.out.println("Data Added");
	}
}
