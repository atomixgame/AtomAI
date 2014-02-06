package sg.atom.core.testbed.cine;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import java.util.concurrent.Callable;
import sg.atom.core.testbed.simple.SimpleLevelGame;

public class CineTestApp extends SimpleLevelGame {

    public static void main(String[] args) {
        CineTestApp app = new CineTestApp();
        //app.startSilent();
    }
    private BitmapText talkLine;

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        createOnScreenText();
    }

    void createOnScreenText() {

        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        talkLine = new BitmapText(guiFont, false);
        talkLine.setSize(guiFont.getCharSet().getRenderedSize());
        talkLine.setText("Hello World");
        talkLine.setLocalTranslation(300, talkLine.getLineHeight() + 40, 0);
        guiNode.attachChild(talkLine);

    }

    void setOnScreenText(final String text, final int passed) {
        enqueue(new Callable<Void>() {
            public Void call() throws Exception {
                talkLine.setText(text.substring(0, passed));
                return null;
            }
        });

    }

    private void loadText(BitmapText txt, String text, BitmapFont font, float x, float y, float z) {
        txt.setSize(font.getCharSet().getRenderedSize());
        txt.setLocalTranslation(txt.getLineWidth() * x, txt.getLineHeight() * y, z);
        txt.setText(text);
        guiNode.attachChild(txt);
    }
}
