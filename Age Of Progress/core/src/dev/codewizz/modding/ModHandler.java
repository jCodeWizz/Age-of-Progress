package dev.codewizz.modding;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.badlogic.gdx.Gdx;

import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Utils;

public class ModHandler {

	private static HashMap<String, JavaMod> register = new HashMap<>();
	
	public void register() {
		File file = Gdx.files.external("mods").file();
		
		File[] mods = file.listFiles();
		
		for(int i = 0; i < mods.length; i++) {
			if(Utils.getFileType(mods[i].getName()).equalsIgnoreCase("jar")) {
				loadMod(mods[i]);
			} else {
				Logger.error("Could not load mod: " + mods[i].getName().toUpperCase() + " because it wasn't detected as a jar file!");
			}
		}
	}
	
	private void loadMod(File file) {
        try {
            URLClassLoader child = new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader());
            
            String name = "";

            ZipFile zip = new ZipFile(file);
            InputStream stream = zip.getInputStream(zip.getEntry("info.mod"));
            Scanner s = new Scanner(stream);
            
            while(s.hasNext()) {
            	String text = s.nextLine();
            	int i = text.indexOf(':');
            	
            	String a = text.substring(0, i);
            	String b = text.substring(i, text.length());
            	b = b.replaceAll("[^a-zA-Z.0-9]", "");
            	
            	if(a.equalsIgnoreCase("main")) name = b;
            	
            	System.out.println(a + " : " + b);
            }
            
            s.close();
            stream.close();

            Class<?> testClass = Class.forName(name,false,child);

            if(testClass.getSuperclass() == JavaMod.class) {
            	JavaMod mod = (JavaMod)testClass.getConstructor().newInstance();
            	mod.start();
            }
                
                
            zip.close();
        } catch (Exception e) {
        	
        }
	}
	 
	public void start() {
		for(JavaMod mod : register.values()) {
			mod.start();
		}
	}
	
	public void update(float dt) {
		for(JavaMod mod : register.values()) {
			mod.update(dt);
		}
	}
	
	public void stop() {
		for(JavaMod mod : register.values()) {
			mod.stop();
		}
	}
	
	public JavaMod getMod(String id) {
		return register.get(id);
	}
	
    public ArrayList<String> getClassNames(File file) {
        ArrayList<String> classNames = new ArrayList<String>();
        try {
            ZipInputStream zip = new ZipInputStream(new FileInputStream(file.getPath()));
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace('/', '.');
                    classNames.add(className.substring(0, className.length() - ".class".length()));
                }
            }
        } catch (Exception ignored) {}
        return classNames;
    }
}
