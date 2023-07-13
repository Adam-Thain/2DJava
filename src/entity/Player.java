package entity;

import main.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity {
	
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	int standCounter = 0;
	public boolean attackCancelled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	
	public Player(GamePanel gp,KeyHandler KeyH) {
		
		super(gp);
		
		this.gp = gp;
		this.keyH = KeyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		// SOLID AREA
		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		// ATTACK AREA
		attackArea.width = 36;
		attackArea.height = 36;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}
	public void setDefaultValues() {

		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
		
		// PLAYER STATUS
		level = 1;
		maxLife = 6;
		life = maxLife;
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		strength = 1; // The more strength you have, the more damage you give.
		dexterity = 1; // The more dexterity you have, the less damage you receive.
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_Fireball(gp);
		attack = getAttack(); // The total attack value is decided by strength and weapon.
		defence = getDefence(); // The total defence value is decided by dexterity and shield.
	}
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
	}
	public int getAttack() {
		return attack = strength * currentWeapon.attackValue;
	}
	public int getDefence() {
		return defence = strength * currentShield.defenceValue;
	}
	public void getPlayerImage() {
				
		up1 = setup("/player/boyup1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boyup2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/boydown1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boydown2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/boyleft1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boyleft2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/boyright1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boyright2", gp.tileSize, gp.tileSize);
	}
	public void getPlayerAttackImage() {
		
		if(currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/boyattackup1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boyattackup2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boyattackdown1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boyattackdown2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boyattackleft1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boyattackleft2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boyattackright1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boyattackright2", gp.tileSize*2, gp.tileSize);
		}
		if(currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/boyaxeup1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boyaxeup2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boyaxedown1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boyaxedown2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boyaxeleft1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boyaxeleft2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boyaxeright1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boyaxeright2", gp.tileSize*2, gp.tileSize);
		}
		
	}
	public void update() {

		if(attacking == true) {
			attacking();
		}		
		else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) 
		{		
			if(keyH.upPressed == true) {
				direction = "up";
			}
			else if(keyH.downPressed == true) {
				direction = "down";
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
			}	
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpobject(objIndex);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.CheckEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.CheckEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// CHECK INTERACTIVE TILE COLLISION
			int iTileIndex = gp.cChecker.CheckEntity(this, gp.iTile);
					
			// CHECK EVENT
			gp.eHandler.checkEvent();
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false && keyH.enterPressed == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			if(keyH.enterPressed == true && attackCancelled == false) {
				gp.playSE(7);
				attacking = true;
				spriteCounter = 0;
			}
			
			attackCancelled = false;
			gp.keyH.enterPressed = false;
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		else {
			standCounter++;
			if(standCounter == 20) {
				spriteNum = 1;
				standCounter = 0;
			}
		}
		
		if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvaliableCounter == 30 && projectile.haveResource(this) == true) {
			
			// SET DEFAULT COORDINATES, DIRECTION AND USER
			projectile.set(worldX, worldY, direction, true, this);
			
			projectile.subtractResource(this);
			
			// ADD IT TO THE LIST
			gp.projectileList.add(projectile);
			
			shotAvaliableCounter = 0;
			
			gp.playSE(10);
		}
		
		// THIS NEEDS TO BE OUTSIDE OF KEY IF STATEMENT!
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		if(shotAvaliableCounter < 30) {
			shotAvaliableCounter++;
		}
		
		if(life > maxLife) {
			life = maxLife;
		}
		
		if(mana > maxMana) {
			mana = maxMana;
		}
	}
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5  && spriteCounter <= 25) {
			spriteNum = 2;
			
			// SAVE THE CURRENT WORLDX, WORLDY AND SOLIDAREA
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			// ADJUST PLAYER WORLDX/WORLDY FOR THE ATTACKAREA
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			
			// ATTACKAREA BECOMES SOLIDAREA
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			// CHECK MONSTER COLLISION WITH THE UPDATED WORLDX, WORLDY AND SOLIDAREA
			int monsterIndex = gp.cChecker.CheckEntity(this, gp.monster);
			damageMonster(monsterIndex, attack);
			
			int iTileIndex = gp.cChecker.CheckEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);
			
			// AFTER CHECKING COLLISION, RESTORE THE ORIGINAL DATA
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	public void pickUpobject(int i) {
		
		if(i != 999) {
			
			// PICKUP ONLY ITEMS
			if(gp.obj[i].type == type_pickupOnly) {
				gp.obj[i].use(this);
				gp.obj[i] = null;
			}		
			
			// INVENTORY ITEMS
			else {
				String text;
				
				if(inventory.size() != maxInventorySize) {
					inventory.add(gp.obj[i]);
					gp.playSE(1);
					text = "Got a " + gp.obj[i].name + "!";
				}
				else 
				{
					text = "You cannot carry any more!";
				}
				gp.ui.addMessage(text);
				gp.obj[i] = null;
			}
		}
	}
	public void interactNPC(int i) {
		
		if(gp.keyH.enterPressed == true) {
			
			if(i != 999) {	
				attackCancelled = true;
				gp.gameState = gp.dialogState;
				gp.npc[i].speak();
			}
		}
	}
	public void contactMonster(int i) {
		
		if(i != 999) {
			if(invincible == false && gp.monster[i].dying == false) {
				
				gp.playSE(6);
				
				int damage = gp.monster[i].attack = defence;
				if(damage < 0) {
					damage = 0;
				}
				
				life -= damage;
				invincible = true;
			}
		}
	}
	public void damageMonster(int i, int attack) {
		
		if(i != 999) {
			if(gp.monster[i].invincible == false) {
				
				gp.playSE(5);
				
				int damage = attack - gp.monster[i].defence;
				if(damage < 0) {
					damage = 0;
				}
				
				gp.monster[i].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
				if(gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
					gp.ui.addMessage("killed the " + gp.monster[i].name + "!");
					gp.ui.addMessage("Exp + " + gp.monster[i].exp);
					exp += gp.monster[i].exp;
					checkLevelUp();
				}
			}
		}
	}
	public void damageInteractiveTile(int i) {
		if(i != 999 && gp.iTile[i].destructible == true && gp.iTile[i].isCorrectItem(this) == true && gp.iTile[i].invincible == false) 
		{
			gp.iTile[i].playSE();
			gp.iTile[i].life--;
			gp.iTile[i].invincible = true;
		
			generateParticle(gp.iTile[i],gp.iTile[i]);
			
			if(gp.iTile[i].life == 0) {
				gp.iTile[i] = gp.iTile[i].getDestroyedForm();
			}
		}
	}
	public void checkLevelUp() {
		if(exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp*2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defence = getDefence();
			
			gp.playSE(8);
			gp.gameState = gp.dialogState;
			gp.ui.currentDialogue = "You are level " + level + " now!\n"
					+ "You feel stronger!";
		}
	}
	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if(selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defence = getDefence();
			}
			if(selectedItem.type == type_consumable) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
		}
	}
	public void draw(Graphics2D g2) {
			
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) image = up1;
				if(spriteNum == 2) image = up2;
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) image = attackUp1;
				if(spriteNum == 2) image = attackUp2;
			}
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) image = down1;
				if(spriteNum == 2) image = down2;
			}
			if(attacking == true) {
				if(spriteNum == 1) image = attackDown1;
				if(spriteNum == 2) image = attackDown2;
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) image = left1;
				if(spriteNum == 2) image = left2;
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) image = attackLeft1;
				if(spriteNum == 2) image = attackLeft2;
			}
			break;
		case "right":
			if(attacking == false) {
				if(spriteNum == 1) image = right1;
				if(spriteNum == 2) image = right2;
			}
			if(attacking == true) {
				if(spriteNum == 1) image = attackRight1;
				if(spriteNum == 2) image = attackRight2;
			}
			break;
		}
		
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);	
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}