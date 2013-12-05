package com.butt.ast;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound extends Thread
{
	String file;
	private AudioFormat format;
    private byte[] samples;
    InputStream audio;

    public Sound(String filename)
    {
    	file=filename;
    	init();
    }
    
    private void init()
    {
    	AudioInputStream AIstream;
    	try
        {
            AIstream=AudioSystem.getAudioInputStream(new File(file));
            format=AIstream.getFormat();
            createSamples(AIstream);
            audio=new ByteArrayInputStream(samples);
        }
        catch (UnsupportedAudioFileException ex)
        { }
        catch (IOException ex)
        { }
    }

    private void createSamples(AudioInputStream ais)
    {
        int length = (int)(ais.getFrameLength()*format.getFrameSize());
        byte[] smpls = new byte[length];
        DataInputStream is = new DataInputStream(ais);
        try
        {
            is.readFully(smpls);
        }
        catch (IOException ex)
        { }

        samples=smpls;
    }

    public synchronized void run()
    {
    	int bufferSize = format.getFrameSize() *
        Math.round(format.getSampleRate() / 10);
    	byte[] buffer = new byte[bufferSize];
    	SourceDataLine line;
    	int numBytes=0;
    	try
    	{
    		DataLine.Info info=new DataLine.Info(SourceDataLine.class, format);
    		line = (SourceDataLine)AudioSystem.getLine(info);
    		line.open(format, bufferSize);
    	}
    	catch (LineUnavailableException ex)
    	{
    		return;
        }
    	line.start();

    	try
    	{
    		while (numBytes != -1)
    		{
    			numBytes=audio.read(buffer, 0, buffer.length);
    			if(numBytes != -1)
    			{
    				line.write(buffer, 0, numBytes);
    			}
    		}
    	}
    	catch (IOException ex)
    	{ }

    	line.drain();
    	line.close();
    }
}