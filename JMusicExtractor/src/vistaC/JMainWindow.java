package vistaC;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JMenuBar;
import java.awt.Dimension;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import java.awt.Font;
import javax.swing.JTree;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.ExtractorModel;

public class JMainWindow extends JFrame
{

	private static final long	serialVersionUID	= 1L;
	private JPanel				mainPanel			= null;
	private JMenuBar			menu				= null;
	private JMenu				fileMenu			= null;
	private JMenu				editMenu			= null;
	private JMenu				helpMenu			= null;
	private JMenuItem			openItem			= null;
	private JMenuItem			modifyTagsItem		= null;
	private JMenuItem			quitItem			= null;
	private JMenuItem			aboutItem			= null;
	private JMenuItem			preferencesItem		= null;
	private JToolBar			toolBar				= null;
	private JToggleButton		playButton			= null;
	private JToggleButton		pauseButton			= null;
	private JButton				stopButton			= null;
	private JPanel				leftPanel			= null;
	private JLabel				explorerLabel		= null;
	private JScrollPane			explorerScroll		= null;
	private JExplorerTree		treeExplorer		= null;
	private JScrollPane			tableScroll			= null;
	private JTable				musicTable			= null;
	private DefaultTableModel	tableModel			= null;
	private JPanel				statusPanel			= null;
	private JButton				searchButton		= null;
	private String[]			columns				= { "File", "Title", "Album", "Artist", "Genre" };

	/**
	 * This is the default constructor
	 */
	public JMainWindow()
	{
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(823, 622);
		this.setJMenuBar(getMenu()); // Generated
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Generated
		this.setContentPane(getMainPanel());
		this.setTitle("JIpodExtractor");
	}

	/**
	 * This method initializes mainPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMainPanel()
	{
		if (mainPanel == null)
		{
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(0); // Generated
			mainPanel = new JPanel();
			mainPanel.setLayout(borderLayout); // Generated
			mainPanel.add(getToolBar(), BorderLayout.NORTH); // Generated
			mainPanel.add(getLeftPanel(), BorderLayout.WEST); // Generated
			mainPanel.add(getTableScroll(), BorderLayout.CENTER); // Generated
			mainPanel.add(getStatusPanel(), BorderLayout.SOUTH); // Generated
		}
		return mainPanel;
	}

	/**
	 * This method initializes menu
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getMenu()
	{
		if (menu == null)
		{
			menu = new JMenuBar();
			menu.setPreferredSize(new Dimension(12, 35)); // Generated
			menu.setName("menubar"); // Generated
			menu.add(getFileMenu()); // Generated
			menu.add(getEditMenu()); // Generated
			menu.add(getHelpMenu()); // Generated
		}
		return menu;
	}

	/**
	 * This method initializes fileMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu()
	{
		if (fileMenu == null)
		{
			fileMenu = new JMenu();
			fileMenu.setText("File"); // Generated
			fileMenu.setMnemonic(KeyEvent.VK_F); // Generated
			fileMenu.add(getOpenItem()); // Generated
			fileMenu.add(getModifyTagsItem()); // Generated
			fileMenu.add(getQuitItem()); // Generated
		}
		return fileMenu;
	}

	/**
	 * This method initializes editMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getEditMenu()
	{
		if (editMenu == null)
		{
			editMenu = new JMenu();
			editMenu.setText("Edit"); // Generated
			editMenu.setMnemonic(KeyEvent.VK_E); // Generated
			editMenu.add(getPreferencesItem()); // Generated
		}
		return editMenu;
	}

	/**
	 * This method initializes helpMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelpMenu()
	{
		if (helpMenu == null)
		{
			helpMenu = new JMenu();
			helpMenu.setText("Help"); // Generated
			helpMenu.setMnemonic(KeyEvent.VK_H); // Generated
			helpMenu.add(getAboutItem()); // Generated
		}
		return helpMenu;
	}

	/**
	 * This method initializes openItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getOpenItem()
	{
		if (openItem == null)
		{
			openItem = new JMenuItem();
			openItem.setText("Open"); // Generated
		}
		return openItem;
	}

	/**
	 * This method initializes modifyTagsItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getModifyTagsItem()
	{
		if (modifyTagsItem == null)
		{
			modifyTagsItem = new JMenuItem();
			modifyTagsItem.setText("Modify ID3 tags"); // Generated
		}
		return modifyTagsItem;
	}

	/**
	 * This method initializes quitItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getQuitItem()
	{
		if (quitItem == null)
		{
			quitItem = new JMenuItem();
			quitItem.setText("Quit"); // Generated
		}
		return quitItem;
	}

	/**
	 * This method initializes aboutItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutItem()
	{
		if (aboutItem == null)
		{
			aboutItem = new JMenuItem();
			aboutItem.setText("About..."); // Generated
		}
		return aboutItem;
	}

	/**
	 * This method initializes preferencesItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPreferencesItem()
	{
		if (preferencesItem == null)
		{
			preferencesItem = new JMenuItem();
			preferencesItem.setText("Preferences"); // Generated
		}
		return preferencesItem;
	}

	/**
	 * This method initializes toolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getToolBar()
	{
		if (toolBar == null)
		{
			toolBar = new JToolBar();
			toolBar.setPreferredSize(new Dimension(11, 35)); // Generated
			toolBar.setFloatable(false); // Generated
			toolBar.add(getSearchButton()); // Generated
			toolBar.addSeparator(new Dimension(168, 10));
			toolBar.add(getPlayButton()); // Generated
			toolBar.add(getStopButton()); // Generated
			toolBar.add(getPauseButton()); // Generated
			toolBar.addSeparator();
		}
		return toolBar;
	}

	/**
	 * This method initializes playButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getPlayButton()
	{
		if (playButton == null)
		{
			playButton = new JToggleButton();
			playButton.setText("Play"); // Generated
			playButton.setIcon(new ImageIcon(getClass().getResource("/images/Play24.gif"))); // Generated
		}
		return playButton;
	}

	/**
	 * This method initializes pauseButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getPauseButton()
	{
		if (pauseButton == null)
		{
			pauseButton = new JToggleButton();
			pauseButton.setText("Pause"); // Generated
			pauseButton.setIcon(new ImageIcon(getClass().getResource("/images/Pause24.gif"))); // Generated
		}
		return pauseButton;
	}

	/**
	 * This method initializes stopButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getStopButton()
	{
		if (stopButton == null)
		{
			stopButton = new JButton();
			stopButton.setText("Stop"); // Generated
			stopButton.setIcon(new ImageIcon(getClass().getResource("/images/Stop24.gif"))); // Generated
		}
		return stopButton;
	}

	/**
	 * This method initializes leftPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getLeftPanel()
	{
		if (leftPanel == null)
		{
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH; // Generated
			gridBagConstraints2.gridy = 1; // Generated
			gridBagConstraints2.weightx = 1.0; // Generated
			gridBagConstraints2.weighty = 1.0; // Generated
			gridBagConstraints2.gridx = 0; // Generated
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0; // Generated
			gridBagConstraints.anchor = GridBagConstraints.CENTER; // Generated
			gridBagConstraints.gridheight = 1; // Generated
			gridBagConstraints.gridwidth = 1; // Generated
			gridBagConstraints.fill = GridBagConstraints.BOTH; // Generated
			gridBagConstraints.gridy = 0; // Generated
			explorerLabel = new JLabel();
			explorerLabel.setText("Explorer"); // Generated
			explorerLabel.setVerticalTextPosition(SwingConstants.CENTER); // Generated
			explorerLabel.setFont(new Font("Verdana", Font.BOLD, 14)); // Generated
			explorerLabel.setHorizontalAlignment(SwingConstants.CENTER); // Generated
			explorerLabel.setForeground(new Color(51, 0, 153)); // Generated
			explorerLabel.setVerticalAlignment(SwingConstants.CENTER); // Generated
			leftPanel = new JPanel();
			leftPanel.setLayout(new GridBagLayout()); // Generated
			leftPanel.setPreferredSize(new Dimension(250, 0)); // Generated
			leftPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)); // Generated
			leftPanel.add(explorerLabel, gridBagConstraints); // Generated
			leftPanel.add(getExplorerScroll(), gridBagConstraints2); // Generated
		}
		return leftPanel;
	}

	/**
	 * This method initializes explorerScroll
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getExplorerScroll()
	{
		if (explorerScroll == null)
		{
			explorerScroll = new JScrollPane();
			explorerScroll.setViewportView(getTreeExplorer()); // Generated
		}
		return explorerScroll;
	}

	/**
	 * This method initializes treeExplorer
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getTreeExplorer()
	{
		if (treeExplorer == null)
		{
			treeExplorer = new JExplorerTree();
		}
		return treeExplorer;
	}

	/**
	 * This method initializes tableScroll
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getTableScroll()
	{
		if (tableScroll == null)
		{
			tableScroll = new JScrollPane();
			tableScroll.setViewportView(getMusicTable()); // Generated
		}
		return tableScroll;
	}

	/**
	 * This method initializes musicTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getMusicTable()
	{
		if (musicTable == null)
		{
			tableModel = new DefaultTableModel(columns, 0);
			musicTable = new JTable(tableModel);
		}
		return musicTable;
	}

	/**
	 * This method initializes statusPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getStatusPanel()
	{
		if (statusPanel == null)
		{
			statusPanel = new JPanel();
			statusPanel.setLayout(new GridBagLayout()); // Generated
			statusPanel.setPreferredSize(new Dimension(0, 15)); // Generated
		}
		return statusPanel;
	}

	/**
	 * This method initializes searchButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSearchButton()
	{
		if (searchButton == null)
		{
			searchButton = new JButton();
			searchButton.setActionCommand("search"); // Generated
			searchButton.setText("Search"); // Generated
			searchButton.setIcon(new ImageIcon(getClass().getResource("/images/Search24.gif"))); // Generated
			searchButton.addActionListener(new ViewController());
		}
		return searchButton;
	}

	// ////////////////////////////////////////////////////
	// Inner Controller Classes
	// ////////////////////////////////////////////////////

	class ViewController implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			if (ev.getActionCommand().equals("search"))
			{
				try
				{
					tableModel.setDataVector(new ExtractorModel().getMusicInfo(treeExplorer.getSelectedDir()), columns);
				}
				catch (Exception e)
				{
					//System.out.println("You must select a folder with read permissions...");
					e.printStackTrace();
				}
			}
		}
	}

} // @jve:decl-index=0:visual-constraint="8,8"
