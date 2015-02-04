/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.RenderAudio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;



import QuranTeacher.AudioNavigationPanel;
import QuranTeacher.Selected;
import javazoom.jl.decoder.JavaLayerException;

public class Reciter implements Runnable {

	public static String DefaultStorage = System.getProperty("user.dir")
			+ "/QuranAudio";
	public static String DefaultURL = "http://www.everyayah.com/data/Alafasy_64kbps";

	private static Selected ayah;
	private static ZPlayer zplayer;
	private static int connectionTimeout=5000;
	
	private Thread t;
	
	
	public Reciter(Selected ayah) {		
		Reciter.ayah = ayah;
		
		t=new Thread(this,"reciterThread");
		t.start();
	}

	public void reciteAyat(Selected ayah) throws JavaLayerException,
			IOException {

		FileInputStream Fstream = null;

		String name = getAyatmp3Name(ayah);
		File playFile = new File(DefaultStorage + "/"+name);

		if (playFile.exists()) 
		{
			try 
			{
				Fstream = new FileInputStream(playFile);
				AudioNavigationPanel.setProgressText("Playing...");

			} catch (Exception e) {e.printStackTrace();}
		}
		else
		{

			AudioNavigationPanel.setProgressText("DownLoading from internet...");
			try {

				if(download(DefaultURL+"/"+name, playFile))
				{
					AudioNavigationPanel.setProgressText("Downloading completed. Playing...");
				}
				else
				{
					AudioNavigationPanel.setProgressText("Downloading Failed.");
					return;
				}
				Fstream = new FileInputStream(playFile);

			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
				AudioNavigationPanel.setProgressText("Downloading Failed.");
				return;
			}
		}

		zplayer = new ZPlayer(Fstream);
	}

	public String getAyatmp3Name(Selected ayah) {
		String a = "";
		String b = "";

		int suraIndex = ayah.suraIndex + 1;
		int ayahIndex = ayah.ayahIndex + 1;

		if (suraIndex < 10)
			a = "00";
		else if (suraIndex < 100)
			a = "0";
		if (ayahIndex < 10)
			b = "00";
		else if (ayahIndex < 100)
			b = "0";

		String mp3Name = a + String.valueOf(suraIndex) + b
				+ String.valueOf(ayahIndex) + ".mp3";
		return mp3Name;
	}

	@Override
	public void run() {
		try {
			//System.out.println("Running Thread :"+Thread.currentThread().getName());
			reciteAyat(Reciter.ayah);
		} catch (Exception e) {// e.printStackTrace();
			AudioNavigationPanel.setProgressText("Player failed");
		}

	}

	public static boolean isAlive() {
		if(zplayer==null)
			return false;
		return !zplayer.isComplete();
	}

	public boolean download(String audioUrl, File outputFile)
	{
		URL url = null;
		try 
		{
			url = new URL(audioUrl);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		
		ReadableByteChannel rbc = null;
		try 
		{
			URLConnection connection=url.openConnection();
			connection.setConnectTimeout(connectionTimeout);
			rbc = Channels.newChannel(connection.getInputStream());
			FileOutputStream foStream = new FileOutputStream(outputFile);
			foStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			
			foStream.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void setDefaultUrl(String url)
	{
		DefaultURL=url;
	}
	
	public static void createDirectory()
	{
		File directory=new File(DefaultStorage);
		if(!directory.exists())
		{
			directory.mkdir();
		}
	}
   
   public void pause(){
        zplayer.pause();
    }
    
    public void resume(){
        
        zplayer.resume();
    }
    
    public void stop(){
        
        zplayer.stop();   
    }
	
}
