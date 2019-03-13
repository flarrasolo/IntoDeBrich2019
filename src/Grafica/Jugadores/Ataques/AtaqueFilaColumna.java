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
		
		for(int i=0;i<8;i++)
			//Si no coincide con mi componente guardo toda la fila
			if(i!=y)
					lista.add(logica.getComponente(x,i));
		
		for(int j=0;j<8;j++)
			//Si no coincide con mi componente guardo toda la columna
			if(j!=x)
					lista.add(logica.getComponente(j,y));
		

	return lista;
	}

}
