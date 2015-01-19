package com.example.andengine;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import com.badlogic.gdx.physics.box2d.Body;



import android.content.Context;

public class Bomb{
	

	public int width,height;
	public Texture texture;
	public TextureRegion textureRegion;
	public Sprite sprite;
	public Body body;
	public Bomb(int width,int height, TextureOptions options, Context context, String path, int positionX, int positionY)
	{

		this.width = width;
		this.height = height;
		texture = new Texture(width, height, options);
		textureRegion = TextureRegionFactory.createFromAsset(texture, context, path, positionX, positionY);
		
	}


}
