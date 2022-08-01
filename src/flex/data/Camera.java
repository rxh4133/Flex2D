package flex.data;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import flex.utils.MatrixTransformer;

public class Camera {

	private final Vector3f position;
	private final Vector3f rotation;
	private final int id;
	
	private Matrix4f viewMatrix;
	
    public Camera(int id) {
    	this.id = id;
    	viewMatrix = new Matrix4f();
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f position, Vector3f rotation, int id) {
        this.position = position;
        this.rotation = rotation;
        viewMatrix = new Matrix4f();
        this.id = id;
    }
    
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
    
    public Matrix4f updateViewMatrix() {
        return MatrixTransformer.updateGenericViewMatrix(position, rotation, viewMatrix);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }
    
    // This math moves the camera with respect to its rotation, rather than along world xyz
    public void movePosition(float x, float y, float z) {
        if ( z != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * z;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * z;
        }
        if ( x != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * x;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * x;
        }
        position.y += y;
    }
    
    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }
    
    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }
    
    public int getId() {
    	return id;
    }
    
    @Override
    public boolean equals(Object obj) {
    	return obj instanceof Camera && ((Camera)obj).id == id;
    }

}
