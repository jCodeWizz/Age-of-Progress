package dev.codewizz.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kotcrab.vis.ui.VisUI;
import dev.codewizz.gfx.Renderer;
import dev.codewizz.gfx.gui.layers.MainMenuLayer;
import dev.codewizz.input.KeyInput;
import dev.codewizz.input.MouseInput;
import dev.codewizz.input.console.Console;
import dev.codewizz.modding.ModHandler;
import dev.codewizz.networking.Client;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.Tiles;
import dev.codewizz.world.World;
import dev.codewizz.world.objects.GameObjects;

public class Main extends ApplicationAdapter {

    public static boolean DEBUG = false;
    public static boolean PLAYING = false;
    public static boolean PAUSED = false;
    public static boolean RUNNING = false;

    public static Main inst;

    public Renderer renderer;
    public Camera camera;
    public InputMultiplexer inputMultiplexer;

    public World world;
    public MouseInput mouseInput;
    public KeyInput keyInput;
    public Client client;
    public ModHandler handler;
    private Console console;

    @Override
    public void create() {
        long start = System.currentTimeMillis();

        RUNNING = true;
        inst = this;

        Assets.setup();
        Logger.setup();
        Assets.create();
        Assets.load();
        client = new Client();
        camera = new Camera();
        renderer = new Renderer();
        mouseInput = new MouseInput();
        keyInput = new KeyInput();

        setInputMultiplexer();

        Tiles.register();
        GameObjects.register();

        handler = new ModHandler();
        handler.register();

        handler.start();

        Assets.checkGameFiles();
        Logger.log(
                "Start time: " + (float) (System.currentTimeMillis() - start) / 1000.0f + " Seconds");

        console = new Console();
        console.start();
		console.register();
    }

    public void setInputMultiplexer() {

        /*
         *
         * Set all classes that need key/mouse inputs
         *
         */

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(renderer.uiStage);
        inputMultiplexer.addProcessor(keyInput);
        inputMultiplexer.addProcessor(mouseInput);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        Gdx.graphics.setTitle("Age of Progress | FPS: " + Gdx.graphics.getFramesPerSecond());

        /*
         * update game
         *
         */

        camera.update(Gdx.graphics.getDeltaTime());
        mouseInput.update(Gdx.graphics.getDeltaTime());

        /*
         * render game
         *
         */

        if (PLAYING) {
            renderer.render(world, camera.cam);
        }

        renderer.renderUI(camera.cam);

        if (DEBUG && PLAYING) {
            renderer.renderDebug(world, camera.cam);
        }
    }

    public void openWorld(World world) {

        this.world = world;
        PLAYING = true;

        //todo: move to game ui.
        setInputMultiplexer();
    }

    public void closeWorld() {

        /*
         *
         * set world to null
         *
         */


        PLAYING = false;
        this.world.stop();
        this.world = null;

        renderer.changeLayer(new MainMenuLayer());

        setInputMultiplexer();
    }

    public static void exit() {
        RUNNING = false;
        PLAYING = false;

        if (Main.inst.world != null) {
            Main.inst.world.stop();
        }
		Main.inst.console.stop();
        Main.inst.client.stop();
        Main.inst.handler.stop();

        Gdx.app.exit();
    }

    @Override
    public void dispose() {
        VisUI.dispose();
        renderer.dispose();
        Assets.dispose();
    }
}
