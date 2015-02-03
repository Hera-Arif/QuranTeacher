/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import QuranTeacher.Preferences.TranslationPreferences;
import QuranTeacher.Texts.TranslationText;

import java.awt.Font;
import java.awt.Color;

public class TranslationPanel extends JPanel {

	/**
	 * Panel that shows translation of ayah
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private static JTextArea txtrTranslationtext;
	private static TranslationText BengaliTranslationText;
	private static TranslationText EngLishTranslationText;
	
	private TranslationPreferences preferences;
	
	private Color bgColor;
	private Color fgColor;
	private Font transFont;
	private static int langIndex;
	
	
	public TranslationPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		preferences=PreferencesDialog.getTransPref();
		
		txtrTranslationtext = new JTextArea();
		txtrTranslationtext.setEditable(false);
		txtrTranslationtext.setLineWrap(true);
		txtrTranslationtext.setWrapStyleWord(true);
		
		updateTransPref();
		
		txtrTranslationtext.setText("TransLationText");
		scrollPane.setViewportView(txtrTranslationtext);
		
		Runnable transLoader=new Runnable() {
			
			@Override
			public void run() {
				try
				{
					//Thread.sleep(3000);
					BengaliTranslationText=new TranslationText(TranslationText.BnTransTextFileName);
					EngLishTranslationText=new TranslationText(TranslationText.EnTransTextFileName);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}				
			}
		};
		
		Thread t=new Thread(transLoader);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	private void setDesign() {
		
		txtrTranslationtext.setBackground(bgColor);
		txtrTranslationtext.setForeground(fgColor);
		txtrTranslationtext.setFont(transFont);
		
	}

	public static void setTranslationText(Selected ayah)
	{

		if(langIndex==0)
		{
			txtrTranslationtext.setText((ayah.suraIndex+1)+":"+(ayah.ayahIndex+1)+") "+
					EngLishTranslationText.getTranslationText(ayah));
		}
			
		else if(langIndex==1)
		{
			txtrTranslationtext.setText((ayah.suraIndex+1)+":"+(ayah.ayahIndex+1)+") "+
				BengaliTranslationText.getTranslationText(ayah));
		}
	}
	
	public void updateTransPref()
	{
		//System.out.println("translation preferences overloaded");
		//preferences=PreferencesDialog.getTransPref();
		bgColor=preferences.getBackGroundColor();
		fgColor=preferences.getForeGroundColor();
		transFont=preferences.getFont();
		langIndex=preferences.getLanguageIndex();
		
		setDesign();
	}
	
	public TranslationPreferences getTranslationPref()
	{
		return new TranslationPreferences(
				"translation.preferences",bgColor,fgColor,transFont,langIndex);
	}
}
