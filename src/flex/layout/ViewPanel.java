package flex.layout;

import java.util.ArrayList;
import java.util.List;

import flex.data.Camera;
import flex.data.RenderEntityGroup;
import flex.utils.MatrixTransformer;
import flex.utils.UniformManager;

public class ViewPanel {
	private Camera camera;
	private static int cameraIdControl = 0;
	private List<RenderEntityGroup> entityGroups;

	public ViewPanel() {
		camera = new Camera(cameraIdControl);
		camera.setPosition(camera.getId() * 3, 0, 0);
		cameraIdControl++;
		entityGroups = new ArrayList<>();
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void addEntityGroup(RenderEntityGroup group) {
		entityGroups.add(group);
	}
	
	public void render(UniformManager uManager, MatrixTransformer transformer) {
		for(RenderEntityGroup group: entityGroups) {
			group.render(camera, uManager, transformer);
	    }
	}
}
