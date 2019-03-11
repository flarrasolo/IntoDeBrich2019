package Logica;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import Grafica.ComponenteGrafico;
import Grafica.GUI;
import Grafica.Jugadores.*;
import Grafica.Jugadores.Ataques.*;
import Grafica.Jugadores.Movimientos.*;
import Grafica.Terreno.*;
import Logica.Hilos.HiloTiempoEspera;
import Logica.Hilos.HiloTurnos;


public class LogicaJuego {
	
	/*Variables*/
	
	protected ComponenteGrafico[][] mapa;
	protected ArrayList<Jugador> jugadoresUsuario;
	protected ArrayList<Jugador> enemigos;
	protected Jugador jugadorDeTurno;
	
	private Movimiento hiloEnemigos;
	private HiloTurnos manejoTurnos;
	private HiloTiempoEspera tiempoEsperaParaFinalizar;
	
	private int enemigosMatados;
	private int	edificiosDestruidos;
	private int usuariosMuertos;
	private int muertesAcumuladas;
	protected int proximoJugadorUsuario, proximoJugadorComputadora;
	
	private ArrayList<ComponenteGrafico> edificios;
	private boolean termina,porQueTermina, ataca, mueve, eliminarEnemigo,turnoComputadora;
	protected GUI grafica;
	
	//Constructor
	public LogicaJuego(GUI interfaz) {
		
		termina=false;
		ataca = false;
		mueve = false;
		turnoComputadora = true;
		eliminarEnemigo = false;
		
		proximoJugadorUsuario = -1;
		proximoJugadorComputadora = -1;
		
		muertesAcumuladas = 0; //al llegar a 3, fin del juego con victoria
		edificiosDestruidos = 0;
		usuariosMuertos = 0;
		
		grafica=interfaz;

		mapa = new ComponenteGrafico[8][8];
		enemigos = new ArrayList<Jugador>();
		jugadoresUsuario = new ArrayList<Jugador>();
		edificios = new ArrayList<ComponenteGrafico> ();
		
		
		//Creacion del mapa
		generacionDeMapaLogico();
		crearYUbicarEnemigos();
		resaltarLugaresPosibles();
		agregarOyentesMouseInicio();

		//jugar();
		//Maneja los turnos
		//manejoTurnos = new Hiloturnos(this); 
		//hiloTurnos.start();		
		
	}

	/* ----------------------------------------------------Mapa--------------------------------------------------*/
	
	/**
	 * Permite ver el mapa logico
	 * @return Una matriz que represanta al mapa logico
	 */
	public	ComponenteGrafico[][] getMapaLogico(){
		return mapa;
	}
	
	/**
	 * Genera una matriz interna en Logica distribuyendo exactamente 8 edificios, 
	 * entre 8 y 10 montañas, entre 7 y 10 celdas de agua y entre 4 y 6 celdas de tierra al azar
	 */
	public void generacionDeMapaLogico(){
		Random aleatorio = new Random();

		int cantEdificios = 8;
		int cantMontanias =	8 + aleatorio.nextInt(3);
		int cantAgua = 7 + aleatorio.nextInt(4);
		int cantTierra = 4 + aleatorio.nextInt(3);
		
		for(int c=0;c<cantEdificios;c++) {
			int x = aleatorio.nextInt(8);
			int y = aleatorio.nextInt(8);
			
			if(mapa[y][x]==null) {
				mapa[y][x] = new Edificio(x,y,this);
				edificios.add(this.getComponente(x, y));
			}
		}
		
		for(int c=0;c<cantMontanias;c++) {
			int x = aleatorio.nextInt(8);
			int y = aleatorio.nextInt(8);
			
			if(mapa[y][x]==null)
				mapa[y][x] = new Montaña(x,y,this);
		}
		
		for(int c=0;c<cantAgua;c++) {
			int x = aleatorio.nextInt(8);
			int y = aleatorio.nextInt(8);
			
			if(mapa[y][x]==null)
				mapa[y][x] = new Agua(x,y,this);
		}
		
		for(int c=0;c<cantTierra;c++) {
			int x = aleatorio.nextInt(8);
			int y = aleatorio.nextInt(8);
			
			if(mapa[y][x]==null)
				mapa[y][x] = new Tierra(x,y,this);
		}
		
		for(int i=0;i<8;i++)
		 	for(int j=0;j<8;j++)
		 		if(mapa[j][i]==null)
					mapa[j][i] = new Piso(i,j,this);
			
		
	}
	
	private void agregarOyentesMouseInicio() {
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++)
				mapa[j][i].addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						ComponenteGrafico comp =(ComponenteGrafico) e.getSource();
						int ingX = comp.getX()/60;
						int ingY = comp.getY()/60;
						
							//Controlar click en la mitad superior
							if(ingY <= 3 && comp.getPuedoPonerJugador()) {
								//Si todavia no agregue el Tanque
								if(jugadoresUsuario.size()==0) {
									crearTanque(ingX,ingY);
									grafica.setMsjUsuario("Seleccione la ubicación donde desea ubicar el Robot");
								}
								else {
									crearRobot(ingX,ingY);
									grafica.setMsjUsuario("Turno de la Computadora");
									comenzarJuego();
								}
							}
					}

					@Override
					public void mousePressed(MouseEvent e) {}

					@Override
					public void mouseReleased(MouseEvent e) {}

					@Override
					public void mouseEntered(MouseEvent e) {}

					@Override
					public void mouseExited(MouseEvent e) {}
					
				}); 
	}
	
	private void comenzarJuego() {
		devolverPisoNormal();
		grafica.repintarPanel();
		//repintarPanel();
		
		//Toda celda es "clickeable"
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++)
				agregarOyenteMouseTurnos(i,j);
	}
	/*
	private void eliminarOyentesInicio() {
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++)
				mapa[j][i].removeMouseListener(mapa[j][i].getMouseListeners()[0]);
	} 
	*/
	
	private void agregarOyenteMouseTurnos(int i, int j) {
		
				mapa[j][i].addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {
						ComponenteGrafico comp =(ComponenteGrafico) e.getSource();
						//int ingX = comp.getX()/60;
						//int ingY = comp.getY()/60;

						if(ataca)
							atacar(comp);
						else
							if(mueve)
								mover(comp);
					}

					@Override
					public void mousePressed(MouseEvent e) {}

					@Override
					public void mouseReleased(MouseEvent e) {}

					@Override
					public void mouseEntered(MouseEvent e) {}

					@Override
					public void mouseExited(MouseEvent e) {}
					
				}); 
	}
	
	/**
	 * Obtengo el componente en las coordenadas (x,y) en el mapa
	 * @param x coordenada en el eje x
	 * @param y coordenada en el eje y
	 * @return Componente en la posicion indicada
	 */
	public ComponenteGrafico getComponente(int x,int y){
		ComponenteGrafico aux;
		if ((x<0 || x>8) || (y<0 || y>8))
			aux=null;
		else
			aux=mapa[y][x];
		return aux;
	}
			
	/**
	 * Ingresa un componente en las coordeandas (x,y)
	 * @param x coordenada en el eje x
	 * @param y coordenada en el eje y
	 * @param p componente a ingresar
	 */
	public void setComponente(ComponenteGrafico c){
		int x=c.getPosicionX();
		int y=c.getPosicionY();
		mapa[y][x]=c;
	}
	
	/**
	 * Elimina el componente de la grafica
	 * @param x Componente a eliminar
	 */
	public void eliminarGrafico(ComponenteGrafico x){
		grafica.eliminarGrafico(x);
	}
	
	/**
	 * Agrega el componente a la grafica
	 * @param x Componente a agregar
	 */
	public void agregarGrafico(ComponenteGrafico x){
		grafica.agregarGrafico(x);
	}

	
	//---------------------------------Jugabilidad----------------------------------------------
	/**
	 * Indica si termino el juego para frenar los hilos
	 * @return True si termino el juego, False en caso contrario
	 */
	public boolean finDelJuego()
	{
		return termina;
	}
	
	/**
	 * Indica que termino el juego y espera medio segundo para que se eliminen 
	 * todos los disparos y enemigos para finalizar todo.
	 * @param x indica si se gano (true) o perdio (false)
	 */
	public void finalizarJuego(boolean x){
		termina=true; 
		porQueTermina=x;
		
		//Matar Hilo de Turnos
		//manejoTurnos.stop();
		//tiempoEsperaParaFinalizar=new HiloTiempoEspera(this);
		//tiempoEsperaParaFinalizar.start();
		
	}
	
	/**
	 * Con todos los enemigos y disparos eliminados, muestro la pantalla de 
	 * fin de juego
	 */
	public void finalizar(){
		grafica.terminarJuego(porQueTermina);
	}
	
	/**
	 * Elimino el componente en las coordenadas (x,y) que fue colicionado, si es el
	 * jugador o el aguila, el eliminado, finalizo el juego.
	 * @param x coordenada x
	 * @param y coordenada y
	 * @param deQuienEs indica si disparo enemigo (0) o jugador (1)
	 */
	public void eliminarColicion(int x,int y,Jugador Ejecutor){
		getComponente(x, y).recibirAtaque(Ejecutor);
		if(getComponente(x, y).getEnergia()==0){
			eliminarGrafico(getComponente(x, y));
			mapa[y][x]=new Piso(x,y,this);
			agregarGrafico(getComponente(x, y));
		}
	}
	
	/**
	 * Ingresa los componentes del mapa en la parte grafica
	 */
	public void generarPanel(){
		for(int i=0;i<8;i++)
		 	for(int j=0;j<8;j++)
		 		agregarGrafico(getComponente(i,j));		 	
	}	
	
	public void actualizarPanel(){
		grafica.repintarPanel();
	}
	
	/**
	 * Crea tres enemigos, dos escarabajos y una avispa y los ubica donde desea posible, 
	 * en la mitad inferior del tablero
	 */
	private void crearYUbicarEnemigos(){
		ArrayList<ComponenteGrafico> posiblesUbicaciones = getPosiblesUbicaciones(5,8,8);
 
		Random r = new Random();
		for(int i=0;i<2;i++) {
			int celdaAleatoria = r.nextInt(posiblesUbicaciones.size());
			ComponenteGrafico ubicacion = posiblesUbicaciones.get(celdaAleatoria);
			Jugador e1 = new Escarabajo(ubicacion.getPosicionX(),ubicacion.getPosicionY(),this,new MovimientoRadio(this,4),new AtaqueAdyacentes(this));
			mapa[ubicacion.getPosicionY()][ubicacion.getPosicionX()]=e1;
			enemigos.add(e1);
			posiblesUbicaciones.remove(celdaAleatoria);
		}
		
		int celdaAleatoria = r.nextInt(posiblesUbicaciones.size());
		ComponenteGrafico ubicacion = posiblesUbicaciones.get(celdaAleatoria);
		Jugador a1 = new Avispa(ubicacion.getPosicionX(),ubicacion.getPosicionY(),this,new MovimientoLibre(this),new AtaqueAdyacentes(this));
		mapa[ubicacion.getPosicionY()][ubicacion.getPosicionX()]=a1;
		enemigos.add(a1);
		posiblesUbicaciones.remove(celdaAleatoria);
		
	}
	
	private ArrayList<ComponenteGrafico> getPosiblesUbicaciones(int x,int topeI,int y){
		ArrayList<ComponenteGrafico> posiblesUbicaciones = new ArrayList<ComponenteGrafico> ();
		
		//Recorro la mitad inferior del tablero para recuperar las celdas que no estan ocupadas
		//por algun elemento de Terreno
		for(int i=x;i<topeI;i++) {
			for(int j=0;j<y;j++) {
				ComponenteGrafico celda =mapa[i][j];
				if(celda.getPuedoPonerJugador())
					posiblesUbicaciones.add(celda);
			}
		}
		return posiblesUbicaciones;
	}
	
	private void resaltarLugaresPosibles() {
		ArrayList<ComponenteGrafico> posiblesUbicaciones = getPosiblesUbicaciones(0,4,8);
		/*
			for(int x=0;x<posiblesUbicaciones.size();x++) {
				ComponenteGrafico celda = posiblesUbicaciones.get(x);
				*/
				//setComponente(new PisoResaltado(celda.getPosicionX(),celda.getPosicionY(),this));
			for(ComponenteGrafico celda : posiblesUbicaciones)
				celda.setImagen("/Imagenes/fondoResaltado.png");
			//}
	}
	
	private void devolverPisoNormal() {
		/*
		ArrayList<ComponenteGrafico> posiblesUbicaciones = getPosiblesUbicaciones(0,4,8);
		for(int x=0;x<posiblesUbicaciones.size();x++) {
			ComponenteGrafico celda = posiblesUbicaciones.get(x);
			setComponente(new Piso(celda.getPosicionX(),celda.getPosicionY(),this));
		}
		*/
		ArrayList<ComponenteGrafico> posiblesUbicaciones = getPosiblesUbicaciones(0,4,8);
		/*
		for(int x=0;x<posiblesUbicaciones.size();x++) {
			ComponenteGrafico celda = posiblesUbicaciones.get(x);
			*/
			for(ComponenteGrafico celda : posiblesUbicaciones)
				celda.setImagen("/Imagenes/fondo.png");
		//}
		
	}
	
	private void atacar(ComponenteGrafico celda) {
		Movimiento ataque = jugadorDeTurno.getMiAtaque();
		ArrayList<ComponenteGrafico> atacables = ataque.getPosiblesMovimientos(jugadorDeTurno.getPosicionX(), jugadorDeTurno.getPosicionY());
		
		if(atacables.contains(celda))
			celda.recibirAtaque(jugadorDeTurno);
		
	}
	
	private void mover(ComponenteGrafico celda) {
		Movimiento movimiento = jugadorDeTurno.getMiMovimiento();
		ArrayList<ComponenteGrafico> movibles = movimiento.getPosiblesMovimientos(jugadorDeTurno.getPosicionX(), jugadorDeTurno.getPosicionY());
		
		if(movibles.contains(celda))
			moverJugador(celda);
	}
	
	/*------------------------------------------Jugador-------------------------------------------- */
	
	/**
	 * @return Robot
	 */
	public Jugador getJugadorActual(){
		return jugadoresUsuario.get(proximoJugadorUsuario);
	}
	
	/**
	 * Mueve al jugador sea del Tanque o Robot a la celda indicada
	 * @param celda a la que pretende moverse el jugador de turno
	 */
	public void moverJugador(ComponenteGrafico celdaMovimiento){
		//Obtengo las coordenadas en la matriz de la celda a la que quiero mover el Jugador de Turno.
		int movX = celdaMovimiento.getX()/60;
		int movY = celdaMovimiento.getY()/60;
		
		//Obtengo las coordenadas del jugador de Turno en la matriz antes del movimiento para hacer el cambio.
		int posXJugador = jugadorDeTurno.getPosicionX();
		int posYJugador = jugadorDeTurno.getPosicionY();

		/*
		 * mover el jugador a la celda destino
		 * poner un piso en la celda donde estaba agregandole el oyente llamando con x,y a 
		 * agregarOyenteMouseTurnos
		*/
		
		grafica.eliminarGrafico(getComponente(movX,movY));
		jugadorDeTurno.setPosicionX(movX);
    	jugadorDeTurno.setPosicionX(movY);
    	this.setComponente(jugadorDeTurno);
    	grafica.agregarGrafico(jugadoresUsuario.get(1));
    	
    	grafica.eliminarGrafico(getComponente(posXJugador,posYJugador));
		ComponenteGrafico c = new Piso(posXJugador,posYJugador,this);
		this.agregarOyenteMouseTurnos(posXJugador, posYJugador);
		this.setComponente(celdaMovimiento);
		grafica.agregarGrafico(jugadoresUsuario.get(1));

	}
	
	/**
	 * Creo al Robot y lo ingreso al mapa logico
	 */
	private void ingresarRobot(int x, int y){
		Jugador robot = new Robot(x,y,this,new MovimientoRadio(this,3),new AtaqueAdyacentes(this));
		mapa[robot.getPosicionX()][robot.getPosicionY()] = robot;
		jugadoresUsuario.add(robot);
	}
	
	/**
	 * Creo al Tanque y lo ingreso al mapa logico
	 */
	private void ingresarTanque(int x, int y){
		Jugador tanque = new Tanque(x,y,this,new MovimientoRadio(this,5),new AtaqueFilaColumna(this));
		mapa[tanque.getPosicionX()][tanque.getPosicionY()] = tanque;
		jugadoresUsuario.add(tanque);
	}
	
	/**
	 * Creo al Robot al iniciar el juego
	 */
	public void crearRobot(int x, int y){
    	ingresarRobot(x,y);
    	grafica.eliminarGrafico(getComponente(x,y));
    	grafica.agregarGrafico(jugadoresUsuario.get(1));
    }
	
	/**
	 * Creo al Tanque al iniciar el juego
	 */
	public void crearTanque(int x, int y){
    	ingresarTanque(x,y);
    	grafica.eliminarGrafico(getComponente(x,y));
    	grafica.agregarGrafico(jugadoresUsuario.get(0));
    }
	
	public boolean fallaAtaque() {
		boolean falla = false;
		Random r = new Random();
		int probab = r.nextInt(100);
			if(probab<=29)
				falla = true;
		return falla;
	}

	/* ---------------------------------Enemigo----------------------------------*/
	/**
	 * Implementa la inteligencia para el movimiento de los Escarabajos y las Avispas
	 * Busca las celdas adyacentes al Tanque,al Robot, y a todos los edificios, si esta
	 * dentro del rango de su movimiento suma esa celda a una lista de celdas de posicion
	 * de ataque
	 * @return lista de posiciones de ataque a las que se puede mover un Enemigo
	 */
	public ArrayList<ComponenteGrafico> inteligenciaMovimientoEnemigos(){
		ArrayList<ComponenteGrafico> posiblesMovInteligentes = new ArrayList<ComponenteGrafico> ();
		
		//Guardo en una Lista todas las celdas adyacentes a los objetivos a destriuir para CPU
		ArrayList<ComponenteGrafico> adyacentesDeObjetivos = new ArrayList<ComponenteGrafico> ();
		
			for (ComponenteGrafico c : this.getCeldasAdyacentes(jugadoresUsuario.get(1)))
				if(!adyacentesDeObjetivos.contains(c))
					adyacentesDeObjetivos.add(c);
			if(jugadoresUsuario.size() == 2)
				for (ComponenteGrafico c : this.getCeldasAdyacentes(jugadoresUsuario.get(2)))
					if(!adyacentesDeObjetivos.contains(c))
						adyacentesDeObjetivos.add(c);
		for (ComponenteGrafico c : edificios)
			for(ComponenteGrafico comp : this.getCeldasAdyacentes(c)) {
				if(!adyacentesDeObjetivos.contains(comp))
					adyacentesDeObjetivos.add(comp);
		}
		
		//Guardo en una Lista todos las celdas de movimiento posibles de CPU
		Movimiento movEnemigo = jugadorDeTurno.getMiMovimiento();
		ArrayList<ComponenteGrafico> posiblesMovCPU = movEnemigo.getPosiblesMovimientos(jugadorDeTurno.getPosicionX(), jugadorDeTurno.getPosicionY());

		//Cada celda que este en ambas listas, es un posible movimiento inteligente
		//Para tomar posicion de ataque, y se guarda en la lista posiblesMovInteligentes 
		//que se retorna
		
		for(ComponenteGrafico comp : adyacentesDeObjetivos)
			if(posiblesMovCPU.contains(comp))
				posiblesMovInteligentes.add(comp);

		return posiblesMovInteligentes;
	}
	
	public ArrayList<ComponenteGrafico> inteligenciaAtaqueEnemigos(){
		ArrayList<ComponenteGrafico> posiblesAtaques = new ArrayList<ComponenteGrafico> ();
		
		Movimiento atqEnemigo = jugadorDeTurno.getMiAtaque();
		ArrayList<ComponenteGrafico> celdasAtqCPU = atqEnemigo.getPosiblesMovimientos(jugadorDeTurno.getPosicionX(), jugadorDeTurno.getPosicionY());
		
			for(ComponenteGrafico celdaAtacable : celdasAtqCPU)
				if(jugadoresUsuario.contains(celdaAtacable) || edificios.contains(celdaAtacable))
					posiblesAtaques.add(celdaAtacable);
			
		return posiblesAtaques;
	}
	
	
	/**
	 * Determina si el Jugador del Usuario esta en posicion de atacar un Enemigo
	 * @param j
	 * @return true si el Jugador esta en posicion de atacar un enemigo, false caso contrario
	 */
	public boolean puedeAtacar(Jugador j) {
		boolean puede = false;
		
		Movimiento ataqueJugador = j.getMiAtaque();
			
		
		return puede;
	}
	
	/**
	 * @return Hilo que contiene a los enemigos
	 */
	public Movimiento getHilosEnemigos(){
		return hiloEnemigos;
	}
	
	/**
	 * Si se murio el tercer y ultimo enemigo, finaliza el juego con victoria para el usuario
	 */
	public void murioCPU(){
		muertesAcumuladas++;
		if(muertesAcumuladas == 3)
			finalizarJuego(true);
	}
	
	public void murioUsuario() {
		usuariosMuertos++;
		if(usuariosMuertos == 2)
			finalizarJuego(false);
	}
	
	public void edificioDestruido() {
		edificiosDestruidos++;
		if(edificiosDestruidos == 4)
			finalizarJuego(false);
	}
	
	
	public ArrayList<ComponenteGrafico> getCeldasAdyacentes(ComponenteGrafico c) {
		int x = c.getPosicionX();
		int y = c.getPosicionY();
		ArrayList<ComponenteGrafico> listaAdy = new ArrayList<ComponenteGrafico> ();
		
		//Casos especiales: Esquinas( (0,0);(0,7);(7,0);(7,7) ) 
		if (x==0 && y == 0){//Esquina superior izquierda
	        listaAdy.add(getComponente(0,1));
	        listaAdy.add(getComponente(1,0));
	    }
	    else 
	    	if (x==0 && y==7){//Esquina superior derecha
	        listaAdy.add(getComponente(0,6));
	        listaAdy.add(getComponente(1,7));
	    	}
	    	else    
	    		if (x==7 && y==0){//Esquina inferior izquierda
	    		listaAdy.add(getComponente(6,0));
	    		listaAdy.add(getComponente(7,1));
	    		}
	    		else    
	    			if (x==7 && y==7){//Esquina inferior derecha
	    				listaAdy.add(getComponente(6,7));
	    				listaAdy.add(getComponente(7,6));
	    			}
	    			else {//Casos Especiales: Bordes( (0,*);(*;0);(7,*);(*,7) )
	    				if (x==0 && y!=0 && y!=7){//Borde Superior
	    			        listaAdy.add(getComponente(0,y-1));
	    			        listaAdy.add(getComponente(0,y+1));
	    			        listaAdy.add(getComponente(1,y));
	    			    }
	    			    else 
	    			    	if (x==7 && y!=0 && y!=7){//Borde Inferior
	    			        listaAdy.add(getComponente(7,y+1));
	    			        listaAdy.add(getComponente(7,y-1));
	    			        listaAdy.add(getComponente(6,y));
	    			    	}
	    			    	else    
	    			    		if (y==0 && x!=0 && x!=7){//Borde Izquierdo
	    			    		listaAdy.add(getComponente(x-1,0));
	    			    		listaAdy.add(getComponente(x+1,0));
		    			        listaAdy.add(getComponente(x,1));
	    			    		}
	    			    		else    
	    			    			if (y==7 && x!=0 && x!=7){//Borde Derecho
	    			    				listaAdy.add(getComponente(x-1,7));
	    			    				listaAdy.add(getComponente(x+1,7));
	    		    			        listaAdy.add(getComponente(x,6));
	    			    			}
	    			    			else {//Adyacentes sin conflicto
	    			    				listaAdy.add(getComponente(x-1,y));
	    			    				listaAdy.add(getComponente(x,y-1));
	    		    			        listaAdy.add(getComponente(x,y+1));
	    		    			        listaAdy.add(getComponente(x+1,y));
	    			    			}
	    			}
		
		return listaAdy;
	}

	public boolean getAtaca() {
		return ataca;
	}

	public void setAtaca(boolean ataca) {
		this.ataca = ataca;
	}

	public boolean getMueve() {
		return mueve;
	}

	public void setMueve(boolean mueve) {
		this.mueve = mueve;
	}
	
	public int proximoJugador(int actual, int cantLista){
		return (actual+1) % cantLista;
	}
	
	//setMsjUsuario
	
	private void jugar(){
		Random r = new Random(); boolean atacoCPU = false; int i;
		
		while(!termina) {
			
			if(turnoComputadora) {
				proximoJugadorComputadora = proximoJugador(proximoJugadorComputadora,enemigos.size());
				jugadorDeTurno = enemigos.get(proximoJugadorComputadora);
					
				while(!atacoCPU) {
					//Si puede atacar, ataca. Sino intenta mover a posicion de ataque
					ArrayList<ComponenteGrafico> ataquesPosibles = this.inteligenciaAtaqueEnemigos();
						if(!ataquesPosibles.isEmpty()) {
							i = r.nextInt(ataquesPosibles.size());
							ComponenteGrafico celdaAtaque = ataquesPosibles.get(i);
							jugadorDeTurno.atacar(celdaAtaque);
							
							atacoCPU = true;
						}
						else { //mueve
							ArrayList<ComponenteGrafico> movimientosPosibles = this.inteligenciaMovimientoEnemigos();
							if(!movimientosPosibles.isEmpty()) {
								i = r.nextInt(movimientosPosibles.size());
								ComponenteGrafico celdaDestino = movimientosPosibles.get(i);
								this.moverJugador(celdaDestino);
							}
						}
				}
				//Fin del turno de la Computadora
				grafica.reestablecerBotones();
				grafica.setMsjUsuario("La Computadora finalizó su turno. Ahora es turno del Usuario");
			}
			else //Turno Jugador 
			{
				proximoJugadorUsuario = proximoJugador(proximoJugadorUsuario,jugadoresUsuario.size());
				jugadorDeTurno = jugadoresUsuario.get(proximoJugadorUsuario);
				
				//Fin del turno del Usuario
				ataca = mueve = false;
			}
		
		}
	}	
	
	
	
}
