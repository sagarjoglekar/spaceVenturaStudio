package space.ventura;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.view.View;




//This class completely implements the game logic 
public class Accelerometer extends Activity 
{
	//Tagging for monitoring in logcat
	private static final String TAG = "Game";

    private SensorManager mSensorManager;
    private Sensor mSensor;
    
    private float[] mValues;
    private SampleView View1;
    public int collisionflag = 0;
    float tempangle = 0.0f;
    
    private final SensorEventListener mListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent event) {
           //if (Config.LOGD) Log.d(TAG,
            //        "sensorChanged (" + event.values[0] + ", " + event.values[1] + ", " + event.values[2] + ")");
            //Sensor values which wold be used later
        	mValues = event.values;
            
          
            if (View1 != null) {
                View1.invalidate();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onCreate(Bundle icicle) {
    	
        super.onCreate(icicle);
        View1 = new SampleView(this);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //Use orientation sensor. Can put accelerometer and several other sensor names here
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        setContentView(View1);
       
    }

    @Override
    protected void onResume()
    {
        if (Config.LOGD) Log.d(TAG, "onResume");
        super.onResume();

        mSensorManager.registerListener(mListener, mSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop()
    {
        if (Config.LOGD) Log.d(TAG, "onStop");
        mSensorManager.unregisterListener(mListener);
        super.onStop();
    }

   private class SampleView extends View {
	   //The Set the complete Drawing 2D background
	   public ShapeDrawable mDrawable1;
		public ShapeDrawable mDrawable2;
		public ShapeDrawable mDrawable3;
		public ShapeDrawable mDrawable4;
		public ShapeDrawable mDrawable5;
		public ShapeDrawable mDrawable6;
		//public Path dest = new Path();
		Paint ship = new Paint();
		Paint land = new Paint();
		Paint fail = new Paint();
		//Coordinates for the landing pad
		 public float pivx = 240.0f;
	     public float pivy = 10.0f;

        public SampleView(Context context) {
            super(context);

            
            

            
        }

        @Override protected void onDraw(Canvas canvas) 
        {
        	//Using Path to draw the space ship
        	Path dest = new Path();
        	canvas.drawColor(0x00000000);
        	int x = 20;
            int y = 50;
            int width = 80;
            int height = 70;
            //Flags for collision and success detection
            int collisions = 0,destination = 0;
            float anglediff;
            
            //Routine to check the shake of the device
            if(mValues != null)
            {
            anglediff = mValues[0]-tempangle;
            if(anglediff > 40.0f || anglediff < -40.0f)
            {
            	collisionflag =0;
            	this.pivx = 240.0f;
            	this.pivy = 10.0f;
            	
            }
            Log.d(TAG, "Angle"+anglediff);
            tempangle = mValues[0];
            }
            
            
           
           
           
                     
        	setBackgroundResource(R.drawable.back1);
            ship.setColor(0xffff0000);
            land.setColor(0xff0000ff);
            land.setTextSize(68);
            
            
            fail.setColor(0xffff0000);
            fail.setTextSize(68);
            //Draw all the objects on the screen 
               

            mDrawable1 = new ShapeDrawable(new OvalShape());
            mDrawable1.getPaint().setColor(0xffffffff);
            mDrawable1.setBounds(x*10, y*3, x*10 + width, y*3 + height);
            
            mDrawable2 = new ShapeDrawable(new OvalShape());
            mDrawable2.getPaint().setColor(0xffffffff);
            mDrawable2.setBounds(x*20, y*5, x*20 + width, y*5 + height);
            
            mDrawable3 = new ShapeDrawable(new OvalShape());
            mDrawable3.getPaint().setColor(0xffffffff);
            mDrawable3.setBounds(x*12, y*6, x*12 + width, y*6 + height);
            
            mDrawable4 = new ShapeDrawable(new OvalShape());
            mDrawable4.getPaint().setColor(0xffffffff);
            mDrawable4.setBounds(x, y*8, x + width, y*8 + height);
            
            mDrawable5 = new ShapeDrawable(new OvalShape());
            mDrawable5.getPaint().setColor(0xffffffff);
            mDrawable5.setBounds(x, y, x + width, y + height);
            
            mDrawable6 = new ShapeDrawable(new OvalShape());
            mDrawable6.getPaint().setColor(0xffffffff);
            mDrawable6.setBounds(x*18, y*10, x*18 + width, y*10 + height);
        	

        	//View1.invalidate();
           setBackgroundResource(R.drawable.back1);
           //setContentView(View1);
           mDrawable1.draw(canvas);
           mDrawable2.draw(canvas);
           mDrawable3.draw(canvas);
           mDrawable4.draw(canvas);
           mDrawable5.draw(canvas);
           mDrawable6.draw(canvas);
           //float offx = 0 , offy = 0;
           canvas.drawRect(230,660,250,760, land);
           canvas.drawRect(200,690,280,710, land);
           //Move the space ship on the screen depending on the sensor values 
           
           if (mValues != null && collisionflag ==0 ) 
           {
        	   if(mValues[2] > 5 || mValues[2] < -5 )
        	   {
        		//offx = mValues[1]/10;
        		if(mValues[2] < 0 )
        		{
        	     this.pivx= this.pivx - 5;
        		}
        		else if (mValues[2] > 0)
        		{
        	     this.pivx=this.pivx + 5;
        		}
        	   }
        	   if(mValues[1] > 5 || mValues[1] < -5)
        	   {
        		//offy = mValues[2]/10;
        		if(mValues[1] < 0)
        		{
        		this.pivy=this.pivy + 5;
        		}
        		else if (mValues[1] > 0)
        		{
        	    this.pivy=this.pivy - 5;
        		}
        		
        	   }
        	   //Draw the revised position of the ship
        	   dest.moveTo(pivx, pivy);
               dest.lineTo((pivx-20), (pivy+70));
               dest.lineTo(pivx, (pivy+50));
               dest.lineTo((pivx+20), (pivy+70));
               dest.lineTo(pivx, pivy);
               dest.close();
               canvas.drawPath(dest, ship);
               //Check for collisions against objects 
              collisions = this.checkcollision(dest);
              if(collisions > 0)
              {
            	  collisionflag=1;
            	  canvas.drawText("You FAIL!! :(",50, 400, fail);
            	  
            	  
              }
              else
              {
              //Check if the destination is reached  
              canvas.drawPath(dest, ship);
              destination = this.reacheddestination(dest);
              //Log.d(TAG, "Method called");
              if(destination > 0)
              {
               collisionflag=2;
           	   canvas.drawText("You WIN! :)",50, 400, land); 
              }
              
              //
             
              }
               
                             
           }
           else
           {
        	   dest.moveTo(pivx, pivy);
               dest.lineTo((pivx-20), (pivy+70));
               dest.lineTo(pivx, (pivy+50));
               dest.lineTo((pivx+20), (pivy+70));
               dest.lineTo(pivx, pivy);
               dest.close();
               if (collisionflag==1)
               {
               canvas.drawPath(dest, ship);
               canvas.drawText("You FAIL!! :(",50, 400, fail);
               }
               else
               {
            	   canvas.drawPath(dest, ship);
            	   canvas.drawText("You WIN! :)",50, 400, land);
               }
               }
        }
           
           
   
        
        public int checkcollision(Path temp)
        {
        //Check the collision using the intersection of the bounding rectangles of the objects 	
          Rect bound1, bound2, bound3, bound4, bound5, bound6;
          int flag = 0;
          bound1 = mDrawable1.copyBounds();
          bound2 = mDrawable2.copyBounds();
          bound3 = mDrawable3.copyBounds();
          bound4 = mDrawable4.copyBounds();
          bound5 = mDrawable5.copyBounds();
          bound6 = mDrawable6.copyBounds();
          RectF ship = new RectF();
          temp.computeBounds(ship, true);
          Rect shiptemp = new Rect((int)ship.left,(int)ship.top,(int)ship.right,(int)ship.bottom);
          if(shiptemp.intersect(bound1))
          {
        	  //Toast.makeText(Accelerometer.this,"COLLISION", 1000);
        	  //Log.d(TAG, "COLLISION DETECTED");
        	  flag++;
          }
          if(shiptemp.intersect(bound2))
          {
        	  //Toast.makeText(Accelerometer.this,"COLLISION", 1000);
        	  //Log.d(TAG, "COLLISION DETECTED");
        	  flag++;
          }
          if(shiptemp.intersect(bound3))
          {
        	  //Toast.makeText(Accelerometer.this,"COLLISION", 1000);
        	  //Log.d(TAG, "COLLISION DETECTED");
        	  flag++;
          }
          if(shiptemp.intersect(bound4))
          {
        	  //Toast.makeText(Accelerometer.this,"COLLISION", 1000);
        	  //Log.d(TAG, "COLLISION DETECTED");
        	  flag++;
          }
          if(shiptemp.intersect(bound5))
          {
        	  //Toast.makeText(Accelerometer.this,"COLLISION", 1000);
        	  //Log.d(TAG, "COLLISION DETECTED");
        	  flag++;
          }
          if(shiptemp.intersect(bound6))
          {
        	  //Toast.makeText(Accelerometer.this,"COLLISION", 1000);
        	  //Log.d(TAG, "COLLISION DETECTED");
        	  flag++;
          }
           return flag;        
        }
        
        public int reacheddestination(Path temp)
        {
        	
            //Check destination using the intersection of DEstination and Ship bounds
        	Rect destbound = new Rect(200,660,280,760);
        	//Log.d(TAG,"Rectangle"+destbound.bottom+"a"+destbound.left+"b"+destbound.right+"c"+destbound.top);
        	RectF shiptemp = new RectF();
            temp.computeBounds(shiptemp, true);
            Rect shipbound = new Rect((int)shiptemp.left,(int)shiptemp.top,(int)shiptemp.right,(int)shiptemp.bottom);
            if(shipbound.intersect(destbound))
            {
         	   
         	   //Log.d(TAG,"Reached Landing pad");
         	   return 5;
            }
            else
            {
            	return 0;
            }
        }
        
       
        
          
          //return false;
        	
        }
}

     