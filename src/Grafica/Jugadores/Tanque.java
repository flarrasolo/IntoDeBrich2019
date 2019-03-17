package Grafica.Jugadores;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Movimientos.Movimiento;
import Grafica.Terreno.Terreno;
import Logica.LogicaJuego;

public class Tanque extends Jugador{

	public Tanque(int x, int y, LogicaJuego l,Movimiento movimiento, Movimiento ataque) {
		super(x,y,l,movimiento,ataque);
		
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/tanque.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		
		setDa�o(1);
		setDa�oEdificios(0);
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
		this.setDibujo("/Imagenes/tanqueResaltado.png");
	}

	@Override
	public void setImagenNormal() {
		this.setDibujo("/Imagenes/tanque.png");
	}
	
	@Override
	public String imprimirme() {
		return "T";
	}
}
