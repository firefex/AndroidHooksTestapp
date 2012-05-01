//
// AndroidHooksTestAppActivity.java
// Payfirma Corporation
//
// Created by Jason Collinge on 3/27/2012
// Copyright 2012 Payfirma Corporation. All rights reserved

package com.pftest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AndroidHooksTestAppActivity extends Activity
{
	public AlertDialog.Builder dialogBuilder = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// create an error dialog in case the Payfirma App cannot be found
		dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Missing Payfirma Mobile App");
		
		dialogBuilder.setMessage("Payfirma Mobile App not found. Please install the app from Google Play to proceed.");
		dialogBuilder.setPositiveButton("Go to Google Play",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	dialog.dismiss();
            	Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.payfirma"));
                startActivity(intent);
            }
        });
		dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	            	dialog.dismiss();
	            }
	        });

		
		// Setup our return value handler
		Intent intent = getIntent();
		if (intent != null)
		{
			String action = intent.getAction();

			// Make sure the action matches our app handle
			if (action != null && action.contentEquals("com.pftest.receivepaymentresult"))
			{
				// Parse the return values
				Bundle extras = getIntent().getExtras();
				if (extras != null)
				{
					TextView text = (TextView) findViewById(R.id.message);
					String displayStr = "Amount: " + extras.getString("pf_amount");
					displayStr = displayStr + "     Tip: " + extras.getString("pf_tip");
					displayStr = displayStr + "     Tax: " + extras.getString("pf_tax");
					displayStr = displayStr + "     Total: " + extras.getString("pf_total");
					displayStr = displayStr + "\n     Description: " + extras.getString("pf_description");
					displayStr = displayStr + "\n     Card Number: " + extras.getString("pf_masked_card");
					displayStr = displayStr + "\n     ID: " + extras.getString("pf_transaction_id");
					displayStr = displayStr + "\n     Result: " + extras.getString("pf_result");
					displayStr = displayStr + "\n     Marchant Name: " + extras.getString("pf_merchant_name");
					displayStr = displayStr + "\n     Timestamp: " + extras.getString("pf_timestamp");
					displayStr = displayStr + "\n     Type: " + extras.getString("pf_transaction_type");

					text.setText(displayStr);
				}
			}
		}

		// setup our launch buttons
		
		// To Launch a Sale:
		final Button button = (Button) findViewById(R.id.Sale);
		button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Indicate that we should launch the payfirma app to make a payment
				Intent intent = new Intent("com.payfirma.makepayment");

				// Set up our variables
				intent.putExtra("com.payfirma.input.amount", "1.25");
				intent.putExtra("com.payfirma.input.description", "Can of Soda");
				intent.putExtra("com.payfirma.input.transaction_type", "SALE");
				intent.putExtra("com.payfirma.input.sending_app", "Test App");
				intent.putExtra("com.payfirma.input.email", "customer@somewhere.com");
				
				// indicate the handle used to call back to us with the result
				intent.putExtra("com.payfirma.input.callback", "com.pftest.receivepaymentresult");

				try
				{
					// Attempt to launch the Payfirma App
					startActivity(intent);
				}
				catch (ActivityNotFoundException e)
				{
					// Oops, the app isn't installed so display an error.
					TextView text = (TextView) findViewById(R.id.message);
					text.setText("Payfirma Application not found.");
					AlertDialog alert = dialogBuilder.create();
				    alert.show();
				}
			}
		});
		
		// Set up the refund button
		final Button Refbutton = (Button) findViewById(R.id.Refund);
		Refbutton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				EditText rID = (EditText) findViewById(R.id.refundID);
								
				// Indicate that we should launch the payfirma app to make a payment
				Intent intent = new Intent("com.payfirma.makepayment");

				// Set up our variables				
				String oid = rID.getText().toString();
				intent.putExtra("com.payfirma.input.amount", "1.25");
				intent.putExtra("com.payfirma.input.sending_app", "Test App");
				intent.putExtra("com.payfirma.input.description", "Refund: Can of Soda");
				intent.putExtra("com.payfirma.input.transaction_type", "REFUND");
				intent.putExtra("com.payfirma.input.email", "customer@somewhere.com");
				
				// indicate the transaction ID of the transaction we wish to refund
				intent.putExtra("com.payfirma.input.orig_id", oid);
				
				// indicate the handle used to call back to us with the result
				intent.putExtra("com.payfirma.input.callback", "com.pftest.receivepaymentresult");
				
				try
				{
					// Attempt to launch the Payfirma App
					startActivity(intent);
				}
				catch (ActivityNotFoundException e)
				{
					// Oops, the app isn't installed so display an error.
					TextView text = (TextView) findViewById(R.id.message);
					text.setText("Payfirma Application not found.");
					AlertDialog alert = dialogBuilder.create();
				    alert.show();
				}
			}
		});
		
		
		final Button authbutton = (Button) findViewById(R.id.Auth);
		authbutton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Indicate that we should launch the payfirma app to make a payment
				Intent intent = new Intent("com.payfirma.makepayment");

				// Set up our variables				
				intent.putExtra("com.payfirma.input.amount", "1.25");
				intent.putExtra("com.payfirma.input.description", "Can of Soda");
				intent.putExtra("com.payfirma.input.transaction_type", "AUTH");
				intent.putExtra("com.payfirma.input.email", "customer@somewhere.com");
				
				// indicate the handle used to call back to us with the result
				intent.putExtra("com.payfirma.input.callback", "com.pftest.receivepaymentresult");
				
				try
				{
					// Attempt to launch the Payfirma App
					startActivity(intent);
				}
				catch (ActivityNotFoundException e)
				{
					// Oops, the app isn't installed so display an error.
					TextView text = (TextView) findViewById(R.id.message);
					text.setText("Payfirma Application not found.");
					AlertDialog alert = dialogBuilder.create();
				    alert.show();
				}
			}
		});
	}

}