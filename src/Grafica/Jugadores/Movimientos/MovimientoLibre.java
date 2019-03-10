package Grafica.Jugadores.Movimientos;

import java.util.ArrayList;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class MovimientoLibre extends Movimiento{

	public MovimientoLibre(LogicaJuego l) {
		super(l);
	}

	@Override
	public ArrayList<ComponenteGrafico> getPosiblesMovimientos(int x, int y) {
		
		ArrayList<ComponenteGrafico> posiblesUbicaciones = new ArrayList<ComponenteGrafico> ();
		
		//Recorro la mitad inferior del tablero para recuperar las celdas que no estan ocupadas
		//por algun elemento de Terreno
		for(int i=0;i<8;i++) {
			for(int j=0;j<y;j++) {
				ComponenteGrafico celda =logica.getComponente(i, j); //mapa[i][j];
				if(celda.getPuedoPonerJugador())
					posiblesUbicaciones.add(celda);
			}
		}
		return posiblesUbicaciones;
	}

}
