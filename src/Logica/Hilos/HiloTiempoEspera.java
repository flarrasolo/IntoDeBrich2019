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
	
	public	HiloTiempoEspera(int i){
		try {
			sleep(i);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public HiloTiempoEspera(LogicaJuego l){
		miLogica=l;
	}
	
	/**
	 * Espera 0,2 segundos para finalizar del todo el juego
	 */
	public void run()
	{
		try{
			sleep(200);
			miLogica.finalizar();
		}catch(InterruptedException e){ e.printStackTrace();}
		stop();
	}

}
