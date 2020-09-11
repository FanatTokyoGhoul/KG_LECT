import java.util.ArrayList;
import java.util.List;

public class Obj extends Object {
    List<Vector3f> vertices = new ArrayList<>();
    List<Vector3f> normals = new ArrayList<>();
    List<List<Vector2f>> mask = new ArrayList<>();

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public List<List<Vector2f>> getMask() {
        return mask;
    }
}
