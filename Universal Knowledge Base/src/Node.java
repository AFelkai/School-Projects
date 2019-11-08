public class Node {

	String question;
	String animal;
	Node yes;
	Node no;

	Node(String question) {
		this.question = question;
	}

	public void setYes(Node node) {
		yes = node;
	}
	
	public void setNo(Node node) {
		no = node;
	}
	
	public Node getYes() {
		return yes;
	}
	
	public Node getNo() {
		return no;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setAnimal(String animal) {
		this.animal = animal;
	}
	
	public String getAnimal() {
		return animal;
	}

	public boolean isLeaf() {
		boolean leaf;
		if(yes == null && no == null) leaf = true;
		else leaf = false;
		return leaf;
	}
}