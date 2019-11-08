import java.util.Scanner;
public class BinaryTree {

	Scanner kb = new Scanner(System.in);
	Node currentNode;
	Node root;
	//Node turningPoint;
	String isNo;

	public String guess() {

		System.out.print(currentNode.getQuestion() + " ");
		String answer = kb.nextLine();

		if(answer.equals("Yes") && currentNode.isLeaf()) guessYes();
		else if(answer.equals("No") && currentNode.isLeaf()) guessNo();
		else if(answer.equals("Yes") && !currentNode.isLeaf()) {
			if(currentNode.getYes() == null) guessYes();
			else {
				isNo = currentNode.getAnimal();
				currentNode = currentNode.getYes();
			}
			return guess();
		}
		else if(answer.equals("No") && !currentNode.isLeaf()) {
			if(currentNode.getNo() == null) guessNo();
			else currentNode = currentNode.getNo();
			return guess();
		}
		return "Goodbye";
	}

	public void newGame() {
		System.out.println("Think of an animal and I will guess it");
		currentNode = root;
		isNo = "Snake";
		guess();
	}

	public void guessYes() {
		System.out.println("Is it a(n) " + currentNode.getAnimal() + "?");
		String yn = kb.nextLine();
		if(yn.equals("yes") || yn.equals("Yes") || yn.equals("Y") || yn.equals("y")) {
			System.out.println("I win! Continue? ");
			yn = kb.nextLine();
			if(yn.equals("yes") || yn.equals("Yes") || yn.equals("Y") || yn.equals("y")) {
				newGame();
			}
			else System.exit(1);
		}
		else {
			leafNoLeft();
			System.out.println("Continue? ");
			yn = kb.nextLine();
			if(yn.equals("yes") || yn.equals("Yes") || yn.equals("Y") || yn.equals("y")) newGame();
			else System.exit(1);
		}
	}

	public void guessNo() {
		System.out.println("Is it a(n) " + isNo + "?");
		String yn = kb.nextLine();
		if(yn.equals("yes") || yn.equals("Yes") || yn.equals("Y") || yn.equals("y")) {
			System.out.println("I win! Continue? ");
			yn = kb.nextLine();
			if(yn.equals("yes") || yn.equals("Yes") || yn.equals("Y") || yn.equals("y")) {
				newGame();
			}
			else System.exit(1);
		}
		else {
			leafNoRight();
			System.out.println("Continue? ");
			yn = kb.nextLine();
			if(yn.equals("yes") || yn.equals("Yes") || yn.equals("Y") || yn.equals("y")) newGame();
			else System.exit(1);
		}
	}

	public void leafNoLeft() {
		System.out.print("I give up. What is it? ");
		String answer = kb.nextLine();
		System.out.println("\nPlease type a question whose answer is yes for a(n) " + answer +  " and no for a(n) " + currentNode.getAnimal() + ".");
		String question = kb.nextLine();

		Node newNode = new Node(question);
		newNode.setAnimal(answer);
		currentNode.setYes(newNode);
	}

	public void leafNoRight() {
		System.out.print("I give up. What is it? ");
		String answer = kb.nextLine();
		System.out.println("\nPlease type a question whose answer is yes for a(n) " + answer +  " and no for a(n) " + isNo + ".");
		String question = kb.nextLine();

		Node newNode = new Node(question);
		newNode.setAnimal(answer);
		currentNode.setNo(newNode);

	}

	public static void main(String[] args) {

		BinaryTree tree = new BinaryTree();

		tree.currentNode = new Node("Does it have legs?");
		tree.currentNode.setAnimal("Cat");
		tree.isNo = "Snake";
		tree.root = tree.currentNode;
	//	tree.turningPoint = tree.root;

		tree.newGame();
	}
}