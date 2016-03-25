package edu.ncc.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.app.ActionBar;
import android.content.Context;


public class MainActivity extends Activity implements View.OnClickListener, ActionBar.OnNavigationListener{

	private ImageButton[] buttons;
	private int numClicked;
	private int[] imageNums;
	private boolean[] clicked;
	private int wrongCount;
	private int[] match;
	private int one;
	private int two;
	private int matchCount;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Bundle b = this.getIntent().getExtras();
		String userName = b.getString("name");
		
		((TextView)findViewById(R.id.Welcome_username)).setText("Welcome " + (CharSequence)userName);

		// create the array to store references to the buttons
		buttons = new ImageButton[12];
		
		// create the array to store clicked status of each button
		clicked = new boolean[12];
		for(int i =0; i < clicked.length; i++){
			clicked[i] = false;
		}
		
		match = new int[2];

		// get the id of the first button
		int idIndex = R.id.button0;

		// store the references into the array by cycling through the id indices & set the listener
		for (int i=0; i<buttons.length; i++) 
		{
			buttons[i] = (ImageButton)findViewById(idIndex++);
			buttons[i].setOnClickListener(this);
		}

		//get the id's for the images and store 2 of each
		imageNums = new int[12];
		imageNums[0] = R.drawable.angry;
		imageNums[1] = R.drawable.angry;
		imageNums[2] = R.drawable.blushing;
		imageNums[3] = R.drawable.blushing;
		imageNums[4] = R.drawable.crying;
		imageNums[5] = R.drawable.crying;
		imageNums[6] = R.drawable.haha;
		imageNums[7] = R.drawable.haha;
		imageNums[8] = R.drawable.sad;
		imageNums[9] = R.drawable.sad;
		imageNums[10] = R.drawable.smile;
		imageNums[11] = R.drawable.smile;

		// randomize the values
		int r1, r2;
		int temp;
		for (int i=0; i<20; i++)
		{
			r1 = (int)(Math.random()*12);
			r2 = (int)(Math.random()*12);
			temp = imageNums[r1];
			imageNums[r1] = imageNums[r2];
			imageNums[r2]= temp;
		}

		numClicked = 0;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setTitle("Memory Game");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_reset){
        	reset();
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void reset(){
    	for(int i = 0; i < buttons.length; i++){
    		buttons[i].setImageResource(R.drawable.defaultsmile);
    		//set all elements in clicked to false
    		clicked[i] = false;
    	}
		int r1, r2;
		int temp;
		for (int i=0; i<20; i++)
		{
			r1 = (int)(Math.random()*12);
			r2 = (int)(Math.random()*12);
			temp = imageNums[r1];
			imageNums[r1] = imageNums[r2];
			imageNums[r2]= temp;
		}
		numClicked = 0;
		wrongCount = 0;
		match = new int[2];
		matchCount = 0;
    }
    
	
	@Override
	public void onClick(View view) {
		//set parameters index(1), context(2), win(3), lose(4), and duration(5)
		int index = view.getId() - R.id.button0;//(1)
		Context context = getApplicationContext();//(2)
		CharSequence win = "You win!!";//(3)
		CharSequence lose = "You lose";//(4)
		int duration = Toast.LENGTH_LONG;//(5)
		
		//if the button hasn't already been clicked(1), set it to clicked(2),
		// and change the image from '?' to its proper face(3)
		if(clicked[index] == false){//(1)
			clicked[index] = true;//(2)
			buttons[index].setImageResource(imageNums[index]);//(3)
			
			//if the numClick count is 0(1), 
			//set the match array at index 0 equal to imageNums at the current index(2)
			//set int one equal to the current index for later(3)
			//increase the numClicked count(4)
			//increase the wrongCount count(5)
			if(numClicked == 0){//(1)
				match[numClicked] = imageNums[index];//(2)
				one = index;//(3)
				numClicked++;//(4)
				wrongCount++;//(5)		
			}
			
			//otherwise, if the numClick is NOT 0(1), 
			//set the match array at index 1 equal to imageNums at the current index(2)
			//set int two equal to the current index for later(3)
			//increase the wrongCount count(4)
			//check for a match(5)
			//if its a match, increase match count(6)
			//otherwise(7)
			//declare new Handler(8)
			// delay setting the images back to the default so the user can see 
			//what was chosen(9)
			//and if there are not 19 clicks yet(10)
			//set the images back to the default(11)(12),
			//and set the buttons back to unclicked(13)(14)
			//finally, reset numClicked(15)
			else{//(1)
				match[numClicked] = imageNums[index];//(2)
				two = index;//(3)
				wrongCount++;//(4)
				if(match[0] == match[1]){//(5)
					matchCount++;//(6)
				}
				else{//(7)
					//play incorrect sound..?
					new Handler().postDelayed(new Runnable(){//(8)
							public void run(){//(9) 
								if(wrongCount < 19){//(10)
									buttons[one].setImageResource(R.drawable.defaultsmile);//(11)
									buttons[two].setImageResource(R.drawable.defaultsmile);//(12)
									clicked[one] = false;//(13)
									clicked[two] = false;//(14)
								}
							}
						}, 1000);//(1000 milliseconds = 1 second)
				}

				//reset numClicked(15)
				numClicked = 0;//(15)
			}
		}
		//if you've clicked more than 18 times(1)
		//create new toast object(2)
		//call toast to tell you you lose(3)
		//delay the reset for 3 seconds so the user can see 
		//what was chosen with a new handler(4)
		// reset the board after the delay now that you've lost(5)
		if(wrongCount > 18){//(1)
			Toast toast = Toast.makeText(context, lose, duration);//(2)
			toast.show();//(3)
			new Handler().postDelayed(new Runnable(){//(4)
					public void run(){
						// reset the board after the delay now that you've lost(1)
						reset();//(5)
					}
				}, 3000);//(3000 milliseconds = 3 seconds)
		}
		//if there are 6 matches(1)
		//check to see if you got all 6 matches in under 19 moves(2),
		//if you did, declare a new toast(3),
		//then call the toast to declare victory!(4)
		//then delay the reset by calling a new Handler(5)
		//then reset(6)
		if(matchCount > 5){//(1)
			if(wrongCount < 19){//(2)
				Toast toast = Toast.makeText(context, win, duration);//(3)
				toast.show();//(4)
				new Handler().postDelayed(new Runnable(){//(5)
					public void run(){
						reset();//(6)
					}
				}, 3000);//(3000 milliseconds = 3 seconds)
			}
		}
	}
	


	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
				//put the array of Booleans into the bundle so it is passed to OnRestoreInstanceState
				//method
				savedInstanceState.putBooleanArray("clickedState", clicked);
				//put the array of Ints into the bundle so it is passed to OnRestoreInstanceState
				//method
				savedInstanceState.putIntArray("imageOrder", imageNums);
				//put the array of Ints into the bundle so it is passed to OnRestoreInstanceState
				//method
				savedInstanceState.putIntArray("matchArray", match);
				//put the Int into the bundle so it is passed to OnrestoreInstanceState method
				savedInstanceState.putInt("wrongCount", wrongCount);
				//put the Int into the bundle so it is passed to OnrestoreInstanceState method
				savedInstanceState.putInt("one", one);
				//put the Int into the bundle so it is passed to OnrestoreInstanceState method
				savedInstanceState.putInt("two", two);
				//put the Int into the bundle so it is passed to OnrestoreInstanceState method
				savedInstanceState.putInt("numClicked", numClicked);
				//put the Int into the bundle so it is passed to OnrestoreInstanceState method
				savedInstanceState.putInt("matchCount", matchCount);
				// Always call the superclass so it can save the view hierarchy state
				super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle restoreInstanceState){
		//call the super class
		super.onRestoreInstanceState(restoreInstanceState);
		//retrieve the currentText from the Bundle
		clicked = restoreInstanceState.getBooleanArray("clickedState");
		imageNums = restoreInstanceState.getIntArray("imageOrder");
		match = restoreInstanceState.getIntArray("matchArray");
		wrongCount = restoreInstanceState.getInt("wrongCount");
		one = restoreInstanceState.getInt("one");
		two = restoreInstanceState.getInt("two");
		numClicked = restoreInstanceState.getInt("numClicked");
		matchCount = restoreInstanceState.getInt("matchCount");
		//place the current Strings onto the appropriate buttons
		for(int i = 0; i < imageNums.length; i++){
			if(clicked[i]){
				buttons[i].setImageResource(imageNums[i]);
			}
		}
	}
}
	

