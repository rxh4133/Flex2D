package flex.data;

import org.joml.Vector3f;

public class RenderEntity {
	private final Model model;
	
	private Vector3f position;
	
	private float scale;
	
	private Vector3f rotation;
	
	private boolean hidden;
	
	public RenderEntity(Model model) {
		this.model = model;
		position = new Vector3f(0,0,0);
		scale = 1;
		rotation = new Vector3f(0,0,0);
	}
	
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }
    
    public void render() {
    	if(!hidden) {
    		model.render();
    	}
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public void rotateX(int i) {
    	rotation.x += i;
    }
    
    public Model getModel() {
    	return model;
    }
}
