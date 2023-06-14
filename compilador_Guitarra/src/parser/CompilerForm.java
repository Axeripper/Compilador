package parser;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import recovery.RecoverySet;

public class CompilerForm {

	private JFrame frame;
	public String EnderecoArquivo,EnderecoTemporario,bufferIn;
    private int resultadoJanelas;
    private compiladoraula compilador;
    private SimpleNode arvore;
    private JList<String> lineladoA;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompilerForm window = new CompilerForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CompilerForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1039, 639);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ladoA = new javax.swing.JTextArea();
		ladoB = new javax.swing.JTextArea();
		ladoA.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 15));
		ladoA.setFont(ladoA.getFont().deriveFont(Font.BOLD)); 
		ladoB.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 15));
		ladoB.setFont(ladoA.getFont().deriveFont(Font.BOLD)); 
		lineladoA = new JList<>();
		lineladoA.setModel(new DefaultListModel<String>());
		

		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
	    LineNumberRenderer lineNumberRenderer = new LineNumberRenderer(Color.BLUE);
	    lineladoA.setCellRenderer(lineNumberRenderer);
		
		JButton btabrirarquivo = new JButton("Abrir Arquivo");
		btabrirarquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btabrirarquivoActionPerfomed(evt);
            }
        });
		
		JButton btarvore = new JButton("Gerar Arvore");
		btarvore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	redirectCmdOutputToTextArea();
            }
        });
		
		JButton btlimparentrada = new JButton("Limpar Entrada");
		btlimparentrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	limparentradaPerformed(evt);
            }
        });
		
		JButton btlimparsaida = new JButton("Limpar Saida");
		btlimparsaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	limparsaidaPerformed(evt);
            }
        });

		
		
		JButton btlimpartudo = new JButton("Limpar Tudo");
		btlimpartudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	limpartudoPerformed(evt);
            }
        });
		
		JLabel lbEntradas = new JLabel("Entrada de dados");
		lbEntradas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lbsaida = new JLabel("Saida de dados");
		lbsaida.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)
					.addGap(41)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btlimparsaida, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btarvore, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btabrirarquivo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btlimparentrada, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
						.addComponent(btlimpartudo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(48))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(98)
					.addComponent(lbEntradas, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
					.addGap(254)
					.addComponent(lbsaida, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(322, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(124)
							.addComponent(btabrirarquivo)
							.addGap(18)
							.addComponent(btarvore)
							.addGap(18)
							.addComponent(btlimparentrada)
							.addGap(18)
							.addComponent(btlimparsaida)
							.addGap(18)
							.addComponent(btlimpartudo))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lbEntradas)
								.addComponent(lbsaida))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(scrollPane_1)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))))
					.addGap(113))
		);
		
		scrollPane_1.setViewportView(ladoB);
		
		scrollPane.setViewportView(ladoA);
		
		JScrollPane scrollPane_2 = new JScrollPane(lineladoA);
		scrollPane.setRowHeaderView(scrollPane_2);
		scrollPane_2.setPreferredSize(new Dimension(20,0));
		frame.getContentPane().setLayout(groupLayout);
		scrollPane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
		    @Override
		    public void adjustmentValueChanged(AdjustmentEvent e) {
		        int scrollValue = e.getValue();
		        lineladoA.setLocation(lineladoA.getX(), -scrollValue);
		    }
		});
		ladoA.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        updateLineNumbers();
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		        updateLineNumbers();
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {
		        updateLineNumbers();
		    }

		    private void updateLineNumbers() {
		        String[] lines = ladoA.getText().split("\n");
		        DefaultListModel<String> listModel = new DefaultListModel<>();
		        for (int i = 0; i < lines.length; i++) {
		            listModel.addElement(String.valueOf(i + 1));
		        }
		        lineladoA.setModel(listModel);
		    }
		});
		lineladoA.addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		            int selectedIndex = lineladoA.getSelectedIndex();
		            if (selectedIndex != -1) {
		                try {
		                    String[] lines = ladoA.getText().split("\n");
		                    if (selectedIndex < lines.length) {
		                        String selectedLine = lines[selectedIndex];
		                        JOptionPane.showMessageDialog(frame, selectedLine, "Valor da Linha", JOptionPane.INFORMATION_MESSAGE);
		                    }
		                } catch (Exception ex) {
		                    ex.printStackTrace();
		                }
		            }
		        }
		    }
		});


	}
	
  
	@SuppressWarnings("serial")
	class LineNumberRenderer extends DefaultListCellRenderer {
	    private Color lineNumberColor;

	    public LineNumberRenderer(Color lineNumberColor) {
	        this.lineNumberColor = lineNumberColor;
	    }

	    @Override
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

	        setForeground(lineNumberColor);

	        return component;
	    }
	}

	private void removeErrorHighlight() {
    	ladoA.getHighlighter().removeAllHighlights();
    }
    
	private void highlightErrorLine(int errorLine) throws BadLocationException{
    	int staroffset = ladoA.getLineStartOffset(errorLine - 1);
    	int endoffset = ladoA.getLineEndOffset(errorLine - 1);
    	
    	DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
    	ladoA.getHighlighter().addHighlight(staroffset, endoffset, highlightPainter);
    }
    
    private void btabrirarquivoActionPerfomed(java.awt.event.ActionEvent evt) {
    	JFileChooser openFile = new JFileChooser();
    	openFile.showOpenDialog(openFile);
    	resultadoJanelas = JFileChooser.OPEN_DIALOG;
    	if(JFileChooser.APPROVE_OPTION == resultadoJanelas) {
    		EnderecoArquivo = openFile.getSelectedFile().toString();
    		AbrirArquivo();
    	}
    }
    
    private void AbrirArquivo() {
    	try {
    		FileReader fileReader = new FileReader(EnderecoArquivo);
    		BufferedReader reader = new BufferedReader(fileReader);
    		DefaultListModel<String> listModel = new DefaultListModel<>();
    		String line;
    		int lineNumber = 1;
    		while((line =reader.readLine()) != null) {
    			ladoA.append(line + "\n");
    			listModel.addElement("" + lineNumber++);
    		}
    		fileReader.close();
    		reader.close();
    		lineladoA.setModel(listModel);
    	}catch(Exception erro) {
    		JOptionPane.showMessageDialog(null, erro.getMessage());
    	}
    }
    
    private String consumeUtil(RecoverySet syncSet, JTextArea ladoB2) {
    	return "";
    }
    
    @SuppressWarnings("static-access")
	private void redirectCmdOutputToTextArea() {
        String codigo = ladoA.getText();
        RecoverySet sincronizacao = new RecoverySet();
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                ladoB.append(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                String output = new String(b, off, len);
                ladoB.append(output);
            }
        });
        System.setOut(printStream);
        System.setErr(printStream);

        try {
            if (compilador == null) {
                compilador = new compiladoraula(new StringReader(codigo));
            } else {
                compilador.ReInit(new StringReader(codigo));
            }
    
            arvore = compilador.main(); 
            arvore.dump("-->");
            removeErrorHighlight();
        	} catch (ParseException ex) {
        		String errorMessage = consumeUtil(sincronizacao,ladoB);
        		System.out.println("Error message: " + errorMessage);
        		System.out.println("Error line: " + ex.currentToken.beginLine);	
	        	try {
	        		highlightErrorLine(ex.currentToken.beginLine);
	        	}catch(BadLocationException e){
	        		e.printStackTrace();
	        	}
        	}
    	}

    
    private void limparentradaPerformed(java.awt.event.ActionEvent evt) {
        
        ladoA.setText("");
     }
    
    private void limparsaidaPerformed(java.awt.event.ActionEvent evt) {
        
        ladoB.setText("");
     }
    
    private void limpartudoPerformed(java.awt.event.ActionEvent evt) {
        
    	ladoA.setText("");
        ladoB.setText("");
     }
    
    
    
    private javax.swing.JTextArea ladoA;
    private javax.swing.JTextArea ladoB;
}
