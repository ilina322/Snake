import java.util.Random;
import java.util.Scanner;

public class Game {

	private static final char SNAKE_SYMBOL = 'o';
	private static final char APPLE_SYMBOL = '@';
	private static final char EMPTY_SYMBOL = ' ';
	private static final char OUTLINE_SYMBOL = '*';
	private static final long DELAY_IN_MILLIS = 500;
	private static int height = 10;
	private static int length = 25;
	private static char[][] plain;
	private static char apple = APPLE_SYMBOL;
	private static int snakeCurrX = 1;
	private static int snakeCurrY = 1;
	private static boolean isRight = true;
	private static boolean isDown = false;
	private static boolean isLeft = false;
	private static boolean isUp = false;
	private static boolean isRunning = true;
	private static int score = 0;
	private static boolean[][] appleBoard;

	public static void main(String[] args) {

		createGamePlain();
		generateApple();
		printGamePlain();
		moveSnakeRight();

	}

	private static void eatApple() {
		if (appleBoard[snakeCurrX][snakeCurrY] == true) {
			generateApple();
		}
	}

	private static void delay() {
		try {
			Thread.sleep(DELAY_IN_MILLIS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void moveSnake(){
		moveSnakeDown();
		moveSnakeLeft();
		moveSnakeRight();
		moveSnakeUp();
		eatApple();
	}

	private static void handleCommand(char command) {
		switch (command) {
		case 'w':
			isUp = true;
			isLeft = false;
			isRight = false;
			isDown = false;
			break;
		case 's':
			isUp = false;
			isLeft = false;
			isRight = false;
			isDown = true;
			break;
		case 'd':
			isUp = false;
			isLeft = false;
			isRight = true;
			isDown = false;
			break;
		case 'a':
			isUp = false;
			isLeft = true;
			isRight = false;
			isDown = false;
			break;
		}
		moveSnake();
	}

	private static void moveSnakeRight() {
		setKeyboardListener();
		while (isRight) {
			snakeCurrX++;
			printSnake(snakeCurrX, snakeCurrY);
			if (!isOnPlainBorder(snakeCurrX - 1, snakeCurrY)) {
				plain[snakeCurrX - 1][snakeCurrY] = EMPTY_SYMBOL;
				printGamePlain();
			}
		eatApple();
		}
	}

	private static void moveSnakeLeft() {
		setKeyboardListener();
		while (isLeft) {
			snakeCurrX--;
			printSnake(snakeCurrX, snakeCurrY);
			if (!isOnPlainBorder(snakeCurrX + 1, snakeCurrY)) {
				plain[snakeCurrX + 1][snakeCurrY] = EMPTY_SYMBOL;
			}
			printGamePlain();
		}
	}

	private static void printSnake(int x, int y) {
		plain[x][y] = SNAKE_SYMBOL;
	}

	private static void moveSnakeUp() {
		setKeyboardListener();
		while (isUp) {
			snakeCurrY--;
			printSnake(snakeCurrX, snakeCurrY);
			if (!isOnPlainBorder(snakeCurrX, snakeCurrY + 1)) {
				plain[snakeCurrX][snakeCurrY + 1] = EMPTY_SYMBOL;
			}
			printGamePlain();
		}
	}

	private static void moveSnakeDown() {
		setKeyboardListener();
		while (isDown) {
			snakeCurrY++;
			printSnake(snakeCurrX, snakeCurrY);
			if (!isOnPlainBorder(snakeCurrX, snakeCurrY - 1)) {
				plain[snakeCurrX][snakeCurrY - 1] = EMPTY_SYMBOL;
			}
			printGamePlain();
		}
	}

	private static void generateApple() {
		Random rand = new Random();
		appleBoard = new boolean[length][height];
		int x = rand.nextInt(length);
		int y = rand.nextInt(height);
		while (isOnPlainBorder(x, y)) {
			x = rand.nextInt(length);
			y = rand.nextInt(height);
		}
		plain[x][y] = APPLE_SYMBOL;
		appleBoard[x][y] = true;

	}

	private static boolean isOnPlainBorder(int x, int y) {
		if (y == 0 || y == height - 1 || x == 0 || x == length - 1) {
			return true;
		}
		return false;
	}

	private static void printGamePlain() {
		clearConsole();
		System.out.println();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < length; x++) {
				{
					System.out.print(plain[x][y]);
					printSnake(snakeCurrX, snakeCurrY);
				}
			}
			System.out.println();
		}
		delay();
	}

	private static void createGamePlain() {
		plain = new char[length][height];
		for (int x = 0; x < length; x++) {
			for (int y = 0; y < height; y++) {
				if (isOnPlainBorder(x, y)) {
					plain[x][y] = OUTLINE_SYMBOL;
				} else {
					plain[x][y] = EMPTY_SYMBOL;
				}
			}
		}

	}

	private static void clearConsole() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void setKeyboardListener() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Scanner scanner = new Scanner(System.in);
				while (isRunning) {
					char consoleReader = scanner.nextLine().charAt(0);
					handleCommand(consoleReader);
					handleKeyPressed(consoleReader);
				}
				scanner.close();
			}
		}).start();
	}

	private static void handleKeyPressed(char keyCode) {
		System.out.println("Key pressed:" + keyCode);
	}

}
