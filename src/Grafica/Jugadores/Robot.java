package Grafica.Jugadores;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Movimientos.Movimiento;
import Grafica.Terreno.Terreno;
import Logica.LogicaJuego;

public class Robot extends Jugador{
	
	public Robot(int x, int y, LogicaJuego l,Movimiento movimiento, Movimiento ataque) {
		super(x,y,l,movimiento,ataque);
		profundidad=2;
		
		setDibujo("/Imagenes/robot.png");
		puedoPonerJugador = false;
		daño = 2;
		dañoEdificios = 0;
	}

	@Override
	public void atacar(ComponenteGrafico celda) {
		celda.recibirAtaque(this);
		
	}

	@Override
	public void morir() {
		logica.murioUsuario(this);
	}

	@Override
	public void setImagenResaltada() {
		this.setDibujo("/Imagenes/robotResaltado.png");
	}

	@Override
	public void setImagenNormal() {
		this.setDibujo("/Imagenes/robot.png");
	}
	
	@Override
	public String imprimirme() {
		return "R";
	}

}
