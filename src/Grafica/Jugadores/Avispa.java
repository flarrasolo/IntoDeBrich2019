package Grafica.Jugadores;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Movimientos.Movimiento;
import Grafica.Terreno.Terreno;
import Logica.LogicaJuego;

public class Avispa extends Jugador{
	
	public Avispa(int x, int y, LogicaJuego l,Movimiento movimiento, Movimiento ataque) {
		super(x,y,l,movimiento,ataque);
		
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/avispa.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		
		setDa�o(2);
		setDa�oEdificios(3);
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
		this.setDibujo("/Imagenes/avispaResaltada.png");
	}

	@Override
	public void setImagenNormal() {
		this.setDibujo("/Imagenes/avispa.png");
	}

	@Override
	public String imprimirme() {
		return "A";
	}
}
