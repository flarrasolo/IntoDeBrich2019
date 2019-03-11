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
		
		puedoPonerJugador = false;
		energia=10;
		
		miMovimiento = miMov;
		miAtaque = miAtq;
	}
	
	public abstract void atacar(ComponenteGrafico celda);
	
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
	
	/**
	 * Retorna true si el Jugador recibio el da�o
	 */
	@Override
	public boolean recibirAtaque(Jugador j) { 
		energia-=j.getDa�o();  
		if(energia == 0)
			morir();
		return true;
	}
	

}
