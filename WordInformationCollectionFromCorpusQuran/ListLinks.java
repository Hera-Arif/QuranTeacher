
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.SysexMessage;

/**
 * Example program to list links from a URL.
 */
public class ListLinks {
	
	static List<WordInformation>WordInformations=new ArrayList<>();
	
    public static void main(String[] args){
        //Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = "http://corpus.quran.com/wordbyword.jsp";
        System.out.printf("Fetching %s...\n", url);

        Document doc=null;
		try {
			doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
			//doc = Jsoup.parse(new File(System.getProperty("user.dir")+"/WbWCorpus/000000.txt"),"UTF-8");
			
			//doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Fetching completed. Collecting Data...");
		Elements columns=doc.select("td");
		for (Element link : columns) {
	            System.out.printf(" * %s %s\n", link.tagName(),link.text());
	    }
		System.out.println("------------------------------------------");
        /*Elements links = doc.select("a[href*=chapter]");
        Elements media = doc.select("[src*=wordimage?id]");
        Elements imports = doc.select("link[href]");
        Elements spans=doc.select("span[class*=location]");*/
        Elements tableRow=doc.select("tr");

        /*//getting images <img>
        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }

        //getting links <link>
        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        //getting links <a>
        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
        
      //getting spans <a>
        print("\nLinks: (%d)", spans.size());
        for (Element link : spans) {
            print(" * span: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }*/
      
        //getting table cells <tr>
        
        getWordInfo(doc);
       /* print("\nLinks: (%d)", tableRow.size());
        for (Element link : tableRow) 
        {
        	try
        	{
	        	if(link.child(0).child(0).hasClass("location"))
	        	{
	        		WordInformation winf=new WordInformation();
		           //print(" * tr: <%s>  (%s)", link.attr("abs:td"), trim(link.text(), 100));
		           Elements rowCells=link.children();
		           
		           Elements items;
		           String tagName;

		           //cell 1
		           Element cell=rowCells.get(0);
			       print(" cell=1 * <%s>: %s", cell.tagName(),trim(cell.text(), 100));
			       //String formatted=new String(cell.text().getBytes(),Charset.forName("UTF-8"));
			       //winf.cell1=formatted;
			       winf.cell1=cell.text();
			     
			       items=cell.getElementsByAttributeValue("class", "location");
			       if(items.size()!=0)
			       {
			    	   String text=items.get(0).text();
			    	   winf.wordId=text;
			    	   //print(text);
			       }
			       else
			    	   System.err.println("failed to load : worId");
			       //}catch(IndexOutOfBoundsException i){i.printStackTrace();}
			       
			      
			       if(cell.getElementsByAttributeValueContaining("href", "qurandictionary.jsp").size()!=0)
			       {
			    	   items=cell.getElementsByAttributeValueContaining("href", "qurandictionary.jsp");
			    	   String text=items.get(0).text();
			    	   winf.transLiteration=text;
			    	   //print(text);
			       }
			       else if(cell.getElementsByAttributeValueContaining("class", "phonetic").size()!=0)
			       {
			    	   items=cell.getElementsByAttributeValueContaining("class", "phonetic");
			    	   String text=items.get(0).text();
			    	   winf.transLiteration=text;
			       }
			       else
			    	   System.err.println("failed to load : transliteration");
			       //}catch(IndexOutOfBoundsException i){i.printStackTrace();}
			       if(winf.wordId!=null && winf.transLiteration!=null)
			       {
			    	   winf.meaning=winf.cell1.replace(winf.transLiteration,"");
			    	   winf.meaning=winf.meaning.replace(winf.wordId, "");
			       }
			       //cell2
			       cell=rowCells.get(1);
			       print(" cell=2 * <%s>: %s", cell.tagName(),trim(cell.text(), 100));
			       winf.cell2="Image";
			     
			       items=cell.getElementsByAttributeValueContaining("src", "wordimage?id");
			       if(items.size()!=0)
			       {
			    	   String s=items.get(0).attr("abs:src");
			    	   winf.imageLink=s;
			    	   winf.imageId=s.split("=")[1];
			    	   //print(s);
			       }
			       else
			    	   System.err.println("failed to load : imagelink and imageId");
			       //}catch(IndexOutOfBoundsException i){i.printStackTrace();}
		           
			       
		           //cell3
			       cell=rowCells.get(2);
			       print(" cell=3 * <%s>: %s", cell.tagName(),trim(cell.text(), 100));
			       //String formatted2=new String(cell.text().getBytes(),Charset.forName("ISO-8859-1"));
			       //winf.cell3=formatted2;
			       winf.cell3=cell.text();
			       
			       items=cell.getElementsByAttributeValueContaining("class", "seg");
			       
			       if(items.size()!=0)
			       {
			    	   String arabic=null;
			    	   String details=winf.cell3;
			    	   if(cell.getElementsByAttributeValue("class", "arabicGrammar").size()!=0)
			    	   {
			    		   arabic=cell.getElementsByAttributeValue("class", "arabicGrammar")
			    				   .get(0).text();
			    		   details=details.replace(arabic, "");
			    	   }
			    	   
				       for(int i=0;i<items.size();i++)
				       {
				    	   String[] colornames=items.get(i).attr("abs:class").split("/");
				    	   winf.segmentColors[i]=colornames[colornames.length-1];
				    	   winf.partsOfSpeeches[i]=items.get(i).text();
				       }
				       
				       for(int j=items.size()-1;j>=0;j--)
				       {
				    	   String temp=winf.partsOfSpeeches[j];
				    	   int k=details.indexOf(temp);
				    	   
				    	   winf.partsOfspeechDetails[j]=details.substring(k);
				    	   details=details.substring(0,k);
				       }
			       }
			       else
			       {
			    	   System.err.println("Failed to load : segments");
			       }
			       
			       WordInformations.add(winf);
		           System.out.println("----------------------------------------");
	        	}
        	}catch(IndexOutOfBoundsException e)
        	{
        		System.out.println("\n\nDon't care");
        		e.printStackTrace();
        		}
        }
        
       printWordInformations();*/
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + "...";
        else
            return s;
    }
    
    static void printWordInformations()
    {
    	for( WordInformation w: WordInformations)
    	{
    		print(w.toString());
    		print("\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
    	}
    }
    
    static void getDetails(Elements link,String tagName)
    {
    	
    }
    /*if(tagName.equals("span"))
	{
		String wordId=item.text();
		print(" * <%s>: AayahPart=%s", item.tagName(),trim(item.text(), 100));
		winf.wordId=wordId;
	}
	else if(tagName.equals("a"))
	{
		print(" * <%s>: Transliteration=%s", item.tagName(),trim(item.text(), 100));
		winf.transLiteration=item.text();
	}*/
    /*item=childrens.get(i);
	   tagName=item.tagName();
		if(tagName.equals("img"))
		{
			String attr="abs:src";
			String s=item.attr(attr);
			if(s.contains("="))
			{
				
				String[] ids=s.split("=");
				print(" * <%s>: id=%s", item.tagName(),ids[1]);
				
				winf.imageLink=s;
				winf.imageId=ids[1];
			}
		} */

	public static void getWordInfo(Document doc) {
		System.out.println("------------------------------------------");
	
		Elements tableRow = doc.select("tr");
	
		for (Element link : tableRow) {
			try {
				Elements rowCells = link.children();// <td>s
	
				// cell 1
				Element cell = null;
	
				if (rowCells.size() == 0)
					continue;
	
				cell = rowCells.get(0);// <td> no.1
				Elements cellChilds = cell.children();
	
				if (cellChilds.size() == 0)
					continue;
	
				if (cell.child(0).hasClass("location")) {
					
					WordInformation winf = new WordInformation();
					// print(" * tr: <%s>  (%s)", link.attr("abs:td"),
					// trim(link.text(), 100));
	
					Elements items;
					// String tagName;
	
					System.out.printf(" cell=1 * <%s>: %s\n", cell.tagName(),
							cell.text());
					// String formatted=new
					// String(cell.text().getBytes(),Charset.forName("UTF-8"));
					// winf.cell1=formatted;
					winf.cell1 = cell.text();
	
					items = cell.getElementsByAttributeValue("class",
							"location");
					if (items.size() != 0) {
						String text = items.get(0).text();
						winf.wordId = text;
						// print(text);
					} else
						System.err.println("failed to load : worId");
					// }catch(IndexOutOfBoundsException i){i.printStackTrace();}
	
					if (cell.getElementsByAttributeValueContaining("href",
							"qurandictionary.jsp").size() != 0) {
						items = cell.getElementsByAttributeValueContaining(
								"href", "qurandictionary.jsp");
						String text = items.get(0).text();
						winf.transLiteration = text;
						// print(text);
					} else if (cell.getElementsByAttributeValueContaining(
							"class", "phonetic").size() != 0) {
						items = cell.getElementsByAttributeValueContaining(
								"class", "phonetic");
						String text = items.get(0).text();
						winf.transLiteration = text;
					} else
						System.err.println("failed to load : transliteration");
					// }catch(IndexOutOfBoundsException i){i.printStackTrace();}
					if (winf.wordId != null && winf.transLiteration != null) {
						winf.meaning = winf.cell1.replace(winf.transLiteration,
								"");
						winf.meaning = winf.meaning.replace(winf.wordId, "");
					}
					
					
					// cell2
					cell = rowCells.get(1);
					System.out.println(cell.toString());
					System.out.printf(" cell=2 * <%s>: %s\n", cell.tagName(),
							cell.text());
					winf.cell2 = "Image";
	
					items = cell.getElementsByAttributeValueContaining("src",
							"wordimage?id");
					if (items.size() != 0) {
						String s = items.get(0).attr("abs:src");
						winf.imageLink = s;
						
						try
						{
							winf.imageId = s.split("=")[1];
						}
						catch(IndexOutOfBoundsException in)
						{
							System.err.println("cell2 image id loading failed.:"+" "+s);
							return;
						}
						// print(s);
					} else
						System.err
								.println("failed to load : imagelink and imageId");
					// }catch(IndexOutOfBoundsException i){i.printStackTrace();}
	
					
					
					// cell3
					cell = rowCells.get(2);
					System.out.printf(" cell=3 * <%s>: %s\n", cell.tagName(),
							cell.text());
					// String formatted2=new
					// String(cell.text().getBytes(),Charset.forName("ISO-8859-1"));
					// winf.cell3=formatted2;
					winf.cell3 = cell.text();
	
					items = cell.getElementsByAttributeValueContaining("class",
							"seg");
	
					if (items.size() != 0) {
						String arabic = null;
						String details = winf.cell3;
						if (cell.getElementsByAttributeValue("class",
								"arabicGrammar").size() != 0) {
							arabic = cell
									.getElementsByAttributeValue("class",
											"arabicGrammar").get(0).text();
							details = details.replace(arabic, "");
						}
	
						for (int i = 0; i < items.size(); i++) {
							String[] colornames = items.get(i)
									.attr("abs:class").split("/");
							winf.segmentColors[i] = colornames[colornames.length - 1];
							winf.partsOfSpeeches[i] = items.get(i).text();
						}
	
						for (int j = items.size() - 1; j >= 0; j--) {
							String temp = winf.partsOfSpeeches[j];
							int k = findLastMatchIndex(details, temp);
	
							winf.partsOfSpeechDetails[j] = details.substring(k);
							details = details.substring(0, k);
						}
					} else {
						System.err.println("Failed to load : segments");
					}
	
					WordInformations.add(winf);
					System.out
							.println("----------------------------------------");
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("\n\nDon't care");
				e.printStackTrace();
			}
		}
		printWordInformations();
	}
	
	public static int findLastMatchIndex(String main,String toMatch)
	{
		int k;
		int beginIndex=-1;
		while((k=main.indexOf(toMatch,beginIndex+1))!=-1)
		{
			beginIndex=k;
		}
		
		return beginIndex;
	}
}
//org/jsoup/examples/ListLinks.java

