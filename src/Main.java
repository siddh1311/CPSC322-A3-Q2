import java.util.*;

class State {
    int a, b, c, d, e, f, g, h;
    public State(int a, int b, int c, int d, int e, int f, int g, int h) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
    }

    public State(State another) {
        this.a = another.a;
        this.b = another.b;
        this.c = another.c;
        this.d = another.d;
        this.e = another.e;
        this.f = another.f;
        this.g = another.g;
        this.h = another.h;
    }

    public String getNextVariable() {
//        DEGREE HEURISTIC CASE
//        if (this.f == 0) return "f";
//        if (this.h == 0) return "h";
//        if (this.c == 0) return "c";
//        if (this.d == 0) return "d";
//        if (this.g == 0) return "g";
//        if (this.e == 0) return "e";
//        if (this.a == 0) return "a";
//        if (this.b == 0) return "b";
        if (this.a == 0) return "a";
        if (this.b == 0) return "b";
        if (this.c == 0) return "c";
        if (this.d == 0) return "d";
        if (this.e == 0) return "e";
        if (this.f == 0) return "f";
        if (this.g == 0) return "g";
        if (this.h == 0) return "h";
        return "none";
    }

    public boolean checkValid() {
        if (this.a == 0) return false;
        if (this.b == 0) return false;
        if (this.c == 0) return false;
        if (this.d == 0) return false;
        if (this.e == 0) return false;
        if (this.f == 0) return false;
        if (this.g == 0) return false;
        if (this.h == 0) return false;
        return true;
    }

    @Override
    public String toString() {
        String out = "";
        if (this.a != 0) out += "A= " + a;
        if (this.b != 0) out += " B= " + b;
        if (this.c != 0) out += " C= " + c;
        if (this.d != 0) out += " D= " + d;
        if (this.e != 0) out += " E= " + e;
        if (this.f != 0) out += " F= " + f;
        if (this.g != 0) out += " G= " + g;
        if (this.h != 0) out += " H= " + h;
        return out;
    }
}
public class Main {
    public static int failingBranches = 0;
    public static void main(String[] args) {
        State start = new State(0, 0, 0, 0, 0, 0, 0, 0);
        List<State> solutions = search(start);
        System.out.println("\n-------------------------------");
        System.out.println("Solutions Found: ");
        for (State s : solutions) {
            System.out.println(s);
        }
    }

    public static List<State> search(State start) {
        List<State> solutions = new ArrayList<>();
        Stack<State> frontier = new Stack<State>();
        frontier.push(start);
        while(!frontier.empty()) {
            State current = frontier.pop();
            String out = "";
            out += current.toString();
            if (checkSolution(current)) {
                out += " success";
                solutions.add(current);
            } else {
                out += " failure";
            }
            System.out.println(out);
            successorFunction(current, frontier, current.getNextVariable());
        }
        System.out.println("Failing Branches: " + failingBranches);
        return solutions;
    }

    public static boolean checkSolution(State state) {
        if (!state.checkValid()) return false;
        if (state.a <= state.g) return false;
        if (state.a > state.h) return false;
        if (Math.abs(state.f - state.b) != 1) return false;
        if (state.g >= state.h) return false;
        if (Math.abs(state.g - state.c) != 1) return false;
        if (Math.abs(state.h - state.c) % 2 == 1) return false;
        if (state.h == state.d) return false;
        if (state.d < state.g) return false;
        if (state.d == state.c) return false;
        if (state.e == state.c) return false;
        if (state.e >= (state.d - 1)) return false;
        if (state.e == (state.h - 2)) return false;
        if (state.g == state.f) return false;
        if (state.h == state.f) return false;
        if (state.c == state.f) return false;
        if (state.d == (state.f - 1)) return false;
        if (Math.abs(state.e - state.f) % 2 == 0) return false;
        return true;
    }

    public static boolean checkConstraint(State state, String nextVar) {
        if (nextVar.equals("d")) {
            if (state.d == state.c) return false;
            return true;
        } else if (nextVar.equals("e")) {
            if (state.e == state.c) return false;
            if (state.e >= (state.d - 1)) return false;
            return true;
        } else if (nextVar.equals("f")) {
            if (Math.abs(state.f - state.b) != 1) return false;
            if (state.c == state.f) return false;
            if (state.d == (state.f - 1)) return false;
            if (Math.abs(state.e - state.f) % 2 == 0) return false;
            return true;
        } else if (nextVar.equals("g")) {
            if (state.a <= state.g) return false;
            if (Math.abs(state.g - state.c) != 1) return false;
            if (state.d < state.g) return false;
            if (state.g == state.f) return false;
            return true;
        } else if (nextVar.equals("h")) {
            if (state.a > state.h) return false;
            if (state.g >= state.h) return false;
            if (Math.abs(state.h - state.c) % 2 == 1) return false;
            if (state.h == state.d) return false;
            if (state.e == (state.h - 2)) return false;
            if (state.h == state.f) return false;
            return true;
        }
        return true;
    }

//    DEGREE HEURISTIC CASE
//    public static boolean checkConstraint(State state, String nextVar) {
//        if (nextVar.equals("h")) {
//            if (state.h == state.f) return false;
//            return true;
//        } else if (nextVar.equals("c")) {
//            if (Math.abs(state.h - state.c) % 2 == 1) return false;
//            if (state.c == state.f) return false;
//            return true;
//        } else if (nextVar.equals("d")) {
//            if (state.h == state.d) return false;
//            if (state.d == state.c) return false;
//            if (state.d == (state.f - 1)) return false;
//            return true;
//        } else if (nextVar.equals("g")) {
//            if (state.g >= state.h) return false;
//            if (Math.abs(state.g - state.c) != 1) return false;
//            if (state.d < state.g) return false;
//            if (state.g == state.f) return false;
//            return true;
//        } else if (nextVar.equals("e")) {
//            if (state.e == state.c) return false;
//            if (state.e >= (state.d - 1)) return false;
//            if (state.e == (state.h - 2)) return false;
//            if (Math.abs(state.e - state.f) % 2 == 0) return false;
//            return true;
//        } else if (nextVar.equals("a")) {
//            if (state.a <= state.g) return false;
//            if (state.a > state.h) return false;
//            return true;
//        } else if (nextVar.equals("b")) {
//            if (Math.abs(state.f - state.b) != 1) return false;
//            return true;
//        }
//        return true;
//    }

    public static void successorFunction(State state, Stack<State> frontier, String nextVar) {
        if (nextVar.equals("none")) {
            return;
        }

        for(int i = 4; i >= 1; i--) {
            State next = new State(state);
            switch (nextVar) {
                case "a" -> next.a = i;
                case "b" -> next.b = i;
                case "c" -> next.c = i;
                case "d" -> next.d = i;
                case "e" -> next.e = i;
                case "f" -> next.f = i;
                case "g" -> next.g = i;
                case "h" -> next.h = i;
            }
            if (checkConstraint(next, state.getNextVariable())) {
                frontier.push(next);
            } else {
                failingBranches++;
            }
        }
    }

}
