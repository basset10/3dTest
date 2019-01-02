import java.util.ArrayList;

public class Volume {
	
	public enum Face{
		TOP, BOTTOM, LEFT, RIGHT
	}

	public float height, x, y, xl, yl;
	public int textureIndex;

	public Volume(float heightArg, float xArg, float yArg, float xlArg, float ylArg, int textureIndexArg){
		height = heightArg;
		x = xArg;
		y = yArg;
		xl = xlArg;
		yl = ylArg;
		textureIndex = textureIndexArg;
	}
	
	public float getPadTop(float padArg){
		return y - (yl/2) - padArg;
	}
	
	public float getPadBtm(float padArg){
		return y + (yl/2) + padArg;
	}
	
	public float getPadLft(float padArg){
		return x - (xl/2) - padArg;
	}
	
	public float getPadRgt(float padArg){
		return x + (xl/2) + padArg;
	}

	public boolean isInLine(float xArg, float yArg){
		return xArg >= x - (xl/2) && xArg <= x + (xl/2) &&
				yArg >= y - (yl/2) && yArg <= y + (yl/2);
	}
	
	public boolean isInLinePad(float xArg, float yArg, float padArg){
		return xArg >= getPadLft(padArg) && xArg <= getPadRgt(padArg) &&
				yArg >= getPadTop(padArg) && yArg <= getPadBtm(padArg);
	}
	
	private boolean isInLineBiasV(float xArg, float yArg){
		return xArg > x - (xl/2) && xArg < x + (xl/2) &&
				yArg >= y - (yl/2) && yArg <= y + (yl/2);
	}
	
	private boolean isInLineBiasH(float xArg, float yArg){
		return xArg >= x - (xl/2) && xArg <= x + (xl/2) &&
				yArg > y - (yl/2) && yArg < y + (yl/2);
	}

	public boolean isBorderShared(ArrayList<Volume> volumesArg, Face faceArg){
		if(faceArg == Face.TOP){
			for(Volume v : volumesArg){
				if(v.isInLineBiasV(x + (xl/2), y - (yl/2)) ||
						v.isInLineBiasV(x - (xl/2), y - (yl/2)) ||
						v.isInLineBiasV(x, y - (yl/2)) ||
						isInLineBiasV(v.x + (v.xl/2), v.y + (v.yl/2)) ||
						isInLineBiasV(v.x - (v.xl/2), v.y + (v.yl/2)) ||
						isInLineBiasV(v.x, v.y + (v.yl/2))){
					return true;
				}
			}
			return false;
		}else if(faceArg == Face.BOTTOM){
			for(Volume v : volumesArg){
				if(v.isInLineBiasV(x + (xl/2), y + (yl/2)) ||
						v.isInLineBiasV(x - (xl/2), y + (yl/2)) ||
						v.isInLineBiasV(x, y + (yl/2)) ||
						isInLineBiasV(v.x + (v.xl/2), v.y - (v.yl/2)) ||
						isInLineBiasV(v.x - (v.xl/2), v.y - (v.yl/2)) ||
						isInLineBiasV(v.x, v.y - (v.yl/2))){
					return true;
				}
			}
			return false;
		}else if(faceArg == Face.LEFT){
			for(Volume v : volumesArg){
				if(v.isInLineBiasH(x - (xl/2), y + (yl/2)) ||
						v.isInLineBiasH(x - (xl/2), y - (yl/2)) ||
						v.isInLineBiasH(x - (xl/2), y) ||
						isInLineBiasH(v.x + (v.xl/2), v.y + (v.yl/2)) ||
						isInLineBiasH(v.x + (v.xl/2), v.y - (v.yl/2)) ||
						isInLineBiasH(v.x + (v.xl/2), v.y)){
					return true;
				}
			}
			return false;
		}else{
			for(Volume v : volumesArg){
				if(v.isInLineBiasH(x + (xl/2), y + (yl/2)) ||
						v.isInLineBiasH(x + (xl/2), y - (yl/2)) ||
						v.isInLineBiasH(x + (xl/2), y) ||
						isInLineBiasH(v.x - (v.xl/2), v.y + (v.yl/2)) ||
						isInLineBiasH(v.x - (v.xl/2), v.y - (v.yl/2)) ||
						isInLineBiasH(v.x - (v.xl/2), v.y)){
					return true;
				}
			}
			return false;
		}
	}

}
