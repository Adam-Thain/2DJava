package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Rock;

public class MON_Bat extends Entity{
    
    GamePanel gp;
	
	public MON_Bat(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_monster;
		name = "Bat";
		defaultSpeed = 4;
		speed = defaultSpeed;
		maxLife = 7;
		life = maxLife;
		attack = 7;
		defence = 0;
		exp = 7;
		
		solidArea.x = 3;
		solidArea.y = 15;
		solidArea.width = 42;
		solidArea.height = 21;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	public void getImage() {
		
		up1 = setup("/monster/batdown1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/batdown2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/batdown1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/batdown2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/batdown1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/batdown2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/batdown1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/batdown2", gp.tileSize, gp.tileSize);
	}
	public void setAction() {

		if(onPath == true){

			// // CHECK IF IT STOPS CHASING
			// checkStopChasingOrNot(gp.player, 15, 100);

			// // SEARCH THE DIRECTION TO GO
			// searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

			// // CHECK IF IT SHOOTS A PROJECTILE
			// checkShootOrNot(200,30);
		}
		else{

			// CHECK IF IT STARTS CHASING
			// checkStartChasingOrNot(gp.player, 5, 100);

			// GET A RANDOM DIRECTION
			getRandomDirection(10);
		}
	}
	public void damageReaction() {

		actionLockCounter = 0;
		// direction = gp.player.direction;
		onPath = true;
	}
	public void checkDrop() {}
}