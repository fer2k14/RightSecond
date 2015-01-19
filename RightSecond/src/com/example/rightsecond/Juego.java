/*
 * Right Second version 0.8
 * Autor: Fernando Arenas Alapont
 */
package com.example.rightsecond;

import com.example.rightsecond.R.string;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class Juego extends Activity implements OnClickListener{
	
	private TextView numeroAveriguar, textViewNivel; //Para probar la cuenta.
	private Button btnJugar;
	private int tiempo, tiempoAcertar; //Guarda el segundo del contador.
	private boolean juegoEnEjecucion; //Ejecucion del juego en marcha.
	private int numRandom, nivel, vidas;
	CountDownTimer timer; //Timer del juego.
	CountDownTimer cuentaAtras; //Timer de la cuenta atras.
	
	private int width; //Guarda el ancho del dispositivo.
	private int height; //Guarda el alto del dispositivo.
	
	//Animacion.
	ObjectAnimator animRestIn = null;
	AnimationDrawable frameAnimation;	
	View frameAnimationView;
	
	private ImageView vida1, vida2, vida3, ivInfo; //ImageView para mostrar las vidas.
	private Thread hiloAnimacion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_juego);
		
		//Recoge las medidas del dispositivo.
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		height = metrics.heightPixels;
		
		numeroAveriguar = (TextView) findViewById(R.id.textViewNumAveriguar);
		ivInfo = (ImageView) findViewById(R.id.imageViewInfo);
		textViewNivel = (TextView) findViewById(R.id.textViewNumero);
		btnJugar = (Button) findViewById(R.id.btnJugar);
		btnJugar.setOnClickListener(this);
		
		vida1 = (ImageView) findViewById(R.id.imageViewVida1);
		vida2 = (ImageView) findViewById(R.id.imageViewVida2);
		vida3 = (ImageView) findViewById(R.id.imageViewVida3);

		juegoEnEjecucion=false;
		
		frameAnimationView = findViewById(R.id.imageViewInfo);
		frameAnimationView.setBackground(null);
		
		//Instancia de variables.
		nivel=1;
		vidas = 3;
		numeroAleatorio();

		//info.setHeight(width-20);
	}
	
	public void juego(){				
		timer = new CountDownTimer((tiempoAcertar+1)*1000, 1000) {

		     public void onTick(long millisUntilFinished) {
		    	 
		    	 tiempo=(int) (millisUntilFinished / 1000);
		    	 
		    	 //Cuenta hasta 3 y desaparece el contador.
		    	 if(tiempo==tiempoAcertar)
		    	 {
		    		 ivInfo.setImageResource(R.drawable.icono_rs_uno);

		    	 }
		    	 else if(tiempo==tiempoAcertar-1)
		    	 {
		    		 ivInfo.setImageResource(R.drawable.icono_rs_dos);
		    	 }
		    	 else if(tiempo==tiempoAcertar-2)
		    	 {
		    		 ivInfo.setImageResource(R.drawable.icono_rs_tres);
		    	 }		    	 
		    	 else{
		    		 ivInfo.setImageResource(0);
		    		 
//		    		 if(juegoEnEjecucion){
//		    		 hiloAnimacion=  new Thread(new Runnable() {
//		    		        public void run() {
//		    		            	numRandom = (int)((Math.random()*5)); //Obtiene un numero random.
//		    		            	if(numRandom==1){
//		    		            		ivInfo.setImageResource(R.drawable.iconoruno);
//		    		            	}
//		    		            	if(numRandom==6){
//		    		            		ivInfo.setImageResource(R.drawable.iconordos);
//		    		            	}
//		    		            	if(numRandom==11){
//		    		            		ivInfo.setImageResource(R.drawable.iconortres);
//		    		            	}
//		    		            	if(numRandom==16){
//		    		            		ivInfo.setImageResource(R.drawable.iconorcuatro);
//		    		            	}
//		    		        }
//		    		 });
//		    		 hiloAnimacion.start();
//		    		 }else{
//		    			 hiloAnimacion.
//		    		 }
		    		 frameAnimationView.setBackgroundResource(R.drawable.animacion_crono);
		    		 frameAnimation = (AnimationDrawable) frameAnimationView.getBackground();
		    		 frameAnimation.start();
		    	 }	    	 
		     }

		     //Si el contador llega a 0.
		     public void onFinish() {
		    	 vidas--;
	    		 imagenesVidas();
		    	 if(vidas==0)
		    	 {
		    		 
		    		 dialogos("¡Te pasaste de tiempo! Ya no te quedan vidas", 3);

		    	 }
		    	 else
		    	 {
		    		 dialogos("¡Te pasaste de tiempo! ¿Quieres intentarlo de nuevo?", 2);
		    	 }
		    	 
		    	 neutralizarJuego();
		    	 
		     }
		};
		timer.start();
	}
	
	public void numeroAleatorio(){
		numRandom = (int)((Math.random()*10)); //Obtiene un numero random.	
		tiempoAcertar=(tiempoAcertar+5)+numRandom;
		textViewNivel.setText("N "+nivel);  
		numeroAveriguar.setText(tiempoAcertar+"\"");	//Muestra el numero a averiguar.
	}
	
	@Override
	public void onClick(View v) {
		if(v==btnJugar){
			if(juegoEnEjecucion==false){
				juegoEnEjecucion=true;
				btnJugar.setText(R.string.detener);
				btnJugar.setBackgroundColor(-65536);
				juego();

			}else
			{
				acertarTiempo();
			}
		}	
	}
	
	//Lo que pasa si se acierta o se falla el tiempo.
	public void acertarTiempo(){
		if(tiempo==1){
			dialogos("¡Enhorabuena, superaste el nivel "+nivel+"!", 1);
			nivel++;
			numeroAleatorio();
		}
		else{
			vidas--;
			
			if(vidas==0){
		    	dialogos("Perdiste por "+(tiempo-1)+" segundos. Ya no te quedan vidas", 3);
			}
			else{
		    	imagenesVidas();
		    	dialogos("Perdiste por "+(tiempo-1)+" segundos. ¿Quieres continuar?", 2);
			}
		}			
		neutralizarJuego();
	}
	
	/*
	 * Crea un dialogo segun el tipo que le pasamos. 1 Nivel superado. 2 Perder vida. 3 Perder partida.
	 */
	public void dialogos(String dial, int tipo){
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setCancelable(false); //El dialogo es modal.
		dialogo.setMessage(dial); //Le pasamos un texto al dialogo como parametro.
		
		if(tipo==1)
		{
			dialogo.setPositiveButton("Continuar", null);
		}
		
		if(tipo==2)
		{
			dialogo.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	            	dialog.dismiss();
	            }
	        });
			dialogo.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	            	finish();
	            }
	        });
		}	
		
		if(tipo==3)
		{
			dialogo.setPositiveButton("Terminar partida", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	            	dialog.dismiss();
	            	finish();
	            }
	        });
		}
		
		dialogo.show();	
	}
	
	/*
	 * Devuelve los graficos del juego a su estado inicial.
	 */
	public void neutralizarJuego(){
		ivInfo.setImageResource(R.drawable.icono_rs_512);
		btnJugar.setText(R.string.jugar);
		int colorOro = Color.parseColor("#FFCC00"); //Parsea el color a un entero.
		btnJugar.setBackgroundColor(colorOro); // Pone el color oro al boton.
		juegoEnEjecucion=false;
		timer.cancel();	//Detiene el contador de tiempo.
				
		//frameAnimation.stop(); //Detiene la animacion.
	}
	
	public void imagenesVidas(){
		if(vidas==2){
			vida3.setImageResource(R.drawable.cor_vacio_48);
		}
		else if(vidas==1){
			vida2.setImageResource(R.drawable.cor_vacio_48);
		}
		else if(vidas==0){
			vida1.setImageResource(R.drawable.cor_vacio_48);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.juego, menu);
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
}
