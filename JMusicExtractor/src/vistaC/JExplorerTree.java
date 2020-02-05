package vistaC;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeSelectionModel;

public class JExplorerTree extends JTree
{
	private static final long		serialVersionUID	= 1L;
	private File					selectedDir			= null;
	private DefaultTreeModel		treeModel			= null;
	private DefaultMutableTreeNode	rootNode			= null;

	public JExplorerTree()
	{
		super();

		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		ExplorerTreeListener treeListener = new ExplorerTreeListener();
		addTreeSelectionListener(treeListener);
		addTreeWillExpandListener(treeListener);

		ImageIcon icon = new ImageIcon(getClass().getResource("/images/node.gif"));
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(icon);
		renderer.setClosedIcon(icon);
		renderer.setOpenIcon(icon);
		setCellRenderer(renderer);

		rootNode = new DefaultMutableTreeNode("My Computer");
		treeModel = new DefaultTreeModel(rootNode);
		setModel(treeModel);
		File[] rootFiles = File.listRoots();

		for (File file : rootFiles)
		{
			DefaultMutableTreeNode rootFile = new DefaultMutableTreeNode(file);
			rootNode.add(rootFile);
		}
	}

	public File getSelectedDir()
	{
		return selectedDir;
	}

	// ////////////////////////////////////////////////////
	// Inner Controller Classes
	// ////////////////////////////////////////////////////

	class ExplorerTreeListener implements TreeSelectionListener, TreeWillExpandListener
	{
		public void valueChanged(TreeSelectionEvent ev)
		{
			if (!JExplorerTree.this.isSelectionEmpty())
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) JExplorerTree.this.getLastSelectedPathComponent();
				Object selected = node.getUserObject();
				if ((selected instanceof File) && (((File) selected).isDirectory()) && (((File) selected).canRead()))
				{
					selectedDir = (File) selected;
					File files[] = ((File) selected).listFiles();
					if(files!=null)
					{
						for (File file : files)
						{
							if (file.isDirectory())
							{
								DefaultMutableTreeNode newFile = new DefaultMutableTreeNode(new NodeFile(file.getAbsolutePath()));
								node.add(newFile);
							}
						}
					}
				}
			}
		}

		public void treeWillCollapse(TreeExpansionEvent ev) throws ExpandVetoException
		{

		}

		public void treeWillExpand(TreeExpansionEvent ev) throws ExpandVetoException
		{

		}

	}

	// ////////////////////////////////////////////////////
	// Auxs Classes
	// ////////////////////////////////////////////////////

	class NodeFile extends File
	{
		private static final long	serialVersionUID	= 1L;

		public NodeFile(String path)
		{
			super(path);
		}

		public String toString()
		{
			return getName();
		}
	}
}
