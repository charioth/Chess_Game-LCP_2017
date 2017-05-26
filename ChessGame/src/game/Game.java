package game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import display.Display;
import input.KeyManager;
import states.GameState;
import states.MenuState;
import states.State;
import tempassets.Assets;

public class Game implements Runnable{
	private final float SCREEN_SIZE = 1052;
	
	//Game Part
	private String mName;
	public int mWidth, mHeight;
	public float mScale;
	
	//Screen Part
	private Display mDisplay;
	private BufferStrategy mBs;
	private Graphics mGraph;
	
	//State Part
	private GameState mGameState;
	private MenuState mMenuState;

	//FPS Lock constant
	private final int mFps = 60;
	private final double mTimerPerTick = 1000000000 / (double) mFps;

	//Input
	public KeyManager mKeyboard;
	
	private Thread mThread;
	private boolean mRunning;
	
	public Game(String name, float scale)
	{
		if(scale <= 0)
			System.exit(0);
		mName = name;
		mWidth = (int) (SCREEN_SIZE*scale);
		mHeight = (int) (SCREEN_SIZE*scale);
		mScale = scale;
	}

	private void init()
	{
		//APAGAR ASSETS DEPOIS
		Assets.init();
		mDisplay = new Display(mName, mWidth, mHeight);
		mGameState = new GameState(this);
		mMenuState = new MenuState(this);
		mKeyboard = new KeyManager();
		mDisplay.getFrame().addKeyListener(mKeyboard);
		State.setCurrentState(mGameState);
	}
	
	private void tick()
	{
		mKeyboard.tick();
		
		if(State.getCurrentState() != null)
		{
			State.getCurrentState().tick();
		}
	}
	
	private void render()
	{
		if(mBs == null)
		{
			mDisplay.getCanvas().createBufferStrategy(3);
		}
		mBs = mDisplay.getCanvas().getBufferStrategy();
		mGraph = mBs.getDrawGraphics();
		mGraph.clearRect(0, 0, mWidth, mHeight);

		if(State.getCurrentState() != null)
		{
			State.getCurrentState().render(mGraph);
		}
		
		mBs.show();
		mGraph.dispose();
	}
	
	@Override
	public void run() {
		double delta = 0.0;
		long now;
		long lastTime = System.nanoTime();
		
		init();
		
		while(mRunning)
		{
			now = System.nanoTime();
			delta += (double) (now - lastTime) /  mTimerPerTick;
			lastTime = now;
			if(delta >= 1)
			{
				delta--;
				this.tick();
				this.render();
			}
		}
	}
	
	public synchronized void start()
	{
		if(mRunning == true)
			return;
		mRunning = true;
		mThread = new Thread(this);
		mThread.start();
	}
	
	public synchronized void stop()
	{
		if(mRunning == false)
			return;
		mDisplay.closeDisplay();
		try {
			mThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
