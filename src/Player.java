import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.osreboot.ridhvl2.HvlCoord;
import com.osreboot.ridhvl2.HvlCoord3;
import com.osreboot.ridhvl2.LegacyMatrix;

public class Player {

	public static final float 
	GRAVITY = 200f,
	COLLISION_RADIUS = 10f,
	MOVESPEED = 200f,
	LOOKSPEED = 0.1f,
	JUMPSPEED = 200f,
	VIEW_HEIGHT = 32f;

	public static HvlCoord3 loc, rot, vel;

	private static LegacyMatrix moveMatrix;

	private static boolean hasJumped = false;

	public static void initialize(){
		loc = new HvlCoord3(0, 0, -33f);
		rot = new HvlCoord3();
		vel = new HvlCoord3();
	}

	public static void update(float delta, ArrayList<Volume> worldArg){

		//Input Handling

		rot.y += Mouse.getDX() * LOOKSPEED;
		rot.x += Mouse.getDY() * LOOKSPEED;

		moveMatrix = LegacyMatrix.createRotationMatrix((float)Math.toRadians(rot.y));

		HvlCoord input = new HvlCoord();

		if(Keyboard.isKeyDown(Keyboard.KEY_A)) input.x -= MOVESPEED;
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) input.x += MOVESPEED;
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) input.y -= MOVESPEED;
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) input.y += MOVESPEED;

		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			if(!hasJumped){
				hasJumped = true;
				vel.z = JUMPSPEED;
			}
		}

		vel.x = moveMatrix.map(input).x;
		vel.y = moveMatrix.map(input).y;

		vel.z -= GRAVITY * delta;

		//Collision Handling

		float newXPos = loc.x + (vel.x * delta);
		float newYPos = loc.y + (vel.y * delta);

		for(Volume v : worldArg){
			
			//checking if the player will collide with this particular volume
			if(v.isInLinePad(newXPos, newYPos, COLLISION_RADIUS)){
				if(loc.z <= v.height){
					if(loc.z - (vel.z * delta) > v.height){
						vel.z = Math.max(vel.z, 0f);
						loc.z = v.height;
						hasJumped = false;
					}
				}else{

					ArrayList<Volume> vs = new ArrayList<>(worldArg);
					vs.remove(v);
					
					ArrayList<Volume> vsTR = new ArrayList<>();
					for(Volume v2 : vs){
						if(loc.z <= v2.height) vsTR.add(v2);
					}
					for(Volume v2 : vsTR) vs.remove(v2);

					//variables for the volume's proximity to other volumes
					boolean vTop = v.isBorderShared(vs, Volume.Face.TOP);
					boolean vBtm = v.isBorderShared(vs, Volume.Face.BOTTOM);
					boolean vLft = v.isBorderShared(vs, Volume.Face.LEFT);
					boolean vRgt = v.isBorderShared(vs, Volume.Face.RIGHT);

					//variables for the player's proximity to the volume
					boolean pTop = loc.y <= v.getPadTop(COLLISION_RADIUS);
					boolean pBtm = loc.y >= v.getPadBtm(COLLISION_RADIUS);
					boolean pLft = loc.x <= v.getPadLft(COLLISION_RADIUS);
					boolean pRgt = loc.x >= v.getPadRgt(COLLISION_RADIUS);

					//handling face collisions
					if(pRgt && !pLft && !pBtm && !pTop){//FLAT RIGHT COLLISION
						vel.x = Math.max(0, vel.x);
						newXPos = v.getPadRgt(COLLISION_RADIUS);
					}else if(!pRgt && pLft && !pBtm && !pTop){//FLAT LEFT COLLISION
						vel.x = Math.min(0, vel.x);
						newXPos = v.getPadLft(COLLISION_RADIUS);
					}else if(!pRgt && !pLft && pBtm && !pTop){//FLAT BOTTOM COLLISION
						vel.y = Math.max(0, vel.y);
						newYPos = v.getPadBtm(COLLISION_RADIUS);
					}else if(!pRgt && !pLft && !pBtm && pTop){//FLAT TOP COLLISION
						vel.y = Math.min(0, vel.y);
						newYPos = v.getPadTop(COLLISION_RADIUS);
					}

					//handling corner collisions
					if(pRgt && pTop){
						if(vRgt){
							vel.y = Math.min(0, vel.y);
							newYPos = v.getPadTop(COLLISION_RADIUS);
						}
						if(vTop){
							vel.x = Math.max(0, vel.x);
							newXPos = v.getPadRgt(COLLISION_RADIUS);
						}
						if(!vRgt && !vTop){
							vel.y = Math.min(0, vel.y);
							newYPos = v.getPadTop(COLLISION_RADIUS);
						}
					}
					if(pRgt && pBtm){
						if(vRgt){
							vel.y = Math.max(0, vel.y);
							newYPos = v.getPadBtm(COLLISION_RADIUS);
						}
						if(vBtm){
							vel.x = Math.max(0, vel.x);
							newXPos = v.getPadRgt(COLLISION_RADIUS);
						}
						if(!vRgt && !vBtm){
							vel.y = Math.max(0, vel.y);
							newYPos = v.getPadBtm(COLLISION_RADIUS);
						}
					}
					if(pLft && pTop){
						if(vLft){
							vel.y = Math.min(0, vel.y);
							newYPos = v.getPadTop(COLLISION_RADIUS);
						}
						if(vTop){
							vel.x = Math.min(0, vel.x);
							newXPos = v.getPadLft(COLLISION_RADIUS);
						}
						if(!vLft && !vTop){
							vel.y = Math.min(0, vel.y);
							newYPos = v.getPadTop(COLLISION_RADIUS);
						}
					}
					if(pLft && pBtm){
						if(vLft){
							vel.y = Math.max(0, vel.y);
							newYPos = v.getPadBtm(COLLISION_RADIUS);
						}
						if(vBtm){
							vel.x = Math.min(0, vel.x);
							newXPos = v.getPadLft(COLLISION_RADIUS);
						}
						if(!vLft && !vBtm){
							vel.y = Math.max(0, vel.y);
							newYPos = v.getPadBtm(COLLISION_RADIUS);
						}
					}
				}
			}
		}

		loc.x = newXPos;
		loc.y = newYPos;

		loc.z -= vel.z * delta;
	}

}
