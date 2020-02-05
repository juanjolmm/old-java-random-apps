import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.event.*;
import javax.swing.border.*;
public class JCommander extends JFrame implements ActionListener,ListSelectionListener,Runnable
{
// ATRIBUTOS PROPIOS GENARALES
	private String donde1="/";
	private String donde2="/mnt/";
	private String ejecucion;
	private int from=1;
	private Object elementos[];
	private Thread aux;
	private Process p=null;
	private Font fontPeque=new Font("terminal", Font.ITALIC, 11);
	private Font fontGrande=new Font("terminal", Font.BOLD, 14);
	private Font fontButton=new Font("lucidatypewriter", Font.BOLD, 10);
	private CeldasJJL1 celda1=new CeldasJJL1();
	private CeldasJJL2 celda2=new CeldasJJL2();
	private JNuevo dialogo;
// FIN DE ATRIBUTOS PROPIOS GENERALES
//	*************************************************************************
//	Objetos para el explorador
	private JScrollPane panelExplorador1;
	private JList lista1=new JList();
	private JScrollPane panelExplorador2;
	private JList lista2=new JList();
	private JLabel actual1=new JLabel(donde1,JLabel.CENTER);
	private JLabel actual2=new JLabel(donde2,JLabel.CENTER);
	private JPanel panel1=new JPanel();
	private JPanel panel2=new JPanel();
	private JPanel panelCentral=new JPanel(new GridLayout(1,1));
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
	private JButton inicio=new JButton("INICIO");
	private JButton refresca=new JButton("REFRESCA");
//	FIN Objetos necesarios para paneles inferiores
//	*************************************************************************
	public JCommander()
	{
		super("JCommander By Niomai");
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent ev){System.exit(1);}});
		this.setSize(750,750);
		initExplorador();
		initPaneles();
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add("South",panelInferior);
		this.getContentPane().add("Center",panelCentral);
		this.getContentPane().setBackground(Color.WHITE);
	}
	public void initPaneles()
	{
		eliminar.setFont(fontButton);
		copiar.setFont(fontButton);
		mover.setFont(fontButton);
		nuevo.setFont(fontButton);
		inicio.setFont(fontButton);
		refresca.setFont(fontButton);
		renombrar.setFont(fontButton);
		eliminar.addActionListener(this);
		copiar.addActionListener(this);
		mover.addActionListener(this);
		nuevo.addActionListener(this);
		inicio.addActionListener(this);
		refresca.addActionListener(this);
		renombrar.addActionListener(this);
		panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
		panel1.add(panelExplorador1);
		panel1.add(actual1);
		actual1.setForeground(Color.WHITE);
		panel1.setBackground(Color.BLACK);
		panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
		panel2.add(panelExplorador2);
		panel2.add(actual2);
		actual2.setForeground(Color.WHITE);
		panel2.setBackground(Color.BLACK);
		panelCentral.add(panel1);
		panelCentral.add(panel2);
		panelCentral.setBackground(Color.BLACK);
		panelCentral.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"JCommander",TitledBorder.CENTER,TitledBorder.TOP,fontGrande,Color.WHITE));
		panelInferior2.add(inicio);
		panelInferior2.add(refresca);
		panelInferior2.add(copiar);
		panelInferior2.add(mover);
		panelInferior2.add(nuevo);
		panelInferior2.add(renombrar);
		panelInferior2.add(eliminar);
		panelInferior2.setBackground(Color.BLACK);
		panelInferior.add("Center",panelInferior2);
		panelInferior.setBackground(Color.BLACK);
	}
	public void initExplorador()
	{
		refrescaPantalla();
		lista1.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent ev){if (ev.getClickCount()==2)cambiaDirOSelecc(1);}});
		lista2.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent ev){if (ev.getClickCount()==2)cambiaDirOSelecc(2);}});
		lista1.addListSelectionListener(this);
		lista2.addListSelectionListener(this);
		panelExplorador1=new JScrollPane(lista1);
		panelExplorador1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),"Explorador1",TitledBorder.CENTER,TitledBorder.TOP,fontPeque,Color.WHITE));
		panelExplorador2=new JScrollPane(lista2);
		panelExplorador2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),"Explorador2",TitledBorder.CENTER,TitledBorder.TOP,fontPeque,Color.WHITE));
		lista1.setBackground(Color.BLACK);
		lista2.setBackground(Color.BLACK);
		lista1.setForeground(Color.white);
		lista2.setForeground(Color.white);
		lista1.setFixedCellHeight(17);
		lista2.setFixedCellHeight(17);
		panelExplorador1.setBackground(Color.BLACK);
		panelExplorador2.setBackground(Color.BLACK);
		iniciaCeldas();
	}
	public void iniciaCeldas()
	{
		celda1.setDonde(donde1);
		celda2.setDonde(donde2);
		lista1.setCellRenderer(celda1);
		lista2.setCellRenderer(celda2);
	}
	public void iniciaLista(String donde,JList lista)
	{
		try
		{
			String nombres[];
			File dir=new File(donde);
			if(!donde.equals("/"))
			{
				String nombresINI[]=dir.list();
				ordenar(nombresINI);
				nombres=new String[nombresINI.length+1];
				nombres[0]="..";
				for(int i=1;i<nombres.length;i++)
				{
					nombres[i]=nombresINI[i-1];
				}
			}
			else
			{
				nombres=dir.list();
				ordenar(nombres);
			}
			lista.setListData(nombres);
			iniciaCeldas();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
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
			if(from==1)
			{
				dialogo=new JNuevo(this,donde1);
			}
			else if(from==2)
			{
				dialogo=new JNuevo(this,donde2);
			}
			dialogo.show();
		}
		else if(control.equals(inicio))
		{
			donde1="/";
			actual1.setText(donde1);
			donde2="/mnt/";
			actual2.setText(donde2);
			refrescaPantalla();
		}
		else if(control.equals(refresca))
		{
			refrescaPantalla();
		}
		else if(control.equals(renombrar))
		{
			File dire;
			if(from==1)
			{
				dire=new File(donde1+lista1.getSelectedValue());
				dialogo=new JNuevo(this,donde1,dire);
			}
			else if(from==2)
			{
				dire=new File(donde1+lista2.getSelectedValue());
				dialogo=new JNuevo(this,donde2,dire);
			}
			dialogo.show();		
		}
	}
	public void copiaOPega(String cual)
	{
		if(from==1)
		{
			elementos=lista1.getSelectedValues();
			for(int i=0;i<elementos.length;i++)
			{
				ejecucion=cual+" "+donde1+elementos[i]+" "+donde2;
				creaProceso();
			}
		}
		else if(from==2)
		{
			elementos=lista2.getSelectedValues();
			for(int i=0;i<elementos.length;i++)
			{
				ejecucion=cual+" "+donde2+elementos[i]+" "+donde1;
				creaProceso();
			}
		}
		refrescaPantalla();
	}
	public void valueChanged(ListSelectionEvent e)
	{
		Object control=e.getSource();
		if (e.getValueIsAdjusting() && control.equals(lista1))
		{
			lista2.clearSelection();
			return;
		}
		else if (e.getValueIsAdjusting() && control.equals(lista2))
		{
			lista1.clearSelection();
			return;
		}
		else
		{
			if(control.equals(lista1))
			{
				from=1;
			}
			else if(control.equals(lista2))
			{
				from=2;
			}
		}
	}
	public void setEjecucion(String command)
	{
		ejecucion=command;
	}
	public void creaProceso()
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
	public void run()
	{
		try
		{
			Runtime r=Runtime.getRuntime();
			p=r.exec(ejecucion);
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
			refrescaPantalla();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	public void eliminarElementos()
	{
		if(from==1)
		{
			elementos=lista1.getSelectedValues();
			for(int i=0;i<elementos.length;i++)
			{
				ejecucion="rm -rf "+donde1+elementos[i];
				creaProceso();
			}
		}
		else if(from==2)
		{
			elementos=lista2.getSelectedValues();
			for(int i=0;i<elementos.length;i++)
			{
				ejecucion="rm -rf "+donde2+elementos[i];
				creaProceso();
			}
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
	public void cambiaDirOSelecc(int cual)
	{
		if(cual==1)
		{
			if(!donde1.equals("/") && lista1.getSelectedIndex()==0)
			{
				File parent=new File(donde1);
				donde1=parent.getParent();
				if(!donde1.equals("/"))
				{
					donde1=donde1+"/";
				}
				iniciaLista(donde1,lista1);
				actual1.setText(donde1);
			}
			else
			{
				File dire=new File(donde1+lista1.getSelectedValue());
				File newFile=dire;
				if(dire.isDirectory() && dire.canRead())
				{
					if(dire.getName().indexOf(' ')!=-1)
					{
						newFile=cambiaNombre(dire,donde1);
					}
					donde1=donde1+newFile.getName()+"/";
					iniciaLista(donde1,lista1);
					actual1.setText(donde1);
				}
				else if(!dire.isDirectory())
				{
					if(dire.getName().indexOf(' ')!=-1)
					{
						newFile=cambiaNombre(dire,donde1);
						refrescaPantalla();
					}
					abreArchivo(donde1+newFile.getName());
				}	
			}
		}
		else if(cual==2)
		{
			if(!donde2.equals("/") && lista2.getSelectedIndex()==0)
			{
				File parent=new File(donde2);
				donde2=parent.getParent();
				if(!donde2.equals("/"))
				{
					donde2=donde2+"/";
				}
				iniciaLista(donde2,lista2);
				actual2.setText(donde2);
			}
			else
			{
				File dire=new File(donde2+lista2.getSelectedValue());
				File newFile=dire;
				if(dire.isDirectory() && dire.canRead())
				{
					if(dire.getName().indexOf(' ')!=-1)
					{
						newFile=cambiaNombre(dire,donde2);
					}
					donde2=donde2+newFile.getName()+"/";
					iniciaLista(donde2,lista2);
					actual2.setText(donde2);
				}
				else if(!dire.isDirectory())
				{
					if(dire.getName().indexOf(' ')!=-1)
					{
						newFile=cambiaNombre(dire,donde2);
						refrescaPantalla();
					}
					abreArchivo(donde2+newFile.getName());
				}
			}
		}
	}
	public void abreArchivo(String archivo)
	{
		if(archivo.endsWith(".avi") || archivo.endsWith(".AVI") || archivo.endsWith(".mpg") || archivo.endsWith(".MPG") || archivo.endsWith(".mpeg") || archivo.endsWith(".MPEG") || archivo.endsWith(".wmv") || archivo.endsWith(".WMV") || archivo.endsWith(".asf") || archivo.endsWith(".ASF"))
		{
			ejecucion="gmplayer "+archivo;
			creaProceso();
		}
		else if(archivo.endsWith(".mp3") || archivo.endsWith(".MP3"))
		{
			ejecucion="xmms "+archivo;
			creaProceso();
		}
		else if(archivo.endsWith(".jpg") || archivo.endsWith(".JPG") || archivo.endsWith(".jpeg") || archivo.endsWith(".JPEG") || archivo.endsWith(".gif") || archivo.endsWith(".GIF") || archivo.endsWith(".bmp") || archivo.endsWith(".BMP"))
		{
			ejecucion="gqview "+archivo;
			creaProceso();
		}
		else if(archivo.endsWith(".pdf") || archivo.endsWith(".PDF"))
		{
			ejecucion="xpdf "+archivo;
			creaProceso();
		}
		else if(archivo.endsWith(".rar") || archivo.endsWith(".rar"))
		{
			File arch=new File(archivo);
			ejecucion="mkdir "+arch.getAbsolutePath()+"DIR";
			creaProceso();
			ejecucion="cd "+arch.getAbsolutePath()+"DIR";
			creaProceso();
			ejecucion="mv "+arch.getAbsolutePath()+" "+arch.getAbsolutePath()+"DIR";
			creaProceso();
			ejecucion="unrar e "+arch.getAbsolutePath()+"DIR//"+arch.getAbsolutePath();
			creaProceso();
		}
		else
		{
			ejecucion="xedit "+archivo;
			creaProceso();
		}
	}
	public File cambiaNombre(File archivoAnt,String donde)
	{
		String nombreNuevo=donde+archivoAnt.getName().replace(' ','.');
		File newFile=new File(nombreNuevo);
		archivoAnt.renameTo(newFile);
		return newFile;
	}
	public void cambiaNombre(String antiguo,String nuevo,String donde)
	{
		String nombreNuevo=donde+nuevo;
		String nombreAnt=donde+antiguo;
		File newFile=new File(nombreNuevo);
		File antFile=new File(nombreAnt);
		antFile.renameTo(newFile);
	}
	public void refrescaPantalla()
	{
		File arch1=new File(donde1);
		iniciaLista(donde1,lista1);
		File arch2=new File(donde2);
		iniciaLista(donde2,lista2);
	}
	public static void main(String [] args)
	{
		JCommander ventana=new JCommander();
		ventana.show();
	}
}



class CeldasJJL1 extends JLabel implements ListCellRenderer 
{
	final static ImageIcon archivoIco = new ImageIcon("imagenes/file3.gif");
	final static ImageIcon carpetaIco= new ImageIcon("imagenes/folder2.gif");
	private String donde;
	public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus)
	{
		String nombre=(String)value;
		this.setText(nombre);
		File arch=new File(donde+nombre);
		if(arch.isDirectory())
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
				setForeground(Color.white);
			}
		}
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	 }
	 public void setDonde(String cual)
	 {
		donde=cual;
	 }
 }
class CeldasJJL2 extends JLabel implements ListCellRenderer 
{
	final static ImageIcon archivoIco = new ImageIcon("imagenes/file3.gif");
	final static ImageIcon carpetaIco= new ImageIcon("imagenes/folder2.gif");
	public String donde;
	public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus)
	{
		String nombre=(String)value;
		this.setText(nombre);
		File arch=new File(donde+nombre);
		if(arch.isDirectory())
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
		else if(arch.isFile())
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
				setForeground(Color.white);
			}
		}
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	 }
	 public void setDonde(String cual)
	 {
	 	donde=cual;
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
 		panel.setBackground(Color.BLACK);
 		this.setBackground(Color.BLACK);
 		archivo.setForeground(Color.WHITE);
		directorio.setForeground(Color.WHITE);
		archivo.setBackground(Color.BLACK);
		directorio.setBackground(Color.BLACK);
		nombre.setForeground(Color.WHITE);
 		grupo.add(archivo);
		grupo.add(directorio);
 		panel.add(archivo);
		panel.add(directorio);
		panel.add(nombre);
		panel.add(texto);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),"Opciones",TitledBorder.CENTER,TitledBorder.TOP,new Font("terminal", Font.ITALIC, 11),Color.WHITE));
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
		panel.setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		nombre.setForeground(Color.WHITE);
		texto.setText(ant.getName());
		panel.add(nombre);
		panel.add(texto);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),"Opciones",TitledBorder.CENTER,TitledBorder.TOP,new Font("terminal", Font.ITALIC, 11),Color.WHITE));
		this.getContentPane().add(panel);
		this.getContentPane().add("South",boton);
		this.setSize(250,120);
		this.setResizable(false);	
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
			vent.cambiaNombre(antiguo.getName(),nuevo,donde);
 		}
		vent.refrescaPantalla();
 		this.dispose();
 	}
 }
 