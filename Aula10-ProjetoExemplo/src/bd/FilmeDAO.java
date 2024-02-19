package bd;

import base.Filme;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class FilmeDAO {
    public void cadastrarFilme(Filme filme){
        String sql = "INSERT INTO filme(nome_filme, genero, ano_lancamento, diretor, id_usuario)"
                + "VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstm = null;
        
        try {
            conn = ConexaoBD.criarConexao();
            pstm = conn.prepareStatement(sql);
            
            pstm.setString(1, filme.getNome_filme());
            pstm.setString(2, filme.getGenero());
            pstm.setInt(3, filme.getAno_lancamento());
            pstm.setString(4, filme.getDiretor());
            pstm.setInt(5, 1);
            
            pstm.execute();
            JOptionPane.showMessageDialog(null, "Filme salvo com sucesso!");
            
        } catch (Exception ex){
            System.out.println("ERRO: "+ex);
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
    
    public List<Filme> listarFilmes(Filme filme){
        String sql = "SELECT * FROM filme WHERE nome_filme like ?";
        List<Filme> filmes = new ArrayList<Filme>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        
        try{
            conn = ConexaoBD.criarConexao();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, filme.getNome_filme()+"%");
            
            rs = pstm.executeQuery();
            
            while(rs.next()){
                Filme f = new Filme();
                f.setNome_filme(rs.getString("nome_filme"));
                f.setGenero(rs.getString("genero"));
                f.setDiretor(rs.getString("diretor"));
                f.setAno_lancamento(rs.getInt("ano_lancamento"));
                
                filmes.add(f);
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
        }
        
        return filmes;
    }
    
    public void deletarFilme (String nome){
        String sql = "DELETE FROM filme WHERE nome_filme = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        String sqlSelect = "SELECT id_filme FROM filme WHERE nome_filme = ?";
        ResultSet rs = null;
        
        try {
            conn = ConexaoBD.criarConexao();
            pstm = conn.prepareStatement(sqlSelect);
            pstm.setString(1, nome); 
            
            rs = pstm.executeQuery();
            
            if (rs.next()){
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, nome);
                pstm.execute();
                JOptionPane.showMessageDialog(null, "Filme deletado com sucesso!");
            }else {
                JOptionPane.showMessageDialog(null, "Filme não encontrado!");
            }
            
        } catch (Exception e){
            System.out.println("Erro: "+e);
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
        }
    }
}
