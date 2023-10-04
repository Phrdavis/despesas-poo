package Entidades;

public class Despesa{

    private int id;
    private String despesa;
    private String categoria;
    private double preco;
    private String data;
    private int id_usuario;


    public String getDespesa(){
        return despesa;
    }
    public void setDespesa(String despesa){
        this.despesa = despesa;
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    
    public String getCategoria(){
        return categoria;
    }
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    
    public double getPreco(){
        return preco;
    }
    public void setPreco(double preco){
        this.preco = preco;
    }

    
    public String getData(){
        return data;
    }
    public void setData(String data){
        this.data = data;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return "Despesa{" +
                "id=" + id +
                ", despesa='" + despesa + '\'' +
                ", categoria=" + categoria +'\'' +
                ", preco=" + preco +'\'' +
                ", data=" + data +
                '}';
    }

}