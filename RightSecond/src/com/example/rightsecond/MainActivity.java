/*
 * Right Second version 0.8
 * Autor: Fernando Arenas Alapont
 */
package com.example.rightsecond;

import com.example.rightsecond.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{
	
	private Button btnNuevoJuego, btnSalir, btnInstrucciones;
	private EditText editTextNombreUsuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnNuevoJuego = (Button) findViewById(R.id.btnCarga);
		btnNuevoJuego.setOnClickListener(this);
		btnSalir = (Button) findViewById(R.id.btnSalir);
		btnSalir.setOnClickListener(this);
		btnInstrucciones = (Button) findViewById(R.id.btnInstrucciones);
		btnInstrucciones.setOnClickListener(this);
		editTextNombreUsuario = (EditText) findViewById(R.id.editTextNombre);
	}
	
	/*
	 * Muestra el dialogo las instrucciones.
	 */
	protected void dialogInstrucciones(){
		AlertDialog.Builder dialogInstrucciones = new AlertDialog.Builder(this);
		dialogInstrucciones.setCancelable(false);
		dialogInstrucciones.setMessage(string.mecanica);
		dialogInstrucciones.setPositiveButton("Cerrar", null);
		dialogInstrucciones.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if(v==btnNuevoJuego){
			Intent i = new Intent(this, Juego.class);
			startActivity(i);
		}
		
		if(v==btnSalir){
			finish();
		}
		
		if(v==btnInstrucciones){
			dialogInstrucciones();
		}
		
	}
}
