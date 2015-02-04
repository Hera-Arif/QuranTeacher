/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import QuranTeacher.Preferences.MyLanguages;
import QuranTeacher.Preferences.TranslationPreferences;

public class TranslationSetupPanel extends PreferencesSetupPanel {

	/**
	 * For setting translation preferences
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	
	public TranslationSetupPanel(String name, final TranslationPreferences preferences) {
		super(name, preferences);
		
		previewText=MyLanguages.previewText[preferences.getLanguageIndex()];
		
		fontPreviewPanel.setText(previewText);
		fontPreviewPanel.updateFontPreview();
		
		JLabel lblLangName = new JLabel("Language :");
		lblLangName.setToolTipText("Set Translating Language");
		lblLangName.setForeground(Color.ORANGE);
		lblLangName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblLangName = new GridBagConstraints();
		gbc_lblLangName.anchor = GridBagConstraints.WEST;
		gbc_lblLangName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLangName.gridx = 0;
		gbc_lblLangName.gridy = 7;
		add(lblLangName, gbc_lblLangName);
		
		
		final JComboBox<String> LangSelectComboBox = new JComboBox<String> (MyLanguages.languageNames);
		LangSelectComboBox.setBackground(Color.LIGHT_GRAY);
		LangSelectComboBox.setSelectedIndex(preferences.getLanguageIndex());
		LangSelectComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int i=LangSelectComboBox.getSelectedIndex();
				preferences.setLanguageIndex(i);
				fontPreviewPanel.setText(MyLanguages.previewText[i]);
				fontPreviewPanel.updateTextArea();
			}
		});
		LangSelectComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_LangSelectComboBox = new GridBagConstraints();
		gbc_LangSelectComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_LangSelectComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_LangSelectComboBox.gridx = 1;
		gbc_LangSelectComboBox.gridy = 7;
		add(LangSelectComboBox, gbc_LangSelectComboBox);
	}

}
