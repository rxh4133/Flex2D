package flex.layout;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import flex.Renderer;
import flex.data.MousePicker;
import flex.utils.ModelLoader;

public class Window {
	
	private long windowHandle;
	private int width;
	private int height;
	private boolean resized;
	private String title;
	private Matrix4f projectionMatrix;
	private double mouseX;
	private double mouseY;
	
	private Renderer renderer;
	
    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f; //Z_WHEREVER_YOU_ARE
    
    private ViewPanel panel;
	
	public Window(int width, int height, String title) throws Exception {
		this.width = width;
		this.height = height;
		this.title = title;
		projectionMatrix = new Matrix4f();
		renderer = new Renderer();
		
	}
	
	public long getHandle() {
		return windowHandle;
	}
	
	public void init() throws Exception {
		GLFWErrorCallback.createPrint(System.err).set();
		if ( !glfwInit() ) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		windowHandle = glfwCreateWindow(300, 300, title, NULL, NULL);
		
		glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
		    Window.this.width = width;
		    Window.this.height = height;
		    Window.this.resized = true;
		});
		
        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
            }
        });
        
        glfwSetCursorPosCallback(windowHandle, (windowHandle, xpos, ypos) -> {
            mouseX = xpos;
            mouseY = ypos;
        });
        
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                windowHandle,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );
		
		glfwMakeContextCurrent(windowHandle);

		glfwShowWindow(windowHandle);
		GL.createCapabilities();

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        
		renderer.init();
	}
	

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
	
    public Matrix4f updateProjectionMatrix() {
        float aspectRatio = (float)width / (float)height;        
        return projectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }
	
    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }
	
	public boolean isKeyPressed(int keyCode) {
	    return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
	}

	public boolean isResized() {
		return resized;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void close() {
		renderer.close();
		ModelLoader.close();
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(windowHandle);
	}

	public void setResized(boolean b) {
		resized = b;
	}
	
	public void setViewPanel(ViewPanel panel) {
		this.panel = panel;
	}
	
	public void update() {
		if(resized) {
			glViewport(0, 0, width, height);
			resized = false;
		}
		
		renderer.render(panel, updateProjectionMatrix());
		glfwSwapBuffers(windowHandle);
		glfwPollEvents();
	}

	public double getMouseX() {
		return mouseX;
	}
	
	public double getMouseY() {
		return mouseY;
	}

}
