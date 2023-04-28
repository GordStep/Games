import java.util.Scanner;

class myStack {
    private int maxSize;
    private int[] stackArray;
    private int top;

    public myStack(int maxSize) {
        this.maxSize = maxSize;
        this.stackArray = new int[maxSize];
        this.top = -1;
    }
    public void addLast(int element) {
        stackArray[++top] = element;
    }
    public void removeLast() {
        stackArray[top] = 0;
        top--;
    }
    public void addFirst(int element) {
        int[] stackCopy = new int[++maxSize];
        stackCopy[0] = element;
        for (int i = 1, j = 0; i < stackCopy.length; i++) {
            stackCopy[i] = stackArray[j++];
        }
        stackArray = stackCopy;
        top++;
    }
    public int getLast() {
        return stackArray[top];
    }
    public boolean isEmpty() {
        return (top == -1);
    }
    public boolean isFull() {
        return (top == maxSize - 1);
    }
    public void print() {
        for (int i = 0; i < stackArray.length; i++) {
            if (i == 0) System.out.print("[" + stackArray[i] + ", ");
            else if (i == maxSize - 1) System.out.print(stackArray[i] + "]");
            else System.out.print(stackArray[i] + ", ");
        }
        System.out.println();
    }
    public int findMax() {
        int result = 0;
        for (int i = 0; i < stackArray.length; i++) {
            if (result < stackArray[i]) {
                result = stackArray[i];
            }
        }
        return result;
    }

}

public class drunkards {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int countStep = 0;
        int maxCountStep = 1_000_000;
        System.out.println("Введите колличество карт в колоде: ");
        int CountCards = scanner.nextInt();
        System.out.println("Введите карты игроков: ");
        myStack playerFirst = new myStack(CountCards / 2);
        myStack playerSecond = new myStack(CountCards / 2);
        String aString = scanner.nextLine();
        String firstCards = scanner.nextLine();
        String secondCards = scanner.nextLine();
        fillPlayer(playerFirst, firstCards);
        fillPlayer(playerSecond, secondCards);
        playerFirst.print();
        int maxCard = max(playerFirst.findMax(), playerSecond.findMax());

        while (!playerFirst.isEmpty() && !playerSecond.isEmpty() && countStep != maxCountStep) {
            if (hideWinner(playerSecond, playerSecond, maxCard) == 1) {
                winner(playerFirst, playerSecond);
            }
            else {
                winner(playerSecond, playerFirst);
            }
            countStep++;
        }

        if (countStep == maxCountStep) {
            System.out.println("botva");
        }
        else if (playerFirst.isEmpty()) {
            System.out.println("second " + countStep);
        }
        else System.out.println("first " + countStep);
    }

    public static void fillPlayer(myStack player, String cards) {
        int element = 0;
        for (int i = 0; i < cards.length(); i++) {
            if (cards.charAt(i) != ' ') {
                element = element * 10 + Character.digit(cards.charAt(i), 10);
            }
            else {
                player.addLast(element);
                element = 0;
            }
        }
        player.addLast(element);
    }


    public static int hideWinner(myStack One, myStack Two, int maxCard) {
        if (One.getLast() == maxCard && Two.getLast() == 0) {
            return 2;
        }
        else if (One.getLast() == 0 && Two.getLast() == maxCard) {
            return 1;
        }
        else if (One.getLast() > Two.getLast()) {
            return 2;
        }
        else return 1;
    }

    public static void winner(myStack win, myStack lose) {
        lose.addFirst(lose.getLast());
        lose.addFirst(win.getLast());
        win.removeLast();
    }



    public static int max(int a, int b) {
        if (a > b) {
            return a;
        }
        else {
            return b;
        }
    }
}
