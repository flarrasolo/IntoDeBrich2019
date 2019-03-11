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
		super(x,y,movimiento,ataque);
		profundidad=2;
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/robot.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
		energia = 10;
		setDaño(2);
		setDañoEdificios(0);
	}

	@Override
	public void atacarTerreno(Terreno t) {}

	@Override
	public void atacarJugador(Jugador j) {
		j.recibirAtaque(this);
	}

	@Override
	public void recibirAtaque(Jugador j) {
		energia-=j.getDaño();
	}

}
