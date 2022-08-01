package flex.data;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL46.*;

import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class Model {
    private int vaoId;
    
    private ArrayList<Integer> vboIds;
    
    private final int vertexCount;
    private final int textId;
    
    private static final int NONE = -1;
    private static final int VERTEX_POSITIONS = 0;
    private static final int TEXTURE_COORDINATES = 1;
    private static final int NORMALS = 2;
    
    private Vector3f color;
    
    public Model(float[] positions, int[] indexes, float[] normals, Vector3f color) {
    	this.textId = NONE;
    	this.color = color;
    	vertexCount = indexes.length;
    	vboIds = new ArrayList<Integer>();
    	loadModelIntoMemory(positions, null, indexes, normals, true);
    }
    
    public Model(float[] positions, float[] textureCoordinates, int[] indexes, float[] normals, int textId) {
    	this.textId = textId;
    	vertexCount = indexes.length;
    	vboIds = new ArrayList<Integer>();
    	loadModelIntoMemory(positions, textureCoordinates, indexes, normals, false);
    }
    
    private void loadModelIntoMemory(float[] vertexPositions, float[] textCoords, int[] faceVertexInfo, float[] normals, boolean useColors) {
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        loadFloatVBOIntoMemory(vertexPositions, VERTEX_POSITIONS, 3);

        if(!useColors) {
        	loadFloatVBOIntoMemory(textCoords, TEXTURE_COORDINATES, 2);
        }
        
        loadFloatVBOIntoMemory(normals, NORMALS, 3);

        loadIntVBOIntoMemory(faceVertexInfo, NONE, NONE);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);         
    }
    
    private int loadFloatVBOIntoMemory(float[] input, int attribId, int numAttrib) {
    	int vboId = glGenBuffers();
    	vboIds.add(vboId);
    	FloatBuffer buffer = null;
    	try {
    		buffer = MemoryUtil.memAllocFloat(input.length);
    		buffer.put(input).flip();
    		glBindBuffer(GL_ARRAY_BUFFER, vboId);
    		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    		if(attribId != NONE) {
    			glEnableVertexAttribArray(attribId);
    			glVertexAttribPointer(attribId, numAttrib, GL_FLOAT, false, 0, 0);
    		}
    	} finally {
    		if(buffer != null) {
    			MemoryUtil.memFree(buffer);
    		}
    	}
    	return vboId;
    }
    
    private int loadIntVBOIntoMemory(int[] input, int attribId, int numAttrib) {
    	int vboId = glGenBuffers();
    	IntBuffer buffer = null;
    	try {
    		buffer = MemoryUtil.memAllocInt(input.length);
    		buffer.put(input).flip();
    		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
    		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    		if(attribId != NONE) {
    			glVertexAttribPointer(attribId, numAttrib, GL_INT, false, 0, 0);
    		}
    	} finally {
    		if(buffer != null) {
    			MemoryUtil.memFree(buffer);
    		}
    	}
    	return vboId;
    }
    
    public int getVertexCount() {
    	return vertexCount;
    }
    
    public int getVaoId() {
    	return vaoId;
    }
    
    public Vector3f getColor() {
    	return color;
    }
    
    public boolean isTextured() {
    	return textId != NONE;
    }
    
    public void render() {
        if (isTextured()) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, textId);
        }

        // Draw the mesh
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(VERTEX_POSITIONS);
        glEnableVertexAttribArray(TEXTURE_COORDINATES);
        glEnableVertexAttribArray(NORMALS);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(VERTEX_POSITIONS);
        glDisableVertexAttribArray(TEXTURE_COORDINATES);
        glDisableVertexAttribArray(NORMALS);
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for(Integer vboId: vboIds) {
        	glDeleteBuffers(vboId);
        }

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}
