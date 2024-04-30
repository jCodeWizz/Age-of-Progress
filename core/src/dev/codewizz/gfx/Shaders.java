package dev.codewizz.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.sun.tools.javac.util.StringUtils;
import dev.codewizz.utils.Logger;

public class Shaders {

	/*
	 * 
	 * This is a util class that holds all of the shaders.
	 * 
	 */
	
	
	
	public static ShaderProgram outlineShader;
	public static ShaderProgram defaultShader;
	
	public static void init() {
		//outlineShader = new ShaderProgram(Gdx.files.internal("shaders/outline.vert").readString(), Gdx.files.internal("shaders/outline.frag").readString());
		String s = Gdx.files.internal("shaders/outline.frag").readString();

		Logger.log(s);
		Logger.log(fragmentShaderOutline);
		Logger.log((s.equals(fragmentShaderOutline)));

		Logger.log(difference(s, fragmentShaderOutline));

		outlineShader = new ShaderProgram(vertexShaderOutline, fragmentShaderOutline);
		defaultShader = SpriteBatch.createDefaultShader();
	}
	
	private static String vertexShaderOutline = "#version 140\r\n"
			+ "in vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "in vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "in vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ "out vec4 v_color;\n" //
			+ "out vec2 v_texCoords;\n" //
			+ "\n" //
			+ "void main()\n" //
			+ "{\n" //
			+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "   v_color.a = v_color.a * (255.0/254.0);\n" //
			+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "}\n";

	private static String fragmentShaderOutline = "#version 140\r\n"
			+ "in vec4 v_color;\r\n" //
			+ "in vec2 v_texCoords;\r\n" //
			+ "out vec4 pixel;\r\n"
			+ "uniform sampler2D u_texture;\r\n" //
			+ "uniform float outline_thickness = .2;\r\n"
			+ "uniform vec3 outline_colour = vec3(0.8, 0.4, 0);\r\n"
			+ "uniform float outline_threshold = .5;\r\n"
			+ "void main() {\r\n"
			+ "    pixel = texture(u_texture, v_texCoords);\r\n"
			+ "\r\n"
			+ "    if (pixel.a <= outline_threshold) {\r\n"
			+ "        ivec2 size = textureSize(u_texture, 0);\r\n"
			+ "\r\n"
			+ "        float uv_x = v_texCoords.x * size.x;\r\n"
			+ "        float uv_y = v_texCoords.y * size.y;\r\n"
			+ "\r\n"
			+ "        float sum = 0.0;\r\n"
			+ "        for (int n = 0; n < 9; ++n) {\r\n"
			+ "            uv_y = (v_texCoords.y * size.y) + (outline_thickness * float(n - 4.5));\r\n"
			+ "            float h_sum = 0.0;\r\n"
			+ "            h_sum += texelFetch(u_texture, ivec2(uv_x - (4.0 * outline_thickness), uv_y), 0).a;\r\n"
			+ "            h_sum += texelFetch(u_texture, ivec2(uv_x - (3.0 * outline_thickness), uv_y), 0).a;\r\n"
			+ "            h_sum += texelFetch(u_texture, ivec2(uv_x - (2.0 * outline_thickness), uv_y), 0).a;\r\n"
			+ "            h_sum += texelFetch(u_texture, ivec2(uv_x - outline_thickness, uv_y), 0).a;\r\n"
			+ "            h_sum += texelFetch(u_texture, ivec2(uv_x, uv_y), 0).a;\r\n"
			+ "            h_sum += texelFetch(u_texture, ivec2(uv_x + outline_thickness, uv_y), 0).a;\r\n"
			+ "            h_sum += texelFetch(u_texture, ivec2(uv_x + (2.0 * outline_thickness), uv_y), 0).a;\r\n"
			+ "            h_sum += texelFetch(u_texture, ivec2(uv_x + (3.0 * outline_thickness), uv_y), 0).a;\r\n"
			+ "            h_sum += texelFetch(u_texture, ivec2(uv_x + (4.0 * outline_thickness), uv_y), 0).a;\r\n"
			+ "            sum += h_sum / 9.0;\r\n"
			+ "        }\r\n"
			+ "\r\n"
			+ "        if (sum / 9.0 >= 0.0001) {\r\n"
			+ "            pixel = vec4(outline_colour, 1);\r\n"
			+ "        }\r\n"
			+ "    }\r\n"
			+ "}";

	public static String difference(String str1, String str2) {
		if (str1 == null) {
			return str2;
		}
		if (str2 == null) {
			return str1;
		}
		int at = indexOfDifference(str1, str2);
		if (at == -1) {
			return "";
		}
		return str2.substring(at);
	}

	public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
		if (cs1 == cs2) {
			return -1;
		}
		if (cs1 == null || cs2 == null) {
			return 0;
		}
		int i;
		for (i = 0; i < cs1.length() && i < cs2.length(); ++i) {
			if (cs1.charAt(i) != cs2.charAt(i)) {
				break;
			}
		}
		if (i < cs2.length() || i < cs1.length()) {
			return i;
		}
		return -1;
	}

}
