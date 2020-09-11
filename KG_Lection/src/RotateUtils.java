public class RotateUtils {
    public static void rotateX(Vector3f vector, int corner) {
        double a = Math.PI * corner / 180;
        double[][] rotateMatrix = {{1, 0, 0}, {0, Math.cos(a), -Math.sin(a)}, {0, Math.sin(a), Math.cos(a)}};
        Vector3f bufferVector = new Vector3f(vector.x, vector.y, vector.z);
        vector.x = bufferVector.x * rotateMatrix[0][0] + bufferVector.y * rotateMatrix[1][0] + bufferVector.z * rotateMatrix[2][0];
        vector.y = bufferVector.x * rotateMatrix[0][1] + bufferVector.y * rotateMatrix[1][1] + bufferVector.z * rotateMatrix[2][1];
        vector.z = bufferVector.x * rotateMatrix[0][2] + bufferVector.y * rotateMatrix[1][2] + bufferVector.z * rotateMatrix[2][2];
    }

    public static void rotateY(Vector3f vector, int corner) {
        double a = Math.PI * corner / 180;
        double[][] rotateMatrix = {{Math.cos(a), 0, Math.sin(a)}, {0, 1, 0}, {-Math.sin(a), 0, Math.cos(a)}};
        Vector3f bufferVector = new Vector3f(vector.x, vector.y, vector.z);
        vector.x = bufferVector.x * rotateMatrix[0][0] + bufferVector.y * rotateMatrix[1][0] + bufferVector.z * rotateMatrix[2][0];
        vector.y = bufferVector.x * rotateMatrix[0][1] + bufferVector.y * rotateMatrix[1][1] + bufferVector.z * rotateMatrix[2][1];
        vector.z = bufferVector.x * rotateMatrix[0][2] + bufferVector.y * rotateMatrix[1][2] + bufferVector.z * rotateMatrix[2][2];
    }

    public static void rotateZ(Vector3f vector, int corner) {
        double a = Math.PI * corner / 180;
        double[][] rotateMatrix = {{Math.cos(a), -Math.sin(a), 0}, {Math.sin(a), Math.cos(a), 0}, {0, 0, 1}};
        Vector3f bufferVector = new Vector3f(vector.x, vector.y, vector.z);
        vector.x = bufferVector.x * rotateMatrix[0][0] + bufferVector.y * rotateMatrix[1][0] + bufferVector.z * rotateMatrix[2][0];
        vector.y = bufferVector.x * rotateMatrix[0][1] + bufferVector.y * rotateMatrix[1][1] + bufferVector.z * rotateMatrix[2][1];
        vector.z = bufferVector.x * rotateMatrix[0][2] + bufferVector.y * rotateMatrix[1][2] + bufferVector.z * rotateMatrix[2][2];
    }
}
