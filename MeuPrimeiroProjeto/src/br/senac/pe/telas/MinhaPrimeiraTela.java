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
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class MinhaPrimeiraTela extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MinhaPrimeiraTela dialog = new MinhaPrimeiraTela();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void salvarUsuario() {
        String usuario = textField.getText();
        String senha = textField_1.getText();


        try (Connection conn = ConexaoMySQL.getConnection()) {
            String sql = "INSERT INTO usuario (usuario, senha) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);


            int resultado = stmt.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco de dados!");
        }
    }


	/**
	 * Create the dialog.
	 */
	public MinhaPrimeiraTela() {
		setBounds(100, 100, 260, 220);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("E-mail:");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPanel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(10, 36, 86, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Senha:");
		lblNewLabel_1.setBounds(10, 81, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 118, 86, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//System.out.println("O nome do usuário é: " + textField.getText());
						//System.out.println("A senha é: " + textField_1.getText());
						//JOptionPane.showMessageDialog(null, "O nome do usuário é: " + textField.getText());
						//JOptionPane.showMessageDialog(null, "A senha é: " + textField_1.getText());
						//JOptionPane.showMessageDialog(null, "A senha é: " + textField_1.getText(), "Erro", JOptionPane.ERROR_MESSAGE);
						
						//boolean continuar = true;
						
						
						
						int resposta = JOptionPane.showConfirmDialog(null, "Deseja salvar os dados do usuário?", "Pergunta", JOptionPane.YES_NO_OPTION);
							
							
							if(resposta == JOptionPane.YES_OPTION) {
								//JOptionPane.showMessageDialog(null, "Você clicou em SIM!", "Resposta", JOptionPane.INFORMATION_MESSAGE);
								salvarUsuario();
								
								resposta = JOptionPane.showConfirmDialog(null, "Deseja salvar outro usuário?", "Pergunta", JOptionPane.YES_NO_OPTION);
								
								if(resposta == JOptionPane.YES_OPTION)
								{
									textField.setText("");
									textField_1.setText("");
									textField.requestFocus();
								}
								else
									dispose();
								
							} else if (resposta == JOptionPane.NO_OPTION) {
								JOptionPane.showMessageDialog(null,  "Você clicou em NÃO!", "Resposta", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null,  "Ação cancelada ou fechada!", "Resposta", JOptionPane.WARNING_MESSAGE);
								dispose();
							}
						

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//System.out.println("Operação cancelada");
						JOptionPane.showMessageDialog(null, "Operação cancelada" );
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

