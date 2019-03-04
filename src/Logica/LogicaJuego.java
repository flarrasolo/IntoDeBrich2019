package Logica;

import java.util.Random;

import Grafica.ComponenteGrafico;
import Grafica.GUI;
import Grafica.Jugadores.Jugador;
import Grafica.Terreno.Piso;
import Logica.Hilos.HiloTiempoEspera;
import Logica.Hilos.Movimiento;


public class LogicaJuego {
	
	/*Variables*/
	
	protected ComponenteGrafico[][] mapa;
	protected ComponenteGrafico miJugador;
	
	private Movimiento hiloEnemigos;
	private Movimiento hiloDisparoJugador;
	private Movimiento hiloDisparoEnemigo;
	private HiloTiempoEspera tiempoEsperaParaFinalizar;
	
	private int puntaje=0;
	private int enemigosMatados=0;
	private int muertesAcumuladas;
	private int nivelJugador;
	private int []respawn;
	private boolean termina;
	private boolean porQueTermina;
	private boolean detenerTanque;
	private boolean eliminarEnemigos;

	
	
	protected GUI grafica;
	
	//Constructor
	public LogicaJuego(GUI interfaz) {
		termina=false;
		
		nivelJugador = 1;
		
		//hiloDisparoJugador=new MovimientoBalas(this); //maneja los disparos del jugador
		hiloDisparoJugador.start();
		
		//hiloDisparoEnemigo=new MovimientoBalas(this); //maneja los disparos de todos los enemigos
		hiloDisparoEnemigo.start();
		
		//hiloEnemigos = new MovimientoEnemigos(this); //maneja a los enemigos
		hiloEnemigos.start();
		
		
		puntaje=0;			 //cuando llega a 20000, sumar una vida
		enemigosMatados=0;   //cuando llega a 4 creo un powerUp y lo reseteo
		muertesAcumuladas=0; //al llegar a 16, fin del juego con victoria
		
		grafica=interfaz;
		
		//creo los respawn
		respawn=new int[8];
		respawn[0]=3;respawn[1]=10;respawn[2]=19;respawn[3]=16;respawn[4]=4;
		respawn[5]=10;respawn[6]=1;respawn[7]=16;
		
		detenerTanque=false;
		eliminarEnemigos=false;


		mapa = new ComponenteGrafico[8][8];
		//creo el mapa
		generacionDeMapaLogico();
		
	}
	
/* ---------------------------------Mapa----------------------------------*/
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
	 * Genera una matriz interna en Logica distribuyendo como m�ximo monta�as, agua y tierra al azar
	 */
	public void generacionDeMapaLogico(){
		//mapa[j][i]= new Agua(i,j,this);
	}
	
	/**
	 * Obtengo el componente en las coordenadas (x,y)
	 * @param x coordenada en el eje x
	 * @param y coordenada en el eje y
	 * @return Componente en la posicion indicada
	 */
	public ComponenteGrafico getComponente(int x,int y){
		ComponenteGrafico aux;
		if (x<0 || x>8 || y<0 || y>8)
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
	public void eliminarColicion(int x,int y,ComponenteGrafico Ejecutor){
		getComponente(x, y).colicion(Ejecutor);
		if(getComponente(x, y).getVida()==0){
			eliminarGrafico(getComponente(x, y));
			mapa[y][x]=new Piso(x,y,this);
			agregarGrafico(getComponente(x, y));
		}
	}
	
	/**
	 * Indica el puntaje total en el juego
	 * @return Puntaje total
	 */
	public	int obtenerPuntaje(){
		return puntaje;
	}
	
	/**
	 * Ingresa los componentes del mapa en la parte grafica
	 */
	public void generarPanel(){
		for(int i=0;i<20;i++)
		 	for(int j=0;j<20;j++)
		 		agregarGrafico(getComponente(i,j));		 	
	}	
	
	/*--------------------------------Jugador---------------------------------- */
	
	/**
	 * @return Jugador
	 */
	public ComponenteGrafico getJugador(){
		return miJugador;
	}
	
	/**
	 * Mueve al jugador en la direccion inducada
	 * @param direccion a la que se desea mover
	 */
	public void mover(int direccion){
		miJugador.mover(direccion);
	}
	
	/**
	 * Creo al jugador y lo ingreso al mapa logico
	 */
	private void ingresarJugador(){
		//miJugador = new Jugador(5,17,this);
		mapa[miJugador.getPosicionY()][miJugador.getPosicionX()]=miJugador;
	}
	
	/**
	 * Creo al jugador al iniciar el juego
	 */
	public void crearJugador(){
    	ingresarJugador();
        agregarGrafico(getJugador());
    }
	
	/**
	 * Creo el disparo del jugador
	 */
	public void crearDisparoJugador(){
		ComponenteGrafico bala=miJugador.crearDisparo(); 
		if(bala!=null){
    		agregarGrafico(bala);
    		repintarPanel();
    		hiloDisparoJugador.addBala(bala);
		}
	}
	
	/**
	 * Crea un enemigo random y lo ingresa en alguno de los respawn
	 */
	public void crearYUbicarEnemigos(){
		
	}

	/* ---------------------------------Enemigo----------------------------------*/
	
	/**
	 * @return Hilo que contiene a los enemigos
	 */
	public Movimiento getHilosEnemigos(){
		return hiloEnemigos;
	}
	
	/**
	 * Creo el disparo a partir del enemigo que disparo
	 * @param x enemigo que disparo
	 */
	public void crearDisparoEnemigo(ComponenteGrafico x){
    	ComponenteGrafico bala=x.crearDisparo();
    	if(bala!=null){
    		agregarGrafico(bala);
    		repintarPanel();
    		hiloDisparoEnemigo.addBala(bala);
    	}
    }
	
	/**
	 * Si se murio un jugador y es el 4 seguido, entonces creo un PowerUp; 
	 * si es el numero 16 eliminado, entonces gane el juego.
	 * Si no termino el juego entonces creo un enemigo para mantener 4 enemigos 
	 * siempre
	 */
	public void enemigoMurio(){
		enemigosMatados++;
		muertesAcumuladas++;
		if(muertesAcumuladas == 16)
			finalizarJuego(true);
		else{
			if(enemigosMatados == 4){
				enemigosMatados = 0;
			}
		}
	}

	public void setDetenerTanque(boolean x){
		detenerTanque=x;
	}
	
	public boolean getDetenerTanque(){
		return detenerTanque;
	}
	
	public void resetearNivel(){
		nivelJugador = 1;
	}
	
}
