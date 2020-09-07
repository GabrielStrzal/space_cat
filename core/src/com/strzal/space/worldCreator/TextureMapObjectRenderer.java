package com.strzal.space.worldCreator;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.strzal.space.SpaceCatGame;


public class TextureMapObjectRenderer extends OrthogonalTiledMapRenderer {

    public TextureMapObjectRenderer(TiledMap map) {
        super(map);
    }

    public TextureMapObjectRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public TextureMapObjectRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public TextureMapObjectRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    @Override
    public void renderObject(MapObject object) {
        if (object instanceof TextureMapObject) {
            TextureMapObject textureObject = (TextureMapObject) object;
            batch.draw(
                    textureObject.getTextureRegion(),
                    textureObject.getX() / SpaceCatGame.PPM,
                    textureObject.getY() / SpaceCatGame.PPM,
                    textureObject.getOriginX() / SpaceCatGame.PPM,
                    textureObject.getOriginY() / SpaceCatGame.PPM,
                    textureObject.getTextureRegion().getRegionWidth() / SpaceCatGame.PPM,
                    textureObject.getTextureRegion().getRegionHeight() / SpaceCatGame.PPM,
                    textureObject.getScaleX(),
                    textureObject.getScaleY(),
                    textureObject.getRotation() * -1
            );
        }
    }
}
