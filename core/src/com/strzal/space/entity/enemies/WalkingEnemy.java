package com.strzal.space.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.strzal.space.SpaceCatGame;

public class WalkingEnemy extends Enemy {

    public WalkingEnemy(Body body, SpaceCatGame game, Texture texture) {
        super(body, game, texture);
        height = texture.getHeight() / SpaceCatGame.PPM;
        width = texture.getWidth() / SpaceCatGame.PPM;
        HP = 3;
    }

    @Override
    public void update() {
        super.update();
        //Força para andar
        //body.applyForceToCenter(-100000, 0 , false);
    }
}
