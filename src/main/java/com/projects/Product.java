package com.projects;

public class Product{
    private Long id;
    private String nome;
    private int quantidade;
    private double valor;

    public Product(Long id, String nome, int quantidade, double valor){
        this.id = id;
        this.nome = nome;
        if(quantidade < 0)
            throw new RuntimeException("A quantidade do produto deve ser maior ou igual a zero!");
        this.quantidade = quantidade;
        if(valor < 0)
            throw new RuntimeException("O valor do produto deve ser maior ou igual a zero!");
        this.valor = valor;
    }

    public Product(String nome, int quantidade, double valor){
        this.id = null;
        this.nome = nome;
        if(quantidade < 0)
            throw new RuntimeException("A quantidade do produto deve ser maior ou igual a zero!");
        this.quantidade = quantidade;
        if(valor < 0)
            throw new RuntimeException("O valor do produto deve ser maior ou igual a zero!");
        this.valor = valor;
    }

    public Product( ){
        this(null, "Sem nome", 0, 0);
    }

    public Long getId(){
        return id;
    }
    public String getNome( ){
        return nome;
    }
    public int getQuantidade( ){
        return quantidade;
    }
    public double getValor( ){
        return valor;
    }

    public String toString( ){
        return String.format("%nID: %d%nNome: %s%nQuantidade: %s%nValor: %.2f%n%n", getId( ), getNome( ), getQuantidade(), getValor( ));
    }
}

