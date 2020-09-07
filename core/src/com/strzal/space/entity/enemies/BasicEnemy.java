package com.strzal.space.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.strzal.space.SpaceCatGame;


public class BasicEnemy extends Enemy {

    public BasicEnemy(Body body, SpaceCatGame game, Texture texture) {
        super(body, game, texture);
        height = texture.getHeight() / SpaceCatGame.PPM;
        width = texture.getWidth() / SpaceCatGame.PPM;
//        HP = 3;
    }
}
