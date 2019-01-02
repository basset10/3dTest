import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlCoord3;

public class Game {

	public static ArrayList<Volume> world;
	
//	public static LegacyFontPainter font;

	public static void initialize(){
		world = new ArrayList<>();
		world.add(new Volume(-50, 200, -100, 200, 200, Main.IDX_FLOOR_TECH));
		world.add(new Volume(-50, 400, -100, 200, 200, Main.IDX_FLOOR_TECH));
		world.add(new Volume(-100, 200, 100, 200, 200, Main.IDX_FLOOR_TECH2));
		world.add(new Volume(-100, 600, -200, 200, 200, Main.IDX_FLOOR_TECH));
		
		world.add(new Volume(-50, 600, 600, 100, 200, Main.IDX_FLOOR_TECH));
		
		world.add(new Volume(-150, 800, 200, 200, 200, Main.IDX_FLOOR_TECH2));
		world.add(new Volume(-300, 600, 200, 200, 200, Main.IDX_FLOOR_TECH));
		
		world.add(new Volume(-225, 700, 400, 50, 50, Main.IDX_FLOOR_TECH));

		for(float x = -1000; x < 1000; x += 200){
			for(float y = -1000; y < 1000; y += 200){
				world.add(new Volume(0, x, y, 200, 200, 
						Math.random() > 0.5 ? Main.IDX_FLOOR_ROCK : 
							Math.random() > 0.5 ? Main.IDX_FLOOR_ROCK2 : Main.IDX_FLOOR_TECH));
			}
		}
		
//		font = new LegacyFontPainter(hvlTexture(Main.IDX_FONT), LegacyFontPainter.Preset.FP_AGOFFICIAL);
		
		Player.initialize();
	}

	public static void update(float delta){
		rotate(new HvlCoord3(), Player.rot.x, new HvlCoord3(1f, 0f, 0f), () -> {
			rotate(new HvlCoord3(),  Player.rot.y, new HvlCoord3(0f, 1f, 0f), () -> {
				rotate(new HvlCoord3(),  Player.rot.z, new HvlCoord3(0f, 0f, 1f), () -> {
					translate(-Player.loc.x, -Player.loc.z + Player.VIEW_HEIGHT, -Player.loc.y, () -> {
						for(Volume c : world) Renderer.draw(c);
						
//						font.drawWordc("reeee", 0, -10, Color.white);
						
						Player.update(delta, world);
					});
				});
			});
		});
	}
	
	private static void rotate(HvlCoord3 coordArg, float degreesArg, HvlCoord3 axisArg, HvlAction.A0 actionArg){
		GL11.glPushMatrix();
		GL11.glTranslatef(coordArg.x, coordArg.y, coordArg.z);
		GL11.glRotatef(degreesArg, axisArg.x, axisArg.y, axisArg.z);
		GL11.glTranslatef(-coordArg.x, -coordArg.y, coordArg.z);
		actionArg.run();
		GL11.glPopMatrix();
	}

	private static void translate(float xArg, float yArg, float zArg, HvlAction.A0 actionArg){
		GL11.glPushMatrix();
		GL11.glTranslatef(xArg, yArg, zArg);
		actionArg.run();
		GL11.glTranslatef(-xArg, -yArg, -zArg);
		GL11.glPopMatrix();
	}

}
