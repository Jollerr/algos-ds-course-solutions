import java.util.LinkedList;

public class LongStack {

    public static void main (String[] argum) {
        String s = "22 -";
        LongStack.interpret (s);
    }

    private LinkedList<Long> stack;

    LongStack() {
        stack = new LinkedList<>();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        LongStack stackClone = new LongStack();
        for (Long aLong : stack) {
            stackClone.push(aLong);
        }
        return stackClone;
    }

    public boolean stEmpty() {
        return stack.isEmpty();
    }

    public void push (long a) {
        stack.addLast(a);
    }

    public long pop() {
        if (stEmpty()) {
            throw new IndexOutOfBoundsException("Stack underflow: " + this);
        }
        return stack.removeLast();
    }

    public void op(String s) throws RuntimeException {
        if (stack.size() < 2) {
            throw new RuntimeException("Not enough elements left in stack. Trying to use " + s + " on " + this);
        }
        long x2 = pop();
        long x1 = pop();
        switch (s) {
            case "+":
                push(x1 + x2);
                break;
            case "-":
                push(x1 - x2);
                break;
            case "*":
                push(x1 * x2);
                break;
            case "/":
                push(x1 / x2);
                break;
            default:
                // http://enos.itcollege.ee/~jpoial/algorithms/examples/IntStack.java
                throw new IllegalArgumentException("Illegal operator: " + s);

        }
    }

    public long tos() {
        if (stEmpty()) {
            throw new IndexOutOfBoundsException("Stack underflow " + this);
        }
        return stack.getLast();
    }

    @Override
    public boolean equals (Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        LongStack toCompare = (LongStack) o;
        return this.toString().equals(toCompare.toString());
    }


    @Override
    public String toString() {
        if (stEmpty()) {
            return "empty";
        }
        StringBuilder sb = new StringBuilder();
        for (Long aLong : stack) {
            sb.append(aLong + " ");
        }
        return sb.toString();
    }

    public static long interpret (String pol) {
        String[] toInterpret = pol.trim().split("\\s+"); // (\\s vs \\s+) -> https://stackoverflow.com/questions/15625629/regex-expressions-in-java-s-vs-s
        LongStack ls = new LongStack();                        // https://stackoverflow.com/questions/225337/how-do-i-split-a-string-with-any-whitespace-chars-as-delimiters
        try {
            for (String s : toInterpret) { //
                try {
                    ls.push(Long.parseLong(s));
                } catch (NumberFormatException e) {
                    ls.op(s); // calculate
                }
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Couldn't interpret " + pol + ". " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Couldn't interpret " + pol + ". " + e.getMessage());
        }
        long solution = ls.pop();
        if (!ls.stEmpty()) {
            throw new RuntimeException("Couldn't interpret " + pol + ". All arithmetic done but the stack is not empty: " + solution + " " + ls.toString());
        }
        return solution;
    }
}

