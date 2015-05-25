package com.cookies.synergy.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class GFX {

    private static float thickness = 0.5f;
    private static int numberOfBolts = 1;

    private static Vector2 tempSphereVector = new Vector2(0,0);

    private static Color colourOne = new Color(14f/255f, 100f/255f, 200f/255f, 1f);
    private static Color colourTwo = new Color(54f/255f, 210f/255f, 239f/255f, 1f);

    private static float dx, dy;
    private static Vector2 returnVector = new Vector2(0, 0);

    private static Texture texture;

    public static void drawSphereLightning(SpriteBatch batch, Vector2 point, float thickness, int numberOfBolts, int radius, int circleSegments){
        drawSphereLightning(batch, point, thickness, numberOfBolts, radius, circleSegments, colourOne, colourTwo);
    }

    public static void drawSphereLightning(SpriteBatch batch, Vector2 point, float thickness, int numberOfBolts, int radius, int circleSegments, Color colourA, Color colourB) {
        for (int i=0; i < 360f; i += (360f / circleSegments)) {
            tempSphereVector = getOrbitLocationDeg(point.x, point.y, i, radius);
            drawP2PLightning(batch, point.x, point.y, tempSphereVector.x, tempSphereVector.y, MathUtils.random(60f, 140f), MathUtils.random(0.8f, 3.8f), thickness, numberOfBolts, colourA, colourB);
        }
    }

    public static void drawChainLightningRandomBetweenPoints(SpriteBatch batch, Vector2 startPos, Vector2 endPointOne, Vector2 endPointTwo) {
        Vector2[] points = new Vector2[2];
        points[1] = startPos;
        points[0] = new Vector2(MathUtils.random(endPointOne.x, endPointTwo.x), MathUtils.random(endPointOne.y, endPointTwo.y));
        drawChainLightning(batch, points, thickness, numberOfBolts, colourOne, colourTwo);
    }

    public static void drawChainLightningRandomBetweenPoints(SpriteBatch batch, Vector2 startPos, Vector2 endPointOne, Vector2 endPointTwo, float thickness, int numberOfBolts) {
        Vector2[] points = new Vector2[2];
        points[1] = startPos;
        points[0] = new Vector2(MathUtils.random(endPointOne.x, endPointTwo.x), MathUtils.random(endPointOne.y, endPointTwo.y));
        drawChainLightning(batch, points, thickness, numberOfBolts, colourOne, colourTwo);
    }

    public static void drawChainLightningRandomBetweenPoints(SpriteBatch batch, Vector2 startPos, Vector2 endPointOne, Vector2 endPointTwo, float thickness, int numberOfBolts, Color colourA, Color colourB) {
        Vector2[] points = new Vector2[2];
        points[1] = startPos;
        points[0] = new Vector2(MathUtils.random(endPointOne.x, endPointTwo.x), MathUtils.random(endPointOne.y, endPointTwo.y));
        drawChainLightning(batch, points, thickness, numberOfBolts, colourA, colourB);
    }

    public static void drawChainLightning(SpriteBatch batch, Vector2[] points, Color colourA, Color colourB) {
        drawChainLightning(batch, points, thickness, numberOfBolts, colourA, colourB);
    }

    public static void drawChainLightning(SpriteBatch batch, Vector2[] points) {
        drawChainLightning(batch, points, thickness, numberOfBolts, colourOne, colourTwo);
    }

    public static void drawChainLightning(SpriteBatch batch, Vector2[] points, float thickness, int numberOfBolts) {
        drawChainLightning(batch, points, thickness, numberOfBolts, colourOne, colourTwo);
    }

    public static void drawChainLightning(SpriteBatch batch, Vector2[] points, float thickness, int numberOfBolts, Color colourA, Color colourB) {
        drawP2PLightning(batch, points[0].x, points[0].y, points[1].x, points[1].y, MathUtils.random(60f, 140f), MathUtils.random(0.8f, 3.8f), thickness, numberOfBolts, colourA, colourB);
    }

    private static void drawLine(SpriteBatch batch, float x1, float y1, float x2, float y2, float thickness, Texture tex) {
        float length = getDistanceAccurate(x1, y1, x2, y2);
        float dx = x1;
        float dy = y1;
        dx = dx - x2;
        dy = dy - y2;
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(dy, dx);
        angle = angle-180;
        batch.draw(tex, x1, y1, 0f, thickness * 0.5f, length, thickness, 1f, 1f, angle, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }

    private static void drawP2PLightning(SpriteBatch batch, float x1, float y1, float x2, float y2, float displace, float detail, float thickness, int numberOfBolts, Color colourA, Color colourB) {
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        for (int i=0; i < numberOfBolts; i++) {
            batch.setColor(MathUtils.random(colourA.r, colourB.r), MathUtils.random(colourA.g, colourB.g), MathUtils.random(colourA.b, colourB.b), 1f);
            drawSingleP2PLightning(batch, x1, y1, x2, y2, 117, 1.8f, thickness);
        }
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private static void drawSingleP2PLightning(SpriteBatch batch, float x1, float y1, float x2, float y2, float displace, float detail, float thickness) {
        if (displace < detail) {
            drawLine(batch, x1, y1, x2, y2, thickness, texture);
        } else {
            float mid_x = (x2 + x1) * 0.5f;
            float mid_y = (y2 + y1) * 0.5f;
            mid_x += (Math.random() - 0.5f) * displace;
            mid_y += (Math.random() - 0.5f) * displace;
            drawSingleP2PLightning(batch, x1, y1, mid_x, mid_y, displace * 0.5f, detail, thickness);
            drawSingleP2PLightning(batch, x2, y2, mid_x, mid_y, displace * 0.5f, detail, thickness);
        }
    }

    public static void setThickness(float setThickness) {
        thickness = setThickness;
    }

    public static void setNumberOfBolts(int setNumberOfBolts) {
        numberOfBolts = setNumberOfBolts;
    }

    public static void setToDefaultColour() {
        colourOne = new Color(14f/255f, 100f/255f, 200f/255f, 1f);
        colourTwo = new Color(54f/255f, 210f/255f, 239f/255f, 1f);
    }

    public static void setColour(Color colourA, Color colourB) {
        colourOne = colourA;
        colourTwo = colourB;
    }

    public static void setTexture(Texture setTexture) {
        texture = setTexture;
    }

    private static float getDistanceAccurate(float srcX, float srcY, float relativeX, float relativeY){
        dx = relativeX - srcX;
        dy = relativeY - srcY;
        return (float)Math.sqrt((double)(dx * dx + dy * dy));
    }

    private static Vector2 getOrbitLocationDeg(float srcX, float srcY, float angle, float radius){
        returnVector.set(srcX + (MathUtils.cosDeg(angle) * radius), srcY + (MathUtils.sinDeg(angle) * radius));
        return returnVector;
    }
}
