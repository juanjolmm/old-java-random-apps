import javax.swing.*;
//import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
public class GrabaCdJjl extends JFrame implements ActionListener //ListSelectionListener
{
// ATRIBUTOS PROPIOS GENARALES
	private double speedGrabacion;
	private long total=0;
	private String donde="/";
	private String[] elementos=new String[100];
	private int contadorElementos=0;
	private String ejecucion;
	private String tamanoDir="";
// FIN DE ATRIBUTOS PROPIOS GENERALES
// *************************************************************************
// Objetos para la velocidad de grabado
	/*private JLabel velocidad=new JLabel("Velocidad");
	private String speed[]={"2x-Juegos de Play","2.4x-DVD+R Doble capa","4x-DVD+-RW","5x-DVD-RAM","8x-DVD-R","16x-DVD+R","24x-CD-RW","40x-CD-R"}; 
	private JComboBox chooseSpeed=new JComboBox(speed);
// FIN Objetos para la velocidad de grabado
//	*************************************************************************
// Objetos para el tipo de grabacion
	private JLabel tipo=new JLabel("Tipo de grabacion");
	private String kind[]={"DAO","TAO"}; 
	private JComboBox chooseKind=new JComboBox(kind);*/
// FIN Objetos para el tipo de grabacion
// *************************************************************************
// Objetos para el que hacer
	private JLabel hacer=new JLabel("¿Que grabacion quieres?");
	private String doing[]={"Juego de PLay","CD de Audio","CD-R","CD-RW","DVD","Imagen de CD","Borrado de CD"}; 
	private JComboBox chooseDoing=new JComboBox(doing);
// FIN Objetos para el que hacer
// *************************************************************************
// Paneles para los tipos de grabacion
	private JPanel panelOpciones=new JPanel();
	private JPanel panelVelo=new JPanel(new FlowLayout());
	private JPanel panelTipo=new JPanel(new FlowLayout());
	private JPanel panelHacer=new JPanel(new FlowLayout());
// FIN Paneles para los tipos de grabacion
//	*************************************************************************
//	Objetos para el explorador
	private JScrollPane panelExplorador;
	private JList lista=new JList();
// FIN Objetos para el explorador
//	*************************************************************************
// Objetos necesarios para paneles inferiores
	private JPanel panelInferior=new JPanel(new BorderLayout());
	private JPanel panelInferior2=new JPanel(new FlowLayout());
	private JLabel actual=new JLabel(donde,JLabel.CENTER);
	private JButton anterior=new JButton("Arriba");
	private JButton grabar=new JButton("Grabar");
	private JButton seleccion=new JButton("Seleccionar");
	private JButton eliminar=new JButton("Eliminar");
//	FIN Objetos necesarios para paneles inferiores
//	*************************************************************************
//	Objetos para la informacion
	private JList listaInfo=new JList();
	private JLabel tamano=new JLabel("TAMAÑO: "+total+" Mb o "+total+" Kb");
	private JScrollPane panelInfo=new JScrollPane(listaInfo);
	private JPanel panelInfoTotal=new JPanel();
//	FIN Objetos para la informacion
	public GrabaCdJjl()
	{
		super("JAVA CD/DVD BY JUANJO");
		this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent ev){System.exit(1);}});
		this.setSize(750,750);
		initPaneles();
		initExplorador();
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add("North",panelOpciones);
		this.getContentPane().add("South",panelInferior);
		this.getContentPane().add("Center",panelExplorador);
		this.getContentPane().add("East",panelInfoTotal);
		this.getContentPane().setBackground(Color.WHITE);
	}
	public void initPaneles()
	{
		anterior.addActionListener(this);
		eliminar.addActionListener(this);
		grabar.addActionListener(this);
		seleccion.addActionListener(this);
		//chooseSpeed.addActionListener(this);
		panelHacer.setBackground(Color.WHITE);
		panelHacer.add(hacer);
		panelHacer.add(chooseDoing);
		panelVelo.setBackground(Color.WHITE);
		//panelVelo.add(velocidad);
		//panelVelo.add(chooseSpeed);
		panelTipo.setBackground(Color.WHITE);
		//panelTipo.add(tipo);
		//panelTipo.add(chooseKind);
		panelInferior2.add(anterior);
		panelInferior2.add(grabar);
		panelInferior2.add(seleccion);
		panelInferior2.add(eliminar);
		panelInferior2.setBackground(Color.WHITE);
		panelInferior.add("Center",panelInferior2);
		panelInferior.add("North",actual);
		panelInferior.setBackground(Color.WHITE);
		//Añadimos los componentes pequeños 
		panelOpciones.setBackground(Color.WHITE);
		panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.LINE_AXIS));
		panelOpciones.setBorder(BorderFactory.createTitledBorder( "Panel de Opciones"));
		panelOpciones.add (panelHacer);
		panelOpciones.add (panelVelo);
		panelOpciones.add (panelTipo);
		//Panel de Opciones
		//listaInfo.setPrototypeCellValue("000000000000000000000000000000000000");
		listaInfo.setFixedCellWidth(350);
		listaInfo.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent ev){if (ev.getClickCount()==2)eliminarElemento();}});
		panelInfoTotal.setBackground(Color.WHITE);
		panelInfoTotal.setLayout(new BoxLayout(panelInfoTotal, BoxLayout.Y_AXIS));
		panelInfoTotal.setBorder(BorderFactory.createTitledBorder( "Panel de Informacion"));
		panelInfoTotal.add (panelInfo);
		panelInfoTotal.add (tamano);
	}
	public void initExplorador()
	{
		File dir=new File(donde);
		iniciaLista(dir);
		//lista.addListSelectionListener(this);
		lista.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent ev){if (ev.getClickCount()==2)cambiaDirOSelecc();}});
		panelExplorador=new JScrollPane(lista);
		panelExplorador.setBorder(BorderFactory.createTitledBorder( "Explorador"));
		panelExplorador.setBackground(Color.WHITE);
	}
	/*public void valueChanged(ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting())
		{
			return;
		}
		File dire=new File(donde+lista.getSelectedValue()+"/");
		if(dire.isDirectory())
		{
			donde=donde+lista.getSelectedValue()+"/";
			actual.setText(donde);
			iniciaLista(dire);
		}
	}*/
	public void iniciaLista(File dir)
	{
		try
		{
			File[]miembros=dir.listFiles();
			String nombres[]=new String[miembros.length];
			for(int i=0;i<miembros.length;i++)
			{
				nombres[i]=miembros[i].getName();
			}
			ordenar(nombres);
			lista.setListData(nombres);
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	/*public void controlaVelocidad(int index)
	{
		switch(index)
		{
			case 0:speedGrabacion=2;
			break;
			case 1:speedGrabacion=2.4;
			break;
			case 2:speedGrabacion=4;
			break;
			case 3:speedGrabacion=5;
			break;
			case 4:speedGrabacion=8;
			break;
			case 5:speedGrabacion=16;
			break;
			case 6:speedGrabacion=24;
			break;
			case 7:speedGrabacion=40;
			break;
		}
	}*/
	public void actionPerformed(ActionEvent ev)
	{
		Object control=ev.getSource();
		/*if(control.equals(chooseSpeed))
		{
			int index=chooseSpeed.getSelectedIndex();
			controlaVelocidad(index);
		}*/
		if(control.equals(anterior))
		{
			subirNivel();		
		}
		else if(control.equals(seleccion))
		{
			String nombre=donde+lista.getSelectedValue();
			actualizaInfo(nombre);
		}
		else if(control.equals(eliminar))
		{
			eliminarElemento();
		}
		else if(control.equals(grabar))
		{
			setEjecucion();
			creaProceso(true);
		}
	}
	public void setEjecucion()
	{
		int opcion=chooseDoing.getSelectedIndex();
		ejecucion="/home/juanjo/bin/NewGraba";
		switch(opcion)
		{
			case 0:ejecucion+=" play ";
			break;
			case 1:ejecucion+=" audio ";
			break;
			case 2:ejecucion+=" cdr ";
			break;
			case 3:ejecucion+=" cdrw ";
			break;
			case 4:ejecucion+=" dvd ";
			break;
			case 5:ejecucion+=" imagen ";
			break;
			case 6:ejecucion+=" borrado ";
			break;
		}
		if(opcion!=6)
		{
			for(int i=0;i<contadorElementos;i++)
			{
				ejecucion+=elementos[i]+" ";
			}
		}
	}
	public void creaProceso(boolean grabar)
	{
		Runtime r=Runtime.getRuntime();
		Process p=null;
		try
		{
			System.out.println(ejecucion);
			p=r.exec(ejecucion);
			InputStream is=p.getInputStream();
			char c;
			int i=1;
			if(grabar)
			{
				JDialog informacion=new JDialog(this,"INFO DE GRABACION");
				JTextArea area=new JTextArea();
				JScrollPane panel=new JScrollPane(area);
				informacion.getContentPane().add(panel);
				area.setEditable(false);
				informacion.setSize(550,350);
				informacion.show();
				while(i!=-1)
				{
					i=is.read();
					if(i!=-1)
					{
						c=(char)i;
						area.setText(area.getText()+c);
					}
				}
			}
			else
			{
				while(i!=-1)
				{
					i=is.read();
					if(i!=-1)
					{
						c=(char)i;
						tamanoDir+=c;
					}
				}
			}
		} 
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public int tamanoDir(String direct)
	{
		int tamano=0;
		ejecucion="du -s "+direct;
		creaProceso(false);
		tamano=Integer.parseInt(tamanoDir.substring(0,tamanoDir.indexOf('/')));
		System.out.println(tamano);
		return tamano;
	}
	public void eliminarElemento()
	{
		if(contadorElementos>0)
		{
			try
			{
				String cual=(String)listaInfo.getSelectedValue();
				if(cual.length()>1)
				{
					for(int i=0;i<contadorElementos;i++)
					{
						if(cual.equals(elementos[i]))
						{
							for(int e=i;e<contadorElementos;e++)
							{
								elementos[e]=elementos[e+1];
							}
						}
					}
					File eliminado=new File(cual);
					total-=eliminado.length();
					contadorElementos--;
					tamano.setText("TAMAÑO: "+total/1024/1024+" Mb o "+total/1024+" Kb");
					listaInfo.setListData(elementos);
				}
			}
			catch(Exception ex)
			{
				
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
	public void cambiaDirOSelecc()
	{
		File dire=new File(donde+lista.getSelectedValue());
		File newFile=dire;
		if(dire.isDirectory() && dire.canRead())
		{
			if(dire.getName().indexOf(' ')!=-1)
			{
				newFile=cambiaNombre(dire);
			}
			donde=donde+newFile.getName()+"/";
			actual.setText(donde);
			iniciaLista(newFile);
		}
		else if(!dire.isDirectory())
		{
			if(dire.getName().indexOf(' ')!=-1)
			{
				newFile=cambiaNombre(dire);
				File recarga=new File(donde);
				iniciaLista(recarga);
			}
			actualizaInfo(donde+newFile.getName());
		}
	}
	public File cambiaNombre(File archivoAnt)
	{
		String nombreNuevo=donde+archivoAnt.getName().replace(' ','.');
		File newFile=new File(nombreNuevo);
		archivoAnt.renameTo(newFile);
		return newFile;
	}
	public void subirNivel()
	{
		if(donde.length()>2)
		{
			int index=1,anterior=-1,anterior2=0;
			while(index!=-1)
			{
				index=donde.indexOf('/',anterior+1);
				if(index!=-1)
				{
					anterior2=anterior;
					anterior=index;
				}
			}
			donde=donde.substring(0,anterior2+1);
			actual.setText(donde);
			File arch=new File(donde);
			iniciaLista(arch);
		}
	}
	public void actualizaInfo(String archivo)
	{
		elementos[contadorElementos]=archivo;
		File archivoNew=new File(archivo);
		contadorElementos++;
		if(archivoNew.isFile())
		{
			total+=archivoNew.length();
		}
		else
		{
			tamanoDir(archivo);
		}
		tamano.setText("TAMAÑO: "+total/1024/1024+" Mb o "+total/1024+" Kb");
		listaInfo.setListData(elementos);
	}
	public static void main(String [] args)
	{
		GrabaCdJjl ventana=new GrabaCdJjl();
		ventana.show();
	}
}