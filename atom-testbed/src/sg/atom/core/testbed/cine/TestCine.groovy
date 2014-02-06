package sg.atom.core.testbed.cine
/**
 *
 * @author cuong.nguyenmanh2
 */

import com.jme3.system.AppSettings
import java.util.concurrent.Callable
import com.jme3.asset.AssetManager
import com.jme3.math.*
import com.jme3.app.SimpleApplication
import sg.atom.cinematic.*
import sg.atom.cinematic.stage.sound.CineSoundManager
import sg.atom.core.testbed.cine.CineTestApp

CineTestApp app = new CineTestApp();
app.startSilent();
app.enqueue{
    /*
    soundMng = new CineSoundManager(app);
    def bgMusic = soundMng.loadMusic("Sounds/Music/love.ogg")
    soundMng.play(bgMusic)
     */
    
    CinematicSystem cs = new CinematicSystem(app);
    //app.stateManager.attach(cs);
    cs.build("SceneScript2.groovy")
    cs.play()
}