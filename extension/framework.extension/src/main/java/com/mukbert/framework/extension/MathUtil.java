package com.mukbert.framework.extension;

import java.util.Random;

import com.mukbert.framework.EntityBasic;

public class MathUtil 
{
	private static Random random = new Random();
	
	public static double getDistance(EntityBasic entity1, EntityBasic entity2)
	{
		return getDistance(entity1.getCenterX(), entity1.getCenterY(), entity2.getCenterX(), entity2.getCenterY());
	}
	
	public static double getDistance(double x1, double y1, double x2, double y2)
	{
		double xDiff = x2 - x1;
		double yDiff = y2 - y1;
		
		return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
	}
	
	public static double getRandomSin()
	{
		return Math.sin(Math.PI * 2 * Math.random());
	}
	
	
	public static double getRandom()
	{
		return random.nextDouble();
	}
	
	public static double getRandom(double min, double max)
	{
		return min + getRandom() * (min - max);
	}
}
