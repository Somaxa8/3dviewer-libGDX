package com.somacode.viewer;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputManager implements InputProcessor, GestureDetector.GestureListener {
    private PerspectiveCamera camera;
    private float angleX;
    private float angleY;

    private int count = 0;
    private float zoom = 100f;

    private float zoomDistance;

    public InputManager(PerspectiveCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if(angleX < screenX) {
            camera.rotateAround(new Vector3(0f,0f,0f), new Vector3(0f,1f,0f), 5f);
        }
        if (angleX > screenX) {
            camera.rotateAround(new Vector3(0f,0f,0f), new Vector3(0f,1f,0f), -5f);
        }
        /*if(angleY < screenY) {
            camera.rotateAround(new Vector3(0f,0f,0f), new Vector3(1f,0f,0f), 5f);
        }
        if (angleY > screenY) {
            camera.rotateAround(new Vector3(0f,0f,0f), new Vector3(1f,0f,0f), -5f);
        }*/
        angleX = screenX;
        angleY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
//        if (count == 2) {
//            camera.position.set(0f,0f, 0f);
//        }
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        System.out.println(distance);

        if (zoomDistance > distance) {
            zoom = zoom + 1;
            camera.position.set(0f,100f, zoom);
        }
        if (zoomDistance < distance) {
            zoom = zoom - 1;
            camera.position.set(0f,100f, zoom);
        }
        zoomDistance = distance;

        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return true;
    }

    @Override
    public void pinchStop() {

    }
}
