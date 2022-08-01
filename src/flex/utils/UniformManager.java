package flex.utils;

import static org.lwjgl.opengl.GL45.*;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

public class UniformManager {
    private final Map<String, Integer> uniforms;
    
    private int shaderProgramId;
    
    public UniformManager(int shaderProgramId) {
    	uniforms = new HashMap<>();
    	this.shaderProgramId = shaderProgramId;
    }
	
    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(shaderProgramId,
            uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" +
                uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }
    
    public void setUniform(String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }
    
    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }
    
    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }
}
