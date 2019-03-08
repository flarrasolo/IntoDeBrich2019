package Logica.Hilos;

import java.util.ArrayList;
import java.util.Random;

import Grafica.ComponenteGrafico;
import Grafica.Terreno.Piso;
import Logica.LogicaJuego;

public class MovimientoEnemigos extends Movimiento{

	protected ArrayList<ComponenteGrafico> enemigosIngresar;
	protected int direccion;
	protected int accion;
	protected Random rnd;
	protected boolean agregarEnemigo;
	protected ArrayList<ComponenteGrafico> eliminar;

	public MovimientoEnemigos(LogicaJuego l)
	{
		super(l);
		rnd= new Random(); 
		enemigosIngresar=new ArrayList<ComponenteGrafico>();
		agregarEnemigo=true;//lo uso para saber si puede agregar un enemigo a la lista
	}
	
	/**
	 * Maneja los enemigos, decide si disparan o se mueven.
	 * Si se activa el PowerUp Granada, elimino a todos los enemigos.
	 */
	public void run()
	{
		try{
			while(!miLogica.finDelJuego() ){
				if(!miLogica.getDetenerTanque()){
						
					eliminar=new ArrayList<ComponenteGrafico>();
					agregarEnemigo=false;
					for(ComponenteGrafico enemigo: enemigos){
						sleep(40);
						if( !miLogica.eliminarTodosLosEnemigos()){
							if(enemigo.getVida()==0)
								eliminar.add(enemigo);
							else{
								direccion = (int) (rnd.nextInt(4)+1);
								accion= (int) (rnd.nextInt(6));
								if(accion==0||accion==1)
									enemigo.mover(direccion);
								else
									if(accion==2||accion==3)
										enemigo.mover(enemigo.getDireccion());
									else
										if(enemigo.getPuedeMover())
											miLogica.crearDisparoEnemigo(enemigo);
							}
						}else{
							eliminar.add(enemigo);
							miLogica.setComponente(new Piso(enemigo.getPosicionX(), enemigo.getPosicionY(),miLogica));
							miLogica.eliminarGrafico(enemigo);
						}
					}
					agregarEnemigo=true;
					
					for(ComponenteGrafico enemigo: eliminar)
						enemigos.remove(enemigo);
					
					for(ComponenteGrafico bala: enemigosIngresar)
						enemigos.add(bala);
					
					enemigosIngresar=new ArrayList<ComponenteGrafico>();		
						
				}
			}
		}catch(InterruptedException e){ e.printStackTrace();}
		eliminar=new ArrayList<ComponenteGrafico>();
		for(ComponenteGrafico enemigo: enemigos){
			eliminar.add(enemigo);
			miLogica.eliminarGrafico(enemigo);
		}
		for(ComponenteGrafico enemigo: eliminar)
			enemigos.remove(enemigo);
			
		stop();
	}
	
	public void addEnemigo(ComponenteGrafico x) {
		if(agregarEnemigo)
			enemigos.add(x);
		else
			enemigosIngresar.add(x);
	}
}
