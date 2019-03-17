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
		
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/robot.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);

		setDaño(2);
		setDañoEdificios(0);
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
