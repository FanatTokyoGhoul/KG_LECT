public class ScaleUtils {
    /*Вспомогательный класс маштабирования*/
    public static void scale(Vector3f vector, double scale) {
        double[][] scaleMatrix = {{scale, 0, 0}, {0, scale, 0}, {0, 0, scale}};
        RotateUtils.multiplyingAVectorByAMatrix(vector, scaleMatrix);
    }
}
