import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Scanner;

public class GetElements {
	
	public GetElements()
	{
		
	}
	
	public static void main(String[] args)
	{
		String url="http://corpus.quran.com/wordbyword.jsp";
		byte[] theByteArray=getContent(url).getBytes();
		
		String s=new String(theByteArray,Charset.forName("UTF-8"));
		//System.out.println(s);
		
		getInfos(s);
		
		System.out.println("ended");
		
		/*String text = "こんにちは";
		byte[] array = text.getBytes("UTF-8");
		String s = new String(array, Charset.forName("UTF-8"));
		System.out.println(s); // Prints as expected*/
		
	}
	
	public static String getContent(String url)
	{
		String content = null;
		URLConnection connection = null;
		try {
		  connection =  new URL(url).openConnection();
		  Scanner scanner = new Scanner(connection.getInputStream());
		  scanner.useDelimiter("\\Z");
		  content = scanner.next();
		}catch ( Exception ex ) {
		    ex.printStackTrace();
		}
		return content;
	}
	
/*	public static String getElements(String url)
	{
		String pageUrl=url;
		String content;
		for(int i=0;i<4;i++)
		{
			pageUrl="8";
		}
		return null;
	}
	*/
	public static void getInfos(String content)
	{
		int k=0,m=0;
		String startStr="<tr><td><span class=\"location\"";
		String endStr="</td></tr>";
		
		while(k<content.length())
		{
			k=content.indexOf(startStr,k);
			if(k!=-1)
			{
				m=k;
				k=content.indexOf(endStr,k);
				//System.out.println(content.substring(m,k));
				getElements1(content.substring(m,k));
				System.out.println("-----------------------------------------");
				k+=endStr.length();				
			}
			else
			{
				getNextPageRelativeUrl(content, m);
				break;
			}
		}
	}
	
	public static void getNextPageRelativeUrl(String content,int k)
	{
		int p=content.indexOf("<div class=\"navigationPane\"",k);
		if(p!=-1)
		{
			int q=content.indexOf("</div",p+1);
			

			
			System.out.println();
			
			int r=getMaxIndexOf(content,"<a", p+1, q-1);
			if(r!=-1)
				System.out.println(content.substring(r,q-1).split("\"")[1]);
		}
	}
	
	public static int getMaxIndexOf(String content,String str,int startIndex,int endIndex)
	{
		int k=startIndex,savePrevious=-1;
		while(k<endIndex)
		{
			k=content.indexOf(str,k);
			if(k!=-1 && k<endIndex)
			{
				savePrevious=k;
				k+=str.length();
			}
			else
				break;
		}
		return savePrevious;
	}
	
	public static void getElements1(String s)
	{
	/*<tr>
		<td>
			<span class="location">(1:1:1)</span><br/>
			<a href="/qurandictionary.jsp?q=smw#(1:1:1)">bis'mi</a><br/>
			In (the) name
		</td>
		<td class="ic"><a href="/wordmorphology.jsp?location=(1:1:1)"><img src="/wordimage?id=1" /></a>
		</td>
		<td class="col3">
			<b class="segRust">P</b> &ndash; prefixed preposition <i class="ab">bi</i><br/>
			<b class="segSky">N</b> &ndash; genitive masculine noun<div class="arabicGrammar">جار ومجرور</div>
		</td>
	</tr>*/
		
		int beginIndex,endIndex=0;
		
		//<span class="location">(1:1:1)</span>
		beginIndex=s.indexOf(">",s.indexOf("<span",endIndex));
		endIndex=s.indexOf("</span",beginIndex);
		System.out.println(s.substring(beginIndex, endIndex));

		//<a href="/qurandictionary.jsp?q=smw#(1:1:1)">bis'mi</a>
		beginIndex=s.indexOf(">",s.indexOf("<a href",endIndex));
		endIndex=s.indexOf("</a",beginIndex);
		System.out.println(s.substring(beginIndex, endIndex));
		
		/*
		 * <br/>
			In (the) name
		</td>
		 */
		beginIndex=s.indexOf(">",s.indexOf("<br/", endIndex));
		endIndex=s.indexOf("</td",beginIndex);
		System.out.println(s.substring(beginIndex, endIndex));
		
		//<img src="/wordimage?id=1" /></a>
		beginIndex=s.indexOf("src",s.indexOf("<img", endIndex));
		endIndex=s.indexOf("/>",beginIndex);
		System.out.println(s.substring(beginIndex, endIndex));
		
		//<b class="segRust">
		beginIndex=s.indexOf("class",s.indexOf("<b", endIndex));
		endIndex=s.indexOf(">",beginIndex);
		System.out.println(s.substring(beginIndex, endIndex));
		
		//>P</b>
		beginIndex=endIndex;//">"
		endIndex=s.indexOf("</b",beginIndex);
		System.out.println(s.substring(beginIndex, endIndex));
		
	}
	
	public static void getElements2(String s)
	{
		int beginIndex,endIndex;
		for(int i=0;i<3;i++)
		{
			//a cell
			beginIndex=s.indexOf("<td>");
			endIndex=s.indexOf("</td>",beginIndex);
			
			String t=s.substring(beginIndex+4,endIndex);
			
			for(int line=0;;line++)
			{
				
				if(s.indexOf("<br/>")!=-1)
					break;
			}
		}
	}

}
