package dev.codewizz.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Shaders {

	/*
	 * 
	 * This is a util class that holds all of the shaders.
	 * 
	 */
	
	
	
	public static ShaderProgram outlineShader;
	public static ShaderProgram defaultShader;
	
	public static void init() {
		outlineShader = new ShaderProgram(Gdx.files.internal("shaders/outline.vert").readString(), Gdx.files.internal("shaders/outline.frag").readString());
        defaultShader = SpriteBatch.createDefaultShader();
	}
}
