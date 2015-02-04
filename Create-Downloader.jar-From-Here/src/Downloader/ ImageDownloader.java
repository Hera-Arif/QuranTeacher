/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package Downloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class ImageDownloader extends Downloader{

	private boolean overwrite;
	public static String parentSource;
	private static String imageFileType=".img";
	
	private List<Thread>imageLoadThreads;
	private int totalThreads;
	
	private int totalCompleted;
	
	
	public ImageDownloader(JButton btnStart_1,JTextArea txtrImageprogresstext,
			boolean imageOverwriteSelected, JLabel kbLabel, JLabel progressLabel) {
		
		super(btnStart_1, txtrImageprogresstext, kbLabel, progressLabel);
		
		parentSource="http://corpus.quran.com/wordimage?";
		overwrite=imageOverwriteSelected;
		
		storageFolder=System.getProperty("user.dir")+"/WbWImages";
		itemId="Image";
		maxVal=77429;
		maxValString=Integer.toString(maxVal);
		
		totalThreads=100;
		imageLoadThreads=new ArrayList<>(totalThreads);
		
		for(int i=1;i<=totalThreads;i++)
		{
			imageLoadThreads.add(new Thread(new Runner(i, totalThreads)));	
		}
		
		totalCompleted=0;
		shouldStop=false;
		
		File directory=new File(storageFolder);
		if(!directory.exists())
		{
			directory.mkdir();
			publish("New directory "+directory+" created.");
		}
		else
		{
			publish("Output directory "+directory);
		}
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		for(int i=0;i<totalThreads;i++)
		{
			imageLoadThreads.get(i).start();	
		}
		
		for(int i=0;i<totalThreads;i++)
		{
			imageLoadThreads.get(i).join();
		}
		if(totalCompleted==maxVal)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/*private String getFileName(String imageUrl)
	{
		return imageUrl.split("=")[1]+".png";
	}*/
	
	class Runner implements Runnable
	{
		int start;
		int gap;

		public Runner(int start,int gap) {
			this.start=start;
			this.gap=gap;
		}
		@Override
		public void run() 
		{
			for(int i=start;i<=maxVal;i+=gap)
			{
				//System.out.println(Thread.currentThread().getName());
				if(shouldStop)
				{
					//System.out.println(isCancelled());
					return;
				}
				
				String outId=Integer.toString(i);
				String outName=outId+imageFileType;
				File outFile=new File(storageFolder+"/"+outName);
				
				if(!overwrite && outFile.exists())
				{
					//publish(outName+" already exists.");
					incrreaseCompleted();
				}
				else
				{
					if(download(parentSource+"id="+outId, outFile))
					{
						//publish(outName+" downloaded");
						incrreaseCompleted();
					}
					else
						return;
				}
				
				if(shouldStop)
				{
					//System.out.println(isCancelled());
					return;
				}
				//sleep
				try
				{
					Thread.sleep(5);
				}
				catch(InterruptedException ie){ie.printStackTrace();}
				
				publish("ppp"+Integer.toString(totalCompleted));
				setProgress(100*totalCompleted/maxVal);
			}
		}
		
	}

	private synchronized void incrreaseCompleted()
	{
		totalCompleted++;
		notify();
	}
}
