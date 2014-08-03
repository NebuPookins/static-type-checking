abstract class Nat<Me extends Nat> {
	Succ<Me> next() { return new Succ<Me>(); }
}
class Zero extends Nat<Zero> { }
class Succ<Prev extends Nat> extends Nat<Succ<Prev>> { }

public class Test {
	static <N extends Nat> void acceptsConsecutive(N first, Succ<N> second) {
		System.out.println("Yup, they're consecutive allright.");
	}
	public static void main(String[] args) {
		Zero zero = new Zero();
		Succ<Zero> one = zero.next();
		Succ<Succ<Zero>> two = one.next();
		Succ<Succ<Succ<Zero>>> three = two.next();

		//acceptsConsecutive(zero, zero); //compile error
		acceptsConsecutive(zero, one);
		//acceptsConsecutive(zero, two); //compile error
		//acceptsConsecutive(zero, three); //compile error

		//acceptsConsecutive(one, zero); //compile error
		//acceptsConsecutive(one, one); //compile error
		acceptsConsecutive(one, two);
		//acceptsConsecutive(one, three); //compile error

		//acceptsConsecutive(two, zero); //compile error
		//acceptsConsecutive(two, one); //compile error
		//acceptsConsecutive(two, two); //compile error
		acceptsConsecutive(two, three);
	}
}

abstract class Stack<E, N extends Nat<N>> {
  public Stack<E, Succ<N>> push(E element) {
    return new StackNode<E, N>(element, this);
  }
}
class EmptyStack<E> extends Stack<E, Zero> { }
class StackNode<E, N extends Nat<N>> extends Stack<E, Succ<N>> {
  private final E head;
  private final Stack<E, N> next;
  public StackNode(E head, Stack<E, N> next) {
    this.head = head; this.next = next;
  }
  public Stack<E, N> pop() { return next; }
  public E get() { return head; }
}

class StackOperations {
  static <E, N extends Nat<N>> Stack<E, N> pop(Stack<E, Succ<N>> stack) {
    assert stack instanceof StackNode;
    return ((StackNode)stack).pop();
  }
  static <E, N extends Nat<N>> E get(Stack<E, Succ<N>> stack) {
    assert stack instanceof StackNode;
    return ((StackNode<E, N>)stack).get();
  }
}

class Test2 {
  public static void main(String[] args) {
    Stack<String,Zero> zero = new EmptyStack();
    //StackOperations.pop(zero); //Compile Error
    //StackOperations.get(zero); //Compile Error
    
    Stack<String,Succ<Zero>> one = zero.push("A");
    StackOperations.get(one);
    Stack<String,Zero> zeroAgain = StackOperations.pop(one);
    
    //StackOperations.get(zeroAgain); //Compile Error
    //StackOperations.pop(zeroAgain); //Compile Error
  }
}