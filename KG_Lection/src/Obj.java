import java.util.ArrayList;
import java.util.List;
/*Просто класс в котором хранятся парметры 3d объекта*/
public class Obj extends Object {
    private List<Vector3f> vertices = new ArrayList<>(); // Вершины
    private List<Vector3f> normals = new ArrayList<>(); // Нормали
    private List<List<Vector2f>> mask = new ArrayList<>(); // Маски

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
