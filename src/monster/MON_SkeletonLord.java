package monster;

import entity.Entity;
import main.GamePanel;

public class MON_SkeletonLord extends Entity{
    
    GamePanel gp;
    public static final String monName = "Skeleton Lord";
	
	public MON_SkeletonLord(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_monster;
        boss = true;
		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 50;
		life = maxLife;
		attack = 10;
		defence = 2;
		exp = 50;
		knockBackPower = 5;
		
        int size = gp.tileSize*5; 
		solidArea.x = 48;
		solidArea.y = 48;
		solidArea.width = size - 48 * 2;
		solidArea.height = size - 48;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
        attackArea.height = 170;
        attackArea.width = 170;
		motion1Duration = 25;
		motion2Duration = 50;
		
		getImage();
        getAttackImage();
	}
	public void getImage() {
		
        int i = 5;

        if(inRage == false){
            up1 = setup("/monster/skeletonlordup1", gp.tileSize*i, gp.tileSize*i);
            up2 = setup("/monster/skeletonlordup2", gp.tileSize*i, gp.tileSize*i);
            down1 = setup("/monster/skeletonlorddown1", gp.tileSize*i, gp.tileSize*i);
            down2 = setup("/monster/skeletonlorddown2", gp.tileSize*i, gp.tileSize*i);
            left1 = setup("/monster/skeletonlordleft1", gp.tileSize*i, gp.tileSize*i);
            left2 = setup("/monster/skeletonlordleft2", gp.tileSize*i, gp.tileSize*i);
            right1 = setup("/monster/skeletonlordright1", gp.tileSize*i, gp.tileSize*i);
            right2 = setup("/monster/skeletonlordright2", gp.tileSize*i, gp.tileSize*i);
        }
        if(inRage == true){
            up1 = setup("/monster/skeletonlordphase2up1", gp.tileSize*i, gp.tileSize*i);
            up2 = setup("/monster/skeletonlordphase2up2", gp.tileSize*i, gp.tileSize*i);
            down1 = setup("/monster/skeletonlordphase2down1", gp.tileSize*i, gp.tileSize*i);
            down2 = setup("/monster/skeletonlordphase2down2", gp.tileSize*i, gp.tileSize*i);
            left1 = setup("/monster/skeletonlordphase2left1", gp.tileSize*i, gp.tileSize*i);
            left2 = setup("/monster/skeletonlordphase2left2", gp.tileSize*i, gp.tileSize*i);
            right1 = setup("/monster/skeletonlordphase2right1", gp.tileSize*i, gp.tileSize*i);
            right2 = setup("/monster/skeletonlordphase2right2", gp.tileSize*i, gp.tileSize*i);
        }
	}
    public void getAttackImage() {

        int i = 5;
	
        if(inRage == false){
            attackUp1 = setup("/monster/skeletonlordattackup1", gp.tileSize*i, gp.tileSize*i*2);
            attackUp2 = setup("/monster/skeletonlordattackup2", gp.tileSize*i, gp.tileSize*i*2);
            attackDown1 = setup("/monster/skeletonlordattackdown1", gp.tileSize*i, gp.tileSize*i*2);
            attackDown2 = setup("/monster/skeletonlordattackdown2", gp.tileSize*i, gp.tileSize*i*2);
            attackLeft1 = setup("/monster/skeletonlordattackleft1", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft2 = setup("/monster/skeletonlordattackleft2", gp.tileSize*i*2, gp.tileSize*i);
            attackRight1 = setup("/monster/skeletonlordattackright1", gp.tileSize*i*2, gp.tileSize*i);
            attackRight2 = setup("/monster/skeletonlordattackright2", gp.tileSize*i*2, gp.tileSize*i);
        }
        if(inRage == true){
            attackUp1 = setup("/monster/skeletonlordphase2attackup1", gp.tileSize*i, gp.tileSize*i*2);
            attackUp2 = setup("/monster/skeletonlordphase2attackup2", gp.tileSize*i, gp.tileSize*i*2);
            attackDown1 = setup("/monster/skeletonlordphase2attackdown1", gp.tileSize*i, gp.tileSize*i*2);
            attackDown2 = setup("/monster/skeletonlordphase2attackdown2", gp.tileSize*i, gp.tileSize*i*2);
            attackLeft1 = setup("/monster/skeletonlordphase2attackleft1", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft2 = setup("/monster/skeletonlordphase2attackleft2", gp.tileSize*i*2, gp.tileSize*i);
            attackRight1 = setup("/monster/skeletonlordphase2attackright1", gp.tileSize*i*2, gp.tileSize*i);
            attackRight2 = setup("/monster/skeletonlordphase2attackright2", gp.tileSize*i*2, gp.tileSize*i);
        }
	}
	public void setAction() {

        if(inRage == false && life < maxLife/2){
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack *= 2;
        }

		if(getTileDistance(gp.player) < 10){
            moveTowardsPlayer(60);
		}
		else{
			// GET A RANDOM DIRECTION
			getRandomDirection(120);
		}

		//  CHECK IF IT ATTACKS
		if(attacking == false){
			checkAttackOrNot(60, gp.tileSize*7, gp.tileSize*5);
		}
	}
	public void damageReaction() {

		actionLockCounter = 0;
		// direction = gp.player.direction;
		onPath = true;
	}
	public void checkDrop() {}
}