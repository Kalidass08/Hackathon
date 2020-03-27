import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.*;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mysql.cj.xdevapi.JsonArray;

public class Memory {
	public static void main(String[] args) throws IOException, SQLException
	{
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Memory","root","Kalidass@0803");
		System.out.println("Database connected\n");
		Statement stm=conn.createStatement();
		stm.executeUpdate("create table data(Usecasename varchar(64),Average DECIMAL(10,2),Max DECIMAL(10,2));");
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
		   //System.out.println(kb);
		   mb=(kb/1024); //Calculate Mb
		   //System.out.println(mb_val);
		   if(max<mb)
		   {
			   max=mb; //Calculate Avg
		   }
		   
		  // System.out.println(mb_val);
		   t= iter+"s";
		   iter++;
		   obj1.put(t,mb); //Pushing Values into json object
		  Total=Total+mb;
		  c++;
		 }
		 avg=(Total/c); //Calculate avg
		 
		 stm.executeUpdate("insert into data(Usecasename,Average,Max)values('"+S+"',"+avg+","+max+")"); //Data Inserted
		 System.out.println("Inserted");

		 obj.put("Values",obj1);
		 obj.put("Usecasename","Sample");
		 obj.put("AverageMemory(MB)", avg);
		 obj.put("MaximumMemory(MB)", max);
		
		 FileWriter file1=new FileWriter("op.json");
			file1.write(obj.toString());
			file1.close();
			System.out.println("Data Added");
		 htmlFormat(S,avg,max); //Function Call to Create HTML Document
	}
	public static void htmlFormat(String S,float avg,float max) throws IOException {
		String Sample = String.valueOf(S);
		String Avg = String.valueOf(avg);
		String Max = String.valueOf(max);
		PrintWriter out = new PrintWriter(new FileWriter("C:/Users/hp/Desktop/OutputHtml.html"));
		out.println("<table border=1>");
        out.println("<caption>CPU VALUES</caption>");
        out.println("<tr><th>Usecasename</th><th>MAXIMUM CPU TIME</th><th>AVERAGE CPU TIME</th></tr>");
        out.println("<tr><td>"+S+"</td><td>"+ max+"</td><td>"+avg+"</td></tr>");     
        out.println("</table>");
        out.close();
        System.out.println("Html Page Created");
	}
}
