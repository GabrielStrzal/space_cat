package com.strzal.space.worldCreator;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.strzal.space.SpaceCatGame;
import com.strzal.space.constants.Bits;
import com.strzal.space.constants.Map;

public class B2WorldCreatorElements {

    public B2WorldCreatorElements(World world, TiledMap map, SpaceCatGame game) {

        if (map.getLayers().get(Map.MAP_BLOCKS) != null) {
            createBlocks(world, map);
        }

    }

    private void createBlocks(World world, TiledMap map) {
        for (MapObject object : map.getLayers().get(Map.MAP_BLOCKS).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            PolygonShape blockShape = createPolygonShape(rect.getWidth(), rect.getHeight());
            FixtureDef blockFdef = createBlockFixtureDef(blockShape);
            BodyDef blockBdef = createBodyDef(rect);
            Body blockBody;
            blockBody = world.createBody(blockBdef);
            blockBody.createFixture(blockFdef).setUserData(Map.MAP_BLOCKS);
        }
    }


    private PolygonShape createPolygonShape(float width, float height) {
        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox((width / 2) / SpaceCatGame.PPM, (height / 2) / SpaceCatGame.PPM);
        return blockShape;
    }

    private FixtureDef createBlockFixtureDef(PolygonShape blockShape) {
        FixtureDef blockFdef = new FixtureDef();
        blockFdef.shape = blockShape;
        blockFdef.filter.categoryBits = Bits.BRICK_BIT;
        blockFdef.filter.maskBits = Bits.PLAYER_BIT | Bits.BASE_BIT | Bits.ENEMY_BIT;
        blockFdef.friction = 0;
        return blockFdef;
    }


    private BodyDef createBodyDef(Rectangle rect) {
        BodyDef blockBdef = new BodyDef();
        blockBdef.type = BodyDef.BodyType.StaticBody;
        blockBdef.position.set((rect.getX() + rect.getWidth() / 2) / SpaceCatGame.PPM, (rect.getY() + rect.getHeight() / 2) / SpaceCatGame.PPM);
        return blockBdef;
    }


}
