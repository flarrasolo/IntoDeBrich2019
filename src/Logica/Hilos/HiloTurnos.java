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
		/*
		while(!miLogica.finDelJuego()) {
			
			//miLogica.actualizarPanel();
			miLogica.actualizarPanel();
			
			//TURNO DE LA COMPUTADORA
			if(turnoComputadora) {
				HiloTiempoEspera espera = new HiloTiempoEspera(miLogica,1);
				espera.run();
				//Corro el indice de la lista de Enemigos al que le toca
				proximoJugadorComputadora = proximoJugador(proximoJugadorComputadora,enemigos.size());
				//Obtengo el jugador (Avispa o uno de los Escarabajos)
				jugadorDeTurno = enemigos.get(proximoJugadorComputadora);
					
				while(!atacoCPU) {
					//Si puede atacar, ataca. Sino intenta mover a posicion de ataque
					ArrayList<ComponenteGrafico> ataquesPosibles = this.inteligenciaAtaqueEnemigos();
						if(!ataquesPosibles.isEmpty()) {
							i = r.nextInt(ataquesPosibles.size());
							ComponenteGrafico celdaAtaque = ataquesPosibles.get(i);
							jugadorDeTurno.atacar(celdaAtaque);
							System.out.println("Ataque CPU");
							atacoCPU = true;
						}
						else { //mueve
							ArrayList<ComponenteGrafico> movimientosPosibles = this.inteligenciaMovimientoEnemigos();
							if(!movimientosPosibles.isEmpty()) {
								i = r.nextInt(movimientosPosibles.size());
								ComponenteGrafico celdaDestino = movimientosPosibles.get(i);
								this.moverJugador(celdaDestino);
								System.out.println("Movio CPU");
							}
						}
				}
				//Fin del turno de la Computadora
				atacoCPU = false;
				grafica.reestablecerBotones();
				grafica.setMsjUsuario("La Computadora finalizó su turno. Ahora es turno del Usuario");
			}
			else //TURNO DEL USUARIO
			{	
				//Corro el indice de la lista de Jugadores al que le toca
				proximoJugadorUsuario = proximoJugador(proximoJugadorUsuario,jugadoresUsuario.size());
				//Obtengo el jugador (Tanque o Robot)
				jugadorDeTurno = jugadoresUsuario.get(proximoJugadorUsuario);
				
				
				
				//Fin del turno del Usuario
				atacar = mover = false;
			}
			
			//Si acaba de terminar el turno de la computadora, le toca al Usuario. Sino al revés
			if(turnoComputadora) 
				turnoComputadora = false;
			else
				turnoComputadora = true;
		
			miLogica.actualizarPanel();
		*/
		}
		
		
		
		
		
		
		//POSIBLE ALGORITMO?
		
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
