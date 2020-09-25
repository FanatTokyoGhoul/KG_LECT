import java.util.List;

public class RotateUtils {
    /*Вспомогательный класс для поворотов по осям XYZ*/
    public static void rotateX(List<Vector3f> vectors, int corner) { //По оси X
        double a = Math.PI * corner / 180;
        double[][] rotateMatrix = {{1, 0, 0}, {0, Math.cos(a), -Math.sin(a)}, {0, Math.sin(a), Math.cos(a)}};
        for (Vector3f vector3f : vectors) {
            multiplyingAVectorByAMatrix(vector3f, rotateMatrix);
        }
    }

    public static void rotateY(List<Vector3f> vectors, int corner) { // ПО оси Y
        double a = Math.PI * corner / 180;
        double[][] rotateMatrix = {{Math.cos(a), 0, Math.sin(a)}, {0, 1, 0}, {-Math.sin(a), 0, Math.cos(a)}};
        for (Vector3f vector3f : vectors) {
            multiplyingAVectorByAMatrix(vector3f, rotateMatrix);
        }
    }

    public static void rotateZ(List<Vector3f> vectors, int corner) { // ПО оси Z
        double a = Math.PI * corner / 180;
        double[][] rotateMatrix = {{Math.cos(a), -Math.sin(a), 0}, {Math.sin(a), Math.cos(a), 0}, {0, 0, 1}};
        for (Vector3f vector3f : vectors) {
            multiplyingAVectorByAMatrix(vector3f, rotateMatrix);
        }
    }

    /*Чуть уменьшил код. Вынес умножение в отдельный метод*/

    public static void multiplyingAVectorByAMatrix(Vector3f vector, double[][] matrix){
        Vector3f bufferVector = new Vector3f(vector.x, vector.y, vector.z);
        vector.x = bufferVector.x * matrix[0][0] + bufferVector.y * matrix[1][0] + bufferVector.z * matrix[2][0];
        vector.y = bufferVector.x * matrix[0][1] + bufferVector.y * matrix[1][1] + bufferVector.z * matrix[2][1];
        vector.z = bufferVector.x * matrix[0][2] + bufferVector.y * matrix[1][2] + bufferVector.z * matrix[2][2];
    }
}
