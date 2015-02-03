/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.Texts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



import QuranTeacher.Selected;
import QuranTeacher.SuraInformation;

public class QuranText {
	/*
	 * loads Quran Arabic Text
	 */
	private static String[][] quranText; 
	
	public static String QuranTextFileName="/QuranTeacher/files/quran-uthmani.txt";
	
	public QuranText(String file)// throws Exception 
	{
		InputStream in=getClass().getResourceAsStream(file);
		if(in!=null)
		{
			BufferedReader reader=null;
			
			try
			{
				reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
				
				String text;
				String firstAyah;
				
				quranText=new String[114][];
				int suraIndex=0;
				int ayahRead=0;
				int ayahCount=SuraInformation.totalAyas[0];
				quranText[0]=new String[ayahCount];
				
				while((text=reader.readLine())!=null)
				{
					if(ayahRead==0)//first ayah
					{
						if(suraIndex!=8)//sura at Tawba
							firstAyah=filterBismillah(text);
						else
							firstAyah=text;
						quranText[suraIndex][ayahRead]=firstAyah;
					}
					else
					{
						quranText[suraIndex][ayahRead]=text;
					}
					
					ayahRead++;
					
					if(ayahRead==ayahCount)
					{
						if(suraIndex==113)
							break;
						suraIndex++;
						ayahRead=0;
						ayahCount=SuraInformation.totalAyas[suraIndex];
						quranText[suraIndex]=new String[ayahCount];
					}
				}
				System.out.println(file+" Reading Success");
				reader.close();
			}
			catch(IOException ie)
			{
				ie.printStackTrace();
			}
		}
		else
		{
			System.out.println(file+" not found");
		}
	}
	
	/**
	 * @return the quranText
	 */
	public String getQuranText(Selected ayah) {
		return quranText[ayah.suraIndex][ayah.ayahIndex];
	}
	
	public String filterBismillah(String firstAyah) {
		int sp = -1;
		for (int i = 0; i < 4; i++) {
			// pass 4 whitespaces. in sura fatiha has 3 spaces, so ultimate sp=-1
			sp = firstAyah.indexOf(' ', sp + 1);
		}
		return firstAyah.substring(sp + 1);
	}
}
