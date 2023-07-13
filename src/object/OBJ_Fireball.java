package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {

	GamePanel gp;
	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Fireball";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/fireballup1", gp.tileSize, gp.tileSize*2);
		up2 = setup("/projectile/fireballup2", gp.tileSize, gp.tileSize*2);
		down1 = setup("/projectile/fireballdown1", gp.tileSize, gp.tileSize*2);
		down2 = setup("/projectile/fireballdown2", gp.tileSize, gp.tileSize*2);
		left1 = setup("/projectile/fireballleft1", gp.tileSize*2, gp.tileSize);
		left2 = setup("/projectile/fireballleft2", gp.tileSize*2, gp.tileSize);
		right1 = setup("/projectile/fireballright1", gp.tileSize*2, gp.tileSize);
		right2 = setup("/projectile/fireballright2", gp.tileSize*2, gp.tileSize);
	}
	
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.mana >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
	public void subtractResource(Entity user) {
		user.mana -= useCost;
	}
	
	public Color getParticleColor() {
		Color color = new Color(240,50,0);
		return color;
	}
	
	public int getParticleSize() {
		int size = 10;
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	
	public int getParticleMaxlife() {
		int maxLife = 20;
		return maxLife;
	}
}
