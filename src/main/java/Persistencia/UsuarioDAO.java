package Persistencia;

import Entidades.Despesa;
import Entidades.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO extends GenericDAO {

    public void criarTabela(){
        String sql = """
                    create table if not exists usuarios(
                    id_usuario integer identity primary key,
                    nome varchar(250), 
                    sobrenome varchar(250), 
                    idade integer, 
                    email varchar(250), 
                    senha varchar(250)
                    ) 
                """;

        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){
            p.execute();
        }catch (SQLException e){
            System.out.println("Erro ao criar a tabela usuarios: "+ e);
            e.printStackTrace();
        }
    }

    public void dropTable(){
        String sql = """
                    drop table usuarios
                """;

        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){
            p.execute();
        }catch (SQLException e){
            System.out.println("Erro ao criar a tabela usuarios: "+ e);
            e.printStackTrace();
        }
    }

    public void inserir(Usuario usuario){
        String sql = """
                    insert into usuarios(nome, sobrenome, idade,email,senha) 
                    values (?,?, ?, ?,?)
                """;
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){
            p.setString(1, usuario.getNome());
            p.setString(2, usuario.getSobrenome());
            p.setDouble(3, usuario.getIdade());
            p.setString(4, usuario.getEmail());
            p.setString(5, usuario.getSenha());

            p.executeUpdate();

        }catch (SQLException e){
            System.out.println("Erro ao inserir dados na tabela "+ e);
            e.printStackTrace();
        }
    }

    public List<Usuario> selecionarTodos(){
        String sql = """
                    select
                     id_usuario,
                     nome,
                     sobrenome,
                     idade,
                     email,
                     senha
                     from usuarios
                """;
        List<Usuario> lista = new ArrayList<>();
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){

            ResultSet r = p.executeQuery();
            while(r.next()){
                Usuario d = new Usuario();
                d.setId(r.getInt("id_usuario"));
                d.setNome(r.getString("nome"));
                d.setSobrenome(r.getString("sobrenome"));
                d.setIdade(r.getDouble("idade"));
                d.setEmail(r.getString("email"));
                d.setSenha(r.getString("senha"));

                lista.add(d);
            }

        }catch (SQLException e){
            System.out.println("Erro ao selecionar dados na tabela "+ e);
            e.printStackTrace();
        }
        return lista;
    }

    public Usuario validarEmailUsuario(Usuario usuario){
        String sql = """
                    select
                     id_usuario,
                     nome,
                     sobrenome,
                     idade,
                     email,
                     senha
                     from usuarios
                     where
                     email = (?)
                """;
        Usuario u = new Usuario();
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){
            p.setString(1, usuario.getEmail());
            ResultSet r = p.executeQuery();
            if(r.next()){
                u.setId(r.getInt("id_usuario"));
                u.setNome(r.getString("nome"));
                u.setSobrenome(r.getString("sobrenome"));
                u.setIdade(r.getDouble("idade"));
                u.setEmail(r.getString("email"));
                u.setSenha(r.getString("senha"));
            }

        }catch (SQLException e){
            System.out.println("Erro ao selecionar email na tabela "+ e);
            e.printStackTrace();
        }
        return u;
    }

    public Usuario validarSenhaUsuario(Usuario usuario){
        String sql = """
                    select
                     id_usuario,
                     nome,
                     sobrenome,
                     idade,
                     email,
                     senha
                     from usuarios
                     where
                     senha = (?)
                """;
        Usuario u = new Usuario();
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){
            p.setString(1, usuario.getSenha());
            ResultSet r = p.executeQuery();
            if(r.next()){
                u.setId(r.getInt("id_usuario"));
                u.setNome(r.getString("nome"));
                u.setSobrenome(r.getString("sobrenome"));
                u.setIdade(r.getDouble("idade"));
                u.setEmail(r.getString("email"));
                u.setSenha(r.getString("senha"));
            }

        }catch (SQLException e){
            System.out.println("Erro ao selecionar senha na tabela "+ e);
            e.printStackTrace();
        }
        return u;
    }

    public void deletarTodos(){
        String sql = """
                    delete from usuarios
                """;
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){

            p.executeUpdate();

        }catch (SQLException e){
            System.out.println("Erro ao deletar todos os dados da tabela "+ e);
            e.printStackTrace();
        }
    }
}
