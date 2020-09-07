package com.strzal.space.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.strzal.gdxUtilLib.utils.GdxUtils;
import com.strzal.space.SpaceCatGame;
import com.strzal.space.config.GameConfig;
import com.strzal.space.constants.ImagesPaths;
import com.strzal.space.constants.LevelNames;
import com.strzal.space.controller.DialogDisplayController;
import com.strzal.space.controller.HudController;
import com.strzal.space.controller.InputController;
import com.strzal.space.entity.Player;
import com.strzal.space.entity.enemies.Enemy;
import com.strzal.space.worldCreator.B2WorldCreator;
import com.strzal.space.worldCreator.TextureMapObjectRenderer;
import com.strzal.space.worldCreator.WorldContactListener;

import static com.strzal.space.screens.GameState.DIALOG;
import static com.strzal.space.screens.GameState.PLAYING;


public class GameScreen implements Screen {

    private SpaceCatGame game;
    public GameState gameState;


    //Controller
    private Player player;
    private float gravity = -10f;

    private InputController inputController;

    private HudController hudController;
    private DialogDisplayController dialogDisplayController;

    // Renderer
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private TiledMap map;
    private TextureMapObjectRenderer mapRenderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private AssetManager assetManager;
    private WorldContactListener worldContactListener;
    private B2WorldCreator b2World;
    private float timeAccumulator = 0;

    public GameScreen(SpaceCatGame game, int level) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        gameState = PLAYING;

        gameCam = new OrthographicCamera();
        gameCam.setToOrtho(false, GameConfig.SCREEN_WIDTH / SpaceCatGame.PPM, GameConfig.SCREEN_HEIGHT / SpaceCatGame.PPM);
        gamePort = new FitViewport(GameConfig.SCREEN_WIDTH / SpaceCatGame.PPM, GameConfig.SCREEN_HEIGHT / SpaceCatGame.PPM, gameCam);

        world = new World(new Vector2(0, gravity), true);
        b2dr = new Box2DDebugRenderer();

        game.currentLevel = level;
        map = assetManager.get(LevelNames.LEVEL + level + LevelNames.TMX);
        mapRenderer = new TextureMapObjectRenderer(map, 1 / SpaceCatGame.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);


        b2World = new B2WorldCreator(world, map, game);
        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);

        player = new Player(world, (Texture) assetManager.get(ImagesPaths.CHAR_ATLAS));
        inputController = new InputController(game, player, worldContactListener);
        hudController = new HudController(game, this);
        dialogDisplayController = new DialogDisplayController(game, this);

    }

    @Override
    public void show() {
    }

    public void update(float dt) {

        final float frameTime = Math.min(dt, 0.25f);
        timeAccumulator += frameTime;
        while (timeAccumulator >= 1f / 60f) {
            world.step(1f / 60f, 6, 2);
            timeAccumulator -= 1f / 60f;
        }

        updateEnemies();
        removeDeadEnemiesFromWorld();
        inputController.handleInput(dt);

        player.update(dt);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        gameCam.position.x = MathUtils.clamp(player.b2body.getPosition().x, gamePort.getWorldWidth() / 2, (layer.getTileWidth() * layer.getWidth()) / SpaceCatGame.PPM - gamePort.getWorldWidth() / 2);


        gameCam.update();
        mapRenderer.setView(gameCam);
    }


    private void updateEnemies() {
        for (Enemy enemy : b2World.b2WorldCreatorEnemies.enemies) {
            if (!player.state.equals(Player.PLAYER_STATE.ATTACKING)) {
                enemy.attacked = false;
            }
            enemy.update();
        }


        Array<Body> bodies = worldContactListener.getBodiesToRemove();
        for (Body b : bodies) {
            Enemy enemy = b2World.b2WorldCreatorEnemies.enemies.get(b2World.b2WorldCreatorEnemies.enemies.indexOf((Enemy) b.getUserData(), true));
            if (player.state.equals(Player.PLAYER_STATE.ATTACKING)) {
                if (!enemy.attacked) {
                    enemy.doDamageInEnemy(1);
                    enemy.attacked = true;
                }
            }
        }

    }

    private void removeDeadEnemiesFromWorld() {

        for (Enemy enemy : b2World.b2WorldCreatorEnemies.enemies) {
            if (enemy.isEnemyDead()) {
                b2World.b2WorldCreatorEnemies.enemies.removeValue(enemy, true);
                world.destroyBody(enemy.getBody());
            }
        }

    }


    @Override
    public void render(float delta) {
        switch (gameState) {
            case PLAYING: {
                update(delta);
//                checkLevelCompleted();
//                checkGameOver();
//                checkSwitchOn();
//                checkWarp();
//                checkShowInfo();
            }
            break;
            case GAME_OVER: {
//                checkUserConfirmation();
            }
            break;
            case LEVEL_CLEARED: {
//                checkUserConfirmation();
            }
            break;
            case PAUSED: {
//                checkUnPauseGame();
            }
            case DIALOG: {
//                checkUnPauseGame();
            }
            break;
        }

        //remove this later
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            //showDialog
            if(gameState != DIALOG) gameState = DIALOG;
            else {
                gameState = PLAYING;
                dialogDisplayController.restart("This is a test");
            }
        }


        GdxUtils.clearScreen();
//        game.batch.begin();
//        game.batch.draw(background, 0, 0, (GameConfig.SCREEN_WIDTH / TekoiGame.PPM), (GameConfig.SCREEN_HEIGHT / TekoiGame.PPM));
//        game.batch.end();

        mapRenderer.render();
        if (GameConfig.debug) {
            b2dr.render(world, gameCam.combined);
        }
        draw(delta);


    }

    private void draw(float delta) {
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        drawEnemies(delta);
        player.draw(game.batch);
        dialogDisplayController.drawDialog(game.batch);
        game.batch.end();
        hudController.draw();
        dialogDisplayController.render(delta);


    }

    private void drawEnemies(float delta) {
        for (Enemy enemy : b2World.b2WorldCreatorEnemies.enemies) {
            enemy.draw(game.batch);
        }

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        hudController.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}
