package bd;

import base.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.Date;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    public void inserirUsuario(Usuario usuario){
        String sql = "INSERT INTO usuario(nome, email, usuario, senha, dt_nascimento) "
                + "VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        
        try{
           conn = ConexaoBD.criarConexao();
           pstm = conn.prepareStatement(sql);
           pstm.setString(1, usuario.getNome());
           pstm.setString(2, usuario.getEmail());
           pstm.setString(3, usuario.getUsuario());
           pstm.setString(4, usuario.getSenha());
           pstm.setDate(5, new Date(usuario.getDtNascimento().getTime()));
           
           pstm.execute();
           JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
           
        } catch (Exception ex){
            System.out.println("Erro: "+ex);
        } finally{
            try{
                if (pstm!=null){
                    pstm.close();
                }
                if (conn!=null){
                    conn.close();
                }
            } catch(Exception ex){
                System.out.println("Erro: "+ex);
            }
        }
                
    }
}
