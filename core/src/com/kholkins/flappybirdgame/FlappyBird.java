package com.kholkins.flappybirdgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    SpriteBatch batch;
    Texture background;
    Texture[] bird;
    int birdFlag = 0;
    float flyHeight;
    float fallingSpeed = 0;
    int gemestartflag = 0;
    Texture topTube;
    Texture bottomTube;
    int spaceBetweenTubes = 500;
    Random random;
    int tubeSpeed = 5;
    int tubeNumber = 5;
    float tubeX[] = new float[tubeNumber];
    float tubeShift[] = new float[tubeNumber];
    float distanceBetweenTubes;

    Circle birdCircle;
//    ShapeRenderer shapeRenderer;
    Rectangle[] topTubeRectangles;
    Rectangle[] bottomTubeRectangles;

    int gameScore = 0;
    int passedTubeIndex = 0;
    BitmapFont scoreFont;

    Texture gameOver;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
//        shapeRenderer = new ShapeRenderer();

        birdCircle = new Circle();
        topTubeRectangles = new Rectangle[tubeNumber];
        bottomTubeRectangles = new Rectangle[tubeNumber];

        random = new Random();
        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.CYAN);
        scoreFont.getData().setScale(10);

        bird = new Texture[2];
        bird[0] = new Texture("bird_wings_up.png");
        bird[1] = new Texture("bird_wings_down.png");
        flyHeight = Gdx.graphics.getHeight() / 2 - bird[0].getHeight() / 2;
        distanceBetweenTubes = Gdx.graphics.getWidth() / 2;

        topTube = new Texture("top_tube.png");
        bottomTube = new Texture("bottom_tube.png");

        for (int i = 0; i < tubeNumber; i++) {
            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + i *
                    distanceBetweenTubes;
            tubeShift[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()
                    - spaceBetweenTubes - 200);
            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }
    }


    @Override
    public void render() {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Gdx.input.justTouched()) {
            Gdx.app.log("tab", "пук");
            gemestartflag = 1;
        }
        if (gemestartflag == 1) {

            if (tubeX[passedTubeIndex] < Gdx.graphics.getWidth() / 2) {
                gameScore++;

                if (passedTubeIndex < tubeNumber-1) {
                    passedTubeIndex++;
                }else {
                    passedTubeIndex = 0;
                }
            }

            if (Gdx.input.justTouched()) {
                fallingSpeed = -30;
            }

            if (flyHeight > 0 || fallingSpeed < 0) {
                fallingSpeed++;
                flyHeight -= fallingSpeed;
            }

        } else {
            if (Gdx.input.justTouched()) {
                Gdx.app.log("tab", "пук");
                gemestartflag = 1;
            }
        }

        for (int i = 0; i < tubeNumber; i++) {

            if (tubeX[i] < -topTube.getWidth()){
                tubeX[i] = tubeNumber * distanceBetweenTubes;
            }else {
                tubeX[i] -= tubeSpeed;
            }

            batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight()
                    / 2 + spaceBetweenTubes / 2 + tubeShift[i]);
            batch.draw(bottomTube, tubeX[i],
                    Gdx.graphics.getHeight() / 2 - spaceBetweenTubes
                            / 2 - Gdx.graphics.getHeight() + tubeShift[i]);

            topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight()
                    / 2 + spaceBetweenTubes / 2 + tubeShift[i], topTube.getWidth(), topTube.getHeight());

            bottomTubeRectangles[i] = new Rectangle(tubeX[i],
                    Gdx.graphics.getHeight() / 2 - spaceBetweenTubes
                            / 2 - Gdx.graphics.getHeight() + tubeShift[i], bottomTube.getWidth(), bottomTube.getHeight());

        }
        if (birdFlag == 0) {
            birdFlag = 1;

        } else {
            birdFlag = 0;
        }


        batch.draw(bird[birdFlag], Gdx.graphics.getWidth() / 2 - bird[birdFlag].getWidth() / 2,
                flyHeight);

        scoreFont.draw(batch, String.valueOf(gameScore),100,200);

        batch.end();

        birdCircle.set(Gdx.graphics.getWidth() / 2,
                flyHeight + bird[birdFlag].getHeight()/2,
                bird[birdFlag].getWidth()/2);

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.CYAN);
//        shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

        for (int i = 0; i < tubeNumber; i++) {

//            shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight()
//                    / 2 + spaceBetweenTubes / 2 + tubeShift[i], topTube.getWidth(), topTube.getHeight());
//            shapeRenderer.rect(tubeX[i],
//                    Gdx.graphics.getHeight() / 2 - spaceBetweenTubes
//                            / 2 - Gdx.graphics.getHeight() + tubeShift[i], bottomTube.getWidth(), bottomTube.getHeight());

            if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])){

            }
        }

//        shapeRenderer.end();
    }


    @Override
    public void dispose() {

    }

}