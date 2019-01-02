import static com.osreboot.ridhvl2.HvlStatics.hvlTexture;
import static com.osreboot.ridhvl2.HvlStatics3.hvlDraw3;
import static com.osreboot.ridhvl2.HvlStatics3.hvlQuad3;
import static com.osreboot.ridhvl2.HvlStatics3.hvlQuad3c;

import com.osreboot.ridhvl2.HvlStatics3.HvlNormal3;

public class Renderer {

	public static final float
	BASE_WALL = 0f;
	
	public static void draw(Volume v){
		hvlDraw3(hvlQuad3c(v.x, v.height, v.y, v.xl, v.yl, HvlNormal3.AXIS_Y), hvlTexture(v.textureIndex));
		hvlDraw3(hvlQuad3(v.x - (v.xl/2), BASE_WALL, v.y + (v.yl/2), v.xl, v.height - BASE_WALL, HvlNormal3.AXIS_Z), hvlTexture(v.textureIndex));
		hvlDraw3(hvlQuad3(v.x - (v.xl/2), BASE_WALL, v.y - (v.yl/2), v.xl, v.height - BASE_WALL, HvlNormal3.AXIS_Z), hvlTexture(v.textureIndex));
		hvlDraw3(hvlQuad3(v.x + (v.xl/2), BASE_WALL, v.y - (v.yl/2), v.yl, v.height - BASE_WALL, HvlNormal3.AXIS_X), hvlTexture(v.textureIndex));
		hvlDraw3(hvlQuad3(v.x - (v.xl/2), BASE_WALL, v.y - (v.yl/2), v.yl, v.height - BASE_WALL, HvlNormal3.AXIS_X), hvlTexture(v.textureIndex));
	}
	
}
