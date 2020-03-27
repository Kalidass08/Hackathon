import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.*;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mysql.cj.xdevapi.JsonArray;

public class Memory {
	public static void main(String[] args) throws IOException
	{
		File file = new File("Memory.txt"); 
		BufferedReader reader= new BufferedReader(new FileReader(file));
		String t;
		String S="Sample";
		int iter=1;
		String val="";
		float kb,mb=0,Total=0,c=0,max=0,avg=0;
		JSONObject obj=new JSONObject();
		JSONObject obj1=new JSONObject();	
		 int lineNum = 0;
		 String line = null;
		 while ( (line = reader.readLine() ) != null )
		 {
			 line=line.trim();
			 lineNum++;
		    if ( lineNum % 2 == 0 )
			  continue;
		   String[] array = line.split("   ");
		   val=array[1].trim();
		   kb=Float.parseFloat(val);
		   mb=(kb/1024);           //Calculating Mb Values
		   if(max<mb)
		   {
			   max=mb;	//Calculating Max Values
		   }
		  // System.out.println(mb_val);
		   t= iter+"s";
		   iter++;
		   obj1.put(t,mb);	//Adding Values to the JSON Object
		   Total=Total+mb;
		   c++;
		 }
		 avg=(Total/c);		//Calculating Average

		 try {
			databaseConnectivity(S,avg,max);	//Make Function Call to Databse Creation and inserting Values to the DB.
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		 obj.put("Values",obj1);
		 obj.put("Usecasename","Sample");
		 obj.put("AverageMemory(MB)", avg);	//Adding Avg Values to Json
		 obj.put("MaximumMemory(MB)", max);	//Adding Max Values to Json
		
		 FileWriter file1=new FileWriter("op.json"); 
			file1.write(obj.toString());
			file1.close();
			System.out.println("Data Added");
			
			htmlFormat(S,avg,max); //Function Call to Create HTML Document
	}
	
	public static void databaseConnectivity(String S,float avg,float max)throws SQLException{
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Memory","root","Kalidass@0803");
		System.out.println("Database connected\n");
		Statement stm=conn.createStatement();
		stm.executeUpdate("create table data(Usecasename varchar(64),Average DECIMAL(10,2),Max DECIMAL(10,2));");
		System.out.println("Table Created");
		 stm.executeUpdate("insert into data(Usecasename,Average,Max)values('"+S+"',"+avg+","+max+")");
		 System.out.println("Inserted");
	}
	
	public static void htmlFormat(String S,float avg,float max) throws IOException {
		String Sample = String.valueOf(S);
		String Avg = String.valueOf(avg);
		String Max = String.valueOf(max);
		PrintWriter out = new PrintWriter(new FileWriter("C:/Users/hp/Desktop/OutputHtml.html"));
		out.println("<center>");
		out.println("<table border=1>");
		out.println("<caption>CPU VALUES</caption>");
		out.println("<tr><th>Usecasename</th><th>MAXIMUM CPU TIME</th><th>AVERAGE CPU TIME</th></tr>");
		out.println("<tr><td>"+S+"</td><td>"+ max+"</td><td>"+avg+"</td></tr>");     
		out.println("</table>");
		out.println("<center>");
		out.close();
        System.out.println("Html Page Created");
	}
}
