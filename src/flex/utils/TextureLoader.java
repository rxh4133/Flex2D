package flex.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.*;

public class TextureLoader {
	public static Map<String, Integer> textureMap = new HashMap<>();

	public static int loadTexture(String path) throws IOException {
		if(!textureMap.containsKey(path)) {
			ByteBuffer buffer;
			int width;
			int height;
			try (MemoryStack stack = MemoryStack.stackPush()) {
	            IntBuffer w = stack.mallocInt(1);
	            IntBuffer h = stack.mallocInt(1);
	            IntBuffer channels = stack.mallocInt(1);

	            buffer = stbi_load(path, w, h, channels, 4);
	            if (buffer == null) {
	                throw new IOException("Image file [" + path  + "] not loaded: " + stbi_failure_reason());
	            }

	            width = w.get();
	            height = h.get();
	        }

			int id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			glGenerateMipmap(GL_TEXTURE_2D);
			
			textureMap.put(path,  id);

			return id;
		}
		return textureMap.get(path);
	}
	

}
