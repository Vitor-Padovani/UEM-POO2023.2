package bd;

import base.Usuario;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

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
    
    public List<Usuario> listarUsuarios(){
       String sql = "SELECT * FROM usuario";
       List<Usuario> usuarios = new ArrayList<Usuario>();
       
       Connection conn = null;
       PreparedStatement pstm = null;
       ResultSet rs = null;
       
       try{
          conn = ConexaoBD.criarConexao();
          pstm = conn.prepareStatement(sql);
          
          rs = pstm.executeQuery();
          
          while(rs.next()){
              Usuario user = new Usuario();
              user.setId(rs.getInt("id"));
              user.setNome(rs.getString("nome"));
              user.setEmail(rs.getString("email"));
              user.setUsuario(rs.getString("usuario"));
              user.setDtNascimento(rs.getDate("dt_nascimento"));
              
              usuarios.add(user);
          }
           
       } catch (Exception ex){
           System.out.println("Erro: "+ex);
       } finally {
           try{
                if (pstm!=null){
                    pstm.close();
                }
                if (conn!=null){
                    conn.close();
                }
                if (rs!=null){
                    rs.close();
                }
            } catch(Exception ex){
                System.out.println("Erro: "+ex);
            }
        return usuarios;
       }
    }
    
    public void removerporUsuario(String usuario){
        String sql = "DELETE FROM usuario where usuario = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        try{
           conn = ConexaoBD.criarConexao();
           pstm = conn.prepareStatement(sql);
           pstm.setString(1, usuario);
           
           pstm.execute();
           JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
           
        } catch (Exception ex){
            System.out.println("Erro: "+ ex);
        } finally {
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
    
    public boolean autenticar(Usuario usuario){
        boolean autenticado = false;
        String sql = "select usuario, senha from usuario "
                + "where usuario=? and senha=?";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        System.out.println("Aqui");
        
        try{
           conn = ConexaoBD.criarConexao();
           pstm = conn.prepareStatement(sql);
           pstm.setString(1, usuario.getUsuario());
           pstm.setString(2, usuario.getSenha());
           
           rs = pstm.executeQuery();
           
           while (rs.next()){
               String usuarioBanco = rs.getString("usuario");
               String senhaBanco = rs.getString("senha");
               autenticado = true;
           }
           
           
        } catch (Exception ex){
            System.out.println("Erro: "+ex);
        } finally {
           try{
                if (pstm!=null){
                    pstm.close();
                }
                if (conn!=null){
                    conn.close();
                }
                if (rs!=null){
                    rs.close();
                }
            } catch(Exception ex){
                System.out.println("Erro: "+ex);
            }
            return autenticado;  
        }
    }
}
