package com.example.pruebaconexion_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	TextView oTextView;
	private ConnectivityManager connectivityManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		oTextView = findViewById(R.id.textView);
	}

	public void conectarOnClick(View v) {

		try {
			if (isConnected()) {
				String sRespuesta = conectar();
				if (null == sRespuesta) // If response is null, an exception has happened
					Toast.makeText(getApplicationContext(), "ERROR_COMUNICACION", Toast.LENGTH_SHORT).show();
				else
					oTextView.setText(sRespuesta);
			} else {
				Toast.makeText(getApplicationContext(), "ERROR_NO_INTERNET", Toast.LENGTH_SHORT).show();
			}
		} catch (InterruptedException e) {
			// This cannot happen!
			Toast.makeText(getApplicationContext(), "ERROR_GENERAL", Toast.LENGTH_SHORT).show();
		}
	}


	private String conectar() throws InterruptedException  {
		ClientThread clientThread = new ClientThread();
		Thread thread = new Thread(clientThread);
		thread.start();
		thread.join(); // Awaiting response from the server...

		return clientThread.getResponse();
	}

	public boolean isConnected() {
		boolean ret = false;
		try {
			connectivityManager = (ConnectivityManager) getApplicationContext()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if ((networkInfo != null) && (networkInfo.isAvailable()) && (networkInfo.isConnected()))
				ret = true;
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error_comunicación", Toast.LENGTH_SHORT).show();
		}

		return ret;
	}
}