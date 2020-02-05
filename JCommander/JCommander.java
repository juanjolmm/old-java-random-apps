import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.event.*;
import javax.swing.border.*;
public class JCommander extends JFrame implements ActionListener, ListSelectionListener, MouseListener, Runnable
{
// ATRIBUTOS PROPIOS GENARALES
	private String pathInicial="/";
	private String pathActivo=pathInicial;
	private String comando;
	private Thread aux;
	private Process p=null;
	private Font fontPeque=new Font("terminal", Font.ITALIC, 11);
	private Font fontGrande=new Font("terminal", Font.BOLD, 14);
	private Font fontButton=new Font("lucidatypewriter", Font.BOLD, 10);
	private CeldasJJL celda=new CeldasJJL(this);
	private JNuevo dialogo;
// FIN DE ATRIBUTOS PROPIOS GENERALES
//	*************************************************************************
//	Objetos para el explorador
	private String contenido[];
	private JScrollPane panelExplorador1;
	private JList lista1=new JList();
	private JScrollPane panelExplorador2;
	private JList lista2=new JList();
	private JLabel actual1=new JLabel(pathInicial,JLabel.CENTER);
	private JLabel actual2=new JLabel(pathInicial,JLabel.CENTER);
	private JPanel panel1=new JPanel();
	private JPanel panel2=new JPanel();
	//private JPanel panelCentral=new JPanel(new GridLayout(1,1));
	private JPanel panelCentral=new JPanel(new GridLayout(1,1));
	private JPanel panelTotal=new JPanel(new BorderLayout(5,5));
// FIN Objetos para el explorador
//	*************************************************************************
// Objetos necesarios para paneles inferiores
	private JPanel panelInferior=new JPanel(new BorderLayout());
	private JPanel panelInferior2=new JPanel(new FlowLayout());
	private JButton copiar=new JButton("COPIAR");
	private JButton mover=new JButton("MOVER");
	private JButton nuevo=new JButton("NUEVO");
	private JButton renombrar=new JButton("RENOMBRAR");
	private JButton eliminar=new JButton("ELIMINAR");
	private JButton refresca=new JButton("REFRESCA");
//	FIN Objetos necesarios para paneles inferiores
//	*************************************************************************
	public JCommander()
	{
		super("JCommander By Niomai");
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent ev){System.exit(1);}});
		this.setSize(700,700);
		lista1.setName("lista1");
		lista2.setName("lista2");
		this.refrescaPantalla();
		this.initExplorador();
		this.initPaneles();
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelTotal,"Center");
	}
	public void refrescaPantalla()
	{
		initLista(lista1,actual1.getText());
		initLista(lista2,actual2.getText());
	}
	public void initLista(JList lista,String directorio)
	{
		File dir=new File(directorio);
		if(!directorio.equals(pathInicial))
		{
			String contenidoINI[]=dir.list();
			contenido=new String[contenidoINI.length+1];
			contenido[0]="..";
			for(int i=1;i<contenido.length;i++)
			{
				contenido[i]=contenidoINI[i-1];
			}
		}
		else
		{
			contenido=dir.list();
		}
		ordenar(contenido);
		lista.setListData(contenido);
		lista.setFixedCellHeight(17);
		lista.setFixedCellWidth(200);
		lista.setCellRenderer(celda);
	}
	public String getActual1()
	{
		return actual1.getText();
	}
	public String getActual2()
	{
		return actual2.getText();
	}
	public void initExplorador()
	{
		lista1.addMouseListener(this);
		lista2.addMouseListener(this);
		lista1.addListSelectionListener(this);
		lista2.addListSelectionListener(this);
		panelExplorador1=new JScrollPane(lista1);
		panelExplorador1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Explorador1",TitledBorder.CENTER,TitledBorder.TOP,fontPeque,Color.BLACK));
		panelExplorador2=new JScrollPane(lista2);
		panelExplorador2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Explorador2",TitledBorder.CENTER,TitledBorder.TOP,fontPeque,Color.BLACK));
		lista1.setFixedCellHeight(17);
		lista2.setFixedCellHeight(17);
	}
	public void initPaneles()
	{
		eliminar.setFont(fontButton);
		copiar.setFont(fontButton);
		mover.setFont(fontButton);
		nuevo.setFont(fontButton);
		refresca.setFont(fontButton);
		renombrar.setFont(fontButton);
		eliminar.addActionListener(this);
		copiar.addActionListener(this);
		mover.addActionListener(this);
		nuevo.addActionListener(this);
		refresca.addActionListener(this);
		renombrar.addActionListener(this);
		panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
		panel1.add(panelExplorador1);
		panel1.add(actual1);
		panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
		panel2.add(panelExplorador2);
		panel2.add(actual2);
		panelCentral.add(panel1);
		panelCentral.add(panel2);
		panelInferior2.add(refresca);
		panelInferior2.add(copiar);
		panelInferior2.add(mover);
		panelInferior2.add(nuevo);
		panelInferior2.add(renombrar);
		panelInferior2.add(eliminar);
		panelInferior.add("Center",panelInferior2);
		panelTotal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"JCommander",TitledBorder.CENTER,TitledBorder.TOP,fontGrande,Color.BLACK));
		panelTotal.add(panelCentral,"Center");
		panelTotal.add(panelInferior,"North");
	}
	public void abreArchivo(String fichero)
	{
		try
		{
			String extension=fichero;
			String programa;
			String ext;
			boolean programaEncontrado=false;
			int donde=extension.indexOf(".");
			if(donde!=-1)
			{
				BufferedReader buffer=new BufferedReader(new FileReader("programs.list"));
				while(donde!=-1)
				{
					extension=extension.substring(donde+1,extension.length());
					donde=extension.indexOf(".");
				}
				while((programa=buffer.readLine())!=null)
				{
					ext=programa.substring(0,programa.indexOf(":"));
					if(ext.equalsIgnoreCase(extension))
					{
						programaEncontrado=true;
						break;
					}
				}
				buffer.close();
				if(programaEncontrado)
				{
					programa=programa.substring(programa.indexOf(":")+1,programa.length());
					comando=programa+" "+fichero;
				}
				else
				{
					return;
				}
			}
			else
			{
				comando="xedit "+fichero;
			}
			this.creaProcesoParalelo();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	public void ordenar(String nombres[ ])
	{
		String tmp;
		for(int i=0; i< nombres.length;i++)
		{
			for(int e=i+1; e<nombres.length; e++)
			{
				if ( nombres[i].compareToIgnoreCase(nombres[e])>0)
				{
					tmp = nombres[i];
					nombres[i] = nombres[e];
					nombres[e] = tmp;    
				}
			}
		}
	}
	public void setEjecucion(String command)
	{
		comando=command;
	}
	public void creaProcesoParalelo()
	{
		try
		{
			aux=new Thread(this);
			aux.start();
		}	
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	public void creaProceso()
	{
		try
		{
			Runtime r=Runtime.getRuntime();
			p=r.exec(comando);
			p.waitFor();
			this.refrescaPantalla();
		}	
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	public void copiaOPega(String cual)
	{
		String origen;
		String destino;
		Object elementos[];
		JList lista;
		if(pathActivo.equals(actual1.getText()))
		{
			destino=actual2.getText();
			lista=lista1;
		}
		else
		{
			destino=actual1.getText();
			lista=lista2;
		}
		elementos=lista.getSelectedValues();
		for(int i=0;i<elementos.length;i++)
		{
			comando=cual+" "+pathActivo+elementos[i]+" "+destino;
			creaProceso();
		}
		refrescaPantalla();
	}
	public void eliminarElementos()
	{
		JList lista;
		Object elementos[];
		if(pathActivo.equals(actual1.getText()))
		{
			lista=lista1;
		}
		else
		{
			lista=lista2;
		}
		elementos=lista.getSelectedValues();
		for(int i=0;i<elementos.length;i++)
		{
			comando="rm -rf "+pathActivo+elementos[i];
			creaProceso();
		}
	}
	public void actionPerformed(ActionEvent ev)
	{
		Object control=ev.getSource();
		if(control.equals(copiar))
		{
			copiaOPega("cp -r");
		}
		else if(control.equals(eliminar))
		{
			eliminarElementos();
		}
		else if(control.equals(mover))
		{
			copiaOPega("mv");
		}
		else if(control.equals(nuevo))
		{
			dialogo=new JNuevo(this,pathActivo);
			dialogo.show();
		}
		else if(control.equals(refresca))
		{
			refrescaPantalla();
		}
		else if(control.equals(renombrar))
		{
			File dire;
			String nombre;
			if(lista1.getSelectedValue()==null)
			{
				nombre=(String)lista2.getSelectedValue();
			}
			else
			{
				nombre=(String)lista1.getSelectedValue();
			}
			if(nombre!=null)
			{
				dire=new File(pathActivo+nombre);
				dialogo=new JNuevo(this,pathActivo,dire);
				dialogo.show();	
			}
		}
	}
	public void valueChanged(ListSelectionEvent e)
	{
		Object control=e.getSource();
		if (e.getValueIsAdjusting())
		{
			return;
		}
		else
		{
			if(control.equals(lista1))
			{
				pathActivo=actual1.getText();
			}
			else if(control.equals(lista2))
			{
				pathActivo=actual2.getText();
			}
		}
	}
	public void mousePressed(MouseEvent e)
	{
		if(e.getClickCount()==2)
		{	
			JList lista;
			if(e.getSource().equals(lista1))
			{
				lista=lista1;
			}
			else
			{
				lista=lista2;
			}
			int cual=lista.getSelectedIndex();
			File elegido=new File(pathActivo+lista.getSelectedValue());
			if(!pathActivo.equals(pathInicial) && cual==0)
			{
				File archivo=new File(pathActivo);
				pathActivo=archivo.getParent();
				if(!pathActivo.equals(pathInicial))
				{
					pathActivo=pathActivo+"/";
				}
				initLista(lista,pathActivo);
			}
			else if(elegido.isDirectory() && elegido.canRead())
			{
				pathActivo=pathActivo+lista.getSelectedValue()+"/";
				initLista(lista,pathActivo);
			}
			else if(elegido.isFile())
			{
				abreArchivo(elegido.getAbsolutePath());
			}
			if(e.getSource().equals(lista1))
			{
				actual1.setText(pathActivo);
			}
			else
			{
				actual2.setText(pathActivo);
			}
		}
	}
	public void mouseClicked(MouseEvent e)	
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
	public void run()
	{
		try
		{
			Runtime r=Runtime.getRuntime();
			p=r.exec(comando);
			InputStream is=p.getInputStream();
			char c;
			int i=1;
			while(i!=-1)
			{
				i=is.read();
				if(i!=-1)
				{
					c=(char)i;
					System.out.print(c);
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	public static void main(String [] args)
	{
		try
		{
			JDialog.setDefaultLookAndFeelDecorated(true);
        	JFrame.setDefaultLookAndFeelDecorated(true);
        	Toolkit.getDefaultToolkit().setDynamicLayout(true);
        	System.setProperty("sun.awt.noerasebackground","true");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			JCommander ventana=new JCommander();
			ventana.show();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
}



class CeldasJJL extends JLabel implements ListCellRenderer 
{
	final static ImageIcon archivoIco = new ImageIcon("imagenes/file3.gif");
	final static ImageIcon carpetaIco= new ImageIcon("imagenes/folder2.gif");
	private String donde;
	JCommander ventana;
	public CeldasJJL(JCommander vent)
	{
		super();
		ventana=vent;
	}
	public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus)
	{
		String nombre=(String)value;
		if(list.getName().equals("lista1"))
		{
			donde=ventana.getActual1();
		}
		else
		{
			donde=ventana.getActual2();
		}
		File arch=new File(donde+nombre);
		if(arch.getName().indexOf(' ')!=-1)
		{
			arch=quitaEspacios(arch,donde);
		}
		this.setText(arch.getName());
		if(arch.isDirectory() && arch.canRead())
		{
			this.setIcon(carpetaIco);
			if(isSelected)
			{
				setBackground(list.getSelectionBackground());
				setForeground(Color.RED);
			}
			else
			{
				setBackground(list.getBackground());
				setForeground(Color.RED);
			}
		}
		else if(arch.isDirectory())
		{
			this.setIcon(carpetaIco);
			if(isSelected)
			{
				setBackground(list.getSelectionBackground());
				setForeground(Color.BLUE);
			}
			else
			{
				setBackground(list.getBackground());
				setForeground(Color.BLUE);
			}
		}
		else if(!arch.isDirectory())
		{
			this.setIcon(archivoIco);
			if(isSelected)
			{
				setBackground(list.getSelectionBackground());
				setForeground(Color.BLACK);
			}
			else
			{
				setBackground(list.getBackground());
				setForeground(Color.BLACK);
			}
		}
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	}
	public File quitaEspacios(File archivoAnt,String donde)
	{
		String nombreNuevo=donde+archivoAnt.getName().replace(' ','.');
		File newFile=new File(nombreNuevo);
		archivoAnt.renameTo(newFile);
		return newFile;
	}
}


class JNuevo extends JDialog implements ActionListener
 {
	private String donde;
 	private String comando;
 	private boolean isDir;
 	private ButtonGroup grupo=new ButtonGroup();
 	private JRadioButton archivo=new JRadioButton("Archivo",true);
	private JRadioButton directorio=new JRadioButton("Directorio",false);
	private JLabel nombre=new JLabel("Nombre");
	private JTextField texto=new JTextField();
	private JButton boton=new JButton("Aceptar");
	private JPanel panel=new JPanel(new GridLayout(2,2));
	private JCommander vent;
	private File antiguo;
 	public JNuevo(JCommander ventana,String dondeDir)
 	{
 		super(ventana,"Nuevo",true);
 		vent=ventana;
 		donde=dondeDir;
 		boton.addActionListener(this);
 		grupo.add(archivo);
		grupo.add(directorio);
 		panel.add(archivo);
		panel.add(directorio);
		panel.add(nombre);
		panel.add(texto);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Opciones",TitledBorder.CENTER,TitledBorder.TOP,new Font("terminal", Font.ITALIC, 11),Color.BLACK));
		this.getContentPane().add(panel);
		this.getContentPane().add("South",boton);
		this.setSize(250,120);
		this.setResizable(false);
 	}
	public JNuevo(JCommander ventana,String dondeDir,File ant)
	{
		super(ventana,"Renombrar",true);
		antiguo=ant;
		vent=ventana;
		donde=dondeDir;
		boton.addActionListener(this);
		texto.setText(ant.getName());
		panel.add(nombre);
		panel.add(texto);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Opciones",TitledBorder.CENTER,TitledBorder.TOP,new Font("terminal", Font.ITALIC, 11),Color.BLACK));
		this.getContentPane().add(panel);
		this.getContentPane().add("South",boton);
		this.setSize(250,120);
		this.setResizable(false);	
	}
	public void cambiaNombre(String antiguo,String nuevo,String donde)
	{
		String nombreNuevo=donde+nuevo;
		String nombreAnt=donde+antiguo;
		File newFile=new File(nombreNuevo);
		File antFile=new File(nombreAnt);
		antFile.renameTo(newFile);
	}
 	public void actionPerformed(ActionEvent ev)
 	{
 		if(antiguo==null)
 		{
 			donde+=texto.getText();
 			if(directorio.isSelected())
 			{
 				comando="mkdir "+donde;
 			}
 			else
 			{
				comando="touch "+donde;
 			}
 			vent.setEjecucion(comando);
 			vent.creaProceso();
 		}
 		else
 		{
			String nuevo=texto.getText();
			this.cambiaNombre(antiguo.getName(),nuevo,donde);
 		}
		vent.refrescaPantalla();
 		this.dispose();
 	}
 }
 