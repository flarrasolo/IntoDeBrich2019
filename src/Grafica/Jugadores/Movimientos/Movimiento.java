package Grafica.Jugadores.Movimientos;

import java.util.ArrayList;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;


public abstract class Movimiento {
	protected ArrayList<ComponenteGrafico> posiblesMov;
	protected LogicaJuego logica;
	
	public Movimiento(LogicaJuego l) {
		logica = l;
		posiblesMov = new ArrayList<ComponenteGrafico> ();
	}
	/**
	 * Devuelve una lista de celdas con los posibles movimientos del Jugador
	 * @return
	 */
	public abstract ArrayList<ComponenteGrafico> getPosiblesMovimientos(int x,int y);
	/*
	protected ArrayList<ComponenteGrafico> getCeldasAdyacentes(ComponenteGrafico c) {
		int x = c.getPosicionX();
		int y = c.getPosicionY();
		ArrayList<ComponenteGrafico> listaAdy = new ArrayList<ComponenteGrafico> ();
		
		//Casos especiales: Esquinas( (0,0);(0,7);(7,0);(7,7) ) 
		if (x==0 && y == 0){//Esquina superior izquierda
	        listaAdy.add(logica.getComponente(0,1));
	        listaAdy.add(logica.getComponente(1,0));
	    }
	    else 
	    	if (x==0 && y==7){//Esquina superior derecha
	        listaAdy.add(logica.getComponente(0,6));
	        listaAdy.add(logica.getComponente(1,7));
	    	}
	    	else    
	    		if (x==7 && y==0){//Esquina inferior izquierda
	    		listaAdy.add(logica.getComponente(6,0));
	    		listaAdy.add(logica.getComponente(7,1));
	    		}
	    		else    
	    			if (x==7 && y==7){//Esquina inferior derecha
	    				listaAdy.add(logica.getComponente(6,7));
	    				listaAdy.add(logica.getComponente(7,6));
	    			}
	    			else {//Casos Especiales: Bordes( (0,*);(*;0);(7,*);(*,7) )
	    				if (x==0 && y!=0 && y!=7){//Borde Superior
	    			        listaAdy.add(logica.getComponente(0,y-1));
	    			        listaAdy.add(logica.getComponente(0,y+1));
	    			        listaAdy.add(logica.getComponente(1,y));
	    			    }
	    			    else 
	    			    	if (x==7 && y!=0 && y!=7){//Borde Inferior
	    			        listaAdy.add(logica.getComponente(7,y+1));
	    			        listaAdy.add(logica.getComponente(7,y-1));
	    			        listaAdy.add(logica.getComponente(6,y));
	    			    	}
	    			    	else    
	    			    		if (y==0 && x!=0 && x!=7){//Borde Izquierdo
	    			    		listaAdy.add(logica.getComponente(x-1,0));
	    			    		listaAdy.add(logica.getComponente(x+1,0));
		    			        listaAdy.add(logica.getComponente(x,1));
	    			    		}
	    			    		else    
	    			    			if (y==7 && x!=0 && x!=7){//Borde Derecho
	    			    				listaAdy.add(logica.getComponente(x-1,7));
	    			    				listaAdy.add(logica.getComponente(x+1,7));
	    		    			        listaAdy.add(logica.getComponente(x,6));
	    			    			}
	    			    			else {//Adyacentes sin conflicto
	    			    				listaAdy.add(logica.getComponente(x-1,y));
	    			    				listaAdy.add(logica.getComponente(x,y-1));
	    		    			        listaAdy.add(logica.getComponente(x,y+1));
	    		    			        listaAdy.add(logica.getComponente(x+1,y));
	    			    			}
	    			}
		
		return listaAdy;
	}
	
	protected ArrayList<ComponenteGrafico> getCeldasRadio(ComponenteGrafico c,int radio) {
		int x = c.getPosicionX();
		int y = c.getPosicionY();
		ArrayList<ComponenteGrafico> listaRadio = new ArrayList<ComponenteGrafico> ();
	
		if(radio>1) {
			int k = 0;
			
			for(int i = radio; i >= 0; i--) {
				for(int j = k; j >= 0; j--) {
					// Pregunto para no agregar la misma posicion donde estoy parado
					if( ! ((i==j) && (i == 0)) ) { 
						if( (x+i) < 8 ) {
							if (( y + j) < 8 )
								//System.out.println("Agrego posición: (" + (x+i) + "," + (y+j) + ")");
								listaRadio.add(logica.getComponente(x+i,y+j));
							// j != 0 para no agregar 2 veces la posicion
							if( ( y - j) > -1 && j != 0) {
								//System.out.println("Agrego posición: (" + (x+i) + "," + (y-j) + ")");
								listaRadio.add(logica.getComponente(x+i,y-j));
							}
						}
						// i != 0 para no agregar dos veces
						if( ( (x-i) > -1 ) && (i != 0) ) { 
							if (( y + j) < 8 )
								//System.out.println("Agrego posición: (" + (x-i) + "," + (y+j) + ")");
								listaRadio.add(logica.getComponente(x-i,y+j));
							// j != 0 para no agregar 2 veces la posicion
							if( ( y - j) > -1 && j != 0) { 
								//System.out.println("Agrego posición: (" + (x-i) + "," + (y-j) + ")");
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
	
	protected ArrayList<ComponenteGrafico> getCeldasMismaFilaYColumna(ComponenteGrafico c) {
		int x = c.getPosicionX();
		int y = c.getPosicionY();
		ArrayList<ComponenteGrafico> lista = new ArrayList<ComponenteGrafico> ();
			
			for(int i=0;i<8;i++) {
				//Si no coincide con mi componente guardo elemento de fila
				if(i!=y)
					lista.add(logica.getComponente(x,i));
				//Si no coincide con mi componente guardo elemento de columna
				if(i!=x)
					lista.add(logica.getComponente(i,y));
			}

		return lista;
	}
	*/
}
