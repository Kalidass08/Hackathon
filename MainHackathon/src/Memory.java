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
	public static void main(String[] args) throws IOException
	{
		String val="";
		float kb,mb_val=0,Total=0,c=0,max=0,avg=0;
		File file = new File("Memory.txt"); 
		BufferedReader reader= new BufferedReader(new FileReader(file));	
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
		   mb_val=(kb/1024);
		   if(max<mb_val)
		   {
			   max=mb_val;
		   }
		   
		  // System.out.println(mb_val);
		  Total=Total+mb_val;
		  c++;
		 }
		 avg=(Total/c);
		 
		 System.out.println("Maximum Value:"+max);
		 System.out.println("Total:"+Total);
		 System.out.println("no of entry:"+c);
		 System.out.println("avg:"+avg);
		 /*HashMap<String, Float> mem = new HashMap<String, Float>();

		    mem.put("AverageMemory(MB)", avg);
		    mem.put("MaximumMemory(MB", max);
		    */
	
		 JSONObject obj=new JSONObject();
		 obj.put("AverageMemory(MB)", avg);
		 obj.put("MaximumMemory(MB)", max);
		
		 FileWriter file1=new FileWriter("op.json");
			file1.write(obj.toJSONString());
			file1.close();
			System.out.println("Data Added");
		 
	}
}
