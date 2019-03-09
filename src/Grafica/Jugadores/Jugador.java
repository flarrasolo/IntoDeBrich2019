package Grafica.Jugadores;

import Grafica.ComponenteGrafico;

public abstract class Jugador extends ComponenteGrafico{
	private static float probabFallo= (float) 0.3;;
	protected int disparosSimultaneos;
	protected int velMovimiento;
	protected int velDisparo;
	protected boolean puedeMover;
	protected int disparosAndando;
	
	//protected Movimiento hiloFluido;
	
	public Jugador(int x,int y){
		super(x,y);		
		disparosAndando=0;
		puedeMover=true;;
		movimientoPosible=true;
		puedoPonerJugador = false;
		vida=10;
	}
	
	public int getVelMovimiento(){
		return velMovimiento;
	}
	
	public int getVelocidadDisparo(){
		return velDisparo;
	}
	
	/**
	 * Crea un disparo hecho por el Jugador, si lo puede crear, empieza a moverlo;
	 * sino, elimina el disparo y con quien coliciono
	 */	
	public boolean movimientoPosible() {
		return false;
	}
	
	public boolean movimientoPosibleEnemigo(){
		return false;
	}
	
	public boolean movimientoPosibleDisparo() {
		return false;
	}
	
	public boolean mejorar() {
		return false;
	}
	
	public boolean getPuedeMover(){
		return puedeMover;
	}
	
	public void reducirDisparoAndando(){
		disparosAndando--;
	}

}
