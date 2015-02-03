/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.Preferences;

public class MyLanguages {
	
	public static String[] languageNames={"English","Bengali"};
	public static String[] previewText={"Praise be to Allah, the Cherisher and Sustainer of the worlds;",
		"যাবতীয় প্রশংসা আল্লাহ তাআলার যিনি সকল সৃষ্টি জগতের পালনকর্তা।",
	};
	
	public static String AnimationPreviewText="\n\u0627\u0644\u0652\u062d\u064e\u0645\u0652\u062f\u064f "
			+ "\u0644\u0650\u0644\u0651\u064e\u0647\u0650 "
			+ "\u0631\u064e\u0628\u0651\u0650 "
			+ "\u0627\u0644\u0652\u0639\u064e\u0627\u0644\u064e\u0645\u0650\u064a\u0646\u064e\n";
	
	public static int getLanguageIndex(String langName)
	{
		for(int i = 0;i<languageNames.length;i++)
		{
			if(languageNames[i].equals(langName))
				return i;
		}
		return 0;
	}
}
