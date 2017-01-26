import java.util.Random;
import java.util.Scanner;

import javax.print.attribute.SetOfIntegerSyntax;

public class Game {

	private static final char SNAKE_SYMBOL = 'o';
	private static final char APPLE_SYMBOL = '@';
	private static final char EMPTY_SYMBOL = ' ';
	private static final char OUTLINE_SYMBOL = '*';
	private static final long DELAY_IN_MILLIS = 500;
	private static final int MAX_SCORE = 3;
	private static int height = 10;
	private static int length = 25;
	private static char[][] plain;
	private static int snakeCurrX = 1;
	private static int snakeCurrY = 1;
	private static boolean isRight = true;
	private static boolean isDown = false;
	private static boolean isLeft = false;
	private static boolean isUp = false;
	private static boolean isRunning = true;
	private static boolean[][] appleLayer;
	private static int score = 0;
	private static boolean[][] snakeLayer;

	public static void main(String[] args) {

		setKeyboardListener();
		createGamePlain();
		generateApple();
		printGamePlain();
		moveSnake();

	}

	private static void eatApple() {
		if (appleLayer[snakeCurrX][snakeCurrY] == true) {
			generateApple();
			score++;
		}
	}

	private static void delay() {
		try {
			Thread.sleep(DELAY_IN_MILLIS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void changeSnakeCoordinates() {
		if (isDown) {
			snakeCurrY++;
		} else if (isUp) {
			snakeCurrY--;
		} else if (isRight) {
			snakeCurrX++;
		} else {
			snakeCurrX--;
		}
	}

	private static void moveSnake() {
		while (isRunning) {
			changeSnakeCoordinates();
			putSnakeOnGamePlain(snakeCurrX, snakeCurrY);
			printGamePlain();
			eatApple();
			endGame();
		}
	}

	private static void clearSnake(){
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < length; x++) {
				if(!snakeLayer[x][y] && !appleLayer[x][y] && !isOnPlainBorder(x, y)){
					plain[x][y] = EMPTY_SYMBOL;
				}
			}
			System.out.println();
		}
	}
	private static void changeSnakeDirection(char command) {
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
	}

	private static void putSnakeOnGamePlain(int x, int y) {
		snakeLayer = new boolean[length][height];
		snakeLayer[x][y] = true;
		plain[x][y] = SNAKE_SYMBOL;	
	}
	
	private static void endGame() {
		if(isOnPlainBorder(snakeCurrX, snakeCurrY) && snakeLayer[snakeCurrX][snakeCurrY]){
			isRunning = false;
			System.out.println("Game Over");
		}
		if(score == MAX_SCORE){
			isRunning = false;
			System.out.println("You win");
		}
	}

	private static void generateApple() {
		Random rand = new Random();
		appleLayer = new boolean[length][height];
		int x = rand.nextInt(length);
		int y = rand.nextInt(height);
		while (isOnPlainBorder(x, y)) {
			x = rand.nextInt(length);
			y = rand.nextInt(height);
		}
		plain[x][y] = APPLE_SYMBOL;
		appleLayer[x][y] = true;

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
					System.out.print(plain[x][y]);
			}
			System.out.println();
		}
		delay();
		clearSnake();
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
		putSnakeOnGamePlain(snakeCurrX, snakeCurrY);
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
					changeSnakeDirection(consoleReader);
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