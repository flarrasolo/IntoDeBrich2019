package Grafica.Jugadores.Movimientos;

import java.util.ArrayList;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;

public class MovimientoTanque extends Movimiento{

	public MovimientoTanque(LogicaJuego l) {
		super(l);
	}

	@Override
	public ArrayList<ComponenteGrafico> getPosiblesMovimientos(int x, int y) {
		//5 celdas en total
		return null;
	}

}
