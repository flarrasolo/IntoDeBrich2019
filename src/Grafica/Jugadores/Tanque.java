package Grafica.Jugadores;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Movimientos.Movimiento;
import Grafica.Terreno.Terreno;
import Logica.LogicaJuego;

public class Tanque extends Jugador{
	private int movimientosRestantes;
	public Tanque(int x, int y, LogicaJuego l,Movimiento movimiento, Movimiento ataque) {
		super(x,y,movimiento,ataque);
		profundidad=2;
		movimientosRestantes = 5;
		ImageIcon fot = new ImageIcon(getClass().getResource("/Imagenes/tanque.png"));
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		this.setIcon(icono);
	}

	public int getMovimientosRestantes() {
		return movimientosRestantes;
	}
	
	public void setMovimientosRestantes(int mov) {
		movimientosRestantes = mov;
	}


	@Override
	public void atacarTerreno(Terreno t) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void atacarJugador(Jugador j) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void recibirAtaque(Jugador j) {
		// TODO Auto-generated method stub
		
	}

}
