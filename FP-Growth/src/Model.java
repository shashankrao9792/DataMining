import java.util.HashMap;

public class Model {
	Node root = null;
	HashMap<String, Links> header = null;
	
	Model(Node root, HashMap<String, Links> header){
		this.root = root;
		this.header = header;
	}
}
