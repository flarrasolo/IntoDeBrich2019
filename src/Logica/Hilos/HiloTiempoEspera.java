package Logica.Hilos;

import Logica.LogicaJuego;

/**
 * Clase dedicada a un tiempo de espera antes de finalizar el Juego
 * @author Usuario
 *
 */
public class HiloTiempoEspera extends Thread{
	protected int tiempo;
	protected LogicaJuego miLogica;
	
	public HiloTiempoEspera(LogicaJuego l, int t){
		miLogica=l;
		tiempo = t;
	}
	
	/**
	 * Espera 0,2 segundos para finalizar del todo el juego
	 */
	public void run()
	{
		try{
			
			sleep(1000*tiempo);
			
		}catch(InterruptedException e){ e.printStackTrace();}
		stop();
	}

}
