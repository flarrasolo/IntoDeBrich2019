package Grafica.Jugadores;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Movimientos.Movimiento;
import Grafica.Terreno.Terreno;
import Logica.LogicaJuego;

public class Escarabajo extends Jugador{
	
	public Escarabajo(int x, int y, LogicaJuego l,Movimiento movimiento, Movimiento ataque) {
		super(x,y,l,movimiento,ataque);
		profundidad=2;
		
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/escarabajo.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		energia = 10;
		setDaño(2);
		setDañoEdificios(3);
	}

	@Override
	public void atacar(ComponenteGrafico celda) {
		celda.recibirAtaque(this);
		
	}

	@Override
	public void morir() {
		logica.murioCPU(this);
	}
	
	@Override
	public void setImagenResaltada() {
		this.setDibujo("/Imagenes/escarabajoResaltado.png");
	}

	@Override
	public void setImagenNormal() {
		this.setDibujo("/Imagenes/escarabajo.png");
	}

}
