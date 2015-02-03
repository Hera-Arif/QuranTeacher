/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.SysexMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtractWordInformation {

	List<WordInformation> wordInformations;
	String defaultResource = System.getProperty("user.dir") + "/WbWCorpus/";
	FileOutputStream foStream;
	String url = "http://corpus.quran.com/wordbyword.jsp";
	int totalWords;

	int[] totalAyas = { 7, 286, 200, 176, 120, 165, 206, 75, 129, 109, 123,
			111, 43, 52, 99, 128, 111, 110, 98, 135, 112, 78, 118, 64, 77, 227,
			93, 88, 69, 60, 34, 30, 73, 54, 45, 83, 182, 88, 75, 85, 54, 53,
			89, 59, 37, 35, 38, 29, 18, 45, 60, 49, 62, 55, 78, 96, 29, 22, 24,
			13, 14, 11, 11, 18, 12, 12, 30, 52, 52, 44, 28, 28, 20, 56, 40, 31,
			50, 40, 46, 42, 29, 19, 36, 25, 22, 17, 19, 26, 30, 20, 15, 21, 11,
			8, 8, 19, 5, 8, 8, 11, 11, 8, 3, 9, 5, 4, 7, 3, 6, 3, 5, 4, 5, 6 };

	public ExtractWordInformation() {
		wordInformations = new ArrayList<>();

		try {
			foStream = new FileOutputStream("WbWInfo1.txt");
			System.out.println("Info file created : WbWInfo.txt");
		} catch (IOException ie) {
			System.err.println("no info file created");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		File file = null;
		// FileInputStream fsStream=null;
		Document doc = null;

		for (int i = 1; i <= 114; i++) {
			for (int j = 1; j <= totalAyas[i-1]; j++) {
				file = new File(defaultResource + getFileName(i, j));

				if (file.exists()) {
					System.out.println("Reading existing file : "
							+ file.getName());
					try {
						doc = Jsoup.parse(file,"UTF-8",url);
						// fsStream=new FileInputStream(file);
						getWordInfo(doc);
						//System.out.println("Done. Thread is going to sleep");
						
						Thread.sleep(100);
					} catch (IOException io) {
						System.err.println("IO");
						io.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		try {
			if(foStream!=null)
			{
				foStream.close();
				System.out.println("Writing file closed. : WbWInfo.txt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ExtractWordInformation().start();
	}

	public String getFileName(int suraIndex, int ayahIndex) {
		String a = "";
		String b = "";

		if (suraIndex < 10)
			a = "00";
		else if (suraIndex < 100)
			a = "0";
		if (ayahIndex < 10)
			b = "00";
		else if (ayahIndex < 100)
			b = "0";

		return a + Integer.toString(suraIndex) + b
				+ Integer.toString(ayahIndex) + ".txt";
	}

	public void getWordInfo(Document doc) {
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
					winf.index=totalWords;
					totalWords++;
					// print(" * tr: <%s>  (%s)", link.attr("abs:td"),
					// trim(link.text(), 100));

					Elements items;
					// String tagName;

					//System.out.printf(" cell=1 * <%s>: %s\n", cell.tagName(),cell.text());
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
						winf.meaning=trimAtFirst(winf.meaning);
					}
					
					
					// cell2
					cell = rowCells.get(1);
					//System.out.println(cell.toString());
					//System.out.printf(" cell=2 * <%s>: %s\n", cell.tagName(),cell.text());
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
					//System.out.printf(" cell=3 * <%s>: %s\n", cell.tagName(), cell.text());
					winf.cell3 = cell.text();

					items = cell.getElementsByAttributeValueContaining("class",
							"seg");

					if (items.size() != 0) {
						String arabic = null;
						String details = winf.cell3;
						
						//System.err.println("--->details :"+details);
						//System.err.println("--->items "+items.text());
						if (cell.getElementsByAttributeValue("class",
								"arabicGrammar").size() != 0) {
							arabic = cell
									.getElementsByAttributeValue("class",
											"arabicGrammar").get(0).text();
							details = details.replace(arabic, "");
							
						}
						//System.err.println("--->details next:"+details);

						for (int i = 0; i < items.size(); i++) {
							String[] colornames = items.get(i)
									.attr("abs:class").split("/");
							winf.segmentColors[i] = colornames[colornames.length - 1];
							winf.partsOfSpeeches[i] = items.get(i).text();
						}

						for (int j = items.size() - 1; j >= 0; j--) {
							String temp = winf.partsOfSpeeches[j];
							int k = findLastMatchIndex(details, temp);
							
							if(k==-1)
							{
								System.out.println("ERRRORR-------------");
								System.err.println("IndexOf returned -1");
								System.err.println("temp (to remove):"+temp);
								System.err.println("details (before removal):"+details);
								continue;
							}

							winf.partsOfSpeechDetails[j] = details.substring(k);
							details = details.substring(0, k);
							//System.err.println("details (after removal):"+details);
						}
					} else {
						System.err.println("Failed to load : segments");
					}
					//listing new word information
					//wordInformations.add(winf);
					try
					{
						byte[] buf=winf.toString().getBytes(Charset.forName("UTF-8"));
						foStream.write(buf);
					}catch(IOException ie)
					{
						ie.printStackTrace();
					}
					//System.out.println("Total Words :"+totalWords);
				}
			} catch (IndexOutOfBoundsException e) {
				System.err.println("wordInformation misses @"+totalWords);
				e.printStackTrace();
			}
		}
		//printWordInformations();
	}

	public void printWordInformations() {
		System.out.println("Printing word info");
		for (WordInformation w : wordInformations) {
			System.out.println(w.toString());
			System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
		}
	}
	
	public int findLastMatchIndex(String main,String toMatch)
	{
		int k;
		int beginIndex=-1;
		while((k=main.indexOf(toMatch,beginIndex+1))!=-1)
		{
			beginIndex=k;
		}
		
		return beginIndex;
	}

	private String trimAtFirst(String s)
	{
		int i=0;
		for(;i<s.length();i++)
			if(s.charAt(i)!=' ')
				break;
		return s.substring(i);
	}
}
