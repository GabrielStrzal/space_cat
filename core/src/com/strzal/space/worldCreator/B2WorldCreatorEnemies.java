package com.strzal.space.worldCreator;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.strzal.space.SpaceCatGame;
import com.strzal.space.constants.Bits;
import com.strzal.space.constants.Map;
import com.strzal.space.entity.enemies.Enemy;
import com.strzal.space.entity.enemies.EnemyFactory;


public class B2WorldCreatorEnemies {

    //WorldElements na classe Game?? (se ficar no Game... quando iniciar outra tela... teria que deletar...)
    private EnemyFactory factory;

    public Array<Enemy> enemies;

    public B2WorldCreatorEnemies(World world, TiledMap map, SpaceCatGame game){

        factory = new EnemyFactory();

        enemies = new Array<Enemy>();

        if (map.getLayers().get(Map.MAP_BASIC_ENEMY) != null) {
            createBasicEnemy(world, map, game);
        }

        if (map.getLayers().get(Map.MAP_WALKING_ENEMY) != null) {
            createWalkingEnemy(world, map, game);
        }

    }

    private void createWalkingEnemy(World world, TiledMap map, SpaceCatGame game) {
        for (MapObject object : map.getLayers().get(Map.MAP_WALKING_ENEMY).getObjects()) {
            Ellipse tiledMapEllipse = ((EllipseMapObject) object).getEllipse();

            BodyDef walkingEnemyBdef = createEnemyBodyDef(tiledMapEllipse.x, tiledMapEllipse.y, tiledMapEllipse.width, tiledMapEllipse.height);
            PolygonShape walkingEnemyShape = createPolygonShape(64, 128);

            FixtureDef walkingEnemyFdef = createWalkingEnemyFixtureDef(walkingEnemyShape);
            Body walkingEnemyBody = createWalkingEnemyBody(world, walkingEnemyBdef, walkingEnemyFdef, Map.MAP_WALKING_ENEMY);
            Enemy f = factory.createEnemy(walkingEnemyBody, game, EnemyFactory.EnemyType.WALKING);
            walkingEnemyBody.setUserData(f);
            enemies.add(f);
        }
    }

    private BodyDef createEnemyBodyDef(float x, float y, float width, float height) {
        BodyDef walkingEnemyBdef = new BodyDef();
        walkingEnemyBdef.type = BodyDef.BodyType.DynamicBody;
        walkingEnemyBdef.position.set((x + width / 2) / SpaceCatGame.PPM,
                (y + height / 2) / SpaceCatGame.PPM);
        return walkingEnemyBdef;
    }

    private FixtureDef createWalkingEnemyFixtureDef(PolygonShape walkingEnemyShape) {
        FixtureDef walkingEnemyFdef = new FixtureDef();
        walkingEnemyFdef.shape = walkingEnemyShape;
        walkingEnemyFdef.filter.categoryBits = Bits.ENEMY_BIT;
        walkingEnemyFdef.filter.maskBits = Bits.PLAYER_BIT | Bits.BASE_BIT | Bits.BRICK_BIT;
        return walkingEnemyFdef;
    }

    private Body createWalkingEnemyBody(World world, BodyDef walkingEnemyBdef, FixtureDef walkingEnemyFdef, String userData) {
        Body walkingEnemyBody = world.createBody(walkingEnemyBdef);
        MassData massData = new MassData();
        massData.mass = 1000000;
        walkingEnemyBody.setMassData(massData);
        walkingEnemyBody.createFixture(walkingEnemyFdef).setUserData(userData);
        return walkingEnemyBody;

    }


    private void createBasicEnemy(World world, TiledMap map, SpaceCatGame game) {
        for (MapObject object : map.getLayers().get(Map.MAP_BASIC_ENEMY).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            BodyDef basicEnemyBdef = createEnemyBodyDef(rect.x, rect.y, rect.width, rect.height);
            PolygonShape basicEnemyShape = createPolygonShape(64, 128);
            FixtureDef basicEnemyFdef = createWalkingEnemyFixtureDef(basicEnemyShape);
            Body basicEnemyBody = createWalkingEnemyBody(world, basicEnemyBdef, basicEnemyFdef, Map.MAP_BASIC_ENEMY);

            Enemy basicEnemy = factory.createEnemy(basicEnemyBody, game, EnemyFactory.EnemyType.BASIC);
            basicEnemyBody.setUserData(basicEnemy);
            enemies.add(basicEnemy);
        }
    }

    private PolygonShape createPolygonShape(float width, float height) {
        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox((width / 2) / SpaceCatGame.PPM, (height / 2) / SpaceCatGame.PPM);
        return blockShape;
    }
}
