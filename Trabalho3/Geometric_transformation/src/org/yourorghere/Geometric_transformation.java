package org.yourorghere;

/*
 * Created on 14/03/2017
 *
*/
/**
 * @author Rosivania
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.sun.opengl.util.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;

public class Geometric_transformation extends JFrame implements GLEventListener, 
					    KeyListener {
    public static final Dimension PREFERRED_FRAME_SIZE = new Dimension(500, 500);

	static float rotacao=0.0f;
        static float xTransladar = 0.0f;
        static float yTransladar = 0.0f;
	static float escalar=1.0f;
	
    /** Constructor.
     */
    public Geometric_transformation() {
	// init JFrame
	super ("Transform_Cube - Opengl 2D ");
	System.out.println("Constructor");
    }

    /** We'd like to be 500x500, please.
     */
    public Dimension getPreferredSize(){
	return PREFERRED_FRAME_SIZE;
    }

    /*
     * METHODS DEFINED BY GLEventListener
     */

    /** Called by drawable to initiate drawing. 
     */
    public void display(GLAutoDrawable drawable) {
		
	GL gl = drawable.getGL();

	/* "CLEAR" o display */
	gl.glClear (GL.GL_COLOR_BUFFER_BIT);
	/* setar a cor de desenho em branco (1,1,1) */
	gl.glColor3f (1.0f, 1.0f, 1.0f);
	
	/* carregar a matriz de identidade na pilha de matriz de transformacao */
	gl.glLoadIdentity();
        

	/* concatenar a matriz de translacao com a do topo da pilha */
	gl.glTranslatef(.0f,.0f,-2.f);

	/* concatenar a matriz de rotacao com a do topo da pilha */
	gl.glRotatef(rotacao,0.0f,0.0f,1.0f);
	  

	/* concatenar a matriz de mudanca de escala com a do topo da pilha */
	gl.glScalef(.5f,.8f,0.5f);
        
        gl.glTranslatef(xTransladar, yTransladar, 0f);
        gl.glScalef(escalar, escalar, 0);
        
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glLineWidth(3.0f);
	/* desenhar o cubo */
	// Draw A Quad
        gl.glBegin(GL.GL_QUADS);
            gl.glColor3f(0.5f, 0.5f, 1.0f);    // Set the current drawing color to light blue
            gl.glVertex3f(-1.0f, 1.0f, 0.0f);  // Top Left
            gl.glVertex3f(1.0f, 1.0f, 0.0f);   // Top Right
            gl.glVertex3f(1.0f, -1.0f, 0.0f);  // Bottom Right
            gl.glVertex3f(-1.0f, -1.0f, 0.0f); // Bottom Left
        // Done Drawing The Quad
        gl.glEnd();
	   /* forcar a execucao dos comandos enviados a OpenGL */
            gl.glFlush ();	   

    }

    /** Called by drawable to indicate mode or device has changed.
     */
    public void displayChanged(GLAutoDrawable drawable, 
			       boolean modeChanged, 
			       boolean deviceChanged){}

    /** Called after OpenGL is init'ed
     */
    public void init(GLAutoDrawable drawable) {

    	System.out.println("init()");

    	GL gl = drawable.getGL();

    	System.err.println("INIT GL IS: " + gl.getClass().getName());
    	System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
    	System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
    	System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));

    	/* definir a cor em preto (0,0,0) como cor de "CLEAR" */
    	gl.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
        //gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    	/* setar o tipo de tonalizacao. Ha duas alternativas
    	      GL_FLAT (constante) e GL_SMOOTH (interpolacao linear
    	      entre os vertices) */
    	gl.glShadeModel (GL.GL_SMOOTH);
    	/* setar o tamanho do ponto em pixels */
    	gl.glPointSize(5.0f);
    	/* setar a largura da linha em pixels */
    	gl.glLineWidth(3.0f);
    	/* setar o modo de renderizacao das faces. Ha tres alternativas:
    	      GL_POINT (somente vertices), GL_LINE (somente linhas) e GL_FILL
    	      (faces preenchidas) */
    	gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
    	/* setar a convencao de orientacao. Ha duas alternativas: GL_CCW (anti-horario)      e GL_CW (horario) */
    	gl.glFrontFace (GL.GL_CCW);
    	/* Habilitar face culling */
    	gl.glEnable (GL.GL_CULL_FACE);
    	/* definir as faces a serem discartadas. Ha tres alternativas: GL_FRONT,
    	      GL_BACK e GL_FRONT_AND_BACK */
    	gl.glCullFace (GL.GL_FRONT);
    	
        /* habilitar os tratadores de eventos oriundos do teclado */
    	drawable.addKeyListener(this);    	
    }

    /** Called to indicate the drawing surface has been moved and/or resized.
     */
    public void reshape(GLAutoDrawable drawable, 
			int x, int y, 
			int width, int height){

    	System.out.println("reshape()");

    	GL gl = drawable.getGL();

    	/* definir matriz de escala do volume canonico
    	 * para o tamanho da janela de exibicao, em pixels */
    	gl.glViewport(0, 0, width, height); 
    	/* chavear para a pilha de transformacao de projecao */ 
    	gl.glMatrixMode (GL.GL_PROJECTION);
    	gl.glLoadIdentity ();
    	/* definir matriz de normalizacao */
    	gl.glOrtho (-2.0f, 2.0f, -2.0f, 2.0f, .0f, 5.0f);
    	/* chavear para a pilha de transformacao de modelos */
    	gl.glMatrixMode (GL.GL_MODELVIEW);   	
    }


    // Methods required for the implementation of KeyListener

    public void keyPressed(KeyEvent e){
    	System.out.println("Evento do teclado");
		switch(e.getKeyChar())
		{
		case 'x':
			/* Incremeta +10 no ângulo de rotação em torno de x */
	    	rotacao += 10.f;
			break;
		case 'y':
                     rotacao -= 10.f;
                break;
		case 'z':
                    /* reset */
	    	rotacao= .0f;
			break;
                case 'u': //move up
                    yTransladar += 2;
                    break;
                case 'd': //move down 
                    yTransladar -= 2;
                    break;
                case 'l': //left
                    xTransladar -= 2;
                    break;
                case 'r': //RIGHT
                    xTransladar += 2;
                    break;
                case 'm': //aumentar a escala 
                    escalar += 1;
                    break;

                case 'e':
                    escalar -= 1; // diminuir a escala
                    if(escalar <=0){
                        escalar = 1;
                    }
                    break; 
                case 27:
                        System.exit(0);
                        return;
    	}
    	/* Forcar o redesenho */
	    ((GLCanvas)(this.getContentPane().getComponentAt(0,0))).display();
    }

    public void keyReleased(KeyEvent e){}
    
    public void keyTyped(KeyEvent e){}
        
    /** main creates and shows a Geometric_Transformation-JFrame
     */
    public static void main(String[] args){

    	Geometric_transformation g = new Geometric_transformation();    
    	//Set frame location
    	g.setLocation(100,100);
       	GLCapabilities gl_c = new GLCapabilities();
    	// Disable double buffer
    	gl_c.setDoubleBuffered(false);
     	// get a GLCanvas
    	GLCanvas canvas = new GLCanvas(gl_c);
     	// add a GLEventListener, which will get called when the
    	// canvas is resized or needs a repaint
    	canvas.addGLEventListener(g);
    	// now add the canvas to the JFrame.  Note we use BorderLayout.CENTER
    	// to make the canvas stretch to fill the container (ie, the frame)
    	g.getContentPane().add(canvas, BorderLayout.CENTER);

    	g.pack();
    	g.setVisible(true);

    }
}

