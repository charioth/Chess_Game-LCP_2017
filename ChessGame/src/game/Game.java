package game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import display.Display;
import input.KeyManager;
import input.MouseManager;
import states.GameState;
import states.MenuState;
import states.State;
import tempassets.Assets;

public class Game implements Runnable {
	private final float SCREEN_SIZE = 1052;

	// Game Part
	private String name;
	public int width, height;
	public float scale;

	// Screen Part
	private Display display;
	private BufferStrategy bs;
	private Graphics graph;

	// State Part
	private GameState gameState;
	private MenuState menuState;

	// FPS Lock constant
	private final int fps = 60;
	private final double timerPerTick = 1000000000 / (double) fps;

	// Input
	private KeyManager keyboard;
	private MouseManager mouse;

	private Thread thread;
	private boolean running;

	public Game(String name, float scale) {
		if (scale <= 0)
			System.exit(0);
		this.name = name;
		width = (int) (SCREEN_SIZE * scale);
		height = (int) (SCREEN_SIZE * scale);
		this.scale = scale;
	}

	private void init() {
		// APAGAR ASSETS DEPOIS
		Assets.init();
		display = new Display(name, width, height);
		keyboard = new KeyManager();
		mouse = new MouseManager();
		gameState = new GameState(this);
		menuState = new MenuState(this);
		display.getFrame().addMouseListener(mouse);
		display.getFrame().addMouseMotionListener(mouse);
		display.getCanvas().addMouseListener(mouse);
		display.getCanvas().addMouseMotionListener(mouse);
		display.getFrame().addKeyListener(keyboard);
		State.setCurrentState(menuState);
	}

	private void tick() {
		keyboard.tick();

		if (State.getCurrentState() != null) {
			State.getCurrentState().tick();
		}
	}

	private void render() {
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
		}
		bs = display.getCanvas().getBufferStrategy();
		graph = bs.getDrawGraphics();
		graph.clearRect(0, 0, width, height);

		if (State.getCurrentState() != null) {
			State.getCurrentState().render(graph);
		}

		bs.show();
		graph.dispose();
	}

	@Override
	public void run() {
		double delta = 0.0;
		long now;
		long lastTime = System.nanoTime();

		init();

		while (running) {
			now = System.nanoTime();
			delta += (double) (now - lastTime) / timerPerTick;
			lastTime = now;
			if (delta >= 1) {
				delta--;
				this.tick();
				this.render();
			}
		}
	}

	public synchronized void start() {
		if (running == true)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (running == false)
			return;
		display.closeDisplay();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public GameState getGameState() {
		return gameState;
	}

	public MenuState getMenuState() {
		return menuState;
	}

	public MouseManager getMouse() {
		return mouse;
	}

	public KeyManager getKeyboard() {
		return keyboard;
	}
}
