import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class OBJLoader {
    public static Obj loadObj(String path){
        return loadObj(new File(path));
    }
    public static Obj loadObj(File file) {
        Obj obj = new Obj();
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine(), " ", false);
            while (tokenizer.hasMoreTokens()) {
                switch (tokenizer.nextToken()) {
                    case "v":
                        obj.getVertices().add(new Vector3f(Float.parseFloat(tokenizer.nextToken()), Float.parseFloat(tokenizer.nextToken()), Float.parseFloat(tokenizer.nextToken())));
                        break;
                    case "vn":
                        obj.getNormals().add(new Vector3f(Float.parseFloat(tokenizer.nextToken()), Float.parseFloat(tokenizer.nextToken()), Float.parseFloat(tokenizer.nextToken())));
                        break;
                    case "f":
                        List<Vector2f> maskList = new ArrayList<>();
                        while (tokenizer.hasMoreTokens()){
                            String[] masksToken = tokenizer.nextToken().split("//|/");
                            if(masksToken.length == 2) {
                                maskList.add(new Vector2f(Float.parseFloat(masksToken[0]), Float.parseFloat(masksToken[1])));
                            }else {
                                maskList.add(new Vector2f(Float.parseFloat(masksToken[0]), 0));
                            }
                        }
                        obj.getMask().add(maskList);
                        break;
                    default:
                        break;
                }
            }

        }
        return obj;
    }
}
