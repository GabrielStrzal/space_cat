package com.strzal.space.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.strzal.space.SpaceCatGame;
import com.strzal.space.constants.ImagesPaths;

public class EnemyFactory {

    public enum EnemyType {
        BASIC, WALKING
    }

    public Enemy createEnemy(Body body, SpaceCatGame game, EnemyType enemyType) {

        switch (enemyType) {
            case BASIC:
                return new BasicEnemy(body, game, (Texture) game.getAssetManager().get(ImagesPaths.BASIC_ENEMY));

            case WALKING:
                return new WalkingEnemy(body, game, (Texture) game.getAssetManager().get(ImagesPaths.WALKING_ENEMY));

            default:
                return null;
        }
    }
}
