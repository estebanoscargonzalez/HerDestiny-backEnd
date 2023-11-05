package com.dev.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PRODUCTO")
public class Producto {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long idProducto;
    
    
    private String nombre;
    
    private String descripcion;
    
    
    private BigDecimal precio;
    
    
	@ManyToOne
	@JoinColumn(name="ID_PAQUETE")
	private Paquete paquete;
    
	@ManyToOne
	@JoinColumn(name="ID_CATEGORIA")
	private Categoria categoria;
    

}