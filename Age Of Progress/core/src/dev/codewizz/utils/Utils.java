package dev.codewizz.utils;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Utils {
	
	public static File file = Gdx.files.external(Assets.pathFolderData + "names.txt").file();
	
	public static Random RANDOM = new Random();

	public static float distance(float x1, float y1, float x2, float y2) {
		return (float) Math.abs(Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
	}
	
	public static float distance(Vector2 a, Vector2 b) {
		return (float) Math.abs(Math.sqrt((b.x - a.x)* (b.x - a.x) + (b.y - a.y) * (b.y - a.y)));
	}
	
	public static float distance(float a, float b) {
		return (float) Math.abs(a - b);
	}
	
	public static float remap(float value, float oldmin, float oldmax, float newmin, float newmax) {
		return((value - oldmin) / (oldmax - oldmin)) * (newmax - newmin) + newmin;
	}
	
	public static int round(float value, float multiplier) {
		return (int) (multiplier*(Math.round(value/multiplier)));
	}
	
	public static float clamp(float v, float a, float b) {
		if(v < a)
			return a;
		else if(v > b)
			return b;
		else
			return v;
	}
	
	public static double clamp(double v, double a, double b) {
		if(v < a) {
			return a;
		} else if(v > b) {
			return b;
		} else {
			if(v > 1.0) {
				Logger.log("" + v);
			}
			return v;
		}
	}
	
	public static int HSBtoRGBA8888(float hue, float saturation, float brightness) {
		int r = 0, g = 0, b = 0;
		if (saturation == 0) {
			r = g = b = (int)(brightness * 255.0f + 0.5f);
		} else {
			float h = (hue - (float)Math.floor(hue)) * 6.0f;
			float f = h - (float)java.lang.Math.floor(h);
			float p = brightness * (1.0f - saturation);
			float q = brightness * (1.0f - saturation * f);
			float t = brightness * (1.0f - (saturation * (1.0f - f)));
			switch ((int)h) {
			case 0:
				r = (int)(brightness * 255.0f + 0.5f);
				g = (int)(t * 255.0f + 0.5f);
				b = (int)(p * 255.0f + 0.5f);
				break;
			case 1:
				r = (int)(q * 255.0f + 0.5f);
				g = (int)(brightness * 255.0f + 0.5f);
				b = (int)(p * 255.0f + 0.5f);
				break;
			case 2:
				r = (int)(p * 255.0f + 0.5f);
				g = (int)(brightness * 255.0f + 0.5f);
				b = (int)(t * 255.0f + 0.5f);
				break;
			case 3:
				r = (int)(p * 255.0f + 0.5f);
				g = (int)(q * 255.0f + 0.5f);
				b = (int)(brightness * 255.0f + 0.5f);
				break;
			case 4:
				r = (int)(t * 255.0f + 0.5f);
				g = (int)(p * 255.0f + 0.5f);
				b = (int)(brightness * 255.0f + 0.5f);
				break;
			case 5:
				r = (int)(brightness * 255.0f + 0.5f);
				g = (int)(p * 255.0f + 0.5f);
				b = (int)(q * 255.0f + 0.5f);
				break;
			}
		}
		return (r << 24) | (g << 16) | (b << 8) | 0x000000ff;
	}
	
	public static int getRandom(int min, int max) {
		return RANDOM.nextInt(max - min) + min;
	}
	
	public static float getRandom(float min, float max) {
		return getRandom((int)min, (int)max) + RANDOM.nextFloat();
	}
	
	public static float getDegreesFromVector(Vector2 v) {
		return (float)(180.0d / Math.PI * Math.atan2(v.x, v.y));		
	}
	
	public static Direction getDirFromVector(Vector2 v) {
		return Direction.getDirFromDeg(getDegreesFromVector(v));
	}
	
	public static String getRandomName() {
		String name = "";
		
		try (Stream<String> lines = Files.lines(Paths.get(file.getAbsolutePath()))) {
		    name = lines.skip(RANDOM.nextInt(7940)).findFirst().get();
		} catch(Exception e) {
			name = "ptr1500";
			e.printStackTrace();
		}
		
		return name;
	}
	
	public static String getFileType(String fileName) {
		String extension = "";

		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

		if (i > p) {
		    extension = fileName.substring(i+1);
		}
		
		return extension;
	}
	
	public static boolean openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}

	public static boolean openWebpage(URL url) {
	    try {
	        return openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
}