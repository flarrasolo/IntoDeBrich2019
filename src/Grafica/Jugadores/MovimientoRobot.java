package Grafica.Jugadores;

import java.util.ArrayList;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class MovimientoRobot extends Movimiento{
		
	public MovimientoRobot(LogicaJuego l) {
		super(l);
	}
	
	@Override
	public ArrayList<ComponenteGrafico> getPosiblesMovimientos(int x,int y) {
		posiblesMov = getCeldasRadio(logica.getComponente(x, y),3);
		
		return posiblesMov;
	}

}
