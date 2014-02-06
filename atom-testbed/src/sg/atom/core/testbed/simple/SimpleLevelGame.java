/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.testbed.simple;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple RPG like application for testing purpose.
 * @author cuong.nguyenmanh2
 */
public class SimpleLevelGame extends SimpleApplication{
    
    public void startSilent() {
        AppSettings settings = new AppSettings(true);
        //settings.setFullscreen(true);
        //settings.setResolution(-1, -1); // current width/height
        settings.setResolution(1024, 768);
        setDisplayStatView(false);
        setShowSettings(false);
        setSettings(settings);
        start();
    }

    /**
     * Initializes game
     */
    @Override
    public void simpleInitApp() {
        Logger.getLogger("com.jme3").setLevel(Level.WARNING);
        //flyCam.setEnabled(false);
        setDisplayStatView(false);
        //setupInputKeys();
        flyCam.setMoveSpeed(10f);
        //rootNode.attachChild(player);
        createLevel();

    }
    
    void setupScriptEngine() {
        Logger.getLogger("ScriptEngine").info("Start game script engine");
        
    }
    
    public void setupSkyBox() {

        //Texture envMap;
        //envMap = assetManager.loadTexture("Textures/Sky/Bright/BrightSky.dds");
        Texture west, east, north, south, up, down;
        west = assetManager.loadTexture("Textures/Skybox/west.png");
        rootNode.attachChild(SkyFactory.createSky(assetManager, west, west, west, west, west, west, new Vector3f(1f, 1f, 1f)));
    }
    
    void createLevel() {
        
        createEarth();

        // load houses . trees . cars
        Node village = (Node) assetManager.loadModel("Scenes/Sim/Architecture/House/cartoon_village.j3o");
        //Node village = (Node) assetManager.loadModel("Scenes/Sim/Architecture/pixel_houses/Pixel houses.j3o");
        rootNode.attachChild(village);

        // init camera
        cam.setLocation(rootNode.getChild("Camera").getWorldTranslation());

        // some birds

        // cloud. skybox
        viewPort.setBackgroundColor(ColorRGBA.Blue);
        
        setupSkyBox();
        setupLight();
        
    }
    
    public void createEarth() {
        // load a sphere -> earth
        //Vector3f center = new Vector3f(0, -40, 0);
        //Sphere rock = new Sphere(32, 32, 2f);
        Mesh earthMesh = new Box(80, 0.01f, 80);
        
        Geometry earth = new Geometry("Shiny rock", earthMesh);
        //rock.setTextureMode(Sphere.TextureMode.Projected); // better quality on spheres
        //TangentBinormalGenerator.generate(rock);   // for lighting effect
        Material mat_grass = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat_grass.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Grass.png"));
        earth.setMaterial(mat_grass);
        earth.setShadowMode(RenderQueue.ShadowMode.Receive);
        //earth.scale(20f);
        //earth.setLocalTranslation(center);
        rootNode.attachChild(earth);
    }
    
    public void setupFog() {
        /**
         * Add fog to a scene
         */
        /*
         FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
         FogFilter fog = new FogFilter();
         fog.setFogColor(new ColorRGBA(0.9f, 0.9f, 0.9f, 1.0f));
         fog.setFogDistance(155);
         fog.setFogDensity(2.0f);
         fpp.addFilter(fog);
         viewPort.addProcessor(fpp);
         * 
         */
    }

    
    public void setupLight() {
        // light . sun
        /**
         * A white, spot light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);

        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        /**
         * Advanced shadows for uneven surfaces
         */
        PssmShadowRenderer pssm = new PssmShadowRenderer(assetManager, 1024, 3);
        pssm.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        viewPort.addProcessor(pssm);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        /*
        
         camTakeOver(tpf);
         if (START) {
         gameLogic(tpf);
         }
         colorLogic();
         * 
         */
    }

    /**
     * Forcefully takes over Camera adding functionality and placing it behind
     * the character
     *
     * @param tpf Tickes Per Frame
     */
    /**
     * Sets up the keyboard bindings
     */
    private void setupInputKeys() {
        inputManager.addMapping("START", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        //inputManager.addListener(this, "START", "Left", "Right");
    }
}
