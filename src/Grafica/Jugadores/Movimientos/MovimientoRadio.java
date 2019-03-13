package Grafica.Jugadores.Movimientos;

import java.util.ArrayList;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class MovimientoRadio extends Movimiento{

	private int radio;
	
	public MovimientoRadio(LogicaJuego l,int r) {
		super(l);
		radio = r;
	}

	@Override
	public ArrayList<ComponenteGrafico> getPosiblesMovimientos(int x, int y) {

		ArrayList<ComponenteGrafico> listaRadio = new ArrayList<ComponenteGrafico> ();
	
		if(radio>1) {
			int k = 0;
			
			for(int i=radio; i>=0; i--) {
				for(int j=k; j>=0; j--) {
					//Pregunto para no agregar la misma posicion donde estoy parado
					if(!((i==j) && (i==0))) { 
						if( (x+i)<8 ) {
							if ((y+j)<8 )
								//System.out.println("Agrego posición: (" + (x+i) + "," + (y+j) + ")");
								if(logica.getComponente(x+i,y+j).getPuedoPonerJugador())
									listaRadio.add(logica.getComponente(x+i,y+j));
							//j!=0 para no agregar 2 veces la posicion
							if( ((y-j)>-1) && j!=0) {
								//System.out.println("Agrego posición: (" + (x+i) + "," + (y-j) + ")");
								if(logica.getComponente(x+i,y-j).getPuedoPonerJugador())
									listaRadio.add(logica.getComponente(x+i,y-j));
							}
						}
						//i!=0 para no agregar dos veces
						if(((x-i)>-1) && (i!=0) ) { 
							if ((y+j)<8)
								//System.out.println("Agrego posición: (" + (x-i) + "," + (y+j) + ")");
								if(logica.getComponente(x-i,y+j).getPuedoPonerJugador())
									listaRadio.add(logica.getComponente(x-i,y+j));
							//j!=0 para no agregar 2 veces la posicion
							if((y-j)>-1 && j!=0) { 
								//System.out.println("Agrego posición: (" + (x-i) + "," + (y-j) + ")");
								if(logica.getComponente(x-i,y-j).getPuedoPonerJugador())
									listaRadio.add(logica.getComponente(x-i,y-j));
							}
						}
					}
				}
				k++;
			}
		}
			
		return listaRadio;
	}

}
