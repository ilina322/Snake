import java.util.Random;
import java.util.Scanner;

public class Game {

	private static final char SNAKE_SYMBOL = 'o';
	private static final char APPLE_SYMBOL = '@';
	private static final char EMPTY_SYMBOL = ' ';
	private static final char OUTLINE_SYMBOL = '*';
	private static final long DELAY_IN_MILLIS = 500;
	static Scanner consoleReader = new Scanner(System.in);
	private static int height = 10;
	private static int length = 25;
	private static char[][] plain;
	private static char snake;
	private static int snakeCurrX = 1;
	private static int snakeCurrY = 1;
	private static String CurrCommand = null;
	private static boolean isRight = true;
	private static boolean isDown = false;
	private static boolean isLeft = false;
	private static boolean isUp = false;
	private static boolean isRunning = true;

	public static void main(String[] args) {

		setKeyboardListener();
		createGamePlain();
		generateApple();
		printGamePlain();
		moveSnakeRight();
	
	}

	private static void delay() {
		try {
			Thread.sleep(DELAY_IN_MILLIS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	private static void handleCommand(char command){
		if(command == 'w'){
			isUp = true;
			isLeft = false;
			isRight = false;
			isDown = false;
			moveSnakeUp();
		}
		if(command == 's'){
			isUp = false;
			isLeft = false;
			isRight = false;
			isDown = true;
			moveSnakeDown();
		}
		if(command == 'd'){
			isUp = false;
			isLeft = false;
			isRight = true;
			isDown = false;
			moveSnakeRight();
		}
		if(command == 'a'){
			isUp = false;
			isLeft = true;
			isRight = false;
			isDown = false;
			moveSnakeLeft();
		}
	}
	
	private static void moveSnakeRight() {
		setKeyboardListener();
		while (isRight) {
			snakeCurrX++;
			printSnake(snakeCurrX, snakeCurrY);
			if (!isOnPlainBorder(snakeCurrX - 1, snakeCurrY)) {
				plain[snakeCurrX - 1][snakeCurrY] = EMPTY_SYMBOL;
			}
			printGamePlain();
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
		int x = rand.nextInt(length);
		int y = rand.nextInt(height);
		while (isOnPlainBorder(x, y)) {
			x = rand.nextInt(length);
			y = rand.nextInt(height);
		}
		plain[x][y] = APPLE_SYMBOL;

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
				while (isRunning) {
					String command = consoleReader.nextLine();
					if(command != null && command.length() > 0){
					char input = consoleReader.nextLine().charAt(0);
					handleKeyPressed(input);
					handleCommand(input);
					}
				}
				consoleReader.close();
			}
		}).start();
	}
	
	private static void handleKeyPressed(char keyCode) {
		System.out.println("Key pressed:" + keyCode);
	}

}
