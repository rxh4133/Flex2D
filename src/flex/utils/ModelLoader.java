package flex.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

import flex.data.Model;

public class ModelLoader {
	private static List<Model> models = new ArrayList<>();
	public static Model loadModel(String path, int textureId) throws Exception {
		Obj obj = ObjUtils.convertToRenderable(ObjReader.read(new FileInputStream(path)));

		Model m = new Model(ObjData.getVerticesArray(obj), flipYAxis(ObjData.getTexCoordsArray(obj, 2)), ObjData.getFaceVertexIndicesArray(obj,3), ObjData.getNormalsArray(obj), textureId);
		models.add(m);
		return m;
	}
	
	public static void close() {
		for(Model m: models) {
			m.cleanUp();
		}
	}
	
	public static float[] flipYAxis(float[] input) {
		for(int i = 0; i < input.length; i+=2) {
			input[i+1] = Math.abs(1- input[i+1]);
		}
		return input;
	}

}
