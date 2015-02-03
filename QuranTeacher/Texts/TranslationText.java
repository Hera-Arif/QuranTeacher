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

public class TranslationText {
	
	private String[][] translationText; 
	
	public static String EnTransTextFileName="/QuranTeacher/files/en.yusufali.txt";
	public static String BnTransTextFileName="/QuranTeacher/files/bn.bengali.txt";
	
	public TranslationText(String file) throws Exception 
	{
		InputStream in=getClass().getResourceAsStream(file);
		if(in!=null)
		{
			BufferedReader reader=null;
			
			try
			{
				reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
				String text;
				
				translationText=new String[114][];
				int suraIndex=0;
				int ayahRead=0;
				int ayahCount=SuraInformation.totalAyas[0];
				translationText[0]=new String[ayahCount];
				
				while((text=reader.readLine())!=null)
				{

					translationText[suraIndex][ayahRead]=text;
					
					ayahRead++;
					
					if(ayahRead==ayahCount)
					{
						if(suraIndex==113)
							break;
						suraIndex++;
						ayahRead=0;
						ayahCount=SuraInformation.totalAyas[suraIndex];
						translationText[suraIndex]=new String[ayahCount];
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
	}
	/**
	 * @return the quranText
	 */
	public String getTranslationText(Selected ayah) {
		return translationText[ayah.suraIndex][ayah.ayahIndex];
	}
}

