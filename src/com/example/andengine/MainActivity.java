package com.example.andengine;


import java.io.IOException;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.particle.ParticleSystem;
import org.anddev.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.anddev.andengine.entity.particle.initializer.RotationInitializer;
import org.anddev.andengine.entity.particle.initializer.VelocityInitializer;
import org.anddev.andengine.entity.particle.modifier.ExpireModifier;
import org.anddev.andengine.entity.particle.modifier.ScaleModifier;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.BuildableTexture;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.net.Uri;
import android.view.KeyEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.searchboxsdk.android.StartAppSearch;
import com.startapp.android.publish.StartAppAd;

public class MainActivity extends BaseGameActivity  
{
    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    static FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.5f, 1.45f, 0.5f);//(1, 0.95f, 0.5f);
    private static FixtureDef FIXTURE_DEF_ENGEL = PhysicsFactory.createFixtureDef(30, -2.0f, 0.5f);//(0.5f, 0.0f, 0.2f);
    private Music mMusic;
    private StartAppAd startAppAd = new StartAppAd(this);
    
    Bomb bomb1,bomb2,bomb3;
    Star star1,star2,star3,kanat1,kanat2;
    Texture moreTexture,bgTexture,textureDrop,textureSepet,TextureSepetDolu, textureYumurta,engelResim1,geciciEngel1,engelResim2,geciciEngel2,
    engelResim3,geciciEngel3,engelResim4,geciciEngel4,textureAnime,anaMenuArka,anaMenuOyna,anaMenuSounds,anaMenuSoundsOff,anaMenuMusics,anaMenuMusicsOff,anaMenuCikis,
    pauseMenuArka,pauseMenuStar1,pauseMenuStar2,pauseMenuStar3, pauseMenuMenu, pauseMenuRestart, pauseMenuResume,pauseMenuForward,pauseMenuBrokenEgg;
    
    private Texture textParcacik1,textParcacik2,textParcacik3,levelMenuArka,levelMenuBackButton;
    private Texture[] levelMenuLevel = new Texture[10];
    
    TextureRegion moreReg,bgReg,textRegDrop,textRegSepet, textRegSepetDolu,textRegYumurta,engelRegResim1,geciciRegEngel1,engelRegResim2,
    geciciRegEngel2,engelRegResim3,geciciRegEngel3,engelRegResim4,geciciRegEngel4,anaMenuRegArka, anaMenuRegOyna,
    anaMenuRegSounds,anaMenuRegSoundsOff,anaMenuRegMusics,anaMenuRegMusicsOff, anaMenuRegCikis,
    pauseRegMenuArka,pauseRegMenuStar1,pauseRegMenuStar2,pauseRegMenuStar3, pauseRegMenuMenu, pauseRegMenuRestart,
    pauseRegMenuResume,pauseRegMenuForward,pauseRegMenuBrokenEgg;
    
    private TextureRegion textRegParcacik1,textRegParcacik2,textRegParcacik3,levelMenuRegArka,levelMenuRegBackButton;
    private TextureRegion[] levelMenuRegLevel = new TextureRegion[10];
    
    Sprite moreSprite,bgSprite,spriteDrop,spriteSepet,spriteDoluSepet, spriteYumurta,spriteEngel1,geciciEngel1Sprite,spriteEngel2,geciciEngel2Sprite,
    spriteEngel3,geciciEngel3Sprite,spriteEngel4,geciciEngel4Sprite,anaMenuArkaSprite, anaMenuOynaSprite,
    anaMenuSoundsSprite,anaMenuSoundsSpriteOff,anaMenuMusicsSprite,anaMenuMusicsSpriteOff, anaMenuCikisSprite,
    pauseMenuArkaSprite,pauseMenuStar1Sprite,pauseMenuStar2Sprite,pauseMenuStar3Sprite, pauseMenuMenuSprite, pauseMenuRestartSprite,
    pauseMenuResumeSprite,pauseMenuForwardSprite,pauseMenuBrokenEggSprite,levelMenuArkaSprite,levelMenuBackButtonSprite;
    
    Sprite[]  levelMenuLockedLevelSprite = new Sprite[10];
    private CircleOutlineParticleEmitter particleEmitterParcacik1,particleEmitterParcacik2,particleEmitterParcacik3;
    private ParticleSystem parcacikSistemi1,parcacikSistemi2,parcacikSistemi3;
 
    Level levels[] = new Level[10];
  
    Body bodySepet, bodyYumurta, bodyEngel1,bodyEngel2,bodyEngel3,bodyEngel4;
    TiledTextureRegion tiledTextRegAnime;
    AnimatedSprite animSpriteAnime;
    private Scene sahneOyun,sahneLevel, sahneAnaMenu, sahnePauseMenu;
    private Camera camera;
    private PhysicsWorld physicsWorld;
    float dokunulanNoktaX = 300, dokunulanNoktaY = 150, dokunulanNokta1X = 150, dokunulanNokta1Y = 300,
    	  dokunulanNokta2X = 500,dokunulanNokta2Y = 50, dokunulanNokta3X = 620, dokunulanNokta3Y = 250;
    TimerHandler timerOyunSuresi;
    private Sound soundOfBomb,soundOfHit,soundOfClick;
    boolean drop = false,yakalanma = false,anaMenuSahnesiMi = true, oyunSahnesiMi = false,levelMenuSahnesiMi = false,
    		pauseMenuSahnesiMi = false, patladiMi = false, basladi = false, sonraki = false ,sound=true,music=true;
    /*Yazý yazabilmek icin gerekli nesneler*/
    Font font;
    BuildableTexture bText;
    ChangeableText ctextLevel;
    int current = 0;
    SharedPreferences prefs = null;
    SharedPreferences.Editor mPrefsEditor;
    String leveller[] = {"bir","iki","uc","dort","bes","alti","yedi","sekiz","dokuz","on"};
    String levelString[] = {"1","2","3","4","5","6","7","8","9"};
    public Engine onLoadEngine() 
    {
            camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
            final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), camera);
            engineOptions.getTouchOptions().setRunOnUpdateThread(true);
            engineOptions.setNeedsSound(true);
            engineOptions.setNeedsMusic(true);
            return new Engine(engineOptions);
    }
    public void onLoadResources() 
    {	
    	prefs = getSharedPreferences("com.example.andengine", MODE_PRIVATE);
    	mPrefsEditor = prefs.edit();
    	
    	bomb1 = new Bomb(64,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA,this,"gfx/bomb.png",0,0);
    	bomb2 =  new Bomb(64,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA,this,"gfx/bomb.png",0,0);
    	bomb3 =  new Bomb(64,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA,this,"gfx/bomb.png",0,0);
    	
    	star1 = new Star(64,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA,this,"gfx/star.png",0,0);
    	star2 = new Star(64,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA,this,"gfx/star.png",0,0);
    	star3 = new Star(64,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA,this,"gfx/star.png",0,0);
    	
    	kanat1 = new Star(128,16,TextureOptions.BILINEAR_PREMULTIPLYALPHA,this,"gfx/kanat1.png",0,0);
    	kanat2 = new Star(16,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA,this,"gfx/kanat.png",0,0);
    	
    	
    	moreTexture = new Texture (128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	bgTexture = new Texture (1024,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	textureDrop = new Texture(128,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	textureAnime = new Texture(512,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	TextureSepetDolu = new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        textureSepet = new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        textureYumurta = new Texture(32,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        engelResim1 = new Texture(32,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        geciciEngel1 = new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        engelResim2 = new Texture(32,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        geciciEngel2 = new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        engelResim3 = new Texture(32,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        geciciEngel3 = new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        engelResim4 = new Texture(32,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        geciciEngel4 = new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        
        pauseMenuArka =  new Texture (1024,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        pauseMenuStar1 = new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        pauseMenuStar2 = new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        pauseMenuStar3 = new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        pauseMenuMenu =  new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        pauseMenuRestart = new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        pauseMenuResume =  new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        pauseMenuForward =  new Texture(128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        pauseMenuBrokenEgg = new Texture(256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        textParcacik1 = new Texture(32,32,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        textParcacik2 = new Texture(32,32,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        textParcacik3 = new Texture(32,32,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        
        anaMenuArka = new Texture (1024,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        anaMenuOyna = new Texture(256,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        anaMenuSounds = new Texture(128,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        anaMenuSoundsOff = new Texture(128,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        anaMenuMusics = new Texture(128,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        anaMenuMusicsOff = new Texture(128,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        
        anaMenuCikis = new Texture(256,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        
        levelMenuArka = new Texture (1024,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        levelMenuBackButton = new Texture (128,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        for(int i=0;i< 10;i++){
        levelMenuLevel[i] = new Texture (128,128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        levels[i] = new Level();
        
        }
        
        moreReg = TextureRegionFactory.createFromAsset(moreTexture, this, "gfx/more.png", 0, 0);
        bgReg = TextureRegionFactory.createFromAsset(bgTexture, this, "gfx/bg.png", 0, 0); 
        textRegDrop = TextureRegionFactory.createFromAsset(textureDrop, this, "gfx/drop.png", 0, 0); 
        tiledTextRegAnime = TextureRegionFactory.createTiledFromAsset(textureAnime, this, "gfx/bomb4.png", 0, 0, 4, 4);
        textRegSepet = TextureRegionFactory.createFromAsset(textureSepet, this, "gfx/sepetcik.png", 0, 0);
        textRegSepetDolu = TextureRegionFactory.createFromAsset(TextureSepetDolu, this, "gfx/sepetDolu.png", 0, 0); 
        engelRegResim1 = TextureRegionFactory.createFromAsset(engelResim1, this, "gfx/wood.png", 0, 0);
        geciciRegEngel1 = TextureRegionFactory.createFromAsset(geciciEngel1, this, "gfx/lock.png", 0, 0);
        engelRegResim2 = TextureRegionFactory.createFromAsset(engelResim2, this, "gfx/wood.png", 0, 0);
        geciciRegEngel2 = TextureRegionFactory.createFromAsset(geciciEngel2, this, "gfx/lock.png", 0, 0);
        engelRegResim3 = TextureRegionFactory.createFromAsset(engelResim3, this, "gfx/wood.png", 0, 0);
        geciciRegEngel3 = TextureRegionFactory.createFromAsset(geciciEngel3, this, "gfx/lock.png", 0, 0);
        engelRegResim4 = TextureRegionFactory.createFromAsset(engelResim4, this, "gfx/wood.png", 0, 0);
        geciciRegEngel4 = TextureRegionFactory.createFromAsset(geciciEngel4, this, "gfx/lock.png", 0, 0);
        textRegYumurta = TextureRegionFactory.createFromAsset(textureYumurta, this, "gfx/yumurta.png", 0, 0);
        pauseRegMenuArka = TextureRegionFactory.createFromAsset(pauseMenuArka, this, "gfx/pauseArkaplan.png", 0, 0); 
        pauseRegMenuStar1 = TextureRegionFactory.createFromAsset(pauseMenuStar1, this, "gfx/yellowStar.png", 0, 0); 
        pauseRegMenuStar2 = TextureRegionFactory.createFromAsset(pauseMenuStar2, this, "gfx/yellowStar.png", 0, 0); 
        pauseRegMenuStar3 = TextureRegionFactory.createFromAsset(pauseMenuStar3, this, "gfx/yellowStar.png", 0, 0); 
        pauseRegMenuMenu = TextureRegionFactory.createFromAsset(pauseMenuMenu, this, "gfx/menu.png", 0, 0); 
        pauseRegMenuRestart = TextureRegionFactory.createFromAsset(pauseMenuRestart, this, "gfx/restart.png", 0, 0); 
        pauseRegMenuResume = TextureRegionFactory.createFromAsset(pauseMenuResume, this, "gfx/resume.png", 0, 0); 
        pauseRegMenuForward = TextureRegionFactory.createFromAsset(pauseMenuForward, this, "gfx/forward.png", 0, 0); 
        pauseRegMenuBrokenEgg = TextureRegionFactory.createFromAsset(pauseMenuBrokenEgg, this, "gfx/brokenEgg.png", 0, 0); 
        
        
        textRegParcacik1 = TextureRegionFactory.createFromAsset(textParcacik1, this, "gfx/miniStar.png", 0, 0); 
        textRegParcacik2 = TextureRegionFactory.createFromAsset(textParcacik2, this, "gfx/miniStar.png", 0, 0); 
        textRegParcacik3 = TextureRegionFactory.createFromAsset(textParcacik3, this, "gfx/miniStar.png", 0, 0);
        
        /*Yazý için*/
        if(bText == null){
        	bText = new BuildableTexture(256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        }
        else{
        	bText = null;
        	bText = new BuildableTexture(256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        	
        }
        
        
        anaMenuRegArka = TextureRegionFactory.createFromAsset(anaMenuArka, this, "gfx/arkaplan.png", 0, 0); 
        anaMenuRegOyna = TextureRegionFactory.createFromAsset(anaMenuOyna, this, "gfx/bt_play.png", 0, 0);
        anaMenuRegSounds = TextureRegionFactory.createFromAsset(anaMenuSounds, this, "gfx/soundon.png", 0, 0);
        anaMenuRegSoundsOff = TextureRegionFactory.createFromAsset(anaMenuSoundsOff, this,"gfx/soundoff.png", 0, 0);
        anaMenuRegMusics = TextureRegionFactory.createFromAsset(anaMenuMusics, this, "gfx/musicon.png", 0, 0);
        anaMenuRegMusicsOff = TextureRegionFactory.createFromAsset(anaMenuMusicsOff, this,"gfx/musicoff.png", 0, 0);
        anaMenuRegCikis = TextureRegionFactory.createFromAsset(anaMenuCikis, this, "gfx/bt_quit.png", 0, 0);
        
        levelMenuRegArka = TextureRegionFactory.createFromAsset(levelMenuArka, this, "gfx/arkaplanLevel.png", 0, 0); 
        levelMenuRegBackButton = TextureRegionFactory.createFromAsset(levelMenuBackButton, this, "gfx/back_arrow.png", 0, 0); 
        String name;
        int say = -1;
        for(int i=0;i<10;i++){
        	switch(i){
        	
        	case 0 : name = "1"; break;
        	case 1 : name = "2"; break;
        	case 2:  name = "3"; break;
        	case 3:  name = "4"; break;
        	case 4:  name = "5"; break;
        	case 5:  name = "6"; break;
        	case 6:  name = "7"; break;
        	case 7:  name = "8"; break;
        	case 8:  name = "9"; break;
        	
        	default:  name = "1"; break;
        	
        	}
        	if(3 == prefs.getInt(leveller[i], 0)){ say = i;
            	levelMenuRegLevel[i] = TextureRegionFactory.createFromAsset(levelMenuLevel[i], this, "gfx/"+name+"no3.png", 0, 0);
            	}
        	else if(2 == prefs.getInt(leveller[i], 0)){ say = i;
        		levelMenuRegLevel[i] = TextureRegionFactory.createFromAsset(levelMenuLevel[i], this, "gfx/"+name+"no2.png", 0, 0);
        		}
        	else if(1 == prefs.getInt(leveller[i], 0)){ say = i;
        		levelMenuRegLevel[i] = TextureRegionFactory.createFromAsset(levelMenuLevel[i], this, "gfx/"+name+"no1.png", 0, 0);
        		}
        	else
            levelMenuRegLevel[i] = TextureRegionFactory.createFromAsset(levelMenuLevel[i], this, "gfx/lockedLevel.png", 0, 0); 
        	
        }
        if(say+1 < 9)
levelMenuRegLevel[say+1] = TextureRegionFactory.createFromAsset(levelMenuLevel[say+1], this, "gfx/"+levelString[say+1]+"no.png", 0, 0);
        
        if (prefs.getBoolean("firstrun", true)) {
        	for(int i=0;i<10;i++)
        	 mPrefsEditor.putInt(leveller[i],0);
        	
         mPrefsEditor.commit();//kaydettik
          levelMenuRegLevel[0] = TextureRegionFactory.createFromAsset(levelMenuLevel[0], this, "gfx/1no.png", 0, 0);
         prefs.edit().putBoolean("firstrun", false).commit();//ilk defa calýsýp calýs
        }
        
        MusicFactory.setAssetBasePath("mfx/");
        try
        {
        	 this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this,"backg.mp3");
             this.mMusic.setLooping(true);
            soundOfBomb = SoundFactory.createSoundFromAsset(getSoundManager(),this, "gfx/bomb.wav");
            soundOfHit = SoundFactory.createSoundFromAsset(getSoundManager(),this, "gfx/blip.wav");
            soundOfClick = SoundFactory.createSoundFromAsset(getSoundManager(),this, "gfx/tiklama.wav");
            		}
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Texture [] textures = {bText,moreTexture,bgTexture,textureDrop,textureSepet,TextureSepetDolu,engelResim1,geciciEngel1,engelResim2,geciciEngel2,
        		engelResim3,geciciEngel3,engelResim4,geciciEngel4,textureYumurta,kanat1.texture,kanat2.texture,star1.texture,star2.texture,star3.texture,bomb1.texture,bomb2.texture,bomb3.texture,
        		textureAnime,anaMenuArka,anaMenuOyna,anaMenuSounds,anaMenuSoundsOff,anaMenuMusics,anaMenuMusicsOff,anaMenuCikis,pauseMenuArka,pauseMenuStar1,pauseMenuStar2,levelMenuArka,
        		levelMenuBackButton,pauseMenuStar3,pauseMenuMenu, pauseMenuRestart, pauseMenuResume,pauseMenuForward,pauseMenuBrokenEgg,
        		textParcacik1,textParcacik2,textParcacik3};
       
        mEngine.getTextureManager().loadTextures(textures);
        mEngine.getTextureManager().loadTextures(levelMenuLevel);
        
        this.font = FontFactory.createStrokeFromAsset(this.bText, this, "gfx/baveuse3.ttf", 50, true, Color.WHITE, 2, Color.WHITE);
        mEngine.getFontManager().loadFont(font);
    }
    @Override
    public Scene onLoadScene() {
            this.mEngine.registerUpdateHandler(new FPSLogger());
            this.sahneOyun = new Scene();
            this.sahneAnaMenu = new Scene();
            this.sahneLevel = new Scene();
            
            startAppAd.showAd(); // show the ad
			startAppAd.loadAd(); // load the next ad
			
            this.sahnePauseMenu = new CameraScene(this.camera);
            this.physicsWorld = new PhysicsWorld(new Vector2(0,SensorManager.GRAVITY_EARTH), false);
           
            particleEmitterParcacik1 = new CircleOutlineParticleEmitter(264,114,10,10);
            particleEmitterParcacik2 = new CircleOutlineParticleEmitter(414,80,10,10);
            particleEmitterParcacik3 = new CircleOutlineParticleEmitter(564,114,10,10);
            
            parcacikSistemi1 = new ParticleSystem(particleEmitterParcacik1,25,250,100,textRegParcacik1);
            parcacikSistemi2 = new ParticleSystem(particleEmitterParcacik2,25,250,100,textRegParcacik2);
            parcacikSistemi3 = new ParticleSystem(particleEmitterParcacik3,25,250,100,textRegParcacik3);
            
        	parcacikSistemi1.addParticleInitializer(new VelocityInitializer(-100,100,-100,100));
    		parcacikSistemi1.addParticleInitializer( new RotationInitializer(0.0f,145.0f));
    		parcacikSistemi1.addParticleModifier(new ScaleModifier(1.0f,0.20f,0.5f,2.0f));
    		parcacikSistemi1.addParticleModifier(new ExpireModifier(1.5f));
    		parcacikSistemi1.setParticlesSpawnEnabled(false);
            
            
    		parcacikSistemi2.addParticleInitializer(new VelocityInitializer(-100,100,-100,100));
    		parcacikSistemi2.addParticleInitializer( new RotationInitializer(0.0f,145.0f));
    		parcacikSistemi2.addParticleModifier(new ScaleModifier(1.0f,0.20f,0.5f,2.0f));
    		parcacikSistemi2.addParticleModifier(new ExpireModifier(1.5f));
    		parcacikSistemi2.setParticlesSpawnEnabled(false);
    		
    		parcacikSistemi3.addParticleInitializer(new VelocityInitializer(-100,100,-100,100));
    		parcacikSistemi3.addParticleInitializer( new RotationInitializer(0.0f,145.0f));
    		parcacikSistemi3.addParticleModifier(new ScaleModifier(1.0f,0.20f,0.5f,2.0f));
    		parcacikSistemi3.addParticleModifier(new ExpireModifier(1.5f));
    		parcacikSistemi3.setParticlesSpawnEnabled(false);
    		
    		
    		
            anaMenuNesneleriniOlustur();
            levelMenuNesneleriniOlustur();
    		pauseMenuNesneleriniOlustur();
            
 this.physicsWorld.setContactListener(new ContactListener(){
				@Override
				public void beginContact(Contact contact) {
					// TODO Auto-generated method stub
					if(contact.getFixtureA().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(spriteYumurta)){
						
						if(contact.getFixtureB().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(star1.sprite)){
							if(sound)
							soundOfHit.play();
							levels[current].setFirstStar(true);
							star1.sprite.setVisible(false);
							star1.body.setActive(false);
						}
					}
if(contact.getFixtureA().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(spriteYumurta)){
						
						if(contact.getFixtureB().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(star2.sprite)){
							if(sound)
							soundOfHit.play();
							levels[current].setSecondStar(true);
							star2.sprite.setVisible(false);
							star2.body.setActive(false);
						}
					}
if(contact.getFixtureA().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(spriteYumurta)){
	if(contact.getFixtureB().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(star3.sprite)){
		if(sound)
		soundOfHit.play();
		levels[current].setThirdStar(true);
		star3.sprite.setVisible(false);
		star3.body.setActive(false);
	}
}
if(contact.getFixtureA().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(spriteYumurta)){
	if(contact.getFixtureB().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(bomb2.sprite) ){
		if(sound)
		soundOfBomb.play();
		spriteYumurta.setVisible(false);
		bodyYumurta.setActive(false);
		
		animSpriteAnime.setPosition(bomb2.sprite.getX()-25, bomb2.sprite.getY()-25);
		animSpriteAnime.setVisible(true);
		animSpriteAnime.animate(70);
		
		bomb2.sprite.setVisible(false);
		bomb2.body.setActive(false);
		
		mEngine.registerUpdateHandler(timerOyunSuresi = new TimerHandler(1.12f, false, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				animSpriteAnime.setVisible(false);
				animSpriteAnime.stopAnimation(0);
				patladiMi = true;
			}
		}));
	}
	if(contact.getFixtureA().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(spriteYumurta)){
		if(contact.getFixtureB().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(bomb3.sprite)){
			if(sound)
			soundOfBomb.play();
			spriteYumurta.setVisible(false);
			bodyYumurta.setActive(false);
			
			animSpriteAnime.setPosition(bomb3.sprite.getX()-25, bomb3.sprite.getY()-25);
			animSpriteAnime.setVisible(true);
			animSpriteAnime.animate(70);
			
			bomb3.sprite.setVisible(false);
			bomb3.body.setActive(false);
			
			mEngine.registerUpdateHandler(timerOyunSuresi = new TimerHandler(1.12f, false, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) 
				{
					animSpriteAnime.setVisible(false);
					animSpriteAnime.stopAnimation(0);
					patladiMi= true;
				}
			}));
		}
}
if(contact.getFixtureA().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(spriteYumurta)){
		if(contact.getFixtureB().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(bomb1.sprite)){
			if(sound)
			soundOfBomb.play();
			spriteYumurta.setVisible(false);
			bodyYumurta.setActive(false);
			
			animSpriteAnime.setPosition(bomb1.sprite.getX()-25, bomb1.sprite.getY()-25);
			animSpriteAnime.setVisible(true);
			animSpriteAnime.animate(70);
			
			bomb1.sprite.setVisible(false);
			bomb1.body.setActive(false);
			
			mEngine.registerUpdateHandler(timerOyunSuresi = new TimerHandler(1.12f, false, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) 
				{
					animSpriteAnime.setVisible(false);
					animSpriteAnime.stopAnimation(0);
					patladiMi= true;
				}
			}));
		}
		}
	if(contact.getFixtureA().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(spriteYumurta)){
		if(contact.getFixtureB().getBody() == physicsWorld.getPhysicsConnectorManager().findBodyByShape(spriteSepet)){
			yakalanma = true;
			spriteSepet.setVisible(false);
			spriteDoluSepet.setVisible(true);
			spriteYumurta.setVisible(false);
			
		}
	}

}
				}
				@Override
				public void endContact(Contact contact) {/* TODO Auto-generated method stub*/}
				@Override
				public void preSolve(Contact contact, Manifold oldManifold){/* TODO Auto-generated method stub*/}
				@Override
				public void postSolve(Contact contact, ContactImpulse impulse){/* TODO Auto-generated method stub*/}
            });
            this.sahneOyun.registerUpdateHandler(this.physicsWorld);
            
            sahneOyun.registerUpdateHandler(new IUpdateHandler() {
                @Override
                public void reset() { }
                @Override
                public void onUpdate(final float pSecondsElapsed) {
                	
                	if(yakalanma){//top sepete girdiginde
                		pauseMenuResumeSprite.setPosition(-500, -500);
                		if(levels[current].getFirstStar() || levels[current].getSecondStar() || levels[current].getThirdStar())//en az bi yýldýz alsýn
                		pauseMenuForwardSprite.setPosition(467,249);
                		
                		if(levels[current].getFirstStar()){
                			pauseMenuStar1Sprite.setVisible(true);
                			particleEmitterParcacik1.setCenter(pauseMenuStar1Sprite.getX()+64, pauseMenuStar1Sprite.getY()+64);
                			parcacikSistemi1.setParticlesSpawnEnabled(true);
                		}
                		if(levels[current].getSecondStar()){
                			pauseMenuStar2Sprite.setVisible(true);
                			particleEmitterParcacik2.setCenter(pauseMenuStar2Sprite.getX()+64, pauseMenuStar2Sprite.getY()+64);
                			parcacikSistemi2.setParticlesSpawnEnabled(true);
                		}
                		if(levels[current].getThirdStar()){
                			pauseMenuStar3Sprite.setVisible(true);
                			particleEmitterParcacik3.setCenter(pauseMenuStar3Sprite.getX()+64, pauseMenuStar3Sprite.getY()+64);
                			parcacikSistemi3.setParticlesSpawnEnabled(true);
                		}
    					sahneOyun.setChildScene(sahnePauseMenu);
    					//Oyunun durdurulmasýný saðlayan kod bloðu. Fiziksel özelliklerin tamamý durdurulur.
    					sahneOyun.unregisterUpdateHandler(physicsWorld);
                		
                	}
                	if(spriteYumurta.getY() < -240 || spriteYumurta.getY() > 520 || spriteYumurta.getX() < -40 || spriteYumurta.getX() > 940  )//ekrandan cýkýp cýkmadýgý
    				{
                		parcacikSistemi1.setVisible(false);
    					parcacikSistemi2.setVisible(false);
    					parcacikSistemi3.setVisible(false);
    					pauseMenuResumeSprite.setPosition(-200, -200);//görünmesin diye
    					sahneOyun.setChildScene(sahnePauseMenu);
    					//Oyunun durdurulmasýný saðlayan kod bloðu. Fiziksel özelliklerin tamamý durdurulur.
    					sahneOyun.unregisterUpdateHandler(physicsWorld);
    				}
    				if(patladiMi) // patladiysa
    				{
    					parcacikSistemi1.setVisible(false);
    					parcacikSistemi2.setVisible(false);
    					parcacikSistemi3.setVisible(false);
    					pauseMenuBrokenEggSprite.setPosition(280, 0);
    					ctextLevel.setVisible(false);
    					pauseMenuResumeSprite.setPosition(-200, -200);
    					sahneOyun.setChildScene(sahnePauseMenu);
    					//Oyunun durdurulmasýný saðlayan kod bloðu. Fiziksel özelliklerin tamamý durdurulur.
    					sahneOyun.unregisterUpdateHandler(physicsWorld);
    				}
                }
        });
            return this.sahneAnaMenu;
    }
    @Override
	public void onLoadComplete() 
	{
		StartAppAd.init(this, "106611313", "201781068");
		StartAppSearch.init(this, "106611313", "201781068");

		startAppAd.onResume();
		
	}
    public void setLevelImage(){
    	String name = null;
    	
    	switch(current){
    	
    	case 0 : name = "1"; break;
    	case 1 : name = "2"; break;
    	case 2:  name = "3"; break;
    	case 3:  name = "4"; break;
    	case 4:  name = "5"; break;
    	case 5:  name = "6"; break;
    	case 6:  name = "7"; break;
    	case 7:  name = "8"; break;
    	case 8:  name = "9"; break;
    	
    	default:  name = "1"; break;
    	
    	}
    	
     	if(levels[current].getFirstStar() && levels[current].getSecondStar() && levels[current].getThirdStar() && yakalanma){
        	levelMenuRegLevel[current] = TextureRegionFactory.createFromAsset(levelMenuLevel[current], this, "gfx/"+name+"no3.png", 0, 0);
        	if(prefs.getInt(leveller[current+1], 0) == 0 && current+1 <9 ){
        	levelMenuRegLevel[current+1] = TextureRegionFactory.createFromAsset(levelMenuLevel[current+1], this, "gfx/"+levelString[current+1]+"no.png", 0, 0);
        	this.sahneLevel.registerTouchArea(levelMenuLockedLevelSprite[current+1]);
        	}
        	mPrefsEditor.putInt(leveller[current],3);
			mPrefsEditor.commit();//kaydettik
    }
     	else if(((levels[current].getFirstStar() && levels[current].getSecondStar()) || (levels[current].getFirstStar() && levels[current].getThirdStar()) || (levels[current].getSecondStar() && levels[current].getThirdStar()))  && yakalanma){
          	levelMenuRegLevel[current] = TextureRegionFactory.createFromAsset(levelMenuLevel[current], this, "gfx/"+name+"no2.png", 0, 0); 
          	if(prefs.getInt(leveller[current+1], 0) == 0  && current+1 <9){
          	levelMenuRegLevel[current+1] = TextureRegionFactory.createFromAsset(levelMenuLevel[current+1], this, "gfx/"+levelString[current+1]+"no.png", 0, 0);
          	this.sahneLevel.registerTouchArea(levelMenuLockedLevelSprite[current+1]);
          	}
          	mPrefsEditor.putInt(leveller[current],2);
			mPrefsEditor.commit();//kaydettik
     	}
     	else if((levels[current].getFirstStar() || levels[current].getSecondStar() || levels[current].getThirdStar()) && yakalanma){
       	levelMenuRegLevel[current] = TextureRegionFactory.createFromAsset(levelMenuLevel[current], this, "gfx/"+name+"no1.png", 0, 0); 
       	if(prefs.getInt(leveller[current+1], 0) == 0  && current+1 <9){
       	levelMenuRegLevel[current+1] = TextureRegionFactory.createFromAsset(levelMenuLevel[current+1], this, "gfx/"+levelString[current+1]+"no.png", 0, 0);
       	this.sahneLevel.registerTouchArea(levelMenuLockedLevelSprite[current+1]);
       	}
       	mPrefsEditor.putInt(leveller[current],1);
		mPrefsEditor.commit();//kaydettik
     	}
     	
     	
    }
    private void anaMenuNesneleriniOlustur()
	{
    	 MainActivity.this.mMusic.play();
		anaMenuArkaSprite = new Sprite(0, 0, anaMenuRegArka);
		
		moreSprite = new Sprite(620, 180, moreReg)
		{
			@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
    			if(pSceneTouchEvent.isActionDown())
    			{
    				if(sound)
    				soundOfClick.play();
    				moreSprite.setScaleX((float) 1.2);
    				moreSprite.setScaleY((float) 1.2);
    				 Intent intent = new Intent();
    			        intent.setAction(Intent.ACTION_VIEW);
    			        intent.addCategory(Intent.CATEGORY_BROWSABLE);
    			        intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=CUNORAZ"));
    			        startActivity(intent);
    			}
    			if(pSceneTouchEvent.isActionUp())
    			{   
    				moreSprite.setScaleX(1);
    				moreSprite.setScaleY(1);
    			}
                return true;
            }
		};
		
		anaMenuOynaSprite = new Sprite(250, 157, anaMenuRegOyna)
		{
			@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
    			if(pSceneTouchEvent.isActionDown())
    			{
    				if(sound)
    				soundOfClick.play();
    				anaMenuOynaSprite.setScaleX((float) 1.2);
    				anaMenuOynaSprite.setScaleY((float) 1.2);
    			}
    			if(pSceneTouchEvent.isActionUp())
    			{   
    				anaMenuOynaSprite.setScaleX(1);
    				anaMenuOynaSprite.setScaleY(1);
    				anaMenuSahnesiMi = false;
    				levelMenuSahnesiMi = true;
    				mEngine.setScene(sahneLevel);
    			}
                return true;
            }
		};
		
		anaMenuSoundsSpriteOff = new Sprite(-250,-250,anaMenuRegSoundsOff){
			@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
    			if(pSceneTouchEvent.isActionDown())
    			{
    				if(sound)
    				soundOfClick.play();
    				sound = true;
    				anaMenuSoundsSpriteOff.setScaleX((float) 1.2);
    				anaMenuSoundsSpriteOff.setScaleY((float)1.2);
    				anaMenuSoundsSprite.setPosition(250, 250);
    				anaMenuSoundsSpriteOff.setPosition(-250,-250);
    				
    			}
    			if(pSceneTouchEvent.isActionUp())
    			{   
    				anaMenuSoundsSpriteOff.setScaleX(1);
    				anaMenuSoundsSpriteOff.setScaleY(1);
    			}
                return true;
            }
		};
		anaMenuSoundsSprite = new Sprite(250,250,anaMenuRegSounds)	{
			@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
    			if(pSceneTouchEvent.isActionDown())
    			{
    				if(sound)
    				soundOfClick.play();
    				sound = false;
    				anaMenuSoundsSprite.setScaleX((float) 1.2);
    				anaMenuSoundsSprite.setScaleY((float)1.2);
    				anaMenuSoundsSpriteOff.setPosition(250, 250);
    				anaMenuSoundsSprite.setPosition(-250,-250);
    				
    			}
    			if(pSceneTouchEvent.isActionUp())
    			{   
    				anaMenuSoundsSprite.setScaleX(1);
    				anaMenuSoundsSprite.setScaleY(1);
    			}
                return true;
            }
		};/////////////////////////////////////////////////////////////////////////
		anaMenuMusicsSpriteOff = new Sprite(-250,-250,anaMenuRegMusicsOff){
			@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
    			if(pSceneTouchEvent.isActionDown())
    			{
    				if(sound)
    				soundOfClick.play();
    				anaMenuMusicsSpriteOff.setScaleX((float) 1.2);
    				anaMenuMusicsSpriteOff.setScaleY((float)1.2);
    				MainActivity.this.mMusic.setVolume(1);
    				anaMenuMusicsSprite.setPosition(380, 250);
    				anaMenuMusicsSpriteOff.setPosition(-250,-250);
    				
    			}
    			if(pSceneTouchEvent.isActionUp())
    			{   
    				anaMenuMusicsSpriteOff.setScaleX(1);
    				anaMenuMusicsSpriteOff.setScaleY(1);
    			}
                return true;
            }
		};
		anaMenuMusicsSprite = new Sprite(380,250,anaMenuRegMusics)	{
			@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
    			if(pSceneTouchEvent.isActionDown())
    			{
    				if(sound)
    				soundOfClick.play();
    				anaMenuMusicsSprite.setScaleX((float) 1.2);
    				anaMenuMusicsSprite.setScaleY((float)1.2);
    				MainActivity.this.mMusic.setVolume(0);
    				anaMenuMusicsSpriteOff.setPosition(380, 250);
    				anaMenuMusicsSprite.setPosition(-250,-250);
    				
    			}
    			if(pSceneTouchEvent.isActionUp())
    			{   
    				anaMenuMusicsSprite.setScaleX(1);
    				anaMenuMusicsSprite.setScaleY(1);
    			}
                return true;
            }
		};
		
		
  anaMenuCikisSprite = new Sprite(250, 340, anaMenuRegCikis)
		{
			@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
    			if(pSceneTouchEvent.isActionDown())
    			{
    				if(sound)
    				soundOfClick.play();
    				anaMenuCikisSprite.setScaleX((float) 1.2);
    				anaMenuCikisSprite.setScaleY((float)1.2);
    			}
    			if(pSceneTouchEvent.isActionUp())
    			{   
    	            System.exit(0);
    			}
                return true;
            }
		};
		this.sahneAnaMenu.attachChild(anaMenuArkaSprite);
		this.sahneAnaMenu.attachChild(anaMenuOynaSprite);
		this.sahneAnaMenu.attachChild(moreSprite);
		this.sahneAnaMenu.attachChild(anaMenuSoundsSprite);
		this.sahneAnaMenu.attachChild(anaMenuSoundsSpriteOff);
		this.sahneAnaMenu.attachChild(anaMenuMusicsSprite);
		this.sahneAnaMenu.attachChild(anaMenuMusicsSpriteOff);
		this.sahneAnaMenu.attachChild(anaMenuCikisSprite);
		
		this.sahneAnaMenu.registerTouchArea(moreSprite);
		this.sahneAnaMenu.registerTouchArea(anaMenuSoundsSprite);
		this.sahneAnaMenu.registerTouchArea(anaMenuSoundsSpriteOff);
		this.sahneAnaMenu.registerTouchArea(anaMenuMusicsSprite);
		this.sahneAnaMenu.registerTouchArea(anaMenuMusicsSpriteOff);
		this.sahneAnaMenu.registerTouchArea(anaMenuOynaSprite);
		this.sahneAnaMenu.registerTouchArea(anaMenuCikisSprite);
		
	}
    
    private void levelMenuNesneleriniOlustur() {

    	levelMenuArkaSprite = new Sprite(0,0,levelMenuRegArka);
    	
    	levelMenuBackButtonSprite = new Sprite(20,400,levelMenuRegBackButton){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{
					if(sound)
					soundOfClick.play();
					levelMenuBackButtonSprite.setScaleX((float) 1.2);
					levelMenuBackButtonSprite.setScaleY((float) 1.2);
					
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					levelMenuBackButtonSprite.setScaleX(1);
					levelMenuBackButtonSprite.setScaleY(1);
					levelMenuSahnesiMi = false;
					anaMenuSahnesiMi = true;
					mEngine.setScene(sahneAnaMenu);	
				}
				return true;
			}
		};
    	
    	levelMenuLockedLevelSprite[0] = new Sprite (80,70,levelMenuRegLevel[0]){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{
					if(sound)
					soundOfClick.play();
					levelMenuLockedLevelSprite[0].setScaleX((float) 1.2);
    				levelMenuLockedLevelSprite[0].setScaleY((float) 1.2);
    				current = 0;
    				ctextLevel.setText("Level "+(current+1));
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					levelMenuLockedLevelSprite[0].setScaleX(1);
					levelMenuLockedLevelSprite[0].setScaleY((float) 1.1);
    				levelMenuSahnesiMi = false;
    				oyunSahnesiMi = true;
    				oyunNesneleriniOlustur();// enigne nesnesi aracýlýðý ile setScene metodunu kullanarak sahne oyunu gösterilecek sahne olarak ayarlýyoruz
    				mEngine.setScene(sahneOyun);
				}
				return true;
			}
		};
		levelMenuLockedLevelSprite[1] = new Sprite (208,70,levelMenuRegLevel[1]){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{
					if(sound)
					soundOfClick.play();
					levelMenuLockedLevelSprite[1].setScaleX((float) 1.2);
    				levelMenuLockedLevelSprite[1].setScaleY((float) 1.2);
    				current = 1;
    				ctextLevel.setText("Level "+(current+1));
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					levelMenuLockedLevelSprite[1].setScaleX(1);
					levelMenuLockedLevelSprite[1].setScaleY((float) 1.1);
    				levelMenuSahnesiMi = false;
    				oyunSahnesiMi = true;
    				oyunNesneleriniOlustur1();
    				mEngine.setScene(sahneOyun);
				}
				return true;
			}
		};
		levelMenuLockedLevelSprite[2] = new Sprite (336,70,levelMenuRegLevel[2]){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{	
					if(sound)
					soundOfClick.play();
					levelMenuLockedLevelSprite[2].setScaleX((float) 1.2);
    				levelMenuLockedLevelSprite[2].setScaleY((float) 1.2);
    				current = 2;
    				ctextLevel.setText("Level "+(current+1));
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					levelMenuLockedLevelSprite[2].setScaleX(1);
					levelMenuLockedLevelSprite[2].setScaleY((float) 1.1);
    				levelMenuSahnesiMi = false;
    				oyunSahnesiMi = true;
    				oyunNesneleriniOlustur2();
    				mEngine.setScene(sahneOyun);
				}
				return true;
			}
		};
		levelMenuLockedLevelSprite[3] = new Sprite (464,70,levelMenuRegLevel[3]){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{	
					if(sound)
					soundOfClick.play();
					levelMenuLockedLevelSprite[3].setScaleX((float) 1.2);
    				levelMenuLockedLevelSprite[3].setScaleY((float) 1.2);
    				current = 3;
    				ctextLevel.setText("Level "+(current+1));
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					levelMenuLockedLevelSprite[3].setScaleX(1);
					levelMenuLockedLevelSprite[3].setScaleY((float) 1.1);
    				levelMenuSahnesiMi = false;
    				oyunSahnesiMi = true;
    				oyunNesneleriniOlustur3();
    				mEngine.setScene(sahneOyun);
				}
				return true;
			}
		};
		levelMenuLockedLevelSprite[4] = new Sprite (592,70,levelMenuRegLevel[4]){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{
					if(sound)
					soundOfClick.play();
					levelMenuLockedLevelSprite[4].setScaleX((float) 1.2);
    				levelMenuLockedLevelSprite[4].setScaleY((float) 1.2);
    				current = 4;
    				ctextLevel.setText("Level "+(current+1));
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					levelMenuLockedLevelSprite[4].setScaleX(1);
					levelMenuLockedLevelSprite[4].setScaleY((float) 1.1);
    				levelMenuSahnesiMi = false;
    				oyunSahnesiMi = true;
    				oyunNesneleriniOlustur4();
    				mEngine.setScene(sahneOyun);
				}
				return true;
			}
		};
		
		levelMenuLockedLevelSprite[5] = new Sprite (80,220,levelMenuRegLevel[5]){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{	
					if(sound)
					soundOfClick.play();
					levelMenuLockedLevelSprite[5].setScaleX((float) 1.2);
    				levelMenuLockedLevelSprite[5].setScaleY((float) 1.2);
    				current = 5;
    				ctextLevel.setText("Level "+(current+1));
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					levelMenuLockedLevelSprite[5].setScaleX(1);
					levelMenuLockedLevelSprite[5].setScaleY((float) 1.1);
    				levelMenuSahnesiMi = false;
    				oyunSahnesiMi = true;
    				oyunNesneleriniOlustur5();
    				mEngine.setScene(sahneOyun);
				}
				return true;
			}
		};
		
		levelMenuLockedLevelSprite[6] = new Sprite (208,220,levelMenuRegLevel[6]){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{
					if(sound)
					soundOfClick.play();
					levelMenuLockedLevelSprite[6].setScaleX((float) 1.2);
    				levelMenuLockedLevelSprite[6].setScaleY((float) 1.2);
    				current = 6;
    				ctextLevel.setText("Level "+(current+1));
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					levelMenuLockedLevelSprite[6].setScaleX(1);
					levelMenuLockedLevelSprite[6].setScaleY((float) 1.1);
    				levelMenuSahnesiMi = false;
    				oyunSahnesiMi = true;
    				oyunNesneleriniOlustur6();
    				mEngine.setScene(sahneOyun);
				}
				return true;
			}
		};
		
		levelMenuLockedLevelSprite[7] = new Sprite (336,220,levelMenuRegLevel[7]){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{
					if(sound)
					soundOfClick.play();
					levelMenuLockedLevelSprite[7].setScaleX((float) 1.2);
    				levelMenuLockedLevelSprite[7].setScaleY((float) 1.2);
    				current = 7;
    				ctextLevel.setText("Level "+(current+1));
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					levelMenuLockedLevelSprite[7].setScaleX(1);
					levelMenuLockedLevelSprite[7].setScaleY((float) 1.1);
    				levelMenuSahnesiMi = false;
    				oyunSahnesiMi = true;
    				oyunNesneleriniOlustur7();
    				mEngine.setScene(sahneOyun);
				}
				return true;
			}
		};
		levelMenuLockedLevelSprite[8] = new Sprite (464,220,levelMenuRegLevel[8]){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{
					if(sound)
					soundOfClick.play();
					levelMenuLockedLevelSprite[8].setScaleX((float) 1.2);
    				levelMenuLockedLevelSprite[8].setScaleY((float) 1.2);
    				current = 8;
    				ctextLevel.setText("Level "+(current+1));
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					levelMenuLockedLevelSprite[8].setScaleX(1);
					levelMenuLockedLevelSprite[8].setScaleY((float) 1.1);
    				levelMenuSahnesiMi = false;
    				oyunSahnesiMi = true;
    				oyunNesneleriniOlustur8();
    				mEngine.setScene(sahneOyun);
				}
				return true;
			}
		};
		levelMenuLockedLevelSprite[9] = new Sprite (592,220,levelMenuRegLevel[9]);
		
		for(int i=0;i<10;i++)
		levelMenuLockedLevelSprite[i].setScaleY((float) 1.1);
		
    	this.sahneLevel.attachChild(levelMenuArkaSprite);
    	this.sahneLevel.attachChild(levelMenuBackButtonSprite);
    	int sayac = -1;
    	if(prefs.getInt(leveller[0] , 0)>0)
    			sayac=0;
    	for(int i=0;i<10;i++){
       this.sahneLevel.attachChild(levelMenuLockedLevelSprite[i]);
       if(prefs.getInt(leveller[i], 0) > 0 && i != 0){ sayac = i;
       this.sahneLevel.registerTouchArea(levelMenuLockedLevelSprite[i]);
       } else
    	   this.sahneLevel.unregisterTouchArea(levelMenuLockedLevelSprite[i]);   
    	}
    	 this.sahneLevel.registerTouchArea(levelMenuLockedLevelSprite[0]);
    	 this.sahneLevel.registerTouchArea(levelMenuLockedLevelSprite[sayac+1]);
    	 
    	this.sahneLevel.registerTouchArea(levelMenuBackButtonSprite);
    	}
    private void pauseMenuNesneleriniOlustur()
	{
		pauseMenuArkaSprite = new Sprite(0, 0, pauseRegMenuArka);
		pauseMenuStar1Sprite = new Sprite(200, 50, pauseRegMenuStar1);
		pauseMenuStar2Sprite = new Sprite(350, 20, pauseRegMenuStar2);
		pauseMenuStar3Sprite = new Sprite(500, 50, pauseRegMenuStar3);
		
		ctextLevel = new ChangeableText(280,200,this.font,"",15);
		ctextLevel.setText("Level "+(current+1));
		
		pauseMenuStar1Sprite.setVisible(false);
		pauseMenuStar2Sprite.setVisible(false);
		pauseMenuStar3Sprite.setVisible(false);
		
		
		pauseMenuBrokenEggSprite = new Sprite (-300,-300,pauseRegMenuBrokenEgg);//görünmemesi için
		pauseMenuForwardSprite = new Sprite (-300,-300,pauseRegMenuForward) {
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
				{
					if (pSceneTouchEvent.isActionDown()) 
					{
						if(sound)
						soundOfClick.play();
						pauseMenuForwardSprite.setScaleX((float) 1.2);
						pauseMenuForwardSprite.setScaleY((float) 1.2);
						
					}
					if (pSceneTouchEvent.isActionUp()) 
					{	
						
						pauseMenuForwardSprite.setScaleX((float) 1.0);
						pauseMenuForwardSprite.setScaleY((float) 1.0);
						
						sonraki = true;
						
						restart(true);
					}
					return true;
				}
			};
		
		pauseMenuRestartSprite = new Sprite(211, 249, pauseRegMenuRestart)
		{
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{
					if(sound)
					soundOfClick.play();
					pauseMenuRestartSprite.setScaleX((float) 1.2);
					pauseMenuRestartSprite.setScaleY((float) 1.2);
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					pauseMenuRestartSprite.setScaleX((float) 1.0);
					pauseMenuRestartSprite.setScaleY((float) 1.0);
					restart(true);
				}
				return true;
			}
		};
		pauseMenuResumeSprite = new Sprite(467, 249, pauseRegMenuResume)
		{
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{
					if(sound)
					soundOfClick.play();
					pauseMenuResumeSprite.setScaleX((float) 1.2);
					pauseMenuResumeSprite.setScaleY((float) 1.2);
					
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					pauseMenuResumeSprite.setScaleX((float) 1.0);
					pauseMenuResumeSprite.setScaleY((float) 1.0);
					pauseMenuSahnesiMi = false;
    				oyunSahnesiMi = true;
    				// clearChildScene Metodu, CameraScene nesnelerini temizleyen metottur
    				// Bu metotla pauseMenu ya da finishMenu gibi menüleri temizleyebiliriz.
					sahneOyun.clearChildScene();
					//Fiziksel özellikler etkinleþtirilir.
					sahneOyun.registerUpdateHandler(physicsWorld);
				}
				return true;
			}
		};
	    pauseMenuMenuSprite = new Sprite(339, 249, pauseRegMenuMenu)
		{
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if (pSceneTouchEvent.isActionDown()) 
				{
					if(sound)
					soundOfClick.play();
					pauseMenuMenuSprite.setScaleX((float) 1.2);
					pauseMenuMenuSprite.setScaleY((float) 1.2);
				}
				if (pSceneTouchEvent.isActionUp()) 
				{
					pauseMenuMenuSprite.setScaleX((float) 1.0);
					pauseMenuMenuSprite.setScaleY((float) 1.0);
    				restart(false);
    				
    				levelMenuSahnesiMi = true;
    				anaMenuSahnesiMi = false;
    				oyunSahnesiMi = false;
    				mEngine.setScene(sahneLevel);
				}
				return true;
			}
		};
		sahnePauseMenu.attachChild(pauseMenuArkaSprite);
		sahnePauseMenu.attachChild(ctextLevel);//yazý için
		sahnePauseMenu.attachChild(parcacikSistemi1);
		sahnePauseMenu.attachChild(parcacikSistemi2);
		sahnePauseMenu.attachChild(parcacikSistemi3);
		sahnePauseMenu.attachChild(pauseMenuStar1Sprite);
		sahnePauseMenu.attachChild(pauseMenuStar2Sprite);
		sahnePauseMenu.attachChild(pauseMenuStar3Sprite);
		sahnePauseMenu.attachChild(pauseMenuMenuSprite);
		sahnePauseMenu.attachChild(pauseMenuRestartSprite);
		sahnePauseMenu.attachChild(pauseMenuResumeSprite);
		sahnePauseMenu.attachChild(pauseMenuForwardSprite);
		sahnePauseMenu.attachChild(pauseMenuBrokenEggSprite);
		
		sahnePauseMenu.registerTouchArea(pauseMenuMenuSprite);
		sahnePauseMenu.registerTouchArea(pauseMenuRestartSprite);
		sahnePauseMenu.registerTouchArea(pauseMenuResumeSprite);
		sahnePauseMenu.registerTouchArea(pauseMenuForwardSprite);
		
		// Pause menu gibi þeffaf sahneler için gerekli iki kod satýrý.
		this.sahneAnaMenu.setTouchAreaBindingEnabled(true);
		this.sahnePauseMenu.setBackgroundEnabled(false);
	}
    private void restart(boolean t){//degiskenler ilk hallerine dönüyor
    	
    	setLevelImage();
    
    	sahneOyun.unregisterUpdateHandler(physicsWorld);
		oyunSahnesiMi = true;
		pauseMenuSahnesiMi = false;
		
		ctextLevel.setVisible(true);
		parcacikSistemi1.setVisible(true);
		parcacikSistemi2.setVisible(true);
		parcacikSistemi3.setVisible(true);
		
		sahneOyun.registerUpdateHandler(physicsWorld);//Fiziksel özellikler etkinleþtirilir.
    	
    	yakalanma = false;
    	drop = false;
    	patladiMi = false;
    	basladi = false;
    	
    	levels[current].setFirstStar(false); 
    	levels[current].setSecondStar(false); 
    	levels[current].setThirdStar(false); 
    	
    	parcacikSistemi1.setParticlesSpawnEnabled(false);
    	parcacikSistemi2.setParticlesSpawnEnabled(false);
    	parcacikSistemi3.setParticlesSpawnEnabled(false);
    		
    		
			pauseMenuStar1Sprite.setVisible(false); 
			pauseMenuStar2Sprite.setVisible(false);
			pauseMenuStar3Sprite.setVisible(false);
    	
    	  bodySepet.setActive(false);  bodyYumurta.setActive(false);  
    	  if(current!=6)
    		  bodyEngel1.setActive(false); 
    	  if(current != 0 && current!=5)
    		  bodyEngel2.setActive(false); 
    	  if(current !=0 && current!=1 && current!=6)
        	  bodyEngel3.setActive(false);
    	  if(current ==4 || current==8)//burda var bi bokluk ya da yok
    	  bodyEngel4.setActive(false);
    	  
    	
    	  if(current == 5 || current == 6 || current == 7 || current == 8)
    	  kanat1.body.setActive(false);
    	  if(current == 6 || current ==8)
    	  kanat2.body.setActive(false);
    	  
          star1.body.setActive(false);  star2.body.setActive(false); star3.body.setActive(false); 
          bomb2.body.setActive(false);
          
          if(current!=0)
          bomb3.body.setActive(false);
          if(current != 0 && current!=1 && current!=3 && current!=6)
          bomb1.body.setActive(false);
          
          this.sahneOyun.detachChild(bgSprite);
    	  this.sahneOyun.detachChild(spriteDrop);
          this.sahneOyun.detachChild(animSpriteAnime);
          this.sahneOyun.detachChild(spriteSepet);
          this.sahneOyun.detachChild(spriteDoluSepet);
          if(current!=6){
          this.sahneOyun.detachChild(spriteEngel1); 
          this.sahneOyun.detachChild(geciciEngel1Sprite); 
          }
          if(current != 0 && current!=5){
          this.sahneOyun.detachChild(spriteEngel2);
          this.sahneOyun.detachChild(geciciEngel2Sprite);
          }
          if(current !=0 && current!=1 && current!=6){
          this.sahneOyun.detachChild(spriteEngel3);
          this.sahneOyun.detachChild(geciciEngel3Sprite);
          }
          if(current ==4 || current==8){
         this.sahneOyun.detachChild(spriteEngel4);
         this.sahneOyun.detachChild(geciciEngel4Sprite);
          }
          this.sahneOyun.detachChild(spriteYumurta);
          if(current == 5 || current == 6 || current == 7 || current == 8)
          this.sahneOyun.detachChild(kanat1.sprite);
          
          if(current ==6 || current ==8)
          this.sahneOyun.detachChild(kanat2.sprite);
          
          this.sahneOyun.detachChild(star1.sprite);
          this.sahneOyun.detachChild(star2.sprite);
          this.sahneOyun.detachChild(star3.sprite);
          
          if(current != 0 && current!=1 && current!=3 && current!=6)
          this.sahneOyun.detachChild(bomb1.sprite);//bomb1 olduguna bakma sonradan gelen bomba bu
          
          this.sahneOyun.detachChild(bomb2.sprite);
          if(current!=0)
          this.sahneOyun.detachChild(bomb3.sprite);
          
          if(current!=6)
          this.sahneOyun.unregisterTouchArea(geciciEngel1Sprite); 
          if(current != 0 && current!=5)
          this.sahneOyun.unregisterTouchArea(geciciEngel2Sprite);
          if(current !=0 && current!=1 && current!=6)
          this.sahneOyun.unregisterTouchArea(geciciEngel3Sprite);
          if(current ==4 || current==8)
          this.sahneOyun.unregisterTouchArea(geciciEngel4Sprite);
          
          this.sahneOyun.unregisterTouchArea(spriteDrop);
		
		pauseMenuResumeSprite.setPosition(467,249);
		pauseMenuBrokenEggSprite.setPosition(-500,-500);//görünmemesi icin
		pauseMenuForwardSprite.setPosition(-500, -500);

		if(sonraki){
		current++;
		 this.sahneLevel.registerTouchArea(levelMenuLockedLevelSprite[current]);//////////////////
		 ctextLevel.setText("Level "+(current+1));//problem burda biliyorum
		if(current+1 == 10){
			ctextLevel.setPosition(200,200);
			ctextLevel.setText("Coming Soon :)");
		}
		sonraki = false;
		}
		//elemanlarý yerleþtir
		if(current == 0 && t)
			oyunNesneleriniOlustur();
		else if(current == 1 && t )
		oyunNesneleriniOlustur1();
		else if(current == 2 && t )
			oyunNesneleriniOlustur2();
		else if(current == 3 && t )
			oyunNesneleriniOlustur3();
		else if(current == 4 && t )
			oyunNesneleriniOlustur4();
		else if(current == 5 && t )
			oyunNesneleriniOlustur5();
		else if(current == 6 && t )
			oyunNesneleriniOlustur6();
		else if(current == 7 && t )
			oyunNesneleriniOlustur7();
		else if(current == 8 && t )
			oyunNesneleriniOlustur8();
	}
 // Fiziksel tuþlarýn kullanýmýna olanak veren onKeyDown Metodu
    @Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent)
	{
		// Geri Tuþuna basýldýðýnda yapýlacaklar
		if(pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN) 
		{
			if(oyunSahnesiMi)
			{
				pauseMenuSahnesiMi = true;
				oyunSahnesiMi = false;
				parcacikSistemi1.setVisible(false); 
				parcacikSistemi2.setVisible(false); 
				parcacikSistemi3.setVisible(false);
				//Oyunun durdurulmasýný saðlayan kod bloðu. Fiziksel özelliklerin tamamý durdurulur.
				sahneOyun.unregisterUpdateHandler(physicsWorld);
				sahneOyun.setChildScene(sahnePauseMenu);
				
			}
			else if(pauseMenuSahnesiMi)
			{
				pauseMenuSahnesiMi = false;
				oyunSahnesiMi = true;
				sahneOyun.registerUpdateHandler(physicsWorld);
				sahneOyun.clearChildScene();
			}
			else if(levelMenuSahnesiMi){
				levelMenuSahnesiMi = false;
				anaMenuSahnesiMi = true;
				mEngine.setScene(sahneAnaMenu);
			}
			else if(anaMenuSahnesiMi)
			{
				System.exit(0);
			}
			else
			{}
			return true;
		}
		// Menu tuþuna basýldýðýnda yapýlacaklar
		else if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) 
		{		
			// Bu kýsýmda bir þey yapmaya gerek duymadým
			// Ama istenilen bir görev buraya yazýlabilir.
			return true;
		}
		else 
		{
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}
 		
 	private void oyunNesneleriniOlustur()
 		{
 		sahneOyun.clearChildScene();
		bgReg = TextureRegionFactory.createFromAsset(bgTexture, this, "gfx/bg8.png", 0, 0); 
        animSpriteAnime = new AnimatedSprite(470, 225, this.tiledTextRegAnime);
            
            spriteDrop = new Sprite(650, 25, textRegDrop){
            	@Override
            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
            	{ 
            		bodyYumurta.setType(BodyType.DynamicBody);
            		if (pSceneTouchEvent.isActionDown())
            		{
            			basladi = true;
            			spriteDrop.setScaleX((float) 1.3);
            			spriteDrop.setScaleY((float) 1.3);
            		}
            		if (pSceneTouchEvent.isActionUp())
            		{
            			spriteDrop.setScaleX(1);
            			spriteDrop.setScaleY(1);
            	}
            		return true;
            	}
            };
            bgSprite = new Sprite(0, 0, bgReg);
            spriteSepet = new Sprite(460, 340, textRegSepet);//sepet
            spriteDoluSepet = new Sprite(460, 340, textRegSepetDolu);//sepet
            spriteDoluSepet.setVisible(false);
            
            bodySepet = PhysicsFactory.createCircleBody(this.physicsWorld, spriteSepet, BodyType.StaticBody, FIXTURE_DEF);
                    
            spriteYumurta = new Sprite (50, 30, textRegYumurta);//yumurta
            bodyYumurta =  PhysicsFactory.createBoxBody(this.physicsWorld, spriteYumurta, BodyType.StaticBody, FIXTURE_DEF);
           
         
          
        spriteEngel1 = new Sprite(dokunulanNoktaX,dokunulanNoktaY,engelRegResim1);
            
        bodyEngel1 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel1, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);

        geciciEngel1Sprite = new Sprite(dokunulanNokta1X, dokunulanNokta1Y, geciciRegEngel1){
            	@Override
            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
            	{ 

            	if(pSceneTouchEvent.isActionDown())
				{
            		dokunulanNokta1X = pSceneTouchEvent.getX();
            		dokunulanNokta1Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionMove())
				{
					dokunulanNokta1X = pSceneTouchEvent.getX();
					dokunulanNokta1Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionUp())
				{
				}
				return true; 
            }
            protected void onManagedUpdate(float pSecondsElapsed) 
			{
            	if(!basladi){//basladýysa engel hareket etmemeli
            	bodyEngel1.setTransform(dokunulanNokta1X/32,dokunulanNokta1Y/32,260);
            	geciciEngel1Sprite.setPosition(dokunulanNokta1X-64,dokunulanNokta1Y-64);//burda çok zekalýydým
            	}
           	}
            };
        
            star1.sprite = new Sprite(40, 130,star1.textureRegion );
            star1.body = PhysicsFactory.createCircleBody(this.physicsWorld, star1.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            star2.sprite = new Sprite(240, 175,star2.textureRegion );
            star2.body = PhysicsFactory.createCircleBody(this.physicsWorld, star2.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            star3.sprite = new Sprite(360, 220,star3.textureRegion );
            star3.body = PhysicsFactory.createCircleBody(this.physicsWorld, star3.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            bomb2.sprite = new Sprite(410, 110,bomb2.textureRegion );
            bomb2.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb2.sprite, BodyType.StaticBody, FIXTURE_DEF);
      
           
            bodySepet.setActive(true);  bodyYumurta.setActive(true);  bodyEngel1.setActive(true);
            star1.body.setActive(true);  star2.body.setActive(true); star3.body.setActive(true);  bomb2.body.setActive(true);
           
          
            animSpriteAnime.setVisible(false);
            
            this.sahneOyun.attachChild(bgSprite);
            this.sahneOyun.attachChild(spriteDrop);
            this.sahneOyun.attachChild(animSpriteAnime);
            this.sahneOyun.attachChild(spriteSepet);
            this.sahneOyun.attachChild(spriteDoluSepet);
            this.sahneOyun.attachChild(spriteYumurta);
            this.sahneOyun.attachChild(star1.sprite);
            this.sahneOyun.attachChild(star2.sprite);
            this.sahneOyun.attachChild(star3.sprite);
            this.sahneOyun.attachChild(bomb2.sprite);
            this.sahneOyun.attachChild(spriteEngel1); 
            this.sahneOyun.attachChild(geciciEngel1Sprite);
            
            this.sahneOyun.registerTouchArea(geciciEngel1Sprite);
            this.sahneOyun.registerTouchArea(spriteDrop);
            
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSepet, bodySepet, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteDoluSepet, bodySepet, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteYumurta, bodyYumurta, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star1.sprite, star1.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star2.sprite, star2.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star3.sprite, star3.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb2.sprite, bomb2.body,true,true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel1, bodyEngel1, true, true)); 
 		}
 	private void oyunNesneleriniOlustur1()
		{
 		sahneOyun.clearChildScene();
		bgReg = TextureRegionFactory.createFromAsset(bgTexture, this, "gfx/bg.png", 0, 0); 
        animSpriteAnime = new AnimatedSprite(470, 225, this.tiledTextRegAnime);
            
            spriteDrop = new Sprite(650, 25, textRegDrop){
            	@Override
            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
            	{ 
            		bodyYumurta.setType(BodyType.DynamicBody);
            		if (pSceneTouchEvent.isActionDown())
            		{
            			basladi = true;
            			spriteDrop.setScaleX((float) 1.3);
            			spriteDrop.setScaleY((float) 1.3);
            		}
            		if (pSceneTouchEvent.isActionUp())
            		{
            			spriteDrop.setScaleX(1);
            			spriteDrop.setScaleY(1);
            	}
            		return true;
            	}
            };
            bgSprite = new Sprite(0, 0, bgReg);
            spriteSepet = new Sprite(270, 340, textRegSepet);//sepet
            spriteDoluSepet = new Sprite(270, 340, textRegSepetDolu);//sepet
            spriteDoluSepet.setVisible(false);
            
            bodySepet = PhysicsFactory.createCircleBody(this.physicsWorld, spriteSepet, BodyType.StaticBody, FIXTURE_DEF);
                    
            spriteYumurta = new Sprite (50, 30, textRegYumurta);//yumurta
            bodyYumurta =  PhysicsFactory.createBoxBody(this.physicsWorld, spriteYumurta, BodyType.StaticBody, FIXTURE_DEF);
           
         
           geciciEngel2Sprite = new Sprite(dokunulanNoktaX, dokunulanNoktaY, geciciRegEngel2){
        	@Override
        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
        	{ 

            	if(pSceneTouchEvent.isActionDown())
				{
            		dokunulanNoktaX = pSceneTouchEvent.getX();
            		dokunulanNoktaY = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionMove())
				{
					dokunulanNoktaX = pSceneTouchEvent.getX();
					dokunulanNoktaY = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionUp())
				{
				}
				return true; 
            }
            protected void onManagedUpdate(float pSecondsElapsed) 
			{
            	if(!basladi){//basladýysa engel hareket etmemeli
            	bodyEngel2.setTransform(dokunulanNoktaX/32,dokunulanNoktaY/32,0);
            	geciciEngel2Sprite.setPosition(dokunulanNoktaX-64,dokunulanNoktaY-64);//burda çok zekalýydým
            	}
           	}
        };
            spriteEngel1 = new Sprite(dokunulanNoktaX,dokunulanNoktaY,engelRegResim1);
            
            bodyEngel1 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel1, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);

            spriteEngel2 = new Sprite(dokunulanNokta1X,dokunulanNokta1Y,engelRegResim2);
        
        geciciEngel1Sprite = new Sprite(dokunulanNokta1X, dokunulanNokta1Y, geciciRegEngel1){
            	@Override
            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
            	{ 

            	if(pSceneTouchEvent.isActionDown())
				{
            		dokunulanNokta1X = pSceneTouchEvent.getX();
            		dokunulanNokta1Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionMove())
				{
					dokunulanNokta1X = pSceneTouchEvent.getX();
					dokunulanNokta1Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionUp())
				{
				}
				return true; 
            }
            protected void onManagedUpdate(float pSecondsElapsed) 
			{
            	if(!basladi){//basladýysa engel hareket etmemeli
            	bodyEngel1.setTransform(dokunulanNokta1X/32,dokunulanNokta1Y/32,260);
            	geciciEngel1Sprite.setPosition(dokunulanNokta1X-64,dokunulanNokta1Y-64);//burda çok zekalýydým
            	}
           	}
            };
        bodyEngel2 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel2, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
            //bodyEngel2.setAngularVelocity(3);
        
            star1.sprite = new Sprite(180, 220,star1.textureRegion );
            star1.body = PhysicsFactory.createCircleBody(this.physicsWorld, star1.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            star2.sprite = new Sprite(300, 195,star2.textureRegion );
            star2.body = PhysicsFactory.createCircleBody(this.physicsWorld, star2.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            star3.sprite = new Sprite(420, 220,star3.textureRegion );
            star3.body = PhysicsFactory.createCircleBody(this.physicsWorld, star3.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            bomb2.sprite = new Sprite(350, 110,bomb2.textureRegion );
            bomb2.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb2.sprite, BodyType.StaticBody, FIXTURE_DEF);
      
            bomb3.sprite = new Sprite(200, 300,bomb3.textureRegion );
            bomb3.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb3.sprite, BodyType.StaticBody, FIXTURE_DEF);
      
            bodySepet.setActive(true);  bodyYumurta.setActive(true);  bodyEngel1.setActive(true); bodyEngel2.setActive(true);
            star1.body.setActive(true);  star2.body.setActive(true); star3.body.setActive(true);  bomb2.body.setActive(true);
            bomb3.body.setActive(true);
          
            animSpriteAnime.setVisible(false);
            
            this.sahneOyun.attachChild(bgSprite);
            this.sahneOyun.attachChild(spriteDrop);
            this.sahneOyun.attachChild(animSpriteAnime);
            this.sahneOyun.attachChild(spriteSepet);
            this.sahneOyun.attachChild(spriteDoluSepet);
            this.sahneOyun.attachChild(spriteYumurta);
            this.sahneOyun.attachChild(star1.sprite);
            this.sahneOyun.attachChild(star2.sprite);
            this.sahneOyun.attachChild(star3.sprite);
            this.sahneOyun.attachChild(bomb2.sprite);
            this.sahneOyun.attachChild(bomb3.sprite);
            this.sahneOyun.attachChild(spriteEngel1); 
        this.sahneOyun.attachChild(geciciEngel1Sprite); 
        this.sahneOyun.attachChild(spriteEngel2);
        this.sahneOyun.attachChild(geciciEngel2Sprite);
            
            this.sahneOyun.registerTouchArea(geciciEngel1Sprite);
            this.sahneOyun.registerTouchArea(geciciEngel2Sprite);
            this.sahneOyun.registerTouchArea(spriteDrop);
            
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSepet, bodySepet, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteDoluSepet, bodySepet, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteYumurta, bodyYumurta, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star1.sprite, star1.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star2.sprite, star2.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star3.sprite, star3.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb2.sprite, bomb2.body,true,true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb3.sprite, bomb3.body,true,true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel1, bodyEngel1, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel2, bodyEngel2, true, true)); 
		}
 	private void oyunNesneleriniOlustur2(){
		
				sahneOyun.clearChildScene();
				bgReg = TextureRegionFactory.createFromAsset(bgTexture, this, "gfx/bg5.png", 0, 0); 
		            animSpriteAnime = new AnimatedSprite(470, 225, this.tiledTextRegAnime);
		            
		            spriteDrop = new Sprite(650, 25, textRegDrop){
		            	@Override
		            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
		            	{ 
		            		bodyYumurta.setType(BodyType.DynamicBody);
		            		if (pSceneTouchEvent.isActionDown())
		            		{
		            			basladi = true;
		            			spriteDrop.setScaleX((float) 1.3);
		            			spriteDrop.setScaleY((float) 1.3);
		            		}
		            		if (pSceneTouchEvent.isActionUp())
		            		{
		            			spriteDrop.setScaleX(1);
		            			spriteDrop.setScaleY(1);
		            	}
		            		return true;
		            	}
		            };
		            bgSprite = new Sprite(0,0,bgReg);
		            spriteSepet = new Sprite(170, 340, textRegSepet);//sepet
		            spriteDoluSepet = new Sprite(170, 340, textRegSepetDolu);//sepet
		            spriteDoluSepet.setVisible(false);
		            
		            bodySepet = PhysicsFactory.createCircleBody(this.physicsWorld, spriteSepet, BodyType.StaticBody, FIXTURE_DEF);
		                    
		            spriteYumurta = new Sprite (150, 10, textRegYumurta);//yumurta
		            bodyYumurta =  PhysicsFactory.createBoxBody(this.physicsWorld, spriteYumurta, BodyType.StaticBody, FIXTURE_DEF);
		           
		         
		           geciciEngel2Sprite = new Sprite(dokunulanNoktaX, dokunulanNoktaY, geciciRegEngel2){
	            	@Override
	            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	            	{ 

	                	if(pSceneTouchEvent.isActionDown())
	    				{
	                		dokunulanNoktaX = pSceneTouchEvent.getX();
	                		dokunulanNoktaY = pSceneTouchEvent.getY();
	    				}
	    				if(pSceneTouchEvent.isActionMove())
	    				{
	    					dokunulanNoktaX = pSceneTouchEvent.getX();
	    					dokunulanNoktaY = pSceneTouchEvent.getY();
	    				}
	    				if(pSceneTouchEvent.isActionUp())
	    				{
	    				
	    				}
	    				return true; 
	                }
	                protected void onManagedUpdate(float pSecondsElapsed) 
	    			{
	                	if(!basladi){//basladýysa engel hareket etmemeli
	                	bodyEngel2.setTransform(dokunulanNoktaX/32,dokunulanNoktaY/32,0);
	                	geciciEngel2Sprite.setPosition(dokunulanNoktaX-64,dokunulanNoktaY-64);//burda çok zekalýydým
	                	}
	               	}
	            };
	               spriteEngel2 = new Sprite(dokunulanNokta1X,dokunulanNokta1Y,engelRegResim2);
		           bodyEngel2 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel2, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
		           
	       
	               geciciEngel1Sprite = new Sprite(dokunulanNokta1X, dokunulanNokta1Y, geciciRegEngel1){
		            	@Override
		            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
		            	{ 

	                	if(pSceneTouchEvent.isActionDown())
	    				{
	                		dokunulanNokta1X = pSceneTouchEvent.getX();
	                		dokunulanNokta1Y = pSceneTouchEvent.getY();
	    				}
	    				if(pSceneTouchEvent.isActionMove())
	    				{
	    					dokunulanNokta1X = pSceneTouchEvent.getX();
	    					dokunulanNokta1Y = pSceneTouchEvent.getY();
	    				}
	    				if(pSceneTouchEvent.isActionUp())
	    				{
	    				
	    				}
	    				return true; 
	                }
	                protected void onManagedUpdate(float pSecondsElapsed) 
	    			{
	                	if(!basladi){//basladýysa engel hareket etmemeli
	                	bodyEngel1.setTransform(dokunulanNokta1X/32,dokunulanNokta1Y/32,260);
	                	geciciEngel1Sprite.setPosition(dokunulanNokta1X-64,dokunulanNokta1Y-64);//burda çok zekalýydým
	                	}
	               	}
		            };
		           spriteEngel1 = new Sprite(dokunulanNoktaX,dokunulanNoktaY,engelRegResim1);
			       bodyEngel1 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel1, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
			       
			       geciciEngel3Sprite = new Sprite(dokunulanNokta2X, dokunulanNokta2Y, geciciRegEngel3){
		            	@Override
		            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
		            	{ 

	               	if(pSceneTouchEvent.isActionDown())
	   				{
	               		dokunulanNokta2X = pSceneTouchEvent.getX();
	               		dokunulanNokta2Y = pSceneTouchEvent.getY();
	   				}
	   				if(pSceneTouchEvent.isActionMove())
	   				{
	   					dokunulanNokta2X = pSceneTouchEvent.getX();
	   					dokunulanNokta2Y = pSceneTouchEvent.getY();
	   				}
	   				if(pSceneTouchEvent.isActionUp())
	   				{
	   				
	   				}
	   				return true; 
	               }
	               protected void onManagedUpdate(float pSecondsElapsed) 
	   			{
	              	if(!basladi){//basladýysa engel hareket etmemeli
	               	bodyEngel3.setTransform(dokunulanNokta2X/32,dokunulanNokta2Y/32,(float) 1.15);
	               	geciciEngel3Sprite.setPosition(dokunulanNokta2X-64,dokunulanNokta2Y-64);//burda çok zekalýydým
	               	}
	              	}
		            };
		           spriteEngel3 = new Sprite(dokunulanNokta2X,dokunulanNokta2Y,engelRegResim3);
			       bodyEngel3 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel3, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
		            
	            
		            star1.sprite = new Sprite(270, 150,star1.textureRegion );
		            star1.body = PhysicsFactory.createCircleBody(this.physicsWorld, star1.sprite, BodyType.StaticBody, FIXTURE_DEF);
		            
		            star2.sprite = new Sprite(350, 100,star2.textureRegion );
		            star2.body = PhysicsFactory.createCircleBody(this.physicsWorld, star2.sprite, BodyType.StaticBody, FIXTURE_DEF);
		            
		            star3.sprite = new Sprite(470, 120,star3.textureRegion );
	                star3.body = PhysicsFactory.createCircleBody(this.physicsWorld, star3.sprite, BodyType.StaticBody, FIXTURE_DEF);
		            
	                bomb2.sprite = new Sprite(385, 200,bomb2.textureRegion );
	                bomb2.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb2.sprite, BodyType.StaticBody, FIXTURE_DEF);
		  
		            bomb3.sprite = new Sprite(300, 240,bomb3.textureRegion );
		            bomb3.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb3.sprite, BodyType.StaticBody, FIXTURE_DEF);
		            
		            bomb1.sprite = new Sprite(215,280,bomb1.textureRegion);
		            bomb1.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb1.sprite, BodyType.StaticBody, FIXTURE_DEF);
			            
		            
		            bodySepet.setActive(true);  bodyYumurta.setActive(true);  bodyEngel1.setActive(true); bodyEngel2.setActive(true);
		            bodyEngel3.setActive(true); star1.body.setActive(true);  star2.body.setActive(true); star3.body.setActive(true); 
		            bomb2.body.setActive(true); bomb3.body.setActive(true);  bomb1.body.setActive(true);
		          
		            animSpriteAnime.setVisible(false);
		            
		            this.sahneOyun.attachChild(bgSprite);
		            this.sahneOyun.attachChild(spriteDrop);
		            this.sahneOyun.attachChild(animSpriteAnime);
		            this.sahneOyun.attachChild(spriteSepet);
		            this.sahneOyun.attachChild(spriteDoluSepet);
		            this.sahneOyun.attachChild(spriteYumurta);
		            this.sahneOyun.attachChild(star1.sprite);
		            this.sahneOyun.attachChild(star2.sprite);
		            this.sahneOyun.attachChild(star3.sprite);
		            this.sahneOyun.attachChild(bomb2.sprite);
		            this.sahneOyun.attachChild(bomb3.sprite);
		            this.sahneOyun.attachChild(bomb1.sprite);
		            this.sahneOyun.attachChild(spriteEngel1); 
		            this.sahneOyun.attachChild(geciciEngel1Sprite); 
		            this.sahneOyun.attachChild(spriteEngel2);
		            this.sahneOyun.attachChild(geciciEngel2Sprite); 
		            this.sahneOyun.attachChild(spriteEngel3);
		            this.sahneOyun.attachChild(geciciEngel3Sprite); 
		            
		            this.sahneOyun.registerTouchArea(geciciEngel1Sprite);
		            this.sahneOyun.registerTouchArea(geciciEngel2Sprite);
		            this.sahneOyun.registerTouchArea(geciciEngel3Sprite);
		            this.sahneOyun.registerTouchArea(spriteDrop);
		            
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSepet, bodySepet, true, true));
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteDoluSepet, bodySepet, true, true));
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteYumurta, bodyYumurta, true, true));
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star1.sprite, star1.body, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star2.sprite, star2.body, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star3.sprite, star3.body, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb2.sprite, bomb2.body, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb3.sprite, bomb3.body, true, true));
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb1.sprite, bomb1.body, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel1, bodyEngel1, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel2, bodyEngel2, true, true));  
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel3, bodyEngel3, true, true)); 
		            
		}
	private void oyunNesneleriniOlustur3(){
	    	sahneOyun.clearChildScene();
			bgReg = TextureRegionFactory.createFromAsset(bgTexture, this, "gfx/bg1.png", 0, 0); 
	            animSpriteAnime = new AnimatedSprite(470, 225, this.tiledTextRegAnime);
	            
	            spriteDrop = new Sprite(650, 25, textRegDrop){
	            	@Override
	            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	            	{ 
	            		bodyYumurta.setType(BodyType.DynamicBody);
	            		if (pSceneTouchEvent.isActionDown())
	            		{
	            			basladi = true;
	            			spriteDrop.setScaleX((float) 1.3);
	            			spriteDrop.setScaleY((float) 1.3);
	            		}
	            		if (pSceneTouchEvent.isActionUp())
	            		{
	            			spriteDrop.setScaleX(1);
	            			spriteDrop.setScaleY(1);
	            	}
	            		return true;
	            	}
	            };
	            bgSprite = new Sprite(0,0,bgReg);
	            spriteSepet = new Sprite(420, 340, textRegSepet);//sepet
	            spriteDoluSepet = new Sprite(420, 340, textRegSepetDolu);//sepet
	            spriteDoluSepet.setVisible(false);
	            
	            bodySepet = PhysicsFactory.createCircleBody(this.physicsWorld, spriteSepet, BodyType.StaticBody, FIXTURE_DEF);
	                    
	            spriteYumurta = new Sprite (50, 30, textRegYumurta);//yumurta
	            bodyYumurta =  PhysicsFactory.createBoxBody(this.physicsWorld, spriteYumurta, BodyType.StaticBody, FIXTURE_DEF);
	           
	         
	           geciciEngel2Sprite = new Sprite(dokunulanNoktaX, dokunulanNoktaY, geciciRegEngel2){
	        	@Override
	        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	        	{ 

	            	if(pSceneTouchEvent.isActionDown())
					{
	            		dokunulanNoktaX = pSceneTouchEvent.getX();
	            		dokunulanNoktaY = pSceneTouchEvent.getY();
					}
					if(pSceneTouchEvent.isActionMove())
					{
						dokunulanNoktaX = pSceneTouchEvent.getX();
						dokunulanNoktaY = pSceneTouchEvent.getY();
					}
					if(pSceneTouchEvent.isActionUp())
					{
					
					}
					return true; 
	            }
	            protected void onManagedUpdate(float pSecondsElapsed) 
				{
	            	if(!basladi){//basladýysa engel hareket etmemeli
	            	bodyEngel2.setTransform(dokunulanNoktaX/32,dokunulanNoktaY/32,0);
	            	geciciEngel2Sprite.setPosition(dokunulanNoktaX-64,dokunulanNoktaY-64);//burda çok zekalýydým
	            	}
	           	}
	        };
	           spriteEngel2 = new Sprite(dokunulanNokta1X,dokunulanNokta1Y,engelRegResim2);
	           bodyEngel2 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel2, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
	           
	   
	           geciciEngel1Sprite = new Sprite(dokunulanNokta1X, dokunulanNokta1Y, geciciRegEngel1){
	            	@Override
	            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	            	{ 

	            	if(pSceneTouchEvent.isActionDown())
					{
	            		dokunulanNokta1X = pSceneTouchEvent.getX();
	            		dokunulanNokta1Y = pSceneTouchEvent.getY();
					}
					if(pSceneTouchEvent.isActionMove())
					{
						dokunulanNokta1X = pSceneTouchEvent.getX();
						dokunulanNokta1Y = pSceneTouchEvent.getY();
					}
					if(pSceneTouchEvent.isActionUp())
					{
					
					}
					return true; 
	            }
	            protected void onManagedUpdate(float pSecondsElapsed) 
				{
	            	if(!basladi){//basladýysa engel hareket etmemeli
	            	bodyEngel1.setTransform(dokunulanNokta1X/32,dokunulanNokta1Y/32,260);//260
	            	geciciEngel1Sprite.setPosition(dokunulanNokta1X-64,dokunulanNokta1Y-64);//burda çok zekalýydým
	            	}
	           	}
	            };
	           spriteEngel1 = new Sprite(dokunulanNoktaX,dokunulanNoktaY,engelRegResim1);
		       bodyEngel1 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel1, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
		       
		       geciciEngel3Sprite = new Sprite(dokunulanNokta2X, dokunulanNokta2Y, geciciRegEngel3){
	            	@Override
	            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	            	{ 

	           	if(pSceneTouchEvent.isActionDown())
					{
	           		dokunulanNokta2X = pSceneTouchEvent.getX();
	           		dokunulanNokta2Y = pSceneTouchEvent.getY();
					}
					if(pSceneTouchEvent.isActionMove())
					{
						dokunulanNokta2X = pSceneTouchEvent.getX();
						dokunulanNokta2Y = pSceneTouchEvent.getY();
					}
					if(pSceneTouchEvent.isActionUp())
					{
					
					}
					return true; 
	           }
	           protected void onManagedUpdate(float pSecondsElapsed) 
				{
	          	if(!basladi){//basladýysa engel hareket etmemeli
	           	bodyEngel3.setTransform(dokunulanNokta2X/32,dokunulanNokta2Y/32,60);//120
	           	geciciEngel3Sprite.setPosition(dokunulanNokta2X-64,dokunulanNokta2Y-64);//burda çok zekalýydým
	           	}
	          	}
	            };
	           spriteEngel3 = new Sprite(dokunulanNokta2X,dokunulanNokta2Y,engelRegResim3);
		       bodyEngel3 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel3, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
	            
	        
	            star1.sprite = new Sprite(190, 200,star1.textureRegion );
	            star1.body = PhysicsFactory.createCircleBody(this.physicsWorld, star1.sprite, BodyType.StaticBody, FIXTURE_DEF);
	            
	            star2.sprite = new Sprite(300, 150,star2.textureRegion );
	            star2.body = PhysicsFactory.createCircleBody(this.physicsWorld, star2.sprite, BodyType.StaticBody, FIXTURE_DEF);
	            
	            star3.sprite = new Sprite(410, 160,star3.textureRegion );
	            star3.body = PhysicsFactory.createCircleBody(this.physicsWorld, star3.sprite, BodyType.StaticBody, FIXTURE_DEF);
	            
	            bomb2.sprite = new Sprite(270,260,bomb2.textureRegion);
	            bomb2.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb2.sprite, BodyType.StaticBody, FIXTURE_DEF);
	            
	            bomb3.sprite = new Sprite(520, 250,bomb3.textureRegion );
	            bomb3.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb3.sprite, BodyType.StaticBody, FIXTURE_DEF);
	      
	            bodySepet.setActive(true);  bodyYumurta.setActive(true);  bodyEngel1.setActive(true); bodyEngel2.setActive(true); 
	            bodyEngel3.setActive(true); star1.body.setActive(true);  star2.body.setActive(true); star3.body.setActive(true); 
	            bomb2.body.setActive(true); bomb3.body.setActive(true); 
	          
	            animSpriteAnime.setVisible(false);
	            
	            this.sahneOyun.attachChild(bgSprite);
	            this.sahneOyun.attachChild(spriteDrop);
	            this.sahneOyun.attachChild(animSpriteAnime);
	            this.sahneOyun.attachChild(spriteSepet);
	            this.sahneOyun.attachChild(spriteDoluSepet);
	            this.sahneOyun.attachChild(spriteYumurta);
	            this.sahneOyun.attachChild(star1.sprite);
	            this.sahneOyun.attachChild(star2.sprite);
	            this.sahneOyun.attachChild(star3.sprite);
	            this.sahneOyun.attachChild(bomb2.sprite);
	            this.sahneOyun.attachChild(bomb3.sprite);
	            this.sahneOyun.attachChild(spriteEngel1); 
	            this.sahneOyun.attachChild(geciciEngel1Sprite); 
	            this.sahneOyun.attachChild(spriteEngel2);
	            this.sahneOyun.attachChild(geciciEngel2Sprite); 
	            this.sahneOyun.attachChild(spriteEngel3);
	            this.sahneOyun.attachChild(geciciEngel3Sprite); 
	            
	            this.sahneOyun.registerTouchArea(geciciEngel1Sprite);
	            this.sahneOyun.registerTouchArea(geciciEngel2Sprite);
	            this.sahneOyun.registerTouchArea(geciciEngel3Sprite);
	            this.sahneOyun.registerTouchArea(spriteDrop);
	            
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSepet, bodySepet, true, true));
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteDoluSepet, bodySepet, true, true));
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteYumurta, bodyYumurta, true, true));
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star1.sprite, star1.body, true, true)); 
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star2.sprite, star2.body, true, true)); 
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star3.sprite, star3.body, true, true)); 
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb2.sprite, bomb2.body, true, true)); 
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb3.sprite, bomb3.body, true, true)); 
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel1, bodyEngel1, true, true)); 
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel2, bodyEngel2, true, true));  
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel3, bodyEngel3, true, true)); 
	    	
	    }	
	private void oyunNesneleriniOlustur4(){
					sahneOyun.clearChildScene();
					bgReg = TextureRegionFactory.createFromAsset(bgTexture, this, "gfx/bg7.png", 0, 0);
			            animSpriteAnime = new AnimatedSprite(470, 225, this.tiledTextRegAnime);
			            
			            spriteDrop = new Sprite(650, 25, textRegDrop){
			            	@Override
			            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			            	{ 
			            		bodyYumurta.setType(BodyType.DynamicBody);
			            		if (pSceneTouchEvent.isActionDown())
			            		{
			            			basladi = true;
			            			spriteDrop.setScaleX((float) 1.3);
			            			spriteDrop.setScaleY((float) 1.3);
			            		}
			            		if (pSceneTouchEvent.isActionUp())
			            		{
			            			spriteDrop.setScaleX(1);
			            			spriteDrop.setScaleY(1);
			            	}
			            		return true;
			            	}
			            };
			            bgSprite = new Sprite(0, 0, bgReg);
			            spriteSepet = new Sprite(470, 355, textRegSepet);//sepet
			            spriteDoluSepet = new Sprite(470, 355, textRegSepetDolu);//sepet
			            spriteDoluSepet.setVisible(false);
			            
			            bodySepet = PhysicsFactory.createCircleBody(this.physicsWorld, spriteSepet, BodyType.StaticBody, FIXTURE_DEF);
			                    
			            spriteYumurta = new Sprite (50, 10, textRegYumurta);//yumurta
			            bodyYumurta =  PhysicsFactory.createBoxBody(this.physicsWorld, spriteYumurta, BodyType.StaticBody, FIXTURE_DEF);
			           
			         
			           geciciEngel2Sprite = new Sprite(dokunulanNoktaX, dokunulanNoktaY, geciciRegEngel2){
		            	@Override
		            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
		            	{ 

		                	if(pSceneTouchEvent.isActionDown())
		    				{
		                		dokunulanNoktaX = pSceneTouchEvent.getX();
		                		dokunulanNoktaY = pSceneTouchEvent.getY();
		    				}
		    				if(pSceneTouchEvent.isActionMove())
		    				{
		    					dokunulanNoktaX = pSceneTouchEvent.getX();
		    					dokunulanNoktaY = pSceneTouchEvent.getY();
		    				}
		    				if(pSceneTouchEvent.isActionUp())
		    				{
		    				
		    				}
		    				return true; 
		                }
		                protected void onManagedUpdate(float pSecondsElapsed) 
		    			{
		                	if(!basladi){//basladýysa engel hareket etmemeli
		                	bodyEngel2.setTransform(dokunulanNoktaX/32,dokunulanNoktaY/32,0);
		                	geciciEngel2Sprite.setPosition(dokunulanNoktaX-64,dokunulanNoktaY-64);//burda çok zekalýydým
		                	}
		               	}
		            };
		               spriteEngel2 = new Sprite(dokunulanNokta1X,dokunulanNokta1Y,engelRegResim2);
			           bodyEngel2 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel2, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
			           
		       
		               geciciEngel1Sprite = new Sprite(dokunulanNokta1X, dokunulanNokta1Y, geciciRegEngel1){
			            	@Override
			            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			            	{ 

		                	if(pSceneTouchEvent.isActionDown())
		    				{
		                		dokunulanNokta1X = pSceneTouchEvent.getX();
		                		dokunulanNokta1Y = pSceneTouchEvent.getY();
		    				}
		    				if(pSceneTouchEvent.isActionMove())
		    				{
		    					dokunulanNokta1X = pSceneTouchEvent.getX();
		    					dokunulanNokta1Y = pSceneTouchEvent.getY();
		    				}
		    				if(pSceneTouchEvent.isActionUp())
		    				{
		    				
		    				}
		    				return true; 
		                }
		                protected void onManagedUpdate(float pSecondsElapsed) 
		    			{
		                	if(!basladi){//basladýysa engel hareket etmemeli
		                	bodyEngel1.setTransform(dokunulanNokta1X/32,dokunulanNokta1Y/32,260);
		                	geciciEngel1Sprite.setPosition(dokunulanNokta1X-64,dokunulanNokta1Y-64);//burda çok zekalýydým
		                	}
		               	}
			            };
			           spriteEngel1 = new Sprite(dokunulanNoktaX,dokunulanNoktaY,engelRegResim1);
				       bodyEngel1 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel1, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
				       
				       geciciEngel3Sprite = new Sprite(dokunulanNokta2X, dokunulanNokta2Y, geciciRegEngel3){
			            	@Override
			            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			            	{ 

		               	if(pSceneTouchEvent.isActionDown())
		   				{
		               		dokunulanNokta2X = pSceneTouchEvent.getX();
		               		dokunulanNokta2Y = pSceneTouchEvent.getY();
		   				}
		   				if(pSceneTouchEvent.isActionMove())
		   				{
		   					dokunulanNokta2X = pSceneTouchEvent.getX();
		   					dokunulanNokta2Y = pSceneTouchEvent.getY();
		   				}
		   				if(pSceneTouchEvent.isActionUp())
		   				{
		   				
		   				}
		   				return true; 
		               }
		               protected void onManagedUpdate(float pSecondsElapsed) 
		   			{
		              	if(!basladi){//basladýysa engel hareket etmemeli
		               	bodyEngel3.setTransform(dokunulanNokta2X/32,dokunulanNokta2Y/32,260);//(float) 2.5
		               	geciciEngel3Sprite.setPosition(dokunulanNokta2X-64,dokunulanNokta2Y-64);//burda çok zekalýydým
		               	}}
			            };
			           spriteEngel3 = new Sprite(dokunulanNokta2X,dokunulanNokta2Y,engelRegResim3);
				       bodyEngel3 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel3, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
				       geciciEngel4Sprite = new Sprite(dokunulanNokta3X, dokunulanNokta3Y, geciciRegEngel4){
			            	@Override
			            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			            	{ 

		               	if(pSceneTouchEvent.isActionDown())
		   				{
		               		dokunulanNokta3X = pSceneTouchEvent.getX();
		               		dokunulanNokta3Y = pSceneTouchEvent.getY();
		   				}
		   				if(pSceneTouchEvent.isActionMove())
		   				{
		   					dokunulanNokta3X = pSceneTouchEvent.getX();
		   					dokunulanNokta3Y = pSceneTouchEvent.getY();
		   				}
		   				if(pSceneTouchEvent.isActionUp())
		   				{
		   				
		   				}
		   				return true; 
		               }
		               protected void onManagedUpdate(float pSecondsElapsed) 
		   			{
		              	if(!basladi){//basladýysa engel hareket etmemeli
		               	bodyEngel4.setTransform(dokunulanNokta3X/32,dokunulanNokta3Y/32,260);//(float) 2.5
		               	geciciEngel4Sprite.setPosition(dokunulanNokta3X-64,dokunulanNokta3Y-64);//burda çok zekalýydým
		               	}
		              	}
			            };
			           spriteEngel4 = new Sprite(dokunulanNokta3X,dokunulanNokta3Y,engelRegResim4);
				       bodyEngel4 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel4, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
			            
		            
			            star1.sprite = new Sprite(200, 150,star1.textureRegion );
			            star1.body = PhysicsFactory.createCircleBody(this.physicsWorld, star1.sprite, BodyType.StaticBody, FIXTURE_DEF);
			            
			            star2.sprite = new Sprite(350, 260,star2.textureRegion );
			            star2.body = PhysicsFactory.createCircleBody(this.physicsWorld, star2.sprite, BodyType.StaticBody, FIXTURE_DEF);
			            
			            star3.sprite = new Sprite(470, 260,star3.textureRegion );
		                star3.body = PhysicsFactory.createCircleBody(this.physicsWorld, star3.sprite, BodyType.StaticBody, FIXTURE_DEF);
		                
		                bomb1.sprite = new Sprite(320,20,bomb1.textureRegion);
			            bomb1.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb1.sprite, BodyType.StaticBody, FIXTURE_DEF);
			            
		                bomb2.sprite = new Sprite(320, 95,bomb2.textureRegion );
		                bomb2.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb2.sprite, BodyType.StaticBody, FIXTURE_DEF);
			  
			            bomb3.sprite = new Sprite(320, 170,bomb3.textureRegion );
			            bomb3.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb3.sprite, BodyType.StaticBody, FIXTURE_DEF);
			            
			      
			            bodySepet.setActive(true);  bodyYumurta.setActive(true);  bodyEngel1.setActive(true); bodyEngel2.setActive(true);
			            bodyEngel3.setActive(true); star1.body.setActive(true);  star2.body.setActive(true); star3.body.setActive(true); 
			            bomb2.body.setActive(true); bomb3.body.setActive(true);  bomb1.body.setActive(true);
			          
			            animSpriteAnime.setVisible(false);
			            
			            this.sahneOyun.attachChild(bgSprite);
			            this.sahneOyun.attachChild(spriteDrop);
			            this.sahneOyun.attachChild(animSpriteAnime);
			            this.sahneOyun.attachChild(spriteSepet);
			            this.sahneOyun.attachChild(spriteDoluSepet);
			            this.sahneOyun.attachChild(spriteYumurta);
			            this.sahneOyun.attachChild(star1.sprite);
			            this.sahneOyun.attachChild(star2.sprite);
			            this.sahneOyun.attachChild(star3.sprite);
			            this.sahneOyun.attachChild(bomb2.sprite);
			            this.sahneOyun.attachChild(bomb3.sprite);
			            this.sahneOyun.attachChild(bomb1.sprite);
			            this.sahneOyun.attachChild(spriteEngel1); 
			            this.sahneOyun.attachChild(geciciEngel1Sprite); 
			            this.sahneOyun.attachChild(spriteEngel2);
			            this.sahneOyun.attachChild(geciciEngel2Sprite); 
			            this.sahneOyun.attachChild(spriteEngel3);
			            this.sahneOyun.attachChild(geciciEngel3Sprite); 
			            this.sahneOyun.attachChild(spriteEngel4);
			            this.sahneOyun.attachChild(geciciEngel4Sprite); 
			            
			            this.sahneOyun.registerTouchArea(geciciEngel1Sprite);
			            this.sahneOyun.registerTouchArea(geciciEngel2Sprite);
			            this.sahneOyun.registerTouchArea(geciciEngel3Sprite);
			            this.sahneOyun.registerTouchArea(geciciEngel4Sprite);
			            this.sahneOyun.registerTouchArea(spriteDrop);
			            
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSepet, bodySepet, true, true));
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteDoluSepet, bodySepet, true, true));
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteYumurta, bodyYumurta, true, true));
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star1.sprite, star1.body, true, true)); 
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star2.sprite, star2.body, true, true)); 
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star3.sprite, star3.body, true, true)); 
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb2.sprite, bomb2.body, true, true)); 
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb3.sprite, bomb3.body, true, true));
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb1.sprite, bomb1.body, true, true)); 
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel1, bodyEngel1, true, true)); 
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel2, bodyEngel2, true, true));  
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel3, bodyEngel3, true, true));
			            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel4, bodyEngel4, true, true));
				}
	private void oyunNesneleriniOlustur5(){
    	sahneOyun.clearChildScene();
    	if(sonraki)
		bgReg = TextureRegionFactory.createFromAsset(bgTexture, this, "gfx/bg5.png", 0, 0); 
            animSpriteAnime = new AnimatedSprite(470, 225, this.tiledTextRegAnime);
            
            spriteDrop = new Sprite(650, 25, textRegDrop){
            	@Override
            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
            	{ 
            		bodyYumurta.setType(BodyType.DynamicBody);
            		if (pSceneTouchEvent.isActionDown())
            		{
            			basladi = true;
            			spriteDrop.setScaleX((float) 1.3);
            			spriteDrop.setScaleY((float) 1.3);
            		}
            		if (pSceneTouchEvent.isActionUp())
            		{
            			spriteDrop.setScaleX(1);
            			spriteDrop.setScaleY(1);
            	}
            		return true;
            	}
            };
            bgSprite = new Sprite(0,0,bgReg);
            spriteSepet = new Sprite(170, 340, textRegSepet);//sepet
            spriteDoluSepet = new Sprite(170, 340, textRegSepetDolu);//sepet
            spriteDoluSepet.setVisible(false);
            
            bodySepet = PhysicsFactory.createCircleBody(this.physicsWorld, spriteSepet, BodyType.StaticBody, FIXTURE_DEF);
                    
            spriteYumurta = new Sprite (150, 10, textRegYumurta);//yumurta
            bodyYumurta =  PhysicsFactory.createBoxBody(this.physicsWorld, spriteYumurta, BodyType.StaticBody, FIXTURE_DEF);
           
            kanat1.sprite = new Sprite(560, 330,kanat1.textureRegion );
	       	kanat1.body = PhysicsFactory.createBoxBody(this.physicsWorld, kanat1.sprite, BodyType.KinematicBody, FIXTURE_DEF);
	       	kanat1.body.setAngularVelocity(-3);
         
          
           geciciEngel1Sprite = new Sprite(dokunulanNokta1X, dokunulanNokta1Y, geciciRegEngel1){
            	@Override
            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
            	{ 

            	if(pSceneTouchEvent.isActionDown())
				{
            		dokunulanNokta1X = pSceneTouchEvent.getX();
            		dokunulanNokta1Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionMove())
				{
					dokunulanNokta1X = pSceneTouchEvent.getX();
					dokunulanNokta1Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionUp())
				{
				
				}
				return true; 
            }
            protected void onManagedUpdate(float pSecondsElapsed) 
			{
            	if(!basladi){//basladýysa engel hareket etmemeli
            	bodyEngel1.setTransform(dokunulanNokta1X/32,dokunulanNokta1Y/32,260);
            	geciciEngel1Sprite.setPosition(dokunulanNokta1X-64,dokunulanNokta1Y-64);//burda çok zekalýydým
            	}
           	}
            };
           spriteEngel1 = new Sprite(dokunulanNoktaX,dokunulanNoktaY,engelRegResim1);
	       bodyEngel1 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel1, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
	       
	       geciciEngel3Sprite = new Sprite(dokunulanNokta2X, dokunulanNokta2Y, geciciRegEngel3){
            	@Override
            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
            	{ 

           	if(pSceneTouchEvent.isActionDown())
				{
           		dokunulanNokta2X = pSceneTouchEvent.getX();
           		dokunulanNokta2Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionMove())
				{
					dokunulanNokta2X = pSceneTouchEvent.getX();
					dokunulanNokta2Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionUp())
				{
				
				}
				return true; 
           }
           protected void onManagedUpdate(float pSecondsElapsed) 
			{
          	if(!basladi){//basladýysa engel hareket etmemeli
           	bodyEngel3.setTransform(dokunulanNokta2X/32,dokunulanNokta2Y/32,(float) 1.15);
           	geciciEngel3Sprite.setPosition(dokunulanNokta2X-64,dokunulanNokta2Y-64);//burda çok zekalýydým
           	}
          	}
            };
           spriteEngel3 = new Sprite(dokunulanNokta2X,dokunulanNokta2Y,engelRegResim3);
	       bodyEngel3 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel3, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
            
        
            star1.sprite = new Sprite(270, 150,star1.textureRegion );
            star1.body = PhysicsFactory.createCircleBody(this.physicsWorld, star1.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            star2.sprite = new Sprite(350, 100,star2.textureRegion );
            star2.body = PhysicsFactory.createCircleBody(this.physicsWorld, star2.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            star3.sprite = new Sprite(470, 120,star3.textureRegion );
            star3.body = PhysicsFactory.createCircleBody(this.physicsWorld, star3.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            bomb2.sprite = new Sprite(385, 200,bomb2.textureRegion );
            bomb2.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb2.sprite, BodyType.StaticBody, FIXTURE_DEF);
  
            bomb3.sprite = new Sprite(300, 240,bomb3.textureRegion );
            bomb3.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb3.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            bomb1.sprite = new Sprite(215,280,bomb1.textureRegion);
            bomb1.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb1.sprite, BodyType.StaticBody, FIXTURE_DEF);
	            
            
            bodySepet.setActive(true);  bodyYumurta.setActive(true);  bodyEngel1.setActive(true);
            bodyEngel3.setActive(true); star1.body.setActive(true);  star2.body.setActive(true); star3.body.setActive(true); 
            bomb2.body.setActive(true); bomb3.body.setActive(true);  bomb1.body.setActive(true); kanat1.body.setActive(true);
          
            animSpriteAnime.setVisible(false);
            
            this.sahneOyun.attachChild(bgSprite);
            this.sahneOyun.attachChild(spriteDrop);
            this.sahneOyun.attachChild(animSpriteAnime);
            this.sahneOyun.attachChild(spriteSepet);
            this.sahneOyun.attachChild(spriteDoluSepet);
            this.sahneOyun.attachChild(spriteYumurta);
            this.sahneOyun.attachChild(kanat1.sprite);
            this.sahneOyun.attachChild(star1.sprite);
            this.sahneOyun.attachChild(star2.sprite);
            this.sahneOyun.attachChild(star3.sprite);
            this.sahneOyun.attachChild(bomb2.sprite);
            this.sahneOyun.attachChild(bomb3.sprite);
            this.sahneOyun.attachChild(bomb1.sprite);
            this.sahneOyun.attachChild(spriteEngel1); 
            this.sahneOyun.attachChild(geciciEngel1Sprite);
            this.sahneOyun.attachChild(spriteEngel3);
            this.sahneOyun.attachChild(geciciEngel3Sprite); 
            
            this.sahneOyun.registerTouchArea(geciciEngel1Sprite);
            this.sahneOyun.registerTouchArea(geciciEngel3Sprite);
            this.sahneOyun.registerTouchArea(spriteDrop);
            
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSepet, bodySepet, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteDoluSepet, bodySepet, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteYumurta, bodyYumurta, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(kanat1.sprite, kanat1.body, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star1.sprite, star1.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star2.sprite, star2.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star3.sprite, star3.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb2.sprite, bomb2.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb3.sprite, bomb3.body, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb1.sprite, bomb1.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel1, bodyEngel1, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel3, bodyEngel3, true, true)); 
    	
    	
    }	
    private void oyunNesneleriniOlustur6(){
	{
		sahneOyun.clearChildScene();
		bgReg = TextureRegionFactory.createFromAsset(bgTexture, this, "gfx/bg.png", 0, 0); 
	        animSpriteAnime = new AnimatedSprite(470, 225, this.tiledTextRegAnime);
	            
	            spriteDrop = new Sprite(650, 25, textRegDrop){
	            	@Override
	            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	            	{ 
	            		bodyYumurta.setType(BodyType.DynamicBody);
	            		if (pSceneTouchEvent.isActionDown())
	            		{
	            			basladi = true;
	            			spriteDrop.setScaleX((float) 1.3);
	            			spriteDrop.setScaleY((float) 1.3);
	            		}
	            		if (pSceneTouchEvent.isActionUp())
	            		{
	            			spriteDrop.setScaleX(1);
	            			spriteDrop.setScaleY(1);
	            	}
	            		return true;
	            	}
	            };
	            bgSprite = new Sprite(0, 0, bgReg);
	            spriteSepet = new Sprite(350, 340, textRegSepet);//sepet
	            spriteDoluSepet = new Sprite(350, 340, textRegSepetDolu);//sepet
	            spriteDoluSepet.setVisible(false);
	            
	            bodySepet = PhysicsFactory.createCircleBody(this.physicsWorld, spriteSepet, BodyType.StaticBody, FIXTURE_DEF);
	                    
	            spriteYumurta = new Sprite (140, 30, textRegYumurta);//yumurta
	            bodyYumurta =  PhysicsFactory.createBoxBody(this.physicsWorld, spriteYumurta, BodyType.StaticBody, FIXTURE_DEF);
	           
	         
	        kanat1.sprite = new Sprite(85, 320,kanat1.textureRegion );
	       	kanat1.body = PhysicsFactory.createBoxBody(this.physicsWorld, kanat1.sprite, BodyType.KinematicBody, FIXTURE_DEF);
	       	kanat1.body.setAngularVelocity(4);

	       	kanat2.sprite = new Sprite(630, 210,kanat2.textureRegion );
	       	kanat2.body = PhysicsFactory.createBoxBody(this.physicsWorld, kanat2.sprite, BodyType.KinematicBody, FIXTURE_DEF);
	       	kanat2.body.setAngularVelocity(-4);
	       	
	       
	            
	          
	            spriteEngel2 = new Sprite(dokunulanNokta1X,dokunulanNokta1Y,engelRegResim2);
            
            geciciEngel2Sprite = new Sprite(dokunulanNokta2X, dokunulanNokta2Y, geciciRegEngel2){
	            	@Override
	            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	            	{ 

                	if(pSceneTouchEvent.isActionDown())
    				{
                		dokunulanNokta2X = pSceneTouchEvent.getX();
                		dokunulanNokta2Y = pSceneTouchEvent.getY();
    				}
    				if(pSceneTouchEvent.isActionMove())
    				{
    					dokunulanNokta2X = pSceneTouchEvent.getX();
    					dokunulanNokta2Y = pSceneTouchEvent.getY();
    				}
    				if(pSceneTouchEvent.isActionUp())
    				{
    				}
    				return true; 
                }
                protected void onManagedUpdate(float pSecondsElapsed) 
    			{
                	if(!basladi){//basladýysa engel hareket etmemeli
                	bodyEngel2.setTransform(dokunulanNokta2X/32,dokunulanNokta2Y/32,260);
                	geciciEngel2Sprite.setPosition(dokunulanNokta2X-64,dokunulanNokta2Y-64);//burda çok zekalýydým
                	}
               	}
	            };
	            bodyEngel2 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel2, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
	            
            
	            star1.sprite = new Sprite(200, 220,star1.textureRegion );
	            star1.body = PhysicsFactory.createCircleBody(this.physicsWorld, star1.sprite, BodyType.StaticBody, FIXTURE_DEF);
	            
	            star2.sprite = new Sprite(320, 195,star2.textureRegion );
	            star2.body = PhysicsFactory.createCircleBody(this.physicsWorld, star2.sprite, BodyType.StaticBody, FIXTURE_DEF);
	            
	            star3.sprite = new Sprite(440, 220,star3.textureRegion );
	            star3.body = PhysicsFactory.createCircleBody(this.physicsWorld, star3.sprite, BodyType.StaticBody, FIXTURE_DEF);
	            
	            bomb2.sprite = new Sprite(580, 400,bomb2.textureRegion );
	            bomb2.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb2.sprite, BodyType.StaticBody, FIXTURE_DEF);
	      
	            bomb3.sprite = new Sprite(200, 400,bomb3.textureRegion );
	            bomb3.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb3.sprite, BodyType.StaticBody, FIXTURE_DEF);
	      
	            bodySepet.setActive(true);  bodyYumurta.setActive(true);  bodyEngel2.setActive(true);
	            star1.body.setActive(true);  star2.body.setActive(true); star3.body.setActive(true);  bomb2.body.setActive(true);
	            bomb3.body.setActive(true);  kanat1.body.setActive(true);   kanat2.body.setActive(true); 
	          
	            animSpriteAnime.setVisible(false);
	            
	            this.sahneOyun.attachChild(bgSprite);
	            this.sahneOyun.attachChild(spriteDrop);
	            this.sahneOyun.attachChild(animSpriteAnime);
	            this.sahneOyun.attachChild(spriteSepet);
	            this.sahneOyun.attachChild(spriteDoluSepet);
	            this.sahneOyun.attachChild(spriteYumurta);
	            this.sahneOyun.attachChild(kanat1.sprite);
	            this.sahneOyun.attachChild(kanat2.sprite);
	            this.sahneOyun.attachChild(star1.sprite);
	            this.sahneOyun.attachChild(star2.sprite);
	            this.sahneOyun.attachChild(star3.sprite);
	            this.sahneOyun.attachChild(bomb2.sprite);
	            this.sahneOyun.attachChild(bomb3.sprite);
	            this.sahneOyun.attachChild(spriteEngel2);
	            this.sahneOyun.attachChild(geciciEngel2Sprite);
	            
            
	            this.sahneOyun.registerTouchArea(geciciEngel2Sprite);
	            this.sahneOyun.registerTouchArea(spriteDrop);
	            
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSepet, bodySepet, true, true));
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteDoluSepet, bodySepet, true, true));
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteYumurta, bodyYumurta, true, true));
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(kanat1.sprite, kanat1.body, true, true));
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(kanat2.sprite, kanat2.body, true, true));
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star1.sprite, star1.body, true, true)); 
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star2.sprite, star2.body, true, true)); 
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star3.sprite, star3.body, true, true)); 
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb2.sprite, bomb2.body,true,true));
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb3.sprite, bomb3.body,true,true)); 
	            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel2, bodyEngel2, true, true));  
					
	}
	}
    private void oyunNesneleriniOlustur7()
 	{
    	sahneOyun.clearChildScene();
		bgReg = TextureRegionFactory.createFromAsset(bgTexture, this, "gfx/bg6.png", 0, 0);
            animSpriteAnime = new AnimatedSprite(470, 225, this.tiledTextRegAnime);
            
            spriteDrop = new Sprite(650, 25, textRegDrop){
            	@Override
            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
            	{ 
            		bodyYumurta.setType(BodyType.DynamicBody);
            		if (pSceneTouchEvent.isActionDown())
            		{
            			basladi = true;
            			spriteDrop.setScaleX((float) 1.3);
            			spriteDrop.setScaleY((float) 1.3);
            		}
            		if (pSceneTouchEvent.isActionUp())
            		{
            			spriteDrop.setScaleX(1);
            			spriteDrop.setScaleY(1);
            	}
            		return true;
            	}
            };
            bgSprite = new Sprite(0, 0, bgReg);
            spriteSepet = new Sprite(470, 355, textRegSepet);//sepet
            spriteDoluSepet = new Sprite(470, 355, textRegSepetDolu);//sepet
            spriteDoluSepet.setVisible(false);
            
            bodySepet = PhysicsFactory.createCircleBody(this.physicsWorld, spriteSepet, BodyType.StaticBody, FIXTURE_DEF);
                    
            spriteYumurta = new Sprite (350, 10, textRegYumurta);//yumurta
            bodyYumurta =  PhysicsFactory.createBoxBody(this.physicsWorld, spriteYumurta, BodyType.StaticBody, FIXTURE_DEF);
           
         
            kanat1.sprite = new Sprite(320, 460,kanat1.textureRegion );
	       	kanat1.body = PhysicsFactory.createBoxBody(this.physicsWorld, kanat1.sprite, BodyType.KinematicBody, FIXTURE_DEF);
	       	kanat1.body.setAngularVelocity(3);
	       	
           geciciEngel2Sprite = new Sprite(dokunulanNoktaX, dokunulanNoktaY, geciciRegEngel2){
        	@Override
        	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
        	{ 

            	if(pSceneTouchEvent.isActionDown())
				{
            		dokunulanNoktaX = pSceneTouchEvent.getX();
            		dokunulanNoktaY = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionMove())
				{
					dokunulanNoktaX = pSceneTouchEvent.getX();
					dokunulanNoktaY = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionUp())
				{
				
				}
				return true; 
            }
            protected void onManagedUpdate(float pSecondsElapsed) 
			{
            	if(!basladi){//basladýysa engel hareket etmemeli
            	bodyEngel2.setTransform(dokunulanNoktaX/32,dokunulanNoktaY/32,0);
            	geciciEngel2Sprite.setPosition(dokunulanNoktaX-64,dokunulanNoktaY-64);//burda çok zekalýydým
            	}
           	}
        };
           spriteEngel2 = new Sprite(dokunulanNokta1X,dokunulanNokta1Y,engelRegResim2);
           bodyEngel2 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel2, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
           
   
           geciciEngel1Sprite = new Sprite(dokunulanNokta1X, dokunulanNokta1Y, geciciRegEngel1){
            	@Override
            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
            	{ 

            	if(pSceneTouchEvent.isActionDown())
				{
            		dokunulanNokta1X = pSceneTouchEvent.getX();
            		dokunulanNokta1Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionMove())
				{
					dokunulanNokta1X = pSceneTouchEvent.getX();
					dokunulanNokta1Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionUp())
				{
				
				}
				return true; 
            }
            protected void onManagedUpdate(float pSecondsElapsed) 
			{
            	if(!basladi){//basladýysa engel hareket etmemeli
            	bodyEngel1.setTransform(dokunulanNokta1X/32,dokunulanNokta1Y/32,(float) 3.80);
            	geciciEngel1Sprite.setPosition(dokunulanNokta1X-64,dokunulanNokta1Y-64);//burda çok zekalýydým
            	}
           	}
            };
           spriteEngel1 = new Sprite(dokunulanNoktaX,dokunulanNoktaY,engelRegResim1);
	       bodyEngel1 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel1, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
	       
	       geciciEngel3Sprite = new Sprite(dokunulanNokta2X, dokunulanNokta2Y, geciciRegEngel3){
            	@Override
            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
            	{ 

           	if(pSceneTouchEvent.isActionDown())
				{
           		dokunulanNokta2X = pSceneTouchEvent.getX();
           		dokunulanNokta2Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionMove())
				{
					dokunulanNokta2X = pSceneTouchEvent.getX();
					dokunulanNokta2Y = pSceneTouchEvent.getY();
				}
				if(pSceneTouchEvent.isActionUp())
				{
				
				}
				return true; 
           }
           protected void onManagedUpdate(float pSecondsElapsed) 
			{
          	if(!basladi){//basladýysa engel hareket etmemeli
           	bodyEngel3.setTransform(dokunulanNokta2X/32,dokunulanNokta2Y/32,260);//(float) 2.5
           	geciciEngel3Sprite.setPosition(dokunulanNokta2X-64,dokunulanNokta2Y-64);//burda çok zekalýydým
           	}
          	}
            };
           spriteEngel3 = new Sprite(dokunulanNokta2X,dokunulanNokta2Y,engelRegResim3);
	       bodyEngel3 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel3, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
	       
	      
            
        
            star1.sprite = new Sprite(120, 150,star1.textureRegion );
            star1.body = PhysicsFactory.createCircleBody(this.physicsWorld, star1.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            star2.sprite = new Sprite(350, 150 ,star2.textureRegion );
            star2.body = PhysicsFactory.createCircleBody(this.physicsWorld, star2.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            star3.sprite = new Sprite(350, 350,star3.textureRegion );
            star3.body = PhysicsFactory.createCircleBody(this.physicsWorld, star3.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            bomb1.sprite = new Sprite(230,180,bomb1.textureRegion);
            bomb1.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb1.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
            bomb2.sprite = new Sprite(450, 265,bomb2.textureRegion );
            bomb2.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb2.sprite, BodyType.StaticBody, FIXTURE_DEF);
  
            bomb3.sprite = new Sprite(100, 380,bomb3.textureRegion );
            bomb3.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb3.sprite, BodyType.StaticBody, FIXTURE_DEF);
            
      
            bodySepet.setActive(true);   bodyYumurta.setActive(true);  bodyEngel1.setActive(true); bodyEngel2.setActive(true);
            bodyEngel3.setActive(true);  star1.body.setActive(true);  star2.body.setActive(true);
            star3.body.setActive(true);  bomb2.body.setActive(true); bomb3.body.setActive(true);  bomb1.body.setActive(true);
            kanat1.body.setActive(true);
            animSpriteAnime.setVisible(false);
            
            this.sahneOyun.attachChild(bgSprite);
            this.sahneOyun.attachChild(spriteDrop);
            this.sahneOyun.attachChild(animSpriteAnime);
            this.sahneOyun.attachChild(spriteSepet);
            this.sahneOyun.attachChild(spriteDoluSepet);
            this.sahneOyun.attachChild(spriteYumurta);
            this.sahneOyun.attachChild(kanat1.sprite);
            this.sahneOyun.attachChild(star1.sprite);
            this.sahneOyun.attachChild(star2.sprite);
            this.sahneOyun.attachChild(star3.sprite);
            this.sahneOyun.attachChild(bomb2.sprite);
            this.sahneOyun.attachChild(bomb3.sprite);
            this.sahneOyun.attachChild(bomb1.sprite);
            this.sahneOyun.attachChild(spriteEngel1); 
            this.sahneOyun.attachChild(geciciEngel1Sprite); 
            this.sahneOyun.attachChild(spriteEngel2);
            this.sahneOyun.attachChild(geciciEngel2Sprite); 
            this.sahneOyun.attachChild(spriteEngel3);
            this.sahneOyun.attachChild(geciciEngel3Sprite);
            
            this.sahneOyun.registerTouchArea(geciciEngel1Sprite);
            this.sahneOyun.registerTouchArea(geciciEngel2Sprite);
            this.sahneOyun.registerTouchArea(geciciEngel3Sprite);
            this.sahneOyun.registerTouchArea(spriteDrop);
            
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSepet, bodySepet, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteDoluSepet, bodySepet, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteYumurta, bodyYumurta, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(kanat1.sprite, kanat1.body, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star1.sprite, star1.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star2.sprite, star2.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star3.sprite, star3.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb2.sprite, bomb2.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb3.sprite, bomb3.body, true, true));
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb1.sprite, bomb1.body, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel1, bodyEngel1, true, true)); 
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel2, bodyEngel2, true, true));  
            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel3, bodyEngel3, true, true)); 		
 		
    }  
    private void oyunNesneleriniOlustur8(){
		{
				sahneOyun.clearChildScene();
		            
		            animSpriteAnime = new AnimatedSprite(470, 225, this.tiledTextRegAnime);
		            
		            
		            spriteDrop = new Sprite(650, 25, textRegDrop){
		            	@Override
		            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
		            	{ 
		            		bodyYumurta.setType(BodyType.DynamicBody);
		            		if (pSceneTouchEvent.isActionDown())
		            		{
		            			basladi = true;
		            			spriteDrop.setScaleX((float) 1.3);
		            			spriteDrop.setScaleY((float) 1.3);
		            		}
		            		if (pSceneTouchEvent.isActionUp())
		            		{
		            			spriteDrop.setScaleX(1);
		            			spriteDrop.setScaleY(1);
		            	}
		            		return true;
		            	}
		            };
		            bgSprite = new Sprite(0, 0, bgReg);
		            spriteSepet = new Sprite(470, 355, textRegSepet);//sepet
		            spriteDoluSepet = new Sprite(470, 355, textRegSepetDolu);//sepet
		            spriteDoluSepet.setVisible(false);
		            
		      
		            bodySepet = PhysicsFactory.createCircleBody(this.physicsWorld, spriteSepet, BodyType.StaticBody, FIXTURE_DEF);
		                    
		            spriteYumurta = new Sprite (350, 10, textRegYumurta);//yumurta
		            bodyYumurta =  PhysicsFactory.createBoxBody(this.physicsWorld, spriteYumurta, BodyType.StaticBody, FIXTURE_DEF);
		            
		        
		         
		           geciciEngel2Sprite = new Sprite(dokunulanNoktaX, dokunulanNoktaY, geciciRegEngel2){
	            	@Override
	            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
	            	{ 
	                	if(pSceneTouchEvent.isActionDown())
	    				{
	                		dokunulanNoktaX = pSceneTouchEvent.getX();
	                		dokunulanNoktaY = pSceneTouchEvent.getY();
	    				}
	    				if(pSceneTouchEvent.isActionMove())
	    				{
	    					dokunulanNoktaX = pSceneTouchEvent.getX();
	    					dokunulanNoktaY = pSceneTouchEvent.getY();
	    				}
	    				if(pSceneTouchEvent.isActionUp())
	    				{
	    				
	    				}
	    				return true; 
	                }
	                protected void onManagedUpdate(float pSecondsElapsed) 
	    			{
	                	if(!basladi){//basladýysa engel hareket etmemeli
	                	bodyEngel2.setTransform(dokunulanNoktaX/32,dokunulanNoktaY/32,0);
	                	geciciEngel2Sprite.setPosition(dokunulanNoktaX-64,dokunulanNoktaY-64);//burda çok zekalýydým
	                	}
	               	}
	            };
	               spriteEngel2 = new Sprite(dokunulanNokta1X,dokunulanNokta1Y,engelRegResim2);
		           bodyEngel2 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel2, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
		           
	       
	               geciciEngel1Sprite = new Sprite(dokunulanNokta1X, dokunulanNokta1Y, geciciRegEngel1){
		            	@Override
		            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
		            	{ 

	                	if(pSceneTouchEvent.isActionDown())
	    				{
	                		dokunulanNokta1X = pSceneTouchEvent.getX();
	                		dokunulanNokta1Y = pSceneTouchEvent.getY();
	    				}
	    				if(pSceneTouchEvent.isActionMove())
	    				{
	    					dokunulanNokta1X = pSceneTouchEvent.getX();
	    					dokunulanNokta1Y = pSceneTouchEvent.getY();
	    				}
	    				if(pSceneTouchEvent.isActionUp())
	    				{
	    				
	    				}
	    				return true; 
	                }
	                protected void onManagedUpdate(float pSecondsElapsed) 
	    			{
	                	if(!basladi){//basladýysa engel hareket etmemeli
	                	bodyEngel1.setTransform(dokunulanNokta1X/32,dokunulanNokta1Y/32,(float) 3.80);
	                	geciciEngel1Sprite.setPosition(dokunulanNokta1X-64,dokunulanNokta1Y-64);//burda çok zekalýydým
	                	}
	               	}
		            };
		           spriteEngel1 = new Sprite(dokunulanNoktaX,dokunulanNoktaY,engelRegResim1);
			       bodyEngel1 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel1, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
			       
			       geciciEngel3Sprite = new Sprite(dokunulanNokta2X, dokunulanNokta2Y, geciciRegEngel3){
		            	@Override
		            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
		            	{ 

	               	if(pSceneTouchEvent.isActionDown())
	   				{
	               		dokunulanNokta2X = pSceneTouchEvent.getX();
	               		dokunulanNokta2Y = pSceneTouchEvent.getY();
	   				}
	   				if(pSceneTouchEvent.isActionMove())
	   				{
	   					dokunulanNokta2X = pSceneTouchEvent.getX();
	   					dokunulanNokta2Y = pSceneTouchEvent.getY();
	   				}
	   				if(pSceneTouchEvent.isActionUp())
	   				{
	   				
	   				}
	   				return true; 
	               }
	               protected void onManagedUpdate(float pSecondsElapsed) 
	   			{
	              	if(!basladi){//basladýysa engel hareket etmemeli
	               	bodyEngel3.setTransform(dokunulanNokta2X/32,dokunulanNokta2Y/32,210);//(float) 2.5
	               	geciciEngel3Sprite.setPosition(dokunulanNokta2X-64,dokunulanNokta2Y-64);//burda çok zekalýydým
	               	}
	              	}
		            };
		           spriteEngel3 = new Sprite(dokunulanNokta2X,dokunulanNokta2Y,engelRegResim3);
			       bodyEngel3 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel3, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
			       
			       geciciEngel4Sprite = new Sprite(dokunulanNokta3X, dokunulanNokta3Y, geciciRegEngel4){
		            	@Override
		            	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
		            	{ 

	               	if(pSceneTouchEvent.isActionDown())
	   				{
	               		dokunulanNokta3X = pSceneTouchEvent.getX();
	               		dokunulanNokta3Y = pSceneTouchEvent.getY();
	   				}
	   				if(pSceneTouchEvent.isActionMove())
	   				{
	   					dokunulanNokta3X = pSceneTouchEvent.getX();
	   					dokunulanNokta3Y = pSceneTouchEvent.getY();
	   				}
	   				if(pSceneTouchEvent.isActionUp())
	   				{
	   				
	   				}
	   				return true; 
	               }
	               protected void onManagedUpdate(float pSecondsElapsed) 
	   			{
	              	if(!basladi){//basladýysa engel hareket etmemeli
	               	bodyEngel4.setTransform(dokunulanNokta3X/32,dokunulanNokta3Y/32,(float) 1.7);//(float) 2.5
	               	geciciEngel4Sprite.setPosition(dokunulanNokta3X-64,dokunulanNokta3Y-64);//burda çok zekalýydým
	               	}
	              	}
		            };
		           spriteEngel4 = new Sprite(dokunulanNokta3X,dokunulanNokta3Y,engelRegResim4);
			       bodyEngel4 = PhysicsFactory.createBoxBody(this.physicsWorld, spriteEngel4, BodyType.KinematicBody, FIXTURE_DEF_ENGEL);
			       
		            
		            kanat1.sprite = new Sprite(280, 430,kanat1.textureRegion );
			       	kanat1.body = PhysicsFactory.createBoxBody(this.physicsWorld, kanat1.sprite, BodyType.KinematicBody, FIXTURE_DEF);
			       	kanat1.body.setAngularVelocity(3);

			       	kanat2.sprite = new Sprite(650, 300,kanat2.textureRegion );
			       	kanat2.body = PhysicsFactory.createBoxBody(this.physicsWorld, kanat2.sprite, BodyType.KinematicBody, FIXTURE_DEF);
			       	kanat2.body.setAngularVelocity(-3);
			       	
			       	
		            star1.sprite = new Sprite(120, 150,star1.textureRegion );
		            star1.body = PhysicsFactory.createCircleBody(this.physicsWorld, star1.sprite, BodyType.StaticBody, FIXTURE_DEF);
		            
		            star2.sprite = new Sprite(350, 150 ,star2.textureRegion );
		            star2.body = PhysicsFactory.createCircleBody(this.physicsWorld, star2.sprite, BodyType.StaticBody, FIXTURE_DEF);
		            
		            star3.sprite = new Sprite(350, 350,star3.textureRegion );
	                star3.body = PhysicsFactory.createCircleBody(this.physicsWorld, star3.sprite, BodyType.StaticBody, FIXTURE_DEF);
	                
	                bomb1.sprite = new Sprite(230,180,bomb1.textureRegion);
		            bomb1.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb1.sprite, BodyType.StaticBody, FIXTURE_DEF);
		            
	                bomb2.sprite = new Sprite(465, 225,bomb2.textureRegion );
	                bomb2.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb2.sprite, BodyType.StaticBody, FIXTURE_DEF);
		  
		            bomb3.sprite = new Sprite(100, 380,bomb3.textureRegion );
		            bomb3.body = PhysicsFactory.createCircleBody(this.physicsWorld, bomb3.sprite, BodyType.StaticBody, FIXTURE_DEF);
		            
		            bodySepet.setActive(true);   bodyYumurta.setActive(true);  bodyEngel1.setActive(true); bodyEngel2.setActive(true);
		            bodyEngel3.setActive(true);  bodyEngel4.setActive(true);   star1.body.setActive(true);  star2.body.setActive(true);
		            star3.body.setActive(true);  bomb2.body.setActive(true);   bomb3.body.setActive(true);  bomb1.body.setActive(true);
		            kanat1.body.setActive(true);   kanat2.body.setActive(true);
		          
		            animSpriteAnime.setVisible(false);
		            
		            this.sahneOyun.attachChild(bgSprite);
		            this.sahneOyun.attachChild(spriteDrop);
		            this.sahneOyun.attachChild(animSpriteAnime);
		            this.sahneOyun.attachChild(spriteSepet);
		            this.sahneOyun.attachChild(spriteDoluSepet);
		            this.sahneOyun.attachChild(spriteYumurta);
		            this.sahneOyun.attachChild(kanat1.sprite);
		            this.sahneOyun.attachChild(kanat2.sprite);
		            this.sahneOyun.attachChild(star1.sprite);
		            this.sahneOyun.attachChild(star2.sprite);
		            this.sahneOyun.attachChild(star3.sprite);
		            this.sahneOyun.attachChild(bomb2.sprite);
		            this.sahneOyun.attachChild(bomb3.sprite);
		            this.sahneOyun.attachChild(bomb1.sprite);
		            this.sahneOyun.attachChild(spriteEngel1); 
		            this.sahneOyun.attachChild(geciciEngel1Sprite); 
		            this.sahneOyun.attachChild(spriteEngel2);
		            this.sahneOyun.attachChild(geciciEngel2Sprite); 
		            this.sahneOyun.attachChild(spriteEngel3);
		            this.sahneOyun.attachChild(geciciEngel3Sprite); 
		            this.sahneOyun.attachChild(spriteEngel4);
		            this.sahneOyun.attachChild(geciciEngel4Sprite); 
		          
		            
		            this.sahneOyun.registerTouchArea(geciciEngel1Sprite);
		            this.sahneOyun.registerTouchArea(geciciEngel2Sprite);
		            this.sahneOyun.registerTouchArea(geciciEngel3Sprite);
		            this.sahneOyun.registerTouchArea(geciciEngel4Sprite);
		            this.sahneOyun.registerTouchArea(spriteDrop);
		            
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSepet, bodySepet, true, true));
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteDoluSepet, bodySepet, true, true));
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteYumurta, bodyYumurta, true, true));
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(kanat1.sprite, kanat1.body, true, true));
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(kanat2.sprite, kanat2.body, true, true));
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star1.sprite, star1.body, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star2.sprite, star2.body, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(star3.sprite, star3.body, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb2.sprite, bomb2.body, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb3.sprite, bomb3.body, true, true));
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(bomb1.sprite, bomb1.body, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel1, bodyEngel1, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel2, bodyEngel2, true, true));  
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel3, bodyEngel3, true, true)); 
		            this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteEngel4, bodyEngel4, true, true)); 
			}

}
    
    @Override
	public void onResume(){
	super.onResume();
	startAppAd.onResume();
	}
    @Override
	public void onBackPressed() {
	startAppAd.onBackPressed();
	super.onBackPressed();
	}
	
	@Override
	public void onPause() {
	super.onPause();
	startAppAd.onPause();
	}
}