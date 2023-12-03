package dev.codewizz.modding;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.badlogic.gdx.Gdx;

import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Pair;
import dev.codewizz.utils.Utils;

public class ModHandler {

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
            
            ZipFile zip = new ZipFile(file);
            InputStream stream = zip.getInputStream(zip.getEntry("info.mod"));

            ModInfo info = new ModInfo(stream);
            
            if(Registers.mods.keySet().contains(info.getId()) || info.getId().equals("aop")) {
            	Logger.error("Cannot load mod: " + file.getName().toUpperCase() + " because mod-id is already used: " + info.getId());
            	
            	child.close();
            	zip.close();
            	stream.close();
            	
            	return;
            }
            
            
            stream.close();
            
            
            
            for(String s : getClassNames(file)) {
            	Class<?> testClass = Class.forName(s, false, child);
                
                if(s.equals(info.getMain())) {
                	if(testClass.getSuperclass() == JavaMod.class) {
                    	JavaMod mod = (JavaMod)testClass.getConstructor().newInstance();
                    	Registers.registerMod(info, mod);
                    } else {
                    	Logger.error("Main class for mod: " + file.getName() + " is not a child of JavaMod");
                    }
                }
            }
                
            zip.close();
            child.close();
        } catch (Exception e) {
        	Logger.error("Exception while loading mod: " + file.getName());
        	e.printStackTrace();
        }
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

	 
	public void start() {
		List<Pair<ModInfo,JavaMod>> mods = new CopyOnWriteArrayList<>();
		
		mods.addAll(Registers.mods.values());
		
		
		for(Pair<ModInfo,JavaMod> pair : mods) {
			try {
				pair.getTypeB().register(pair.getTypeA());
				Logger.log("Registered mod: " + pair.getTypeA().getId().toUpperCase());
			} catch (Exception e) {
				Logger.error("Exception when mod: " + pair.getTypeA().getId() + " was registering");
				e.printStackTrace();
			}
		}
		
		while(!mods.isEmpty()) {
			for(Pair<ModInfo,JavaMod> pair : mods) {
				if(dependenciesClear(pair)) {
					try {
						pair.getTypeB().start();
						Logger.log("Started mod: " + pair.getTypeA().getId().toUpperCase());
						pair.getTypeA().initialized = true;
						mods.remove(pair);
						
					} catch (Exception e) {
						Logger.error("Exception when mod: " + pair.getTypeA().getId() + " was started");
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private boolean dependenciesClear(Pair<ModInfo,JavaMod> m) {
		String[] d = m.getTypeA().getDependencies();
		
		if(d == null) return true;
		
		for(int i = 0; i < d.length; i++) {
			if(!(d[i].isBlank() || d[i].isEmpty())) {
				ModInfo mod = Registers.mods.get(d[i]).getTypeA();
				if(!mod.initialized) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void update(float dt) {
		for(Pair<ModInfo, JavaMod> mod : Registers.mods.values()) {
			mod.getTypeB().update(dt);
		}
	}
	
	public void stop() {
		for(Pair<ModInfo, JavaMod> mod : Registers.mods.values()) {
			try {
				mod.getTypeB().stop();
			} catch (Exception e) {
				Logger.error("Exception when mod: " + mod.getTypeA().getId() + " was stopped");
				e.printStackTrace();
			}
		}
	}
}
