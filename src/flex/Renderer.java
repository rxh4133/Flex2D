package flex;

import static org.lwjgl.opengl.GL46.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.joml.Matrix4f;

import flex.layout.ViewPanel;
import flex.utils.MatrixTransformer;
import flex.utils.UniformManager;

public class Renderer {
	private ShaderProgram shaderProgram;
	private UniformManager uniformManager;
    
    private MatrixTransformer transformer;
	
	public void init() throws Exception {
		transformer = new MatrixTransformer();
	    shaderProgram = new ShaderProgram();
	    shaderProgram.createVertexShader(loadShader(".\\resources\\vertex.vs"));
	    shaderProgram.createFragmentShader(loadShader(".\\resources\\fragment.fs"));
	    shaderProgram.link();
	    
	    uniformManager = new UniformManager(shaderProgram.getProgramId());
	    uniformManager.createUniform("projectionMatrix");
	    uniformManager.createUniform("worldMatrix");
	    uniformManager.createUniform("texture_sampler");
	    uniformManager.createUniform("color");
	    uniformManager.createUniform("useColor");
	}
	
	public void close() {
		shaderProgram.cleanup();
	}
	
	private String loadShader(String path) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			StringBuilder builder = new StringBuilder();
			String line = "";
			while((line = reader.readLine()) != null) {
				builder.append(line);	
				builder.append("\r\n");
			}
			return builder.toString();
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
	}
	
	public void render(ViewPanel panel, Matrix4f projectionMatrix) {
	    clear();
	    
	    shaderProgram.bind();
	    uniformManager.setUniform("texture_sampler", 0);
    	uniformManager.setUniform("projectionMatrix", projectionMatrix);
    	
    	panel.render(uniformManager, transformer);
    	
	    shaderProgram.unbind();
	}
	
	public void clear() {
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
}
