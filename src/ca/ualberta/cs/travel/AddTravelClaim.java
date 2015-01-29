package ca.ualberta.cs.travel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddTravelClaim extends Activity implements OnClickListener {
	
	private Button addedit;
	private TextView claimname;
	
	//taken from http://www.cnblogs.com/plokmju/p/android_datepiceker.html
	private Button btnDate, btntoDate;
	private Calendar calendar;
	private Calendar tocalendar;
	private TextView dateView;
	private TextView todateView;
	private int year, month, day;
	private int toyear, tomonth,today;
	private TextView descripition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travelclaim);
		//StudentListManager.initManager(this.getApplicationContext());
		btnDate = (Button) findViewById(R.id.setfromdate);
		//btnTime = (Button) findViewById(R.id.btnTimePickerDialog);
		btnDate.setOnClickListener(this);
		//btnTime.setOnClickListener(this);
	
		btntoDate = (Button) findViewById(R.id.settodate);
		btntoDate.setOnClickListener(this);
		//very important
    	ClaimListManager.initManager(this.getApplicationContext());
    	
    	todateView =(TextView) findViewById(R.id.datetotext);
        dateView = (TextView) findViewById(R.id.datefromtext);
        descripition=(EditText) findViewById(R.id.enterdescription);
        calendar = Calendar.getInstance();
        tocalendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        toyear = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        tomonth = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        today = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        showDate(toyear,tomonth+1,today);
        
        //Bundle b=this.getIntent().getExtras(); 
        //final int temp = b.getInt("id");
        
        
        //data
        addedit = (Button) findViewById(R.id.addtc);
        claimname = (TextView) findViewById(R.id.addtravelclaimname);
        Bundle extras = this.getIntent().getExtras();
        if (extras == null){
        	addedit.setOnClickListener(new addClaimAction());
        }
        else{
        	
			int set = extras.getInt("pos");
			Toast.makeText(this, "Expense Item"+ set, Toast.LENGTH_SHORT).show();
        	Claim storeclaim = ClaimListController.getClaimList().getPosition(set);
        	String name = storeclaim.getName();
        	claimname.setText(name);
        	
        	String fromdate;
        	fromdate = storeclaim.getFromDate();
        	dateView.setText(fromdate);
    		String todate;
    		todate = storeclaim.getToDate();
    		todateView.setText(todate);
    		
    		String des;
    		des = storeclaim.getdescripition();
    		descripition.setText(des);
    		
        	addedit.setOnClickListener(new EditClaimAction(storeclaim));
        }
        
	}
	   private DatePickerDialog.OnDateSetListener myDateListener
	   = new DatePickerDialog.OnDateSetListener() {

	   @Override
	   public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
	      // TODO Auto-generated method stub
	      // arg1 = year
	      // arg2 = month
	      // arg3 = day
	      showDate(arg1, arg2+1, arg3);
	   }
	   };
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.settodate:
	
			DatePickerDialog datePicker = new DatePickerDialog(
					AddTravelClaim.this, new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							showtoDate(year, monthOfYear+1, dayOfMonth);
						}
					}, 2013, 7, 20);
					
			datePicker.show();
			break;
		
		
		case R.id.setfromdate:
	
			DatePickerDialog datetoPicker = new DatePickerDialog(
					AddTravelClaim.this, new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							showDate(year, monthOfYear+1, dayOfMonth);
						}
					}, 2015, 7, 20);
					
			datetoPicker.show();
			break;
		}
	
		
	
	}
	
	
	
	private void showDate(int year, int month, int day) {
		
		 dateView.setText(new StringBuilder().append(day).append("/")
		     .append(month).append("/").append(year));
		   }
 
	private void showtoDate(int year, int month, int day) {
		 todateView.setText(new StringBuilder().append(day).append("/")
		     .append(month).append("/").append(year));
		   }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.travel_claim, menu);
		return true;
	}
/*	
	public void addClaimAction(View v) {
		Toast.makeText(this, "Adding A Claim", Toast.LENGTH_SHORT).show();
		ClaimListController st = new ClaimListController();
		EditText textView = (EditText) findViewById (R.id.addtravelclaimname);
		st.addClaim(new Claim(textView.getText().toString()));
		Intent intent = new Intent(AddTravelClaim.this, MainActivity.class);
    	startActivity(intent);
	}
*/
	private class addClaimAction implements OnClickListener {
		public void onClick(View v) {
			ClaimListController st = new ClaimListController();
			EditText claimname = (EditText) findViewById(R.id.addtravelclaimname);
			
			TextView dateView = (TextView) findViewById(R.id.datefromtext);
			TextView todateView= (TextView) findViewById(R.id.datetotext);
			EditText descripition = (EditText) findViewById(R.id.enterdescription);
			
			Claim claim = new Claim(claimname.getText().toString());
			st.addClaim(claim);
			claim.setFromDate(dateView.getText().toString());
			claim.setToDate(todateView.getText().toString());
			claim.setdescripition(descripition.getText().toString());
			
			Intent intent = new Intent(AddTravelClaim.this,
					MainActivity.class);
			startActivity(intent);
		}
	}

	public void expenseItemsAction(View v){
		Toast.makeText(this, "Expense Item", Toast.LENGTH_SHORT).show();
		
		//ClaimListController st = new ClaimListController();
		Intent intent = new Intent(AddTravelClaim.this, ExpenseItemActivity.class);
    	startActivity(intent);
	}
	
	private class EditClaimAction implements OnClickListener {
		private Claim orginalclaim;
		
		public EditClaimAction(Claim storeclaim) {
			// TODO Auto-generated constructor stub
			this.orginalclaim=storeclaim;
		}


		
		public void onClick(View v) {
			
			EditText claimname = (EditText) findViewById(R.id.addtravelclaimname);
			this.getClaim().setName(claimname.getText().toString());
			
			TextView fromdate= (TextView) findViewById(R.id.datefromtext);
			TextView todate = (TextView) findViewById(R.id.datetotext);
			this.getClaim().setFromDate(fromdate.getText().toString());
			this.getClaim().setToDate(todate.getText().toString());
			
			EditText descripition = (EditText) findViewById(R.id.enterdescription);
			this.getClaim().setdescripition(descripition.getText().toString());
			
			Intent intent = new Intent(AddTravelClaim.this,
					MainActivity.class);
			startActivity(intent);
		}

		private Claim getClaim() {
			// TODO Auto-generated method stub
			return orginalclaim;
		}
		
		
	}

	 public void BackToMainMenu (MenuItem menu){
	    	Toast.makeText(this, "Main Menu", Toast.LENGTH_SHORT).show();
	    	Intent intent= new Intent(AddTravelClaim.this, MainActivity.class);
	    	startActivity(intent);
	    }
}
