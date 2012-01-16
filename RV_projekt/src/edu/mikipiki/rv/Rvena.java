

package edu.mikipiki.rv;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.backends.openal.Mp3.Music;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.lwjgl.opengl.GL41;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class Rvena implements ApplicationListener {
	private Mesh[] mesh = null;
	private Mesh mesh2 = null;
	private Texture tekstura = null;
	private PerspectiveCamera camera;
	private CameraPosition camPos;
	private float transX = 0.0f, transY = 0.0f, transZ = 2.0f;
	private float rotSpeed = 0.8f;
	com.badlogic.gdx.audio.Music music;
	int m = 0;

	public static void main (String[] argv) {
		new JoglApplication(new Rvena(), "Hello World", 480, 320, false);
	}

	public void load_tekstura(){
		
		FileHandle bla = Gdx.files.internal("data/BlueMarble.jpg");
		tekstura = new Texture(bla);
		InputStream stream=null;
		try {
		//stream = new FileInputStream(Gdx.files.internal("data/blender/world/world.obj").path());
			stream = new FileInputStream(Gdx.files.internal("data/hammer.obj").path());
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		}
		mesh2 = ObjLoader.loadObj(stream, true);
		
		
	}
	
	public void create() {
		if (mesh == null) {
			mesh = new Mesh[6];
			camPos = new CameraPosition();


			for (int i = 0; i < 6; i++) {
				mesh[i] = new Mesh(true, 4, 4,
						new VertexAttribute(Usage.Position, 3, "a_position"),
						new VertexAttribute(Usage.ColorPacked, 4, "a_color"));

				mesh[i].setIndices(new short[] { 0, 1, 2, 3 });
			}

			mesh[0].setVertices(new float[] {
					0.5f, 0.5f, 0.5f, Color.toFloatBits(96, 0, 0, 255),
					-0.5f, 0.5f, 0.5f, Color.toFloatBits(96, 0, 0, 255),
					0.5f, -0.5f, 0.5f, Color.toFloatBits(96, 0, 0, 255),
					-0.5f, -0.5f, 0.5f, Color.toFloatBits(96, 0, 0, 255) });

			mesh[1].setVertices(new float[] {
					0.5f, 0.5f, -0.5f, Color.toFloatBits(255, 0, 0, 255),
					-0.5f, 0.5f, -0.5f, Color.toFloatBits(255, 0, 0, 255),
					0.5f, -0.5f, -0.5f,  Color.toFloatBits(255, 0, 0, 255),
					-0.5f, -0.5f, -0.5f, Color.toFloatBits(255, 0, 0, 255) });

			mesh[2].setVertices(new float[] {
					0.5f, 0.5f, -0.5f, Color.toFloatBits(0, 255, 0, 255),
					-0.5f, 0.5f, -0.5f, Color.toFloatBits(0, 255, 0, 255),
					0.5f, 0.5f, 0.5f, Color.toFloatBits(0, 255, 0, 255),
					-0.5f, 0.5f, 0.5f, Color.toFloatBits(0, 255, 0, 255) });

			mesh[3].setVertices(new float[] {
					0.5f, -0.5f, -0.5f, Color.toFloatBits(0, 96, 0, 255),
					-0.5f, -0.5f, -0.5f, Color.toFloatBits(0, 96, 0, 255),
					0.5f, -0.5f, 0.5f, Color.toFloatBits(0, 96, 0, 255),
					-0.5f, -0.5f, 0.5f,  Color.toFloatBits(0, 96, 0, 255) });

			mesh[4].setVertices(new float[] {
					0.5f, 0.5f, 0.5f, Color.toFloatBits(0, 0, 255, 255),
					0.5f, -0.5f, 0.5f, Color.toFloatBits(0, 0, 255, 255),
					0.5f, 0.5f, -0.5f, Color.toFloatBits(0, 0, 255, 255),
					0.5f, -0.5f, -0.5f, Color.toFloatBits(0, 0, 255, 255) });

			mesh[5].setVertices(new float[] {
					-0.5f, 0.5f, 0.5f, Color.toFloatBits(0, 0, 96, 255),
					-0.5f, -0.5f, 0.5f, Color.toFloatBits(0, 0, 96, 255),
					-0.5f, 0.5f, -0.5f, Color.toFloatBits(0, 0, 96, 255),
					-0.5f, -0.5f, -0.5f, Color.toFloatBits(0, 0, 96, 255) });
		}

		load_tekstura();
		
		
		//Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		
		
		
	}

	public void resume() { }

	public void render() {
		handleInput();

		//camPos.rotate();

		camera.position.set(camPos.getPosX(), camPos.getPosY(), camPos.getPosZ());
		camera.lookAt(0, 50, 0);	

		

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
		
		camera.update();
		
		/*for (Mesh face : mesh) {
			face.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
		}*/
		//tekstura.bind();
		camera.apply(Gdx.gl10);
		mesh2.render(GL20.GL_TRIANGLE_STRIP);
		//mesh2.render(GL10.GL_LINE_STRIP);
		
		try {
			Thread.sleep(16); // ~60FPS
		} catch (InterruptedException e) {}
	}

	private void handleInput(){
		if (Gdx.input.isKeyPressed(Input.Keys.PLUS)) {
			camPos.moveForward();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
			camPos.moveBackward();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camPos.moveLeft();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camPos.moveRight();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camPos.moveDown();
		} 
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camPos.moveUp();
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.R)){
			camera.translate(20f, 0f, 0f);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.P)) {
       	 spilaj();
       	 m=1;
       }
       
       if(Gdx.input.isKeyPressed(Input.Keys.O)){
       		pavza();
       }
		

	}
	
	 public void spilaj(){
	    	if (m==0)
	    		music = Gdx.audio.newMusic(Gdx.files.absolute("C:\\StariZenin.mp3"));
	    	music.play();
	    	
	    	
	    }
	    
	    public void pavza(){
	    	music.pause();
	    }

	public void resize(int width, int height) {
		float aspectRatio = (float) width / (float) height;
		camera = new PerspectiveCamera(67, 2f * aspectRatio, 2f);
		camera.near = 0.1f;
		camera.translate(transX, transY, transZ);
	}

	public void pause() { }
	
	

	public void dispose() { }
}
 