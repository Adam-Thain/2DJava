package entity;

import main.GamePanel;

public class PlayerDummy extends Entity{

    public static final String npcName = "Dummy";

    public PlayerDummy(GamePanel gp){
        super(gp);

        name = npcName;
        getImage();
    }
    public void getImage(){
        up1 = setup("/player/boyup1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boyup2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/boydown1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boydown2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/boyleft1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boyleft2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/boyright1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boyright2", gp.tileSize, gp.tileSize);
    }
}
