package flex.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import flex.utils.MatrixTransformer;
import flex.utils.UniformManager;

public class RenderEntityGroup {
	private List<RenderEntity> entities;
	private String identifier;
	private boolean hidden;
	public RenderEntityGroup(String identifier){
		entities = new ArrayList<RenderEntity>();
		this.identifier = identifier;
	}
	
	public RenderEntityGroup(String identifier, Collection<RenderEntity> entities) {
		this.entities = new ArrayList<RenderEntity>();
		this.entities.addAll(entities);
		this.identifier = identifier;
	}
	
	public void render(Camera camera, UniformManager uManager, MatrixTransformer transformer) {
		if(!hidden) {
			for(RenderEntity entity: entities) {
				uManager.setUniform("worldMatrix", transformer.getModelViewMatrix(entity, camera.getViewMatrix()));
				uManager.setUniform("useColor", entity.getModel().isTextured()? 0 : 1);
				if(!entity.getModel().isTextured()) {
					uManager.setUniform("color", entity.getModel().getColor());
				}
				entity.render();
			}
		}
	}
	
	public void addEntity(RenderEntity entity) {
		entities.add(entity);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof RenderEntityGroup && ((RenderEntityGroup)obj).identifier.equals(identifier);
	}
	
	@Override
	public String toString() {
		return identifier;
	}
	
	
}
