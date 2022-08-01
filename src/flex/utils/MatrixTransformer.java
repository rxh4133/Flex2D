package flex.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import flex.data.Camera;
import flex.data.RenderEntity;

public class MatrixTransformer {

    private final Matrix4f projectionMatrix;

    private final Matrix4f modelViewMatrix;
    
    private final Matrix4f cameraMatrix;

    public MatrixTransformer() {
        modelViewMatrix = new Matrix4f();
        projectionMatrix = new Matrix4f();
        cameraMatrix = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRatio = width / height;        
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return projectionMatrix;
    }

    public Matrix4f getModelViewMatrix(RenderEntity model, Matrix4f viewMatrix) {
    	Vector3f rotation = model.getRotation();
        modelViewMatrix.identity().translate(model.getPosition()).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(model.getScale());
        Matrix4f viewCurr = new Matrix4f(viewMatrix);
        return viewCurr.mul(modelViewMatrix);
    }
    
    public static Matrix4f updateGenericViewMatrix(Vector3f position, Vector3f rotation, Matrix4f matrix) {
        return matrix.rotationX((float)Math.toRadians(rotation.x))
                     .rotateY((float)Math.toRadians(rotation.y))
                     .translate(-position.x, -position.y, -position.z);
    }
    
    public Matrix4f getCameraMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();

        cameraMatrix.identity();
        // First do the rotation so camera rotates over its position
        cameraMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
            .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
            .rotate((float)Math.toRadians(rotation.z), new Vector3f(0,0,1));
        // Then do the translation
        cameraMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return cameraMatrix;
    }
}
