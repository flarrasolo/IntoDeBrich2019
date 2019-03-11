package Logica.Hilos;

import java.util.ArrayList;

import Grafica.ComponenteGrafico;
import Grafica.Jugadores.Jugador;
import Logica.LogicaJuego;

public class HiloTurnos extends Thread{
	
	protected LogicaJuego miLogica;
	protected Jugador jugadorDeTurno;
	protected ArrayList<Jugador> jugadoresUsuario, enemigos;
	
	protected boolean mover, atacar, turnoJugador, turnoComputadora;
	int proximoJugadorUsuario, proximoJugadorComputadora;
	
	
	public HiloTurnos(LogicaJuego logic,ArrayList<Jugador> jugadores,ArrayList<Jugador> enemigos)
	{
		miLogica = logic;
		
		jugadoresUsuario = jugadores;
		this.enemigos = enemigos;
		
		proximoJugadorUsuario = 0;
		proximoJugadorComputadora = 0;
		
		turnoJugador = false;
		turnoComputadora = true;
		mover = false;
		atacar = false;
		
		jugadorDeTurno = enemigos.get(proximoJugadorComputadora);
	}
	
	/**
	 * Corre un turno de jugador o computadora mientras no sea fin del juego
	 */
	public void run() {
		
		while(!miLogica.finDelJuego()) {
			
			if(turnoComputadora) {
				
			}
			else //Turno Jugador 
			{
				
			}
			
		//ResaltarJugador?
		//Elegir MoverAtacar (Botón en GUI->settear atributo acá en base a lo elegido)
		//DeshabilitarBotones
		//jugadorDeTurno.getMiAtaque o jugadorDeTurno.getMiMovimiento -> getPosiblesMovimientos
		//Si es turnoUsuario esperar Click en proxima celda -> getCelda donde hizo click
			//Si la celda esta en getPosiblesMovimientos
				//Si atacar = true -> atacarCelda
				//Sino -> moverCelda
					//Habilitar boton atacar
		//Si es turnoComputadora
			//Si es posible atacarCercano -> atacar
			//Sino 
				//mover
				//atacarCercano
		
		//Fin del turno.
		
		
		
		}
		
	}
}
