/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.Preferences;

import java.awt.Color;
import java.awt.Font;



public class TranslationPreferences extends Preferences {
	
	private int languageIndex = 0;//for translation
	private static final String[] prefStrings={
		"BgColor=","FgColor=","Font=","LanguageIndex="};
	
	public TranslationPreferences(String id){
		super(id);
		defaultFont=new Font("SolaimanLipiNormal", Font.PLAIN, 18);
	}
	
	public TranslationPreferences(String id, Color bgColor, Color fgColor,
			Font transFont, int langIndex) {
		super(id);
		backGroundColor=bgColor;
		foreGroundColor=fgColor;
		font=transFont;
		languageIndex=langIndex;
	}

	public int getLanguageIndex()
	{
		return languageIndex;
	}
	
	public void setLanguageIndex(int i)
	{
		this.languageIndex=i;
	}
	
	@Override
	public String toString()
	{
		
		return
				prefStrings[0]+getBackGroundColor().getRGB()
				+ "\n"+prefStrings[1]+getForeGroundColor().getRGB()
				+ "\n"+prefStrings[2]+getFont().getFontName()
				+ fontPartsSeparator+getFont().getStyle()
				+ fontPartsSeparator+getFont().getSize()
				+ "\n"+prefStrings[3]+getLanguageIndex();
	}

	@Override
	public void checkAndGrab(String text) 
	{
		if(text.startsWith(prefStrings[0]))
			setBackGroundColor(new Color
					(Integer.parseInt(text.substring(text.indexOf("=")+1))));

		else if(text.startsWith(prefStrings[1]))
			setForeGroundColor(new Color
					(Integer.parseInt(text.substring(text.indexOf("=")+1))));

		else if(text.startsWith(prefStrings[2]))
			setFont(readFont(text.substring(text.indexOf("=")+1)));	

		else if(text.startsWith(prefStrings[3]))
			setLanguageIndex(Integer.parseInt(text.substring(text.indexOf("=")+1)));
		
	}

	@Override
	public void resetToDefault() {
		backGroundColor=Color.BLACK;
		foreGroundColor=Color.WHITE;
		font=defaultFont;
	}
}
