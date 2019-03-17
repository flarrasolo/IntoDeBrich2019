package Grafica.Terreno;

import Grafica.ComponenteGrafico;
import Logica.LogicaJuego;


public abstract class Terreno extends ComponenteGrafico{
	public Terreno(int x,int y,LogicaJuego l){	
		super(x,y,l);	
		logica=l;
	}
	
}
