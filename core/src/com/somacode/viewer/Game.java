package com.somacode.viewer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.UBJsonReader;

public class Game extends ApplicationAdapter {
	private PerspectiveCamera camera;

	private ModelBatch modelBatch;
	private Model model;
	private ModelInstance modelInstance;

	private Environment environment;
	private AnimationController controller;

	private InputManager inputManager;
	private GestureDetector gestureDetector;
	private InputMultiplexer inputMultiplexer;

	private SpriteBatch batch;
	private Sprite playButton;
	
	@Override
	public void create () {
		camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0f,100f,200f);
		camera.lookAt(0f,100f,0f);
		camera.near = 0.1f;
		camera.far = 600f;

		modelBatch = new ModelBatch();

		UBJsonReader jsonReader = new UBJsonReader();
		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);

		model = modelLoader.loadModel(Gdx.files.internal("modelo.d3db"));
		modelInstance = new ModelInstance(model);
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));

		controller = new AnimationController(modelInstance);
		controller.setAnimation("mixamo.com", -1, new AnimationController.AnimationListener() {
			@Override
			public void onEnd(AnimationController.AnimationDesc animation) {
			}
			@Override
			public void onLoop(AnimationController.AnimationDesc animation) {
				Gdx.app.log("INFO","Animation Ended");
			}
		});

		inputManager = new InputManager(camera);

		gestureDetector = new GestureDetector(inputManager);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(gestureDetector);
		inputMultiplexer.addProcessor(inputManager);
		Gdx.input.setInputProcessor(inputMultiplexer);

		playButton = new Sprite(new Texture(Gdx.files.internal("play.png")));
		playButton.setPosition(10, 10);
		ImageButton button = new ImageButton();

		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		Gdx.gl.glViewport(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();
		if (Gdx.input.isTouched()) {
			controller.update(Gdx.graphics.getDeltaTime());
		}
		modelBatch.begin(camera);
		modelBatch.render(modelInstance, environment);
		modelBatch.end();

		batch.begin();
		playButton.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		modelBatch.dispose();
		model.dispose();
	}
}
