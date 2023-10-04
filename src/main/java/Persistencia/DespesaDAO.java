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

public class DespesaDAO extends GenericDAO{

    public void criarTabela(){
        String sql = """
                    create table if not exists despesa(
                    id_despesa integer identity primary key,
                    nome_despesa varchar(250), 
                    categoria varchar(250),
                    preco double,
                    data  varchar(250)
                    ) 
                """;

        try(Connection c = obterConexao();
        PreparedStatement p = c.prepareStatement(sql)){
        p.execute();
        }catch (SQLException e){
            System.out.println("Erro ao criar a tabela despesa: "+ e);
            e.printStackTrace();
        }
    }

    public void dropTable(){
        String sql = """
                    drop table despesa
                """;

        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){
            p.execute();
        }catch (SQLException e){
            System.out.println("Erro ao criar a tabela despesa: "+ e);
            e.printStackTrace();
        }
    }
    public void inserir(Despesa despesa){
        String sql = """
                    insert into despesa(nome_despesa, categoria, preco, data) 
                    values (?,?,?,?)
                """;
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){
            p.setString(1, despesa.getDespesa());
            p.setString(2, despesa.getCategoria());
            p.setDouble(3, despesa.getPreco());
            p.setString(4, despesa.getData());
            
            p.executeUpdate();
        }catch (SQLException e){
            System.out.println("Erro ao inserir dados na tabela "+ e);
            e.printStackTrace();
        }
    }
    public List<Despesa> selecionarTodos(){
        String sql = """
                    select 
                     id_despesa,
                     nome_despesa,
                     categoria,
                     preco,
                     data
                     from despesa
                """;
        List<Despesa> lista = new ArrayList<>();
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){

            ResultSet r = p.executeQuery();
            while(r.next()){
                Despesa d = new Despesa();
                d.setId(r.getInt("id_despesa"));
                d.setDespesa(r.getString("nome_despesa"));
                d.setCategoria(r.getString("categoria"));
                d.setPreco(r.getDouble("preco"));
                d.setData(r.getString("data"));

                lista.add(d);
            }

        }catch (SQLException e){
            System.out.println("Erro ao selecionar dados na tabela "+ e);
            e.printStackTrace();
        }
        return lista;
    }
//    public List<Despesa> selecionarDespesaUsuario(Usuario usuario){
//        String sql = """
//                    select
//                     id_despesa,
//                     nome_despesa,
//                     categoria,
//                     preco,
//                     data,
//                     id_usuario
//                     from despesa
//                     where  id_usuario = (?)
//                """;
//        List<Despesa> lista = new ArrayList<>();
//        try(Connection c = obterConexao();
//            PreparedStatement p = c.prepareStatement(sql)){
//            p.setInt(1, usuario.getId());
//            ResultSet r = p.executeQuery();
//            while(r.next()){
//                Despesa d = new Despesa();
//                d.setId(r.getInt("id_despesa"));
//                d.setDespesa(r.getString("nome_despesa"));
//                d.setCategoria(r.getString("categoria"));
//                d.setPreco(r.getDouble("preco"));
//                d.setData(r.getString("data"));
//
//                lista.add(d);
//            }
//
//        }catch (SQLException e){
//            System.out.println("Erro ao selecionar dados na tabela "+ e);
//            e.printStackTrace();
//        }
//        return lista;
//    }
    public Integer quantidadeDespesa(){
        String sql = """
                    select 
                     count (id_despesa) 
                     as totalDespesas
                     from despesa
                """;
        int qtdDespesa = 0;
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){

            ResultSet r = p.executeQuery();
            if(r.next()){
                qtdDespesa = r.getInt(1);
            }

        }catch (SQLException e){
            System.out.println("Erro ao selecionar dados na tabela "+ e);
            e.printStackTrace();
        }
        return qtdDespesa;
    }
    public Double precoTotal(){
        String sql = """
                    select 
                     sum (preco) 
                     as precoTotal
                     from despesa
                """;
        double precoTotal = 0;
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){

            ResultSet r = p.executeQuery();
            if(r.next()){
                precoTotal = r.getDouble(1);
            }

        }catch (SQLException e){
            System.out.println("Erro ao selecionar dados na tabela "+ e);
            e.printStackTrace();
        }
        return precoTotal;
    }

    public int categoriasTotal(){
        String sql = """
                    select 
                     count (*) 
                     as categoriasTotal
                     from despesa 
                     group by categoria
                """;
        int catTotal = 0;
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){

            ResultSet r = p.executeQuery();
            while(r.next()){
                catTotal += 1;
            }

        }catch (SQLException e){
            System.out.println("Erro ao selecionar dados na tabela "+ e);
            e.printStackTrace();
        }
        return catTotal;
    }

    public void deletar(Despesa despesa){
        String sql = """
                    delete from despesa where id_despesa = ?
                """;
        try(Connection c = obterConexao();
            PreparedStatement p = c.prepareStatement(sql)){
            p.setLong(1, despesa.getId());
            p.executeUpdate();

        }catch (SQLException e){
            System.out.println("Erro ao deletar dados da tabela "+ e);
            e.printStackTrace();
        }
    }

    public void deletarTodos(){
        String sql = """
                    delete from despesa
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