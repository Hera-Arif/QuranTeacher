/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class GetAllImageLinks {
	
	String defaultResource = System.getProperty("user.dir") +"/WbWInfo.txt";
	public static void main(String[] args)
	{
		new GetAllImageLinks();
	}
	
	public GetAllImageLinks()
	{
		FileOutputStream foStream=null;
		File file=new File(defaultResource);
		BufferedReader reader=null;
		try
		{
			reader=new BufferedReader(new FileReader(file));
			System.out.println("Info file accessed successfully");
			
			foStream=new FileOutputStream(System.getProperty("user.dir")+"/WbWImageLinks.txt");
			System.out.println("links file created successfully");
			
			String text;
			while((text=reader.readLine())!=null)
			{
				if(text.startsWith("imageLink"))
				{
					String link=text.substring(text.indexOf('=')+1)+"\n";
					foStream.write(link.getBytes());
				}
			}
			
			
		}catch(IOException ie)
		{
			ie.printStackTrace();
		}
		finally
		{
			try
			{
				if(reader!=null)
					reader.close();
				if(foStream!=null)
				{
					foStream.close();
					System.out.println("Writing file closed");
				}
			}
			catch(IOException ie)
			{
				ie.printStackTrace();
			}
		}
	}
	
}
