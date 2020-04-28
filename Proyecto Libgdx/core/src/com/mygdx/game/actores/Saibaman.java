package com.mygdx.game.actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.geom.RectangularShape;

public class Saibaman extends Actor {

    private World world;
    private Body body;
    private Sprite sprite;
    private Texture staticSaibaman, animacionWalking1,animacionWalking2;

    private Animation walkAnimation;
    private TextureRegion[]walkFrames;
    private TextureRegion[][]tmp;
    TextureRegion currentWalkFrame;

    private boolean ida;

    private float posInicialX;

    public Saibaman(World mundo){
        this.world = mundo;

        staticSaibaman = new Texture("personajes/Saibaman/sai.png");
        animacionWalking1 = new Texture("personajes/Saibaman/saibamanwalking1.png");
        animacionWalking2 = new Texture("personajes/Saibaman/saibamanwalking2.png");

        propiedadesFisicas();

        ida = true;



        posInicialX = body.getPosition().x;


    }

    public void propiedadesFisicas(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(2090,69);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(4, 6);
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //sprite = new Sprite(staticSaibaman);
        sprite.setBounds(body.getPosition().x-7,body.getPosition().y-7,16,16);
        sprite.setPosition(body.getPosition().x - 9f, body.getPosition().y - 9);
        sprite.draw(batch);
        patrullar();
        System.out.println(body.getPosition().x);
    }

    public void patrullar() {

       if(body.getPosition().x<2200){

           if(body.getPosition().x < 2200 && ida == true){
               body.setLinearVelocity(36,0);
           }else{
               ida = false;
           }
           if(body.getPosition().x>posInicialX && ida == false){
               body.setLinearVelocity(-36,0);
           }else{
               ida = true;
           }

       }else{
           if(body.getPosition().x<2200 && ida == true){
               body.setLinearVelocity(36,0);
           }else{
               ida = false;
           }

           if(body.getPosition().x>2200 && ida == false){
               body.setLinearVelocity(-36,0);
           }else{
               ida = true;
           }
       }

    }

    public void animacionAcciones(float elapsedTime){

        if(ida == true){

            tmp = TextureRegion.split(animacionWalking1,37,58);

            walkFrames = new TextureRegion[6];

            int index = 0;

            for(int i = 0; i<6;i++){
                for(int j = 0; j<1;j++){
                    walkFrames[index++] = tmp[j][i];
                }
            }

            walkAnimation = new Animation(0.09f,walkFrames);

            currentWalkFrame =  (TextureRegion)walkAnimation.getKeyFrame((elapsedTime),true);

            sprite = new Sprite(currentWalkFrame);

        }else{


            tmp = TextureRegion.split(animacionWalking2,37,58);

            walkFrames = new TextureRegion[6];

            int index = 0;

            for(int i = 0; i<6;i++){
                for(int j = 0; j<1;j++){
                    walkFrames[index++] = tmp[j][i];
                }
            }

            walkAnimation = new Animation(0.09f,walkFrames);

            currentWalkFrame =  (TextureRegion)walkAnimation.getKeyFrame((elapsedTime),true);

            sprite = new Sprite(currentWalkFrame);

        }
    }
}
