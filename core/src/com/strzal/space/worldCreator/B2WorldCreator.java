package com.strzal.space.worldCreator;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.strzal.space.SpaceCatGame;


public class B2WorldCreator {


    public B2WorldCreatorEnemies b2WorldCreatorEnemies;
    public B2WorldCreatorElements b2WorldCreatorElements;

    public B2WorldCreator(World world, TiledMap map, SpaceCatGame game) {


        b2WorldCreatorEnemies = new B2WorldCreatorEnemies(world, map, game);
        b2WorldCreatorElements = new B2WorldCreatorElements(world, map, game);

        //bits controll what collides with what...
        //if I have (Character, enemies, sensor[passa tudo, mas mede o que passou], block[nao deixa passar nada])

        //B2WorldCreatorAllies()

    }


}