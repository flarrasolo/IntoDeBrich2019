package Logica.Hilos;

import java.util.ArrayList;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.*;
import Logica.LogicaJuego;

public class MovimientoBalas extends Movimiento{
	
	protected ArrayList<ComponenteGrafico> balasIngresar;
	protected boolean agregarBala;
	protected ArrayList<ComponenteGrafico> eliminar;
	
	public MovimientoBalas(LogicaJuego l){
		super(l);
		balasIngresar=new ArrayList<ComponenteGrafico>();
		agregarBala=true;//lo uso para saber si puede agregar una bala a la lista
	}
	/**
	 * Se encarga de mover todas las balas del juego
	 */
	public void run()
	{	
		while(!miLogica.finDelJuego()){
				eliminar=new ArrayList<ComponenteGrafico>();
				agregarBala=false;
				try {
					sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(ComponenteGrafico bala: balas){	
					if(bala.getVida()!=0)
						bala.mover(bala.getDireccion());
					else
						eliminar.add(bala);
				}
				agregarBala=true;
				
				for(ComponenteGrafico bala: eliminar)
					balas.remove(bala);
				
				for(ComponenteGrafico bala: balasIngresar)
					balas.add(bala);
				
				balasIngresar=new ArrayList<ComponenteGrafico>();
			}
	}

	public void addBala(ComponenteGrafico x) {
		if(agregarBala)
			balas.add(x);
		else
			balasIngresar.add(x);
	}
	
}