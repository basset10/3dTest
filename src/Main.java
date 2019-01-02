import static com.osreboot.ridhvl2.HvlStatics.hvlLoad;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.osreboot.ridhvl2.HvlPainter3;
import com.osreboot.ridhvl2.painter.HvlPainter;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlDisplayWindowed;
import com.osreboot.ridhvl2.template.HvlTemplateI;

public class Main extends HvlTemplateI{

	public static final double 
	WINDOW_WIDTH = 1280,
	WINDOW_HEIGHT = 720,
	FOV_OFFSET = 0.3;

	public static void main(String[] args){
		HvlChronology.registerChronology(HvlPainter3.class);
		HvlPainter3.setInitializeFrustumParams(-(WINDOW_WIDTH/WINDOW_HEIGHT)/2, (WINDOW_WIDTH/WINDOW_HEIGHT)/2, 0.5, -0.5, 1 - FOV_OFFSET, 10000);
		new Main();
	}

	public static final int 
	IDX_FLOOR_TECH = 0,
	IDX_FLOOR_TECH2 = 1,
	IDX_FLOOR_ROCK = 2,
	IDX_FLOOR_ROCK2 = 3,
	IDX_FONT = 4;

	public Main(){
		super(new HvlDisplayWindowed(144, (int)WINDOW_WIDTH, (int)WINDOW_HEIGHT, "Ridhvl23D - Test FPS", false), Long.MAX_VALUE - HvlPainter.LAUNCH_CODE_RAW, Long.MAX_VALUE - 1);
	}

	@Override
	public void initialize(){
		hvlLoad("Floor_Tech.png");
		hvlLoad("Floor_Tech2.png");
		hvlLoad("Floor_Rock.png");
		hvlLoad("Floor_Rock2.png");
		hvlLoad("Font.png");

		Game.initialize();

		Mouse.setGrabbed(true);
	}

	@Override
	public void update(float delta){
		Game.update(delta);

		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) setExiting();
	}

}
