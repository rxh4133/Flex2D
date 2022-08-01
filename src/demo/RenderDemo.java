package demo;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2d;
import org.joml.Vector3f;

import flex.Renderer;
import flex.data.Camera;
import flex.data.RenderEntity;
import flex.data.RenderEntityGroup;
import flex.data.MousePicker;
import flex.layout.ViewPanel;
import flex.layout.Window;
import flex.utils.ModelLoader;
import flex.utils.TextureLoader;

public class RenderDemo {

	public static void main(String[] args) throws Exception {
		Window window = new Window(300, 300, "Window");
		window.init();
		
//		RenderEntity entity = renderer.loadModel(".\\resources\\Shork.obj", ".\\resources\\Shork.png");
		int texId = TextureLoader.loadTexture(".\\resources\\bonkCard.png");
		RenderEntity entity = new RenderEntity(ModelLoader.loadModel(".\\resources\\bonk.obj", texId));
		entity.setPosition(0, 0, -5);
		entity.setRotation(90, -90, 0);
		RenderEntityGroup group = new RenderEntityGroup("test");
		group.addEntity(entity);
		ViewPanel panel = new ViewPanel();
		panel.addEntityGroup(group);
		window.setViewPanel(panel);
		
//		MousePicker picker = new MousePicker();
//		glfwSetMouseButtonCallback(window.getHandle(), (windowHandle, button, action, mods) ->{
//        	if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE) {
//        		RenderEntity found = picker.selectGameItem(new RenderEntity[] {entity}, window, new Vector2d(window.getMouseX(), window.getMouseY()), cam);
//        		System.out.println(found);
//        	}
//        });
		while(!window.shouldClose()){
			//moveCode(window, cam);
			window.update();
			Thread.sleep(1);
		}
		
		
		
		
		window.close();
	}
	
	public static void moveCode(Window window, Camera camera) {
		float xOff = 0;
		float yOff = 0;
		float zOff = 0;
		float xRot = 0;
		float yRot = 0;
		float zRot = 0;
		float posOffset = .01f;
		float rotOffset = .1f;
		if(window.isKeyPressed(GLFW_KEY_W)) {
			zOff -= posOffset;
		}
		if(window.isKeyPressed(GLFW_KEY_S)) {
			zOff += posOffset;
		}
		if(window.isKeyPressed(GLFW_KEY_A)) {
			xOff -= posOffset;
		}
		if(window.isKeyPressed(GLFW_KEY_D)) {
			xOff += posOffset;
		}
		if(window.isKeyPressed(GLFW_KEY_SPACE)) {
			yOff += posOffset;
		}
		if(window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
			yOff -= posOffset;
		}
		if(window.isKeyPressed(GLFW_KEY_UP)) {
			xRot -= rotOffset;
		}
		if(window.isKeyPressed(GLFW_KEY_DOWN)) {
			xRot += rotOffset;
		}
		if(window.isKeyPressed(GLFW_KEY_LEFT)) {
			yRot -= rotOffset;
		}
		if(window.isKeyPressed(GLFW_KEY_RIGHT)) {
			yRot += rotOffset;
		}
		camera.movePosition(xOff, yOff, zOff);
		camera.moveRotation(xRot, yRot, zRot);
	}
	
    static float[] positions = new float[] {
            // V0
            -0.5f, 0.5f, 0.5f,
            // V1
            -0.5f, -0.5f, 0.5f,
            // V2
            0.5f, -0.5f, 0.5f,
            // V3
            0.5f, 0.5f, 0.5f,
            // V4
            -0.5f, 0.5f, -0.5f,
            // V5
            0.5f, 0.5f, -0.5f,
            // V6
            -0.5f, -0.5f, -0.5f,
            // V7
            0.5f, -0.5f, -0.5f,
            
            // For text coords in top face
            // V8: V4 repeated
            -0.5f, 0.5f, -0.5f,
            // V9: V5 repeated
            0.5f, 0.5f, -0.5f,
            // V10: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V11: V3 repeated
            0.5f, 0.5f, 0.5f,

            // For text coords in right face
            // V12: V3 repeated
            0.5f, 0.5f, 0.5f,
            // V13: V2 repeated
            0.5f, -0.5f, 0.5f,

            // For text coords in left face
            // V14: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V15: V1 repeated
            -0.5f, -0.5f, 0.5f,

            // For text coords in bottom face
            // V16: V6 repeated
            -0.5f, -0.5f, -0.5f,
            // V17: V7 repeated
            0.5f, -0.5f, -0.5f,
            // V18: V1 repeated
            -0.5f, -0.5f, 0.5f,
            // V19: V2 repeated
            0.5f, -0.5f, 0.5f,
        };
        static float[] textCoords = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            
            // For text coords in top face
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,

            // For text coords in right face
            0.0f, 0.0f,
            0.0f, 0.5f,

            // For text coords in left face
            0.5f, 0.0f,
            0.5f, 0.5f,

            // For text coords in bottom face
            0.5f, 0.0f,
            1.0f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
        };
        static int[] indices = new int[]{
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            8, 10, 11, 9, 8, 11,
            // Right face
            12, 13, 7, 5, 12, 7,
            // Left face
            14, 15, 6, 4, 14, 6,
            // Bottom face
            16, 18, 19, 17, 16, 19,
            // Back face
            4, 6, 7, 5, 4, 7,};

}
