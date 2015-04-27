package space.ventura;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//This is the main activity that throws the screen with start button and description of the game 
public class spaceventura extends Activity {
    /** Called when the activity is first created. */
   
    Button button;
	@Override

protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.main);
	    
	    
	    this.button = (Button)this.findViewById(R.id.START);
	    this.button.setOnClickListener( new OnClickListener() 
	    {
	    	@Override
	    	public void onClick(View v)
	    	{
	    		//Call a new activity which implements the complete game.
	    		Intent myIntent = new Intent(spaceventura.this,Accelerometer.class);
	    		startActivity(myIntent);
	    	}
	    });
	  /* v.setOnTouchListener(new v.OnTouchListener()
	   {
		    public boolean onTouch(View v, MotionEvent e) {
		    	 
		    }
		});*/
    
    }
}