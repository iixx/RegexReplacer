package trytocatch.regexreplacer.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.undo.UndoManager;

import trytocatch.swingplus.text.LineLabel;

/**
 * @author trytocatch@163.com
 * @date 2012-12-27
 */
public class RegexReplacer {
	public static void main(String[] a) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				start();
			}
		});
	}
	
	@SuppressWarnings({ "deprecation", "serial" })
	private static void start(){
		try {
			UIManager
					.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
		}
		JFrame f = new JFrame(StrUtils.getStr("RegexReplacer.title"));
		JTextArea jta = new JTextArea(10, 20);
		jta.setLineWrap(true);
		final UndoManager undoManager = new UndoManager();
		jta.getDocument().addUndoableEditListener(undoManager);
		jta.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "undo");
		jta.getActionMap().put("undo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (undoManager.canUndo())
					undoManager.undo();
			}
		});
		jta.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "redo");
		jta.getActionMap().put("redo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (undoManager.canRedo())
					undoManager.redo();
			}
		});
		SearchPanel searchPanel = new SearchPanel(jta);
		searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
		searchPanel.setMinimumSize(searchPanel.getPreferredSize());
		JScrollPane jsp = new JScrollPane(jta);
		jta.setFont(new Font(Font.MONOSPACED, 0, 14));
		jsp.setRowHeaderView(new LineLabel(jta));
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jsp,
				searchPanel);
		splitPane.setResizeWeight(1);
		splitPane.setDividerSize(3);
		f.getContentPane().add(splitPane);
		JMenuBar jmb = new JMenuBar();
		JMenu jm = new JMenu(StrUtils.getStr("RegexReplacer.help"));
		jm.add(new JMenuItem(new AbstractAction(StrUtils
				.getStr("HelpFrame.regexHelp")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				HelpFrame.showRegexHelp();
			}
		}));
		jm.add(new JMenuItem(new AbstractAction(StrUtils
				.getStr("HelpFrame.help")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				HelpFrame.showHelp();
			}
		}));
		jm.add(new JMenuItem(new AbstractAction(StrUtils
				.getStr("HelpFrame.functionsHelp")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				HelpFrame.showFunctionsHelp();
			}
		}));
		jm.add(new JMenuItem(new AbstractAction(StrUtils
				.getStr("HelpFrame.newFunctionHelp")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				HelpFrame.showNewFunctionHelp();
			}
		}));
		jmb.add(jm);
		f.setJMenuBar(jmb);

		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
