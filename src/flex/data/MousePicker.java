package flex.data;

import org.joml.Intersectionf;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import flex.layout.Window;

public class MousePicker {
	
	 private final Matrix4f invProjectionMatrix;
	    
	    private final Matrix4f invViewMatrix;

	    private final Vector3f mouseDir;
	    
	    private final Vector4f tmpVec;
	    
	    private final Vector3f max;

	    private final Vector3f min;

	    private final Vector2f nearFar;


	    public MousePicker() {
	        min = new Vector3f();
	        max = new Vector3f();
	        nearFar = new Vector2f();
	        invProjectionMatrix = new Matrix4f();
	        invViewMatrix = new Matrix4f();
	        mouseDir = new Vector3f();
	        tmpVec = new Vector4f();
	    }
	
    public RenderEntity selectGameItem(RenderEntity[] entities, Window window, Vector2d mousePos, Camera camera) {
        // Transform mouse coordinates into normalized spaze [-1, 1]
        int wdwWitdh = window.getWidth();
        int wdwHeight = window.getHeight();
        
        float x = (float)(2 * mousePos.x) / (float)wdwWitdh - 1.0f;
        float y = 1.0f - (float)(2 * mousePos.y) / (float)wdwHeight;
        float z = -1.0f;

        invProjectionMatrix.set(window.getProjectionMatrix());
        invProjectionMatrix.invert();
        
        tmpVec.set(x, y, z, 1.0f);
        tmpVec.mul(invProjectionMatrix);
        tmpVec.z = -1.0f;
        tmpVec.w = 0.0f;
        
        camera.updateViewMatrix();
        Matrix4f viewMatrix = camera.getViewMatrix();
        invViewMatrix.set(viewMatrix);
        invViewMatrix.invert();
        tmpVec.mul(invViewMatrix);
        
        mouseDir.set(tmpVec.x, tmpVec.y, tmpVec.z);
  
        RenderEntity selectedGameItem = null;
        float closestDistance = Float.POSITIVE_INFINITY;

        for (RenderEntity entity : entities) {
            min.set(entity.getPosition());
            max.set(entity.getPosition());
            min.add(-entity.getScale(), -entity.getScale(), -entity.getScale());
            max.add(entity.getScale(), entity.getScale(), entity.getScale());
            if (Intersectionf.intersectRayAab(camera.getPosition(), mouseDir, min, max, nearFar) && nearFar.x < closestDistance) {
                closestDistance = nearFar.x;
                selectedGameItem = entity;
            }
        }

        return selectedGameItem;
    }
}
