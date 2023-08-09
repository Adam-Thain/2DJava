package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_Orc extends Entity {
    
    GamePanel gp;
	
	public MON_Orc(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_monster;
		name = "Orc";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 10;
		life = maxLife;
		attack = 8;
		defence = 2;
		exp = 10;
		knockBackPower = 5;
		
		solidArea.x = 4;
		solidArea.y = 4;
		solidArea.width = 40;
		solidArea.height = 44;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
        attackArea.height = 48;
        attackArea.width = 48;
		motion1Duration = 40;
		motion2Duration = 85;
		
		getImage();
        getAttackImage();
	}
	public void getImage() {
		
		up1 = setup("/monster/orcup1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/orcup2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/orcdown1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/orcdown2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/orcleft1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/orcleft2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/orcright1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/orcright2", gp.tileSize, gp.tileSize);
	}
    public void getAttackImage() {
		
        attackUp1 = setup("/monster/orcattackup1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/monster/orcattackup2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/monster/orcattackdown1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/monster/orcattackdown2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/monster/orcattackleft1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/monster/orcattackleft2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/monster/orcattackright1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/monster/orcattackright2", gp.tileSize*2, gp.tileSize);
	}
	public void setAction() {

		if(onPath == true){

			// CHECK IF IT STOPS CHASING
			checkStopChasingOrNot(gp.player, 15, 100);

			// SEARCH THE DIRECTION TO GO
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
		}
		else{

			// CHECK IF IT STARTS CHASING
			checkStartChasingOrNot(gp.player, 5, 100);

			// GET A RANDOM DIRECTION
			getRandomDirection();
		}

		//  CHECK IF IT ATTACKS
		if(attacking == false){
			checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
		}
	}
	public void damageReaction() {

		actionLockCounter = 0;
		// direction = gp.player.direction;
		onPath = true;
	}
	public void checkDrop() {
		
		// THROW A DICE
		int i = new Random().nextInt(100) + 1;
		
		// SET THE MONSTER DROP
		if(i < 50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if(i >= 50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if(i >= 75 && i < 100) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}
}