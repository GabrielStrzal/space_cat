package com.strzal.space;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.strzal.gdxUtilLib.BasicGame;
import com.strzal.gdxUtilLib.screenManager.ScreenManager;
import com.strzal.space.audio.AudioHandler;
import com.strzal.space.config.GameConfig;
import com.strzal.space.loading.LoadingPathsImpl;
import com.strzal.space.screenManager.ScreenEnum;
import com.strzal.space.screens.MenuScreen;

public class SpaceCatGame extends BasicGame {

	private AudioHandler audioHandler;

	@Override
	public void create () {
		screenWidth = GameConfig.SCREEN_WIDTH;
		screenHeight = GameConfig.SCREEN_HEIGHT;

		batch = new SpriteBatch();
		loadingPaths = new LoadingPathsImpl();
		audioHandler = new AudioHandler(this);

		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance()
				.showScreen(ScreenEnum.LOADING_SCREEN, this, loadingPaths, new MenuScreen(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public AudioHandler getAudioHandler() {
		return audioHandler;
	}
}
