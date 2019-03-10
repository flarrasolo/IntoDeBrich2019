package Grafica.Jugadores.Ataques;

import java.util.ArrayList;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Movimientos.Movimiento;
import Logica.LogicaJuego;

public class AtaqueAdyacentes extends Movimiento{

	public AtaqueAdyacentes(LogicaJuego l) {
		super(l);
	}

	@Override
	public ArrayList<ComponenteGrafico> getPosiblesMovimientos(int x, int y) {
		//int x = c.getPosicionX();
		//int y = c.getPosicionY();
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
	
}
