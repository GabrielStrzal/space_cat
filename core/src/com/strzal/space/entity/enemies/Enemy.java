package com.strzal.space.entity.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.strzal.space.SpaceCatGame;

public abstract class Enemy extends Sprite {

    protected Body body;
    protected SpaceCatGame game;
    protected AssetManager assetManager;
    protected float width;
    protected float height;

    protected int HP = 1;
    public boolean attacked = false;

    public Enemy(Body body, SpaceCatGame game, Texture texture) {
        super(texture);
        setSize(texture.getWidth()/ SpaceCatGame.PPM,texture.getHeight()/ SpaceCatGame.PPM);
        this.body = body;
        this.game = game;
        this.assetManager = game.getAssetManager();
    }
    public boolean isEnemyDead(){
        return HP <= 0;
    }
    public void doDamageInEnemy(int damage){
        HP -= damage;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Body getBody() {
        return body;
    }

    public void update() {
        setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
    }
}
