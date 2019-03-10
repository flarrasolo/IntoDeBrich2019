package Logica;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import Grafica.ComponenteGrafico;
import Grafica.GUI;
import Grafica.Jugadores.*;
import Grafica.Jugadores.Movimientos.Movimiento;
import Grafica.Terreno.*;
import Logica.Hilos.HiloTiempoEspera;
import Logica.Hilos.HiloTurnos;


public class LogicaJuego {
	
	/*Variables*/
	
	protected ComponenteGrafico[][] mapa;
	protected Jugador jugadorDeTurno;
	protected ComponenteGrafico miJugadorRobot;
	protected ComponenteGrafico miJugadorTanque;
	protected ArrayList<ComponenteGrafico> enemigos;
	
	private Movimiento hiloEnemigos;
	private HiloTurnos manejoTurnos;
	private HiloTiempoEspera tiempoEsperaParaFinalizar;
	
	private int enemigosMatados=0;
	private int muertesAcumuladas;
	
	private ArrayList<ComponenteGrafico> edificios;
	private boolean termina;
	private boolean porQueTermina;
	private boolean eliminarEnemigo;

	protected GUI grafica;
	
	//Constructor
	public LogicaJuego(GUI interfaz) {
		
		termina=false;
		
		miJugadorRobot = null;
		miJugadorTanque = null;
		
		

		enemigosMatados=0;   //cuando llega a 4 creo un powerUp y lo reseteo
		muertesAcumuladas=0; //al llegar a 16, fin del juego con victoria
		
		grafica=interfaz;
		eliminarEnemigo = false;

		mapa = new ComponenteGrafico[8][8];
		enemigos = new ArrayList<ComponenteGrafico>();
		edificios = new ArrayList<ComponenteGrafico> ();
		
		
		//Creacion del mapa
		generacionDeMapaLogico();
		crearYUbicarEnemigos();
		resaltarLugaresPosibles();
		agregarOyentesMouseInicio();
		

		//Maneja los turnos
		//manejoTurnos = new Hiloturnos(this); 
		//hiloTurnos.start();		
		
	}
	
/* ----------------------------------------------------Mapa--------------------------------------------------*/
	/**
	 * Repaint a todos los elementos del panel
	 */
	private void repintarPanel(){
		grafica.repaint();
	}
	
	/**
	 * Permite ver el mapa logico
	 * @return Una matriz que represanta al mapa logico
	 */
	public	ComponenteGrafico[][] getMapaLogico(){
		return mapa;
	}
	
	/**
	 * Genera una matriz interna en Logica distribuyendo exactamente 8 edificios, 
	 * entre 8 y 10 monta�as, entre 7 y 10 celdas de agua y entre 4 y 6 celdas de tierra al azar
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
				mapa[y][x] = new Monta�a(x,y,this);
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
								if(miJugadorTanque == null) {
									crearTanque(ingX,ingY);
									grafica.setMsjUsuario("Seleccione la ubicaci�n donde desea ubicar el Robot");
								}
								else {
									crearRobot(ingX,ingY);
									devolverPisoNormal();
									grafica.setMsjUsuario("Turno de la Computadora");
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
	
	private void agregarOyentesMouseTurnos() {
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++)
				mapa[j][i].addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {
						ComponenteGrafico comp =(ComponenteGrafico) e.getSource();
						int ingX = comp.getX()/60;
						int ingY = comp.getY()/60;
						//System.out.println(ingY+" - "+ingX);
						
						
							//Controlar click en la mitad superior
							if(ingY <= 3) {
								//Si todavia no agregue el Tanque
								if(miJugadorTanque == null) {
									crearTanque(ingY,ingX);
									//resaltarLugaresPosibles();
									grafica.setMsjUsuario("Seleccione la ubicaci�n donde desea ubicar el Robot");
								}
								else {
									crearRobot(ingY,ingX);
									grafica.setMsjUsuario("Turno de la Computadora");
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
		tiempoEsperaParaFinalizar=new HiloTiempoEspera(this);
		tiempoEsperaParaFinalizar.start();
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
		if(getComponente(x, y).getVida()==0){
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
		this.repintarPanel();
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
			ComponenteGrafico e1 = new Escarabajo(ubicacion.getPosicionX(),ubicacion.getPosicionY(),this);
			mapa[ubicacion.getPosicionY()][ubicacion.getPosicionX()]=e1;
			enemigos.add(e1);
			posiblesUbicaciones.remove(celdaAleatoria);
		}
		
		int celdaAleatoria = r.nextInt(posiblesUbicaciones.size());
		ComponenteGrafico ubicacion = posiblesUbicaciones.get(celdaAleatoria);
		ComponenteGrafico a1 = new Avispa(ubicacion.getPosicionX(),ubicacion.getPosicionY(),this);
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
			for(int x=0;x<posiblesUbicaciones.size();x++) {
				ComponenteGrafico celda = posiblesUbicaciones.get(x);
				setComponente(new PisoResaltado(celda.getPosicionX(),celda.getPosicionY(),this));
			}
	}
	
	private void devolverPisoNormal() {
		ArrayList<ComponenteGrafico> posiblesUbicaciones = getPosiblesUbicaciones(0,4,8);
		for(int x=0;x<posiblesUbicaciones.size();x++) {
			ComponenteGrafico celda = posiblesUbicaciones.get(x);
			setComponente(new Piso(celda.getPosicionX(),celda.getPosicionY(),this));
		}
	}
		
	/*------------------------------------------Jugador-------------------------------------------- */
	
	/**
	 * @return Robot
	 */
	public ComponenteGrafico getJugadorRobot(){
		return miJugadorRobot;
	}
	
	/**
	 * @return Tanque
	 */
	public ComponenteGrafico getJugadorTanque(){
		return miJugadorTanque;
	}
	
	/**
	 * Mueve al jugador sea del Tanque o Robot en la direccion inducada
	 * @param direccion a la que se desea mover
	 */
	public void moverJugador(int direccion){
		jugadorDeTurno.mover(direccion);
	}
	
	/**
	 * Creo al Robot y lo ingreso al mapa logico
	 */
	private void ingresarRobot(int x, int y){
		miJugadorRobot = new Robot(x,y,this);
		mapa[miJugadorRobot.getPosicionX()][miJugadorRobot.getPosicionY()] = miJugadorRobot;
	}
	
	/**
	 * Creo al Tanque y lo ingreso al mapa logico
	 */
	private void ingresarTanque(int x, int y){
		miJugadorTanque = new Tanque(x,y,this);
		mapa[miJugadorTanque.getPosicionX()][miJugadorTanque.getPosicionY()] = miJugadorTanque;
	}
	
	/**
	 * Creo al Robot al iniciar el juego
	 */
	public void crearRobot(int x, int y){
    	ingresarRobot(x,y);
    	grafica.eliminarGrafico(getComponente(x,y));
    	grafica.agregarGrafico(getJugadorRobot());
    }
	
	/**
	 * Creo al Tanque al iniciar el juego
	 */
	public void crearTanque(int x, int y){
    	ingresarTanque(x,y);
    	grafica.eliminarGrafico(getComponente(x,y));
    	grafica.agregarGrafico(getJugadorTanque());
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

		for (ComponenteGrafico c : this.getCeldasAdyacentes(miJugadorTanque))
			if(!adyacentesDeObjetivos.contains(c))
				adyacentesDeObjetivos.add(c);
		for (ComponenteGrafico c : this.getCeldasAdyacentes(miJugadorRobot))
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
	public void enemigoMurio(){
		muertesAcumuladas++;
		if(muertesAcumuladas == 3)
			finalizarJuego(true);
	}
	
	protected ArrayList<ComponenteGrafico> getCeldasAdyacentes(ComponenteGrafico c) {
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
}
