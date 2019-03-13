package Grafica.Jugadores;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Movimientos.Movimiento;
import Grafica.Terreno.Terreno;
import Logica.LogicaJuego;

public abstract class Jugador extends ComponenteGrafico{
	
	protected int daño,dañoEdificios;
	protected Movimiento miMovimiento;
	protected Movimiento miAtaque;
	protected int disparosSimultaneos;
	protected int velMovimiento;
	protected int velDisparo;
	
	public Jugador(int x,int y,LogicaJuego l,Movimiento miMov,Movimiento miAtq){
		super(x,y,l);
		
		puedoPonerJugador = false;
		energia=10;
		
		miMovimiento = miMov;
		miAtaque = miAtq;
	}
	
	public abstract void atacar(ComponenteGrafico celda);
	
	public abstract void setImagenResaltada();
	
	public abstract void setImagenNormal();
	
	public Movimiento getMiMovimiento() {
		return miMovimiento;
	}

	public Movimiento getMiAtaque() {
		return miAtaque;
	}

	public int getDaño() {
		return daño;
	}

	public void setDaño(int daño) {
		this.daño = daño;
	}

	public int getDañoEdificios() {
		return dañoEdificios;
	}

	public void setDañoEdificios(int dañoEdificios) {
		this.dañoEdificios = dañoEdificios;
	}
	
	@Override
	public boolean recibirAtaque(Jugador j) { 
		energia-=j.getDaño();  
		if(energia <= 0)
			morir();
		return true;
	}
	

}
