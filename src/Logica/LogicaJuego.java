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


public class LogicaJuego {
	
	/*Variables*/
	
	protected ComponenteGrafico[][] mapa;
	
	protected ArrayList<Jugador> jugadoresUsuario;
	protected ArrayList<Jugador> enemigos;
	protected ArrayList<ComponenteGrafico> edificios;
	
	protected Jugador jugadorDeTurno;
	protected ComponenteGrafico celdaClickUsuario;

	//private HiloTiempoEspera tiempoEsperaParaFinalizar;
	
	private int edificiosDestruidos,usuariosMuertos,muertesAcumuladas,proximoJugadorUsuario, proximoJugadorComputadora;
	
	private boolean termina,porQueTermina;
	private boolean movioUsuario;
	protected GUI grafica;
	
	//Constructor
	public LogicaJuego(GUI interfaz) {
		
		termina=false;
		movioUsuario = false;

		//Comienza Jugando la Computadora
		proximoJugadorComputadora = 0;
		proximoJugadorUsuario = -1;
		
		muertesAcumuladas = 0; //al llegar a 3, fin del juego con victoria
		edificiosDestruidos = 0; //al llegar a 4, fin del juego con derrota
		usuariosMuertos = 0; //al llegar a 2, fin del juego con derrota
		
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
		int cantMontanias =	4 + aleatorio.nextInt(2);
		int cantAgua = 7 + aleatorio.nextInt(3);
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
									System.out.println("Seleccione la ubicación donde desea ubicar el Robot");
									grafica.setMsjUsuario("Seleccione la ubicación donde desea ubicar el Robot");
								}
								else {
									crearRobot(ingX,ingY);
									/*
									System.out.println("Turno de la Computadora");
									grafica.setMsjUsuario("Turno de la Computadora");
									*/
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
	
	private void agregarOyenteMouseTurnos(int i, int j) {
		
		mapa[j][i].addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				celdaClickUsuario =(ComponenteGrafico) e.getSource();
				jugarTurnoUsuario();

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
	
	private void comenzarJuego() {
		
		devolverPisoNormal();
		grafica.repintarPanel();
		actualizarPanel();
		
		//Toda celda es "clickeable", solamente va a haber accion si es una accion posible, sino no pasa nada
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++)
				agregarOyenteMouseTurnos(i,j);
		
		//HiloTiempoEspera pausa = new HiloTiempoEspera(this,2);
		//pausa.start();
		
		jugadorDeTurno = enemigos.get(proximoJugadorComputadora);
		jugarTurnoCPU();
		
	}
	
	/**
	 * Indica si termino el juego
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
		HiloTiempoEspera tiempoEsperaParaFinalizar=new HiloTiempoEspera(this,2);
		tiempoEsperaParaFinalizar.start();
		this.finalizar();
	}
	
	/**
	 * Con todos los enemigos y disparos eliminados, muestro la pantalla de 
	 * fin de juego
	 */
	public void finalizar(){
		grafica.terminarJuego(porQueTermina);
	}
	
	/**
	 * Elimino el componente en las coordenadas (x,y).
	 * @param x coordenada x
	 * @param y coordenada y
	 */
	public void eliminarComponente(int x,int y){
			eliminarGrafico(getComponente(x, y));
			mapa[y][x]=new Piso(x,y,this);
			agregarOyenteMouseTurnos(x,y);
			agregarGrafico(getComponente(x,y));
		
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
			for(ComponenteGrafico celda : posiblesUbicaciones)
				celda.setDibujo("/Imagenes/fondoResaltado.png");
	}
	
	private void devolverPisoNormal() {
		
		ArrayList<ComponenteGrafico> posiblesUbicaciones = getPosiblesUbicaciones(0,4,8);
			for(ComponenteGrafico celda : posiblesUbicaciones)
				celda.setDibujo("/Imagenes/fondo.png");
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

    	//Pongo Piso donde estaba el Jugador
    	eliminarComponente(posXJugador,posYJugador);
		
		//Pongo el Jugador donde hizo click el usuario
		eliminarGrafico(getComponente(movX,movY));
    	jugadorDeTurno.setPosicionX(movX); 
    	jugadorDeTurno.setPosicionY(movY);
    	setComponente(jugadorDeTurno);
    	agregarGrafico(getComponente(movX,movY));
    	
	}
	
	
	public boolean fallaAtaque() {
		boolean falla = true;
		/*
		Random r = new Random();
		int probab = r.nextInt(100);
			if(probab<=29)
				falla = true;
		*/
		double random = Math.random();// generamos un numero al azar entre 0 y 1 

		if(random < 0.7)// el 70% de las veces 
			falla = false;
		return falla;
	}

	/* ---------------------------------Enemigo----------------------------------*/
	
	/* -------------------------Inteligencia Enemigo----------------------------------*/
	/**
	 * Implementa la inteligencia para el movimiento de los Escarabajos y las Avispas
	 * Busca las celdas adyacentes al Tanque,al Robot, y a todos los edificios, si esta
	 * dentro del rango de su movimiento suma esa celda a una lista de celdas de posicion
	 * de ataque
	 * @return lista de posiciones de ataque a las que se puede mover un Enemigo
	 */
	public ArrayList<ComponenteGrafico> inteligenciaMovimientoEnemigos(){
		ArrayList<ComponenteGrafico> posiblesMovInteligentes = new ArrayList<ComponenteGrafico> ();
		
		//Guardo en una Lista todas las celdas adyacentes a los objetivos a destriuir para la CPU
		ArrayList<ComponenteGrafico> adyacentesDeObjetivos = new ArrayList<ComponenteGrafico> ();
		
			for (ComponenteGrafico c : this.getCeldasAdyacentes(jugadoresUsuario.get(0)))
				if(!adyacentesDeObjetivos.contains(c))
					adyacentesDeObjetivos.add(c);
			if(jugadoresUsuario.size() == 2)
				for (ComponenteGrafico c : this.getCeldasAdyacentes(jugadoresUsuario.get(1)))
					if(!adyacentesDeObjetivos.contains(c))
						adyacentesDeObjetivos.add(c);
		for (ComponenteGrafico c : edificios)
			for(ComponenteGrafico comp : this.getCeldasAdyacentes(c)) {
				if(!adyacentesDeObjetivos.contains(comp))
					adyacentesDeObjetivos.add(comp);
		}
		
		//Guardo en una Lista todos las celdas de movimiento posibles de CPU
		Movimiento movEnemigo = jugadorDeTurno.getMiMovimiento();
		ArrayList<ComponenteGrafico> posiblesMovCPU = movEnemigo.getPosiblesMovimientos(jugadorDeTurno.getPosicionY(), jugadorDeTurno.getPosicionX());

		//Cada celda que este en ambas listas, es un posible movimiento inteligente
		//Para tomar posicion de ataque, se guarda en la lista posiblesMovInteligentes 
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
		
			//Agrega a la lista de posibles ataques solo l
			for(ComponenteGrafico celdaAtacable : celdasAtqCPU)
				if(jugadoresUsuario.contains(celdaAtacable) || edificios.contains(celdaAtacable))
					posiblesAtaques.add(celdaAtacable);
			
		return posiblesAtaques;
	}

	/* -----------------------------------------------------------------------------------------*/
	
	/**
	 * Si se murio el tercer y ultimo enemigo, finaliza el juego con victoria para el usuario
	 */
	public void murioCPU(Jugador morirme){
		eliminarComponente(morirme.getPosicionX(),morirme.getPosicionY());
		muertesAcumuladas++;
		actualizarEnemigos();
		if(muertesAcumuladas == 3)
			finalizarJuego(true);
		else//Reseteo el contador de jugadores para que siga en rango
			proximoJugadorComputadora = 0;
		enemigos.remove(morirme);
	}
	
	/**
	 * Si se murio el segundo y ultimo usuario, finaliza el juego con victoria para la computadora
	 */
	public void murioUsuario(Jugador morirme) {
		eliminarComponente(morirme.getPosicionX(),morirme.getPosicionY());
		usuariosMuertos++;
		actualizarUsuarios();
		if(usuariosMuertos == 2)
			finalizarJuego(false);
		else//Reseteo el contador de enemigos para que siga en rango
			proximoJugadorUsuario = 0;
		jugadoresUsuario.remove(morirme);
	}
	
	/**
	 * Si se destruyo el cuarto edificio, finaliza el juego con victoria para la computadora
	 */
	public void edificioDestruido(ComponenteGrafico destruirme) {
		eliminarComponente(destruirme.getPosicionX(),destruirme.getPosicionY());
		edificiosDestruidos++;
		actualizarEdificios();
		if(edificiosDestruidos == 4)
			finalizarJuego(false);
		edificios.remove(destruirme);
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
	
	public int proximoJugador(int actual, int cantLista){
		int prox = 0;
		if(cantLista != 0)
			prox = (actual+1) % cantLista;
		return prox;
	}

	private void jugarTurnoUsuario(){
		
			System.out.println("-----------------Turno del Usuario-----------------");
			grafica.setMsjUsuario("Turno del Usuario");
			
				//jugadorDeTurno.setImagenResaltada();
				grafica.repintarPanel();
				actualizarPanel();
				
				ArrayList<ComponenteGrafico> movimientos = jugadorDeTurno.getMiMovimiento().getPosiblesMovimientos(jugadorDeTurno.getPosicionY(), jugadorDeTurno.getPosicionX());
				
				//Si no es un ataque, todavia no movio y es un posible movimiento, muevo al Jugador.
				if(!movioUsuario && movimientos.contains(celdaClickUsuario)) {
					//Mueve el Jugador a la celda clickeada
					moverJugador(celdaClickUsuario);
					movioUsuario = true;
					
					System.out.println("MOVIO JUGADOR A");
					System.out.println("( "+jugadorDeTurno.getPosicionY()+" , "+jugadorDeTurno.getPosicionX()+" )");
					System.out.println("NUEVOS ATAQUES");

					for(ComponenteGrafico c: jugadorDeTurno.getMiAtaque().getPosiblesMovimientos(jugadorDeTurno.getPosicionX(), jugadorDeTurno.getPosicionY()))
						System.out.println("( "+c.getPosicionY()+" , "+c.getPosicionX()+" )");
					
					//System.out.println("Mueve Usuario");
					grafica.setMsjUsuario("Mueve Usuario");
				}
				else {
					ArrayList<ComponenteGrafico> ataques = jugadorDeTurno.getMiAtaque().getPosiblesMovimientos(jugadorDeTurno.getPosicionX(), jugadorDeTurno.getPosicionY());
					
					//Si no es un movimiento, entonces si es un ataque produzco el daño
					if(ataques.contains(celdaClickUsuario)) {
								//Inflige el daño
								//if (!fallaAtaque()) {
									jugadorDeTurno.atacar(celdaClickUsuario);
									//System.out.println("Fallo Ataque Usuario");
									//}
									//else
									grafica.setMsjUsuario("Ataque Usuario");
									System.out.println("Ataque Usuario");
									
									grafica.setMsjUsuario("Fin del Turno del Usuario");
									System.out.println("Fin del Turno del Usuario");
									
									//jugadorDeTurno.setImagenNormal();
									grafica.repintarPanel();
									actualizarPanel();

									movioUsuario = false;
									
									//Corro el indice de la lista de Enemigos al que le toca
									proximoJugadorComputadora = proximoJugador(proximoJugadorComputadora,enemigos.size());
									
									//Obtengo el jugador (Avispa o uno de los Escarabajos)
									jugadorDeTurno = enemigos.get(proximoJugadorComputadora);
									
									//Actualizo la GUI y le toca el turno a la Computadora
									actualizarVidas();
									grafica.repintarPanel();
									actualizarPanel();
									jugarTurnoCPU();
					}
					else {
					//Si no es un movimiento ni un ataque selecciono una celda equivocada
					if (!movimientos.contains(celdaClickUsuario)){
						grafica.setMsjUsuario("Selecciono una celda que no es Ataque ni Movimiento");
						System.out.println("Selecciono una celda que no es Ataque ni Movimiento");
					}
					else
						System.out.println("NO HIZO NADA");
					}
				}
		
		grafica.repintarPanel();
		actualizarPanel();
	}
	
	private void jugarTurnoCPU() {
		Random r = new Random(); boolean finDelTurno = false; boolean movioCPU = false;
		int i;
		
		//HiloTiempoEspera espera = new HiloTiempoEspera(this,2);
		//espera.run();
		
		System.out.println("------------------------------Turno CPU------------------------------");
		//jugadorDeTurno.setImagenResaltada();
		grafica.repintarPanel();
		actualizarPanel();
					
		while(!finDelTurno) {
			
			//Si puede atacar, ataca y fin del turno. Sino intenta mover a posicion de ataque
			ArrayList<ComponenteGrafico> ataquesPosibles = this.inteligenciaAtaqueEnemigos();
				if(!ataquesPosibles.isEmpty()) {
					i = r.nextInt(ataquesPosibles.size());
					ComponenteGrafico celdaAtaque = ataquesPosibles.get(i);
					//if(!fallaAtaque()) {
						jugadorDeTurno.atacar(celdaAtaque);
					//	System.out.println("Fallo Ataque CPU");
					//}
					//else
						grafica.setMsjUsuario("Ataque CPU");
						System.out.println("Ataque CPU");
						finDelTurno = true;
				}
				else { //mueve si es que todavia no movio
					ArrayList<ComponenteGrafico> movimientosPosibles = this.inteligenciaMovimientoEnemigos();
					if(!movioCPU) {
						if(!movimientosPosibles.isEmpty()) {
						i = r.nextInt(movimientosPosibles.size());
						ComponenteGrafico celdaDestino = movimientosPosibles.get(i);
						this.moverJugador(celdaDestino);
						
						//jugadorDeTurno.setImagenNormal();
						grafica.repintarPanel();
						actualizarPanel();
						System.out.println("( "+jugadorDeTurno.getPosicionY()+" , "+jugadorDeTurno.getPosicionX()+" )");
						movioCPU = true;
						grafica.setMsjUsuario("Movio CPU");
						System.out.println("Movio CPU");
						}
						else
							if(!movioCPU && !finDelTurno)
								finDelTurno = true;
					}
					else
						finDelTurno = true;
				}
		}
		//Fin del turno de la Computadora
		
		actualizarVidas();
	
		//jugadorDeTurno.setImagenNormal();
		grafica.repintarPanel();
		actualizarPanel();
		
		if(usuariosMuertos<2) {
			//Corro el indice de la lista de Jugadores al que le toca
			proximoJugadorUsuario = proximoJugador(proximoJugadorUsuario,jugadoresUsuario.size());
			//Obtengo el jugador (Tanque o Robot)
			jugadorDeTurno = jugadoresUsuario.get(proximoJugadorUsuario);
		}
		
		System.out.println("La Computadora finalizó su turno. Ahora es turno del Usuario");
		grafica.setMsjUsuario("La Computadora finalizó su turno. Ahora es turno del Usuario");
		
		grafica.repintarPanel();
		actualizarPanel();
	}
	
	/*--------------------------------Actualizacion de Interfaz---------------------------------*/

	/**
	 * Creo al Robot y lo ingreso al mapa logico
	 */
	private void ingresarRobot(int x, int y){
		Jugador robot = new Robot(x,y,this,new MovimientoRadio(this,3),new AtaqueAdyacentes(this));
		mapa[robot.getPosicionX()][robot.getPosicionY()] = robot;
		/*System.out.println("MOVIMIENTOS POSIBLES");
		for(ComponenteGrafico c: robot.getMiMovimiento().getPosiblesMovimientos(x,y))
			System.out.println("( "+c.getPosicionY()+" , "+c.getPosicionX()+" )");
			*/
		jugadoresUsuario.add(robot);
	}
	
	/**
	 * Creo al Tanque y lo ingreso al mapa logico
	 */
	private void ingresarTanque(int x, int y){
		Jugador tanque = new Tanque(x,y,this,new MovimientoRadio(this,5),new AtaqueFilaColumna(this));
		mapa[tanque.getPosicionX()][tanque.getPosicionY()] = tanque;
		/*
		 * System.out.println("MOVIMIENTOS POSIBLES");
		for(ComponenteGrafico c: tanque.getMiMovimiento().getPosiblesMovimientos(x,y))
			System.out.println("( "+c.getPosicionY()+" , "+c.getPosicionX()+" )");
		*/
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
	
	
	/*-------------------------------------------LABELS----------------------------------------*/
	private void actualizarVidas() {
		String vidas = " "; String vidasEdificios = " ";
		for(Jugador j: jugadoresUsuario)
			vidas+="    -    "+j.getEnergia();
		for(Jugador enemigo : enemigos)
			vidas+="    -    "+enemigo.getEnergia();
		
		for(ComponenteGrafico edif : edificios)
			vidasEdificios+=edif.getEnergia()+ "  -  ";
		grafica.setVidas(vidas);
		grafica.setVidasEdificios(vidasEdificios);
	}
	
	private void actualizarEdificios() {
		grafica.setEdificiosDestruidos(""+edificiosDestruidos);
	}
	
	private void actualizarUsuarios() {
		grafica.setUsuariosDestruidos(""+usuariosMuertos);
	}
	
	private void actualizarEnemigos() {
		grafica.setEnemigosDestruidos(""+muertesAcumuladas);
	}
}