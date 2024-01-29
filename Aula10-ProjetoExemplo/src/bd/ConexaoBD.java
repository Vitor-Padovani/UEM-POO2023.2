package bd;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBD {
    private static final String usuario = "root";
    private static final String senha = "";
    private static final String bd_url = "jdbc:mysql://localhost:3306/projetopoo";
    
    public static Connection criarConexao() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conexao = DriverManager.getConnection(bd_url, usuario, senha);
        return conexao;
    }
    
    public static void main(String[] args) throws Exception{
        Connection con = criarConexao();
        
        if (con!= null){
            System.out.println("Conexao com sucesso! "+con);
        } else {
            System.out.println("Conexão não ativada!");
        }
    }
}
