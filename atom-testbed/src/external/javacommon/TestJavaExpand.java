/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package external.javacommon;

/**
 *
@author atomix
 */
public class TestJavaExpand {

    public static void main(String[] args) {
        FPSGame game = new FPSGame();
        game.init();
        game.getGameManager().who();
        game.getGameManager().getWorld().who();
    }
}

class MainGame {

    protected GM gameManager;
    public MainGame() {
    }

    protected void init() {
        this.gameManager = new GM();
        this.gameManager.init();
    }

    protected void postInit() {
    }

    public GM getGameManager() {
        return gameManager;
    }

    public void setGameManager(GM gameManager) {
        this.gameManager = gameManager;
    }
    
}


class GM {
    String name;
    protected WM world;

    GM() {
    }

    public void init() {
        this.world = new WM();
        this.world.init();
    }

    protected void postInit() {
        this.name = "I'm Game Manager";
    }

    public void who() {
        System.out.println(name);
    }

    public void setWM(WM world) {
        this.world = world;
    }

    public WM getWorld() {
        return world;
    }
    
    
}

class WM {
    String name;
    WM() {
    }

    public void init() {
        this.postInit();
    }

    protected void postInit() {
        this.name = "I'm World Manager";
    }

    public void who() {
        System.out.println(name);
    }
}

// Extended classes


class FPSGame extends MainGame{

    @Override
    protected void init() {
        super.init();
        this.gameManager = new FPSGM();
        this.gameManager.init();
    }
    
}

class FPSGM extends GM{

    @Override
    public void init() {
        //super.init();
        this.world = new FPSWM();
        this.world.init();
    }
    
    
}
class FPSWM extends WM {


    @Override
    public void init() {
        super.init();
    }
    
    @Override
    protected void postInit() {
        //super.postInit();
        this.name = "I'm FPS WorldManager";

    }
}