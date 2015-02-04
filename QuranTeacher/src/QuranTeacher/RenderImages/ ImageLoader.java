/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.RenderImages;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader 
{
	/*
	 * Loads images from file
	 */
	//public static List<Image>images=new ArrayList<>();
	public static String ImageFileType=".img";
	
	private static String defaultDir=System.getProperty("user.dir")+"/WbWImages";
	//private final int lastImageId=77429;
	public ImageLoader() 
	{
		File directory=new File(defaultDir);
		if(!directory.exists())
		{
			directory.mkdir();
		}
	}
	
	//this wasn't called
/*	public void load()
	{
		BufferedImage img = null;
		File imageFile=null;
		
		for(int i=1;i<=lastImageId;i++)
		{
			imageFile=new File(defaultDir+"/"+Integer.toString(i)+ImageFileType);
			
			if(imageFile.exists())
			{
				try
				{
					img = ImageIO.read(imageFile);
					images.add(img);
				}
				catch(IOException io)
				{
					//io.printStackTrace();
					System.out.println(imageFile.getName()+" loading failed");
				}
			}
			else
				break;
		}
		
		System.out.println("Images loaded :"+images.size());
	}
	
	public static Image getImage(int imageId)
	{
		if(images.size()>=imageId)
			return images.get(imageId-1);
		return null;
	}*/
	
	public static Image getImageFromFile(int imageIndex)
	{
		BufferedImage img = null;
		File imageFile=null;
		
		imageFile=new File(defaultDir+"/"+Integer.toString(imageIndex+1)+ImageFileType);
		
		if(imageFile.exists())
		{
			try
			{
				img = ImageIO.read(imageFile);
				//images.add(img);
			}
			catch(IOException io)
			{
				//io.printStackTrace();
				System.out.println(imageFile.getName()+" loading failed");
			}
		}
		return img;
	}
}
