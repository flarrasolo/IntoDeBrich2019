package Grafica.Jugadores;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Movimientos.Movimiento;
import Grafica.Terreno.Terreno;

public abstract class Jugador extends ComponenteGrafico{
	
	protected int da�o,da�oEdificios;
	protected Movimiento miMovimiento;
	protected Movimiento miAtaque;
	protected int disparosSimultaneos;
	protected int velMovimiento;
	protected int velDisparo;
	
	public Jugador(int x,int y,Movimiento miMov,Movimiento miAtq){
		super(x,y);		
		movimientoPosible=true;
		puedoPonerJugador = false;
		energia=10;
		
		miMovimiento = miMov;
		miAtaque = miAtq;
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
	
	public abstract void atacarTerreno(Terreno t);
	
	public abstract void atacarJugador(Jugador j);

	public Movimiento getMiMovimiento() {
		return miMovimiento;
	}

	public Movimiento getMiAtaque() {
		return miAtaque;
	}

	public int getDa�o() {
		return da�o;
	}

	public void setDa�o(int da�o) {
		this.da�o = da�o;
	}

	public int getDa�oEdificios() {
		return da�oEdificios;
	}

	public void setDa�oEdificios(int da�oEdificios) {
		this.da�oEdificios = da�oEdificios;
	}
	

}
