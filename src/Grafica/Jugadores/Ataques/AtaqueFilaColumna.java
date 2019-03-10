package Grafica.Jugadores.Ataques;

import java.util.ArrayList;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Movimientos.Movimiento;
import Logica.LogicaJuego;

public class AtaqueFilaColumna extends Movimiento{

	public AtaqueFilaColumna(LogicaJuego l) {
		super(l);
	}

	@Override
	public ArrayList<ComponenteGrafico> getPosiblesMovimientos(int x, int y) {
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

}
