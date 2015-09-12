package com.mukbert.framework;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound 
{
	private File soundFile;
	private Clip clip;
	
	public Sound(File file) 
	{
		this.soundFile = file;
	}
	
	public void start(boolean loop)
	{
		try 
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			
			clip = AudioSystem.getClip();
			
			clip.open(audioInputStream);

			clip.setFramePosition(0);
			if(loop)
			{
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			
			clip.start();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void stop()
	{
		clip.stop();
	}

	public boolean isPlaying() 
	{
		return clip.isActive();
	}
}
