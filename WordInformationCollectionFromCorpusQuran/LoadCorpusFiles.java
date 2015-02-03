
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;



/**
 * Example program to list links from a URL.
 */
public class LoadCorpusFiles{
	
	
	public static String DefaultResource=System.getProperty("user.dir")+"/WbWCorpus/";
	public static int total;
    
	public static void main(String[] args){
    	
    	new LoadCorpusFiles();
        
    }
    
    //Thread t;
    
    public LoadCorpusFiles()
    {
    	/*t=new Thread(this,"foo");
    	t.start();*/
    	run();
    }
    
    public static void getLinks(String url)
    {
        //System.out.printf("Fetching %s...\n", url);
        //print("Fetching %s...", url);

        Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(60000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Elements links = doc.select("a[href*=?chapter=]");
        
        if(links.size()!=0)
        {
        	String foo=links.get(links.size()-1).attr("abs:href");
        	 System.out.println("Next link :"+foo);
        	 
        	 String contents=doc.toString();
        	 //System.out.println(contents);
        	 try {
				store(contents,url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 getLinks(foo);
        	 
        	 
        }
        else
        {
        	System.out.println("\n\nLink not found----------------------------------\n\n");
        	return;
        }
        
       
    }
    
    
    public static void store(String content,String url) throws IOException
    {
    	try {
    		String name=getName(url);
    		
			FileOutputStream out=new FileOutputStream(DefaultResource+name+".txt");
			System.out.println("file created : "+name+".txt");
			byte[] buf=content.getBytes(Charset.forName("UTF-8"));
			
			out.write(buf);
			out.close();
			System.out.println("file closed : "+name+".txt");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File Name : "+getName(url));
			e.printStackTrace();
			System.exit(1);
		}
    }

	//@Override
	public void run() {
		// TODO Auto-generated method stub
		getLinks("http://corpus.quran.com/wordbyword.jsp?");
		
		try
		{
			Thread.sleep(1000);
		}catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
	}
	
	public static String getName(String url)
	{
		String[] parts=url.split("\\?");
		
		if(parts.length>1)
		{
			parts=parts[1].split("&");
			
			String part1=parts[0].split("=")[1];
			String part2=parts[1].split("=")[1];
			
			int m=Integer.parseInt(part1);
			int n=Integer.parseInt(part2);
			
			if(m < 10)
		        part1 = "00"+part1;
		    else if(m < 100)
		        part1 = "0"+part1;
		    
			if(n < 10)
		        part2 = "00"+part2;
		    else if(n < 100)
		        part2 = "0"+part2;
			
			return part1+part2;
		}
		
		total++;
		return "00100"+total;//only for first file
	}
    
    
}
