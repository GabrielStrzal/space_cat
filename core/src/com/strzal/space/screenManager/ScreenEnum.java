package com.strzal.space.screenManager;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.strzal.gdxUtilLib.loading.LoadingPaths;
import com.strzal.gdxUtilLib.screenManager.ScreenEnumInterface;
import com.strzal.gdxUtilLib.screens.LoadingScreen;
import com.strzal.space.SpaceCatGame;
import com.strzal.space.screens.GameScreen;
import com.strzal.space.screens.MenuScreen;


/**
 * Based on http://www.pixnbgames.com/blog/libgdx/how-to-manage-screens-in-libgdx/
 */

public enum ScreenEnum implements ScreenEnumInterface {
    GAME_SCREEN {
        public Screen getScreen(Object... params) {
            return new GameScreen((SpaceCatGame)params[0], (Integer) params[1]);
        }
    },
    LOADING_SCREEN {
        public Screen getScreen(Object... params) {
            return new LoadingScreen((SpaceCatGame)params[0], (LoadingPaths)params[1], (ScreenAdapter)params[2]);
        }
    },
    MENU_SCREEN {
        public Screen getScreen(Object... params) {
            return new MenuScreen((SpaceCatGame)params[0]);
        }
    }

}
