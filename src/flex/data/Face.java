package flex.data;

public class Face {

    /**
     * List of idxGroup groups for a face triangle (3 vertices per face).
    */
    private FaceSegment[] idxGroups = new FaceSegment[3];

    public Face(String v1, String v2, String v3) {
        idxGroups = new FaceSegment[3];
        // Parse the lines
        idxGroups[0] = getFaceSegment(v1);
        idxGroups[1] = getFaceSegment(v2);
        idxGroups[2] = getFaceSegment(v3);
    }

    private FaceSegment getFaceSegment(String line) {
        FaceSegment faceSegment = new FaceSegment();

        String[] lineTokens = line.split("/");
        int length = lineTokens.length;
        faceSegment.idxPos = Integer.parseInt(lineTokens[0]) - 1;
        if (length > 1) {
            // It can be empty if the obj does not define text coords
            String textCoord = lineTokens[1];
            faceSegment.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : FaceSegment.NO_VALUE;
            if (length > 2) {
                faceSegment.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
            }
        }

        return faceSegment;
    }

    public FaceSegment[] getFaceVertexIndices() {
        return idxGroups;
    }
    
    public static class FaceSegment {

        public static final int NO_VALUE = -1;

        public int idxPos;

        public int idxTextCoord;

        public int idxVecNormal;

        public FaceSegment() {
            idxPos = NO_VALUE;
            idxTextCoord = NO_VALUE;
            idxVecNormal = NO_VALUE;
            }
    }
}