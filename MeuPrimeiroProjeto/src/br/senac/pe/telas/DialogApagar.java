package br.senac.pe.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.senac.pe.banco.ConexaoMySQL;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class DialogApagar extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldIdUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogApagar dialog = new DialogApagar();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void apagarUsuario() {
	    String idUsuario = textFieldIdUsuario.getText();


	    // Verifica se o campo está vazio
	    if (idUsuario.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Por favor, informe o ID do usuário.");
	        return;
	    }


	    try (Connection conn = ConexaoMySQL.getConnection()) {
	        // Verifica se o usuário existe no banco de dados
	        String sqlVerificar = "SELECT * FROM usuario WHERE id = ?";
	        PreparedStatement stmtVerificar = conn.prepareStatement(sqlVerificar);
	        stmtVerificar.setString(1, idUsuario);
	        ResultSet rs = stmtVerificar.executeQuery();


	        if (rs.next()) {
	            // Se o usuario existir, perguntar se deseja apagar
	            int confirmacao = JOptionPane.showConfirmDialog(this, 
	                    "Usuário encontrado! Deseja apagar o registro?", 
	                    "Confirmação", 
	                    JOptionPane.YES_NO_OPTION);


	            if (confirmacao == JOptionPane.YES_OPTION) {
	                // Realiza a exclusão do registro
	                String sqlApagar = "DELETE FROM usuario WHERE id = ?";
	                PreparedStatement stmtApagar = conn.prepareStatement(sqlApagar);
	                stmtApagar.setString(1, idUsuario);


	                int resultado = stmtApagar.executeUpdate();
	                if (resultado > 0) {
	                    JOptionPane.showMessageDialog(this, "Usuário apagado com sucesso!");
	                    resultado = JOptionPane.showConfirmDialog(null, "Deseja apagar outro usuário?", "Pergunta", JOptionPane.YES_NO_OPTION);
	                    if(resultado == JOptionPane.YES_OPTION)
						{
							textFieldIdUsuario.setText("");
							textFieldIdUsuario.requestFocus();
						}
						else
							dispose();
	                    
	                } else {
	                    JOptionPane.showMessageDialog(this, "Erro ao apagar usuário!");
	                }
	            } else {
	                JOptionPane.showMessageDialog(this, "Operação cancelada.");
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Usuário não encontrado!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados!");
	    }
	}

	
	
	/**
	 * Create the dialog.
	 */
	public DialogApagar() {
		setBounds(100, 100, 197, 209);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID do usuário:");
		lblNewLabel.setBounds(10, 11, 86, 14);
		contentPanel.add(lblNewLabel);
		
		textFieldIdUsuario = new JTextField();
		textFieldIdUsuario.setBounds(10, 52, 86, 20);
		contentPanel.add(textFieldIdUsuario);
		textFieldIdUsuario.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						apagarUsuario();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
