public class ScaleUtils {
    public static void scale(Vector3f vector, double scale){
        double[][] scaleMatrix = {{scale, 0, 0}, {0, scale, 0}, {0, 0, scale}};
        Vector3f bufferVector = new Vector3f(vector.x, vector.y, vector.z);
        vector.x = bufferVector.x * scaleMatrix[0][0] + bufferVector.y * scaleMatrix[1][0] + bufferVector.z * scaleMatrix[2][0];
        vector.y = bufferVector.x * scaleMatrix[0][1] + bufferVector.y * scaleMatrix[1][1] + bufferVector.z * scaleMatrix[2][1];
        vector.z = bufferVector.x * scaleMatrix[0][2] + bufferVector.y * scaleMatrix[1][2] + bufferVector.z * scaleMatrix[2][2];
    }
}
